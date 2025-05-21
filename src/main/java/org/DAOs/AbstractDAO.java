package org.DAOs;

import org.hibernate.Session;
import org.hibernate.SessionFactory;

import org.hibernate.Transaction;
import org.sessionUtil.SessionUtil;

import java.util.ArrayList;
import java.util.List;

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
        C oldData;
        Session session = sessionFactory.openSession();
        Transaction transaction = session.beginTransaction();
        oldData = session.merge(entity);
        transaction.commit();
        session.close();
        return oldData;
    }

    public void delete(C entity) {
        sessionFactory.inTransaction(session -> session.remove(entity));
    }

    public List<C> findAll() {
        List<C> entities = new ArrayList<C>();
        sessionFactory.inTransaction(session -> {
            session.findMultiple(processedClass, entities);
        });
        return entities;
    }

}