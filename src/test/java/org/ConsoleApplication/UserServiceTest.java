package org.ConsoleApplication;

import org.DAOs.UserDAO;
import org.dataClasses.UserData;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;



import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import static org.junit.jupiter.api.Assertions.*;

class UserServiceTest {

    UserDAO userDAO;
    UserService userService;

    @BeforeEach
    public void beforeEach() {
        userDAO = Mockito.mock(UserDAO.class);
        userService = new UserService(userDAO);
    }

    @Test
    public void testAddUser() {
        UserData user = new UserData("Test", "@gmail.com", 15);
        Mockito.doNothing().when(userDAO).create(Mockito.any());
        userService.create(user.getName(), user.getEmail(), user.getAge());
        Mockito.verify(userDAO, Mockito.times(1)).create(Mockito.any());
    }

    @Test
    public void testNullAndEmptyAddUser() {
        UserData user1 = new UserData(null, "@gmail.com", 15);
        UserData user2 = new UserData("Test", null, 15);
        UserData user3 = new UserData("Test", "@gmail.com", 0);

        Mockito.doNothing().when(userDAO).create(Mockito.any());

        userService.create(user1.getName(), user1.getEmail(), user1.getAge());
        userService.create(user2.getName(), user2.getEmail(), user2.getAge());
        userService.create(user3.getName(), user3.getEmail(), user3.getAge());

        Mockito.verify(userDAO, Mockito.never()).create(Mockito.any());
    }

    @Test
    public void testEqualEmailAdd() {
        UserData user = new UserData("Test", "@gmail.com", 15); //already in database
        UserData user1 = new UserData("Test", "@gmail.com", 15);
        UserData user2 = new UserData("Test", "not@gmail.com", 15);

        Mockito.doNothing().when(userDAO).create(Mockito.any());

        Mockito.when(userDAO.containsEmail(Mockito.eq(user.getEmail())))
                .thenReturn(true);

        userService.create(user1.getName(), user1.getEmail(), user1.getAge());
        userService.create(user2.getName(), user2.getEmail(), user2.getAge());

        Mockito.verify(userDAO, Mockito.times(1)).create(Mockito.any());
    }

    @Test
    public void testGetAllUsers() {
        ArrayList<UserData> expected = new ArrayList<UserData>();
        expected.add(new UserData("Test", "@gmail.com", 15));

        Mockito.when(userDAO.findAll()).thenReturn(expected);

        List<UserData> actual = userService.getAll();

        assertEquals(expected, actual);
    }

    @Test
    public void testGetUserById() {
        UserData expected = new UserData("Test", "@gmail.com", 15);

        Mockito.when(userDAO.findById(Mockito.eq(1))).thenReturn(expected);

        UserData actual = userService.getByID(1);

        assertEquals(expected, actual);

    }

    @Test
    public void testUpdateUser() {
        UserData oldValue = new UserData("Test", "@gmail.com", 15);
        UserData newValue = new UserData("Test123", "123@gmail.com", 15);

        Mockito.when(userDAO.update(newValue)).thenReturn(oldValue);

        UserData actual = userService.update(newValue);

        assertEquals(oldValue, actual);
    }

    @Test
    public void testNullAndEmptyUpdateUser() {

        UserData nullNameValue = new UserData(null, "@gmail.com", 15);
        UserData nullEmailValue = new UserData("Test", null, 15);
        UserData incorrectAgeValue = new UserData("Test", "gmail.com", 0);
        UserData emptyNameValue = new UserData("", "@gmail.com", 15);
        UserData emptyEmailValue = new UserData("Test", "", 15);

        Mockito.when(userDAO.update(Mockito.any(UserData.class))).thenReturn(nullNameValue);

        boolean resultIsNull = Objects.isNull(userService.update(nullNameValue));
        resultIsNull = Objects.isNull(userService.update(nullEmailValue)) && resultIsNull;
        resultIsNull = Objects.isNull(userService.update(incorrectAgeValue)) && resultIsNull;
        resultIsNull = Objects.isNull(userService.update(emptyNameValue)) && resultIsNull;
        resultIsNull = Objects.isNull(userService.update(emptyEmailValue)) && resultIsNull;

        assertTrue(resultIsNull);
    }

    @Test
    public void testDeleteExistedUser() {
        UserData user = new UserData("Test", "@gmail.com", 15);

        Mockito.when(userDAO.findById(Mockito.eq(1))).thenReturn(user);

        assertTrue(userService.delete(1));

    }

    @Test
    public void testDeleteNotExistedUser() {

        Mockito.when(userDAO.findById(Mockito.eq(1))).thenReturn(null);

        assertFalse(userService.delete(1));

    }

}