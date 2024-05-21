package com.xiaopeng.commonfunc.utils;

import android.net.Uri;
import com.xiaopeng.lib.apirouter.ApiRouter;
import com.xiaopeng.lib.utils.LogUtils;
import com.xiaopeng.libconfig.ipc.AccountConfig;
/* loaded from: classes4.dex */
public class AgingTestHelper {
    public static final String AUTHORITY_AGING_TEST = "com.xpeng.qa.aging.RemoteService";
    private static final String TAG = "AgingTestHelper";

    public static boolean startAgingTest() {
        boolean state = false;
        Uri.Builder builder = new Uri.Builder();
        builder.authority(AUTHORITY_AGING_TEST).path(AccountConfig.FaceIDRegisterAction.STATUS_START);
        try {
            state = ((Boolean) ApiRouter.route(builder.build())).booleanValue();
        } catch (Exception e) {
            LogUtils.e(TAG, "startAgingTest Exception : " + e.getMessage());
        }
        LogUtils.i(TAG, "Aging Test Start State = " + state);
        return state;
    }

    public static int getAgingTestCurrState() {
        int state = Integer.MIN_VALUE;
        Uri.Builder builder = new Uri.Builder();
        builder.authority(AUTHORITY_AGING_TEST).path("getCurrState");
        try {
            state = ((Integer) ApiRouter.route(builder.build())).intValue();
        } catch (Exception e) {
            LogUtils.e(TAG, "getAgingTestCurrState Exception : " + e.getMessage());
        }
        LogUtils.i(TAG, "Aging Test Current State = " + state);
        return state;
    }

    public static int getAgingTestFailType() {
        int type = Integer.MIN_VALUE;
        Uri.Builder builder = new Uri.Builder();
        builder.authority(AUTHORITY_AGING_TEST).path("getFailType");
        try {
            type = ((Integer) ApiRouter.route(builder.build())).intValue();
        } catch (Exception e) {
            LogUtils.e(TAG, "getAgingTestFailType Exception : " + e.getMessage());
        }
        LogUtils.i(TAG, "Aging Test Fail Type = " + type);
        return type;
    }

    public static boolean interruptAgingTest() {
        boolean state = false;
        Uri.Builder builder = new Uri.Builder();
        builder.authority(AUTHORITY_AGING_TEST).path("interrupt");
        try {
            state = ((Boolean) ApiRouter.route(builder.build())).booleanValue();
        } catch (Exception e) {
            LogUtils.e(TAG, "interruptAgingTest Exception : " + e.getMessage());
        }
        LogUtils.i(TAG, "Aging Test Interrupt = " + state);
        return state;
    }

    public static boolean reTest() {
        boolean state = false;
        Uri.Builder builder = new Uri.Builder();
        builder.authority(AUTHORITY_AGING_TEST).path("reTest");
        try {
            state = ((Boolean) ApiRouter.route(builder.build())).booleanValue();
        } catch (Exception e) {
            LogUtils.e(TAG, "reTest Exception : " + e.getMessage());
        }
        LogUtils.i(TAG, "Aging Test ReTest = " + state);
        return state;
    }
}
