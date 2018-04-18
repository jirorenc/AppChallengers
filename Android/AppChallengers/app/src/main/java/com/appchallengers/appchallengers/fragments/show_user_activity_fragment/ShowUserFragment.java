package com.appchallengers.appchallengers.fragments.show_user_activity_fragment;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.appchallengers.appchallengers.R;
import com.appchallengers.appchallengers.helpers.adapters.UserInfoChallengeFeedAdapter;
import com.appchallengers.appchallengers.helpers.util.ErrorHandler;
import com.appchallengers.appchallengers.webservice.remote.GetUserInfo;
import com.appchallengers.appchallengers.webservice.remote.GetUserInfoApiClient;
import com.appchallengers.appchallengers.webservice.response.GetUserInfoResponseModel;
import com.appchallengers.appchallengers.webservice.response.UserChallengeFeedListModel;
import com.squareup.picasso.Picasso;
import com.victor.loading.rotate.RotateLoading;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;
import im.ene.toro.widget.Container;
import io.reactivex.Observable;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;
import retrofit2.Response;

public class ShowUserFragment extends Fragment {

    private View mRootView;
    private long mUserId;
    private CircleImageView mProfilePicture;
    private TextView mUserName;
    private TextView mUserChallengeNumber;
    private TextView mUserAcceptedChallengeNumber;
    private TextView mUserFriendsNumber;
    private Button mRelationshipButton;
    private LinearLayout mUserInfoLinearLayout;
    private List<UserChallengeFeedListModel> mUserChallengeInfoFeedList;
    private UserInfoChallengeFeedAdapter mUserInfoChallengeFeedAdapter;
    private Container mContainer;
    private RotateLoading mRotateLoading;
    private RotateLoading mFeedRotateLoading;
    private LinearLayoutManager mLinerlayoutmanager;
    private CompositeDisposable mCompositeDisposable;
    Observable<Response<List<UserChallengeFeedListModel>>> getUserChallenges;
    private Observable<Response<GetUserInfoResponseModel>> mGetUserInfoObservable;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        mRootView = inflater.inflate(R.layout.fragment_show_user, container, false);
        initialView(mRootView);
        return mRootView;
    }

    private void initialView(View mRootView) {
        mUserChallengeInfoFeedList = new ArrayList<>();
        mContainer = mRootView.findViewById(R.id.player_container);
        mLinerlayoutmanager = new LinearLayoutManager(getContext());
        mContainer.setLayoutManager(mLinerlayoutmanager);
        mCompositeDisposable = new CompositeDisposable();
        mRotateLoading = (RotateLoading) mRootView.findViewById(R.id.activity_show_user_info_rotateloading);
        mFeedRotateLoading = (RotateLoading) mRootView.findViewById(R.id.activity_show_user_rotateloading);
        mProfilePicture = (CircleImageView) mRootView.findViewById(R.id.show_user_activity_user_profile_picture);
        mUserName = (TextView) mRootView.findViewById(R.id.show_user_activity_user_name);
        mUserInfoLinearLayout = (LinearLayout) mRootView.findViewById(R.id.user_info);
        mUserChallengeNumber = (TextView) mRootView.findViewById(R.id.show_user_activity_challenge_number);
        mUserAcceptedChallengeNumber = (TextView) mRootView.findViewById(R.id.show_user_activity_accepted_challenge_number);
        mUserFriendsNumber = (TextView) mRootView.findViewById(R.id.show_user_activity_friends_number);
        mRelationshipButton = (Button) mRootView.findViewById(R.id.show_user_activity_relationship_button);
        Bundle bundle = getArguments();
        if (bundle != null && bundle.containsKey("user_id")) {
            mUserId = bundle.getLong("user_id");
            getUserInfo();
        } else {
            getActivity().finish();
        }
    }

    private void getUserInfoChallengeFeed() {
        GetUserInfo getUserInfo = GetUserInfoApiClient.getUserInfoClientWithCache(getContext());
        getUserChallenges = getUserInfo.getUserChallenges(mUserId);
        getUserChallenges.subscribeOn(Schedulers.io())
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
                    }

                    @Override
                    public void onComplete() {
                        mContainer.setVisibility(View.VISIBLE);
                        mFeedRotateLoading.stop();
                        mFeedRotateLoading.setVisibility(View.GONE);
                    }
                });
    }

    public void getUserInfo() {
        GetUserInfo getUserInfo = GetUserInfoApiClient.getUserInfoClientWithCache(getContext());
        mGetUserInfoObservable = getUserInfo.getUserInfo(mUserId);
        mGetUserInfoObservable.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<Response<GetUserInfoResponseModel>>() {
                    @Override
                    public void onSubscribe(Disposable d) {
                        mCompositeDisposable.add(d);
                        mRotateLoading.start();
                    }

                    @Override
                    public void onNext(Response<GetUserInfoResponseModel> getUserInfoResponseModelResponse) {
                        if (getUserInfoResponseModelResponse.isSuccessful()) {
                            Picasso.with(getContext()).load(getUserInfoResponseModelResponse.body().getProfilepicture()).into(mProfilePicture);
                            mUserName.setText(getUserInfoResponseModelResponse.body().getFullname());
                            mUserChallengeNumber.setText(getUserInfoResponseModelResponse.body().getChallenges() + "");
                            mUserAcceptedChallengeNumber.setText(getUserInfoResponseModelResponse.body().getAccepted_challenges() + "");
                            mUserFriendsNumber.setText(getUserInfoResponseModelResponse.body().getFriends() + "");
                            relationship(getUserInfoResponseModelResponse.body().getUseractionid(), getUserInfoResponseModelResponse.body().getStatus());
                            getUserInfoChallengeFeed();
                        } else {
                            if (getUserInfoResponseModelResponse.code() == 400) {
                                if (getUserInfoResponseModelResponse.errorBody() != null) {
                                    try {
                                        ErrorHandler.getInstance(getContext()).showEror(getUserInfoResponseModelResponse.errorBody().string());
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
                    }

                    @Override
                    public void onComplete() {
                        mRotateLoading.stop();
                        mRotateLoading.setVisibility(View.GONE);
                        mUserInfoLinearLayout.setVisibility(View.VISIBLE);
                    }
                });
    }

    private void relationship(long useractionid, int status) {
        if (status == 1) {
            mRelationshipButton.setText("FRİENDS");
        } else if (status == -1) {
            mRelationshipButton.setText("add friends");
        }
    }


    @Override
    public void onDetach() {
        if (mCompositeDisposable != null)
            mCompositeDisposable.dispose();
        if (mRotateLoading != null) {
            mRotateLoading.stop();
            mRotateLoading.setVisibility(View.GONE);
        }
        if (mFeedRotateLoading != null) {
            mFeedRotateLoading.stop();
            mFeedRotateLoading.setVisibility(View.GONE);
        }

        super.onDetach();
    }
}
