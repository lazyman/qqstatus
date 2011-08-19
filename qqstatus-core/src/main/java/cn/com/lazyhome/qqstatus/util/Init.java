package cn.com.lazyhome.qqstatus.util;

import java.io.FileInputStream;
import java.util.Calendar;
import java.util.Properties;
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
	 * ����ʼ
	 */
	public void contextInitialized(ServletContextEvent event) {
		rootpath = event.getServletContext().getRealPath("/");

		String file = rootpath + "/WEB-INF/classes/qqstatus.properties";
		SysConfig conf = new SysConfig();
		conf.load(file);

		LineChart.WIDTH = conf.getImageWidth();
		LineChart.HEIGHT = conf.getImageHeight();
		CheckStatus.URL_BEGIN = conf.getImageUrlBegin();
		CheckStatus.URL_END = conf.getImageUrlEnd();
		CheckStatus.IMAGE_SIZE = conf.getImageSize();
		
		// ��ʱ��ѯ״̬
		fetcher = new FetchStatus();
		fetcher.setRun(conf.isRunFetcher());
		fetcher.setPeriod(conf.getFetcherPeriod());
		
		thread = new Thread(fetcher);
		thread.start();
		
		// �����ʼ�֪ͨ
		tracker = new Tracker();
		tracker.setRun(conf.isRunTracker());
		tracker.setPeriod(conf.getTrackerPeriod());
		new Thread(tracker).start();
		
		// ÿ����ʷ״̬֪ͨ
		Timer timer = new Timer(true);
		MailNotify task = new MailNotify();
		task.setRepeat(false);
		
		Calendar firstTime = Calendar.getInstance();

		firstTime.set(Calendar.HOUR_OF_DAY, 1);
		firstTime.set(Calendar.MINUTE, 0);
		firstTime.set(Calendar.SECOND, 0);

		logger.info(firstTime.before(Calendar.getInstance()));
		if(firstTime.before(Calendar.getInstance())) {
			firstTime.add(Calendar.DAY_OF_YEAR, 1);
		}
		
		timer.schedule(task, firstTime.getTime(), conf.getMailPeriod());
	}

	/**
	 * �����˳�
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
