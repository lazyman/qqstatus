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
		//���1�죬�ʼ�֪ͨ����
		long period;
		// �ӳ�һ��ʱ��ִ��
		long delay;
		// �������״̬���
		long fetch_period;
		// ͳ����ϢͼƬ��С
		int width, height;
		String url_begin;
		String url_end;
		long imageSize;
		
		Properties props = new Properties();
		try {
			// �������ļ���ȡ����
			String file = rootpath + "/WEB-INF/classes/qqstatus.properties";
			logger.debug("qqstatus.properties path:\t" + file);
			FileInputStream fis = new FileInputStream(file);
			props.load(fis);
			String prop = props.getProperty("mail.notify.period");
			period = Long.parseLong(prop);
			
			prop = props.getProperty("mail.notify.delay");
			delay = Long.parseLong(prop);
			
			prop = props.getProperty("fetcher.period");
			fetch_period = Long.parseLong(prop);
			
			prop = props.getProperty("image.height");
			height = Integer.parseInt(prop);

			prop = props.getProperty("image.width");
			width = Integer.parseInt(prop);
			
			// URL
			url_begin = props.getProperty("image.url.begin");
			url_end = props.getProperty("image.url.end");
			
			prop = props.getProperty("image.size");
			imageSize = Integer.parseInt(prop);
			
		} catch (Exception e) {
			period = 1000 * 60 * 60 * 24;
			delay = 5000;
			fetch_period = 60000;
			
			
			width = 200;
			height = 15000;
			url_begin = "http://wpa.qq.com/pa?p=2:";
			url_end = ":41";
			imageSize = 1243;
			
			logger.warn("failed load qqstatus.properties file!! use the default config.");
			logger.warn(e.getMessage(), e);
		}

		LineChart.WIDTH = width;
		LineChart.HEIGHT = height;
		CheckStatus.URL_BEGIN = url_begin;
		CheckStatus.URL_END = url_end;
		CheckStatus.IMAGE_SIZE = imageSize;
		
		// ��ʱ��ѯ״̬
		fetcher = new FetchStatus();
		fetcher.setPeriod(fetch_period);
		
		thread = new Thread(fetcher);
		thread.start();
		
		// �����ʼ�֪ͨ
		tracker = new Tracker();
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
		
		timer.schedule(task, firstTime.getTime(), period);
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
