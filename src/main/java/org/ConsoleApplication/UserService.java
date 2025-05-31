package org.ConsoleApplication;

import org.DAOs.UserDAO;
import org.dataClasses.UserData;

import java.util.List;

public class UserService {
    private final UserDAO userDAO;

    public UserService() {
        userDAO = new UserDAO();
    }

    public UserService(UserDAO userDAO) {
        this.userDAO = userDAO;
    }

    public boolean create(String name, String email, int age) {
        if (name == null || name.isEmpty()) {
            return false;
        }
        if (email == null || email.isEmpty()) {
            return false;
        }
        if (age <= 0) {
            return false;
        }
        if (userDAO.containsEmail(email)) {
            return false;
        }
        UserData temp = new UserData(name, email, age);
        userDAO.create(temp);
        return true;
    }

    public List<UserData> getAll() {
        return userDAO.findAll();
    }

    public UserData getByID(int id) {
        return userDAO.findById(id);
    }

    public UserData update(UserData user) {
        if (user.getName() == null || user.getName().isEmpty()) {
            return null;
        }
        if(user.getEmail() == null || user.getEmail().isEmpty()) {
            return null;
        }
        if (user.getAge() <= 0) {
            return null;
        }

        return userDAO.update(user);
    }

    public boolean delete(int id) {
        UserData temp = userDAO.findById(id);
        if (temp != null) {
            userDAO.delete(id);
            return true;
        }
        return false;
    }
}
