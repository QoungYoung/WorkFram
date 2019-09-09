package com.smile.taobaodemo.ui.fragment;

import android.app.Activity;
import android.content.ClipboardManager;
import android.content.Context;
import android.content.Intent;
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

import com.smile.taobaodemo.R;
import com.smile.taobaodemo.base.Constant;
import com.smile.taobaodemo.bean.logisticsInfo;
import com.smile.taobaodemo.ui.activity.ConfirmSucessActivity;
import com.smile.taobaodemo.ui.adapter.CartAdapter;
import com.smile.taobaodemo.widget.TraceDialog;

import android.widget.Button;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.FindListener;


public class NavCartFragment extends Fragment implements CartAdapter.OnItemClickListener, SwipeRefreshLayout.OnRefreshListener {

    SwipeRefreshLayout refreshLayout;
    @BindView(R.id.cart_recycle)
    RecyclerView cartRecyclerView;
    private CartAdapter cartAdapter;
    private Context context = getContext();
    private Activity activity =getActivity();
    private List<logisticsInfo> logisticsInfolist;

    public static NavCartFragment newInstance() {
        NavCartFragment fragment = new NavCartFragment();
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


        View v = inflater.inflate(R.layout.fragment_navigation_cart, container, false);
        refreshLayout = (SwipeRefreshLayout) v.findViewById(R.id.refresh_cart_layout);
        if(getActivity()!=null){
            cartRecyclerView = (RecyclerView) v.findViewById(R.id.cart_recycle);
            context =getContext();
            activity =getActivity();
            refreshLayout.setColorSchemeResources(R.color.font_orange_color);
            refreshLayout.setOnRefreshListener(this);
            initData();
        }
        return v;
    }

    private void initData() {

        logisticsInfolist = new ArrayList<>();
        BmobQuery<logisticsInfo> BmobQuery = new BmobQuery<>();
        BmobQuery.addWhereEqualTo("userId", Constant.USER.getObjectId());
        BmobQuery.findObjects(new FindListener<logisticsInfo>() {
            @Override
            public void done(List<logisticsInfo> list, BmobException e) {
                if(e == null){
                    Log.d("Logist", "done: "+list.get(0).getLogisticsName());
                    logisticsInfolist = list;
                    cartAdapter = new CartAdapter(context, activity, logisticsInfolist);
                    cartAdapter.setItemClickListener(NavCartFragment.this);
                    cartRecyclerView.setAdapter(cartAdapter);
                    LinearLayoutManager manager = new LinearLayoutManager(context);
                    cartRecyclerView.setLayoutManager(manager);
                }
            }
        });

    }

    @Override
    public void onItemClick(View view, int position) {
        switch (view.getId()){
            case R.id.trace_button:
                Bundle bundle = new Bundle();
                bundle.putString("trackingNumber",logisticsInfolist.get(position).getLogisticsNum());
                loseDialog(bundle);
                break;
            case R.id.trace_copy:
                /*
                * 复制到剪贴板*/
                Button button = (Button) view;
                String str = button.getText().toString();
                ClipboardManager clip = (ClipboardManager)context.getSystemService(Context.CLIPBOARD_SERVICE);
                str = str.replace(" 复制","");
                clip.setText(str);
                Toast.makeText(context,"成功复制"+str+"到剪贴板",Toast.LENGTH_SHORT).show();
                break;
            case R.id.reap:
                confirmDialog();
                break;

        }

    }
    private void loseDialog(Bundle bundle){
        TraceDialog traceDialog = new TraceDialog();
        traceDialog.setArguments(bundle);
        traceDialog.show(activity.getFragmentManager(),"lose");
    }
    private void confirmDialog(){

        Intent intent = new Intent(getActivity(), ConfirmSucessActivity.class);
        startActivity(intent);

//        ConfirmDialog confirmDialog = new ConfirmDialog();
//        confirmDialog.show(activity.getFragmentManager(),"lose");
    }

    @Override
    public void onRefresh() {
        initData();
        setRefreshLoading(false);
    }

    private void setRefreshLoading(boolean isLoading) {
        if (!isLoading) {
            refreshLayout.setRefreshing(false);
        }
    }
}
