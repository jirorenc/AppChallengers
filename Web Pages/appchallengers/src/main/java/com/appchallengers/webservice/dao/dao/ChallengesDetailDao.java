package com.appchallengers.webservice.dao.dao;

import com.appchallengers.webservice.model.entity.ChallengeDetail;
import com.appchallengers.webservice.model.response.ChallengeResponse;

import java.util.List;

public interface ChallengesDetailDao {

    ChallengeDetail addChallengesDetail(ChallengeDetail challengeDetail);
    ChallengeDetail getChallengeDetail(long challenge_detail_id);
    List<ChallengeResponse> getUserChallengeFeedList(long userId);
}
