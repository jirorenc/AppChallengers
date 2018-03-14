package com.appchallengers.webservice.dao.dao;

import com.appchallengers.webservice.model.entity.Votes;
import com.appchallengers.webservice.model.response.UsersBaseData;

import java.util.List;

public interface VotesDao {

    void addVote(Votes votes);
    void deleteVote(Votes votes);
    Votes getVote(long challenge_detail_id,long vote_user_id);
    List<UsersBaseData> getVotes(long challenge_detail_id);
}
