package com.appchallengers.appchallengers.dao.dao;

import com.appchallengers.appchallengers.model.response.ChallengeResponse;
import com.appchallengers.appchallengers.model.response.GetChallengeDetailInfoModel;

import java.util.List;

public interface ChallengesDetailInfoDao {

    GetChallengeDetailInfoModel getChallengeDetailInfo(long challengeId);
    List<ChallengeResponse> getChallengeDetailOrderByDesc(long userId,long challengeId);
}
