package com.appchallengers.appchallengers;


import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.appchallengers.appchallengers.fragments.camera.CaptureVideoFragment;
import com.appchallengers.appchallengers.fragments.camera.VideoPlayerFragment;
import com.appchallengers.appchallengers.helpers.setpages.SetCameraPages;

public class CameraActivity extends AppCompatActivity {

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
        } else {
            super.onBackPressed();
        }
    }
}
