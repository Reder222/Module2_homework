package org.consoleApplication;


import jakarta.validation.Validation;
import org.application.configuration.SpringConfiguration;
import org.application.dataClasses.UserData;
import org.application.dto.UserDTOUtil;
import org.application.repositories.UserRepository;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.application.service.UserService;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;


import javax.xml.validation.Validator;
import java.util.*;
import java.util.stream.Collectors;

import static org.junit.jupiter.api.Assertions.*;

class UserServiceTest {

    static UserRepository userRepository;
    static UserService userService;

    @BeforeAll
    public static void beforeEach() {
        AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext(SpringConfiguration.class);
        UserService userService = context.getBean(UserService.class);
        userRepository = Mockito.mock(UserRepository.class);
        userService.replaceRepository(userRepository);

    }

    @Test
    public void testAddUser() {
        UserData user = new UserData("Test", "@gmail.com", 15);
        Mockito.when(userRepository.save(Mockito.any()));
        userService.create(user.getName(), user.getEmail(), user.getAge());
        Mockito.verify(userRepository, Mockito.times(1)).save(Mockito.any());
    }

    @Test
    public void testNullAndEmptyAddUser() {
        UserData user1 = new UserData(null, "@gmail.com", 15);
        UserData user2 = new UserData("Test", null, 15);
        UserData user3 = new UserData("Test", "@gmail.com", 0);

        Mockito.when(userRepository.save(Mockito.any()));

        userService.create(user1.getName(), user1.getEmail(), user1.getAge());
        userService.create(user2.getName(), user2.getEmail(), user2.getAge());
        userService.create(user3.getName(), user3.getEmail(), user3.getAge());

        Mockito.verify(userRepository, Mockito.never()).save(Mockito.any());
    }

    @Test
    public void testEqualEmailAdd() {
        UserData user = new UserData("Test", "@gmail.com", 15); //already in database
        UserData user1 = new UserData("Test", "@gmail.com", 15);
        UserData user2 = new UserData("Test", "not@gmail.com", 15);

        Mockito.when(userRepository.save(Mockito.any()));

        List<UserData> list = new ArrayList<>();
        list.add(user);

        Mockito.when(userRepository.findAllByEmail((Mockito.eq(user.getEmail())))).thenReturn(list);

        userService.create(user1.getName(), user1.getEmail(), user1.getAge());
        userService.create(user2.getName(), user2.getEmail(), user2.getAge());

        Mockito.verify(userRepository, Mockito.times(1)).save(Mockito.any());
    }

    @Test
    public void testGetAllUsers() {
        ArrayList<UserData> expected = new ArrayList<UserData>();
        expected.add(new UserData("Test", "@gmail.com", 15));

        Mockito.when(userRepository.findAll()).thenReturn(expected);

        List<UserData> actual = userService.getAll().stream().map(UserDTOUtil::dtoToData).collect(Collectors.toList());

        assertEquals(expected, actual);
    }

    @Test
    public void testGetUserById() {
        UserData expected = new UserData("Test", "@gmail.com", 15);

        Optional<UserData> optUser = Optional.of(expected);

        Mockito.when(userRepository.findById(Mockito.eq(1))).thenReturn(optUser);

        UserData actual = UserDTOUtil.dtoToData(userService.getByID(1));

        Mockito.verify(userRepository, Mockito.times(1)).save(Mockito.any());

    }

    @Test
    public void testUpdateUser() {
        UserData oldValue = new UserData("Test", "@gmail.com", 15);
        UserData newValue = new UserData("Test123", "123@gmail.com", 15);

        Mockito.when(userRepository.save(newValue)).thenReturn(oldValue);

        userService.update(UserDTOUtil.dataToDTO(newValue));

        UserData actual = UserDTOUtil.dtoToData(userService.getByID(1));

        assertEquals(oldValue, actual);
    }

    @Test
    public void testNullAndEmptyUpdateUser() {

        UserData nullNameValue = new UserData(null, "@gmail.com", 15);
        UserData nullEmailValue = new UserData("Test", null, 15);
        UserData incorrectAgeValue = new UserData("Test", "gmail.com", 0);
        UserData emptyNameValue = new UserData("", "@gmail.com", 15);
        UserData emptyEmailValue = new UserData("Test", "", 15);

        Mockito.when(userRepository.save(Mockito.any(UserData.class)));



        boolean result =userService.update(UserDTOUtil.dataToDTO(nullNameValue)).equals( "success");
         result =userService.update(UserDTOUtil.dataToDTO(nullEmailValue)).equals( "success");
         result =userService.update(UserDTOUtil.dataToDTO(incorrectAgeValue)).equals( "success");
         result =userService.update(UserDTOUtil.dataToDTO(emptyNameValue)).equals( "success");
         result =userService.update(UserDTOUtil.dataToDTO(emptyEmailValue)).equals( "success");



        assertFalse(result);
    }

    @Test
    public void testDeleteExistedUser() {
        UserData user = new UserData("Test", "@gmail.com", 15);

        Optional<UserData> optUser = Optional.of(user);

        Mockito.when(userRepository.findById(Mockito.eq(1))).thenReturn(optUser);

        Mockito.verify(userRepository, Mockito.times(1)).delete(Mockito.any());

    }

    @Test
    public void testDeleteNotExistedUser() {

        Mockito.when(userRepository.findById(Mockito.eq(1))).thenReturn(null);

        Mockito.verify(userRepository, Mockito.never()).delete(Mockito.any());

    }

}