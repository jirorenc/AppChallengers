package com.appchallengers.appchallengers.dao.daoimpl;

import com.appchallengers.appchallengers.dao.dao.ChallengesDetailInfoDao;
import com.appchallengers.appchallengers.model.response.ChallengeResponse;
import com.appchallengers.appchallengers.model.response.GetChallengeDetailInfoModel;
import com.appchallengers.appchallengers.util.JpaFactory;

import javax.persistence.EntityManager;
import javax.persistence.Query;
import java.util.List;

public class ChallengesDetailInfoDaoImpl implements ChallengesDetailInfoDao {

    public GetChallengeDetailInfoModel getChallengeDetailInfo(long challengeId) {
        EntityManager entityManager = JpaFactory.getInstance().getEntityManager();
        entityManager.getTransaction().begin();
        Query namedQuery = entityManager.createNamedQuery("ChallengeDetail.getChallengeDetailInfo");
        namedQuery.setParameter(1, challengeId);
        return (GetChallengeDetailInfoModel) namedQuery.getSingleResult();
    }

    public List<ChallengeResponse> getChallengeDetailOrderByDesc(long userId, long challengeId) {
        EntityManager entityManager = JpaFactory.getInstance().getEntityManager();
        entityManager.getTransaction().begin();
        Query query = entityManager.createNamedQuery("ChallengeDetail.getChallengeDetailOrderByDesc");
        query.setParameter(1, userId);
        query.setParameter(2, userId);
        query.setParameter(3, challengeId);
        List<ChallengeResponse> getUserChallengesList = query.getResultList();
        entityManager.close();
        return getUserChallengesList;
    }
}
