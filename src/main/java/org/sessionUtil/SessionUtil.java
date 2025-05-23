package org.sessionUtil;



import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

public class SessionUtil {
    private static SessionFactory sessionFactory;


    public SessionUtil() {

    }

    public static SessionFactory getSessionFactory() {
        if (sessionFactory == null) {
            try {
                sessionFactory = new Configuration()
                        .configure()
                        .buildSessionFactory();
            } catch (Exception e) {
                e.printStackTrace();
                sessionFactory = null;
            }

        }
        return sessionFactory;
    }

    public static void closeSessionFactory() {
        sessionFactory.close();
        sessionFactory = null;
    }


}
