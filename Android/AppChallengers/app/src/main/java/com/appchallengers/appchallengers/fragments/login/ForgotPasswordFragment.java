package com.appchallengers.appchallengers.fragments.login;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.appchallengers.appchallengers.R;
import com.appchallengers.appchallengers.helpers.setpages.SetLoginPages;
import com.appchallengers.appchallengers.helpers.util.Utils;
import com.dd.processbutton.iml.ActionProcessButton;


public class ForgotPasswordFragment extends Fragment  implements  View.OnClickListener{
    private ImageView mForgotBackArrow;
    private View mRootView;
    private EditText mFortgotEmailEdit;
    private LinearLayout mForgotLinerLayout;
    private Animation mForgotShakeAnimation;
    private ActionProcessButton mForgotSendButton;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        mRootView= inflater.inflate(R.layout.fragment_forgot_password, container, false);
        initalView(mRootView);
        return mRootView;
    }
    private void initalView(View view){
        mForgotBackArrow=(ImageView) view.findViewById(R.id.forgot_password_fragment_back_arrow_imageview);
        mForgotSendButton=(ActionProcessButton) view.findViewById(R.id.forgot_password_fragment_forgot_send_button);
        mForgotLinerLayout=(LinearLayout)view.findViewById(R.id.forgot_password_fragment_ll);
        mFortgotEmailEdit=(EditText) view.findViewById(R.id.forgot_password_fragment_email_edittext);
        mForgotShakeAnimation=AnimationUtils.loadAnimation(getActivity(), R.anim.shake);
        mForgotSendButton.setMode(ActionProcessButton.Mode.ENDLESS);
        mForgotBackArrow.setOnClickListener(this);
        mForgotSendButton.setOnClickListener(this);

    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.forgot_password_fragment_back_arrow_imageview :{
                SetLoginPages.getInstance().constructor(getActivity(),0);
                break;
            }
            case R.id.forgot_password_fragment_forgot_send_button:{

                if(checkValidation()){
                    ButtonActionActive();
                   SetLoginPages.getInstance().constructor(getActivity(),2);
                }
                break;

            }
        }

    }
    private boolean checkValidation() {
        String email = mFortgotEmailEdit.getText().toString();
        return Utils.checkValidation(new String[]{email},mForgotLinerLayout,mForgotShakeAnimation,getActivity(),mRootView);
    }
    private void ButtonActionActive(){
        mForgotSendButton.setProgress(1);
    }
    private void ButtonActionPasif(){
        mForgotSendButton.setProgress(0);
    }
}
