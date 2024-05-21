package com.xiaopeng.commonfunc.utils;

import android.content.Context;
import android.content.SharedPreferences;
import com.xiaopeng.commonfunc.Constant;
/* loaded from: classes4.dex */
public class SharedUtils {
    private static SharedUtils sSharedUtils;
    private final SharedPreferences mSharedPreferences;

    private SharedUtils(Context context) {
        this.mSharedPreferences = context.getSharedPreferences(Constant.XP_SHARED_PREFERENCE, 0);
    }

    public static SharedUtils getInstance(Context context) {
        if (sSharedUtils == null) {
            sSharedUtils = new SharedUtils(context);
        }
        return sSharedUtils;
    }

    public void putBoolean(String key, boolean flag) {
        this.mSharedPreferences.edit().putBoolean(key, flag).commit();
    }

    public boolean getBoolean(String key) {
        return this.mSharedPreferences.getBoolean(key, false);
    }

    public boolean getBoolean(String key, boolean def) {
        return this.mSharedPreferences.getBoolean(key, def);
    }

    public void putInt(String key, int value) {
        this.mSharedPreferences.edit().putInt(key, value).commit();
    }

    public int getInt(String key) {
        return this.mSharedPreferences.getInt(key, -1);
    }

    public SharedPreferences getmSharedPreferences() {
        return this.mSharedPreferences;
    }
}
