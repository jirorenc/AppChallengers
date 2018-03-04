package com.appchallengers.appchallengers.webservice.remote;


import com.appchallengers.appchallengers.helpers.util.Constants;
import com.jakewharton.retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;


public class ApiClientWithoutCache {


    private static Retrofit getRetroClient() {
        return new Retrofit.Builder()
                .baseUrl(Constants.WEB_SERVİCE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .build();
    }

    public static UserAccountClient getUserAccountClient() {
        return getRetroClient().create(UserAccountClient.class);
    }

    public static UserRelationshipClient getUserRelationshipClient() {
        return getRetroClient().create(UserRelationshipClient.class);
    }

    public static AppClient getAppClient() {
        return getRetroClient().create(AppClient.class);
    }
}
