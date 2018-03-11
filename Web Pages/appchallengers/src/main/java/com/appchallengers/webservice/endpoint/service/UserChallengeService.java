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
import com.appchallengers.webservice.model.response.AddChallengeResponse;
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
import java.util.LinkedList;
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
            Users users = null;
            try {
                users = userDao.findUserById(Util.getIdFromToken(token));
                if (users.getId() == null) {
                    throw new CommonExceptionHandler("289");
                }
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
                    headLine, currentTimestamp, uploadedFileLocation,0,0,users
            ));
            ChallengeDetail challengeDetail = challengesDetailDao.addChallengesDetail(new ChallengeDetail(
                    challenges, users, challenges.getChallenge_video_url(), currentTimestamp,0,0
            ));
            AddChallengeResponse addChallengeResponse = new AddChallengeResponse(
                    users.getId(), users.getFullName(), users.getProfilePicture(),
                    challengeDetail.getChallenge_detail().getId(),
                    challengeDetail.getChallenge_detail().getHeadLine(),
                    challengeDetail.getChallenge_url(),
                    challengeDetail.getChallenge_detail().getLikes(),
                    challengeDetail.getChallenge_detail().getDislikes(),2
            );
            return Response.status(Response.Status.OK).entity(new Gson().toJson(addChallengeResponse)).build();
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
                if (users.getId() == null) {
                    throw new CommonExceptionHandler("289");
                }
            } catch (UnsupportedEncodingException e) {
                throw new CommonExceptionHandler("289");
            } catch (MalformedJwtException exception) {
                throw new CommonExceptionHandler("289");
            } catch (SignatureException exception) {
                throw new CommonExceptionHandler("289");
            }

            List<AddChallengeResponse> userChallengeFeedList =challengesDetailDao.getUserChallengeFeedList(users.getId());
            return Response.status(Response.Status.OK).entity(new Gson().toJson(userChallengeFeedList)).build();
        }

    }
}

