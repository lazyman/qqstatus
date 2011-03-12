package cn.com.lazyhome.qqstatus.util;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;



public class Init implements ServletContextListener {
	private Thread thread;
	private Tasker tasker;
	
	private static String rootpath;
	
	/**
	 * 程序开始
	 */
	public void contextInitialized(ServletContextEvent event) {
		rootpath = event.getServletContext().getRealPath("/");
		
		tasker = new Tasker();
		
		thread = new Thread(tasker);
		thread.start();
		
	}

	/**
	 * 程序退出
	 */
	public void contextDestroyed(ServletContextEvent event) {
		tasker.setRun(false);
		tasker = null;
		
		thread = null;
	}

	public static String getRootpath() {
		return rootpath;
	}

}
