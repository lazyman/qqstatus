package cn.com.lazyhome.qqstatus.util;

import java.util.List;

import org.hibernate.Query;
import org.hibernate.Session;

import cn.com.lazyhome.qqstatus.bean.Qq;

public class Tasker implements Runnable {
	private boolean run = true;

	public void run() {
		String hql = "from Qq";

		while (run) {
			Session s = HibernateUtil.getSessionFactory().openSession();
			Query q = s.createQuery(hql);
			CheckStatus check = new CheckStatus();

			List<Qq> qqs = q.list();

			int size = qqs.size();
			for (int i = 0; i < size; i++) {
				Qq qq = qqs.get(i);
				check.checking(qq.getQqId());
			}
			
			try {
				Thread.sleep(300000);
			} catch (InterruptedException e) {
				e.printStackTrace();
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
