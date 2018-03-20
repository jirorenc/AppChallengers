package com.appchallengers.appchallengers.helpers.util;

import android.graphics.Color;
import android.support.design.widget.Snackbar;
import android.view.View;
import android.widget.TextView;

import android.widget.FrameLayout;

public class InternetControl {
    public static InternetControl internetControl = null;
    public static Object object = new Object();

    public boolean InternetControl(){
        if(!ConnectivityReceiver.isConnected()){
            return false;
        }
        else {
            return true;
        }

    }

    public static InternetControl getInstance() {
        if (internetControl == null) {
            synchronized (object) {
                if (internetControl == null) {
                    return internetControl = new InternetControl();
                }
            }
        }
        return internetControl;
    }

    public void showSnack(View view) {
        String message;
        int color;
        if (!ConnectivityReceiver.isConnected()) {
            message = "Sorry! Not connected to internet";
            color = Color.WHITE;
            Snackbar snackbar = Snackbar
                    .make(view, message, Snackbar.LENGTH_LONG);
            View sbView = snackbar.getView();
            TextView textView = (TextView) sbView.findViewById(android.support.design.R.id.snackbar_text);
            textView.setTextColor(color);
            snackbar.show();

        } else {

        }

    }
    public void showSnackGeneral(FrameLayout frameLayout, boolean isConnected) {
        String message;
        int color;
        if (!isConnected) {
            message = "Sorry! Not connected to internet";
            color = Color.WHITE;
            Snackbar snackbar = Snackbar
                    .make(frameLayout, message, Snackbar.LENGTH_LONG);
            View sbView = snackbar.getView();
            TextView textView = (TextView) sbView.findViewById(android.support.design.R.id.snackbar_text);
            textView.setTextColor(color);
            snackbar.show();
        } else {

        }

    }
}
