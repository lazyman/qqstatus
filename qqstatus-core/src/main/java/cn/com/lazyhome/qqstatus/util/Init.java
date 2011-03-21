package cn.com.lazyhome.qqstatus.util;

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
