package cn.com.lazyhome.qqstatus.util;

import static org.junit.Assert.*;

import org.junit.Test;

public class SysConfigTest {

	@Test
	public void testLoad() {
		String rootpath = System.getProperty("");
		String file = rootpath + "/qqstatus.properties";
		SysConfig conf = new SysConfig();
		conf.load(file);
		
		//# fetch qq status images per minute
		assertEquals("fetcher.run", false, conf.isRunFetcher());
		assertEquals("fetcher.period", 300000, 300000);
		
		//#image size
		assertEquals("image.height", 1500, conf.getImageHeight());
		assertEquals("image.width", 200, conf.getImageWidth());
		assertEquals("image.url.begin", "http://wpa.qq.com/pa?p=2:", conf.getImageUrlBegin());
		assertEquals("image.url.end", "41", conf.getImageUrlEnd());
		assertEquals("image.size", 1243, conf.getImageSize());
		
		//tracker
		assertEquals("tracker.run", true, conf.isRunTracker());
		assertEquals("tracker.period", 60000, conf.getTrackerPeriod());
		
		//mail
		assertEquals("mail.notify.period", 86400000, conf.getMailPeriod());
		assertEquals("mail.notify.delay", 5000, conf.getMailPeriod());
		assertEquals("mail.username", "", "");
		assertEquals("mail.password", "", "");
		assertEquals("mail.pop3", "", "");
		assertEquals("mail.type", "", "");
	}

}
