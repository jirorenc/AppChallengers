package com.appchallengers.appchallengers;

import android.content.Intent;
import android.content.SharedPreferences;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;

import com.appchallengers.appchallengers.fragments.main.TrendsFeedFragment;
import com.appchallengers.appchallengers.helpers.setpages.SetMainPages;
import com.appchallengers.appchallengers.helpers.util.Constants;
import com.appchallengers.appchallengers.helpers.util.Utils;
import com.squareup.picasso.Picasso;

import okhttp3.internal.Util;

import static com.appchallengers.appchallengers.helpers.util.Constants.MY_PREFS_NAME;
import static android.content.Context.MODE_PRIVATE;

public class MainActivity extends AppCompatActivity  implements BottomNavigationView.OnNavigationItemSelectedListener,View.OnClickListener{
    private SharedPreferences mSharedPreferences;
    private String mToken;
    private String mActive;
    private String mProfileImageUrl;
    private ImageView mProfileImageView;
    public static FragmentManager mFragmentManager;
    private BottomNavigationView mBottomNavigationView;
    private static final String TAG = "MainActivity";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Constants.contex=getApplicationContext();
        initialView();
        checkAuthentication();

    }
    private void checkAuthentication(){
        if (mToken.equals("") || mToken == null || mToken.equals("0")) {
            Intent intent = new Intent(getApplicationContext(), LoginActivity.class).addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            startActivityForResult(intent, 0);
            finish();
        } else {
            if ( mActive.equals("0")) {
                Intent intent = new Intent(getApplicationContext(), LoginActivity.class).addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivityForResult(intent, 0);
                finish();
            }else{
                mFragmentManager = getSupportFragmentManager();
                mBottomNavigationView.getMenu().getItem(0).setCheckable(true);
                SetMainPages.getInstance().constructor(MainActivity.this, 0);
            }
        }

    }
    private void initialView() {
        mSharedPreferences = getSharedPreferences(MY_PREFS_NAME,MODE_PRIVATE);
        Utils.sharedPreferences = mSharedPreferences;
        mToken = Utils.getPref("token");
        mActive = Utils.getPref("active");
        mProfileImageUrl=Utils.getPref("imageUrl");
        mProfileImageView=(ImageView)findViewById(R.id.main_activity_user_profile_picture);
        if (mProfileImageUrl!=null){
            Picasso.with(this).load(mProfileImageUrl).into(mProfileImageView);
        }
        mBottomNavigationView = (BottomNavigationView) findViewById(R.id.bottom_navigation);

        mBottomNavigationView.setOnNavigationItemSelectedListener(this);
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        item.setChecked(true);

        switch (item.getItemId()) {
            case R.id.action_add_challenge:
                startActivity(new Intent(MainActivity.this,CameraActivity.class));
                break;
            case R.id.action_user_feed:
                if (mFragmentManager!=null)
                    SetMainPages.getInstance().constructor(MainActivity.this,0);
                break;
            case R.id.action_trends_feed:
                if (mFragmentManager!=null)
                    SetMainPages.getInstance().constructor(MainActivity.this,1);
                break;
        }
        return false;
    }

    @Override
    public void onClick(View view) {

    }

    @Override
    public void onBackPressed() {
        Fragment currentFragment = MainActivity.mFragmentManager.findFragmentById(R.id.pager);
        if (currentFragment instanceof TrendsFeedFragment){
            SetMainPages.getInstance().constructor(MainActivity.this,0);
        }else{
            super.onBackPressed();
        }

    }
}
