package com.smile.taobaodemo.widget;

import android.app.Activity;
import android.app.DialogFragment;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.support.v4.app.FragmentManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.ListView;

import com.smile.taobaodemo.R;
import com.smile.taobaodemo.model.entity.TraceBase;
import com.smile.taobaodemo.ui.adapter.TraceListAdapter;
import com.smile.taobaodemo.ui.fragment.AttachDialogFragment;
import com.smile.taobaodemo.utils.KuaidiBridUtil;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class TraceDialog extends AttachDialogFragment {

    private final Handler mHandler = new Handler();
    private ListView lvTrace;
    private List<TraceBase> traceList = new ArrayList<TraceBase>(10);
    private TraceListAdapter adapter;
    private Context context;
    private Activity activity;
    private String trackingNumber;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setStyle(DialogFragment.STYLE_NO_FRAME, R.style.MyMiddleDialogStyle);
    }
    @RequiresApi(api = Build.VERSION_CODES.M)
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        getDialog().requestWindowFeature(Window.FEATURE_NO_TITLE); //无标题
        getDialog().getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        View view = inflater.inflate(R.layout.cart_search,container);
        lvTrace = (ListView) view.findViewById(R.id.lvTrace);
        //context =getContext();
        activity = getActivity();
        Bundle bundle = getArguments();
        if(bundle!=null){
            trackingNumber = bundle.getString("trackingNumber");
        }
        initData();
        return view;
    }
    @Override
    public void onStart() {
        super.onStart();
        int dialogHeight = (int) (mContext.getResources().getDisplayMetrics().heightPixels * 0.85);
        int dialogWidth = (int) (mContext.getResources().getDisplayMetrics().widthPixels * 0.9);
        getDialog().getWindow().setLayout(dialogWidth,dialogHeight);
        getDialog().setCanceledOnTouchOutside(true); //点击边际可消失
    }
    private void initData() {
        // 根据单号查询物流状态，使用了快递鸟API
            new AsyncTask<String, Void, Object>() {

                //在doInBackground 执行完成后，onPostExecute 方法将被UI 线程调用，
                // 后台的计算结果将通过该方法传递到UI 线程，并且在界面上展示给用户.
                protected void onPostExecute(Object result) {
                    super.onPostExecute(result);
                    adapter = new TraceListAdapter(activity, traceList);
                    lvTrace.setAdapter(adapter);
                }

                //该方法运行在后台线程中，因此不能在该线程中更新UI，UI线程为主线程
                protected Object doInBackground(String... params) {
//						activity_main_btn1.setText("请求结果为："+result);
                    String result = null;
                    try {
                        KuaidiBridUtil api = new KuaidiBridUtil();
                        result = api.getOrderTracesByJson("HTKY", trackingNumber);
                        JSONObject json = new JSONObject(result);
                        if("0".equals(json.getString("State")))
                        {
                            traceList.add(new TraceBase(json.getString("LogisticCode"), json.getString("Reason")+",\t建议您稍后查询"));
                            return result;
                        }
                        JSONArray jsonArray = json.getJSONArray("Traces");
                        for (int i = jsonArray.length()-1; i>= 0; i--) {
                            JSONObject jsonObject = jsonArray.getJSONObject(i);
                            traceList.add(new TraceBase(jsonObject.getString("AcceptTime"), jsonObject.getString("AcceptStation")));
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    return result;
                }

            }.execute();
    }
}
