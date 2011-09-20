package cn.com.lazyhome.qqstatus.util;

import java.io.InputStream;
import java.util.Properties;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

public class SysConfig {
	private static Log logger = LogFactory.getLog(SysConfig.class);
	
	// 间隔1天，邮件通知任务
	private long mailPeriod;
	private long mailDelay;
	// 采集器启动开关
	private boolean runFetcher, runTracker;
	// 延迟一定时间执行
	private long fetcherdelay;
	// 检查在线状态间隔
	private long fetcherPeriod;
	private long trackerPeriod;
	// image统计信息图片大小
	private int imageWidth, imageHeight;
	private String imageUrlBegin;
	private String imageUrlEnd;
	private long imageSize;
	
	public void load(InputStream is) {
		logger.info("loading properties...");
		Properties props = new Properties();
		try {
			// 从配置文件读取参数
//			String file = is;
//			logger.debug("qqstatus.properties path:\t" + file);
//			FileInputStream fis = new FileInputStream(file);
			props.load(is);
			String prop;
			
			prop = props.getProperty("fetcher.period");
			fetcherPeriod = Long.parseLong(prop);
			
			prop = props.getProperty("fetcher.run");
			runFetcher = prop.equals("true");
			
			//image
			prop = props.getProperty("image.height");
			imageHeight = Integer.parseInt(prop);

			prop = props.getProperty("image.width");
			imageWidth = Integer.parseInt(prop);
			
			// URL
			imageUrlBegin = props.getProperty("image.url.begin");
			imageUrlEnd = props.getProperty("image.url.end");
			
			prop = props.getProperty("image.size");
			imageSize = Integer.parseInt(prop);
			
			// tracker
			prop = props.getProperty("tracker.run");
			runTracker = prop.equals("true");
			prop = props.getProperty("tracker.period");
			trackerPeriod = Long.parseLong(prop);
			
			//mail
			prop = props.getProperty("mail.notify.period");
			mailPeriod = Long.parseLong(prop);
			
			prop = props.getProperty("mail.notify.delay");
			mailDelay = Long.parseLong(prop);
			
		} catch (Exception e) {
			runFetcher = false;
			mailPeriod = 1000 * 60 * 60 * 24;
			fetcherdelay = 5000;
			fetcherPeriod = 60000;
			
			runTracker = false;
			trackerPeriod = 60000;
			
			
			imageWidth = 200;
			imageHeight = 15000;
			imageUrlBegin = "http://wpa.qq.com/pa?p=2:";
			imageUrlEnd = ":41";
			imageSize = 1243;
			
			logger.warn("failed load qqstatus.properties file!! use the default config.");
			logger.warn(e.getMessage(), e);
		}
		
		logger.info("properties is loaded.");
	}

	public long getMailPeriod() {
		return mailPeriod;
	}

	public void setMailPeriod(long period) {
		this.mailPeriod = period;
	}

	public boolean isRunFetcher() {
		return runFetcher;
	}

	public void setRunFetcher(boolean runFetcher) {
		this.runFetcher = runFetcher;
	}

	public boolean isRunTracker() {
		return runTracker;
	}

	public void setRunTracker(boolean runTracker) {
		this.runTracker = runTracker;
	}

	public long getFetcherdelay() {
		return fetcherdelay;
	}

	public void setFetcherdelay(long fetcherdelay) {
		this.fetcherdelay = fetcherdelay;
	}

	public long getFetcherPeriod() {
		return fetcherPeriod;
	}

	public void setFetcherPeriod(long fetcherPeriod) {
		this.fetcherPeriod = fetcherPeriod;
	}

	public long getTrackerPeriod() {
		return trackerPeriod;
	}

	public void setTrackerPeriod(long trackerPeriod) {
		this.trackerPeriod = trackerPeriod;
	}

	public int getImageWidth() {
		return imageWidth;
	}

	public void setImageWidth(int imageWidth) {
		this.imageWidth = imageWidth;
	}

	public int getImageHeight() {
		return imageHeight;
	}

	public void setImageHeight(int imageHeight) {
		this.imageHeight = imageHeight;
	}

	public String getImageUrlBegin() {
		return imageUrlBegin;
	}

	public void setImageUrlBegin(String imageUrlBegin) {
		this.imageUrlBegin = imageUrlBegin;
	}

	public String getImageUrlEnd() {
		return imageUrlEnd;
	}

	public void setImageUrlEnd(String imageUrlEnd) {
		this.imageUrlEnd = imageUrlEnd;
	}

	public long getImageSize() {
		return imageSize;
	}

	public void setImageSize(long imageSize) {
		this.imageSize = imageSize;
	}

	public long getMailDelay() {
		return mailDelay;
	}

	public void setMailDelay(long mailDelay) {
		this.mailDelay = mailDelay;
	}
	
	
}
