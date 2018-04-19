package com.appchallengers.appchallengers.mongodb.websocket;

import com.appchallengers.appchallengers.mongodb.model.ChallengedModel;
import com.google.gson.Gson;
import com.mongodb.*;

import javax.websocket.*;
import javax.websocket.OnClose;
import javax.websocket.OnMessage;
import javax.websocket.OnOpen;
import javax.websocket.Session;
import javax.websocket.server.ServerEndpoint;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

@ServerEndpoint("/websocketserver")
public class WebSocketServer {

    private Session session;
/*
    @OnOpen
    public void handleOpen(){
        System.out.println("----->>>>   �stemci bagland�....");
    }
    @OnMessage
    public String handleMessage(String message){
        System.out.println("---->>>>> GELEN MESAJJJ"+message);
        return "�al�st�";
    }
    @OnClose
    public void handleClose(){

    }
    @OnError
    public void handleError(Throwable t){
       t.printStackTrace();
    }
    */

    @OnOpen
    public void connect(Session session) {
        this.session = session;
        System.out.println("----->>>>>>>>>> baglant� sagland�");

    }

    @OnClose
    public void close() {
        this.session = null;
    }

    @OnMessage
    public void message(String userid) throws InterruptedException {
        MongoClient mongoClient = new MongoClient("localhost", 27017);
        DB db =  mongoClient.getDB("appchallengers");
        DBCollection collection=db.getCollection("notification");
        DBCursor cursor = collection.find(new BasicDBObject("toId",102));
        List<ChallengedModel> challengedModels=new LinkedList<ChallengedModel>();
        String json=null;
        while (cursor.hasNext()) {
            DBObject resultElement = cursor.next();
            Map resultElementmap = resultElement.toMap();
            resultElementmap.remove("_id");
            json=new Gson().toJson(resultElementmap);

        }
        mongoClient.close();

        String data=json.substring(1,json.length()-1);
        System.out.println(data);
        this.session.getAsyncRemote().sendText(json);


           /* for( int i=0; i<=100; i++){
                this.session.getAsyncRemote().sendText(message + " received");
                Thread.sleep(5000);
            }*/

        //return(" �al��t� amk!");
    }
}