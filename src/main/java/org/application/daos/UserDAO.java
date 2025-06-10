package org.application.daos;

import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import org.application.dataClasses.UserData;
import org.hibernate.SessionFactory;
import org.hibernate.query.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class UserDAO {

    @Autowired
    protected SessionFactory sessionFactory;

    //CRUD

    public void create(UserData entity) {
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


    public UserData update(UserData entity) {
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

    public List<UserData> findAll() {

        return sessionFactory.fromTransaction(session -> {
            CriteriaQuery<UserData> cq = session.getCriteriaBuilder().createQuery(UserData.class);
            cq.select(cq.from(UserData.class));
            Query<UserData> query = session.createQuery(cq);
            return query.getResultList();
        });

    }

    public UserData findById(int id) {

        Object object = sessionFactory.fromTransaction(session -> {
            return session.find(UserData.class, id);
        });
        return object == null ? null : UserData.class.cast(object);

    }


    public boolean containsEmail(String email) {

        return sessionFactory.fromTransaction(session ->
        {
            CriteriaBuilder builder = session.getCriteriaBuilder();
            CriteriaQuery<UserData> cq = builder.createQuery(UserData.class);
            cq.select(cq.from(UserData.class)).where(builder.equal(cq.from(UserData.class).get("email"), email));
            return !session.createQuery(cq).list().isEmpty();
        });

    }

}
