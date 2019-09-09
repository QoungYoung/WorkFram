package com.smile.taobaodemo.widget;

import android.app.DialogFragment;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;

import com.smile.taobaodemo.R;
import com.smile.taobaodemo.base.Constant;
import com.smile.taobaodemo.bean.Address;
import com.smile.taobaodemo.bean.SeedsInfo;
import com.smile.taobaodemo.bean.logisticsInfo;
import com.smile.taobaodemo.ui.activity.MainActivity;
import com.smile.taobaodemo.ui.activity.SucessActivity;
import com.smile.taobaodemo.ui.fragment.AttachDialogFragment;
import com.smile.taobaodemo.ui.fragment.NavWeFragment;
import com.smile.taobaodemo.utils.ToastUtil;
import com.xiasuhuei321.loadingdialog.view.LoadingDialog;

import java.util.List;

import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.FindListener;
import cn.bmob.v3.listener.QueryListener;
import cn.bmob.v3.listener.UpdateListener;

public class AddressDialog extends AttachDialogFragment implements View.OnClickListener {

    private EditText edit_name;
    private EditText edit_phone;
    private EditText edit_address;
    private Button reap;
    boolean flag = false;
    private LoadingDialog load;
    Address address;
    private final Handler mHandler = new Handler();

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setStyle(DialogFragment.STYLE_NO_FRAME, R.style.MyMiddleDialogStyle);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        getDialog().requestWindowFeature(Window.FEATURE_NO_TITLE); //无标题

        getDialog().getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        View view = inflater.inflate(R.layout.address_dialog,container);
        edit_address = (EditText) view.findViewById(R.id.link_address);
        edit_name = (EditText) view.findViewById(R.id.link_name);
        edit_phone = (EditText) view.findViewById(R.id.link_phpne);
        reap = (Button) view.findViewById(R.id.link_ok);
        load = setLoad(load);
        reap.setOnClickListener(this);
        getAdress();
        return view;
    }

    @Override
    public void onStart() {
        super.onStart();
        int dialogHeight = (int) (mContext.getResources().getDisplayMetrics().heightPixels * 0.5);
        int dialogWidth = (int) (mContext.getResources().getDisplayMetrics().widthPixels * 0.85);
        getDialog().getWindow().setLayout(dialogWidth,dialogHeight);
        getDialog().setCanceledOnTouchOutside(true); //点击边际可消失
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.link_ok:

                if(edit_address.length() >0 && edit_phone.length()==11 && edit_name.length() >0){
                    if(address == null){
                        address.setAddress(edit_address.getText().toString());
                        address.setName(edit_name.getText().toString());
                        address.setPhone(edit_phone.getText().toString());
                        address.setUserId(Constant.USER.getObjectId());
                        address.save();

                    }
                    reap();
                    dismiss();
                }else {

                    ToastUtil.showShortToast(getActivity(),"请仔细检查输入是否有误"+edit_address.getTextSize()+"   "+edit_phone.getTextSize()+"    "+edit_name.getTextSize());
                }






        }
    }

    public void getAdress(){

        BmobQuery<Address> query = new BmobQuery<>();
        query.addWhereEqualTo("userId", Constant.USER.getObjectId());
        query.findObjects(new FindListener<Address>() {
            @Override
            public void done(List<Address> list, BmobException e) {
                if(e == null){
                    if(list.size()>0){
                        address = list.get(0);
                        ToastUtil.showShortToast(getActivity(),"系统自动为您填写上次保存的收货地址");
                        edit_name.setText(address.getName());
                        edit_phone.setText(address.getPhone());
                        edit_address.setText(address.getAddress());
                        flag = true;
                    }else {
                        ToastUtil.showShortToast(getActivity(),"系统检测到您是第一次收货，在收货完成后会为您自动保存本次地址");
                    }
                }else {
                    ToastUtil.showShortToast(getActivity(),"出现错误："+e.getMessage());
                }
            }
        });

    }

    public void  reap(){
        load.show();
        final SeedsInfo info = new SeedsInfo();
        info.setObjectId(getTag());
        BmobQuery<SeedsInfo> bmobQuery = new BmobQuery<SeedsInfo>();
        bmobQuery.getObject(info.getObjectId(), new QueryListener<SeedsInfo>() {
            @Override
            public void done(SeedsInfo seedsInfo, BmobException e) {
                if(e == null){
                    final logisticsInfo logisticsInfo = new logisticsInfo();
                    logisticsInfo.setUserId(Constant.USER.getObjectId());
                    logisticsInfo.setLogisticsName(seedsInfo.getSeedName());
                    logisticsInfo.setNum(""+seedsInfo.getSeedArea());
                    logisticsInfo.setLogisticsNum("稍后为您发货");
                    logisticsInfo.setUrl(seedsInfo.getUrl());

                    seedsInfo.delete(new UpdateListener() {
                        @Override
                        public void done(BmobException e) {
                            if(e == null){
                                logisticsInfo.save();
                                load.loadSuccess();
                            }else {
                                load.loadFailed();
                            }
                        }
                    });

                }
            }
        });
    }

    private LoadingDialog setLoad(LoadingDialog load){

        load = new LoadingDialog(getActivity());
        load.setLoadingText("正在收获...");
        load.setSuccessText("收获成功");//显示加载成功时的文字
        load.setFailedText("收获失败");
        load.setLoadSpeed(LoadingDialog.Speed.SPEED_TWO);
        return load;

    }


}
