package org;


import org.DAOs.UserDAO;
import org.dataClasses.User;

public class Main {
    public static void main(String[] args) {

        UserDAO userDAO = new UserDAO();

        User user = new User("Sonya",Math.random()+"@mail.ru",(int)(Math.random()*100));

        userDAO.create(user);
    }
}
