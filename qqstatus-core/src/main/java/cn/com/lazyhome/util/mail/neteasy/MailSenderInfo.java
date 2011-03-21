package cn.com.lazyhome.util.mail.neteasy;

import java.util.Properties;
import java.util.Vector;

public class MailSenderInfo {

	public void send() {

	}

	// 发送邮件的服务器的IP和端口
	private String mailServerHost;
	private String mailServerPort = "25";
	// 邮件发送者的地址
	private String fromAddress;
	// 邮件接收者的地址
	private String toAddress;
	// 登陆邮件发送服务器的用户名和密码
	private String userName;
	private String password;
	// 是否需要身份验证
	private boolean validate = true;
	// 邮件主题
	private String subject;
	// 邮件的文本内容
	private String content;
	// 邮件附件的文件名
	private Vector<String> attachFileNames;
	
	private Properties mailProp;
	private static Properties neteaseProp;
	private static Properties GmailProp;
	public static final String SSL_FACTORY = "javax.net.ssl.SSLSocketFactory";

	/** */
	/**
	 * 获得邮件会话属性
	 */
	public Properties getMailProp() {
		if (mailProp == null) {
			mailProp = new Properties();
			mailProp.put("mail.smtp.host", this.mailServerHost);
			mailProp.put("mail.smtp.port", this.mailServerPort);
			mailProp.put("mail.smtp.auth", validate ? "true" : "false");
		}
		return mailProp;
	}
	
	public static Properties get163Prop() {
		if(neteaseProp == null) {
			neteaseProp = new Properties();
			neteaseProp.put("mail.smtp.host", "smtp.163.com");
			neteaseProp.put("mail.smtp.port", 25);
			neteaseProp.put("mail.smtp.auth", "true" );
		}
		
		return neteaseProp;
	}
	public static Properties getGmailProp() {
		if(GmailProp == null) {
			
			GmailProp = new Properties();
			GmailProp.setProperty("mail.smtp.host", "smtp.gmail.com");
			// Gmail提供的POP3和SMTP是使用安全套接字层SSL的
			GmailProp.setProperty("mail.smtp.socketFactory.class", SSL_FACTORY);
			GmailProp.setProperty("mail.smtp.socketFactory.fallback", "false");
			GmailProp.setProperty("mail.smtp.port", "465");
			GmailProp.setProperty("mail.smtp.socketFactory.port", "465");
	
			GmailProp.setProperty("mail.imap.socketFactory.class", SSL_FACTORY);
			GmailProp.setProperty("mail.imap.socketFactory.fallback", "false");
			GmailProp.setProperty("mail.imap.port", "993");
			GmailProp.setProperty("mail.imap.socketFactory.port", "993");
	
			GmailProp.setProperty("mail.pop3.socketFactory.class", SSL_FACTORY);
			GmailProp.setProperty("mail.pop3.socketFactory.fallback", "false");
			GmailProp.setProperty("mail.pop3.port", "995");
			GmailProp.setProperty("mail.pop3.socketFactory.port", "995");
	
			GmailProp.put("mail.smtp.auth", "true");
		}
		return GmailProp;
	}

	public void setMailProp(Properties mailProp) {
		this.mailProp = mailProp;
	}

	public String getMailServerHost() {
		return mailServerHost;
	}

	public void setMailServerHost(String mailServerHost) {
		this.mailServerHost = mailServerHost;
	}

	public String getMailServerPort() {
		return mailServerPort;
	}

	public void setMailServerPort(String mailServerPort) {
		this.mailServerPort = mailServerPort;
	}

	public boolean isValidate() {
		return validate;
	}

	public void setValidate(boolean validate) {
		this.validate = validate;
	}

	public Vector<String> getAttachFileNames() {
		return attachFileNames;
	}

	public void setAttachFileNames(Vector<String> fileNames) {
		this.attachFileNames = fileNames;
	}

	public String getFromAddress() {
		return fromAddress;
	}

	public void setFromAddress(String fromAddress) {
		this.fromAddress = fromAddress;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getToAddress() {
		return toAddress;
	}

	public void setToAddress(String toAddress) {
		this.toAddress = toAddress;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getSubject() {
		return subject;
	}

	public void setSubject(String subject) {
		this.subject = subject;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String textContent) {
		this.content = textContent;
	}
}
