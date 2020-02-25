package com.knight.jone.mySuperDemo.net;

import android.os.Bundle;
import android.os.Environment;
import androidx.appcompat.app.AppCompatActivity;
import android.util.Log;
import android.widget.Button;

import com.knight.jone.mySuperDemo.MyApplication;
import com.knight.jone.mySuperDemo.R;
import com.knight.jone.mySuperDemo.utils.Lg;

import java.io.File;
import java.io.IOException;
import java.util.concurrent.TimeUnit;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import okhttp3.Cache;
import okhttp3.CacheControl;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class OkhttpCacheTest extends AppCompatActivity {

    @BindView(R.id.btn1)
    Button mBtn1;
    @BindView(R.id.btn2)
    Button mBtn2;
    private CacheControl mCacheControl;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_okhttp_cache_test);
        ButterKnife.bind(this);
    }


    /**
     * 无论有无网络都添加缓存
     */
    Interceptor netInterceptor = new Interceptor() {
        @Override
        public Response intercept(Chain chain) throws IOException {
            Request  request  = chain.request();
            Response response = chain.proceed(request);
            int      maxAge   = 60;
            return response.newBuilder()
                    .removeHeader("Pragma")// 清除头信息，因为服务器如果不支持，会返回一些干扰信息，不清除下面无法生效
                    .removeHeader("Cache-Control")
                    .header("Cache-Control", "public, max-age=" + maxAge)
                    .build();
        }
    };

    public OkHttpClient saveCache() {
        File cacheFile = new File(MyApplication.getInstance().getCacheDir(), "caheData");
        //设置缓存大小
        Cache cache = new Cache(cacheFile, 1024 * 1024 * 1024);//google建议放到这里
        OkHttpClient client = new OkHttpClient.Builder()
                .retryOnConnectionFailure(true)//连接失败后是否重新连接
                .connectTimeout(15, TimeUnit.SECONDS)//超时时间15S
                .addNetworkInterceptor(netInterceptor)//这里大家一定要注意了是addNetworkOnterceptor别搞错了啊。
                .cache(cache)
                .build();
        return client;
    }

    public class CacheInterceptor implements Interceptor {
        @Override
        public Response intercept(Chain chain) throws IOException {
            Request request = chain.request();//获取请求
            //这里就是说判读我们的网络条件，要是有网络的话我么就直接获取网络上面的数据，要是没有网络的话我么就去缓存里面取数据
            if (!NetWorkUtils.isNetWorkAvailable(MyApplication.getInstance().getApplicationContext())) {
                request = request.newBuilder()
                        //这个的话内容有点多啊，大家记住这么写就是只从缓存取，想要了解这个东西我等下在
                        // 给大家写连接吧。大家可以去看下，获取大家去找拦截器资料的时候就可以看到这个方面的东西反正也就是缓存策略。
                        .cacheControl(CacheControl.FORCE_CACHE)
                        .build();
                Log.d("CacheInterceptor", "no network");
            }
            Response originalResponse = chain.proceed(request);
            if (NetWorkUtils.isNetWorkAvailable(MyApplication.getInstance().getApplicationContext())) {
                //这里大家看点开源码看看.header .removeHeader做了什么操作很简答，就是的加字段和减字段的。
                String cacheControl = request.cacheControl().toString();
                return originalResponse.newBuilder()
                        //这里设置的为0就是说不进行缓存，我们也可以设置缓存时间
                        .header("Cache-Control", "public, max-age=" + 0)
                        .removeHeader("Pragma")
                        .build();
            } else {
                int maxTime = 4 * 24 * 60 * 60;
                return originalResponse.newBuilder()
                        //这里的设置的是我们的没有网络的缓存时间，想设置多少就是多少。
                        .header("Cache-Control", "public, only-if-cached, max-stale=" + maxTime)
                        .removeHeader("Pragma")
                        .build();
            }
        }

    }

    @OnClick(R.id.btn1)
    public void onViewClicked() {
        OkHttpClient okHttpClient = saveCache();
        Request request = new Request.Builder()
                .url("http://www.baidu.com")
                .build();
        okHttpClient.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {

            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                Lg.d("返回结果：" + response.body().string());
            }
        });
    }

    Interceptor btn2Interceptor = new Interceptor() {

        @Override
        public Response intercept(Chain chain) throws IOException {
            Request request = chain.request();
            //网络不可使用，直接拿缓存
            if (!NetWorkUtils.isNetWorkAvailable(MyApplication.getInstance())) {
                request = request.newBuilder()
                        .cacheControl(mCacheControl)
                        .build();
            }

            Response originalResponse = chain.proceed(request);
            if (NetWorkUtils.isNetWorkAvailable(MyApplication.getInstance())) {
                int maxAge = 60; // read from cache
                return originalResponse.newBuilder()
                        .removeHeader("Pragma")
                        .header("Cache-Control", "public ,max-age=" + maxAge)
                        .build();
            } else {
                int maxStale = 60 * 60 * 24 * 28; // tolerate 4-weeks stale
                return originalResponse.newBuilder()
                        .removeHeader("Pragma")
                        .header("Cache-Control", "public, only-if-cached, max-stale=" + maxStale)
                        .build();
            }
        }
    };

    @OnClick(R.id.btn2)
    public void onViewClicked2() {
        mCacheControl = new CacheControl.Builder()
                .maxAge(0, TimeUnit.SECONDS) //.maxAge(0,TimeUnit.SECONDS)设置的时间比拦截器长是不起效果
                .maxStale(365, TimeUnit.DAYS)
                .build();
    }


    private static final long   cacheSize      = 1024 * 1024 * 20;// 缓存文件最大限制大小20M
    private static       String cacheDirectory = Environment.getExternalStorageDirectory() + "/okttpcaches"; // 设置缓存文件路径
    private static       Cache  cache          = new Cache(new File(cacheDirectory), cacheSize);  //

    static {
        //如果无法生存缓存文件目录，检测权限使用已经加上，检测手机是否把文件读写权限禁止了
        OkHttpClient.Builder builder = new OkHttpClient.Builder();
        builder.connectTimeout(8, TimeUnit.SECONDS); // 设置连接超时时间
        builder.writeTimeout(8, TimeUnit.SECONDS);// 设置写入超时时间
        builder.readTimeout(8, TimeUnit.SECONDS);// 设置读取数据超时时间
        builder.retryOnConnectionFailure(true);// 设置进行连接失败重试
        builder.cache(cache);// 设置缓存
        OkHttpClient httpClient = builder.build();
    }

}
