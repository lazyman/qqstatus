package cn.com.lazyhome.qqstatus.util;

import java.io.InputStream;
import java.util.Calendar;
import java.util.Timer;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import cn.com.lazyhome.qqstatus.LineChart;


public class Init implements ServletContextListener {
	private static Log logger = LogFactory.getLog(Init.class);
	private Thread thread;
	private FetchStatus fetcher;
	private Tracker tracker;
	
	private static String rootpath;
	
	/**
	 * 程序开始
	 */
	public void contextInitialized(ServletContextEvent event) {
		rootpath = event.getServletContext().getRealPath("/");
		
		// 设置web目录属性，用于log4j相对路径的使用
		System.setProperty("workdir", rootpath);

		//String file = rootpath + "/WEB-INF/classes/qqstatus.properties";
		SysConfig conf = new SysConfig();

		InputStream is = getClass().getResourceAsStream("/qqstatus.properties");
		conf.load(is);

		logger.debug("config paramter");
		LineChart.WIDTH = conf.getImageWidth();
		LineChart.HEIGHT = conf.getImageHeight();
		CheckStatus.URL_BEGIN = conf.getImageUrlBegin();
		CheckStatus.URL_END = conf.getImageUrlEnd();
		CheckStatus.IMAGE_SIZE = conf.getImageSize();
		
		// 定时查询状态
		fetcher = new FetchStatus();
		fetcher.setRun(conf.isRunFetcher());
		fetcher.setPeriod(conf.getFetcherPeriod());
		
		thread = new Thread(fetcher);
		thread.start();
		
		// 上线邮件通知
		tracker = new Tracker();
		tracker.setRun(conf.isRunTracker());
		tracker.setPeriod(conf.getTrackerPeriod());
		new Thread(tracker).start();
		
		// 每日历史状态通知
		Timer timer = new Timer(true);
		MailNotify mailNotify = new MailNotify();
		mailNotify.setRepeat(false);
		
		Calendar firstTime = Calendar.getInstance();

		firstTime.set(Calendar.HOUR_OF_DAY, 1);
		firstTime.set(Calendar.MINUTE, 0);
		firstTime.set(Calendar.SECOND, 0);

		if(firstTime.before(Calendar.getInstance())) {
			firstTime.add(Calendar.DAY_OF_YEAR, 1);
		}
		
		timer.schedule(mailNotify, firstTime.getTime(), conf.getMailPeriod());
	}

	/**
	 * 程序退出
	 */
	public void contextDestroyed(ServletContextEvent event) {
		fetcher.setRun(false);
		tracker.setRun(false);
		
		synchronized (fetcher) {
			fetcher.notify();
		}
		
		thread = null;
	}

	public static String getRootpath() {
		return rootpath;
	}

}
