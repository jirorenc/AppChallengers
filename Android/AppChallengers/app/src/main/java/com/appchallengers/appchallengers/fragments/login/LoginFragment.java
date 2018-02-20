package com.appchallengers.appchallengers.fragments.login;


import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.EditText;
import android.support.v4.app.Fragment;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.appchallengers.appchallengers.MainActivity;
import com.appchallengers.appchallengers.R;
import com.appchallengers.appchallengers.helpers.setpages.SetLoginPages;
import com.appchallengers.appchallengers.helpers.util.Constants;
import com.appchallengers.appchallengers.helpers.util.CustomToast;
import com.appchallengers.appchallengers.helpers.util.Instructions;
import com.appchallengers.appchallengers.helpers.util.Utils;
import com.appchallengers.appchallengers.webservice.remote.ApiClient;
import com.appchallengers.appchallengers.webservice.remote.UserClient;
import com.appchallengers.appchallengers.webservice.response.UserPreferencesData;
import com.appchallengers.appchallengers.webservice.request.UsersLoginRequestModel;
import com.dd.processbutton.iml.ActionProcessButton;
import com.labo.kaji.fragmentanimations.CubeAnimation;
import com.labo.kaji.fragmentanimations.MoveAnimation;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static android.content.Context.MODE_PRIVATE;
import static com.appchallengers.appchallengers.helpers.util.Constants.MY_PREFS_NAME;


public class LoginFragment extends Fragment implements View.OnClickListener {
    private View mRootView;
    private EditText mLoginFragmentUserEmail;
    private EditText mLoginFragmentPassword;
    private ActionProcessButton mLoginFragmentLogin;
    private TextView mLoginFragmentForgotPassword;
    private TextView mLoginFragmentSingUp;
    private LinearLayout mLinearLayout;
    private Animation mShakeAnimation;
    private SharedPreferences mSharedPreferences;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        mRootView = inflater.inflate(R.layout.fragment_login, container, false);
        initialView(mRootView);

        return mRootView;
    }

    private void initialView(View rootView) {
        mLoginFragmentUserEmail=(EditText)rootView.findViewById(R.id.login_fragment_email_edittext);
        mLoginFragmentPassword=(EditText)rootView.findViewById(R.id.login_fragment_password_edittext);
        mLoginFragmentLogin=(ActionProcessButton) rootView.findViewById(R.id.login_fragment_login_button);
        mLoginFragmentForgotPassword=(TextView) rootView.findViewById(R.id.login_fragment_forgot_password_textview);
        mLoginFragmentSingUp=(TextView) rootView.findViewById(R.id.login_fragment_signup_link_textview);
        mLinearLayout=(LinearLayout) rootView.findViewById(R.id.login_fragment_login_image_ll);
        mShakeAnimation=AnimationUtils.loadAnimation(getActivity(), R.anim.shake);
        mSharedPreferences=getActivity().getSharedPreferences(MY_PREFS_NAME, MODE_PRIVATE);
        mLoginFragmentLogin.setMode(ActionProcessButton.Mode.ENDLESS);
        mLoginFragmentSingUp.setOnClickListener(this);
        mLoginFragmentLogin.setOnClickListener(this);
        mLoginFragmentForgotPassword.setOnClickListener(this);
        controlAuthorization();
    }
    @Override
    public void onClick(View view) {
        switch (view.getId()){

            case R.id.login_fragment_signup_link_textview:{
                SetLoginPages.getInstance().constructor(getActivity(),1);
                break;
            }  case R.id.login_fragment_forgot_password_textview:{
                Instructions.getInstance().constructor(getContext(), Constants.URL_FORGOT_PASSWORD);
                break;
            }
            case R.id.login_fragment_login_button:{
                if(checkValidation()){
                    ButtonActionActive();
                    usersLogin();
                }
                break;
            }

        }
    }
    private void usersLogin() {
        UserClient userClient= ApiClient.getUserClient();
        String email = mLoginFragmentUserEmail.getText().toString();
        String password = mLoginFragmentPassword.getText().toString();
        Call<UserPreferencesData> userPreferencesDataCall=userClient.usersLogin(new UsersLoginRequestModel(email,password));
        userPreferencesDataCall.enqueue(new Callback<UserPreferencesData>() {
            @Override
            public void onResponse(Call<UserPreferencesData> call, Response<UserPreferencesData> response) {
                if (response.body().getStatusCode()==200){
                    Utils.sharedPreferences = mSharedPreferences;
                    Utils.setSharedPreferences("token",response.body().getToken());
                    Utils.setSharedPreferences("id",response.body().getId()+"");
                    Utils.setSharedPreferences("email",response.body().getEmail());
                    Utils.setSharedPreferences("active",response.body().getActive()+"");
                    ButtonActionPasif();
                    if(response.body().getActive()==0){
                        SetLoginPages.getInstance().constructor(getActivity(),2);
                    }
                    else if(response.body().getActive()==1){
                        startActivity(new Intent(getActivity(), MainActivity.class));
                        getActivity().finish();
                    }

                }else if(response.body().getStatusCode()==251){
                    new CustomToast().Show_Toast(getContext(),mRootView,getString(R.string.error_251));
                    ButtonActionPasif();
                }else {
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
    private void controlAuthorization(){
        Utils.sharedPreferences=mSharedPreferences;
      String control= Utils.getPref("token");
        if (control.equals("") || control == null || control.equals("0")) {

        }
        else{
            SetLoginPages.getInstance().constructor(getActivity(),2);
        }
    }
    private boolean checkValidation() {
        String email = mLoginFragmentUserEmail.getText().toString();
        String password = mLoginFragmentPassword.getText().toString();
        return Utils.checkValidation(new String[]{email,password},mLinearLayout,mShakeAnimation,getActivity(),mRootView);
    }

    private void ButtonActionActive() {
        mLoginFragmentLogin.setProgress(1);
    }
    private void ButtonActionPasif() {
        mLoginFragmentLogin.setProgress(0);
    }
    @Override
    public Animation onCreateAnimation(int transit, boolean enter, int nextAnim) {
        if (enter) {
            return MoveAnimation.create(MoveAnimation.RIGHT, enter, 500);
        } else {
            return MoveAnimation.create(MoveAnimation.LEFT, enter, 500);
        }
    }

}
