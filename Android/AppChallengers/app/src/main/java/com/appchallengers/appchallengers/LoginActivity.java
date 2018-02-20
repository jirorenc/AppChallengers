package com.appchallengers.appchallengers;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.appchallengers.appchallengers.fragments.login.SignUpFragment;
import com.appchallengers.appchallengers.helpers.setpages.SetLoginPages;

public class LoginActivity extends AppCompatActivity {

    public static FragmentManager mFragmentManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        mFragmentManager = getSupportFragmentManager();
        SetLoginPages.getInstance().constructor(LoginActivity.this, 0);
    }

    @Override
    public void onBackPressed() {
        Fragment currentFragment = LoginActivity.mFragmentManager.findFragmentById(R.id.pager);
        if (currentFragment instanceof SignUpFragment)
            SetLoginPages.getInstance().constructor(LoginActivity.this,0);
        else
            super.onBackPressed();
    }
}
