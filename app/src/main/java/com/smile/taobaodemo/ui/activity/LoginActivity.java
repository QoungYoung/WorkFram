package com.smile.taobaodemo.ui.activity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.support.design.widget.Snackbar;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import com.smile.taobaodemo.R;
import com.smile.taobaodemo.Regist.Regist_Phone;
import com.smile.taobaodemo.base.Constant;
import com.smile.taobaodemo.bean.User;
import com.smile.taobaodemo.ui.activity.MainActivity;
import com.xiasuhuei321.loadingdialog.view.LoadingDialog;

import java.util.List;

import cn.bmob.v3.Bmob;
import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.BmobUser;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.BmobUpdateListener;
import cn.bmob.v3.listener.FindListener;
import cn.bmob.v3.listener.SaveListener;
import cn.bmob.v3.update.BmobUpdateAgent;
import cn.bmob.v3.update.UpdateResponse;

public class LoginActivity extends AppCompatActivity {

    private static final String TAG = "Login";
    private EditText phone;
    private EditText pwd;
    private Button login;
    private TextView reg;
    private TextView question;
    private LoadingDialog load;
    private SharedPreferences sp;
    private CheckBox auto;
    private String pwd_str;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        Bmob.initialize(this, "848cad5ba37c4fd27d8e0fcbee8e6ac9");
        sp = this.getSharedPreferences("userInfo",MODE_PRIVATE);
        phone = (EditText) findViewById(R.id.login_phone);
        pwd = (EditText) findViewById(R.id.login_pwd);
        login = (Button) findViewById(R.id.login);
        reg =  (TextView) findViewById(R.id.login_reg);
        question = (TextView) findViewById(R.id.login_ques);
        auto = (CheckBox) findViewById(R.id.auto_login);
        load = setLoad(load);
        pwd_str = null;

        /*
        * 自动登录*/
        if(sp.getBoolean("auto_isCheck", false)){
            auto.setChecked(true);
            Constant.USER.setUsername(sp.getString("Phone",""));
            Constant.USER.setPassword(sp.getString("Password",""));
            pwd_str = sp.getString("Password","");
            load.show();
            login(Constant.USER);
        }




        //登录
        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(final View view) {
                final User user = new User();
                //此处替换为你的用户名
                user.setUsername(phone.getText().toString());
                //此处替换为你的密码
                user.setPassword(pwd.getText().toString());
                load = setLoad(load);
                load.show();
                login(user);
            }
        });


        //注册
        reg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(LoginActivity.this, Regist_Phone.class));
            }
        });
        question.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(LoginActivity.this,PhoneLogin.class));
            }
        });


    }

    private void show(String e) {


        switch (e){

            case "9015":
                Toast.makeText(LoginActivity.this,"登录失败，请检查手机号是否正确，错误代码"+e,Toast.LENGTH_LONG).show();
                break;
            case "1001":
                Toast.makeText(LoginActivity.this,"登录失败:密码错误！错误代码"+e,Toast.LENGTH_LONG).show();
                break;
                default:Toast.makeText(LoginActivity.this,"登录失败:未知错误，错误代码"+e,Toast.LENGTH_LONG).show();



        }

    }

    private LoadingDialog setLoad(LoadingDialog load){

        load = new LoadingDialog(this);
        load.setLoadingText("正在登录...");
        load.setSuccessText("登录成功");//显示加载成功时的文字
        load.setFailedText("登录失败");
        load.setLoadSpeed(LoadingDialog.Speed.SPEED_TWO);
        return load;

    }

    private void login(final User user){
        user.login(new SaveListener<User>() {
            @Override
            public void done(User bmobUser, BmobException e) {
                if (e == null) {
                    if(pwd_str == null)
                        pwd_str = pwd.getText().toString();
                    if(auto.isChecked()){
                        SharedPreferences.Editor editor = sp.edit();
                        editor.putString("Phone",bmobUser.getMobilePhoneNumber());
                        editor.putString("Password",pwd_str);
                        editor.putBoolean("auto_isCheck",true);
                        editor.apply();
                    }
                    load.loadSuccess();
                    load.close();
                    Intent data = new Intent(LoginActivity.this, MainActivity.class);
                    Constant.USER = BmobUser.getCurrentUser(User.class);;
                    startActivity(data);
                    finish();
                } else {
                    show(e.getMessage());
                    load.loadFailed();
                }
            }
        });
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        menu.add(0,100,1,"验证码登录");
        menu.add(1,200,2,"取消");
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()){
            case 100 :
                startActivity(new Intent(LoginActivity.this,Code_Login.class));
                break;
            case 200:
                break;
        }


        return super.onOptionsItemSelected(item);
    }


}
