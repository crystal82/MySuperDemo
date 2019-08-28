package xhh.android.com.xhh;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Build;
import android.os.Environment;
import android.provider.MediaStore;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MainActivity extends AppCompatActivity implements View.OnClickListener{
    Button bt;
    TextView tv,tv1;
    ImageView img;
    final int REQUEST_WRITE=1;
    public static final  int getOnPictrue=11;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        if(Build.VERSION.SDK_INT>=23) {
            if (ContextCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
                ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, REQUEST_WRITE);
            }
        }

        bt=(Button) findViewById(R.id.bt);
        tv=(TextView) findViewById(R.id.info);
        tv1=(TextView) findViewById(R.id.info1);
        img=(ImageView) findViewById(R.id.img);
        bt.setOnClickListener(this);
    }

    public void request(String s1,String s2) {
//声明日志类
        HttpLoggingInterceptor httpLoggingInterceptor = new HttpLoggingInterceptor();
//设定日志级别
        httpLoggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY);

//自定义OkHttpClient
        OkHttpClient.Builder okHttpClient = new OkHttpClient.Builder();
//添加拦截器
        okHttpClient.addInterceptor(httpLoggingInterceptor);
        //步骤4:创建Retrofit对象
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("http://restapi.amap.com/v3/geocode/")
                .client(okHttpClient.build())
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        // 步骤5:创建 网络请求接口 的实例
        final GetRequest request = retrofit.create(GetRequest.class);

        //对 发送请求 进行封装
        String s3=s1+","+s2;
        Call<rep> call = request.getCall(s3);

        //步骤6:发送网络请求(异步)
        call.enqueue(new Callback<rep>() {
            //请求成功时回调
            @Override
            public void onResponse(Call<rep> call, Response<rep> response) {
                // 步骤7：处理返回的数据结果
                if(response.body()==null){
                    return;
                }
                if("1".equals(response.body().getStatus())){
                    tv1.setText(response.body().getRegeocode().getFormatted_address());
//                    Log.d("taggggg",response.body().getRegeocode().getFormatted_address());
                }

            }

            //请求失败时回调
            @Override
            public void onFailure(Call<rep> call, Throwable throwable) {
                System.out.println("连接失败"+call.toString());
            }
        });
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.bt:
                String status = Environment.getExternalStorageState();
                if (status.equals(Environment.MEDIA_MOUNTED)) {
                    Intent intent = new Intent();
                    intent.setAction(Intent.ACTION_PICK);
                    intent.setData(MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                    startActivityForResult(intent,getOnPictrue);
                }
                break;
            default:
                break;
        }
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if(RESULT_OK!=resultCode){
            return;
        }
        switch (requestCode){
            case getOnPictrue:
                Uri uri = data.getData();
                final String pathGallery = FileSaveUtil.getPath(getApplicationContext(), uri);
                Log.d("ttt",pathGallery);
                final ImgInfoBean imgInfoBean = new SampleUsage().parseImgInfo(pathGallery);
                if(!TextUtils.isEmpty(imgInfoBean.getLatitude())){
//                    new Thread(new Runnable() {
//                        @Override
//                        public void run() {
                            request(imgInfoBean.getLongitude(),imgInfoBean.getLatitude());
//                        }
//                    });

                }
                tv.setText(imgInfoBean.toString());
                File mCurrentPhotoFile = new File(pathGallery);
                if(mCurrentPhotoFile.exists()) {
                    try {
                        InputStream is1 = new FileInputStream(mCurrentPhotoFile);
                        Bitmap bitmap = BitmapFactory.decodeStream(is1);
                        img.setImageBitmap(bitmap);
                    } catch (FileNotFoundException e) {
                        e.printStackTrace();
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
                break;

            default:
                break;
        }
    }
    @Override
    public void onRequestPermissionsResult(int requestCode,  String[] permissions,  int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if(requestCode==REQUEST_WRITE&&grantResults[0]!= PackageManager.PERMISSION_GRANTED){
            finish();
        }

    }
}
