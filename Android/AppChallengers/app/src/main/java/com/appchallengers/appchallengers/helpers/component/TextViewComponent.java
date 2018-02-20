package com.appchallengers.appchallengers.helpers.component;


import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Typeface;
import android.os.Build;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.util.AttributeSet;
import android.widget.TextView;

/**
 * Created by jir on 14.2.2018.
 */

@SuppressLint("AppCompatCustomView")
public class TextViewComponent extends TextView {
    private Context mContext;
    public TextViewComponent(Context context) {
        super(context);
        mContext=context;
        initialView();
    }

    public TextViewComponent(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        mContext=context;
        initialView();
    }

    public TextViewComponent(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        mContext=context;
        initialView();
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    public TextViewComponent(Context context, @Nullable AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        mContext=context;
        initialView();
    }

    public void initialView(){
        Typeface font = Typeface.createFromAsset(mContext.getAssets(), "fonts/roboto_medium.ttf");
        setTypeface(font);
    }
}
