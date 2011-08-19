package cn.com.lazyhome.qqstatus.util;

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

	public void run() {
		while(run) {
			CheckStatus check = new CheckStatus();
			Log log = check.checking(qq);
			
			if(laststat == null) {
				laststat = log;
				continue;
			}
			
			if(laststat.getStatus() != log.getStatus()) {
				laststat = log;
				
				MailSenderInfo mailInfo = new MailSenderInfo();
				mailInfo.setMailProp(MailSenderInfo.getGmailProp());
				mailInfo.setUserName("dchrainbow@gmail.com");
				mailInfo.setPassword("d19840226");// 您的邮箱密码
				mailInfo.setFromAddress("dch438@163.com");
				mailInfo.setToAddress(mail);
				mailInfo.setSubject(qq + " - " + log.getStatus() + " - " + log.getTime());
				mailInfo.setContent(log.getStatus() + " - " + log.getTime());
			}
			
			
			synchronized (this) {
				try {
					this.wait(60000);
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

}
