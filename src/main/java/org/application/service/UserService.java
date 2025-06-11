package org.application.service;


import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validator;
import org.application.dataClasses.UserData;
import org.application.dto.UserDTO;
import org.application.dto.UserDTOUtil;
import org.application.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.BindingResult;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@Transactional(readOnly = true)
public class UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    Validator validator;


    @Transactional
    public String create(String name, String email, int age) {

        UserData temp = new UserData(name, email, age);

        Set<ConstraintViolation<UserData>> constraintViolations = validator.validate(temp);
        if (!constraintViolations.isEmpty()) {

                StringBuilder errors = new StringBuilder();
                for (ConstraintViolation<UserData> violation : constraintViolations) {
                    errors.append(violation.getMessage())
                    .append("\n");
                }
                return errors.toString();
        }

        userRepository.save(temp);

        return "success";
    }

    public List<UserDTO> getAll() {
        return userRepository.findAll().stream().map(UserDTOUtil::dataToDTO).collect(Collectors.toList());
    }

    public UserDTO getByID(int id) {

        UserData temp = userRepository.findById(id).orElse(null);
        return temp != null ? UserDTOUtil.dataToDTO(temp) : null;
    }

    @Transactional
    public String update(UserDTO user) {
        UserData temp = UserDTOUtil.dtoToData(user);

        Set<ConstraintViolation<UserData>> constraintViolations = validator.validate(temp);
        if (!constraintViolations.isEmpty()) {

            StringBuilder errors = new StringBuilder();
            for (ConstraintViolation<UserData> violation : constraintViolations) {
                errors.append(violation.getMessage())
                        .append("\n");
            }
            return errors.toString();
        }


        userRepository.save(temp);
        return "success";
    }

    @Transactional
    public void delete(int id) {
        userRepository.findById(id).ifPresent(temp -> userRepository.deleteById(id));
    }


}
