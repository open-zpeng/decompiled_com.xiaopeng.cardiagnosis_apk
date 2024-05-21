package com.xiaopeng.commonfunc.utils;

import android.content.Context;
import android.content.Intent;
/* loaded from: classes4.dex */
public class BroadcastUtil {
    public static final String ACTION_CHANGE_LOG_DIR_PERMISSION = "com.xiaopeng.action.CHANGE_LOG_PERMISSION";
    private static final String TAG = "BroadcastUtil";

    public static void requestChangeNaviLogPermission(Context context) {
        Intent intent = new Intent(ACTION_CHANGE_LOG_DIR_PERMISSION);
        intent.setPackage("com.telenav.app.arp");
        context.sendBroadcast(intent);
    }
}
