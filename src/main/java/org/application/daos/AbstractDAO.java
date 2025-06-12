package org.application.daos;

import jakarta.persistence.criteria.CriteriaQuery;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.query.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.*;

@Component
public abstract class AbstractDAO<C> {


    protected final SessionFactory sessionFactory;

    protected final Class<C> processedClass;


    @Autowired
    AbstractDAO(Class<C> processedClass, SessionFactory sessionFactory) {
        this.processedClass = processedClass;
        this.sessionFactory = sessionFactory;
    }


    protected Session getCurentSession() {
        return sessionFactory.getCurrentSession();
    }

    //CRUD

    public void create(C entity) {
        if (entity == null) {
            return;
        }

        try {
            sessionFactory.inTransaction(session -> {
                session.persist(entity);
            });
        } catch (Exception e) {
        }
    }


    public C update(C entity) {
        if (entity == null) {
            return null;
        }

        try {
            return sessionFactory.fromTransaction(session -> session.merge(entity));
        } catch (Exception e) {
            System.out.println("Invalid changes");
            return null;
        }


    }

    public void delete(int id) {
        sessionFactory.inTransaction(session -> session.remove(findById(id)));
    }

    public List<C> findAll() {

        return sessionFactory.fromTransaction(session -> {
            CriteriaQuery<C> cq = session.getCriteriaBuilder().createQuery(processedClass);
            cq.select(cq.from(processedClass));
            Query<C> query = session.createQuery(cq);
            return query.getResultList();
        });

    }

    public C findById(int id) {

        Object object = sessionFactory.fromTransaction(session -> {
            return session.find(processedClass, id);
        });
        return object == null ? null : processedClass.cast(object);

    }


}