package com.knight.jone.mySuperDemo.simpleTest;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.knight.jone.mySuperDemo.R;
import com.knight.jone.mySuperDemo.utils.Cockroach;

public class CockroachActivity extends AppCompatActivity {

    private TextView mTv_cockroach_result;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cockroach);

        mTv_cockroach_result = (TextView) findViewById(R.id.tv_cockroach_result);
        findViewById(R.id.btn_get_error).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                doErrorInfoClick(v);
            }
        });
    }

    public void doInstallClick(View view) {
        Toast.makeText(this, "doInstallClick", Toast.LENGTH_SHORT).show();
        Cockroach.install(new Cockroach.ExceptionHandler() {
            @Override
            public void handlerException(Thread thread, Throwable throwable) {
                Toast.makeText(CockroachActivity.this, "异常:" + throwable.getMessage(), Toast.LENGTH_SHORT).show();
                mTv_cockroach_result.setText(throwable.getMessage() + "");
            }
        });
    }

    public void doUnInstallClick(View view) {
        Toast.makeText(this, "doUnInstallClick", Toast.LENGTH_SHORT).show();
        Cockroach.uninstall();
    }

    public void doErrorInfoClick(View view) {
        int a = 1 / 0;
        mTv_cockroach_result.setText(a);
        Toast.makeText(this, "doGetErrorClick", Toast.LENGTH_SHORT).show();
    }
}
