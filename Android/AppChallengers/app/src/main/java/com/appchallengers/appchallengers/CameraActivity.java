package com.appchallengers.appchallengers;


import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.widget.FrameLayout;

import com.appchallengers.appchallengers.fragments.camera.SelectFriendsFragment;
import com.appchallengers.appchallengers.fragments.camera.VideoPlayerFragment;
import com.appchallengers.appchallengers.helpers.setpages.SetCameraPages;
import com.appchallengers.appchallengers.helpers.util.ConnectivityReceiver;
import com.appchallengers.appchallengers.helpers.util.InternetControl;
import com.appchallengers.appchallengers.helpers.util.MyApplication;

public class CameraActivity extends AppCompatActivity implements ConnectivityReceiver.ConnectivityReceiverListener {

    public static FragmentManager mFragmentManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_camera);
        mFragmentManager = getSupportFragmentManager();
        if (null == savedInstanceState) {
            SetCameraPages.getInstance().constructor(this, 0);
        }
    }

    @Override
    public void onBackPressed() {
        Fragment currentFragment = CameraActivity.mFragmentManager.findFragmentById(R.id.pager);
        if (currentFragment instanceof VideoPlayerFragment) {
            SetCameraPages.getInstance().constructor(CameraActivity.this, 0);
        } else if (currentFragment instanceof SelectFriendsFragment) {
            SetCameraPages.getInstance().constructor(CameraActivity.this, 0);
        } else {
            startActivity(new Intent(CameraActivity.this,MainActivity.class));
            super.onBackPressed();
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        // register connection status listener
        MyApplication.getInstance().setConnectivityListener(this);
    }


    @Override
    public void onNetworkConnectionChanged(boolean isConnected) {
        FrameLayout frameLayout=(FrameLayout) findViewById(R.id.pager);
        InternetControl.getInstance().showSnackGeneral(frameLayout,isConnected);
    }
}
