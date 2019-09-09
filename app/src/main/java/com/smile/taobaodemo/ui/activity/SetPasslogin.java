package com.smile.taobaodemo.ui.activity;

import android.content.Intent;
import android.content.ServiceConnection;
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
import com.smile.taobaodemo.bean.User;
import com.xiasuhuei321.loadingdialog.view.LoadingDialog;

import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.QueryListener;
import cn.bmob.v3.listener.UpdateListener;

public class SetPasslogin extends AppCompatActivity {

    private EditText pwd;
    private EditText re_pwd;
    private String id;
    private Button finsh;
    private TextView tips;
    private int flag = 0;
    private LoadingDialog dialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_set_passlogin);
        final Intent data = getIntent();
        id = data.getStringExtra("ObjectId");
        pwd = (EditText) findViewById(R.id.login_set_pwd);
        re_pwd = (EditText) findViewById(R.id.login_set_re_pwd);
        finsh = (Button) findViewById(R.id.login_set_finsh);
        tips = (TextView) findViewById(R.id.login_set_tip);
        dialog = setLoad(dialog);

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
                    finsh.setVisibility(View.INVISIBLE);
                }else if(editable.toString().equals(re_pwd.getText().toString())){
                    tips.setText("密码通过");
                    tips.setTextColor(Color.GREEN);
                    finsh.setVisibility(View.VISIBLE);
                }else {
                    tips.setText("两次密码不一致！");
                    tips.setTextColor(Color.RED);
                    finsh.setVisibility(View.INVISIBLE);
                }

            }
        });

        re_pwd.addTextChangedListener(new TextWatcher() {
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
                    finsh.setVisibility(View.INVISIBLE);
                }else if(editable.toString().equals(pwd.getText().toString())) {
                    tips.setText("密码通过");
                    tips.setTextColor(Color.GREEN);
                    finsh.setVisibility(View.VISIBLE);
                }else {
                    tips.setText("两次输入的密码不一致！");
                    tips.setTextColor(Color.RED);
                    finsh.setVisibility(View.INVISIBLE);
                }

            }
        });

        finsh.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.show();
                BmobQuery<User> query = new BmobQuery<>();
                query.getObject(id, new QueryListener<User>() {
                    @Override
                    public void done(User user, BmobException e) {
                        user.setPassword(pwd.getText().toString());
                        user.update(new UpdateListener() {
                            @Override
                            public void done(BmobException e) {
                                if(e==null){
                                    Intent data = new Intent(SetPasslogin.this,MainActivity.class);
                                    data.putExtra("ObjectId",id);
                                    dialog.loadSuccess();
                                    startActivity(data);
                                    finish();
                                }else {
                                    dialog.loadFailed();
                                    Toast.makeText(SetPasslogin.this,"修改失败，请检查网络是否通畅",Toast.LENGTH_LONG).show();
                                }
                            }
                        });
                    }
                });
            }
        });

    }

    private LoadingDialog setLoad(LoadingDialog load){

        load = new LoadingDialog(this);
        load.setLoadingText("正在登录...");
        load.setSuccessText("登录成功");//显示加载成功时的文字
        load.setFailedText("登录失败");
        load.setLoadSpeed(LoadingDialog.Speed.SPEED_TWO);
        return load;

    }
}
