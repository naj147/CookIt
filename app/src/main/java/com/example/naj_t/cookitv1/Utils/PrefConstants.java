package com.example.naj_t.cookitv1.Utils;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Build;
import android.preference.PreferenceManager;

/**
 * Created by naj_t on 06/05/2018.
 */

public class PrefConstants {
    public static int getAppPrefInt(Context context, String prefName){
        int result = -1;
        if(context != null){
            SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(context);
            if(sharedPreferences!=null){
                result = sharedPreferences.getInt(
                        prefName, -1);
            }
        }
        return result;
    }

    public static void putAppPrefInt(Context context, String prefName, int value) {
        if(context!=null){
            SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(context);
            SharedPreferences.Editor edit = sharedPreferences.edit();
            edit.putInt(prefName, value);
            if(Build.VERSION.SDK_INT>=9){
                edit.apply();
            }else{
                edit.commit();
            }
        }
    }
}
