package com.smile.taobaodemo.widget;
import android.app.DialogFragment;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.smile.taobaodemo.R;
import com.smile.taobaodemo.model.entity.PayPsdInputView;
import com.smile.taobaodemo.ui.activity.ConfirmSucessActivity;
import com.smile.taobaodemo.ui.fragment.AttachDialogFragment;


public class ConfirmDialog extends AttachDialogFragment{

    private PayPsdInputView payPsdInputView;


    private final Handler mHandler = new Handler();

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setStyle(DialogFragment.STYLE_NO_FRAME, R.style.Dialog_Fullscreen_Bottom);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        getDialog().requestWindowFeature(Window.FEATURE_NO_TITLE); //无标题
        getDialog().getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        View view = inflater.inflate(R.layout.fragmentdialog_confrim,container);
        payPsdInputView = (PayPsdInputView) view.findViewById(R.id.pay_password);
        Window window = getDialog().getWindow();
        WindowManager.LayoutParams params = window.getAttributes();
        params.gravity = Gravity.BOTTOM;
        /*
        * 支付密码输入后的校对
        * */
        payPsdInputView.setComparePassword("123456",new PayPsdInputView.onPasswordListener() {

            @Override
            public void onDifference(String oldPsd, String newPsd) {
                Toast.makeText(getActivity(),"密码输入错误！请重新输入密码",Toast.LENGTH_SHORT).show();
                payPsdInputView.cleanPsd();
            }

            @Override
            public void onEqual(String psd) {
                Intent intent = new Intent(getActivity(), ConfirmSucessActivity.class);
                getDialog().dismiss();
                startActivity(intent);
            }

            @Override
            public void inputFinished(String inputPsd) {
                Toast.makeText(getActivity(),"这里是输入完成后的密码："+inputPsd,Toast.LENGTH_SHORT).show();
            }
        });
        return view;
    }

    @Override
    public void onStart() {
        super.onStart();
        int dialogHeight = (int) (mContext.getResources().getDisplayMetrics().heightPixels * 0.6);
        int dialogWidth = (int) (mContext.getResources().getDisplayMetrics().widthPixels);
        getDialog().getWindow().setLayout(dialogWidth,dialogHeight);
        getDialog().setCanceledOnTouchOutside(true); //点击边际可消失
    }
}
