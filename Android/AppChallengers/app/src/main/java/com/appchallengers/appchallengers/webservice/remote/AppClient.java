package com.appchallengers.appchallengers.webservice.remote;

import com.appchallengers.appchallengers.webservice.response.CountryList;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;

/**
 * Created by MHMTNASIF on 22.02.2018.
 */

public interface AppClient {

    @GET("application/get_country_list")
    Call<List<CountryList>> getCountryList();
}
