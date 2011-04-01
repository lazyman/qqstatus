package cn.com.lazyhome.qqstatus.util;

import java.sql.Date;
import java.util.Calendar;
import java.util.Timer;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;


public class Init implements ServletContextListener {
	private Thread thread;
	private FetchStatus fetcher;
	
	private static String rootpath;
	
	/**
	 * ����ʼ
	 */
	public void contextInitialized(ServletContextEvent event) {
		rootpath = event.getServletContext().getRealPath("/");
		
		fetcher = new FetchStatus();
		
		thread = new Thread(fetcher);
		thread.start();
		
		//���1��
		long period = 1000 * 60 * 60 * 24;
		// �ӳ�һ��ʱ��ִ��
		long delay = 5000;
		
		Timer timer = new Timer(true);
		MailNotify task = new MailNotify();
		task.setRepeat(false);
		
		Calendar firstTime = Calendar.getInstance();
		firstTime.set(Calendar.HOUR_OF_DAY, 8);
		firstTime.set(Calendar.MINUTE, 0);
		firstTime.set(Calendar.SECOND, 0);
		firstTime.add(Calendar.DATE, 1);
		
		timer.schedule(task, firstTime.getTime(), period);
	}

	/**
	 * �����˳�
	 */
	public void contextDestroyed(ServletContextEvent event) {
		fetcher.setRun(false);
		
		synchronized (fetcher) {
			fetcher.notify();
		}
		fetcher = null;
		
		thread = null;
	}

	public static String getRootpath() {
		return rootpath;
	}

}
