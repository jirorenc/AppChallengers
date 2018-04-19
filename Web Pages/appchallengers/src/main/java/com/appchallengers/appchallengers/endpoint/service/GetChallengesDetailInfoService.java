package com.appchallengers.appchallengers.endpoint.service;

import com.appchallengers.appchallengers.dao.dao.ChallengesDetailInfoDao;
import com.appchallengers.appchallengers.dao.daoimpl.ChallengesDetailInfoDaoImpl;
import com.appchallengers.appchallengers.endpoint.error_handling.CommonExceptionHandler;
import com.appchallengers.appchallengers.util.Util;
import com.google.gson.Gson;

import javax.ws.rs.*;
import javax.ws.rs.core.Response;

@Path("/users/get_challenges_detail_info")
public class GetChallengesDetailInfoService {

    ChallengesDetailInfoDao challengesDetailInfoDao=new ChallengesDetailInfoDaoImpl();

    @POST
    @Path("/info")
    @Produces("application/json")
    public Response getUserInfo(@HeaderParam("token") String token, @FormParam("challengeId") long challengeId) throws CommonExceptionHandler {
        if (token == null || token.equals("")) {
            throw new CommonExceptionHandler("289");
        } else {
            return Response.status(Response.Status.OK).entity(new Gson().toJson(challengesDetailInfoDao.getChallengeDetailInfo(challengeId))).build();
        }
    }

    @POST
    @Path("/get_latest_challenges")
    @Produces("application/json")
    public Response getChallengeDetailOrderByDesc(@HeaderParam("token") String token, @FormParam("challengeId") long challengeId) throws CommonExceptionHandler {
        if (token == null || token.equals("")) {
            throw new CommonExceptionHandler("289");
        } else {
            long id = Util.getId(token);
            return Response.status(Response.Status.OK).entity(new Gson().toJson(challengesDetailInfoDao.getChallengeDetailOrderByDesc(id,challengeId))).build();
        }
    }
}
