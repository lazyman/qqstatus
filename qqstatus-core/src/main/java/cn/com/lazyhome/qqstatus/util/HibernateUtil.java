package cn.com.lazyhome.qqstatus.util;

import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

public class HibernateUtil {

    private static SessionFactory sessionFactory ;

    static {
        try {
            // Create the SessionFactory from hibernate.cfg.xml
            sessionFactory = new Configuration().configure("hibernate/hibernate-main.cfg.xml").buildSessionFactory();

        } catch (Throwable ex) {
            // Make sure you log the exception, as it might be swallowed
            System.err.println("Initial SessionFactory creation failed. " + ex);
            throw new ExceptionInInitializerError(ex);
        }
    }

    public static SessionFactory getSessionFactory() {
    	if(sessionFactory == null) {
    		sessionFactory = new Configuration().configure("hibernate/hibernate-main.cfg.xml").buildSessionFactory();
    	}
    	
        return sessionFactory;
    }

}
