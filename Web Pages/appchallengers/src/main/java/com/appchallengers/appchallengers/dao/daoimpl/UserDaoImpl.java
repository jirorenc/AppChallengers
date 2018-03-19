package com.appchallengers.appchallengers.dao.daoimpl;

import com.appchallengers.appchallengers.dao.dao.UserDao;
import com.appchallengers.appchallengers.model.entity.Users;
import com.appchallengers.appchallengers.util.JpaFactory;
import com.appchallengers.appchallengers.util.Util;
import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import java.security.NoSuchAlgorithmException;
import java.util.Calendar;
import java.util.Locale;

public class UserDaoImpl implements UserDao {


    public Users saveUser(Users users) {
        EntityManager entityManager = JpaFactory.getInstance().getEntityManager();
        entityManager.getTransaction().begin();
        entityManager.persist(users);
        entityManager.flush();
        entityManager.getTransaction().commit();
        entityManager.close();
        return users;
    }

    public Users findByEmail(String email) {
        EntityManager entityManager = JpaFactory.getInstance().getEntityManager();
        entityManager.getTransaction().begin();
        TypedQuery<Users> usersTypedQuery = entityManager.createNamedQuery("Users.findUserByEmail", Users.class);
        usersTypedQuery.setParameter("email", email);
        Users user = usersTypedQuery.getSingleResult();
        entityManager.close();
        return user;
    }


    public Long checkEmail(String email) {
        EntityManager entityManager = JpaFactory.getInstance().getEntityManager();
        entityManager.getTransaction().begin();
        TypedQuery<Long> longTypedQuery = entityManager.createNamedQuery("Users.checkEmail", Long.class);
        longTypedQuery.setParameter("email", email);
        return longTypedQuery.getSingleResult();
    }

    public Long checkIdAndPasswordSalt(long id, String passwordSalt) {
        EntityManager entityManager = JpaFactory.getInstance().getEntityManager();
        entityManager.getTransaction().begin();
        TypedQuery<Long> longTypedQuery = entityManager.createNamedQuery("Users.checkIdAndPasswordSalt", Long.class);
        longTypedQuery.setParameter("id", id);
        longTypedQuery.setParameter("passwordSalt", passwordSalt);
        return longTypedQuery.getSingleResult();
    }

    public void confirmEmail(long id, String passwordSalt) {
        if (checkIdAndPasswordSalt(id, passwordSalt)==1) {
            EntityManager entityManager = JpaFactory.getInstance().getEntityManager();
            entityManager.getTransaction().begin();
            Users user = entityManager.find(Users.class, id);
            user.setActive(Users.Active.ACTIVE);
            try {
                user.setPasswordSalt(Util.hashMD5(user.getPasswordHash() + user.getPasswordSalt()));
            } catch (NoSuchAlgorithmException e) {
                e.printStackTrace();
            }
            entityManager.merge(user);
            entityManager.getTransaction().commit();
            entityManager.close();
        }

    }

    public void changePassword(long id,String passwordHash) {
        EntityManager entityManager = JpaFactory.getInstance().getEntityManager();
        entityManager.getTransaction().begin();
        java.sql.Timestamp currentTimestamp = new java.sql.Timestamp(Calendar.getInstance(Locale.US).getTime().getTime());
        Users user = entityManager.find(Users.class, id);
        user.setPasswordHash(passwordHash);
        user.setUpdateDate(currentTimestamp);
        try {
            user.setPasswordSalt(Util.hashMD5(user.getPasswordHash() + user.getPasswordSalt()));
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        entityManager.merge(user);
        entityManager.getTransaction().commit();
        entityManager.close();
    }

    public Users findUserById(long id) {
        EntityManager entityManager=JpaFactory.getInstance().getEntityManager();
        entityManager.getTransaction().begin();
        Users users=entityManager.find(Users.class,id);
        entityManager.close();
        return users;
    }

    public Long login(String email, String passwordHash) {
        EntityManager entityManager = JpaFactory.getInstance().getEntityManager();
        entityManager.getTransaction().begin();
        TypedQuery<Long> longTypedQuery = entityManager.createNamedQuery("Users.login", Long.class);
        longTypedQuery.setParameter("email", email);
        longTypedQuery.setParameter("passwordHash", passwordHash);
        return  longTypedQuery.getSingleResult();
    }


}
