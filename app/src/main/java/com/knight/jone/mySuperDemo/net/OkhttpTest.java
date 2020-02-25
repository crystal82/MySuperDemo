package com.knight.jone.mySuperDemo.net;

import android.content.Context;
import android.os.Handler;

import org.jetbrains.annotations.NotNull;

import java.io.File;
import java.io.IOException;
import java.util.concurrent.TimeUnit;

import okhttp3.Cache;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class OkhttpTest {

    private static OkhttpTest instance;
    private Context context;
    private final OkHttpClient okHttpClient;
    private final Handler handler;

    public abstract class ResultCallback {
        public abstract void onError(Call call, IOException e);

        public abstract void onSuccess(Call call, Response response);
    }

    public static OkhttpTest getInstance(Context context) {
        if (instance == null) {
            synchronized (OkhttpTest.class) {
                instance = new OkhttpTest(context);
            }
        }
        return instance;
    }

    private OkhttpTest(Context context) {
        this.context = context;
        int cacheSize = 10 * 1024 * 1024;

        File sdCache = context.getExternalCacheDir();
        OkHttpClient.Builder builder = new OkHttpClient.Builder()
                .connectTimeout(15, TimeUnit.SECONDS)
                .writeTimeout(20, TimeUnit.SECONDS)
                .readTimeout(20, TimeUnit.SECONDS)
                .cache(new Cache(sdCache.getAbsoluteFile(), cacheSize));
        okHttpClient = builder.build();
        handler = new Handler();
    }

    public void getAsynHttp(String url, ResultCallback callback) {

        Request request = new Request.Builder()
                .url(url)
                .build();
        Call call = okHttpClient.newCall(request);

        call.enqueue(new Callback() {
            @Override
            public void onFailure(@NotNull Call call, @NotNull IOException e) {
                handler.post(new Runnable() {
                    @Override
                    public void run() {
                        callback.onError(call, e);
                    }
                });
            }

            @Override
            public void onResponse(@NotNull Call call, @NotNull Response response) throws IOException {
                handler.post(new Runnable() {
                    @Override
                    public void run() {
                        callback.onSuccess(call, response);
                    }
                });
            }
        });
    }


    public void simplePost(String url) {
        OkHttpClient okHttpClient = new OkHttpClient();
        FormBody formBody = new FormBody.Builder().add("name", "aaa").build();
        Request request = new Request.Builder()
                .url(url)
                .post(formBody)
                .build();

        Call call = okHttpClient.newCall(request);
        call.enqueue(new Callback() {
            @Override
            public void onFailure(@NotNull Call call, @NotNull IOException e) {

            }

            @Override
            public void onResponse(@NotNull Call call, @NotNull Response response) throws IOException {

            }
        });
    }
}
