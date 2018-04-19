package com.appchallengers.appchallengers.helpers.adapters;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import com.appchallengers.appchallengers.fragments.show_challenge_detail_activity_fragment.DetailChallengeFragment;
import com.appchallengers.appchallengers.fragments.show_challenge_detail_activity_fragment.KnowDetailChallengesFragment;

/**
 * Created by jir on 17.4.2018.
 */

public class DetailChallengePagerAdapter extends FragmentStatePagerAdapter {
    private int mNoTabs;
  private Bundle mBundle;
    public DetailChallengePagerAdapter(FragmentManager fm, int NumberOfTabs, Bundle bundle) {
        super(fm);
        this.mNoTabs=NumberOfTabs;
        this.mBundle=bundle;
    }

    @Override
    public Fragment getItem(int position) {
        switch (position){
            case 0:{
                DetailChallengeFragment detailChallengeFragment=new DetailChallengeFragment();
                detailChallengeFragment.setArguments(mBundle);
                return  detailChallengeFragment;
            }

            case 1:{
                KnowDetailChallengesFragment knowDetailChallengesFragment =new KnowDetailChallengesFragment();
                knowDetailChallengesFragment.setArguments(mBundle);
                return knowDetailChallengesFragment;
            }

            default: return null;
        }

    }

    @Override
    public int getCount() {
        return mNoTabs;
    }
}
