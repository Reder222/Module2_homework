package org.DAOs;

import org.dataClasses.UserData;
import org.hibernate.SessionFactory;

public class UserDAO extends AbstractDAO<UserData> {

    public UserDAO() {
        super(UserData.class);
    }

    public UserDAO(SessionFactory sessionFactory) {
        super(UserData.class, sessionFactory);
    }

    public boolean containsEmail(String email) {
        return sessionFactory.fromTransaction(session -> {
            return session.find(processedClass, email);
        }) != null;
    }

}
