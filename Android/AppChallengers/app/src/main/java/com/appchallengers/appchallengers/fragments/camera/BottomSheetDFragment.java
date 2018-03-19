package com.appchallengers.appchallengers.fragments.camera;


import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.support.design.widget.BottomSheetDialogFragment;
import android.widget.Toast;

import com.appchallengers.appchallengers.helpers.util.ConnectivityReceiver;
import com.appchallengers.appchallengers.R;

import br.com.simplepass.loading_button_lib.customViews.CircularProgressButton;


public class BottomSheetDFragment extends BottomSheetDialogFragment implements View.OnClickListener {
    private View mRootView;
    private EditText mHeaderChallengersText;
    private CircularProgressButton mChallengeButtonSheet;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        mRootView = inflater.inflate(R.layout.fragment_bottom_sheet_dialog, container, false);
        initialView(mRootView);
        return mRootView;
    }

    private void initialView(View mRootView) {
        mHeaderChallengersText=(EditText) mRootView.findViewById(R.id.bottom_sheet_dialog_fragment_header_edittext);
        mChallengeButtonSheet=(CircularProgressButton)mRootView.findViewById(R.id.bottom_sheet_dialog_fragment_challenge_button);
        mChallengeButtonSheet.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.bottom_sheet_dialog_fragment_challenge_button:{
                checkConnection();
                break;
            }
        }
    }
    public void checkConnection() {
        boolean isConnected = ConnectivityReceiver.isConnected();
        showSnack(isConnected);
    }
    private void showSnack(boolean isConnected) {
        String message;
        int color;
        if (!isConnected) {
            message = "Sorry! Not connected to internet";
            color = Color.WHITE;
            Toast.makeText(getContext(),message, Toast.LENGTH_SHORT).show();
            /*Snackbar snackbar = Snackbar
                    .make(mHeaderChallengersText, message, Snackbar.LENGTH_LONG);
            View sbView = snackbar.getView();
            TextView textView = (TextView) sbView.findViewById(android.support.design.R.id.snackbar_text);
            textView.setTextColor(color);
            snackbar.show();*/
        } else {

        }
    }
     public String getEdittextText(){
        return mHeaderChallengersText.getText().toString();
     }
}
