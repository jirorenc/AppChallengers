package com.appchallengers.appchallengers.webservice.remote;

import com.appchallengers.appchallengers.webservice.response.FriendsList;

import java.util.List;

import io.reactivex.Observable;
import retrofit2.Response;
import retrofit2.http.GET;
import retrofit2.http.Header;

/**
 * Created by MHMTNASIF on 2.03.2018.
 */

public interface UserRelationship {

    @GET("users/relationship/get_friend_list")
    Observable<Response<List<FriendsList>>> getFriendList();
}
