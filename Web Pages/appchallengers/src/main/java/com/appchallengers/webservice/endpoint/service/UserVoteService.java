package com.appchallengers.webservice.endpoint.service;

import com.appchallengers.webservice.dao.dao.ChallengesDetailDao;
import com.appchallengers.webservice.dao.dao.UserDao;
import com.appchallengers.webservice.dao.dao.VotesDao;
import com.appchallengers.webservice.dao.daoimpl.ChallengesDetailDaoImpl;
import com.appchallengers.webservice.dao.daoimpl.UserDaoImpl;
import com.appchallengers.webservice.dao.daoimpl.VotesDaoImpl;
import com.appchallengers.webservice.endpoint.error_handling.CommonExceptionHandler;
import com.appchallengers.webservice.model.entity.ChallengeDetail;
import com.appchallengers.webservice.model.entity.Users;
import com.appchallengers.webservice.model.entity.Votes;
import com.appchallengers.webservice.model.response.UsersBaseData;
import com.appchallengers.webservice.util.Util;
import com.google.gson.Gson;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.SignatureException;

import javax.ws.rs.*;
import javax.ws.rs.core.Response;
import java.io.UnsupportedEncodingException;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;
@Path("/users/votes")
public class UserVoteService {
    UserDao userDao = new UserDaoImpl();
    VotesDao votesDao = new VotesDaoImpl();
    ChallengesDetailDao challengesDetailDao = new ChallengesDetailDaoImpl();

    @POST
    @Path("/vote")
    @Produces("application/json")
    public Response vote(@HeaderParam("token") String token,@FormParam("challenge_detail_id") long challenge_detail_id) throws CommonExceptionHandler {
        if (token == null || token.equals("")) {
            throw new CommonExceptionHandler("289");
        } else {
            java.sql.Timestamp currentTimestamp = new java.sql.Timestamp(Calendar.getInstance(Locale.US).getTime().getTime());
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
            Votes votes = votesDao.getVote(challenge_detail_id, users.getId());
            ChallengeDetail challengeDetail = challengesDetailDao.getChallengeDetail(challenge_detail_id);
            if (votes.getCreate_date() == null) {
                votesDao.addVote(new Votes(
                        challengeDetail, users, currentTimestamp
                ));
            }else{
                votesDao.deleteVote(votes);
            }
            return Response.status(Response.Status.OK).build();
        }
    }

    @POST
    @Path("/get_vote_list")
    @Produces("application/json")
    public Response getVoteList(@HeaderParam("token") String token,@FormParam("challenge_detail_id") long challenge_detail_id) throws CommonExceptionHandler {
        if (token == null || token.equals("")) {
            throw new CommonExceptionHandler("289");
        } else {
            List<UsersBaseData> usersBaseData=votesDao.getVotes(challenge_detail_id);
            return Response.status(Response.Status.OK).entity(new Gson().toJson(usersBaseData)).build();
        }
    }
}
