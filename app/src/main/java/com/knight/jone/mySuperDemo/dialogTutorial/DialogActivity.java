package com.knight.jone.mySuperDemo.dialogTutorial;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.knight.jone.mySuperDemo.R;

/**
 * DialogActivity
 * 继承android:theme="@style/MyDialog"
 */
public class DialogActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dialog);
    }
}