package com.xiaopeng.commonfunc.model.security;

import android.content.Context;
import android.util.Base64;
import com.google.gson.Gson;
import com.xiaopeng.commonfunc.Constant;
import com.xiaopeng.commonfunc.bean.event.SSLKeyEvent;
import com.xiaopeng.commonfunc.model.keystore.KeyStoreModel;
import com.xiaopeng.commonfunc.model.security.WifiKeyModel;
import com.xiaopeng.commonfunc.utils.DataHelp;
import com.xiaopeng.commonfunc.utils.FileUtil;
import com.xiaopeng.commonfunc.utils.IndivHelper;
import com.xiaopeng.commonfunc.utils.SystemPropertyUtil;
import com.xiaopeng.lib.framework.module.Module;
import com.xiaopeng.lib.framework.moduleinterface.netchannelmodule.http.Callback;
import com.xiaopeng.lib.framework.moduleinterface.netchannelmodule.http.IHttp;
import com.xiaopeng.lib.framework.moduleinterface.netchannelmodule.http.IRequest;
import com.xiaopeng.lib.framework.moduleinterface.netchannelmodule.http.IResponse;
import com.xiaopeng.lib.framework.netchannelmodule.NetworkChannelsEntry;
import com.xiaopeng.lib.utils.LogUtils;
import com.xiaopeng.lib.utils.ThreadUtils;
import com.xiaopeng.lib.utils.info.BuildInfoUtils;
import com.xiaopeng.xmlconfig.Support;
import java.util.HashMap;
import java.util.Map;
import org.greenrobot.eventbus.EventBus;
import org.json.JSONObject;
/* loaded from: classes4.dex */
public class WifiKeyModel {
    private static final String TAG = "WifiKeyModel";
    public static final String WIFI_CLIENT_CERT_PATH = "/mnt/vendor/private/ck58l92i5/wf_c.png";
    public static final String WIFI_ROOT_BKS_CERT_PATH = "/mnt/vendor/private/ck58l92i5/wf_r_bks.png";
    public static final String WIFI_ROOT_CERT_PATH = "/mnt/vendor/private/ck58l92i5/wf_r.png";
    private Context mContext;
    private boolean mIsChecking;
    private final KeyStoreModel mKeyStoreModel;
    private boolean mNeedRequestWifiKey = true;

    public WifiKeyModel(Context context) {
        this.mContext = context;
        this.mKeyStoreModel = new KeyStoreModel(context);
    }

    public void onDestroy() {
    }

    public boolean isWifiKeyExist() {
        return FileUtil.isExistFilePath(WIFI_CLIENT_CERT_PATH) && FileUtil.isExistFilePath(WIFI_ROOT_CERT_PATH) && FileUtil.isExistFilePath(WIFI_ROOT_BKS_CERT_PATH);
    }

    public void saveWifiKey(JSONObject jsonObject) {
        try {
            String pkcs12 = jsonObject.getString(Constant.PKCS12);
            String caPem = jsonObject.getString(Constant.CAPEM);
            String caBks = jsonObject.getString(Constant.CABKS);
            LogUtils.v(TAG, "saveWifiKey pkcs12.length:" + pkcs12.length() + ", caPem.length:" + caPem.length() + ", caBks.length:" + caBks.length());
            FileUtil.writeNCreateFile(WIFI_CLIENT_CERT_PATH, pkcs12);
            FileUtil.writeNCreateFile(WIFI_ROOT_CERT_PATH, caPem);
            FileUtil.writeNCreateFile(WIFI_ROOT_BKS_CERT_PATH, caBks);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void asyncGetKey(final Runnable callback) {
        LogUtils.v(TAG, "asyncGetKey");
        if (this.mIsChecking) {
            return;
        }
        this.mIsChecking = true;
        this.mNeedRequestWifiKey = true;
        ThreadUtils.execute(new Runnable() { // from class: com.xiaopeng.commonfunc.model.security.-$$Lambda$WifiKeyModel$1jcZMK2bXnY9uvJ5NwF8PEaTXcw
            @Override // java.lang.Runnable
            public final void run() {
                WifiKeyModel.this.lambda$asyncGetKey$0$WifiKeyModel(callback);
            }
        });
    }

    /* JADX WARN: Code restructure failed: missing block: B:10:0x0036, code lost:
        com.xiaopeng.lib.utils.LogUtils.i(com.xiaopeng.commonfunc.model.security.WifiKeyModel.TAG, "get key");
     */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct add '--show-bad-code' argument
    */
    public /* synthetic */ void lambda$asyncGetKey$0$WifiKeyModel(java.lang.Runnable r9) {
        /*
            r8 = this;
            java.lang.String r0 = "WifiKeyModel"
            r1 = 5
            r2 = 0
        L4:
            int r3 = r2 + 1
            r4 = 5
            r5 = 0
            if (r2 >= r4) goto L51
            java.lang.StringBuilder r2 = new java.lang.StringBuilder     // Catch: java.lang.Throwable -> L3e java.lang.Exception -> L40
            r2.<init>()     // Catch: java.lang.Throwable -> L3e java.lang.Exception -> L40
            java.lang.String r4 = "mNeedRequestWifiKey = "
            r2.append(r4)     // Catch: java.lang.Throwable -> L3e java.lang.Exception -> L40
            boolean r4 = r8.mNeedRequestWifiKey     // Catch: java.lang.Throwable -> L3e java.lang.Exception -> L40
            r2.append(r4)     // Catch: java.lang.Throwable -> L3e java.lang.Exception -> L40
            java.lang.String r2 = r2.toString()     // Catch: java.lang.Throwable -> L3e java.lang.Exception -> L40
            com.xiaopeng.lib.utils.LogUtils.i(r0, r2)     // Catch: java.lang.Throwable -> L3e java.lang.Exception -> L40
            boolean r2 = r8.mNeedRequestWifiKey     // Catch: java.lang.Throwable -> L3e java.lang.Exception -> L40
            if (r2 == 0) goto L2b
            r8.mNeedRequestWifiKey = r5     // Catch: java.lang.Throwable -> L3e java.lang.Exception -> L40
            android.content.Context r2 = r8.mContext     // Catch: java.lang.Throwable -> L3e java.lang.Exception -> L40
            r8.reqWifiKey(r2)     // Catch: java.lang.Throwable -> L3e java.lang.Exception -> L40
        L2b:
            r6 = 10500(0x2904, double:5.1877E-320)
            java.lang.Thread.sleep(r6)     // Catch: java.lang.Throwable -> L3e java.lang.Exception -> L40
            boolean r2 = r8.isWifiKeyExist()     // Catch: java.lang.Throwable -> L3e java.lang.Exception -> L40
            if (r2 == 0) goto L3c
            java.lang.String r2 = "get key"
            com.xiaopeng.lib.utils.LogUtils.i(r0, r2)     // Catch: java.lang.Throwable -> L3e java.lang.Exception -> L40
            goto L51
        L3c:
            r2 = r3
            goto L4
        L3e:
            r0 = move-exception
            goto L49
        L40:
            r0 = move-exception
            r0.printStackTrace()     // Catch: java.lang.Throwable -> L3e
            r8.mIsChecking = r5
            if (r9 == 0) goto L58
            goto L55
        L49:
            r8.mIsChecking = r5
            if (r9 == 0) goto L50
            com.xiaopeng.lib.utils.ThreadUtils.execute(r9)
        L50:
            throw r0
        L51:
            r8.mIsChecking = r5
            if (r9 == 0) goto L58
        L55:
            com.xiaopeng.lib.utils.ThreadUtils.execute(r9)
        L58:
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: com.xiaopeng.commonfunc.model.security.WifiKeyModel.lambda$asyncGetKey$0$WifiKeyModel(java.lang.Runnable):void");
    }

    private void reqWifiKey(Context context) {
        Map<String, String> param = new HashMap<>();
        param.put("app_id", "xmart:appid:002");
        param.put("cduId", SystemPropertyUtil.getHardwareId());
        long time = System.currentTimeMillis();
        param.put("timestamp", String.valueOf(time));
        String sign = IndivHelper.sign(context, param, time);
        param.put("sign", sign);
        IHttp http = (IHttp) Module.get(NetworkChannelsEntry.class).get(IHttp.class);
        IRequest uploadJson = http.post(Support.Url.getUrl(Support.Url.REQUEST_WIFI_KEY)).uploadJson(new Gson().toJson(param));
        uploadJson.headers("Client", Constant.HTTP_CAR_XMART + BuildInfoUtils.getSystemVersion()).execute(new AnonymousClass1(sign));
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    /* renamed from: com.xiaopeng.commonfunc.model.security.WifiKeyModel$1  reason: invalid class name */
    /* loaded from: classes4.dex */
    public class AnonymousClass1 implements Callback {
        final /* synthetic */ String val$sign;

        AnonymousClass1(String str) {
            this.val$sign = str;
        }

        public /* synthetic */ void lambda$onSuccess$0$WifiKeyModel$1(IResponse iResponse, String sign) {
            WifiKeyModel.this.dealResponse(iResponse.body(), sign);
        }

        @Override // com.xiaopeng.lib.framework.moduleinterface.netchannelmodule.http.Callback
        public void onSuccess(final IResponse iResponse) {
            final String str = this.val$sign;
            ThreadUtils.execute(new Runnable() { // from class: com.xiaopeng.commonfunc.model.security.-$$Lambda$WifiKeyModel$1$DZpO8FjkbBphtZPMVwgn48zssNY
                @Override // java.lang.Runnable
                public final void run() {
                    WifiKeyModel.AnonymousClass1.this.lambda$onSuccess$0$WifiKeyModel$1(iResponse, str);
                }
            });
        }

        @Override // com.xiaopeng.lib.framework.moduleinterface.netchannelmodule.http.Callback
        public void onFailure(IResponse iResponse) {
            WifiKeyModel.this.mNeedRequestWifiKey = true;
            EventBus.getDefault().post(new SSLKeyEvent(5));
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void dealResponse(String response, String sign) {
        try {
            JSONObject jsonObject = new JSONObject(response);
            int code = jsonObject.getInt("code");
            if (code == 200) {
                byte[] buff = DataHelp.decrypt(Base64.decode(jsonObject.getString("data"), 0), DataHelp.getKey(sign, DataHelp.SEC_SALT).getBytes());
                String result = new String(buff);
                LogUtils.v(TAG, "dealResponse result.length=" + result.length() + " ,result = " + result);
                saveWifiKey(new JSONObject(result));
            } else {
                this.mNeedRequestWifiKey = true;
                LogUtils.v(TAG, "dealResponse code=" + code);
                EventBus.getDefault().post(new SSLKeyEvent(6));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
