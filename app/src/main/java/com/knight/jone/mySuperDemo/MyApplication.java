package com.knight.jone.mySuperDemo;

import android.app.Application;
import android.os.Handler;
import android.os.Looper;

import com.knight.jone.mySuperDemo.utils.Cockroach;
import com.knight.jone.mySuperDemo.utils.Lg;

/**
 * 作者：HWQ on 2017/11/29 15:33
 * 描述：
 */

public class MyApplication  extends Application {
    public static MyApplication mInstance;

    @Override
    public void onCreate() {
        super.onCreate();
        mInstance = this;

        //initCockroach();
    }

    public static MyApplication getInstance() {
        return mInstance;
    }

    //异常捕获，防崩溃处理
    private void initCockroach() {
        Cockroach.install(new Cockroach.ExceptionHandler() {
            @Override
            public void handlerException(final Thread thread, final Throwable throwable) {
                new Handler(Looper.getMainLooper()).post(new Runnable() {
                    @Override
                    public void run() {
                        try {
                            throwable.printStackTrace();
                            Lg.d("Exception Happend\n" + thread + "\n" + throwable.toString());
                        } catch (Throwable e) {

                        }
                    }
                });
            }
        });
    }
}
