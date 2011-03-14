package cn.com.lazyhome.qqstatus.util;

import java.util.List;

import org.hibernate.Query;
import org.hibernate.Session;

public class Tasker implements Runnable {
	private boolean run = true;

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
					this.wait(300000);
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

	public static void main(String[] args) {
		new Thread(new Tasker()).start();
	}

}
