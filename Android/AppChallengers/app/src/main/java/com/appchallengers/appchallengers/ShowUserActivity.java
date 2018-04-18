package com.appchallengers.appchallengers;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;

import com.appchallengers.appchallengers.fragments.show_user_activity_fragment.ShowUserFragment;


public class ShowUserActivity extends AppCompatActivity {

    private Bundle bundle;
    private FragmentManager mFragmentManager;
    private FragmentTransaction mFragmentTransaction;
    private Fragment mFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_user);
        if (!getIntent().hasExtra("user_id")) {
            finish();
        } else {
            bundle = getIntent().getExtras();
            mFragmentManager=getSupportFragmentManager();
            mFragmentTransaction=mFragmentManager.beginTransaction();
            mFragment=new ShowUserFragment();
            mFragment.setArguments(bundle);
            mFragmentTransaction.replace(R.id.pager,mFragment,"show_user_fragment");
            mFragmentTransaction.commit();
        }

    }

}
