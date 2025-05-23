package org.DAOs;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.sessionUtil.SessionUtil;
import java.util.*;

public abstract class AbstractDAO<C> {
    private final SessionFactory sessionFactory;
    private final Class<C> processedClass;

    AbstractDAO(Class<C> processedClass) {
        sessionFactory = SessionUtil.getSessionFactory();
        this.processedClass = processedClass;
    }

    protected Session getCurentSession() {
        return sessionFactory.getCurrentSession();
    }

    //CRUD

    public void create(C entity) {
        try {
            sessionFactory.inTransaction(session -> {
                session.persist(entity);
            });
        }
        catch (Exception e) {
            System.out.println("Can`t persist new User. Probably email is already in use.");
        }
    }

    public C update(C entity) {
        try {
            return sessionFactory.fromTransaction(session -> session.merge(entity));

        }
        catch (Exception e) {
            System.out.println("Invalid changes");
            return null;
        }
    }

    public void delete(C entity) {
        sessionFactory.inTransaction(session -> session.remove(entity));
    }

    public List<C> findAll() {

        return sessionFactory.fromTransaction(session -> {
            return session.createQuery("from " + processedClass.getName()).list();
        });
    }

    public C findById(int id) {

        Object object = sessionFactory.fromTransaction(session -> {
            return session.find(processedClass, id);
        });
        return object == null ? null : processedClass.cast(object);

    }

}