package cn.com.lazyhome.qqstatus.action;

import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

import org.apache.commons.logging.LogFactory;

import cn.com.lazyhome.qqstatus.LineChart;

import com.opensymphony.xwork2.ActionSupport;

/**
 * 图片形式显示查询信息
 * @author Administrator
 *
 */
@SuppressWarnings("serial")
public class ShowPic extends ActionSupport {
	private static org.apache.commons.logging.Log logger = LogFactory.getLog(ShowPic.class);
	
	private String qqId;
	private Date date;

	@Override
	public String execute() throws Exception {
		LineChart chart = new LineChart(qqId);
		
		// 当输入日期为空时，取当天日期
		Calendar c = new GregorianCalendar();
		if(date != null ) {
			c.setTime(date);
		} else {
			c.set(Calendar.HOUR_OF_DAY, 0);
			c.set(Calendar.MINUTE, 0);
			c.set(Calendar.SECOND, 0);
			c.set(Calendar.MILLISECOND, 0);
		}
		date = c.getTime();
		
		chart.setBegintime(c);
		try {
			chart.writeImage();
		} catch (Exception e) {
			logger.debug(e.getMessage());
			return e.getMessage();
		}
		
		return SUCCESS;
	}

	
	public String getQqId() {
		return qqId;
	}
	public void setQqId(String qqId) {
		this.qqId = qqId;
	}
	public Date getDate() {
		return date;
	}
	public void setDate(Date date) {
		this.date = date;
	}

}
