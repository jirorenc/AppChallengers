package com.appchallengers.appchallengers.mongodb.factories;


import com.mongodb.MongoClient;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import org.bson.Document;

public class ChallengedFactory {

    public static ChallengedFactory challengedFactory;
    public static Object object = new Object();
    MongoClient mongoClient = new MongoClient("localhost", 27017);

    private ChallengedFactory() {
    }

    public static ChallengedFactory getInstance() {
        if (challengedFactory == null) {
            synchronized (object) {
                if (challengedFactory == null) {
                    return challengedFactory = new ChallengedFactory();
                }
            }
        }
        return challengedFactory;
    }

    public MongoCollection<Document> getChallengedCollection() {
        MongoDatabase db = mongoClient.getDatabase("appchallengers");
        return db.getCollection("challenged");
    }

    public void closeMongoClient(){
        mongoClient.close();
    }
}
