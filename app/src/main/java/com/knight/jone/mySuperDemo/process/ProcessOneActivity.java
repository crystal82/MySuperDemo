package com.knight.jone.mySuperDemo.process;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

import com.knight.jone.mySuperDemo.R;
import com.knight.jone.mySuperDemo.utils.Lg;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class ProcessOneActivity extends AppCompatActivity {

    @BindView(R.id.process_one)
    Button processOne;
    @BindView(R.id.process_two)
    Button processTwo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_process_one);
        ButterKnife.bind(this);

    }

    @Override
    protected void onStart() {
        super.onStart();

        Lg.d("ProcessOneActivity TestBean.num:" + TestBean.num);
        TestBean.num = 100;
    }

    @OnClick({R.id.process_one, R.id.process_two})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.process_one:
                startActivity(new Intent(this, ProcessTwoActivity.class));
                break;
            case R.id.process_two:
                startActivity(new Intent(this, ProcessThreeActivity.class));
                break;
            default:
        }
    }
}
