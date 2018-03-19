package com.appchallengers.appchallengers.dao.Jpa;


import com.appchallengers.appchallengers.dao.RelationshipDao;
import com.appchallengers.appchallengers.model.Relationship;
import com.appchallengers.appchallengers.model.Users;
import com.appchallengers.appchallengers.util.JpaFactory;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import java.util.List;

public class RelationshipDaoImpl implements RelationshipDao {


    public void addRelationship(Integer firstUserId, Integer secondUserId,Integer actionId) {
        EntityManager entityManager= JpaFactory.getInstance().getEntityManager();
        entityManager.getTransaction().begin();
        entityManager.persist(new Relationship(
                entityManager.find(Users.class,firstUserId),
                entityManager.find(Users.class,secondUserId),
                actionId,Relationship.Type.SEND_REQUEST
        ));
        entityManager.getTransaction().commit();
        entityManager.close();
    }

    public Long checkRelationship(Integer firstUser, Integer secondUser) {
        EntityManager entityManager = JpaFactory.getInstance().getEntityManager();
        entityManager.getTransaction().begin();
        TypedQuery<Long> longTypedQuery = entityManager.createNamedQuery("Relationship.checkRelationship", Long.class);
        longTypedQuery.setParameter("firstuser", firstUser);
        longTypedQuery.setParameter("seconduser", secondUser);
        return longTypedQuery.getSingleResult();
    }

    public Relationship getRelationship(Integer firstUser, Integer secondUser) {
        if (checkRelationship(firstUser,secondUser)==0){
            return null;
        }else{
            EntityManager entityManager = JpaFactory.getInstance().getEntityManager();
            entityManager.getTransaction().begin();
            TypedQuery<Relationship> relationshipTypedQuery = entityManager.createNamedQuery("Relationship.getRelationship", Relationship.class);
            relationshipTypedQuery.setParameter("firstuser", firstUser);
            relationshipTypedQuery.setParameter("seconduser", secondUser);
            return relationshipTypedQuery.getSingleResult();
        }
    }

    public List<Users> getFriends(Integer userId) {
        EntityManager entityManager = JpaFactory.getInstance().getEntityManager();
        entityManager.getTransaction().begin();
        TypedQuery<Users> usersFriendsTypedQuery = entityManager.createNamedQuery("Relationship.getRelationships", Users.class);
        usersFriendsTypedQuery.setParameter("userid", userId);
        usersFriendsTypedQuery.setParameter("param", Relationship.Type.FRIEND);
        return usersFriendsTypedQuery.getResultList();
    }


}
