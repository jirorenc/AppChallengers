package com.appchallengers.appchallengers.fragments.login;


import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.appchallengers.appchallengers.R;
import com.appchallengers.appchallengers.helpers.database.CountriesTable;
import com.appchallengers.appchallengers.helpers.database.Database;
import com.appchallengers.appchallengers.helpers.setpages.SetLoginPages;
import com.appchallengers.appchallengers.helpers.util.Utils;
import com.appchallengers.appchallengers.webservice.remote.ApiClient;
import com.appchallengers.appchallengers.webservice.remote.AppClient;
import com.appchallengers.appchallengers.webservice.response.CountryList;

import java.util.List;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static android.content.Context.MODE_PRIVATE;
import static com.appchallengers.appchallengers.helpers.util.Constants.MY_PREFS_NAME;


public class SplashFragment extends Fragment {

    private SharedPreferences mSharedPreferences;
    private boolean mStatus;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        mSharedPreferences =getActivity().getSharedPreferences(MY_PREFS_NAME, MODE_PRIVATE);
        Utils.sharedPreferences = mSharedPreferences;
        mStatus=true;
        getCountryList();
        return inflater.inflate(R.layout.fragment_splash, container, false);
    }


    private void getCountryList() {
        AppClient appClient= ApiClient.getAppClient();
        Call<List<CountryList>> countryListCall=appClient.getCountryList();
        countryListCall.enqueue(new Callback<List<CountryList>>() {
            @Override
            public void onResponse(Call<List<CountryList>> call, Response<List<CountryList>> response) {
                if (response.code()==200){
                    Database database=new Database();
                    CountriesTable countriesTable=new CountriesTable(database.open(getContext()));
                    for (CountryList countryList:response.body()){
                        long status=countriesTable.create(countryList);
                        if (status==-1){
                            mStatus=false;
                            break;
                            //TODO:Eror message if insert failed
                        }
                    }
                    database.close();
                    if (mStatus){
                        Utils.setSharedPreferencesBoolean("firstrun", false);
                        SetLoginPages.getInstance().constructor(getActivity(),1);
                    }
                }
            }
            @Override
            public void onFailure(Call<List<CountryList>> call, Throwable t) {
                mStatus=false;
                //TODO:Eror message response failed
            }
        });
    }


}
