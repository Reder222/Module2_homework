package org.DAOs;

import org.hibernate.Session;
import org.hibernate.SessionFactory;

import org.hibernate.Transaction;
import org.sessionUtil.SessionUtil;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

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
        sessionFactory.inTransaction(session -> {
            session.persist(entity);
        });
    }

    public C update(C entity) {
        return sessionFactory.fromTransaction(session -> session.merge(entity));
    }

    public void delete(C entity) {
        sessionFactory.inTransaction(session -> session.remove(entity));
    }

    public List<C> findAll() {

        return  sessionFactory.openSession().createQuery("from "+processedClass.getName()).list();


    }

}