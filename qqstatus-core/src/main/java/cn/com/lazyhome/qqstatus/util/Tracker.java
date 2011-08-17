package cn.com.lazyhome.qqstatus.util;

import cn.com.lazyhome.qqstatus.bean.Log;
import cn.com.lazyhome.util.mail.neteasy.MailSenderInfo;

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
				mailInfo.setPassword("d19840226");// ƒ˙µƒ” œ‰√‹¬Î
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
