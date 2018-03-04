package com.appchallengers.appchallengers.helpers.util;

import android.content.Context;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.view.View;
import android.view.animation.Animation;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.Toast;

import java.net.InetAddress;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


public class Utils {
    public static final String regEx = "\\b[A-Za-z0-9._%+-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,4}\\b";

    public static SharedPreferences sharedPreferences;

    public static void setSharedPreferences(String tag, String value) {
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(tag, value);
        editor.commit();
    }
    public static void setSharedPreferencesBoolean(String tag, boolean value) {
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putBoolean(tag, value);
        editor.commit();
    }

    public static String getPref(String tag) {
        String value = null;

        try {
            value = sharedPreferences.getString(tag, null).toString();
        } catch (Exception ex) {

        }
        return value;
    }public static boolean getPrefBoolean(String tag) {
        return sharedPreferences.getBoolean(tag,true);
    }



    public static boolean checkValidation(String[] components,
                                          LinearLayout linearLayout,
                                          Animation shakeAnimation,
                                          Context context,
                                          View view) {
        Pattern p = Pattern.compile(Utils.regEx);
        Matcher m = p.matcher(components[0]);
        for (String component:components){
            if (component.equals("") || component.length() == 0) {
                linearLayout.startAnimation(shakeAnimation);
                new CustomToast().Show_Toast(context, view,
                        "There are empty spaces");
                return false;
            }
        }
        if (!m.find()) {
            new CustomToast().Show_Toast(context, view,
                    "Your Email  is Invalid.");
            return false;
        }
        return true;

    }
}
