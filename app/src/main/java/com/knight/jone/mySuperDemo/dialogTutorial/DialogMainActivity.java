package com.knight.jone.mySuperDemo.dialogTutorial;

import android.app.AlertDialog;
import android.content.Intent;
import androidx.appcompat.app.AppCompatActivity;
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
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setIcon(R.mipmap.ic_launcher);
        builder.setMessage("this is the content view!!!");
        builder.setTitle("this is the title view!!!");
       //builder.setView(R.layout.activity_second);
       //builder.setPositiveButton("知道了", new DialogInterface.OnClickListener() {
       //    @Override
       //    public void onClick(DialogInterface dialog, int which) {
       //        alertDialog.dismiss();
       //    }
       //});
       //alertDialog = builder.create();
       //alertDialog.show();
    }
}
