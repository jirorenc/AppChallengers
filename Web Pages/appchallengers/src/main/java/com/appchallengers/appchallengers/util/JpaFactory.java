package com.appchallengers.appchallengers.util;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

public class JpaFactory {

    public static JpaFactory mJpaFactory;
    public static final Object OBJECT=new Object();
    private EntityManagerFactory mEntityManagerFactory;

    private JpaFactory(){
        mEntityManagerFactory= Persistence.createEntityManagerFactory("AppChallengersPersistenceUnit");
    }

    public static JpaFactory getInstance() {
        if (mJpaFactory==null){
            synchronized (OBJECT){
                if (mJpaFactory==null)
                    return new JpaFactory();
            }
        }
        return mJpaFactory;
    }
    public EntityManager getEntityManager(){
        return mEntityManagerFactory.createEntityManager();
    }
}
