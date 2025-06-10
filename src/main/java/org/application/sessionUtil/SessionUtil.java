package org.application.sessionUtil;

import org.application.dataClasses.UserData;
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
                        .addAnnotatedClass(UserData.class)
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
