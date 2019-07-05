package com.knight.jone.mySuperDemo.netRequest;

import android.app.Activity;
import android.os.Bundle;
import android.os.Environment;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

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
import okhttp3.FormBody;
import okhttp3.HttpUrl;
import okhttp3.Interceptor;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
import okhttp3.ResponseBody;

public class OkhttpTutorial extends Activity {
    @BindView(R.id.textView)
    TextView mTextView;
    @BindView(R.id.getButton)
    Button   mGetButton;
    @BindView(R.id.postButton)
    Button   mPostButton;
    @BindView(R.id.interceptButton)
    Button   mInterceptButton;
    private OkHttpClient mOkHttpClient;

    Interceptor appInterceptor = new Interceptor() {
        @Override
        public Response intercept(Chain chain) throws IOException {
            Request request = chain.request();
            HttpUrl url     = request.url();
            String  s       = url.url().toString();
            //---------请求之前-----
            Lg.d("app interceptor:begin:" + s);
            Response response = chain.proceed(request);
            Lg.d("app interceptor:end");
            //---------请求之后------------
            return response;
        }
    };

    Interceptor netInterceptor = new Interceptor() {
        @Override
        public Response intercept(Chain chain) throws IOException {
            Request request = chain.request();
            //---------请求之前-----
            Lg.d("network interceptor:begin");
            Response response = chain.proceed(request);
            Lg.d("network interceptor:end");

            return response;
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_okhttp_tutorial);
        ButterKnife.bind(this);


    }

    @Override
    protected void onResume() {
        super.onResume();
    }


    public void cacheTest(){
        CacheControl cacheControl = new CacheControl.Builder()
                .maxAge(10, TimeUnit.SECONDS)//最大缓存时间
                .maxStale(365, TimeUnit.DAYS)//缓存过时时间
                .build();
    }

    public void test(){
        //cache url
        File httpCacheDirectory = new File(getApplicationContext().getExternalCacheDir()+"/cache", "responses");
        int cacheSize = 10 * 1024 * 1024; // 10 MiB
        Cache cache = new Cache(httpCacheDirectory, cacheSize);

        Request request = new Request.Builder().cacheControl(CacheControl.FORCE_NETWORK).build();
        request = request.newBuilder()
                .cacheControl(CacheControl.FORCE_CACHE)
                .build();
    }

    public void jointUrl() {
        HttpUrl httpUrl = new HttpUrl.Builder()
                .scheme("http")
                .host("www.baidu.com")
                .addPathSegment("user")
                .addPathSegment("login")
                .addQueryParameter("username", "zhangsan")
                .addQueryParameter("password", "123456")
                .build();
        //拼接结果：http://www.baidu.com/user/login/username=zhangsan&password=123456
    }

    public void initOkhttp() {
        OkHttpClient okHttpClient = new OkHttpClient.Builder().build();

        File         cacheDir     = new File(getCacheDir(), "okhttp_cache");
        //File cacheDir = new File(getExternalCacheDir(), "okhttp");
        Cache cache = new Cache(cacheDir, 10 * 1024 * 1024);

        okHttpClient = new OkHttpClient.Builder()
                .connectTimeout(5 * 1000, TimeUnit.MILLISECONDS) //链接超时
                .readTimeout(10 * 1000, TimeUnit.MILLISECONDS) //读取超时
                .writeTimeout(10 * 1000, TimeUnit.MILLISECONDS) //写入超时
                //.addInterceptor(new HttpHeadInterceptor()) //应用拦截器：统一添加消息头
                //.addNetworkInterceptor(new NetworkspaceInterceptor())//网络拦截器
                //.addInterceptor(loggingInterceptor)//应用拦截器：打印日志
                .cache(cache)  //设置缓存
                .build();
    }

    @OnClick({R.id.getButton, R.id.postButton, R.id.interceptButton})
    public void click(View view) {
        if (mOkHttpClient == null) {
            //1.初始化Okhttp对象，可以重复使用
            mOkHttpClient = new OkHttpClient.Builder().build();
        }

        switch (view.getId()) {
            case R.id.interceptButton:
                OkHttpClient build = mOkHttpClient.newBuilder()
                        .addInterceptor(appInterceptor)
                        .addNetworkInterceptor(netInterceptor)
                        .build();
                Request request = new Request.Builder()
                        .url("http://www.baidu.com")
                        .build();
                build.newCall(request).enqueue(new Callback() {
                    @Override
                    public void onFailure(Call call, IOException e) {

                    }

                    @Override
                    public void onResponse(Call call, Response response) throws IOException {
                        Lg.d("---Interceptor测试-----");
                    }
                });
                break;
            case R.id.getButton:
                Lg.d("----点击----");
                //doBaiduRequest();
                //doQqRequest();
                //cacheRequest();
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        try {
                            execute();
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                }).start();
                break;

            case R.id.postButton:
                postData();
                postRequst();
                break;
            default:
        }
    }

    private void postData() {
        //2.1 获取文件
        File file = new File(Environment.getExternalStorageDirectory() + "test.txt");
        //2.2 创建 MediaType 设置上传文件类型
        MediaType mediatype = MediaType.parse("text/plain; charset=utf-8");
        //2.3 获取请求体
        RequestBody requestBody = RequestBody.create(mediatype, file);
        Request build = new Request.Builder().url("http://www.baidu.com")
                .post(requestBody)
                .build();
        Call call = mOkHttpClient.newCall(build);
        call.cancel();
        call.enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {

            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {

            }
        });
    }

    private void postRequst() {
        FormBody formBody = new FormBody.Builder()
                .add("name", "ad")
                .build();
        Request request = new Request.Builder()
                .post(formBody)
                .url("http://baidu.com")
                .build();

        mOkHttpClient.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {

            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                Lg.d("postResponse1:" + response.body().string());
            }
        });
    }

    private void doQqRequest() {
        Request.Builder requestBuild = new Request.Builder().url("http://www.qq.com");
        Call            call         = mOkHttpClient.newCall(requestBuild.build());
        call.enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {

            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                ResponseBody body = response.body();
                Lg.d("doQqRequest:" + body.string());
            }
        });
    }

    public void doBaiduRequest() {
        //2.创建请求对象
        Request.Builder builder = new Request
                .Builder()
                .get() //builder.method("GET", null); //默认Get
                .url("http://www.baidu.com");

        //3.创建Call对象，用于发起请求
        Call call = mOkHttpClient.newCall(builder.build());

        //同步请求，耗时操作在子线程
        doExecuteSyncRequest(call);

        //异步
        doEnqueueAsyncRequest(call);
    }

    private void doEnqueueAsyncRequest(Call call) {
        call.enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {

            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                ResponseBody body = response.body();
                Lg.d("doBaiduRequest:" + body.string());
            }
        });
    }

    private void doExecuteSyncRequest(final Call call) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    Response execute = call.execute();
                    Lg.d("execute:" + execute.body().string());
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }).start();
    }

    private void cacheRequest() {
        Request build = new Request.Builder()
                .get()
                .url("http://www.baidu.com")
                .build();

        Cache cache = new Cache(getExternalCacheDir(), 10 * 1024 * 1024);
        mOkHttpClient.newBuilder().cache(cache);

        Call call = mOkHttpClient.newCall(build);
        call.enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {

            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                Lg.d("response111:" + response);
                Lg.d("cacheResponse111:" + response.cacheResponse());
                Lg.d("networkResponse111:" + response.networkResponse());
            }
        });

        Call call2 = mOkHttpClient.newCall(build);
        call2.enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {

            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                Lg.d("response222:" + response);
                Lg.d("cacheResponse222:" + response.cacheResponse());
                Lg.d("networkResponse222:" + response.networkResponse());
            }
        });
    }

    public void execute() throws Exception {
        Request request = new Request.Builder()
                .url("http://publicobject.com/helloworld.txt")
                .build();

        Response response1 = mOkHttpClient.newCall(request).execute();
        if (!response1.isSuccessful()) {
            throw new IOException("Unexpected code " + response1);
        }

        String response1Body = response1.body().string();
        System.out.println("Response 1 response:          " + response1);
        System.out.println("Response 1 cache response:    " + response1.cacheResponse());
        System.out.println("Response 1 network response:  " + response1.networkResponse());

        Response response2 = mOkHttpClient.newCall(request).execute();
        if (!response2.isSuccessful()) {
            throw new IOException("Unexpected code " + response2);
        }

        String response2Body = response2.body().string();
        System.out.println("Response 2 response:          " + response2);
        System.out.println("Response 2 cache response:    " + response2.cacheResponse());
        System.out.println("Response 2 network response:  " + response2.networkResponse());

        System.out.println("Response 2 equals Response 1? " + response1Body.equals(response2Body));

    }
}
//class GetExample {
//    OkHttpClient client = new OkHttpClient();

//    String run(String url) throws IOException {
//        Request request = new Request.Builder()
//                .url(url)
//                .build();

//        Response response = client.newCall(request).execute();
//        System.out.println(response.protocol());
//        return response.body().string();
//    }

//    public static void main(String[] args) throws IOException {
//        GetExample example  = new GetExample();
//        String     response = example.run("http://www.google.com");
//        System.out.println(response.length());
//    }
//}
