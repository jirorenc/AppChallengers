package com.appchallengers.appchallengers;

import android.net.Uri;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.TabLayout;
import android.content.res.Resources;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.appchallengers.appchallengers.fragments.show_challenge_detail_activity_fragment.LatestDetailChallengeFragment;
import com.appchallengers.appchallengers.fragments.show_challenge_detail_activity_fragment.PopularDetailChallengesFragment;
import com.appchallengers.appchallengers.helpers.adapters.DetailChallengePagerAdapter;
import com.appchallengers.appchallengers.helpers.util.ErrorHandler;
import com.appchallengers.appchallengers.webservice.remote.GetChallengeDetailInfo;
import com.appchallengers.appchallengers.webservice.remote.GetChallengeDetailInfoApiClient;
import com.appchallengers.appchallengers.webservice.response.GetChallengeDetailResponseModel;

import java.io.IOException;

import io.reactivex.Observable;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;
import retrofit2.Response;

public class ChallengeDetailActivity extends AppCompatActivity implements View.OnClickListener,LatestDetailChallengeFragment.OnFragmentInteractionListener,PopularDetailChallengesFragment.OnFragmentInteractionListener {

    private Bundle mBundle;
    private Long mChallengeId;
    private TextView mChallengeNumber;
    private FloatingActionButton mFabButton;
    private ImageView mBackArrow;
    private TextView mChallengeDetailHeadLine;
    private CompositeDisposable mCompositeDisposable;
    private TabLayout mTablayout;
    private Observable<Response<GetChallengeDetailResponseModel>> mGetChallengeDetailHeadLine;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_challenge_detail);
       if (!getIntent().hasExtra("challenge_detail_id")) {
            finish();
        } else {
            mBundle=getIntent().getExtras();
            mChallengeId =getIntent().getLongExtra("challenge_detail_id",-1);
            initialView();
            getChallengeDetailHeadline();
            proccess();
        }

    }
    private void proccess() {
        mTablayout = (TabLayout) findViewById(R.id.detail_tablayout);
        mTablayout.addTab(mTablayout.newTab().setText("Latest Page"));
        mTablayout.addTab(mTablayout.newTab().setText("Popular Page"));
        mTablayout.setTabGravity(TabLayout.GRAVITY_FILL);

        final ViewPager viewPager = (ViewPager) findViewById(R.id.detail_pager);
        final DetailChallengePagerAdapter detailChallengePagerAdapter = new DetailChallengePagerAdapter(getSupportFragmentManager(), mTablayout.getTabCount(),mBundle);
        viewPager.setAdapter(detailChallengePagerAdapter);
        //todo
        viewPager.setOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(mTablayout));
        mTablayout.setOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                viewPager.setCurrentItem(tab.getPosition());
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }

        });


    }  private void initialView() {
        mCompositeDisposable = new CompositeDisposable();
        mBackArrow = (ImageView) findViewById(R.id.detail_fragment_back_button);
        mChallengeDetailHeadLine = (TextView) findViewById(R.id.detail_fragment_headline);
        mChallengeNumber = (TextView) findViewById(R.id.detail_fragment_challenge_number);
        mFabButton=(FloatingActionButton) findViewById(R.id.detail_challenge_fab);
        mBackArrow.setOnClickListener(this);
        mFabButton.setOnClickListener(this);
    }


    public void getChallengeDetailHeadline() {
        GetChallengeDetailInfo getChallengeDetailInfo = GetChallengeDetailInfoApiClient.getChallengeDetailInfoClientWithCache(getApplicationContext());
        mGetChallengeDetailHeadLine = getChallengeDetailInfo.getChallengeDetailInfo(mChallengeId);
        mGetChallengeDetailHeadLine.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<Response<GetChallengeDetailResponseModel>>() {
                    @Override
                    public void onSubscribe(Disposable d) {
                        mCompositeDisposable.add(d);
                    }

                    @Override
                    public void onNext(Response<GetChallengeDetailResponseModel> getChallengeDetailInfoResponseModelResponse) {
                        if (getChallengeDetailInfoResponseModelResponse.isSuccessful()) {
                            String headline,counter;
                            headline=(getChallengeDetailInfoResponseModelResponse.body().getHeadLine()).toString();
                            headline= headline.trim();
                            counter= String.valueOf((getChallengeDetailInfoResponseModelResponse.body().getCounter()))+" challenge";
                            mChallengeDetailHeadLine.setText(headline);
                            mChallengeNumber.setText(counter);
                            setmFabButton(getChallengeDetailInfoResponseModelResponse.body().getStatus());

                        } else {
                            if (getChallengeDetailInfoResponseModelResponse.code() == 400) {
                                if (getChallengeDetailInfoResponseModelResponse.errorBody() != null) {
                                    try {
                                        ErrorHandler.getInstance(getApplicationContext()).showEror(getChallengeDetailInfoResponseModelResponse.errorBody().string());
                                    } catch (IOException e) {
                                        e.printStackTrace();
                                    }
                                }
                            } else {
                                ErrorHandler.getInstance(getApplicationContext()).showEror("{code:1000}");
                            }

                        }
                    }

                    @Override
                    public void onError(Throwable e) {
                        if (e instanceof IOException) {
                            if (e instanceof java.net.ConnectException) {
                                ErrorHandler.getInstance(getApplicationContext()).showInfo(300);
                            }
                        } else {
                            ErrorHandler.getInstance(getApplicationContext()).showEror("{code:1000}");
                        }
                    }

                    @Override
                    public void onComplete() {
                    }
                });
    }
    public void setmFabButton(int status){
        switch (status){
            case 0:{
                mFabButton.setImageDrawable(getResources().getDrawable(R.drawable.ic_videocam_on_black_24dp));
                break;
            }
            case  1:{
                mFabButton.setImageDrawable(getResources().getDrawable(R.drawable.ic_videocam_off_white_24dp));
                break;
            }
        }

    }


    @Override
    protected void onDestroy() {
        if (mCompositeDisposable != null)
            mCompositeDisposable.dispose();
        super.onDestroy();
    }


    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.detail_fragment_back_button: {
                finish();
                break;
            }
            case R.id.detail_challenge_fab: {
                // todo for fab button add
                break;
            }
        }
    }

    @Override
    public void onFragmentInteraction(Uri uri) {

    }
}
