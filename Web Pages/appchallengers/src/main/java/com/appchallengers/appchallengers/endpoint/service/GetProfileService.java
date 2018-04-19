package com.appchallengers.appchallengers.endpoint.service;

import com.appchallengers.appchallengers.dao.dao.GetProfileDao;
import com.appchallengers.appchallengers.dao.daoimpl.GetProfileDaoImpl;
import com.appchallengers.appchallengers.endpoint.error_handling.CommonExceptionHandler;
import com.appchallengers.appchallengers.model.response.ChallengeResponse;
import com.appchallengers.appchallengers.model.response.UsersBaseData;
import com.appchallengers.appchallengers.util.Util;
import com.google.gson.Gson;

import javax.ws.rs.*;
import javax.ws.rs.core.Response;
import java.util.List;

@Path("/users/get_user/profile")
public class GetProfileService {

    GetProfileDao getProfileDao = new GetProfileDaoImpl();

    @GET
    @Path("/info")
    @Produces("application/json")
    public Response getProfileInfo(@HeaderParam("token") String token) throws CommonExceptionHandler {
        if (token == null || token.equals("")) {
            throw new CommonExceptionHandler("289");
        } else {
            long id = Util.getId(token);
            return Response.status(Response.Status.OK).entity(new Gson().toJson(getProfileDao.getProfileInfo(id))).build();
        }
    }

    @GET
    @Path("/get_challenges")
    @Produces("application/json")
    public Response getProfileChallenges(@HeaderParam("token") String token) throws CommonExceptionHandler {
        if (token == null || token.equals("")) {
            throw new CommonExceptionHandler("289");
        } else {
            long id = Util.getId(token);
            List<ChallengeResponse> challengeResponses = getProfileDao.getProfileChallenges(id);
            return Response.status(Response.Status.OK).entity(new Gson().toJson(challengeResponses)).build();
        }
    }

    @GET
    @Path("/get_accepted_challenges")
    @Produces("application/json")
    public Response getProfileAcceptedChallenges(@HeaderParam("token") String token) throws CommonExceptionHandler {
        if (token == null || token.equals("")) {
            throw new CommonExceptionHandler("289");
        } else {
            long id = Util.getId(token);
            List<ChallengeResponse> challengeResponses = getProfileDao.getProfileAcceptedChallenges(id);
            return Response.status(Response.Status.OK).entity(new Gson().toJson(challengeResponses)).build();
        }
    }
    @GET
    @Path("/get_relationships")
    @Produces("application/json")
    public Response getProfileRelationships(@HeaderParam("token") String token) throws CommonExceptionHandler {
        if (token == null || token.equals("")) {
            throw new CommonExceptionHandler("289");
        } else {
            long id = Util.getId(token);
            List<UsersBaseData> challengeResponses = getProfileDao.getProfileRelationships(id);
            return Response.status(Response.Status.OK).entity(new Gson().toJson(challengeResponses)).build();
        }
    }
}
