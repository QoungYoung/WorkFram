package com.smile.taobaodemo.ui.activity;

import android.graphics.Color;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;

import com.smile.taobaodemo.R;
import com.smile.taobaodemo.ui.fragment.SetNameDialog;

import butterknife.BindView;
import butterknife.ButterKnife;

public class SettingActivity extends AppCompatActivity implements View.OnClickListener {

    @BindView(R.id.set_toolbar)
    Toolbar toolbar;
    @BindView(R.id.set_name)
    Button set_name;
    @BindView(R.id.set_pay_pwd)
    Button set_pay_pwd;
    @BindView(R.id.address_mng)
    Button address_mng;
    @BindView(R.id.logout)
    Button logout;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setting);
        ButterKnife.bind(this);
        toolbar.setTitle("个人设置");
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });
        toolbar.setTitleTextColor(Color.rgb(255, 255, 255));
        //toolbar.getBackground().mutate().setAlpha(0);
        setSupportActionBar(toolbar);
        set_name.setOnClickListener(this);
        set_pay_pwd.setOnClickListener(this);
        address_mng.setOnClickListener(this);
        logout.setOnClickListener(this);

    }

    @Override
    public void onClick(View view) {

        if(view.getId() == R.id.set_name){

            SetNameDialog dialog = new SetNameDialog();
            dialog.show(getFragmentManager(),"SetDialog");

        }else if (view.getId() == R.id.set_pay_pwd){

        }else if(view.getId() ==  R.id.address_mng){

        }else if(view.getId() == R.id.logout){

        }
    }
}
