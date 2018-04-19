package com.appchallengers.appchallengers;

import android.net.Uri;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

import com.appchallengers.appchallengers.fragments.show_challenge_detail_activity_fragment.DetailChallengeFragment;
import com.appchallengers.appchallengers.fragments.show_challenge_detail_activity_fragment.KnowDetailChallengesFragment;
import com.appchallengers.appchallengers.helpers.adapters.DetailChallengePagerAdapter;

public class ChallengeDetailActivity extends AppCompatActivity implements DetailChallengeFragment.OnFragmentInteractionListener,KnowDetailChallengesFragment.OnFragmentInteractionListener {

    private TextView deneme;
    private Bundle mBundle;
    private TabLayout mTablayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_challenge_detail);
        if (!getIntent().hasExtra("challenge_detail_id")) {
            finish();
        } else {

        }
        proccess();
    }
    private void proccess() {
        mTablayout = (TabLayout) findViewById(R.id.detail_tablayout);
        mTablayout.addTab(mTablayout.newTab().setText("Fisrt Page"));
        mTablayout.addTab(mTablayout.newTab().setText("Second Page"));
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


    }

    @Override
    public void onFragmentInteraction(Uri uri) {

    }
}
