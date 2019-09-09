package com.smile.taobaodemo.ui.activity;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.smile.taobaodemo.R;
import com.smile.taobaodemo.bean.User;

import java.util.List;

import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.FindListener;

public class PhoneLogin extends AppCompatActivity {

    private EditText phone_edit;
    private Button next;
    private Dialog meserr;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_phone_login);
        phone_edit = (EditText) findViewById(R.id.phone_login_num);
        next = (Button) findViewById(R.id.phone_login_next);
        meserr = new AlertDialog.Builder(PhoneLogin.this).setTitle("手机号不存在！")
                .setMessage("手机号不存在！请点击登录界面右下角注册~")
                .setPositiveButton("去注册", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        startActivity(new Intent(PhoneLogin.this,LoginActivity.class));
                    }
                })
                .setNegativeButton("再输一次", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {

                    }
                })
                .create();

        phone_edit.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {

                if (editable.length() > 10) {
                    next.setVisibility(View.VISIBLE);
                } else {
                    next.setVisibility(View.INVISIBLE);
                }

            }
        });
        next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                BmobQuery<User> query = new BmobQuery<>();
                query.addWhereEqualTo("mobilePhoneNumber", phone_edit.getText().toString());
                query.findObjects(new FindListener<User>() {
                    @Override
                    public void done(List<User> list, BmobException e) {
                        if (e!=null) {
                            meserr.show();
                            Log.d("PhoneLogin", "done: "+e.getMessage());
                        } else {
                            Intent data = new Intent(PhoneLogin.this, Code_Login.class);
                            data.putExtra("ObjectId", list.get(0).getObjectId());
                            startActivity(data);
                        }
                    }
                });
            }
        });
    }
}