package com.appchallengers.appchallengers.fragments.login;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.appchallengers.appchallengers.R;
import com.appchallengers.appchallengers.helpers.setpages.SetLoginPages;


public class LoginFragment extends Fragment implements View.OnClickListener {

    private View mRootView;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        mRootView = inflater.inflate(R.layout.fragment_login, container, false);
        initialView(mRootView);
        return mRootView;
    }

    private void initialView(View rootView) {

    }

    @Override
    public void onClick(View view) {

    }
}
