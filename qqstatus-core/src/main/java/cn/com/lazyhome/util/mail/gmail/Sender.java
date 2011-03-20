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
 * 使用Gmail发送邮件
 */
public class Sender {

	public static final String messageContentMimeType = "text/html; charset=gb2312";

	public static final String SSL_FACTORY = "javax.net.ssl.SSLSocketFactory";

	/**
	 * 构建邮件,并发送
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
		
		// 构建邮件体
		Message msg = new MimeMessage(session);
		
		//鉴别发送者，您可以使用setFrom()和setReplyTo()方法。
		//msg.setFrom(new InternetAddress("[发件人]"));
//		msg.addFrom(InternetAddress.parse("84074663@qq.com"));//地址来源,没作用?
//		msg.setReplyTo(InternetAddress.parse("[回复时收件人]"));//回复时用的地址
		//消息接收者
		msg.setRecipients(Message.RecipientType.TO, InternetAddress.parse(
				receiver, false));
		msg.setSubject(subject);
//		msg.setSentDate(new Date());

		// 邮件内容数据（Content）
		msg.setContent(buildMimeMultipart(content, attachment));

		//Transport 是用来发送信息的
		Transport.send(msg);
	}

	/**
	 * 构建邮件的正文和附件
	 * 
	 * @param msgContent
	 * @param attachedFileList
	 * @return
	 * @throws MessagingException
	 */
	public Multipart buildMimeMultipart(String msgContent,
			Vector<String> attachedFileList) throws MessagingException {
		Multipart mPart = new MimeMultipart();// 多部分实现

		// 邮件正文
		MimeBodyPart mBodyContent = new MimeBodyPart();// MIME邮件段体
		if (msgContent != null) {
			mBodyContent.setContent(msgContent, messageContentMimeType);
		} else {
			mBodyContent.setContent("", messageContentMimeType);
		}
		mPart.addBodyPart(mBodyContent);

		// 附件
		String file;
		String fileName;
		if (attachedFileList != null) {
			for (Enumeration<String> fileList = attachedFileList.elements(); fileList
					.hasMoreElements();) {
				file = fileList.nextElement();
				fileName = file.substring(file.lastIndexOf("/") + 1);
				MimeBodyPart mBodyPart = new MimeBodyPart();
				//远程资源
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
	 * 字串解码
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
	 * 发送邮件
	 * 
	 * @throws AddressException
	 * @throws MessagingException
	 */
	public void send() throws AddressException, MessagingException {
		
		final String username = "dchrainbow@gmail.com";
		final String password = "密码";
		String to = "84074663@qq.com";
		

		String subject = "dch jmail";
		String content = "How are you!这是一个测试!";
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
		// Gmail提供的POP3和SMTP是使用安全套接字层SSL的
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