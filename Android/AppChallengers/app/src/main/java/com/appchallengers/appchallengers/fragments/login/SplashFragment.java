package com.appchallengers.appchallengers.fragments.login;


import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.appchallengers.appchallengers.R;
import com.appchallengers.appchallengers.helpers.database.CountriesTable;
import com.appchallengers.appchallengers.helpers.database.Database;
import com.appchallengers.appchallengers.helpers.setpages.SetLoginPages;
import com.appchallengers.appchallengers.helpers.util.Utils;
import com.appchallengers.appchallengers.webservice.remote.ApiClientWithoutCache;
import com.appchallengers.appchallengers.webservice.remote.AppClient;
import com.appchallengers.appchallengers.webservice.response.CountryList;
import com.jakewharton.retrofit2.adapter.rxjava2.HttpException;

import java.util.List;

import io.reactivex.Observable;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;
import retrofit2.Response;

import static android.content.Context.MODE_PRIVATE;
import static com.appchallengers.appchallengers.helpers.util.Constants.MY_PREFS_NAME;


public class SplashFragment extends Fragment {

    private SharedPreferences mSharedPreferences;
    private boolean mStatus;
    private CompositeDisposable mCompositeDisposable;
    Observable<Response<List<CountryList>>> mListObservable;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        mSharedPreferences = getActivity().getSharedPreferences(MY_PREFS_NAME, MODE_PRIVATE);
        Utils.sharedPreferences = mSharedPreferences;
        mCompositeDisposable = new CompositeDisposable();
        mStatus = true;
        getCountryList();
        return inflater.inflate(R.layout.fragment_splash, container, false);
    }


    private void getCountryList() {
        AppClient appClient = ApiClientWithoutCache.getAppClient();
        mListObservable = appClient.getCountryList();
        mListObservable.subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<Response<List<CountryList>>>() {
                    @Override
                    public void onSubscribe(Disposable d) {
                        mCompositeDisposable.add(d);
                    }

                    @Override
                    public void onNext(Response<List<CountryList>> value) {
                        Database database = new Database();
                        CountriesTable countriesTable = new CountriesTable(database.open(getContext()));
                        for (CountryList countryList : value.body()) {
                            long status = countriesTable.create(countryList);
                            if (status == -1) {
                                mStatus = false;
                                break;
                                //TODO:Eror message if insert failed
                            }
                        }
                        database.close();
                        if (mStatus) {
                            Utils.setSharedPreferencesBoolean("firstrun", false);
                            SetLoginPages.getInstance().constructor(getActivity(), 1);
                        }
                    }

                    @Override
                    public void onError(Throwable e) {
                        if (e instanceof HttpException)
                            Log.e("retrofit",((HttpException) e).code()+"");
                    }

                    @Override
                    public void onComplete() {

                    }
                });
    }

    @Override
    public void onDestroy() {
        mCompositeDisposable.dispose();
        super.onDestroy();
    }

    @Override
    public void onPause() {
        mCompositeDisposable.dispose();
        super.onPause();
    }
}
