package com.appchallengers.appchallengers.dao.dao;

import com.appchallengers.appchallengers.model.entity.Votes;
import com.appchallengers.appchallengers.model.response.UsersBaseData;

import java.util.List;

public interface VotesDao {

    void addVote(Votes votes);
    void deleteVote(Votes votes);
    Votes getVote(long challenge_detail_id,long vote_user_id);
    List<UsersBaseData> getVotes(long challenge_detail_id);
}
