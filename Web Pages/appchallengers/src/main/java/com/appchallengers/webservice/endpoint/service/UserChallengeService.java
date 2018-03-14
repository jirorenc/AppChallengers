package com.appchallengers.webservice.endpoint.service;

import com.appchallengers.webservice.dao.dao.ChallengesDao;
import com.appchallengers.webservice.dao.dao.ChallengesDetailDao;
import com.appchallengers.webservice.dao.dao.UserDao;
import com.appchallengers.webservice.dao.daoimpl.ChallengesDaoImpl;
import com.appchallengers.webservice.dao.daoimpl.ChallengesDetailDaoImpl;
import com.appchallengers.webservice.dao.daoimpl.UserDaoImpl;
import com.appchallengers.webservice.endpoint.error_handling.*;
import com.appchallengers.webservice.model.entity.ChallengeDetail;
import com.appchallengers.webservice.model.entity.Challenges;
import com.appchallengers.webservice.model.entity.Users;
import com.appchallengers.webservice.model.response.ChallengeResponse;
import com.appchallengers.webservice.util.Util;
import com.google.gson.Gson;
import com.sun.jersey.core.header.FormDataContentDisposition;
import com.sun.jersey.multipart.FormDataParam;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.SignatureException;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
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
            String temp="https://scontent-frt3-2.cdninstagram.com/vp/ca547410851e8ccd91f9b7bec3d605ec/5AA616F8/t50.2886-16/28838640_572766689788967_7697253445599203511_n.mp4";
            Users users = null;
            try {
                users = userDao.findUserById(Util.getIdFromToken(token));
            } catch (UnsupportedEncodingException e) {
                throw new CommonExceptionHandler("289");
            } catch (MalformedJwtException exception) {
                throw new CommonExceptionHandler("289");
            } catch (SignatureException exception) {
                throw new CommonExceptionHandler("289");
            }
            if (!Util.writeToFile(uploadedInputStream, uploadedFileLocation)) {
                throw new CommonExceptionHandler("290");
            }
            Challenges challenges = challengesDao.addChallenge(new Challenges(
                    headLine, currentTimestamp,users
            ));
            ChallengeDetail challengeDetail = challengesDetailDao.addChallengesDetail(new ChallengeDetail(
                    challenges, users, temp, currentTimestamp
            ));
            ChallengeResponse challengeResponse = new ChallengeResponse(
                    users.getId(), users.getFullName(), users.getProfilePicture(),
                    challengeDetail.getId(),
                    challengeDetail.getChallenge_url(),
                    challengeDetail.getChallenge().getHeadLine(),
                    0,0
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
            Users users = null;
            try {
                users = userDao.findUserById(Util.getIdFromToken(token));
            } catch (UnsupportedEncodingException e) {
                throw new CommonExceptionHandler("289");
            } catch (MalformedJwtException exception) {
                throw new CommonExceptionHandler("289");
            } catch (SignatureException exception) {
                throw new CommonExceptionHandler("289");
            }

            List<ChallengeResponse> userChallengeFeedList =challengesDetailDao.getUserChallengeFeedList(users.getId());
            return Response.status(Response.Status.OK).entity(new Gson().toJson(userChallengeFeedList)).build();
        }

    }
}

