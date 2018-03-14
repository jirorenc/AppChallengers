package com.appchallengers.webservice.dao.daoimpl;

import com.appchallengers.webservice.dao.dao.RelationshipDao;
import com.appchallengers.webservice.model.entity.Relationship;
import com.appchallengers.webservice.model.entity.Users;
import com.appchallengers.webservice.model.response.UsersBaseData;
import com.appchallengers.webservice.util.JpaFactory;

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
