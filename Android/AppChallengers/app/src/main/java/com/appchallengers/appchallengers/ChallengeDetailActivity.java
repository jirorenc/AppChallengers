package com.appchallengers.appchallengers;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

public class ChallengeDetailActivity extends AppCompatActivity {

    private TextView deneme;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_challenge_detail);
        deneme=(TextView)findViewById(R.id.deneme);
        if (!getIntent().hasExtra("challenge_detail_id")) {
            finish();
        } else {
            deneme.setText(getIntent().getLongExtra("challenge_detail_id",-1)+"");
        }
    }
}
