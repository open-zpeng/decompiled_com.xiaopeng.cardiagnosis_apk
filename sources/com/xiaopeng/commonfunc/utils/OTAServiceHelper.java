package com.xiaopeng.commonfunc.utils;

import android.net.Uri;
import com.google.gson.Gson;
import com.xiaopeng.commonfunc.bean.VcuModeResponse;
import com.xiaopeng.commonfunc.system.runnable.Sleep;
import com.xiaopeng.lib.apirouter.ApiRouter;
import com.xiaopeng.lib.utils.LogUtils;
import com.xiaopeng.xmlconfig.Support;
/* loaded from: classes4.dex */
public class OTAServiceHelper {
    public static final int FACTORY_MODE = 0;
    public static final int NORMAL_MODE = 2;
    public static final String OTHER = "other";
    public static final String POWERON = "powerOn";
    public static final int REPAIR_MODE = 3;
    private static final String TAG = "OTAServiceHelper";
    public static final int TANSPORT_MODE = 1;
    public static final int UNKNOWN_MODE = -1;
    public static final String AUTHORITY_VCU_SERVICE = Support.Spec.getString(Support.Spec.SET_VCU_SERVICE);
    public static final Boolean USE_CAR_DIAGNOSTIC_SET = Boolean.valueOf(DiagnoseServiceHelper.AUTHORITY_DIAGNOSE_SERVICE.equals(Support.Spec.getString(Support.Spec.SET_VCU_SERVICE)));
    private static final boolean SUPPORT_SPEED_LIMIT = Support.Feature.getBoolean(Support.Feature.SUPPORT_SPEED_LIMIT);

    public static int getVcuMode() {
        Uri.Builder builder = new Uri.Builder();
        builder.authority(AUTHORITY_VCU_SERVICE).path("getVcuMode");
        try {
            String data = (String) ApiRouter.route(builder.build());
            LogUtils.i(TAG, "getVcuMode, data: " + data);
            VcuModeResponse response = (VcuModeResponse) new Gson().fromJson(data, (Class<Object>) VcuModeResponse.class);
            LogUtils.d(TAG, "getVcuMode, response: " + response.toString());
            if (response.getCode() == null) {
                return -1;
            }
            if (response.getCode().intValue() == 0) {
                int mode = response.getMode().intValue();
                return mode;
            } else if (response.getCode().intValue() == 5) {
                Sleep.sleep(2000L);
                return reTryGetVcuMode(1);
            } else {
                LogUtils.e(TAG, "Get Vcu Mode Fail : " + response.getMessage());
                return -1;
            }
        } catch (Exception e) {
            LogUtils.e(TAG, "Get Vcu Mode Exception : " + e.getMessage());
            return -1;
        }
    }

    private static int reTryGetVcuMode(int reTryCount) {
        VcuModeResponse response;
        String str = TAG;
        if (reTryCount > 4) {
            return -1;
        }
        int mode = -1;
        Uri.Builder builder = new Uri.Builder();
        builder.authority(AUTHORITY_VCU_SERVICE).path("getVcuMode");
        try {
            String data = (String) ApiRouter.route(builder.build());
            LogUtils.i(TAG, "reTryGetVcuMode, data: " + data);
            response = (VcuModeResponse) new Gson().fromJson(data, (Class<Object>) VcuModeResponse.class);
            LogUtils.d(TAG, "reTryGetVcuMode, response: " + response.toString());
        } catch (Exception e) {
            LogUtils.e(str, "reTryGetVcuMode Vcu Mode Exception : " + e.getMessage());
        }
        if (response.getCode() == null) {
            return -1;
        }
        if (response.getCode().intValue() == 0) {
            int intValue = response.getMode().intValue();
            mode = intValue;
            str = intValue;
        } else if (response.getCode().intValue() == 5) {
            Sleep.sleep(2000L);
            reTryGetVcuMode(reTryCount + 1);
            str = str;
        } else {
            LogUtils.e(TAG, "reTryGetVcuMode Vcu Mode Fail : " + response.getMessage());
            str = str;
        }
        return mode;
    }

    public static boolean setVcuModel(int mode, String source) {
        Uri.Builder builder = new Uri.Builder();
        if (USE_CAR_DIAGNOSTIC_SET.booleanValue()) {
            builder.authority(AUTHORITY_VCU_SERVICE).path("setVcuMode").appendQueryParameter("mode", String.valueOf(mode)).appendQueryParameter("source", String.valueOf(source));
        } else {
            builder.authority(AUTHORITY_VCU_SERVICE).path("setVcuMode").appendQueryParameter("mode", String.valueOf(mode));
        }
        try {
            String data = (String) ApiRouter.route(builder.build());
            LogUtils.i(TAG, "setVcuMode: " + mode + " data: " + data);
            VcuModeResponse response = (VcuModeResponse) new Gson().fromJson(data, (Class<Object>) VcuModeResponse.class);
            LogUtils.d(TAG, "setVcuMode: " + mode + "response: " + response.toString());
            if (response.getCode() == null) {
                return false;
            }
            if (response.getCode().intValue() == 0) {
                setReportStatus();
                return true;
            }
            LogUtils.e(TAG, "Set Vcu Mode Fail : " + response.getMessage());
            return false;
        } catch (Exception e) {
            LogUtils.e(TAG, "Set Vcu Mode Exception : " + e.getMessage());
            return false;
        }
    }

    private static void setReportStatus() {
        if (SUPPORT_SPEED_LIMIT) {
            if (!SystemPropertyUtil.getUploadRepairModeSended()) {
                LogUtils.i(TAG, "herz setUploadRepairModeSended ");
                SystemPropertyUtil.setUploadRepairModeSended(true);
            }
        } else if (!SystemPropertyUtil.getRepairModeSended()) {
            LogUtils.i(TAG, "herz setRepairModeSended ");
            SystemPropertyUtil.setRepairModeSended(true);
        }
    }
}
