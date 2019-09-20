package com.knight.jone.mySuperDemo.memory;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import com.knight.jone.mySuperDemo.MyApplication;
import com.knight.jone.mySuperDemo.R;

/**
 * 内容相关Activity
 * 1.模拟内存泄漏
 * 2.内存优化
 *
 * @author zone
 */
public class MemoryActivity extends AppCompatActivity {

    public static Context sContext;
    private ImageView ivIcon;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_memory);
        ivIcon = findViewById(R.id.iv_icon);
    }

    public void simulateLeakMemory(View view) {
        sContext = this;
        MyApplication.getInstance().activities.add(this);
    }

    public void jumpActivity(View view) {
        startActivity(new Intent(this, SecondActivity.class));
        finish();
    }
}
