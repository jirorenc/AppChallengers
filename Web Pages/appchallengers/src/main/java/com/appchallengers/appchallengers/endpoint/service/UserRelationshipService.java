package com.appchallengers.appchallengers.endpoint.service;

import com.appchallengers.appchallengers.dao.dao.RelationshipDao;
import com.appchallengers.appchallengers.dao.daoimpl.RelationshipDaoImpl;
import com.appchallengers.appchallengers.endpoint.error_handling.CommonExceptionHandler;
import com.appchallengers.appchallengers.model.entity.Relationship;
import com.appchallengers.appchallengers.model.request.RelationshipRequestModel;
import com.appchallengers.appchallengers.model.response.RelationshipResponse;
import com.appchallengers.appchallengers.model.response.UsersBaseData;
import com.appchallengers.appchallengers.util.Util;
import com.google.gson.Gson;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.SignatureException;

import javax.ws.rs.*;
import javax.ws.rs.core.Response;
import java.io.UnsupportedEncodingException;
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
    public Response addRelationship(@HeaderParam("token") String token, RelationshipRequestModel relationshipRequestModel) throws CommonExceptionHandler {
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
            if (relationshipDao.checkRelationship(first, second) == 0) {
                relationshipDao.addRelationship(first, second, relationshipRequestModel.getFirstId());
                return Response.status(200).build();
            } else {
                throw new CommonExceptionHandler("290");
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
            //Todo burası güncellenecek
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
            try {
                List<UsersBaseData> usersBaseData = relationshipDao.getFriends(Util.getIdFromToken(token));
                return Response.status(200).entity(new Gson().toJson(usersBaseData)).build();
            } catch (UnsupportedEncodingException e) {
                throw new CommonExceptionHandler("290");
            } catch (MalformedJwtException exception) {
                throw new CommonExceptionHandler("289");
            } catch (SignatureException exception) {
                throw new CommonExceptionHandler("289");
            }
        }
    }
}

