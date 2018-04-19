package com.appchallengers.appchallengers.dao.dao;

import com.appchallengers.appchallengers.model.response.ChallengeResponse;
import com.appchallengers.appchallengers.model.response.GetUserInfoResponseModel;
import com.appchallengers.appchallengers.model.response.UsersBaseData;

import java.util.List;

public interface GetProfileDao {

    GetUserInfoResponseModel getProfileInfo(long user_id);

    List<ChallengeResponse> getProfileChallenges(long user_id);

    List<ChallengeResponse> getProfileAcceptedChallenges(long user_id);

    List<UsersBaseData> getProfileRelationships(long user_id);
}
