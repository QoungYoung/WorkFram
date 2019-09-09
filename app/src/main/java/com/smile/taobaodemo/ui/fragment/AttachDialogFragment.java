package com.smile.taobaodemo.ui.fragment;

import android.app.Activity;
import android.app.DialogFragment;

public class AttachDialogFragment extends DialogFragment {

    public Activity mContext;

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        this.mContext = activity;
    }
}