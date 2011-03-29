package cn.com.lazyhome.qqstatus.action;

import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

import cn.com.lazyhome.qqstatus.LineChart;

import com.opensymphony.xwork2.ActionSupport;

/**
 * 显示查询信息
 * @author Administrator
 *
 */
public class Show extends ActionSupport {
	private String qqId;
	private Date date;

	@Override
	public String execute() throws Exception {
		LineChart chart = new LineChart(qqId);
		if(date != null ) {
			Calendar c = new GregorianCalendar();
			c.setTime(date);
			chart.setBegintime(c);
		}
		try {
			Calendar now = new GregorianCalendar();
			now.add(Calendar.DATE, -1);
			
			//每次显示图片前，先绘制。以后改为历史图片不再重新绘制
//			if(now.before(chart.getBegintime())) {
				chart.writeImage();
//			}
		} catch (Exception e) {
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
