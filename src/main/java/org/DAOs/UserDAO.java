package org.DAOs;

import org.dataClasses.UserData;

public class UserDAO extends AbstractDAO<UserData> {

   public UserDAO() {
        super(UserData.class);
    }

    public boolean containsEmail(String email) {
        return sessionFactory.fromTransaction(session -> {
            return session.find(processedClass, email);
        }) != null;
    }

}
