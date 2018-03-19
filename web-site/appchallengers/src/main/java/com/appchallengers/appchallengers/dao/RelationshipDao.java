package com.appchallengers.appchallengers.dao;

import com.appchallengers.appchallengers.model.Relationship;
import com.appchallengers.appchallengers.model.Users;

import java.util.List;

public interface RelationshipDao {

     void addRelationship(Integer firstUser, Integer secondUser, Integer actionId);
     Relationship getRelationship(Integer firstUser, Integer secondUser);
     List<Users> getFriends(Integer userId);
     Long checkRelationship(Integer firstUser, Integer secondUser);
}
