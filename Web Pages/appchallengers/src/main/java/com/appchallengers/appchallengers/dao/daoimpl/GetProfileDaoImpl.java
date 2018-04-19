package com.appchallengers.appchallengers.dao.daoimpl;

import com.appchallengers.appchallengers.dao.dao.GetProfileDao;
import com.appchallengers.appchallengers.dao.dao.GetUserInfoDao;
import com.appchallengers.appchallengers.model.response.ChallengeResponse;
import com.appchallengers.appchallengers.model.response.GetUserInfoResponseModel;
import com.appchallengers.appchallengers.model.response.UsersBaseData;
import com.appchallengers.appchallengers.util.JpaFactory;

import javax.persistence.EntityManager;
import javax.persistence.Query;
import java.util.List;

public class GetProfileDaoImpl implements GetProfileDao {

    public GetUserInfoResponseModel getProfileInfo(long user_id) {
        EntityManager entityManager = JpaFactory.getInstance().getEntityManager();
        entityManager.getTransaction().begin();
        Query query = entityManager.createNamedQuery("Users.getInfo");
        long first = 0;
        long second = 0;
        if (user_id < user_id) {
            first = user_id;
            second = user_id;
        } else {
            first = user_id;
            second = user_id;
        }
        query.setParameter(1, first);
        query.setParameter(2, second);
        query.setParameter(3, user_id);
        query.setParameter(4, user_id);
        query.setParameter(5, user_id);
        query.setParameter(6, user_id);
        GetUserInfoResponseModel getUserInfoResponseModel = (GetUserInfoResponseModel) query.getSingleResult();
        entityManager.close();
        return getUserInfoResponseModel;
    }

    public List<ChallengeResponse> getProfileChallenges(long user_id) {
        EntityManager entityManager = JpaFactory.getInstance().getEntityManager();
        entityManager.getTransaction().begin();
        Query query = entityManager.createNamedQuery("Challenges.getUserChallenges");
        query.setParameter(1, user_id);
        query.setParameter(2, user_id);
        query.setParameter(3, user_id);
        query.setParameter(4, user_id);
        List<ChallengeResponse> getUserChallengesList = query.getResultList();
        entityManager.close();
        return getUserChallengesList;
    }

    public List<ChallengeResponse> getProfileAcceptedChallenges(long user_id) {
        EntityManager entityManager = JpaFactory.getInstance().getEntityManager();
        entityManager.getTransaction().begin();
        Query query = entityManager.createNamedQuery("Challenges.getAcceptedChallenges");
        query.setParameter(1, user_id);
        query.setParameter(2, user_id);
        query.setParameter(3, user_id);
        query.setParameter(4, user_id);
        List<ChallengeResponse> getUserChallengesList = query.getResultList();
        entityManager.close();
        return getUserChallengesList;
    }

    public List<UsersBaseData> getProfileRelationships(long user_id) {
        EntityManager entityManager = JpaFactory.getInstance().getEntityManager();
        entityManager.getTransaction().begin();
        Query query = entityManager.createNamedQuery("Relationship.getRelationships");
        query.setParameter(1, user_id);
        query.setParameter(2, user_id);
        List<UsersBaseData> getUserChallengesList = query.getResultList();
        entityManager.close();
        return getUserChallengesList;
    }
}
