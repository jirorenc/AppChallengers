package com.appchallengers.webservice.dao.daoimpl;

import com.appchallengers.webservice.dao.dao.ChallengesDao;
import com.appchallengers.webservice.model.entity.Challenges;
import com.appchallengers.webservice.util.JpaFactory;

import javax.persistence.EntityManager;

public class ChallengesDaoImpl implements ChallengesDao {

    public Challenges addChallenge(Challenges challenge) {
        EntityManager entityManager= JpaFactory.getInstance().getEntityManager();
        entityManager.getTransaction().begin();
        entityManager.persist(challenge);
        entityManager.flush();
        entityManager.getTransaction().commit();
        entityManager.close();
        return challenge;
    }
}
