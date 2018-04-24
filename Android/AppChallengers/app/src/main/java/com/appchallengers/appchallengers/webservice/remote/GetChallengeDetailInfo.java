package com.appchallengers.appchallengers.webservice.remote;

import com.appchallengers.appchallengers.webservice.response.GetChallengeDetailResponseModel;
import com.appchallengers.appchallengers.webservice.response.UserChallengeFeedListModel;

import java.util.List;

import io.reactivex.Observable;
import retrofit2.Response;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;

/**
 * Created by MHMTNASIF on 19.03.2018.
 */

public interface GetChallengeDetailInfo {

    @FormUrlEncoded
    @POST("users/challenges_detail/info")
    Observable<Response<GetChallengeDetailResponseModel>> getChallengeDetailInfo(@Field("challengeId") long get_challenge_id);

    @FormUrlEncoded
    @POST("users/challenges_detail/get_latest_challenges")
    Observable<Response<List<UserChallengeFeedListModel>>> getLatestChallengeDetail(@Field("challengeId") long get_challenge_id);

    @FormUrlEncoded
    @POST("users/challenges_detail/get_popular_challenges")
    Observable<Response<List<UserChallengeFeedListModel>>> getPopularChallengeDetail(@Field("challengeId") long get_challenge_id);

}
