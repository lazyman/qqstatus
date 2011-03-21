package cn.com.lazyhome.qqstatus.util;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;



public class Init implements ServletContextListener {
	private Thread thread;
	private FetchStatus fetcher;
	
	private static String rootpath;
	
	/**
	 * 程序开始
	 */
	public void contextInitialized(ServletContextEvent event) {
		rootpath = event.getServletContext().getRealPath("/");
		
		fetcher = new FetchStatus();
		
		thread = new Thread(fetcher);
		thread.start();
		
	}

	/**
	 * 程序退出
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
