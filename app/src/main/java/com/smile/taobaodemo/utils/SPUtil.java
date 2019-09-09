package com.smile.taobaodemo.utils;

import android.app.Activity;
import android.content.SharedPreferences;

import static android.content.Context.MODE_PRIVATE;

public class SPUtil {

    public static String get(Activity activity,String key,String value){

        SharedPreferences pref = activity.getSharedPreferences(key,MODE_PRIVATE);
        return pref.getString(value," ");
    }


}
