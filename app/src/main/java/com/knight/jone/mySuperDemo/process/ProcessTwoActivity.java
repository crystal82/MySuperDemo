package com.knight.jone.mySuperDemo.process;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.knight.jone.mySuperDemo.R;
import com.knight.jone.mySuperDemo.utils.Lg;

public class ProcessTwoActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_process_two);

        Lg.d("ProcessOneActivity TestBean.num:" + TestBean.num);
        TestBean.num = 200;
    }
}
