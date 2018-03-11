package com.appchallengers.webservice.dao.dao;

import com.appchallengers.webservice.model.entity.ChallengeDetail;
import com.appchallengers.webservice.model.response.AddChallengeResponse;

import java.util.List;

public interface ChallengesDetailDao {

    ChallengeDetail addChallengesDetail(ChallengeDetail challengeDetail);
    List<AddChallengeResponse> getUserChallengeFeedList(Integer userId);
}
