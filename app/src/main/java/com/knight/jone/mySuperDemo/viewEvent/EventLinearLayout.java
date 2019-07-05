package com.knight.jone.mySuperDemo.viewEvent;

import android.content.Context;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.widget.LinearLayout;
import android.widget.ScrollView;

import com.knight.jone.mySuperDemo.utils.Lg;

public class EventLinearLayout extends LinearLayout {

    public EventLinearLayout(Context context) {
        super(context);
    }

    public EventLinearLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public EventLinearLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    public EventLinearLayout(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        Lg.d("EventLinearLayout onTouchEvent:" + ev.getAction());
        return super.dispatchTouchEvent(ev);
    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {
        Lg.d("EventLinearLayout onInterceptTouchEvent:" + ev.getAction());
        return super.onInterceptTouchEvent(ev);
    }

    @Override
    public boolean onTouchEvent(MotionEvent ev) {
        Lg.d("EventLinearLayout onTouchEvent:" + ev.getAction());
        return super.onTouchEvent(ev);
    }
}
