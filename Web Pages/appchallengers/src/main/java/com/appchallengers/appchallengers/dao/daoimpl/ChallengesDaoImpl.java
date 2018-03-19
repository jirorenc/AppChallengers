package com.appchallengers.appchallengers.dao.daoimpl;

import com.appchallengers.appchallengers.dao.dao.ChallengesDao;
import com.appchallengers.appchallengers.model.entity.Challenges;
import com.appchallengers.appchallengers.util.JpaFactory;

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
