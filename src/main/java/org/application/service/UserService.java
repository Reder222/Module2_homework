package org.application.service;


import org.application.dataClasses.UserData;
import org.application.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional(readOnly = true)
public class UserService {


    /*@Autowired
    private UserDAO userDAO;*/

    @Autowired
    private UserRepository userRepository;


    @Transactional
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
        if (false) {
            return "Email already exists";
        }
        UserData temp = new UserData(name, email, age);
        userRepository.save(temp);
        return "OK.";
    }

    public List<UserData> getAll() {
        return userRepository.findAll();
    }

    public UserData getByID(int id) {
        return userRepository.findById(id).orElse(null);
    }

    @Transactional
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

        return userRepository.save(user);
    }

    @Transactional
    public boolean delete(int id) {
        UserData temp = userRepository.findById(id).orElse(null);
        if (temp != null) {
            userRepository.deleteById(id);
            return true;
        }
        return false;
    }
}
