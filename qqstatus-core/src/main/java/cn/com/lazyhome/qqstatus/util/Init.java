package cn.com.lazyhome.qqstatus.util;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;



public class Init implements ServletContextListener {
	private Thread thread;
	private Tasker tasker;
	
	private static String rootpath;
	
	/**
	 * ����ʼ
	 */
	public void contextInitialized(ServletContextEvent event) {
		rootpath = event.getServletContext().getRealPath("/");
		
		tasker = new Tasker();
		
		thread = new Thread(tasker);
		thread.start();
		
	}

	/**
	 * �����˳�
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
