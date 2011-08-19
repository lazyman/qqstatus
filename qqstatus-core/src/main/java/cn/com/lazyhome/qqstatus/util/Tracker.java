package cn.com.lazyhome.qqstatus.util;

import java.util.List;

import org.hibernate.Query;
import org.hibernate.Session;

import cn.com.lazyhome.qqstatus.bean.Log;
import cn.com.lazyhome.util.mail.neteasy.MailSenderInfo;

/**
 * 跟踪QQ状态，只要一上线就发到目标邮箱。
 * @author Administrator
 *
 */
public class Tracker implements Runnable {
	private boolean run = true;
	private String qq = "109719189";
	private String mail = "18957689879@189.cn";
	private Log laststat;
	private long period = 60000;

	@SuppressWarnings("unchecked")
	public void run() {
		while(run) {
			Session s = HibernateUtil.getSessionFactory().openSession();
			String hql = "from Log l where l.qqId = ? order by l.time desc";
			Query q = s.createQuery(hql);
			q.setString(0, qq);
			q.setMaxResults(1);
			
			List<Log> logs = q.list();
			Log log = logs.get(0);
			s.close();
			
			if(laststat == null) {
				laststat = log;
				continue;
			}
			
			if(laststat.getStatus() != log.getStatus()) {
				laststat = log;
				
				MailSenderInfo mailInfo = new MailSenderInfo();
				mailInfo.setMailProp(MailSenderInfo.getGmailProp());
				mailInfo.setUserName("dchrainbow@gmail.com");
				mailInfo.setPassword("d19840226");
				mailInfo.setFromAddress("dch438@163.com");
				mailInfo.setToAddress(mail);
				mailInfo.setSubject(qq + " - " + log.getStatus() + " - " + log.getTime());
				mailInfo.setContent(log.getStatus() + " - " + log.getTime());
			}
			
			
			synchronized (this) {
				try {
					this.wait(period);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
		}
	}

	public boolean isRun() {
		return run;
	}

	public void setRun(boolean run) {
		this.run = run;
	}

	public long getPeriod() {
		return period;
	}

	public void setPeriod(long period) {
		this.period = period;
	}

}
