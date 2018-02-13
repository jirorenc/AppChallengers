package com.appchallengers.appchallengers;

import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

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
}
