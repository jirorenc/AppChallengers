package com.appchallengers.webservice;

import com.appchallengers.webservice.dao.Jpa.RelationshipDaoImpl;
import com.appchallengers.webservice.dao.RelationshipDao;
import com.appchallengers.webservice.model.Relationship;
import com.appchallengers.webservice.model.Users;
import com.appchallengers.webservice.model.request.AddFriendModel;
import com.appchallengers.webservice.model.response.CommonStatus;
import com.appchallengers.webservice.model.response.GetFriendListResponse;
import com.appchallengers.webservice.model.response.RelationshipResponse;
import com.appchallengers.webservice.util.Util;
import com.google.gson.Gson;

import javax.ws.rs.*;
import javax.ws.rs.core.Response;
import java.io.UnsupportedEncodingException;
import java.util.LinkedList;
import java.util.List;

@Path("/users/relationship")
public class UserRelationshipService {

    RelationshipDao relationshipDao = new RelationshipDaoImpl();

    @GET
    @Path("/example")
    @Produces("text/plain")
    public String createAccount() {
        return "hello word";
    }

    @POST
    @Path("/addfriend")
    @Consumes("application/json")
    @Produces("application/json")
    public Response addRelationship(@HeaderParam("token") String token, AddFriendModel addFriendModel) {
        if (token == null) {
            return Response.status(200).entity(new Gson().toJson(new CommonStatus(290))).build();
        } else {
            Integer first = 0;
            Integer second = 0;
            if (addFriendModel.getFirstId() < addFriendModel.getSecondId()) {
                first = addFriendModel.getFirstId();
                second = addFriendModel.getSecondId();
            } else {
                first = addFriendModel.getSecondId();
                second = addFriendModel.getFirstId();
            }
            if (relationshipDao.checkRelationship(first, second) == 0) {
                relationshipDao.addRelationship(first, second, addFriendModel.getFirstId());
                return Response.status(200).entity(new Gson().toJson(new CommonStatus(200))).build();
            } else {
                return Response.status(200).entity(new Gson().toJson(new CommonStatus(290))).build();
            }
        }
    }

    @POST
    @Path("/get_relotionship")
    @Consumes("application/json")
    @Produces("application/json")
    public Response getRelationship(@HeaderParam("token") String token, AddFriendModel addFriendModel) {
        if (token == null) {
            return Response.status(200).entity(new Gson().toJson(new CommonStatus(290))).build();
        } else {
            Integer first = 0;
            Integer second = 0;
            if (addFriendModel.getFirstId() < addFriendModel.getSecondId()) {
                first = addFriendModel.getFirstId();
                second = addFriendModel.getSecondId();
            } else {
                first = addFriendModel.getSecondId();
                second = addFriendModel.getFirstId();
            }
            Relationship relationship = relationshipDao.getRelationship(first, second);
            if (relationship == null) {
                return Response.status(200).entity(new Gson().toJson(new RelationshipResponse(260))).build();
            } else {
                return Response.status(200).entity(new Gson().toJson(new RelationshipResponse(
                        200, addFriendModel.getFirstId(), addFriendModel.getSecondId(),
                        relationship.getUserActionId(), relationship.getStatus().ordinal()))).build();
            }
        }
    }

    @GET
    @Path("/get_friend_list")
    @Produces("application/json")
    public Response getFriendList(@HeaderParam("token") String token) {
        if (token == null) {
            return Response.status(200).entity(new Gson().toJson(new CommonStatus(290))).build();
        } else {
            List<GetFriendListResponse> getFriendListResponses = new LinkedList<GetFriendListResponse>();
            try {
                List<Users> usersList = relationshipDao.getFriends(Util.getIdFromToken(token));
                for (Users users : usersList) {
                    GetFriendListResponse getFriendListResponse = new GetFriendListResponse();
                    getFriendListResponse.setId(users.getId());
                    getFriendListResponse.setFullName(users.getFullName());
                    getFriendListResponse.setEmail(users.getEmail());
                    getFriendListResponse.setCountry(users.getCountry());
                    getFriendListResponse.setActive(users.getActive().ordinal());
                    getFriendListResponse.setImageUrl(users.getProfilePicture());
                    getFriendListResponses.add(getFriendListResponse);
                }
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
                return Response.status(200).entity(new Gson().toJson(new CommonStatus(290))).build();
            }
            return Response.status(200).entity(new Gson().toJson(getFriendListResponses)).build();
        }
    }
}

