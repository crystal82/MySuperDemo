package com.knight.jone.mySuperDemo;

import android.app.ActivityManager;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;

import androidx.appcompat.app.AppCompatActivity;

import com.knight.jone.mySuperDemo.dialogTutorial.DialogMainActivity;
import com.knight.jone.mySuperDemo.simpleTest.CockroachActivity;
import com.knight.jone.mySuperDemo.utils.Cockroach;
import com.knight.jone.mySuperDemo.utils.Lg;

import java.nio.ByteBuffer;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class MainActivity extends AppCompatActivity implements AdapterView.OnItemClickListener {

    String[] titles = {"Cockroach", "DialogTutorial",};
    @BindView(R.id.test)
    Button test;
    @BindView(R.id.lv_title)
    ListView lvTitle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);

        ListView lv_title = (ListView) findViewById(R.id.lv_title);
        lv_title.setAdapter(new ArrayAdapter<String>(this, R.layout.item_main_title, titles));
        lv_title.setOnItemClickListener(this);
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        switch (position) {
            case 0:
                startActivity(new Intent(this, CockroachActivity.class));
                break;
            case 1:
                startActivity(new Intent(this, DialogMainActivity.class));
                break;
            default:
        }
    }

    public void s() throws Exception {
        throw new Exception();
    }

    public void ss() {
        throw new RuntimeException();
    }

    @OnClick({R.id.exception_main, R.id.exception_thread, R.id.install_cockroach, R.id.uninstall_cockroach})
    public void onExceptionCLicked(View view) {
        switch (view.getId()) {
            case R.id.install_cockroach:
                Cockroach.install(new Cockroach.ExceptionHandler() {
                    @Override
                    public void handlerException(final Thread thread, final Throwable throwable) {
                        try {
                            throwable.printStackTrace();
                            Lg.d("Exception happen:" + thread + "   " + throwable.toString());
                        } catch (Throwable e) {
                            Lg.d("Throwable" + thread + "   " + throwable.toString());
                        }
                    }
                });
                break;
            case R.id.uninstall_cockroach:
                Cockroach.uninstall();
                break;
            case R.id.exception_thread:
                new Thread(() -> {
                    throw new RuntimeException("子线程异常");
                }).start();
                break;
            case R.id.exception_main:
                throw new RuntimeException("主线程异常");
            default:
        }
    }

    @OnClick({R.id.test, R.id.test2})
    public void onViewClicked(View view) {

        ActivityManager activityManager = (ActivityManager) getSystemService(ACTIVITY_SERVICE);
//最大分配内存
        int memory = activityManager.getMemoryClass();
        Lg.d("memory: " + memory);
//扩大默认虚拟机JAVA堆的上限，在manifest中设置largeheap = true。
        int largeMemoryClass = activityManager.getLargeMemoryClass();
//最大分配内存获取方法2
        float maxMemory = (float) (Runtime.getRuntime().maxMemory() * 1.0 / (1024 * 1024));
//当前分配的总内存（包括已经使用和还没使用的）
        float totalMemory = (float) (Runtime.getRuntime().totalMemory() * 1.0 / (1024 * 1024));
//剩余内存（已经申请但是还没使用的）
        float freeMemory = (float) (Runtime.getRuntime().freeMemory() * 1.0 / (1024 * 1024));
//已经使用内存
        float usedMemory = totalMemory - freeMemory;
        Lg.d("maxMemory: " + maxMemory);
        Lg.d("largeMemory: " + largeMemoryClass);
        Lg.d("totalMemory: " + totalMemory);
        Lg.d("freeMemory: " + freeMemory);
        Lg.d("usedMemory: " + usedMemory);

        if (view.getId() == R.id.test2) {
            ByteBuffer.allocate(1024 * 1000 * 100);
        } else {
            ByteBuffer.allocateDirect(1024 * 1000 * 100);
        }


//当前分配的总内存（包括已经使用和还没使用的）
        float totalMemory2 = (float) (Runtime.getRuntime().totalMemory() * 1.0 / (1024 * 1024));
//剩余内存（已经申请但是还没使用的）
        float freeMemory2 = (float) (Runtime.getRuntime().freeMemory() * 1.0 / (1024 * 1024));
//已经使用内存

        Lg.d("totalMemory222: " + totalMemory);
        Lg.d("freeMemory222: " + freeMemory);
        Lg.d("usedMemory222: " + (totalMemory2 - freeMemory2));
    }
}
