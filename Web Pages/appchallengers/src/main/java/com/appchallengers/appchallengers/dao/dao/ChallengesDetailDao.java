package com.appchallengers.appchallengers.dao.dao;

import com.appchallengers.appchallengers.model.entity.ChallengeDetail;
import com.appchallengers.appchallengers.model.response.ChallengeResponse;

import java.util.List;

public interface ChallengesDetailDao {

    ChallengeDetail addChallengesDetail(ChallengeDetail challengeDetail);
    ChallengeDetail getChallengeDetail(long challenge_detail_id);
    List<ChallengeResponse> getUserChallengeFeedList(long userId);
}
