package com.smile.taobaodemo.ui.fragment;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.DialogInterface;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import com.smile.taobaodemo.R;
import com.smile.taobaodemo.base.Constant;
import com.smile.taobaodemo.utils.ToastUtil;

import butterknife.BindView;
import butterknife.ButterKnife;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.UpdateListener;


public class SetNameDialog extends DialogFragment {

    private View view;
    private EditText nameEdit;
    private Button save;
    private Dialog meserr;
    // 回调接口，用于传递数据给Activity -------

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
    }
    @Override
    public View onCreateView(final LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        view = inflater.inflate(R.layout.dialog_set_name, container);
        nameEdit = (EditText) view.findViewById(R.id.edit_name);
        save = (Button) view.findViewById(R.id.save_name);
            meserr = new AlertDialog.Builder(getActivity()).setTitle("错误！")
                    .setMessage("请先将信息填写完整！")
                    .setNegativeButton("知道了", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int which) {

                        }
                    }).create();
            getDialog().setTitle("设置新昵称");
            nameEdit.setText(Constant.USER.getUsername());

        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if(nameEdit.length()>0){
                    Constant.USER.setUsername(nameEdit.getText().toString());
                    Constant.USER.update(new UpdateListener() {
                        @Override
                        public void done(BmobException e) {
                            if(e == null){
                                ToastUtil.showShortToast(getActivity(),"成功修改昵称，请重启应用后查看！");
                                Log.d("Save", "done: 成功修改昵称，请重启应用后查看！");
                                onDestroy();
                            }else {
                                Log.d("Save", "done: "+e.getMessage());
                                ToastUtil.showShortToast(getActivity(),"修改昵称失败，请检查网络连接或者重启应用");
                            }
                        }
                    });

                }else {
                    meserr.show();
                }
            }
        });

        return view;
    }


}
