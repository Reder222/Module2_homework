package org.example;


import org.hibernate.*;
import org.hibernate.cfg.Configuration;


public class Main {
    public static void main(String[] args) {

        try (SessionFactory sessionFactory = new Configuration().buildSessionFactory();
                Session session = sessionFactory.openSession()){

            session.getTransaction().begin();

        }
        catch (Exception exception){
            exception.printStackTrace();
        }



    }
}
