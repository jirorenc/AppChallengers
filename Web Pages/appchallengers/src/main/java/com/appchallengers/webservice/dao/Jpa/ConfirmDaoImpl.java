package com.appchallengers.webservice.dao.Jpa;

import com.appchallengers.webservice.dao.ConfirmDao;
import com.appchallengers.webservice.model.Confirm;
import com.appchallengers.webservice.util.JpaFactory;

import javax.persistence.EntityManager;

public class ConfirmDaoImpl implements ConfirmDao {

    public void saveConfirm(Confirm confirm) {
        EntityManager entityManager= JpaFactory.getInstance().getEntityManager();
        entityManager.getTransaction().begin();
        entityManager.persist(confirm);
        entityManager.getTransaction().commit();
        entityManager.close();
    }
}
