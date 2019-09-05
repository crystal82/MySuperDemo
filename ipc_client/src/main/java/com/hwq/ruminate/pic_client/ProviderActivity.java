package com.hwq.ruminate.pic_client;

import android.net.Uri;
import android.os.Build;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.hwq.ruminate.ipc_client.R;

public class ProviderActivity extends AppCompatActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.provider_activity);

        Uri uri = Uri.parse("content://com.hwq.ipc.provider");

        getContentResolver().query(uri, null, null, null, null);
    }
}
