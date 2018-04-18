package com.appchallengers.appchallengers.endpoint.service;

import com.appchallengers.appchallengers.dao.dao.ChallengesDao;
import com.appchallengers.appchallengers.dao.dao.ChallengesDetailDao;
import com.appchallengers.appchallengers.dao.dao.UserDao;
import com.appchallengers.appchallengers.dao.daoimpl.ChallengesDaoImpl;
import com.appchallengers.appchallengers.dao.daoimpl.ChallengesDetailDaoImpl;
import com.appchallengers.appchallengers.dao.daoimpl.UserDaoImpl;
import com.appchallengers.appchallengers.endpoint.error_handling.CommonExceptionHandler;
import com.appchallengers.appchallengers.model.entity.ChallengeDetail;
import com.appchallengers.appchallengers.model.entity.Challenges;
import com.appchallengers.appchallengers.model.entity.Users;
import com.appchallengers.appchallengers.model.response.ChallengeResponse;
import com.appchallengers.appchallengers.util.Util;
import com.google.gson.Gson;
import com.sun.jersey.core.header.FormDataContentDisposition;
import com.sun.jersey.multipart.FormDataParam;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.SignatureException;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.io.InputStream;
import java.security.NoSuchAlgorithmException;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;

@Path("/users/challenges")
public class UserChallengeService {

    ChallengesDao challengesDao = new ChallengesDaoImpl();
    UserDao userDao = new UserDaoImpl();
    ChallengesDetailDao challengesDetailDao = new ChallengesDetailDaoImpl();

    @GET
    @Path("/example")
    @Produces("text/plain")
    public String createAccount() {
        return "hello word";
    }


    @POST
    @Path("/add_challenge")
    @Consumes(MediaType.MULTIPART_FORM_DATA)
    @Produces("application/json")
    public Response addChallenge(@HeaderParam("token") String token,
                                 @FormDataParam("video") InputStream uploadedInputStream,
                                 @FormDataParam("video") FormDataContentDisposition fileDetail,
                                 @FormDataParam("headLine") String headLine) throws CommonExceptionHandler {

        if (token == null || token.equals("")) {
            throw new CommonExceptionHandler("289");
        } else {
            java.sql.Timestamp currentTimestamp = new java.sql.Timestamp(Calendar.getInstance(Locale.US).getTime().getTime());
            String uploadedFileLocation = null;
            try {
                uploadedFileLocation = "c://Users/MHMTNASIF/Desktop/uploaded/" + Util.hashMD5(currentTimestamp.toString() + token) + fileDetail.getFileName();
            } catch (NoSuchAlgorithmException e) {
                throw new CommonExceptionHandler("290");
            }
            String temp = "https://vcdn.ensonhaber.com//flv/flvideo/v/2018/02/1519182275.mp4";
            Users users = userDao.findUserById(Util.getId(token));
            if (!Util.writeToFile(uploadedInputStream, uploadedFileLocation)) {
                throw new CommonExceptionHandler("290");
            }
            Challenges challenges = challengesDao.addChallenge(new Challenges(
                    headLine, currentTimestamp, users
            ));
            ChallengeDetail challengeDetail = challengesDetailDao.addChallengesDetail(new ChallengeDetail(
                    challenges, users, temp, currentTimestamp
            ));
            ChallengeResponse challengeResponse = new ChallengeResponse(
                    users.getId(), users.getFullName(), users.getProfilePicture(),
                    challengeDetail.getChallenge().getId(),
                    challengeDetail.getId(),
                    challengeDetail.getChallenge_url(),
                    challengeDetail.getChallenge().getHeadLine(),
                    0, 0
            );
            return Response.status(Response.Status.OK).entity(new Gson().toJson(challengeResponse)).build();
        }

    }

    @GET
    @Path("/get_user_challenge_feed")
    @Produces("application/json")
    public Response getUserChallengeFeed(@HeaderParam("token") String token) throws CommonExceptionHandler {

        if (token == null || token.equals("")) {
            throw new CommonExceptionHandler("289");
        } else {
            Users users = userDao.findUserById(Util.getId(token));
            List<ChallengeResponse> userChallengeFeedList = challengesDetailDao.getUserChallengeFeedList(users.getId());
            return Response.status(Response.Status.OK).entity(new Gson().toJson(userChallengeFeedList)).build();
        }

    }
}

