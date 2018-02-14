package com.appchallengers.webservice.dao.Jpa;

import com.appchallengers.webservice.dao.UserDao;
import com.appchallengers.webservice.model.Users;
import com.appchallengers.webservice.util.JpaFactory;
import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import java.util.List;

public class UserDaoImpl implements UserDao {



    public void saveUser(Users users) {
        EntityManager entityManager = JpaFactory.getInstance().getEntityManager();
        entityManager.getTransaction().begin();
        entityManager.persist(users);
        entityManager.getTransaction().commit();
        entityManager.close();
    }

    public Users findByEmail(String email) {
        EntityManager entityManager = JpaFactory.getInstance().getEntityManager();
        entityManager.getTransaction().begin();
        TypedQuery<Users> usersTypedQuery=entityManager.createNamedQuery("Users.findUserByEmail",Users.class);
        usersTypedQuery.setParameter("email",email);
        Users user=usersTypedQuery.getSingleResult();
        entityManager.close();
        return user;
    }

    public List<Users> checkEmail(String email) {
        EntityManager entityManager = JpaFactory.getInstance().getEntityManager();
        entityManager.getTransaction().begin();
        TypedQuery<Users> usersTypedQuery=entityManager.createNamedQuery("Users.findUserByEmail",Users.class);
        usersTypedQuery.setParameter("email",email);
        List<Users> users=usersTypedQuery.getResultList();
        entityManager.close();
        return users;
    }


}
