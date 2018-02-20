package com.appchallengers.appchallengers;

import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.appchallengers.appchallengers.helpers.util.Utils;

import static com.appchallengers.appchallengers.helpers.util.Constants.MY_PREFS_NAME;
import static android.content.Context.MODE_PRIVATE;

public class MainActivity extends AppCompatActivity {
    private SharedPreferences mSharedPreferences;
    private String mToken;
    private String mActive;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initialView();
        checkAuthentication();

    }
    private void checkAuthentication(){
        if (mToken.equals("") || mToken == null || mToken.equals("0")) {
            //Intent intent = new Intent(getApplicationContext(), LoginActivity.class).addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            //  startActivityForResult(intent, 0);
            startActivity(new Intent(MainActivity.this,LoginActivity.class));
            finish();
        } else {
            if ( mActive.equals("0")) {
                startActivity(new Intent(MainActivity.this,LoginActivity.class));
                finish();
            }
        }

    }
    private void initialView() {
        mSharedPreferences = getSharedPreferences(MY_PREFS_NAME,MODE_PRIVATE);
        Utils.sharedPreferences = mSharedPreferences;
        mToken = Utils.getPref("token");
        mActive = Utils.getPref("active");

    }

}
