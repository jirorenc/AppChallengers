package com.appchallengers.webservice.dao.daoimpl;

import com.appchallengers.webservice.dao.dao.ChallengesDetailDao;
import com.appchallengers.webservice.model.entity.ChallengeDetail;
import com.appchallengers.webservice.model.entity.Relationship;
import com.appchallengers.webservice.model.response.AddChallengeResponse;
import com.appchallengers.webservice.util.JpaFactory;

import javax.persistence.EntityManager;
import javax.persistence.Query;
import java.util.List;

public class ChallengesDetailDaoImpl implements ChallengesDetailDao {

    public ChallengeDetail addChallengesDetail(ChallengeDetail challengeDetail) {
        EntityManager entityManager = JpaFactory.getInstance().getEntityManager();
        entityManager.getTransaction().begin();
        entityManager.persist(challengeDetail);
        entityManager.flush();
        entityManager.getTransaction().commit();
        entityManager.close();
        return challengeDetail;
    }

    public List<AddChallengeResponse> getUserChallengeFeedList(Integer userId) {
        EntityManager entityManager = JpaFactory.getInstance().getEntityManager();
        entityManager.getTransaction().begin();
        Query namedQuery = entityManager.createNamedQuery("ChallengeDetail.getUserChallengeDetail");
        namedQuery.setParameter(1, userId);
        namedQuery.setParameter(2, userId);
        namedQuery.setParameter(3, Relationship.Type.FRIEND.ordinal());
        //challengeDetailTypedQuery.setParameter("param0", Reaction.Type.LİKE);
        //challengeDetailTypedQuery.setParameter("param1", Reaction.Type.DISLIKE);
        return namedQuery.getResultList();

    }
}
