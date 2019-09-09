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
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import com.smile.taobaodemo.R;
import com.smile.taobaodemo.ui.activity.SucessActivity;
import com.smile.taobaodemo.ui.fragment.AttachDialogFragment;


public class CustomLoseDialog extends AttachDialogFragment implements View.OnClickListener{

    private Button mImg_close;
    private Button mImg_add;
    private Button mImg_mov;
    private EditText editText;
    private int free = 150;//获取空闲土地面积
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
        View view = inflater.inflate(R.layout.lose_custome_dialog,container);
        mImg_close = (Button) view.findViewById(R.id.link_ok);
        mImg_add = (Button) view.findViewById(R.id.dialog_add);
        mImg_mov = (Button) view.findViewById(R.id.mov);
        editText = (EditText) view.findViewById(R.id.number_edit);
        free = 150;//从数据库获取空闲土地面积
        mImg_close.setOnClickListener(this);
        mImg_add.setOnClickListener(this);
        mImg_mov.setOnClickListener(this);
        editText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                String originText = editable.toString();
                if(originText.equals("")){
                    editText.setText(0+"");
                }else if(Integer.valueOf(originText)>free){
                    int temp = free;
                    editText.setText(temp+"");
                }
            }
        });
        return view;
    }

    @Override
    public void onStart() {
        super.onStart();
        int dialogHeight = (int) (mContext.getResources().getDisplayMetrics().heightPixels * 0.4);
        int dialogWidth = (int) (mContext.getResources().getDisplayMetrics().widthPixels * 0.85);
        getDialog().getWindow().setLayout(dialogWidth,dialogHeight);
        getDialog().setCanceledOnTouchOutside(true); //点击边际可消失
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.link_ok:

                /*
                * 携带金额参数跳转到支付界面*/

                /*
                *支付成功后跳转支付成功页面销毁原页面 ，否则返回原页面并提示*/
                Intent intent = new Intent(getActivity(), SucessActivity.class);
                startActivity(intent);
                break;
            case R.id.dialog_add:
                /*
                * 执行加1*/
                if(editText.getText().toString().equals("")){
                    editText.setText(1+"");
                }else {
                    editText.setText(Integer.valueOf(editText.getText().toString())+1+"");
                }
                break;
            case R.id.mov:
                /*
                * 执行减一*/
                if(editText.getText().toString().equals("")||Integer.valueOf(editText.getText().toString())==0){
                    editText.setText(1+"");
                }else {
                    editText.setText(Integer.valueOf(editText.getText().toString())-1+"");
                }
                break;
        }
    }


}
