package com.knight.jone.mySuperDemo.memory;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.knight.jone.mySuperDemo.R;

public class SecondActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_second);
    }

    public void backToMemory(View view) {
        startActivity(new Intent(this, MemoryActivity.class));
        finish();
    }
}
