package org.DAOs;

import org.dataClasses.UserData;

public class UserDAO extends AbstractDAO<UserData> {

   public UserDAO() {
        super(UserData.class);
    }

}
