package cn.com.lazyhome.qqstatus.action;

import java.util.Date;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.struts2.interceptor.RequestAware;
import org.apache.struts2.interceptor.ServletRequestAware;
import org.hibernate.Session;
import org.hibernate.Transaction;

import com.opensymphony.xwork2.ActionSupport;

import cn.com.lazyhome.qqstatus.bean.Concern;
import cn.com.lazyhome.qqstatus.util.HibernateUtil;

/**
 * ��¼��ע��QQ��������Ϣ�����䡢ʱ���
 * @author Administrator
 *
 */
public class Record extends ActionSupport implements ServletRequestAware {
	private Concern concern;
	private HttpServletRequest request;
	
	@Override
	public String execute() throws Exception {
		Session s = HibernateUtil.getSessionFactory().openSession();
		Transaction t = s.beginTransaction();
		concern.setCreateIp(request.getRemoteAddr());
		concern.setCreateTime(new Date());
		s.save(concern);
		t.commit();
		s.close();
		
		addActionMessage("�ѿ�ʼ��עQQ:" + concern.getQqId());
		
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
