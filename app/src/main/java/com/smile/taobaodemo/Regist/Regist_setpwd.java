package com.smile.taobaodemo.Regist;

import android.content.Intent;
import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import com.smile.taobaodemo.R;
import com.smile.taobaodemo.base.Constant;
import com.smile.taobaodemo.bean.User;

import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.SaveListener;

public class Regist_setpwd extends AppCompatActivity {


    private EditText name;
    private EditText pwd;
    private String phone;
    private Button finsh;
    private TextView tips;
    private User user;
    private int flag = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_regist_setpwd);
        Intent data = getIntent();
        phone = data.getStringExtra("phone");
        user = new User();
        name = (EditText) findViewById(R.id.regist_set_name);
        pwd = (EditText) findViewById(R.id.regist_set_pwd);
        finsh = (Button) findViewById(R.id.regist_set_finsh);
        tips = (TextView) findViewById(R.id.regist_set_tip);


        pwd.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {

                if(editable.length()<6){
                    tips.setText("密码不足六位！");
                    tips.setTextColor(Color.RED);
                }else{
                    tips.setText("密码通过");
                    tips.setTextColor(Color.GREEN);
                    flag = 1;
                }

            }
        });

        finsh.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(flag==1){
                    user.setMobilePhoneNumber(phone);
                    user.setPassword(pwd.getText().toString());
                    user.setUsername(name.getText().toString());
                    user.signUp(new SaveListener<User>() {
                        @Override
                        public void done(User user, BmobException e) {
                            if(e==null){

                                Intent data = new Intent(Regist_setpwd.this,Regist_sucess.class);
                                Constant.USER =user;
                                startActivity(data);

                            }else{
                                show(e.getMessage());


                            }
                        }
                    });
                }
            }
        });

    }

    private void show(String e){

        Toast.makeText(Regist_setpwd.this,"注册失败："+e+" ",Toast.LENGTH_LONG).show();

    }


}
