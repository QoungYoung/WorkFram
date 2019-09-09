package com.smile.taobaodemo.ui.activity;

import android.annotation.TargetApi;
import android.graphics.Color;
import android.os.Build;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;

import com.smile.taobaodemo.R;
import com.smile.taobaodemo.bean.AreaBean;
import com.smile.taobaodemo.model.entity.HomeBase;
import com.smile.taobaodemo.ui.adapter.AreaAdapter;
import com.smile.taobaodemo.utils.StatusBarUtil;
import com.smile.taobaodemo.widget.GradientScrollView;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class ListActivity extends AppCompatActivity {


    @BindView(R.id.area_recycleView)
    RecyclerView areaRecyclerView;

    private AreaAdapter areaAdapter;
    private List<HomeBase> list;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list);
        ButterKnife.bind(this);
        initData();
        initView();
    }

    private void initData(){

        list = new ArrayList<>();
            list.add(new HomeBase(1,34.5,"https://dpic2.tiankong.com/xx/dc/QJ6357752798.jpg?x-oss-process=style/240h","阳信冬枣","阳信冬枣颗大饱满，清脆可口",1,300,500));
            list.add(new HomeBase(2,31.5,"http://dpic.tiankong.com/w6/29/QJ7101639804.jpg","济南大棚","济南大棚种植区蔬菜种类多样，工业化管理，保证产品健康",1,300,700));
            list.add(new HomeBase(3,34.5,"https://dpic.tiankong.com/fr/6u/QJ8133971620.jpg?x-oss-process=style/240h","东营鱼塘","东营鱼塘水质清透，水生态优良，鱼肥虾美",2,452,600));
            list.add(new HomeBase(4,39.6,"https://dpic2.tiankong.com/xx/dc/QJ6357752798.jpg?x-oss-process=style/240h","阳信冬枣二区","阳信冬枣颗大饱满，清脆可口",1,300,500));

    }
    @TargetApi(Build.VERSION_CODES.M)
    private void initView(){
        areaRecyclerView.setHasFixedSize(true);
        areaAdapter = new AreaAdapter(ListActivity.this,ListActivity.this,list);
        areaRecyclerView.setAdapter(areaAdapter);
        LinearLayoutManager manager = new LinearLayoutManager(ListActivity.this);
        areaRecyclerView.setLayoutManager(manager);
    }

}
