package com.appchallengers.appchallengers;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

import com.appchallengers.appchallengers.helpers.adapters.ShowLikesAdapter;
import com.appchallengers.appchallengers.helpers.util.ErrorHandler;
import com.appchallengers.appchallengers.webservice.remote.UserVote;
import com.appchallengers.appchallengers.webservice.remote.UserVoteApiClient;
import com.appchallengers.appchallengers.webservice.response.UserBaseDataModel;
import com.victor.loading.rotate.RotateLoading;

import java.io.IOException;
import java.util.List;

import io.reactivex.Observable;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;
import retrofit2.Response;

public class ShowUserActivity extends AppCompatActivity {

    private long mUserId;
    private TextView example;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_user);
        if (!getIntent().hasExtra("user_id")) {
            finish();
        } else {
            Bundle bundle = getIntent().getExtras();
            mUserId = bundle.getLong("user_id");
            initialView();
        }

    }

    private void initialView() {
        example=(TextView)findViewById(R.id.example);
        example.setText(mUserId+"");
    }



    @Override
    public void finish() {
        super.finish();
    }
}
