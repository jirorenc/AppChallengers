package com.appchallengers.appchallengers.fragments.login;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.widget.TextView;

import com.appchallengers.appchallengers.MainActivity;
import com.appchallengers.appchallengers.R;
import com.appchallengers.appchallengers.helpers.setpages.SetLoginPages;
import com.appchallengers.appchallengers.helpers.util.Constants;
import com.appchallengers.appchallengers.helpers.util.CustomToast;
import com.appchallengers.appchallengers.helpers.util.Utils;
import com.appchallengers.appchallengers.webservice.remote.ApiClient;
import com.appchallengers.appchallengers.webservice.remote.UserClient;
import com.appchallengers.appchallengers.webservice.request.UsersLoginRequestModel;
import com.appchallengers.appchallengers.webservice.response.UserPreferencesData;
import com.dd.processbutton.iml.ActionProcessButton;
import com.labo.kaji.fragmentanimations.MoveAnimation;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static android.content.Context.MODE_PRIVATE;
import static com.appchallengers.appchallengers.helpers.util.Constants.MY_PREFS_NAME;


public class ConfirmEmailFragment extends Fragment implements View.OnClickListener {
    private View mRootView;
    private ActionProcessButton mConfirmEmailValidate;
    private TextView mConfirmEmailCodeSendAgain;
    private SharedPreferences mSharedPreferences;
    public static String mToken;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        mRootView = inflater.inflate(R.layout.fragment_confirm_email, container, false);
        initalView(mRootView);
        return mRootView;

    }

    private void initalView(View view) {
        mConfirmEmailValidate = (ActionProcessButton) view.findViewById(R.id.confirm_email_fragment_email_validate_button);
        mConfirmEmailCodeSendAgain = (TextView) view.findViewById(R.id.confirm_email_fragment_code_again_send_link_textview);
        mConfirmEmailValidate.setMode(ActionProcessButton.Mode.ENDLESS);
        mSharedPreferences= getActivity().getSharedPreferences(MY_PREFS_NAME, MODE_PRIVATE);
        mConfirmEmailCodeSendAgain.setOnClickListener(this);
        mConfirmEmailValidate.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.confirm_email_fragment_email_validate_button: {
                ButtonActionActive();
                checkConfrimEmail();
                break;
            }
            case R.id.confirm_email_fragment_code_again_send_link_textview: {
                userResendConfirmEmail();
                break;
            }

        }

    }
    private void userResendConfirmEmail(){
        Utils.sharedPreferences=mSharedPreferences;
        UserClient userClient= ApiClient.getUserClient();
        mToken = Utils.getPref("token");
        Call<UserPreferencesData> userPreferencesDataCall=userClient.userResendConfirmEmail(mToken);
        userPreferencesDataCall.enqueue(new Callback<UserPreferencesData>() {
            @Override
            public void onResponse(Call<UserPreferencesData> call, Response<UserPreferencesData> response) {
                if (response.body().getStatusCode()==252){
                    new CustomToast().Show_Toast(getContext(),mRootView,getString(R.string.info_252));
                }
                else {
                    new CustomToast().Show_Toast(getContext(),mRootView,getString(R.string.error_290));

                }
            }
            @Override
            public void onFailure(Call<UserPreferencesData> call, Throwable t) {
                new CustomToast().Show_Toast(getContext(),mRootView,getString(R.string.error_290));
            }
        });
    }
    private void checkConfrimEmail(){
        Utils.sharedPreferences=mSharedPreferences;
        UserClient userClient= ApiClient.getUserClient();
        mToken = Utils.getPref("token");
        Call<UserPreferencesData> userPreferencesDataCall=userClient.checkConfrimEmail(mToken);
        userPreferencesDataCall.enqueue(new Callback<UserPreferencesData>() {
            @Override
            public void onResponse(Call<UserPreferencesData> call, Response<UserPreferencesData> response) {
                 if (response.body().getStatusCode()==253){
                    new CustomToast().Show_Toast(getContext(),mRootView,getString(R.string.info_253));
                     ButtonActionPasif();
                } else if (response.body().getStatusCode()==200){
                   Utils.setSharedPreferences("active",1+"");
                   ButtonActionPasif();
                   startActivity(new Intent(getActivity(),MainActivity.class));
                   getActivity().finish();
                } else {
                    new CustomToast().Show_Toast(getContext(),mRootView,getString(R.string.error_290));
                    ButtonActionPasif();
                }
            }
            @Override
            public void onFailure(Call<UserPreferencesData> call, Throwable t) {
                new CustomToast().Show_Toast(getContext(),mRootView,getString(R.string.error_290));
                ButtonActionPasif();
            }
        });
    }


    private void ButtonActionActive() {
        mConfirmEmailValidate.setProgress(1);
    }

    private void ButtonActionPasif() {
        mConfirmEmailValidate.setProgress(0);
    }
    @Override
    public Animation onCreateAnimation(int transit, boolean enter, int nextAnim) {
        if (enter) {
            return MoveAnimation.create(MoveAnimation.UP, enter, 500);
        } else {
            return MoveAnimation.create(MoveAnimation.UP, enter, 500);
        }
    }
}