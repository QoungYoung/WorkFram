package com.smile.taobaodemo.ui.activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.smile.taobaodemo.R;

import butterknife.BindView;

public class SucessActivity extends AppCompatActivity implements View.OnClickListener {

     private Button home;
     private Button list;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sucess);
        home = (Button) findViewById(R.id.sucess_home);
        list = (Button) findViewById(R.id.sucess_list);
        list.setOnClickListener(this);
        home.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        Intent intent = null;
        switch (view.getId()){
            case R.id.sucess_home:
                intent = new Intent(SucessActivity.this,MainActivity.class);
                break;
            case R.id.sucess_list:
                intent = new Intent(SucessActivity.this,ListActivity.class);
                break;
        }
        finish();
        startActivity(intent);
    }
}
