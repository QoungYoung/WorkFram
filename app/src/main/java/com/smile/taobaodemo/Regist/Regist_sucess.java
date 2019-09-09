package com.smile.taobaodemo.Regist;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import com.smile.taobaodemo.R;
import com.smile.taobaodemo.base.Constant;
import com.smile.taobaodemo.bean.User;
import com.smile.taobaodemo.ui.activity.MainActivity;
import com.xiasuhuei321.loadingdialog.view.LoadingDialog;

import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.QueryListener;

public class Regist_sucess extends AppCompatActivity {

    private TextView phone;
    private Button login;
    private String id;
    private LoadingDialog load;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_regist_sucess);
        Intent data = getIntent();
        phone = (TextView) findViewById(R.id.regist_suce_num);
        login = (Button) findViewById(R.id.regist_suce_login);
        load = setLoad();
        id = data.getStringExtra("ObjectId");
        load.show();
        phone.setText(Constant.USER.getMobilePhoneNumber());
        load.loadSuccess();
        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent data = new Intent(Regist_sucess.this, MainActivity.class);
                data.putExtra("ObjectId",id);
                startActivity(data);
            }
        });
    }

    private LoadingDialog setLoad(){

        LoadingDialog load = new LoadingDialog(this);
        load.setLoadingText("正在注册...");
        load.setSuccessText("注册成功");//显示加载成功时的文字
        load.setFailedText("注册失败");
        load.setLoadSpeed(LoadingDialog.Speed.SPEED_TWO);
        return load;

    }
}
