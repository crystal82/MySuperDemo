package com.knight.jone.mySuperDemo.viewEvent;

import android.content.Context;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.widget.TextView;

import com.knight.jone.mySuperDemo.utils.Lg;

public class EventTextView extends android.support.v7.widget.AppCompatTextView {

    public EventTextView(Context context) {
        super(context);
    }

    public EventTextView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public EventTextView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        Lg.d("onTouchEvent:" + event.getAction());
        return super.onTouchEvent(event);
    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent event) {
        Lg.d("dispatchTouchEvent:" + event.getAction());
        return super.dispatchTouchEvent(event);
    }
}
