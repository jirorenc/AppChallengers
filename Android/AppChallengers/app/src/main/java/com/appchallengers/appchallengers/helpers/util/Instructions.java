package com.appchallengers.appchallengers.helpers.util;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;

/**
 * Created by jir on 20.2.2018.
 */

public class Instructions {
    public static Instructions mInstructions = null;
    public static final Object lock = new Object();

    private Instructions() {

    }

    public static Instructions getInstance() {
        if (mInstructions == null) {
            synchronized (lock) {
                if (mInstructions == null)
                    return new Instructions();
            }
        }
        return mInstructions;
    }

    public void constructor(Context context,String url){
        Uri uri=Uri.parse(url);
        Intent intent = new Intent();
        intent.setData(uri);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        context.startActivity(intent);
    }

}
