package cn.com.lazyhome.qqstatus.test;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.sql.Blob;
import java.sql.SQLException;
import java.util.List;

import org.hibernate.Query;
import org.hibernate.Session;

import cn.com.lazyhome.qqstatus.bean.Log;
import cn.com.lazyhome.qqstatus.util.HibernateUtil;

public class TBlob {
	public void blob() {
		Session s = HibernateUtil.getSessionFactory().openSession();
		String hql = "from Log";
		Query q = s.createQuery(hql);
		
		List<Log> logs = q.list();
		
		
		for(int i = 0; i < logs.size(); i++) {
			Log log = logs.get(i);
			Blob file = log.getFile();
			if(file != null) {
			try {
				InputStream is = file.getBinaryStream();
				File f = new File("d:/" + log.getId() + "." + log.getQqId() + ".gif");
				
				FileOutputStream fos = new FileOutputStream(f);
				byte[] buf = new byte[4096];
				int len = is.read(buf);
				fos.write(buf, 0, len);
				
				fos.close();
				is.close();
			} catch (SQLException e) {
				e.printStackTrace();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			}
		}
	}
	
	public static void main(String[] args) {
		TBlob test = new TBlob();
		test.blob();
	}

}
