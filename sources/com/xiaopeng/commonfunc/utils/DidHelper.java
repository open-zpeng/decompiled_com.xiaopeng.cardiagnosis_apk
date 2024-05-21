package com.xiaopeng.commonfunc.utils;

import android.net.Uri;
import android.util.Base64;
import com.google.gson.Gson;
import com.xiaopeng.commonfunc.bean.car.DidRequest;
import com.xiaopeng.commonfunc.bean.car.DidResponse;
import com.xiaopeng.lib.apirouter.ApiRouter;
import com.xiaopeng.lib.utils.LogUtils;
/* loaded from: classes4.dex */
public class DidHelper {
    public static final String AUTHORITY_OTA_SERVICE = "com.xiaopeng.ota.OTAService";
    public static final int DID_CDU_FAULT_CODE = 1024;
    public static final int DID_PART_NUMBER = 61831;
    public static final int ECU_ADDRESS_AMCU = 43;
    public static final int ECU_ADDRESS_CDU = 45;
    public static final int ECU_ADDRESS_ICM = 40;
    public static final int ECU_ADDRESS_T4G = 44;
    public static final int ECU_ADDRESS_TMCU = 46;
    public static final int ECU_ADDRESS_XPU = 22;
    private static final String TAG = "DidHelper";

    public static byte[] readDid(int address, int did) {
        byte[] res = null;
        DidRequest request = new DidRequest(address, did);
        Uri.Builder builder = new Uri.Builder();
        builder.authority("com.xiaopeng.ota.OTAService").path("readDid").appendQueryParameter("requestJsonContent", new Gson().toJson(request));
        try {
            DidResponse response = (DidResponse) new Gson().fromJson((String) ApiRouter.route(builder.build()), (Class<Object>) DidResponse.class);
            if (response.getCode() == 0 && response.getAddress() == address && response.getDid() == did) {
                res = Base64.decode(response.getValue(), 0);
            } else {
                LogUtils.e(TAG, "read did fail : " + response.getMessage());
            }
        } catch (Exception e) {
            LogUtils.e(TAG, "read did Exception : " + e.getMessage());
        }
        LogUtils.i(TAG, "readDid address: " + address + ", did: " + did + ", res : " + DataHelp.byteArrayToHexStr(res, true));
        return res;
    }
}
