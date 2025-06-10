package org.daos;

import static org.junit.jupiter.api.Assertions.*;

import org.application.daos.UserDAO;
import org.application.dataClasses.UserData;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;
import org.hibernate.cfg.Environment;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;

import java.util.List;
import java.util.Properties;


@Testcontainers

class UserDAOTest {

    @Container
    static PostgreSQLContainer<?> postgreSQLContainer = new
            PostgreSQLContainer<>("postgres:17")
            .withInitScript("init.sql");

   static SessionFactory sessionFactory;
   static UserDAO userDAO;

    @BeforeAll
    public static void setUp() {

        postgreSQLContainer.start();

        Properties properties = new Properties();

        properties.put(Environment.JAKARTA_JDBC_URL, postgreSQLContainer.getJdbcUrl());
        properties.put(Environment.JAKARTA_JDBC_USER, postgreSQLContainer.getUsername());
        properties.put(Environment.JAKARTA_JDBC_PASSWORD, postgreSQLContainer.getPassword());

        properties.put(Environment.HBM2DDL_AUTO, "update");

        sessionFactory = new Configuration().setProperties(properties).addAnnotatedClass(UserData.class).buildSessionFactory();

        userDAO = new UserDAO(sessionFactory);

    }

    @Test
    public void testPostgresDBIntegration() {


        List<UserData> actual = userDAO.findAll();

        assertNotNull(actual);

        assertEquals(3, actual.size());

        assertEquals(actual.get(0), userDAO.findById(actual.get(0).getId()));
        assertEquals(actual.get(1), userDAO.findById(actual.get(1).getId()));
        assertEquals(actual.get(2), userDAO.findById(actual.get(2).getId()));

        userDAO.delete(actual.get(0).getId());
        userDAO.delete(actual.get(1).getId());
        userDAO.delete(actual.get(2).getId());

        assertTrue(userDAO.findAll().isEmpty());

        postgreSQLContainer.stop();
        sessionFactory.close();

    }


}