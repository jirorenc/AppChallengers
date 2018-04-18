package com.appchallengers.appchallengers.endpoint.service;

import com.appchallengers.appchallengers.dao.dao.ChallengesDetailDao;
import com.appchallengers.appchallengers.dao.dao.UserDao;
import com.appchallengers.appchallengers.dao.dao.VotesDao;
import com.appchallengers.appchallengers.dao.daoimpl.ChallengesDetailDaoImpl;
import com.appchallengers.appchallengers.dao.daoimpl.UserDaoImpl;
import com.appchallengers.appchallengers.dao.daoimpl.VotesDaoImpl;
import com.appchallengers.appchallengers.endpoint.error_handling.CommonExceptionHandler;
import com.appchallengers.appchallengers.model.entity.ChallengeDetail;
import com.appchallengers.appchallengers.model.entity.Users;
import com.appchallengers.appchallengers.model.entity.Votes;
import com.appchallengers.appchallengers.model.response.UsersBaseData;
import com.appchallengers.appchallengers.mongodb.dao.ChallengedDao;
import com.appchallengers.appchallengers.mongodb.daoimpl.ChallengedDaoImpl;
import com.appchallengers.appchallengers.mongodb.model.ChallengedModel;
import com.appchallengers.appchallengers.util.Util;
import com.google.gson.Gson;

import javax.ws.rs.*;
import javax.ws.rs.core.Response;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;

@Path("/users/votes")
public class UserVoteService {
    UserDao userDao = new UserDaoImpl();
    VotesDao votesDao = new VotesDaoImpl();
    ChallengedDao challengedDao = new ChallengedDaoImpl();
    ChallengesDetailDao challengesDetailDao = new ChallengesDetailDaoImpl();

    @POST
    @Path("/vote")
    @Produces("application/json")
    public Response vote(@HeaderParam("token") String token, @FormParam("challenge_detail_id") long challenge_detail_id) throws CommonExceptionHandler {
        if (token == null || token.equals("")) {
            throw new CommonExceptionHandler("289");
        } else {
            java.sql.Timestamp currentTimestamp = new java.sql.Timestamp(Calendar.getInstance(Locale.US).getTime().getTime());
            Users users = userDao.findUserById(Util.getId(token));
            Votes votes = votesDao.getVote(challenge_detail_id, users.getId());
            ChallengeDetail challengeDetail = challengesDetailDao.getChallengeDetail(challenge_detail_id);
            if (votes.getCreate_date() == null) {
                votesDao.addVote(new Votes(
                        challengeDetail, users, currentTimestamp
                ));
                challengedDao.insert(new ChallengedModel(users.getId(), users.getFullName(),users.getProfilePicture(), challengeDetail.getChallenge_detail_user().getId(), 3, challengeDetail.getChallenge().getHeadLine(), users.getId()));

            } else {
                votesDao.deleteVote(votes);
            }
            return Response.status(Response.Status.OK).build();
        }
    }

    @POST
    @Path("/get_vote_list")
    @Produces("application/json")
    public Response getVoteList(@HeaderParam("token") String token, @FormParam("challenge_detail_id") long challenge_detail_id) throws CommonExceptionHandler {
        if (token == null || token.equals("")) {
            throw new CommonExceptionHandler("289");
        } else {
            List<UsersBaseData> usersBaseData = votesDao.getVotes(challenge_detail_id);
            return Response.status(Response.Status.OK).entity(new Gson().toJson(usersBaseData)).build();
        }
    }
}
