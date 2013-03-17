package cn.com.lazyhome.qqstatus.bean;

// Generated 2011-3-14 22:03:55 by Hibernate Tools 3.3.0.GA

import java.util.Date;

/**
 * Concern generated by hbm2java
 */
@SuppressWarnings("serial")
public class Concern implements java.io.Serializable {

	/**
	 * 主键
	 */
	private int id;
	/**
	 * QQ号
	 */
	private String qqId;
	/**
	 * 昵称、网名、外号
	 */
	private String nick;
	/**
	 * 真实姓名
	 */
	private String realname;
	/**
	 * 备注
	 */
	private String remark;
	/**
	 * 延迟什么？
	 */
	private Integer delay;
	/**
	 * 接收每天报送上线情况的邮箱
	 */
	private String mail;
	/**
	 * 接收报送情况的时间
	 */
	private Integer receivedTime;
	/**
	 * 标志是否跟踪在线状态
	 */
	private String trace;
	/**
	 * 跟踪时的报送邮箱
	 */
	private String traceMail;
	/**
	 * 创建时间
	 */
	private Date createTime;
	/**
	 * 创建人IP
	 */
	private String createIp;

	public Concern() {
	}

	public Concern(int id, String qqId) {
		this.id = id;
		this.qqId = qqId;
	}

	public Concern(int id, String qqId, Integer delay, String mail,
			Integer receivedTime, Date createTime, String createIp) {
		this.id = id;
		this.qqId = qqId;
		this.delay = delay;
		this.mail = mail;
		this.receivedTime = receivedTime;
		this.createTime = createTime;
		this.createIp = createIp;
	}

	public int getId() {
		return this.id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getQqId() {
		return this.qqId;
	}

	public void setQqId(String qqId) {
		this.qqId = qqId;
	}

	public Integer getDelay() {
		return this.delay;
	}

	public void setDelay(Integer delay) {
		this.delay = delay;
	}

	public String getMail() {
		return this.mail;
	}

	public void setMail(String mail) {
		this.mail = mail;
	}

	public Integer getReceivedTime() {
		return this.receivedTime;
	}

	public void setReceivedTime(Integer receivedTime) {
		this.receivedTime = receivedTime;
	}

	public Date getCreateTime() {
		return this.createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	public String getCreateIp() {
		return this.createIp;
	}

	public void setCreateIp(String createIp) {
		this.createIp = createIp;
	}

	public String getTrace() {
		return trace;
	}

	public void setTrace(String trace) {
		this.trace = trace;
	}

	public String getTraceMail() {
		return traceMail;
	}

	public void setTraceMail(String traceMail) {
		this.traceMail = traceMail;
	}

	public String getNick() {
		return nick;
	}

	public void setNick(String nick) {
		this.nick = nick;
	}

	public String getRealname() {
		return realname;
	}

	public void setRealname(String realname) {
		this.realname = realname;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

}
