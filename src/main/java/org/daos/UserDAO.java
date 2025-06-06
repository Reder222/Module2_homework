package org.daos;

import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import org.dataClasses.UserData;
import org.hibernate.SessionFactory;

public class UserDAO extends AbstractDAO<UserData> {

    public UserDAO() {
        super(UserData.class);
    }

    public UserDAO(SessionFactory sessionFactory) {
        super(UserData.class, sessionFactory);
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
