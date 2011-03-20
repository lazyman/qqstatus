package cn.com.lazyhome.util.mail.gmail;

import java.io.UnsupportedEncodingException;
import java.security.Security;
import java.util.Enumeration;
import java.util.Properties;
import java.util.Vector;

import javax.activation.DataHandler;
import javax.activation.FileDataSource;
import javax.mail.Authenticator;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Multipart;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;
import javax.mail.internet.MimeUtility;

/**
 * ʹ��Gmail�����ʼ�
 */
public class Sender {

	public static final String messageContentMimeType = "text/html; charset=gb2312";

	public static final String SSL_FACTORY = "javax.net.ssl.SSLSocketFactory";

	/**
	 * �����ʼ�,������
	 * @param username
	 * @param password
	 * @param receiver
	 * @param subject
	 * @param content
	 * @param attachment
	 * @throws AddressException
	 * @throws MessagingException
	 */
	public void sendMail(final String username, final String password, String receiver, String subject, String content,Vector<String> attachment) throws AddressException,
			MessagingException {
		Security.addProvider(new com.sun.net.ssl.internal.ssl.Provider());

		Session session = Session.getDefaultInstance(getProperties(),
				new Authenticator() {
					protected PasswordAuthentication getPasswordAuthentication() {
						return new PasswordAuthentication(username, password);
					}
				});
		
		// �����ʼ���
		Message msg = new MimeMessage(session);
		
		//�������ߣ�������ʹ��setFrom()��setReplyTo()������
		//msg.setFrom(new InternetAddress("[������]"));
//		msg.addFrom(InternetAddress.parse("84074663@qq.com"));//��ַ��Դ,û����?
//		msg.setReplyTo(InternetAddress.parse("[�ظ�ʱ�ռ���]"));//�ظ�ʱ�õĵ�ַ
		//��Ϣ������
		msg.setRecipients(Message.RecipientType.TO, InternetAddress.parse(
				receiver, false));
		msg.setSubject(subject);
//		msg.setSentDate(new Date());

		// �ʼ��������ݣ�Content��
		msg.setContent(buildMimeMultipart(content, attachment));

		//Transport ������������Ϣ��
		Transport.send(msg);
	}

	/**
	 * �����ʼ������ĺ͸���
	 * 
	 * @param msgContent
	 * @param attachedFileList
	 * @return
	 * @throws MessagingException
	 */
	public Multipart buildMimeMultipart(String msgContent,
			Vector<String> attachedFileList) throws MessagingException {
		Multipart mPart = new MimeMultipart();// �ಿ��ʵ��

		// �ʼ�����
		MimeBodyPart mBodyContent = new MimeBodyPart();// MIME�ʼ�����
		if (msgContent != null) {
			mBodyContent.setContent(msgContent, messageContentMimeType);
		} else {
			mBodyContent.setContent("", messageContentMimeType);
		}
		mPart.addBodyPart(mBodyContent);

		// ����
		String file;
		String fileName;
		if (attachedFileList != null) {
			for (Enumeration<String> fileList = attachedFileList.elements(); fileList
					.hasMoreElements();) {
				file = fileList.nextElement();
				fileName = file.substring(file.lastIndexOf("/") + 1);
				MimeBodyPart mBodyPart = new MimeBodyPart();
				//Զ����Դ
				//URLDataSource uds=new URLDataSource(http://www.javaeye.com/logo.gif);
				FileDataSource fds = new FileDataSource(file);
				mBodyPart.setDataHandler(new DataHandler(fds));
				mBodyPart.setFileName(fileName);
				mPart.addBodyPart(mBodyPart);
			}
		}

		return mPart;
	}

	/**
	 * �ִ�����
	 * 
	 * @param text
	 * @return
	 * @throws UnsupportedEncodingException
	 */
	protected static String decodeText(String text)
			throws UnsupportedEncodingException {
		if (text == null)
			return null;
		if (text.startsWith("=?GB") || text.startsWith("=?gb")) {
			text = MimeUtility.decodeText(text);
		} else {
			text = new String(text.getBytes("ISO8859_1"));
		}
		return text;
	}

	/**
	 * �����ʼ�
	 * 
	 * @throws AddressException
	 * @throws MessagingException
	 */
	public void send() throws AddressException, MessagingException {
		
		final String username = "dchrainbow@gmail.com";
		final String password = "����";
		String to = "84074663@qq.com";
		

		String subject = "dch jmail";
		String content = "How are you!����һ������!";
		String file1 = "d:/84074663-2011-03-12.png";
		Vector<String> fileset = null;
		//fileset = new Vector<String>();
		//fileset.add(file1);
		
		sendMail(username, password, to, subject, content, fileset);

		System.out.println("Message send...");

	}
	
	public static void main(String[] args) {
		try {
			new Sender().send();
		} catch (AddressException e) {
			e.printStackTrace();
		} catch (MessagingException e) {
			e.printStackTrace();
		}
	}


	public static Properties getProperties() {
		Properties props = System.getProperties();
		props.setProperty("mail.smtp.host", "smtp.gmail.com");
		// Gmail�ṩ��POP3��SMTP��ʹ�ð�ȫ�׽��ֲ�SSL��
		props.setProperty("mail.smtp.socketFactory.class", SSL_FACTORY);
		props.setProperty("mail.smtp.socketFactory.fallback", "false");
		props.setProperty("mail.smtp.port", "465");
		props.setProperty("mail.smtp.socketFactory.port", "465");

		props.setProperty("mail.imap.socketFactory.class", SSL_FACTORY);
		props.setProperty("mail.imap.socketFactory.fallback", "false");
		props.setProperty("mail.imap.port", "993");
		props.setProperty("mail.imap.socketFactory.port", "993");

		props.setProperty("mail.pop3.socketFactory.class", SSL_FACTORY);
		props.setProperty("mail.pop3.socketFactory.fallback", "false");
		props.setProperty("mail.pop3.port", "995");
		props.setProperty("mail.pop3.socketFactory.port", "995");

		props.put("mail.smtp.auth", "true");
		return props;
	}

}