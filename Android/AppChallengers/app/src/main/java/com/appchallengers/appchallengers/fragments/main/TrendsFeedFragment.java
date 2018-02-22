package com.appchallengers.appchallengers.fragments.main;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;

import com.appchallengers.appchallengers.R;
import com.labo.kaji.fragmentanimations.MoveAnimation;

public class TrendsFeedFragment extends Fragment {


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_trends_feed, container, false);
    }

    @Override
    public Animation onCreateAnimation(int transit, boolean enter, int nextAnim) {
        if (enter) {
            return MoveAnimation.create(MoveAnimation.RIGHT, enter, 500);
        } else {
            return MoveAnimation.create(MoveAnimation.LEFT, enter, 500);
        }
    }

}
