package cn.com.lazyhome.qqstatus.action;

import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.struts2.interceptor.ServletRequestAware;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.Transaction;

import cn.com.lazyhome.qqstatus.bean.Concern;
import cn.com.lazyhome.qqstatus.util.HibernateUtil;

import com.opensymphony.xwork2.ActionSupport;

/**
 * 记录关注的QQ及接收信息的邮箱、时间等
 * @author Administrator
 *
 */
@SuppressWarnings("serial")
public class Record extends ActionSupport implements ServletRequestAware {
	private Concern concern;
	private HttpServletRequest request;
	private Date nowtime;
	
	public Date getNowtime() {
		return nowtime;
	}


	public void setNowtime(Date nowtime) {
		this.nowtime = nowtime;
	}


	@Override
	public String execute() throws Exception {
		Session s = HibernateUtil.getSessionFactory().openSession();
		
		String hql = "from Concern c where c.qqId = ? and c.mail=?";
		Query q = s.createQuery(hql);
		q.setString(0, concern.getQqId());
		q.setString(1, concern.getMail());
		
		@SuppressWarnings("unchecked")
		List<Concern> l = q.list();
		if(l.size() >0) {
			addActionMessage("你正在关注QQ:" + concern.getQqId() + "，无需重复提交。");
		} else {
			Transaction t = s.beginTransaction();
			concern.setCreateIp(request.getRemoteAddr());
			concern.setCreateTime(new Date());
			s.save(concern);
			t.commit();
			s.close();
			
			addActionMessage("已开始关注QQ:" + concern.getQqId());
		}
		nowtime = new Date();
		
		return SUCCESS;
	}


	public Concern getConcern() {
		return concern;
	}
	public void setConcern(Concern concern) {
		this.concern = concern;
	}

	public void setServletRequest(HttpServletRequest arg0) {
		this.request = arg0;
	}
}
