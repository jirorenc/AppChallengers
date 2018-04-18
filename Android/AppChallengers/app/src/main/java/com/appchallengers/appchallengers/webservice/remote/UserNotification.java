package com.appchallengers.appchallengers.webservice.remote;

import com.appchallengers.appchallengers.webservice.response.NotificationResponseModel;

import java.util.List;

import io.reactivex.Observable;
import retrofit2.Response;
import retrofit2.http.GET;

public interface UserNotification {

    @GET("users/natifications/get_user_natification")
    Observable<Response<List<NotificationResponseModel>>> getNotificationList();
}
