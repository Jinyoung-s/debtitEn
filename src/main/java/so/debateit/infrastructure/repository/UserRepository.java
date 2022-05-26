/**
 * The UserRepository class for JPA repository
 *
 * @author  Jinyoung So
 * @version 1.0
 * @since   5/03/2022
 */
package so.debateit.infrastructure.repository;

import org.hibernate.query.Query;
import org.springframework.stereotype.Repository;
import so.debateit.common.model.User;

import javax.persistence.EntityManager;

/*
 * Hibernate repository about User
 */
@Repository
public class UserRepository extends HibernateSupport {
    public UserRepository(EntityManager entityManager) {
        super(entityManager);
    }

    public User findByUserId(String userId) {
        Query<User> query = getSession().createQuery("from User where userId = :userId", User.class);
        query.setParameter("userId", userId);
        return query.uniqueResult();
    }

}
