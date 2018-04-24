package com.appchallengers.appchallengers;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.appchallengers.appchallengers.fragments.show_setting_user_activity_fragment.SettingProfileFragment;

import static com.appchallengers.appchallengers.helpers.util.Constants.MY_PREFS_NAME;

public class ShowSettingUserActivity extends AppCompatActivity implements AdapterView.OnItemClickListener, View.OnClickListener {
    private ListView mListViewSetting;
    private ImageView mBackImage;
    private int mPageState;
    private FrameLayout mFrameLayout;
    private TextView mTitle;
    private FragmentTransaction mFragmentTransaction;
    private ArrayAdapter<String> mArrayAdapter;
    private SharedPreferences mSharedPreferences;
    private String[] mUlkeler =
            {"Profil Setting", "Uygulama Hakkında", "Çıkış"};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_setting_user);
        initialView();
    }

    private void initialView() {
        mListViewSetting = (ListView) findViewById(R.id.activity_setting_listview);
        mBackImage = (ImageView) findViewById(R.id.activity_setting_back_Image);
        mSharedPreferences = getSharedPreferences(MY_PREFS_NAME, MODE_PRIVATE);
        mFrameLayout=(FrameLayout) findViewById(R.id.activity_setting_framelayout);
        mTitle=(TextView) findViewById(R.id.activity_setting_title);
        mArrayAdapter = new ArrayAdapter<String>(this, R.layout.list_item_setting, R.id.text_item, mUlkeler);
        mListViewSetting.setAdapter(mArrayAdapter);
        mFrameLayout.setVisibility(View.GONE);
        mListViewSetting.setOnItemClickListener(this);
        mBackImage.setOnClickListener(this);
        mPageState=0;
    }

    @Override
    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
        switch (i) {
            case 0: {//Profil setting
                mPageState=i+1;
                mTitle.setText("Profile Setting");
                loadFragment(new SettingProfileFragment());
                break;
            }
            case 1: {// App About setting
                //  loadFragment( getApplicationContext(),ne);
                break;
            }
            case 2: {//App exit
                mSharedPreferences.edit().remove("token").commit();
                mSharedPreferences.edit().remove("fullName").commit();
                mSharedPreferences.edit().remove("imageUrl").commit();
                mSharedPreferences.edit().remove("email").commit();
                mSharedPreferences.edit().remove("active").commit();
                mSharedPreferences.edit().remove("status").commit();
                startActivity(new Intent(getApplicationContext(), MainActivity.class));
                finish();
                break;
            }
        }
    }

    private void loadFragment(Fragment fragment) {
        mFragmentTransaction = getSupportFragmentManager().beginTransaction();
        mFragmentTransaction.add(R.id.activity_setting_framelayout, fragment);
        mFragmentTransaction.commit();
        mFrameLayout.setVisibility(View.VISIBLE);
        mListViewSetting.setVisibility(View.GONE);

    }

    private void removeFragment(Fragment fragment) {
        mFragmentTransaction = getSupportFragmentManager().beginTransaction();
        mFragmentTransaction.add(R.id.activity_setting_framelayout, fragment);
        mFragmentTransaction.remove(fragment).commit();
        mFrameLayout.setVisibility(View.GONE);
        mListViewSetting.setVisibility(View.VISIBLE);
        mPageState=0;
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.activity_setting_back_Image: {
                switch (mPageState) {
                    case 0: {
                        startActivity(new Intent(getApplicationContext(), ShowProfilActivity.class));
                        finish();
                        break;
                    }
                    case 1:{
                        mTitle.setText("Settings");
                        removeFragment(new SettingProfileFragment());
                        break;
                    }
                }
                break;
            }
        }
    }
}
