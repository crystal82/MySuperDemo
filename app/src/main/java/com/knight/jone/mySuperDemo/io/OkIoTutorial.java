package com.knight.jone.mySuperDemo.io;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

import com.knight.jone.mySuperDemo.R;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.charset.Charset;

import okio.Buffer;
import okio.BufferedSink;
import okio.BufferedSource;
import okio.Okio;
import okio.Sink;
import okio.Timeout;

public class OkIoTutorial extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ok_io_tutorial);

        initSink();
    }

    //OutPutStream
    private void initSink() {
        File           file       = new File("/test.txt");

        BufferedSink   bufferSkin = null;
        BufferedSource buffer     = null;
        try {
            if (!file.exists()) {
                file.createNewFile();
            }
            bufferSkin = Okio.buffer(Okio.sink(file));
            bufferSkin.writeString("哈哈哈", Charset.forName("UTF-8"));

            buffer = Okio.buffer(Okio.source(file));
            String s = buffer.readString(Charset.forName("UTF-8"));
            Log.d("Okio", s);
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                if (bufferSkin != null) {
                    bufferSkin.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
