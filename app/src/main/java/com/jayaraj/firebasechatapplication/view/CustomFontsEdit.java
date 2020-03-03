package com.jayaraj.firebasechatapplication.view;


import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Typeface;
import android.util.AttributeSet;

import com.jayaraj.firebasechatapplication.R;


public class CustomFontsEdit extends android.support.v7.widget.AppCompatEditText  {
    public CustomFontsEdit(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        init();
    }
    public CustomFontsEdit(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }
    public CustomFontsEdit(Context context) {
        super(context);
        init();
    }
    @SuppressLint("WrongConstant")
    public void init() {
        Typeface tf = Typeface.createFromAsset(getContext().getAssets(), getResources().getString(R.string.font_family));
        setTypeface(tf ,1);
    }
}
