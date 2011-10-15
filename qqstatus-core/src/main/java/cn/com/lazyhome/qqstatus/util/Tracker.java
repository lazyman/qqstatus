package cn.com.lazyhome.qqstatus.util;

import java.util.HashMap;
import java.util.List;

import org.apache.commons.logging.LogFactory;
import org.hibernate.Query;
import org.hibernate.Session;

import cn.com.lazyhome.qqstatus.bean.Concern;
import cn.com.lazyhome.qqstatus.bean.Log;
import cn.com.lazyhome.util.mail.neteasy.MailSenderInfo;
import cn.com.lazyhome.util.mail.neteasy.SimpleMailSender;

/**
 * 跟踪QQ状态，只要一上线就发到目标邮箱。
 * @author Administrator
 *
 */
public class Tracker implements Runnable {
	private static org.apache.commons.logging.Log logger = LogFactory.getLog(Tracker.class);
	
	private boolean run = true;
//	private String qq = "109719189";
//	private String mail = "18957689879@189.cn";
	private HashMap<String, Log> laststat = new HashMap<String, Log>();
	private long period = 60000;

	@SuppressWarnings("unchecked")
	public void run() {
		while(run) {
			Session s = HibernateUtil.getSessionFactory().openSession();
			//找出所有需要跟踪的号
			String hql = "from Concern c where c.trace = '1'";
			Query q = s.createQuery(hql);
			List<Concern> concerns = q.list();
			
			for(int i = 0; i<concerns.size(); i++) {
				Concern c = concerns.get(i);
				hql = "from Log l where l.qqId = ? order by l.time desc";
				q = s.createQuery(hql);
				q.setString(0, c.getQqId());
				q.setMaxResults(1);
				
				List<Log> logs = q.list();
				Log log = null;
				if (logs.size() > 0 )
					log = logs.get(0);

				Log status = laststat.get(concerns.get(i).getQqId());
				if(status == null) {
					laststat.put(c.getQqId(), log);
//					status = log;
					
					// 将第一次的跟踪状态显示到日志中
					if(log != null) {
						logger.info("Tracker from:" + c.getQqId() + " - " + log.getStatus());
					} else {
						logger.info("Tracker:null" );
					}
				} else {
					if(status.getStatus() != log.getStatus()) {
						logger.info(c.getQqId() + " found the status changed, send message to mail:" + c.getTraceMail());
						laststat.put(c.getQqId(), log);
//						status = log;
						
						MailSenderInfo mailInfo = new MailSenderInfo();
						mailInfo.setMailProp(MailSenderInfo.getGmailProp());
						mailInfo.setUserName("dchrainbow@gmail.com");
						mailInfo.setPassword("d19840226");
						mailInfo.setFromAddress("dch438@163.com");
						mailInfo.setToAddress(c.getTraceMail());
						mailInfo.setSubject(c.getQqId() + " - " + log.getStatus() + " - " + log.getTime());
						mailInfo.setContent(log.getStatus() + " - " + log.getTime());
						
						SimpleMailSender.sendHtmlMail(mailInfo);
					}
				}
			}
			s.close();
			
			
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
