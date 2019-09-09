package com.smile.taobaodemo.ui.activity;

import android.content.Context;
import android.content.Intent;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Window;
import android.view.WindowManager;
import android.widget.TextView;

import com.aurelhubert.ahbottomnavigation.AHBottomNavigation;
import com.aurelhubert.ahbottomnavigation.AHBottomNavigationItem;
import com.aurelhubert.ahbottomnavigation.AHBottomNavigationViewPager;
import com.smile.taobaodemo.R;
import com.smile.taobaodemo.base.Constant;
import com.smile.taobaodemo.ui.adapter.ViewPagerAdapter;
import com.smile.taobaodemo.utils.SPUtil;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * @author Smile Wei
 * @since 2017/03/01.
 */

public class MainActivity extends AppCompatActivity {

    @BindView(R.id.view_pager)
    AHBottomNavigationViewPager viewPager;
    @BindView(R.id.bottom_navigation)
    AHBottomNavigation bottomNavigation;
    /*@BindView(R.id.toolbar)
    Toolbar toolbar;*/
    @BindView(R.id.tv_search_tips)
    TextView tvSearchTips;

    private CountDownTimer timer;

    private ArrayList<AHBottomNavigationItem> bottomNavigationItems = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        Log.d("Theme", "initView: "+SPUtil.get(this,"theme","dayTheme"));
        initView();
    }

    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
    }

    public void initView() {
        //toolbar.setTitle("");
        //setSupportActionBar(toolbar);
        AHBottomNavigationItem item1 = new AHBottomNavigationItem(R.string.bottom_navigation_item_home, R.drawable.navtab_home, R.color.colorBottomNavigationActiveColored);
        AHBottomNavigationItem item2 = new AHBottomNavigationItem(R.string.bottom_navigation_item_we, R.drawable.navtab_we, R.color.colorBottomNavigationActiveColored);
        //AHBottomNavigationItem item3 = new AHBottomNavigationItem(R.string.bottom_navigation_item_help, R.drawable.navtab_help, R.color.colorBottomNavigationActiveColored);
        AHBottomNavigationItem item4 = new AHBottomNavigationItem(R.string.bottom_navigation_item_cart, R.drawable.navtab_cart, R.color.colorBottomNavigationActiveColored);
        AHBottomNavigationItem item5 = new AHBottomNavigationItem(R.string.bottom_navigation_item_user, R.drawable.navtab_user, R.color.colorBottomNavigationActiveColored);

        bottomNavigationItems.add(item1);
        bottomNavigationItems.add(item2);
        //bottomNavigationItems.add(item3);
        bottomNavigationItems.add(item4);
        bottomNavigationItems.add(item5);

        bottomNavigation.addItems(bottomNavigationItems);
        bottomNavigation.setForceTitlesDisplay(true);
        bottomNavigation.setDefaultBackgroundColor(Color.parseColor("#FEFEFE"));
        bottomNavigation.setAccentColor(Color.parseColor("#1ba784"));
        bottomNavigation.setInactiveColor(Color.parseColor("#949494"));

        bottomNavigation.setOnTabSelectedListener(new AHBottomNavigation.OnTabSelectedListener() {
            @Override
            public boolean onTabSelected(int position, boolean wasSelected) {
                viewPager.setCurrentItem(position, false);
                return true;
            }
        });
        viewPager.setOffscreenPageLimit(4);
        viewPager.setAdapter(new ViewPagerAdapter(getSupportFragmentManager()));




        /**
         * 顶部搜索框内容定时更新，简单的采用倒计时功能
         */
        /*final List<String> tips = new ArrayList<>();
        tips.add("不需要搜索框可以去掉");
        tips.add("不需要搜索框可以去掉");
        tips.add("不需要搜索框可以去掉");
        timer = new CountDownTimer(3000000, 5000) {
            @Override
            public void onTick(long millisUntilFinished) {
                tvSearchTips.setText(tips.get(new Random().nextInt(2)));
            }

            @Override
            public void onFinish() {

            }
        };*/
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    @Override
    protected void onStart() {
        super.onStart();
        //timer.start();
    }

    @Override
    protected void onStop() {
        super.onStop();
    }

    @Override
    public void onBackPressed() {
        moveTaskToBack(false);
    }


    @Override
    public Resources getResources() {
        Resources res = super.getResources();
        Configuration config = res.getConfiguration();
        config.fontScale = Constant.TEXT_SIZE; //1 设置正常字体大小的倍数
        res.updateConfiguration(config, res.getDisplayMetrics());
        return res;
    }
    public void refresh() {

        onCreate(null);

    }
}
