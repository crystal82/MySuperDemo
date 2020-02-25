package com.knight.jone.mySuperDemo.net.okhttp;

import android.util.Log;

import java.io.IOException;

import okhttp3.Interceptor;
import okhttp3.MediaType;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.ResponseBody;

/**
 * 作者：HWQ on 2018/2/28 17:13
 * 描述：
 */

public class OkLoggingInterceptor implements Interceptor {
    private static final String TAG="Okhttp";
    @Override public Response intercept(Interceptor.Chain chain) throws IOException {
        Request request = chain.request();

        long t1 = System.nanoTime();
        Log.d(TAG, String.format("Sending request %s on %s%n%s",
                                 request.url(), chain.connection(), request.headers()));

        Response response = chain.proceed(request);
        long t2 = System.nanoTime();
        Log.d(TAG,String.format("Received response for %s in %.1fms%n%s",
                                response.request().url(), (t2 - t1) / 1e6d, response.headers()));

        MediaType mediaType = response.body().contentType();
        String    content   = response.body().string();
        Log.d(TAG,content);

        response=response.newBuilder()
                .body(ResponseBody.create(mediaType, content))
                .build();
        return response;
    }
}