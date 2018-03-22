package com.appchallengers.appchallengers.dao.dao;

import com.appchallengers.appchallengers.model.response.ChallengeResponse;
import com.appchallengers.appchallengers.model.response.GetUserInfoResponseModel;
import com.appchallengers.appchallengers.model.response.UsersBaseData;

import java.util.List;

public interface GetUserInfoDao {

    GetUserInfoResponseModel getUserInfo(long user_id, long info_user_id);
    List<ChallengeResponse>  getUserChallenges(long user_id, long info_user_id);
    List<ChallengeResponse>  getUserAcceptedChallenges(long user_id, long info_user_id);
    List<UsersBaseData>  getUserRelationships(long info_user_id);
}
