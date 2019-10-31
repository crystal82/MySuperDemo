package com.knight.jone.mySuperDemo.handler;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.os.Handler;
import android.os.HandlerThread;
import android.util.Log;
import android.view.View;

import com.knight.jone.mySuperDemo.R;

public class HandlerTest extends AppCompatActivity {

    private Handler handler;
    private HandlerThread handlerThread;
    private int num = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_handler_test);
    }

    public void setHandler(View view) {
        handlerThread = new HandlerThread("HandlerThread_" + num++);
        handlerThread.start();
        handler = new Handler(handlerThread.getLooper());
    }

    public void setNull(View view) {
        if (handler == null) {
            Log.d("HandlerTest", "handler is null");
            return;
        }
        handler.post(new Runnable() {
            @Override
            public void run() {
                Log.d("HandlerTest", Thread.currentThread().getName() + "  handler post " + num);
            }
        });

        handlerThread = null;
        handler = null;
    }

    public void quitSafely(View view) {
        if (handler == null) {
            Log.d("HandlerTest", "handler is null");
            return;
        }
        handler.post(new Runnable() {
            @Override
            public void run() {
                Log.d("HandlerTest", Thread.currentThread().getName() + "  handler post " + num);
            }
        });

        handlerThread.quitSafely();
        handlerThread = null;
        handler = null;
    }

    public void quit(View view) {
        if (handler == null) {
            Log.d("HandlerTest", "handler is null");
            return;
        }
        handler.post(new Runnable() {
            @Override
            public void run() {
                Log.d("HandlerTest", Thread.currentThread().getName() + "  handler post " + num);
            }
        });

        handlerThread.quit();
        handlerThread = null;
        handler = null;
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Log.d("onDestroy", "onDestroy");
    }
}
