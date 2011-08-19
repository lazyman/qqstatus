package cn.com.lazyhome.qqstatus.action;

import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;
import java.util.Vector;

import org.hibernate.Query;
import org.hibernate.Session;

import cn.com.lazyhome.qqstatus.LineChart;
import cn.com.lazyhome.qqstatus.bean.Log;
import cn.com.lazyhome.qqstatus.util.HibernateUtil;

import com.opensymphony.xwork2.ActionSupport;

/**
 * 显示查询信息
 * @author Administrator
 *
 */
@SuppressWarnings("serial")
public class ShowText extends ActionSupport {
	private String qqId;
	private Date date;
	private Vector<Log> textlog;

	@Override
	public String execute() throws Exception {
		LineChart chart = new LineChart(qqId);
		if(date != null ) {
			Calendar c = new GregorianCalendar();
			c.setTime(date);
			chart.setBegintime(c);
		}
		try {
			chart.writeImage();
		} catch (Exception e) {
			return e.getMessage();
		}
		
		return SUCCESS;
	}
	public String showtext() {

		Session s = HibernateUtil.getSessionFactory().openSession();
		String hql = "from Log l where l.qqId = ? and l.time<? and l.time>? order by l.time";
		Query q = s.createQuery(hql);
		q.setString(0, qqId);

		Calendar c = new GregorianCalendar();
		c.setTime(date);
		
		c.add(Calendar.DATE, 1);
		q.setCalendar(1, c);

		Calendar yestoday = new GregorianCalendar();
		yestoday.setTimeInMillis(c.getTimeInMillis());
		yestoday.add(Calendar.DATE, -1);
		q.setCalendar(2, yestoday);

		@SuppressWarnings("unchecked")
		List<Log> logs = q.list();
		
		Vector<Log> vector = new Vector<Log>();
		int laststatus = logs.get(0).getStatus();
		int size = logs.size();
		for(int i=1; i<size; i++) {
			Log l = logs.get(i);
			
			if(l.getStatus() != laststatus ) {
				vector.add(l);
			}
			
			laststatus = l.getStatus();
		}
		textlog = vector;
		
		s.close();
		return SUCCESS;
	}
	
	public Vector<Log> getTextlog() {
		return textlog;
	}
	public void setTextlog(Vector<Log> textlog) {
		this.textlog = textlog;
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
