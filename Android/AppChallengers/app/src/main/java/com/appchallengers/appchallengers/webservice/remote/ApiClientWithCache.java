package com.appchallengers.appchallengers.webservice.remote;


import com.appchallengers.appchallengers.helpers.util.Constants;
import com.jakewharton.retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;

import java.io.IOException;

import okhttp3.Cache;
import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;


public class ApiClientWithCache {
    static int cacheSize = 10 * 1024 * 1024; // 10 MB
    static Cache cache = new Cache(Constants.contex.getCacheDir(), cacheSize);

    static OkHttpClient okHttpClient = new OkHttpClient.Builder()
            .cache(cache)
            .addNetworkInterceptor(new ResponseCacheInterceptor())
            .build();

    private static Retrofit getRetroClient() {
        return new Retrofit.Builder()
                .client(okHttpClient)
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


    private static class ResponseCacheInterceptor implements Interceptor {
        @Override
        public okhttp3.Response intercept(Interceptor.Chain chain) throws IOException {
            okhttp3.Response originalResponse = chain.proceed(chain.request());
            return originalResponse.newBuilder()
                    .header("Cache-Control", "public, max-age=" + 60)
                    .build();
        }
    }

}
