package com.smile.taobaodemo.Regist;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.smile.taobaodemo.R;
import com.smile.taobaodemo.bean.User;
import com.smile.taobaodemo.ui.activity.LoginActivity;

import java.util.List;

import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.FindListener;

public class Regist_Phone extends AppCompatActivity {

    private EditText num;
    private Button next;
    private TextView read;
    private CheckBox right;
    private int true_num = 0;
    private int true_box = 1;
    private Dialog meserr;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_regist__phone);
        num = (EditText) findViewById(R.id.regist_phone_num);
        next = (Button) findViewById(R.id.regist_phone_next);
        read = (TextView) findViewById(R.id.regist_phone_text);
        right = (CheckBox) findViewById(R.id.regist_right);
        meserr = new AlertDialog.Builder(Regist_Phone.this).setTitle("错误！")
                .setMessage("手机号已存在！请直接登录，如果忘记密码，请点击右下角的“无法登录？”")
                .setNegativeButton("知道了", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        startActivity(new Intent(Regist_Phone.this, LoginActivity.class));
                    }
                }).create();

        num.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {

                if(editable.length()>10){
                    true_num = 1;
                    if(true_box==1){
                        /*
                        * 更改按钮颜色*/
                        next.setVisibility(View.VISIBLE);
                    }
                }else{
                    next.setVisibility(View.INVISIBLE);
                }

            }
        });
        right.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if(b){
                    true_box = 1;
                    if(true_num==1){
                        /*
                        * 修改按钮*/
                        next.setVisibility(View.VISIBLE);

                    }else{
                        next.setVisibility(View.INVISIBLE);


                    }
                }else {
                    true_box = 0;
                    next.setVisibility(View.INVISIBLE);
                }
            }
        });
        next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(true_box==1){
                    if(true_num==1){

                        BmobQuery<User> query = new BmobQuery<>();
                        query.addWhereEqualTo("num",num.getText().toString());
                        query.findObjects(new FindListener<User>() {
                            @Override
                            public void done(List<User> list, BmobException e) {
                                if(list.size()==1){
                                    meserr.show();
                                }else if(list.size()==0){
                                        Intent data = new Intent(Regist_Phone.this,Regist_code.class);
                                        data.putExtra("phone",num.getText().toString());
                                        startActivity(data);
                                }
                            }
                        });
                    }else{
                        Toast.makeText(Regist_Phone.this,"手机号码不正确！",Toast.LENGTH_LONG).show();
                    }
                }else{
                    Toast.makeText(Regist_Phone.this,"请勾选“我已阅读并同意使用条款和隐私政策”！",Toast.LENGTH_LONG).show();
                }
            }
        });
        findViewById(R.id.regist_phone_text).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(Regist_Phone.this,Using.class));
            }
        });
    }
}
