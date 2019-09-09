package com.smile.taobaodemo.ui.activity;

import android.app.Activity;
import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.smile.taobaodemo.R;
import com.smile.taobaodemo.base.BundleKey;
import com.smile.taobaodemo.model.entity.HomeBase;
import com.smile.taobaodemo.model.entity.HomeTop;
import com.smile.taobaodemo.ui.adapter.GoodsDetailPagerAdapter;
import com.smile.taobaodemo.ui.adapter.ImageHomeAdapter;
import com.smile.taobaodemo.utils.StatusBarUtil;
import com.smile.taobaodemo.widget.CirclePageIndicator;
import com.smile.taobaodemo.widget.CustomLoseDialog;
import com.smile.taobaodemo.widget.GradientScrollView;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import cn.trinea.android.view.autoscrollviewpager.AutoScrollViewPager;

public class DetailActivity extends AppCompatActivity {
    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.status_bar)
    View statusBar;
    @BindView(R.id.scroll_view)
    GradientScrollView scrollView;
    @BindView(R.id.auto_view_pager)
    AutoScrollViewPager autoViewPager;
    @BindView(R.id.indicator)
    CirclePageIndicator indicator;
    @BindView(R.id.tv_name)
    TextView tvName;
    @BindView(R.id.tv_price)
    TextView tvPrice;
    @BindView(R.id.tv_areaCount)
    TextView tvAreaCount;
    @BindView(R.id.tv_usedCount)
    TextView tvUsedCount;
    @BindView(R.id.tv_spandCount)
    TextView tvSpandCount;
    @BindView(R.id.tab_layout)
    TabLayout tabLayout;
    @BindView(R.id.view_pager)
    ViewPager viewPager;
    @BindView(R.id.tv_buy)
    Button tvbuy;

    private Activity activity;
    private Context context;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_detail);

        ButterKnife.bind(this);

        activity = this;
        context = getApplicationContext();

        initView();
    }

    private void initView() {
        toolbar.setTitle("农场详情");
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null)
            actionBar.setDisplayHomeAsUpEnabled(true);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });

        toolbar.setTitleTextColor(Color.argb(0, 255, 255, 255));
        toolbar.getBackground().mutate().setAlpha(0);
        statusBar.getBackground().mutate().setAlpha(0);
        scrollView.setHeader(toolbar, statusBar);
        StatusBarUtil.setTransparentForImageView(this, tvName);

        HomeBase bean = getIntent().getParcelableExtra(BundleKey.PARCELABLE);


        /*
        * 此处利用ObjectID获取数据库信息
        * */

        tvName.setText(bean.getName());
        tvSpandCount.setText("可用： "+bean.getSpanCount());
        tvAreaCount.setText("总面积： "+bean.getAreaCount());
        tvUsedCount.setText("已用： "+(bean.getAreaCount()-bean.getSpanCount()));
        tvPrice.setText("¥" + bean.getPrice());
        List<HomeTop.Carousel> list = new ArrayList<>();
        /*
        * 后期从数据库获取对应农场的图片
        * */


        /*
        * 从数据库拉入图片*/
        list.add(new HomeTop.Carousel(1, "http://dpic.tiankong.com/w6/29/QJ7101639804.jpg"));
        list.add(new HomeTop.Carousel(2, "http://dpic.tiankong.com/ki/2w/QJ6778940753.jpg"));
        list.add(new HomeTop.Carousel(3, "http://dpic.tiankong.com/cx/xu/QJ6661798855.jpg"));
        list.add(new HomeTop.Carousel(4, "http://dpic.tiankong.com/89/0d/QJ6704808577.jpg"));
        list.add(new HomeTop.Carousel(5, "http://dpic.tiankong.com/es/3h/QJ9107185690.jpg"));

        autoViewPager.setAdapter(new ImageHomeAdapter(context, activity, list));
        indicator.setViewPager(autoViewPager);
        autoViewPager.setInterval(4000);
        autoViewPager.setSlideBorderMode(AutoScrollViewPager.SLIDE_BORDER_MODE_TO_PARENT);

        GoodsDetailPagerAdapter adapter = new GoodsDetailPagerAdapter(getSupportFragmentManager(),
                new String[]{"详细信息", "用户反馈(20)"});
        viewPager.setAdapter(adapter);
        tabLayout.setupWithViewPager(viewPager);

        tvbuy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                loseDialog();
            }
        });
    }

    private void loseDialog(){
        CustomLoseDialog customLoseDialog = new CustomLoseDialog();
        customLoseDialog.show(getFragmentManager(),"lose");
    }
}
