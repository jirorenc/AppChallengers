package com.appchallengers.webservice.endpoint.service;

import com.appchallengers.webservice.dao.daoimpl.RelationshipDaoImpl;
import com.appchallengers.webservice.dao.dao.RelationshipDao;
import com.appchallengers.webservice.endpoint.error_handling.CommonExceptionHandler;
import com.appchallengers.webservice.model.entity.Relationship;
import com.appchallengers.webservice.model.entity.Users;
import com.appchallengers.webservice.model.request.RelationshipRequestModel;
import com.appchallengers.webservice.model.response.CommonStatus;
import com.appchallengers.webservice.model.response.GetFriendListResponse;
import com.appchallengers.webservice.model.response.RelationshipResponse;
import com.appchallengers.webservice.util.Util;
import com.google.gson.Gson;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.SignatureException;

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
    public Response addRelationship(@HeaderParam("token") String token, RelationshipRequestModel relationshipRequestModel) {
        if (token == null) {
            return Response.status(200).entity(new Gson().toJson(new CommonStatus(290))).build();
        } else {
            Integer first = 0;
            Integer second = 0;
            if (relationshipRequestModel.getFirstId() < relationshipRequestModel.getSecondId()) {
                first = relationshipRequestModel.getFirstId();
                second = relationshipRequestModel.getSecondId();
            } else {
                first = relationshipRequestModel.getSecondId();
                second = relationshipRequestModel.getFirstId();
            }
            if (relationshipDao.checkRelationship(first, second) == 0) {
                relationshipDao.addRelationship(first, second, relationshipRequestModel.getFirstId());
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
    public Response getRelationship(@HeaderParam("token") String token, RelationshipRequestModel relationshipRequestModel) throws CommonExceptionHandler {
        if (token == null || token.equals("")) {
            throw new CommonExceptionHandler("289");
        } else {
            Integer first = 0;
            Integer second = 0;
            if (relationshipRequestModel.getFirstId() < relationshipRequestModel.getSecondId()) {
                first = relationshipRequestModel.getFirstId();
                second = relationshipRequestModel.getSecondId();
            } else {
                first = relationshipRequestModel.getSecondId();
                second = relationshipRequestModel.getFirstId();
            }
            Relationship relationship = relationshipDao.getRelationship(first, second);
            return Response.status(200).entity(new Gson().toJson(new RelationshipResponse(relationshipRequestModel.getFirstId(), relationshipRequestModel.getSecondId(),
                    relationship.getUserActionId(), relationship.getStatus().ordinal()))).build();
        }
    }

    @GET
    @Path("/get_friend_list")
    @Produces("application/json")
    public Response getFriendList(@HeaderParam("token") String token) throws CommonExceptionHandler {
        if (token == null || token.equals("")) {
            throw new CommonExceptionHandler("289");
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
                throw new CommonExceptionHandler("290");
            } catch (MalformedJwtException exception) {
                throw new CommonExceptionHandler("289");
            } catch (SignatureException exception) {
                throw new CommonExceptionHandler("289");
            }
            return Response.status(200).entity(new Gson().toJson(getFriendListResponses)).build();
        }
    }
}

