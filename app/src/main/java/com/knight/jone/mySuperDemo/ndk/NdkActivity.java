package com.knight.jone.mySuperDemo.ndk;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

import com.knight.jone.mySuperDemo.R;

public class NdkActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ndk);

        String text = NDKTools.getStringFromNDK();
        Log.i("NdkActivity","text="+text);
        ((TextView)findViewById(R.id.tv)).setText(text);
    }
}
