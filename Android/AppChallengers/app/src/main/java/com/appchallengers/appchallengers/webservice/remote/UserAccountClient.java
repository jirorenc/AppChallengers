package com.appchallengers.appchallengers.webservice.remote;




import com.appchallengers.appchallengers.webservice.request.SignUpRequestModel;
import com.appchallengers.appchallengers.webservice.response.UserPreferencesData;
import com.appchallengers.appchallengers.webservice.request.UsersLoginRequestModel;

import io.reactivex.Observable;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Response;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;


public interface UserAccountClient {

    @Multipart
    @POST("users/account/create/with_image")
    Observable<Response<UserPreferencesData>> createAccountWithImage(
            @Part("fullName") RequestBody fullName,
            @Part("email") RequestBody email,
            @Part("password") RequestBody password,
            @Part("country") RequestBody country,
            @Part MultipartBody.Part image);



    @POST("users/account/create/without_image")
    Observable<Response<UserPreferencesData>> createAccountWithoutImage(@Body SignUpRequestModel signUpRequestModel);


    @POST("users/account/login")
    Observable<Response<UserPreferencesData>> usersLogin(@Body UsersLoginRequestModel usersLoginRequestModel);


    @GET("users/account/send_email")
    Observable<Response<UserPreferencesData>> userResendConfirmEmail(@Header("token") String token);


    @GET("users/account/check_confirm_email")
    Observable<Response<UserPreferencesData>> checkConfrimEmail(@Header("token") String token);


}
