package org.application.service;

import org.application.daos.UserDAO;
import org.application.dataClasses.UserData;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class UserService {


    @Autowired
    private UserDAO userDAO;


    public String create(String name, String email, int age) {
        if (name == null || name.isEmpty()) {
            return "Invalid name";
        }
        if (email == null || email.isEmpty()) {
            return "Invalid email";
        }
        if (age <= 0) {
            return "Invalid age";
        }
        if (userDAO.containsEmail(email)) {
            return "Email already exists";
        }
        UserData temp = new UserData(name, email, age);
        userDAO.create(temp);
        return "OK.";
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
        if (user.getEmail() == null || user.getEmail().isEmpty()) {
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
