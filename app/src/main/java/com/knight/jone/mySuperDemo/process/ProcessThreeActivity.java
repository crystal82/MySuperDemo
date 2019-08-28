package com.knight.jone.mySuperDemo.process;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.knight.jone.mySuperDemo.R;
import com.knight.jone.mySuperDemo.utils.Lg;

public class ProcessThreeActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_process_three);

        Lg.d("ProcessThreeActivity TestBean.num:" + TestBean.num);
        TestBean.num = 300;
    }
}
