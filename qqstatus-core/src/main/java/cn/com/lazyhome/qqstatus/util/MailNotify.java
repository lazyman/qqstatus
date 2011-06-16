package cn.com.lazyhome.qqstatus.util;

import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.List;
import java.util.TimerTask;
import java.util.Vector;

import org.hibernate.Query;
import org.hibernate.Session;

import cn.com.lazyhome.qqstatus.LineChart;
import cn.com.lazyhome.qqstatus.bean.Concern;
import cn.com.lazyhome.util.mail.neteasy.MailSenderInfo;
import cn.com.lazyhome.util.mail.neteasy.SimpleMailSender;

public class MailNotify extends TimerTask  {
	private boolean repeat = true;

	public void run() {
		String hql = "from Concern c";

		do {
			Session s = HibernateUtil.getSessionFactory().openSession();
			Query q = s.createQuery(hql);
			CheckStatus check = new CheckStatus();
			
			
			List<Concern> concerns = q.list();
			
			int size = concerns.size();
			for (int i = 0; i < size; i++) {
				Concern c = concerns.get(i);
				String qq = c.getQqId();
				String mail = c.getMail();
//				check.checking(qq);
				if(mail == null || mail.trim().equals("")) {
					// 邮箱若为空，则跳过下一个不发送
					continue;
				}
				
				LineChart chart = new LineChart(qq);
				Calendar cal = new GregorianCalendar();
				cal.add(Calendar.DATE, -1);
				chart.setBegintime(cal);
				
				try {
					String file = chart.writeImage();
					
					MailSenderInfo mailInfo = new MailSenderInfo();
					mailInfo.setMailProp(MailSenderInfo.getGmailProp());
					mailInfo.setUserName("dchrainbow@gmail.com");
					mailInfo.setPassword("d19840226");// 您的邮箱密码
					mailInfo.setFromAddress("dch438@163.com");
					mailInfo.setToAddress(mail);
					mailInfo.setSubject(qq + "状态");
					mailInfo.setContent("设置邮箱内容 如http://www.guihua.org 中国桂花网 是中国最大桂花网站==");
					
					// 附件图片
					Vector<String> attchment = new Vector<String>();
					attchment.add(file);
					mailInfo.setAttachFileNames(attchment);
					
					// 这个类主要来发送邮件
//					SimpleMailSender sms = new SimpleMailSender();
//					sms.sendTextMail(mailInfo);// 发送文体格式
					SimpleMailSender.sendHtmlMail(mailInfo);// 发送html格式
				} catch (Exception e) {
					//TODO
					e.printStackTrace();
				}
			}
			
//			synchronized (this) {
//				try {
//					this.wait(300000);
//				} catch (InterruptedException e) {
//					e.printStackTrace();
//				}
//			}
			
			s.close();
		} while(repeat);
	}
	
	public boolean isRepeat() {
		return repeat;
	}

	public void setRepeat(boolean run) {
		this.repeat = run;
	}

	public static void main(String[] args) {
		MailNotify notify = new MailNotify();
		new Thread(notify).start();
	}
}
