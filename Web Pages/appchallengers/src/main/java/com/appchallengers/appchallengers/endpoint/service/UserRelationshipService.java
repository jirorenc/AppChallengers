package com.appchallengers.appchallengers.endpoint.service;

import com.appchallengers.appchallengers.dao.dao.RelationshipDao;
import com.appchallengers.appchallengers.dao.dao.UserDao;
import com.appchallengers.appchallengers.dao.daoimpl.RelationshipDaoImpl;
import com.appchallengers.appchallengers.dao.daoimpl.UserDaoImpl;
import com.appchallengers.appchallengers.endpoint.error_handling.CommonExceptionHandler;
import com.appchallengers.appchallengers.model.entity.Users;
import com.appchallengers.appchallengers.model.response.UsersBaseData;
import com.appchallengers.appchallengers.mongodb.dao.ChallengedDao;
import com.appchallengers.appchallengers.mongodb.daoimpl.ChallengedDaoImpl;
import com.appchallengers.appchallengers.mongodb.model.ChallengedModel;
import com.appchallengers.appchallengers.util.Util;
import com.google.gson.Gson;

import javax.ws.rs.*;
import javax.ws.rs.core.Response;
import java.util.List;

@Path("/users/relationship")
public class UserRelationshipService {

    UserDao userDao=new UserDaoImpl();
    RelationshipDao relationshipDao = new RelationshipDaoImpl();
    ChallengedDao challengedDao = new ChallengedDaoImpl();

    @GET
    @Path("/example")
    @Produces("text/plain")
    public String createAccount() {
        return "hello word";
    }

    @POST
    @Path("/addfriend")
    @Produces("application/json")
    public Response addRelationship(@HeaderParam("token") String token, @FormParam("request_id") long request_id) throws CommonExceptionHandler {
        if (token == null || token.equals("")) {
            throw new CommonExceptionHandler("289");
        } else {
            long id = Util.getId(token);
            Users users=userDao.findUserById(id);
            long first = 0;
            long second = 0;
            if (id < request_id) {
                first = id;
                second = request_id;
            } else {
                first = request_id;
                second = id;
            }
            if (relationshipDao.checkRelationship(first, second) == 0) {
                relationshipDao.addRelationship(first, second, id);
                challengedDao.insert(new ChallengedModel(id,users.getFullName(),users.getProfilePicture(),request_id,1,"default",id));
                return Response.status(200).build();
            } else {
                throw new CommonExceptionHandler("290");
            }
        }
    }

    @PUT
    @Path("/accept")
    @Produces("application/json")
    public Response acceptRelationShip(@HeaderParam("token") String token, @FormParam("request_id") long request_id) throws CommonExceptionHandler {
        if (token == null || token.equals("")) {
            throw new CommonExceptionHandler("289");
        } else {
            long id = Util.getId(token);
            Users users=userDao.findUserById(id);
            long first = 0;
            long second = 0;
            if (id < request_id) {
                first = id;
                second = request_id;
            } else {
                first = request_id;
                second = id;
            }
            if (relationshipDao.acceptRelationship(first, second, id)){
                challengedDao.insert(new ChallengedModel(id,users.getFullName(),users.getProfilePicture(),request_id,2,"default",id));
            }
            return Response.status(200).build();
        }
    }

    @DELETE
    @Path("/delete")
    @Produces("application/json")
    public Response deleteRelationShip(@HeaderParam("token") String token, @FormParam("request_id") long request_id) throws CommonExceptionHandler {
        if (token == null || token.equals("")) {
            throw new CommonExceptionHandler("289");
        } else {
            long id = Util.getId(token);
            long first = 0;
            long second = 0;
            if (id < request_id) {
                first = id;
                second = request_id;
            } else {
                first = request_id;
                second = id;
            }
            relationshipDao.deleteRelationship(first, second);
            return Response.status(200).build();
        }
    }

    @GET
    @Path("/get_friend_list")
    @Produces("application/json")
    public Response getFriendList(@HeaderParam("token") String token) throws CommonExceptionHandler {
        if (token == null || token.equals("")) {
            throw new CommonExceptionHandler("289");
        } else {
            List<UsersBaseData> usersBaseData = relationshipDao.getFriends(Util.getId(token));
            return Response.status(200).entity(new Gson().toJson(usersBaseData)).build();
        }
    }

}

