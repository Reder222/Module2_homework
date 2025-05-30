package org.DAOs;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.sessionUtil.SessionUtil;

import java.util.*;

public abstract class AbstractDAO<C> {
    protected final SessionFactory sessionFactory;
    protected final Class<C> processedClass;

    AbstractDAO(Class<C> processedClass) {
        sessionFactory = SessionUtil.getSessionFactory();
        this.processedClass = processedClass;
    }

    protected Session getCurentSession() {
        return sessionFactory.getCurrentSession();
    }

    //CRUD

    public void create(C entity) {
        if (entity == null) {
            return ;
        }
        if (entity.getClass().isInstance(processedClass)) {
            try {
                sessionFactory.inTransaction(session -> {
                    session.persist(entity);
                });
            } catch (Exception e) {
            }
        }

    }

    public C update(C entity) {
        if (entity == null) {
            return null;
        }
        if (entity.getClass().isInstance(processedClass)) {
            try {
                return sessionFactory.fromTransaction(session -> session.merge(entity));
            } catch (Exception e) {
                System.out.println("Invalid changes");
                return null;
            }
        }
        return null;
    }

    public void delete(int id) {
        sessionFactory.inTransaction(session -> session.remove(findById(id)));
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