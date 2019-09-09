package com.smile.taobaodemo.ui.activity;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.os.CountDownTimer;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import com.smile.taobaodemo.R;
import com.smile.taobaodemo.bean.User;
import com.xiasuhuei321.loadingdialog.view.LoadingDialog;

import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.BmobSMS;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.QueryListener;
import cn.bmob.v3.listener.UpdateListener;

public class Code_Login extends AppCompatActivity {

    private EditText code_edit;
    private Button next;
    private Button send;
    private String phone;
    private Dialog meserr;
    private String id;
    private LoadingDialog load;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_code__login);
        code_edit = (EditText) findViewById(R.id.code_login_edit);
        send = (Button) findViewById(R.id.code_login_send);
        next = (Button) findViewById(R.id.code_login_next);
        Intent data = getIntent();
        id = data.getStringExtra("ObjectId");

        meserr = new AlertDialog.Builder(Code_Login.this).setTitle("重置密码！")
                .setMessage("您已通过短信成功登录，是否重新设置登录密码？")
                .setPositiveButton("立即设置", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        Intent data = new Intent(Code_Login.this, SetPasslogin.class);
                        data.putExtra("ObjectId",id);
                        startActivity(data);
                    }
                })
                .setNegativeButton("跳过", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        Intent data = new Intent(Code_Login.this, MainActivity.class);
                        data.putExtra("ObjectId",id);
                        startActivity(data);
                    }
                })
                .create();
        load = setLoad(load);
        load.show();

        BmobQuery<User> query = new BmobQuery<>();
        query.getObject(id, new QueryListener<User>() {
            @Override
            public void done(User user, BmobException e) {
                phone = user.getMobilePhoneNumber();
                load.loadSuccess();
                new MyThread().start();
            }
        });
        send.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                new MyThread().start();
            }
        });

        next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                BmobSMS.verifySmsCode(phone, code_edit.getText().toString(), new UpdateListener() {
                    @Override
                    public void done(BmobException e) {
                        if(e==null){
                            /*
                             * 进入下一个页面*/
                            meserr.show();


                        }else{
                            Toast.makeText(Code_Login.this,"验证码错误！",Toast.LENGTH_LONG).show();
                        }
                    }
                });
            }
        });

    }

    private class  MyThread extends Thread {

        public void run() {
            BmobSMS.requestSMSCode(phone, "易记方登录", new QueryListener<Integer>() {
                @Override
                public void done(Integer integer, BmobException e) {

                    if(e!=null){
                        Toast.makeText(Code_Login.this,e.getMessage().toString(),Toast.LENGTH_LONG).show();
                    }else {
                        new CountDownTimer(60000,1000){
                            @Override
                            public void onTick(long millisUntilFinished) {
                                send.setClickable(false);
                                send.setText(millisUntilFinished / 1000 + "秒");
                                send.setBackgroundColor(Color.GRAY);
                            }
                            @Override
                            public void onFinish() {
                                send.setClickable(true);
                                send.setText("重新发送");
                            }
                        }.start();
                    }
                }
            });
        }
    }

    private LoadingDialog setLoad(LoadingDialog load){

        load = new LoadingDialog(this);
        load.setLoadingText("正在连接...");
        load.setSuccessText("连接成功");//显示加载成功时的文字
        load.setFailedText("连接失败");
        load.setLoadSpeed(LoadingDialog.Speed.SPEED_TWO);
        return load;

    }

}
