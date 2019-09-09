package com.smile.taobaodemo.ui.fragment;

import android.annotation.TargetApi;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ScrollView;
import android.widget.TextView;

import com.smile.taobaodemo.R;
import com.smile.taobaodemo.base.Constant;
import com.smile.taobaodemo.bean.PersonAreaInfo;
import com.smile.taobaodemo.bean.SeedsInfo;
import com.smile.taobaodemo.ui.activity.BuyingSeedsActivity;
import com.smile.taobaodemo.ui.adapter.SeedsAdapter;
import com.smile.taobaodemo.widget.AddressDialog;

import java.util.ArrayList;
import java.util.List;

import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.FindListener;

/**
 * @author Smile Wei
 * @since 2017/3/2.
 */

public class NavWeFragment extends Fragment implements SwipeRefreshLayout.OnRefreshListener,SeedsAdapter.OnItemClickListener {

    RecyclerView seedsRecyclerView;
    private SeedsAdapter seedsAdapter;
    private List<SeedsInfo> seedsList;
    TextView areaName;
    TextView areaNo;
    TextView countArea;
    TextView usedArea;
    TextView freeArea;
    TextView freeDay;
    ScrollView scrollView;
    Button buyyingSeeds;
    int freeAreaNum = 0;
    SwipeRefreshLayout refreshLayout;

    public static NavWeFragment newInstance() {
        NavWeFragment fragment = new NavWeFragment();
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
    public View onCreateView(final LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_navigation_we, container, false);
        seedsRecyclerView = (RecyclerView) v.findViewById(R.id.seeds_list);
        seedsList = new ArrayList<SeedsInfo>();
        refreshLayout = (SwipeRefreshLayout) v.findViewById(R.id.refresh_we_layout);
        areaName = (TextView) v.findViewById(R.id.land_name);
        areaNo = (TextView) v.findViewById(R.id.land_id);
        countArea = (TextView) v.findViewById(R.id.land_count_area);
        usedArea = (TextView) v.findViewById(R.id.land_used_area);
        freeArea = (TextView) v.findViewById(R.id.land_free_area);
        freeDay = (TextView) v.findViewById(R.id.land_date);
        scrollView = (ScrollView) v.findViewById(R.id.scrollView3);
        buyyingSeeds = (Button) v.findViewById(R.id.buying_seeds);
        buyyingSeeds.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(), BuyingSeedsActivity.class);
                intent.putExtra("freeArea",freeAreaNum);
                startActivity(intent);
            }
        });
        refreshLayout.setColorSchemeResources(R.color.font_orange_color);
        refreshLayout.setOnRefreshListener(this);
        initView();
        initData();
        return v;
    }

    private void initData() {

        BmobQuery<SeedsInfo> BmobQuery = new BmobQuery<>();
        BmobQuery.addWhereEqualTo("userId", Constant.USER.getObjectId());
        BmobQuery.findObjects(new FindListener<SeedsInfo>() {
            @Override
            public void done(List<SeedsInfo> list, BmobException e) {

                if(e==null){
                    if(list.size() == 0){
                        seedsRecyclerView.setVisibility(View.GONE);
                    }else {
                        for (SeedsInfo seedsInfo:list){
                            seedsList.add(seedsInfo);
                        }
                        seedsRecyclerView.setHasFixedSize(true);
                        seedsAdapter = new SeedsAdapter(getActivity(),getActivity(),seedsList);
                        seedsRecyclerView.setAdapter(seedsAdapter);
                        seedsAdapter.setOnItemClickListener(NavWeFragment.this);
                        LinearLayoutManager manager = new LinearLayoutManager(getActivity());
                        seedsRecyclerView.setLayoutManager(manager);
                    }
                }else {
                    Log.d("getSeedsList ERROR", "done: "+e.getMessage());
                }


            }
        });

    }
    @TargetApi(Build.VERSION_CODES.M)
    private void initView() {

        BmobQuery<PersonAreaInfo> BmobQuery = new BmobQuery<>();
        BmobQuery.addWhereEqualTo("userId", Constant.USER.getObjectId());
        BmobQuery.findObjects(new FindListener<PersonAreaInfo>() {
            @Override
            public void done(List<PersonAreaInfo> list, BmobException e) {

                if(e==null){
                    if(list.size()==0){
                        scrollView.setVisibility(View.GONE);
                        buyyingSeeds.setVisibility(View.GONE);

                    }else {
                        scrollView.setVisibility(View.VISIBLE);
                        areaName.setText(list.get(0).getAreaName());
                        areaNo.setText(list.get(0).getAreaName()+"_"+String.valueOf(list.get(0).getAreaNo()));
                        countArea.setText(String.valueOf(list.get(0).getCountArea())+"㎡");
                        usedArea.setText(String.valueOf(list.get(0).getUsedArea())+"㎡");
                        freeArea.setText(String.valueOf(list.get(0).getFreedArea())+"㎡");
                        freeAreaNum = list.get(0).getFreedArea();
                        freeDay.setText(String.valueOf(list.get(0).getFreeDay())+"天");
                        Constant.PERSON_AREA_INFO = list.get(0);
                    }
                }else {
                    Log.d("getLand ERROR", "done: "+e.getMessage());
                    scrollView.setVisibility(View.GONE);
                    buyyingSeeds.setVisibility(View.GONE);
                }


            }
        });
    }

    private void setRefreshLoading(boolean isLoading) {
        if (!isLoading) {
            refreshLayout.setRefreshing(false);
        }
    }

    @Override
    public void onRefresh() {
        seedsList.clear();
        initData();
        initView();
        setRefreshLoading(false);
    }

    @Override
    public void onItemClick(View view, int position, String s) {
        if(view.getId() == R.id.reap){
            AddressDialog addressDialog = new AddressDialog();
            addressDialog.show(getActivity().getFragmentManager(),s);
        }
    }
}
