package com.appchallengers.webservice.dao.dao;


import com.appchallengers.webservice.model.entity.Relationship;
import com.appchallengers.webservice.model.entity.Users;

import java.util.List;

public interface RelationshipDao {

     void addRelationship(Integer firstUser,Integer secondUser,Integer actionId);
     Relationship getRelationship(Integer firstUser, Integer secondUser);
     List<Users> getFriends(Integer userId);
     Long checkRelationship(Integer firstUser,Integer secondUser);
}
