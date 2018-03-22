package com.appchallengers.appchallengers.dao.daoimpl;

import com.appchallengers.appchallengers.dao.dao.RelationshipDao;
import com.appchallengers.appchallengers.model.entity.Relationship;
import com.appchallengers.appchallengers.model.entity.Users;
import com.appchallengers.appchallengers.model.response.UsersBaseData;
import com.appchallengers.appchallengers.util.JpaFactory;

import javax.persistence.EntityManager;
import javax.persistence.Query;
import javax.persistence.TypedQuery;
import java.util.List;

public class RelationshipDaoImpl implements RelationshipDao {


    public void addRelationship(long firstUserId, long secondUserId, long actionId) {
        EntityManager entityManager = JpaFactory.getInstance().getEntityManager();
        entityManager.getTransaction().begin();
        entityManager.persist(new Relationship(
                entityManager.find(Users.class, firstUserId),
                entityManager.find(Users.class, secondUserId),
                actionId, Relationship.Type.SEND_REQUEST
        ));
        entityManager.getTransaction().commit();
        entityManager.close();
    }

    public void acceptRelationship(long firstUser, long secondUser, long actionId) {
        EntityManager entityManager=JpaFactory.getInstance().getEntityManager();
        entityManager.getTransaction().begin();
        Relationship relationship=getRelationship(firstUser,secondUser);
        relationship.setStatus(Relationship.Type.FRIEND);
        relationship.setUserActionId(actionId);
        entityManager.merge(relationship);
        entityManager.getTransaction().commit();
        entityManager.close();
    }

    public void deleteRelationship(long firstUser, long secondUser) {
        EntityManager entityManager=JpaFactory.getInstance().getEntityManager();
        entityManager.getTransaction().begin();
        Relationship relationship=getRelationship(firstUser,secondUser);
        entityManager.remove(entityManager.find(Relationship.class,relationship.getId()));
        entityManager.getTransaction().commit();
        entityManager.close();
    }

    public Long checkRelationship(long firstUser, long secondUser) {
        EntityManager entityManager = JpaFactory.getInstance().getEntityManager();
        entityManager.getTransaction().begin();
        TypedQuery<Long> longTypedQuery = entityManager.createNamedQuery("Relationship.checkRelationship", Long.class);
        longTypedQuery.setParameter("firstuser", firstUser);
        longTypedQuery.setParameter("seconduser", secondUser);
        return longTypedQuery.getSingleResult();
    }

    public Relationship getRelationship(long firstUser, long secondUser) {
        if (checkRelationship(firstUser, secondUser) == 0) {
            return null;
        } else {
            EntityManager entityManager = JpaFactory.getInstance().getEntityManager();
            entityManager.getTransaction().begin();
            TypedQuery<Relationship> relationshipTypedQuery = entityManager.createNamedQuery("Relationship.getRelationship", Relationship.class);
            relationshipTypedQuery.setParameter("firstuser", firstUser);
            relationshipTypedQuery.setParameter("seconduser", secondUser);
            return relationshipTypedQuery.getSingleResult();
        }
    }

    public List<UsersBaseData> getFriends(long userId) {
        EntityManager entityManager = JpaFactory.getInstance().getEntityManager();
        entityManager.getTransaction().begin();
        Query query = entityManager.createNamedQuery("Relationship.getRelationships", Users.class);
        query.setParameter(1, userId);
        query.setParameter("2", userId);
        return query.getResultList();
    }


}
