package com.knight.jone.mySuperDemo.dialogTutorial;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import com.knight.jone.mySuperDemo.R;

public class DialogMainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dialog_main);
    }

    //显示Activity使用dialog,theme
    public void showDialogActivity(View view) {
        startActivity(new Intent(this, DialogActivity.class));
    }

    //显示自定义Dialog
    public void showCustomDialog(View view) {
        CommonDialog commonDialog = new CommonDialog(this, "TestShow", "Sure");
        commonDialog.showDialog();
    }

    //Dialog工具
    public void showUtilDialog(View view) {
    }

    //直接new
    public void showNewDialog(View view) {
    }
}
