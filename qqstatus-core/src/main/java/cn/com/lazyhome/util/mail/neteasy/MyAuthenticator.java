package cn.com.lazyhome.util.mail.neteasy;

import javax.mail.Authenticator;
import javax.mail.PasswordAuthentication;


public class MyAuthenticator extends Authenticator {
	String userName = null;
	String password = null;

	public MyAuthenticator() {
	}

	public MyAuthenticator(String username, String password) {
		this.userName = username;
		this.password = password;
	}

	protected PasswordAuthentication getPasswordAuthentication() {
		return new PasswordAuthentication(userName, password);
	}

	public static void main(String[] args) {
		// 这个类主要是设置邮件
		MailSenderInfo mailInfo = new MailSenderInfo();
		mailInfo.setMailProp(MailSenderInfo.get163Prop());
		mailInfo.setUserName("dch438@163.com");
		mailInfo.setPassword("密码");// 您的邮箱密码
		mailInfo.setFromAddress("dch438@163.com");
		mailInfo.setToAddress("84074663@qq.com");
		mailInfo.setSubject("设置邮箱标题 如http://www.guihua.org 中国桂花网1");
		mailInfo.setContent("设置邮箱内容 如http://www.guihua.org 中国桂花网 是中国最大桂花网站==");
		// 这个类主要来发送邮件
		SimpleMailSender sms = new SimpleMailSender();
		sms.sendTextMail(mailInfo);// 发送文体格式
		SimpleMailSender.sendHtmlMail(mailInfo);// 发送html格式
	}
}