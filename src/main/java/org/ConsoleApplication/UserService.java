package org.ConsoleApplication;

import org.DAOs.UserDAO;
import org.dataClasses.UserData;

import java.util.List;

public class UserService {
    private UserDAO userDAO;

    public UserService() {
        userDAO = new UserDAO();
    }

    public boolean create (String name, String email, int age){
        if (name == null || email == null || age <= 0){
            return false;
        }
        if (userDAO.containsEmail(email)){
            return false;
        }
        UserData temp = new UserData(name, email, age);
        userDAO.create(temp);
        return true;
    }

    public List<UserData> getAll(){
        return userDAO.findAll();
    }

    public UserData getByID(int id){
        return userDAO.findById(id);
    }

    public UserData update (UserData user){
        return userDAO.update(user);
    }

    public boolean delete (int id){
        UserData temp = userDAO.findById(id);
        if(temp != null){
            userDAO.delete(id);
            return true;
        }
        return false;
    }
}
