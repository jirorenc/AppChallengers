package com.appchallengers.appchallengers.mongodb.daoimpl;

import com.appchallengers.appchallengers.model.response.UsersBaseData;
import com.appchallengers.appchallengers.mongodb.factories.ChallengedFactory;
import com.appchallengers.appchallengers.mongodb.dao.ChallengedDao;
import com.google.gson.Gson;
import com.mongodb.BasicDBObject;
import com.mongodb.DBObject;
import com.mongodb.client.MongoCollection;

import java.util.List;

public class ChallengedDaoImpl implements ChallengedDao {

    public void insert(String challenge_detail_id, List<UsersBaseData> usersBaseData) {
        MongoCollection mongoCollection = ChallengedFactory.getInstance().getChallengedCollection();
        DBObject dbObject = new BasicDBObject();
        dbObject.put("challenge_detail_id", challenge_detail_id);
        dbObject.put("likes", new Gson().toJson(usersBaseData));
        mongoCollection.insertOne(dbObject);
        ChallengedFactory.getInstance().closeMongoClient();
    }
}
