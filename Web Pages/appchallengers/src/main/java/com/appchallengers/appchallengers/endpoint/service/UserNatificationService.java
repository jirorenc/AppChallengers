package com.appchallengers.appchallengers.endpoint.service;

import com.appchallengers.appchallengers.dao.dao.UserNatifications;
import com.appchallengers.appchallengers.dao.daoimpl.UserNatificationsImpl;
import com.appchallengers.appchallengers.endpoint.error_handling.CommonExceptionHandler;
import com.appchallengers.appchallengers.mongodb.model.ChallengedModel;
import com.appchallengers.appchallengers.util.Util;
import com.google.gson.Gson;

import javax.ws.rs.GET;
import javax.ws.rs.HeaderParam;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Response;
import java.util.List;

@Path("/users/natifications")
public class UserNatificationService {

    UserNatifications userNatifications = new UserNatificationsImpl();

    @GET
    @Path("/get_user_natification")
    @Produces("application/json")
    public Response getUserNatification(@HeaderParam("token") String token) throws CommonExceptionHandler {

        if (token == null || token.equals("")) {
            throw new CommonExceptionHandler("289");
        } else {
            List<ChallengedModel> challengedModels = userNatifications.getUserNatifications(Util.getId(token));
            return Response.status(200).entity(new Gson().toJson(challengedModels)).build();
        }
    }
}
