package com.appchallengers.appchallengers.webservice.remote;


import com.appchallengers.appchallengers.helpers.util.Constants;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;


public class ApiClient {

    private static Retrofit getRetroClient() {
        return new Retrofit.Builder()
                .baseUrl(Constants.WEB_SERVİCE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
    }

    public static UserClient getUserClient() {
        return getRetroClient().create(UserClient.class);
    }

    public static AppClient getAppClient() {
        return getRetroClient().create(AppClient.class);
    }
}
