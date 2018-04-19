package com.appchallengers.appchallengers.fragments.show_profil_activity_fragment;

import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.appchallengers.appchallengers.R;
import com.appchallengers.appchallengers.helpers.adapters.UserInfoChallengeFeedAdapter;
import com.appchallengers.appchallengers.helpers.util.ErrorHandler;
import com.appchallengers.appchallengers.webservice.remote.GetProfileApiClient;
import com.appchallengers.appchallengers.webservice.remote.GetProfileInfo;
import com.appchallengers.appchallengers.webservice.remote.GetUserInfo;
import com.appchallengers.appchallengers.webservice.remote.GetUserInfoApiClient;
import com.appchallengers.appchallengers.webservice.response.UserChallengeFeedListModel;
import com.victor.loading.rotate.RotateLoading;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import im.ene.toro.widget.Container;
import io.reactivex.Observable;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;
import retrofit2.Response;

public class ProfilAcceptedChallangesFragment extends Fragment {


    private View mRootView;
    private long mUserId;
    private List<UserChallengeFeedListModel> mUserChallengeInfoFeedList;
    private UserInfoChallengeFeedAdapter mUserInfoChallengeFeedAdapter;
    private Container mContainer;
    private RotateLoading mFeedRotateLoading;
    private LinearLayoutManager mLinerlayoutmanager;
    private CompositeDisposable mCompositeDisposable;
    Observable<Response<List<UserChallengeFeedListModel>>> getProfileAcceptedChallenges;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        mRootView = inflater.inflate(R.layout.fragment_profil_accepted_challanges,container,false);
        initialView(mRootView);
        return mRootView;
    }
    private void initialView(View mRootView) {
        mUserChallengeInfoFeedList = new ArrayList<>();
        mContainer = mRootView.findViewById(R.id.player_container);
        mLinerlayoutmanager = new LinearLayoutManager(getContext());
        mContainer.setLayoutManager(mLinerlayoutmanager);
        mCompositeDisposable = new CompositeDisposable();
        mFeedRotateLoading = (RotateLoading) mRootView.findViewById(R.id.profile_fragment_accepted_rotateloading);
        getProfileInfoAcceptedChallengeFeed();
    }

    private void getProfileInfoAcceptedChallengeFeed() {
        GetProfileInfo getProfileInfo = GetProfileApiClient.getProfileClientWithCache(getContext());
        getProfileAcceptedChallenges = getProfileInfo.getProfileAcceptedChallenges();
        getProfileAcceptedChallenges.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<Response<List<UserChallengeFeedListModel>>>() {
                    @Override
                    public void onSubscribe(Disposable d) {
                        mCompositeDisposable.add(d);
                        mFeedRotateLoading.start();
                    }

                    @Override
                    public void onNext(Response<List<UserChallengeFeedListModel>> listResponse) {
                        if (listResponse.isSuccessful()) {
                            mUserChallengeInfoFeedList.addAll(listResponse.body());
                            mUserInfoChallengeFeedAdapter = new UserInfoChallengeFeedAdapter(getContext(), listResponse.body(), getActivity());
                            mContainer.setAdapter(mUserInfoChallengeFeedAdapter);
                        } else {
                            if (listResponse.code() == 400) {
                                if (listResponse.errorBody() != null) {
                                    try {
                                        ErrorHandler.getInstance(getContext()).showEror(listResponse.errorBody().string());
                                    } catch (IOException e) {
                                        e.printStackTrace();
                                    }
                                }
                            } else {
                                ErrorHandler.getInstance(getContext()).showEror("{code:1000}");
                            }

                        }

                    }

                    @Override
                    public void onError(Throwable e) {
                        if (e instanceof IOException) {
                            if (e instanceof java.net.ConnectException) {
                                ErrorHandler.getInstance(getContext()).showInfo(300);
                            }
                        } else {
                            ErrorHandler.getInstance(getContext()).showEror("{code:1000}");
                        }
                        mFeedRotateLoading.stop();
                        mFeedRotateLoading.setVisibility(View.GONE);
                    }

                    @Override
                    public void onComplete() {
                        mFeedRotateLoading.stop();
                        mFeedRotateLoading.setVisibility(View.GONE);
                    }
                });
    }


    @Override
    public void onDestroy() {
        if (mCompositeDisposable != null)
            mCompositeDisposable.dispose();
        if (mFeedRotateLoading != null) {
            mFeedRotateLoading.stop();
            mFeedRotateLoading.setVisibility(View.GONE);
        }
        super.onDestroy();
    }

    public interface OnFragmentInteractionListener {
        void onFragmentInteraction(Uri uri);
    }

}