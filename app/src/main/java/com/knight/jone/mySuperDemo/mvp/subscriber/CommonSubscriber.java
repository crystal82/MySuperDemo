package com.knight.jone.mySuperDemo.mvp.subscriber;

import android.content.Context;

import com.knight.jone.mySuperDemo.exception.ApiException;
import com.knight.jone.mySuperDemo.mvp.base.BaseSubscriber;
import com.knight.jone.mySuperDemo.utils.Lg;
import com.knight.jone.mySuperDemo.utils.NetworkUtil;


/**
 * Created by gaosheng on 2016/11/6.
 * 22:42
 * com.example.gaosheng.myapplication.subscriber
 */

public abstract class CommonSubscriber<T> extends BaseSubscriber<T> {

    private Context context;

    public CommonSubscriber(Context context) {
        this.context = context;
    }

    private static final String TAG = "CommonSubscriber";

    @Override
    public void onStart() {

        if (!NetworkUtil.isNetworkAvailable(context)) {
            Lg.e(TAG, "网络不可用");
        } else {
            Lg.e(TAG, "网络可用");
        }
    }



    @Override
    protected void onError(ApiException e) {
        Lg.e(TAG, "错误信息为 " + "code:" + e.code + "   message:" + e.message);
    }

    @Override
    public void onCompleted() {
        Lg.e(TAG, "成功了");
    }

}
