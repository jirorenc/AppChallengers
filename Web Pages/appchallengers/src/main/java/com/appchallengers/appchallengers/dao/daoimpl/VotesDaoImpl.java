package com.appchallengers.appchallengers.dao.daoimpl;

import com.appchallengers.appchallengers.dao.dao.VotesDao;
import com.appchallengers.appchallengers.model.entity.Votes;
import com.appchallengers.appchallengers.model.response.UsersBaseData;
import com.appchallengers.appchallengers.util.JpaFactory;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.Query;
import java.util.List;

public class VotesDaoImpl implements VotesDao {

    public void addVote(Votes votes) {
        EntityManager entityManager = JpaFactory.getInstance().getEntityManager();
        entityManager.getTransaction().begin();
        entityManager.persist(votes);
        entityManager.getTransaction().commit();
        entityManager.close();
    }

    public void deleteVote(Votes votes) {
        EntityManager entityManager = JpaFactory.getInstance().getEntityManager();
        entityManager.getTransaction().begin();
        entityManager.remove(entityManager.find(Votes.class,votes.getId()));
        entityManager.getTransaction().commit();
        entityManager.close();
    }

    public Votes getVote(long challenge_detail_id, long vote_user_id) {
        EntityManager entityManager = JpaFactory.getInstance().getEntityManager();
        entityManager.getTransaction().begin();
        Query query = entityManager.createNamedQuery("Vote.getVote");
        query.setParameter(1, challenge_detail_id);
        query.setParameter(2, vote_user_id);
        try {
            return (Votes) query.getSingleResult();
        }catch (NoResultException e){
            return new Votes();
        }

    }

    public List<UsersBaseData> getVotes(long challenge_detail_id) {
        EntityManager entityManager = JpaFactory.getInstance().getEntityManager();
        entityManager.getTransaction().begin();
        Query query = entityManager.createNamedQuery("Vote.getVoteList");
        query.setParameter(1, challenge_detail_id);
        return query.getResultList();
    }
}
