package cn.com.lazyhome.qqstatus.util;

import java.util.List;

import org.apache.commons.logging.LogFactory;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.Transaction;

import cn.com.lazyhome.qqstatus.bean.Log;

public class FetchStatus implements Runnable {
	private static org.apache.commons.logging.Log logger = LogFactory.getLog(FetchStatus.class);
	
	private boolean run = true;
	private long period = 60000;

	public void run() {
		String hql = "select distinct c.qqId from Concern c";

		Session s = null;
		while (run) {
			try {
				s = HibernateUtil.getSessionFactory().openSession();
				Query q = s.createQuery(hql);
				CheckStatus check = new CheckStatus();
	
				@SuppressWarnings("unchecked")
				List<String> concerns = q.list();
	
				int size = concerns.size();
				for (int i = 0; i < size; i++) {
					String qq = concerns.get(i);
					
					// 将检查记录存入数据库
					Log log = check.checking(qq);
					Transaction t = s.beginTransaction();
					s.save(log);
					t.commit();
				}
			} catch (Exception e) {
				// 采集图片信息出错时不致线程终止
				logger.error(e.getMessage(), e);
			} finally {
				s.close();
			}
			
			synchronized (this) {
				try {
					logger.debug("我休息一下");
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

	public static void main(String[] args) {
		new Thread(new FetchStatus()).start();
	}

}
