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
		// �������Ҫ�������ʼ�
		MailSenderInfo mailInfo = new MailSenderInfo();
		mailInfo.setMailServerHost("smtp.163.com");
		mailInfo.setMailServerPort("25");
		mailInfo.setValidate(true);
		mailInfo.setUserName("dch438@163.com");
		mailInfo.setPassword("����");// ������������
		mailInfo.setFromAddress("dch438@163.com");
		mailInfo.setToAddress("84074663@qq.com");
		mailInfo.setSubject("����������� ��http://www.guihua.org �й�����1");
		mailInfo.setContent("������������ ��http://www.guihua.org �й����� ���й�������վ==");
		// �������Ҫ�������ʼ�
		SimpleMailSender sms = new SimpleMailSender();
		sms.sendTextMail(mailInfo);// ���������ʽ
		SimpleMailSender.sendHtmlMail(mailInfo);// ����html��ʽ
	}
}