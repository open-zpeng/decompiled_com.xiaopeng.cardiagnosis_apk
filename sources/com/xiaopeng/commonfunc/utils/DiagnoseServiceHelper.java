package com.xiaopeng.commonfunc.utils;

import android.net.Uri;
import com.google.gson.Gson;
import com.xiaopeng.commonfunc.bean.VcuModeResponse;
import com.xiaopeng.lib.apirouter.ApiRouter;
import com.xiaopeng.lib.utils.LogUtils;
/* loaded from: classes4.dex */
public class DiagnoseServiceHelper {
    public static final String AUTHORITY_DIAGNOSE_SERVICE = "com.xiaopeng.diagnostic.DiagnoseService";
    public static final int DEFAULT_MODE = 0;
    public static final int FACTORY_MODE = 1;
    private static final String TAG = "DiagnoseServiceHelper";
    public static final int UNKNOWN_MODE = -1;

    public static int getFactoryMode() {
        VcuModeResponse response;
        int mode = -1;
        Uri.Builder builder = new Uri.Builder();
        builder.authority(AUTHORITY_DIAGNOSE_SERVICE).path("getFactoryMode");
        try {
            String data = (String) ApiRouter.route(builder.build());
            LogUtils.d(TAG, "getFactoryMode: " + data);
            response = (VcuModeResponse) new Gson().fromJson(data, (Class<Object>) VcuModeResponse.class);
            LogUtils.d(TAG, "getFactoryMode: " + response.toString());
        } catch (Exception e) {
            LogUtils.e(TAG, "Get Factory Mode Exception : " + e.getMessage());
        }
        if (response.getCode() == null) {
            return -1;
        }
        if (response.getCode().intValue() == 0) {
            mode = response.getMode().intValue();
        } else {
            LogUtils.e(TAG, "Get Factory Mode Fail : " + response.getMessage());
        }
        return mode;
    }
}
