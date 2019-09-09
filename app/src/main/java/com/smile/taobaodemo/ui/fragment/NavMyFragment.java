package com.smile.taobaodemo.ui.fragment;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatDelegate;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Switch;
import android.widget.TextView;

import com.smile.taobaodemo.R;
import com.smile.taobaodemo.base.Constant;
import com.smile.taobaodemo.ui.activity.MainActivity;
import com.smile.taobaodemo.ui.activity.SettingActivity;
import com.smile.taobaodemo.ui.activity.WebActivity;
import com.smile.taobaodemo.ui.adapter.CartAdapter;

import butterknife.BindView;

/**
 * @author Smile Wei
 * @since 2017/3/2.
 */

public class NavMyFragment extends Fragment implements View.OnClickListener, CompoundButton.OnCheckedChangeListener, RadioGroup.OnCheckedChangeListener, SwipeRefreshLayout.OnRefreshListener {

     Button setting;
     ImageButton weather;
     ImageButton baike;
     ImageButton share;
     RadioButton basic_font;
     RadioButton better_font;
     RadioButton best_font;
     Switch night_button;
     TextView user_name;
     View v;
    SwipeRefreshLayout refreshLayout;
    private Context context = getContext();
    private Activity activity =getActivity();


    public static NavMyFragment newInstance() {
        NavMyFragment fragment = new NavMyFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        v = inflater.inflate(R.layout.fragment_navigation_user, container, false);
        initView(v);
        //
        return v;
    }


    private void initView(View v) {
        if(getActivity()!=null){

            context =getContext();
            activity =getActivity();
            basic_font = (RadioButton) v.findViewById(R.id.basic_font);
            better_font = (RadioButton) v.findViewById(R.id.better_font);
            best_font = (RadioButton) v.findViewById(R.id.best_font);
            night_button = (Switch) v.findViewById(R.id.night_button);
            baike = (ImageButton) v.findViewById(R.id.baike);
            weather = (ImageButton) v.findViewById(R.id.weather);
            share = (ImageButton) v.findViewById(R.id.share);
            user_name = (TextView) v.findViewById(R.id.user_name);
            setting = (Button) v.findViewById(R.id.person_setting);
            refreshLayout = (SwipeRefreshLayout) v.findViewById(R.id.refresh_user_layout);
            Log.d("User", "onCreateView: "+Constant.USER.getUsername());
            user_name.setText(Constant.USER.getUsername());
            baike.setOnClickListener(this);
            weather.setOnClickListener(this);
            refreshLayout.setColorSchemeResources(R.color.font_orange_color);
            refreshLayout.setOnRefreshListener(this);
            share.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent=new Intent(Intent.ACTION_SEND);
                    intent.setType("text/plain");
                    intent.putExtra(Intent.EXTRA_SUBJECT, "Share");
                    intent.putExtra(Intent.EXTRA_TEXT, "我正在使用蜗牛农场种菜哦，每天都能吃到新鲜的有机蔬菜，你也来试试吧！点击下面的链接下载安装就能体验到啦\nhttps://www.baidu.com");
                    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    startActivity(Intent.createChooser(intent, activity.getTitle()));
                }
            });
            basic_font.setOnCheckedChangeListener(this);
            best_font.setOnCheckedChangeListener(this);
            better_font.setOnCheckedChangeListener(this);
            night_button.setChecked(Constant.NIGHT);
            night_button.setOnCheckedChangeListener(this);
            setting.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    startActivity(new Intent(activity, SettingActivity.class));
                }
            });
            Log.d("字号", "onCreateView: "+Constant.CHECKED);
            //根据字号控制类判断单选按钮的选择
            if(Constant.CHECKED == 1){
                basic_font.setChecked(true);
            }
        }
    }

    @Override
    public void onClick(View view) {
        String url = "";
        switch (view.getId()){
            case R.id.baike:
                url = "http://www.cfvin.com/1005/";
                break;
            case R.id.weather:
                url = "http://m.hao123.com/a/tianqi";
                break;
        }
        Intent intent = new Intent(getActivity(), WebActivity.class);
        intent.putExtra("url",url);
        startActivity(intent);
    }

    /**
     * 单选按钮发生选中改变后对字号进行调整
     * @param compoundButton 被选中的按钮
     * @param b 选中状态
     */
    @Override
    public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
        if(b){
            switch (compoundButton.getId()){
                case R.id.basic_font:
                    //if(b){
                        Constant.TEXT_SIZE = 1f;
                        Constant.CHECKED = 1;
                        Log.d("标准", "onCheckedChanged: 点击了标准");
                        activity.finish();
                        startActivity(new Intent( activity, activity.getClass()));
                        break;
                    //}

                case R.id.better_font:
                    //if(b){
                        Constant.TEXT_SIZE = 1.5f;
                        Constant.CHECKED = 2;
                        Log.d("偏大", "onCheckedChanged: 点击了偏大");

                        break;
                    //}

                case R.id.best_font:
                    //if(b){
                        Constant.TEXT_SIZE = 2f;
                        Constant.CHECKED = 3;
                        Log.d("最大", "onCheckedChanged: 点击了最大");
                    activity.finish();
                    startActivity(new Intent( activity, activity.getClass()));
                        break;

                    //}

            }
        }
        if(compoundButton.getId() == R.id.night_button)
        if(b){
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES);
            Constant.NIGHT = b;
            activity.finish();
            startActivity(new Intent( activity, activity.getClass()));

        }else {
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
            Constant.NIGHT = b;
            activity.finish();

            startActivity(new Intent( activity, activity.getClass()));

        }
    }

    @Override
    public void onCheckedChanged(RadioGroup radioGroup, int i) {

    }

    private void setRefreshLoading(boolean isLoading) {
        if (!isLoading) {
            refreshLayout.setRefreshing(false);
        }
    }

    @Override
    public void onRefresh() {
        initView(v);
        setRefreshLoading(false);
    }
}
