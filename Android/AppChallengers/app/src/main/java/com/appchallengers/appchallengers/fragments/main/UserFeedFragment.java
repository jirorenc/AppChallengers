package com.appchallengers.appchallengers.fragments.main;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.widget.TextView;
import android.widget.Toast;

import com.appchallengers.appchallengers.R;
import com.appchallengers.appchallengers.helpers.adapters.UserFeedAdapter;
import com.appchallengers.appchallengers.helpers.onitemclick.RecyclerItemClickListener;
import com.appchallengers.appchallengers.helpers.util.ErrorHandler;
import com.appchallengers.appchallengers.webservice.remote.UserChallenges;
import com.appchallengers.appchallengers.webservice.remote.UserChallengesApiClient;
import com.appchallengers.appchallengers.webservice.response.UserChallengeFeedListModel;
import com.labo.kaji.fragmentanimations.MoveAnimation;
import com.victor.loading.rotate.RotateLoading;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import im.ene.toro.widget.Container;
import io.reactivex.Observable;
import io.reactivex.Observer;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class UserFeedFragment extends Fragment {
    private View mRootView;
    private String mHeadLine;
    private String mPath;
    private UserFeedAdapter mUserFeedAdapter;
    private Container mContainer;
    private RotateLoading mRotateLoading;
    private TextView mLoadMessage;
    private LinearLayoutManager mLinerlayoutmanager;
    private CompositeDisposable mCompositeDisposable;
    private List<UserChallengeFeedListModel> mChallengeFeedListModelList;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        mRootView = inflater.inflate(R.layout.fragment_user_feed, container, false);
        initialView(mRootView);
        return mRootView;
    }

    private void initialView(View mRootView) {
        mChallengeFeedListModelList = new ArrayList<>();
        mContainer = mRootView.findViewById(R.id.player_container);
        mLinerlayoutmanager = new LinearLayoutManager(getContext());
        mContainer.setLayoutManager(mLinerlayoutmanager);
        mCompositeDisposable = new CompositeDisposable();
        mLoadMessage = (TextView) mRootView.findViewById(R.id.user_feed_new_load_message);
        mRotateLoading = (RotateLoading) mRootView.findViewById(R.id.user_feed_fragment_rotateloading);
        Bundle bundle = getArguments();
        if (bundle != null && bundle.containsKey("status")) {
            mPath = bundle.getString("path");
            mHeadLine = bundle.getString("headLine");
            addChallenge();
        }
    }

    private void addChallenge() {
        mLoadMessage.setVisibility(View.VISIBLE);
        UserChallenges userChallenges = UserChallengesApiClient.getUserChallengesClient(getContext());
        File file = new File(mPath);
        RequestBody requestFile = RequestBody.create(MediaType.parse("multipart/form-data"), file);
        RequestBody headLine = RequestBody.create(MultipartBody.FORM, mHeadLine);
        MultipartBody.Part body = MultipartBody.Part.createFormData("video", file.getName(), requestFile);
        Call<UserChallengeFeedListModel> createAccountResponseModelCall = userChallenges.addChallenge(headLine, body);
        createAccountResponseModelCall.enqueue(new Callback<UserChallengeFeedListModel>() {
            @Override
            public void onResponse(Call<UserChallengeFeedListModel> call, Response<UserChallengeFeedListModel> response) {

                if (response.isSuccessful()) {
                    mChallengeFeedListModelList.add(0, response.body());
                    mUserFeedAdapter = new UserFeedAdapter(getContext(), mChallengeFeedListModelList);
                    mContainer.setAdapter(mUserFeedAdapter);
                } else {
                    if (response.code() == 400) {
                        if (response.errorBody() != null) {
                            try {
                                ErrorHandler.getInstance(getContext()).showEror(response.errorBody().string());
                            } catch (IOException e) {
                                e.printStackTrace();
                            }
                        }
                    } else {
                        ErrorHandler.getInstance(getContext()).showEror("{code:1000}");
                    }
                }
                mLoadMessage.setVisibility(View.GONE);
            }

            @Override
            public void onFailure(Call<UserChallengeFeedListModel> call, Throwable t) {
                mLoadMessage.setVisibility(View.GONE);
                if (t instanceof IOException) {
                    if (t instanceof java.net.ConnectException) {
                        ErrorHandler.getInstance(getContext()).showInfo(300);
                    }
                } else {
                    ErrorHandler.getInstance(getContext()).showEror("{code:1000}");
                }

            }
        });

    }

    private void getUserChallengeFeed() {
        UserChallenges userChallenges = UserChallengesApiClient.getUserChallengesClient(getContext());
        Observable<Response<List<UserChallengeFeedListModel>>> responseObservable = userChallenges.getUserChallengeFeed();
        responseObservable.subscribeOn(Schedulers.newThread())
                .observeOn(io.reactivex.android.schedulers.AndroidSchedulers.mainThread()).subscribe(new Observer<Response<List<UserChallengeFeedListModel>>>() {
            @Override
            public void onSubscribe(Disposable d) {
                mCompositeDisposable.add(d);
                mRotateLoading.start();
            }

            @Override
            public void onNext(Response<List<UserChallengeFeedListModel>> userChallengeFeedListModelResponse) {
                mChallengeFeedListModelList.addAll(userChallengeFeedListModelResponse.body());
                mUserFeedAdapter = new UserFeedAdapter(getContext(), userChallengeFeedListModelResponse.body());
                mContainer.setAdapter(mUserFeedAdapter);
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
            }
        });
    }

    @Override
    public void onResume() {
        super.onResume();
        getUserChallengeFeed();
        click();
    }

    @Override
    public void onDetach() {
        mCompositeDisposable.dispose();
        mRotateLoading.stop();
        mRotateLoading.setVisibility(View.GONE);
        super.onDetach();
    }

    public void click(){



    }
    @Override
    public Animation onCreateAnimation(int transit, boolean enter, int nextAnim) {
        if (enter) {
            return MoveAnimation.create(MoveAnimation.LEFT, enter, 500);
        } else {
            return MoveAnimation.create(MoveAnimation.RIGHT, enter, 500);
        }
    }

}
