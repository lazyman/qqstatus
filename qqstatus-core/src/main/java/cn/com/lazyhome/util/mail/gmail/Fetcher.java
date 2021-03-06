package cn.com.lazyhome.util.mail.gmail;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.security.Security;

import javax.mail.BodyPart;
import javax.mail.FetchProfile;
import javax.mail.Folder;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Multipart;
import javax.mail.Part;
import javax.mail.Session;
import javax.mail.Store;
import javax.mail.URLName;
import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMultipart;
import javax.mail.internet.MimeUtility;

/**
 * 使用Gmail发送邮件
 */
public class Fetcher {

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
	 * 分析邮件
	 * 
	 * @param mPart
	 */
	public static void parseMailContent(Object content) {
		try {
			if (content instanceof Multipart) {
				Multipart mPart = (MimeMultipart) content;
				for (int i = 0; i < mPart.getCount(); i++) {
					extractPart((MimeBodyPart) mPart.getBodyPart(i));
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * 抽取内容
	 * 
	 * @param part
	 */
	public static void extractPart(MimeBodyPart part) {
		try {
			String disposition = part.getDisposition();

			if (disposition != null
					&& (disposition.equalsIgnoreCase(Part.ATTACHMENT) || disposition.equalsIgnoreCase(Part.INLINE))) {// 附件
				String fileName = decodeText(part.getFileName());
				System.out.println(fileName);
				saveAttachFile(part);//保存附件
			} else {// 正文
				if(part.getContent() instanceof String){//接收到的纯文本
					System.out.println(part.getContent());
				}
				if(part.getContent() instanceof MimeMultipart){//接收的邮件有附件时
					BodyPart bodyPart = ((MimeMultipart) part.getContent()).getBodyPart(0);
					System.out.println(bodyPart.getContent());
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * 保存附件
	 * @param part
	 */
	public static void saveAttachFile(Part part){
		try{
			if(part.getDisposition()==null) return;

			String dir="D:/uploadDir/";
			String filename = decodeText(part.getFileName());
			
			InputStream in=part.getInputStream();
			OutputStream out = new FileOutputStream(new File(dir+filename));
			
			byte [] buffer=new byte[8192];
			while(in.read(buffer) != -1){
				out.write(buffer);
			}
			
			in.close();
			out.flush();
			out.close();
		}catch(Exception e){
			e.printStackTrace();
		}
	}
	
	public static void main(String[] args) {
		try {
			fetchMail();
		} catch (AddressException e) {
			e.printStackTrace();
		} catch (MessagingException e) {
			e.printStackTrace();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	/**
	 * 取邮件信息
	 * 
	 * @throws Exception
	 */
	public static void fetchMail() throws Exception {
		Security.addProvider(new com.sun.net.ssl.internal.ssl.Provider());
		Session session = Session.getDefaultInstance(Sender.getProperties(), null);
		//用pop3协议：new URLName("pop3", "pop.gmail.com", 995, null,"[邮箱帐号]", "[邮箱密码]");
		//用IMAP协议
		URLName urln = new URLName("imap", "imap.gmail.com", 995, null,
				"[邮箱帐号]", "[邮箱密码]");
		Store store = null;
		Folder inbox = null;
		try {
			//Store用来收信,Store类实现特定邮件协议上的读、写、监视、查找等操作。
			store = session.getStore(urln);
			store.connect();
			inbox = store.getFolder("INBOX");//收件箱
			inbox.open(Folder.READ_ONLY);
			FetchProfile profile = new FetchProfile();
			profile.add(FetchProfile.Item.ENVELOPE);
			Message[] messages = inbox.getMessages();
			inbox.fetch(messages, profile);
			System.out.println("收件箱的邮件数：" + messages.length);
			System.out.println("未读邮件数：" + inbox.getUnreadMessageCount());
			System.out.println("新邮件数：" + inbox.getNewMessageCount());

			for (int i = 0; i < messages.length; i++) {
				// 邮件发送者
				String from = decodeText(messages[i].getFrom()[0].toString());
				InternetAddress ia = new InternetAddress(from);
				System.out.println("FROM:" + ia.getPersonal() + '('
						+ ia.getAddress() + ')');
				// 邮件标题
				System.out.println("TITLE:" + messages[i].getSubject());
				// 邮件内容
				parseMailContent(messages[i].getContent());

				// 邮件大小
				System.out.println("SIZE:" + messages[i].getSize());
				// 邮件发送时间
				System.out.println("DATE:" + messages[i].getSentDate());
			}
		} finally {
			try {
				inbox.close(false);
			} catch (Exception e) {
			}
			try {
				store.close();
			} catch (Exception e) {
			}
		}
	}

}