package cn.com.lazyhome.qqstatus.util;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.sql.Blob;
import java.util.Date;

import org.apache.commons.logging.LogFactory;
import org.hibernate.lob.BlobImpl;

import cn.com.lazyhome.qqstatus.bean.Log;

/**
 * 检查QQ状态
 * @author Administrator
 *
 */
public class CheckStatus {
	private static org.apache.commons.logging.Log logger = LogFactory.getLog(CheckStatus.class);

	public static String URL_BEGIN = "http://wpa.qq.com/pa?p=2:";
	public static String URL_END = ":41";
	public static long IMAGE_SIZE;
	
//	private long onlength = 1243;
//	private long offlength = 1252;

	/**
	 * 根据QQ号查完状态返回封装好的记录
	 * @throws IOException 
	 */
	public Log checking(String qqno) throws IOException {
		String url_s = URL_BEGIN + qqno + URL_END;

		URL url = new URL(url_s);
		// 打开连接
		HttpURLConnection httpConnection = (HttpURLConnection) url
				.openConnection();

		InputStream input = httpConnection.getInputStream();
		byte[] b = new byte[1024];
		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		// 读取网络文件,写入指定的文件中
		int nRead;
		while ((nRead = input.read(b)) > 0 ) {
			baos.write(b, 0, nRead);
		}
		httpConnection.disconnect();
		

		// 获得文件长度
		long nEndPos =getFileSize(url_s);
		logger.debug("end pos:" + String.valueOf(nEndPos));
		int status = 0;
		status = IMAGE_SIZE == nEndPos?1:0;
		logger.debug("status:" + String .valueOf(status));
		
		Blob file = new BlobImpl(baos.toByteArray());
		Log log = new Log();
		log.setQqId(qqno);
		log.setTime(new Date());
		log.setStatus(status);
		log.setFileSize((int)nEndPos);
		log.setFile(file);
		
		
		return log;
	}

	// 获得文件长度
	public static long getFileSize(String sURL) {
		int nFileLength = -1;
		try {
			URL url = new URL(sURL);
			HttpURLConnection httpConnection = (HttpURLConnection) url.openConnection();
			httpConnection.setRequestProperty("User-Agent", "Internet Explorer");

			int responseCode = httpConnection.getResponseCode();
			if (responseCode >= 400) {
				logger.debug("Error Code : " + responseCode);
				
				return -2; // -2 represent access is error
			}
			String sHeader;
			for (int i = 1;; i++) {
				sHeader = httpConnection.getHeaderFieldKey(i);
				if (sHeader != null) {
					if (sHeader.equals("Content-Length")) {
						nFileLength = Integer.parseInt(httpConnection
								.getHeaderField(sHeader));
						break;
					}
				} else
					break;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		logger.debug("file size:" + nFileLength);
		
		return nFileLength;
	}

	public static void main(String[] args) {
		String qqno = "109719189";
		
		CheckStatus check = new CheckStatus();
		try {
			check.checking(qqno);
			qqno = "84074663";
			check.checking(qqno);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
