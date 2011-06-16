package cn.com.lazyhome.qqstatus.util;

import java.util.List;

import org.hibernate.Query;
import org.hibernate.Session;

public class FetchStatus implements Runnable {
	private boolean run = true;
	private long period = 300000;

	public void run() {
		String hql = "select distinct c.qqId from Concern c";

		while (run) {
			Session s = HibernateUtil.getSessionFactory().openSession();
			Query q = s.createQuery(hql);
			CheckStatus check = new CheckStatus();

			List<String> concerns = q.list();

			int size = concerns.size();
			for (int i = 0; i < size; i++) {
				String qq = concerns.get(i);
				check.checking(qq);
			}
			
			synchronized (this) {
				try {
					this.wait(period);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
			
			s.close();
			
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
