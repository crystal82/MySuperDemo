package com.knight.jone.mySuperDemo;

import android.content.Intent;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.knight.jone.mySuperDemo.dialogTutorial.DialogMainActivity;
import com.knight.jone.mySuperDemo.simpleTest.CockroachActivity;

public class MainActivity extends AppCompatActivity implements AdapterView.OnItemClickListener {

    String[] titles = {"Cockroach", "DialogTutorial",};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ListView lv_title = (ListView) findViewById(R.id.lv_title);
        lv_title.setAdapter(new ArrayAdapter<String>(this, R.layout.item_main_title, titles));
        lv_title.setOnItemClickListener(this);
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        switch (position) {
            case 0:
                startActivity(new Intent(this, CockroachActivity.class));
                break;
            case 1:
                startActivity(new Intent(this, DialogMainActivity.class));
                break;
            default:
        }
    }
}
