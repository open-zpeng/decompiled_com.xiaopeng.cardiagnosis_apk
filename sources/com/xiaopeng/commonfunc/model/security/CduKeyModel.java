package com.xiaopeng.commonfunc.model.security;

import android.content.Context;
import android.util.Base64;
import com.google.gson.Gson;
import com.xiaopeng.commonfunc.Constant;
import com.xiaopeng.commonfunc.bean.event.SSLKeyEvent;
import com.xiaopeng.commonfunc.model.keystore.KeyStoreModel;
import com.xiaopeng.commonfunc.model.security.CduKeyModel;
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
import com.xiaopeng.lib.utils.SendBroadCastUtil;
import com.xiaopeng.lib.utils.ThreadUtils;
import com.xiaopeng.lib.utils.config.CommonConfig;
import com.xiaopeng.lib.utils.info.BuildInfoUtils;
import com.xiaopeng.xmlconfig.Support;
import java.util.HashMap;
import java.util.Map;
import org.greenrobot.eventbus.EventBus;
import org.json.JSONObject;
/* loaded from: classes4.dex */
public class CduKeyModel {
    private static final String RESPONSE_OK = "ok";
    private static final String TAG = "CduKeyModel";
    private static final String URL_CDU_CERT_CHECK = Support.Url.getUrl(Support.Url.CHECK_CDU_KEY);
    private static final String URL_V18_CDU_CERT_CHECK = Support.Url.getUrl(Support.Url.CHECK_V18_CDU_KEY);
    private Context mContext;
    private boolean mIsChecking;
    private final KeyStoreModel mKeyStoreModel;
    private boolean mNeedRequestCduKey = true;
    private String mInputHardwareId = null;

    public CduKeyModel(Context context) {
        this.mContext = context;
        this.mKeyStoreModel = new KeyStoreModel(context);
    }

    public void onDestroy() {
    }

    public void saveCDUKey(JSONObject jsonObject) {
        try {
            String pkcs12 = jsonObject.getString(Constant.PKCS12);
            LogUtils.v(TAG, "saveCDUKey pkcs12.length=" + pkcs12.length());
            this.mKeyStoreModel.CreateClientCert(pkcs12);
            SendBroadCastUtil.getInstance(this.mContext).sendBroadCast(CommonConfig.ACTION_BROADCAST_CLIENT_SSL_UPDATE);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public boolean changeV18CaCert() {
        boolean res = this.mKeyStoreModel.CreateV18CaCert();
        SendBroadCastUtil.getInstance(this.mContext).sendBroadCast(CommonConfig.ACTION_BROADCAST_CLIENT_SSL_UPDATE);
        return res;
    }

    public boolean changeChnCaCert() {
        boolean res = this.mKeyStoreModel.CreateCaCert();
        SendBroadCastUtil.getInstance(this.mContext).sendBroadCast(CommonConfig.ACTION_BROADCAST_CLIENT_SSL_UPDATE);
        return res;
    }

    public void asyncGetKey(final Runnable callback, String hardwareId) {
        LogUtils.v(TAG, "asyncGetKey");
        if (this.mIsChecking) {
            return;
        }
        this.mIsChecking = true;
        this.mNeedRequestCduKey = true;
        this.mInputHardwareId = hardwareId;
        ThreadUtils.execute(new Runnable() { // from class: com.xiaopeng.commonfunc.model.security.-$$Lambda$CduKeyModel$1kOM3WZzMOPeTfv2N8UHKuZA8ME
            @Override // java.lang.Runnable
            public final void run() {
                CduKeyModel.this.lambda$asyncGetKey$0$CduKeyModel(callback);
            }
        });
    }

    /* JADX WARN: Code restructure failed: missing block: B:10:0x0036, code lost:
        com.xiaopeng.lib.utils.LogUtils.i(com.xiaopeng.commonfunc.model.security.CduKeyModel.TAG, "get key");
     */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct add '--show-bad-code' argument
    */
    public /* synthetic */ void lambda$asyncGetKey$0$CduKeyModel(java.lang.Runnable r9) {
        /*
            r8 = this;
            java.lang.String r0 = "CduKeyModel"
            r1 = 5
            r2 = 0
        L4:
            int r3 = r2 + 1
            r4 = 5
            r5 = 0
            if (r2 >= r4) goto L51
            java.lang.StringBuilder r2 = new java.lang.StringBuilder     // Catch: java.lang.Throwable -> L3e java.lang.Exception -> L40
            r2.<init>()     // Catch: java.lang.Throwable -> L3e java.lang.Exception -> L40
            java.lang.String r4 = "mNeedRequestCduKey = "
            r2.append(r4)     // Catch: java.lang.Throwable -> L3e java.lang.Exception -> L40
            boolean r4 = r8.mNeedRequestCduKey     // Catch: java.lang.Throwable -> L3e java.lang.Exception -> L40
            r2.append(r4)     // Catch: java.lang.Throwable -> L3e java.lang.Exception -> L40
            java.lang.String r2 = r2.toString()     // Catch: java.lang.Throwable -> L3e java.lang.Exception -> L40
            com.xiaopeng.lib.utils.LogUtils.i(r0, r2)     // Catch: java.lang.Throwable -> L3e java.lang.Exception -> L40
            boolean r2 = r8.mNeedRequestCduKey     // Catch: java.lang.Throwable -> L3e java.lang.Exception -> L40
            if (r2 == 0) goto L2b
            r8.mNeedRequestCduKey = r5     // Catch: java.lang.Throwable -> L3e java.lang.Exception -> L40
            android.content.Context r2 = r8.mContext     // Catch: java.lang.Throwable -> L3e java.lang.Exception -> L40
            r8.reqCduKey(r2)     // Catch: java.lang.Throwable -> L3e java.lang.Exception -> L40
        L2b:
            r6 = 10500(0x2904, double:5.1877E-320)
            java.lang.Thread.sleep(r6)     // Catch: java.lang.Throwable -> L3e java.lang.Exception -> L40
            boolean r2 = r8.isCduKeyGotten()     // Catch: java.lang.Throwable -> L3e java.lang.Exception -> L40
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
        throw new UnsupportedOperationException("Method not decompiled: com.xiaopeng.commonfunc.model.security.CduKeyModel.lambda$asyncGetKey$0$CduKeyModel(java.lang.Runnable):void");
    }

    private boolean isCduKeyGotten() {
        return this.mInputHardwareId != null ? isCduKeyFileExist() : isCduKeyExist();
    }

    public boolean verifyCduKey() {
        Map<String, String> param = new HashMap<>();
        try {
            IHttp http = (IHttp) Module.get(NetworkChannelsEntry.class).get(IHttp.class);
            IResponse response = http.bizHelper().post(URL_CDU_CERT_CHECK, new Gson().toJson(param)).build().execute();
            boolean result = response.body().contains(RESPONSE_OK);
            return result;
        } catch (Exception ex) {
            ex.printStackTrace();
            return false;
        }
    }

    public boolean verifyV18CduKey() {
        Map<String, String> param = new HashMap<>();
        try {
            IHttp http = (IHttp) Module.get(NetworkChannelsEntry.class).get(IHttp.class);
            IResponse response = http.bizHelper().post(URL_V18_CDU_CERT_CHECK, new Gson().toJson(param)).build().execute();
            boolean result = response.body().contains(RESPONSE_OK);
            return result;
        } catch (Exception ex) {
            ex.printStackTrace();
            return false;
        }
    }

    public void verifyCduKey(Callback callback) {
        Map<String, String> param = new HashMap<>();
        IHttp http = (IHttp) Module.get(NetworkChannelsEntry.class).get(IHttp.class);
        http.cancelTag(URL_CDU_CERT_CHECK);
        http.bizHelper().post(URL_CDU_CERT_CHECK, new Gson().toJson(param)).build().tag(URL_CDU_CERT_CHECK).execute(callback);
    }

    public void verifyV18CduKey(Callback callback) {
        LogUtils.i(TAG, "verifyV18CduKey");
        Map<String, String> param = new HashMap<>();
        IHttp http = (IHttp) Module.get(NetworkChannelsEntry.class).get(IHttp.class);
        http.cancelTag(URL_V18_CDU_CERT_CHECK);
        http.bizHelper().post(URL_V18_CDU_CERT_CHECK, new Gson().toJson(param)).build().tag(URL_V18_CDU_CERT_CHECK).execute(callback);
    }

    public boolean isCduKeyExist() {
        boolean res = this.mKeyStoreModel.isClientCertExist();
        LogUtils.i(TAG, "isCduKeyExist? res:" + res);
        return res;
    }

    public boolean isCduKeyFileExist() {
        boolean res = false;
        if (this.mInputHardwareId != null) {
            res = FileUtil.isExistFilePath(Constant.PATH_SDCARD + this.mInputHardwareId);
        }
        LogUtils.i(TAG, "isCduKeyFileExist? mInputHardwareId:" + this.mInputHardwareId + ", res:" + res);
        return res;
    }

    public String getInputHardwareId() {
        return this.mInputHardwareId;
    }

    private void reqCduKey(Context context) {
        Map<String, String> param = new HashMap<>();
        param.put("app_id", "xmart:appid:002");
        String str = this.mInputHardwareId;
        if (str == null) {
            str = SystemPropertyUtil.getHardwareId();
        }
        param.put(Constant.HARDWAREID, str);
        long time = System.currentTimeMillis();
        param.put("timestamp", String.valueOf(time));
        String sign = IndivHelper.sign(context, param, time);
        param.put("sign", sign);
        IHttp http = (IHttp) Module.get(NetworkChannelsEntry.class).get(IHttp.class);
        IRequest uploadJson = http.post(Support.Url.getUrl(Support.Url.REQUEST_CDU_KEY)).uploadJson(new Gson().toJson(param));
        uploadJson.headers("Client", Constant.HTTP_CAR_XMART + BuildInfoUtils.getSystemVersion()).execute(new AnonymousClass1(sign));
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    /* renamed from: com.xiaopeng.commonfunc.model.security.CduKeyModel$1  reason: invalid class name */
    /* loaded from: classes4.dex */
    public class AnonymousClass1 implements Callback {
        final /* synthetic */ String val$sign;

        AnonymousClass1(String str) {
            this.val$sign = str;
        }

        public /* synthetic */ void lambda$onSuccess$0$CduKeyModel$1(IResponse iResponse, String sign) {
            CduKeyModel.this.dealResponse(iResponse.body(), sign);
        }

        @Override // com.xiaopeng.lib.framework.moduleinterface.netchannelmodule.http.Callback
        public void onSuccess(final IResponse iResponse) {
            final String str = this.val$sign;
            ThreadUtils.execute(new Runnable() { // from class: com.xiaopeng.commonfunc.model.security.-$$Lambda$CduKeyModel$1$0DG8eByik3Z19YJe9Jxl2IJHcuM
                @Override // java.lang.Runnable
                public final void run() {
                    CduKeyModel.AnonymousClass1.this.lambda$onSuccess$0$CduKeyModel$1(iResponse, str);
                }
            });
        }

        @Override // com.xiaopeng.lib.framework.moduleinterface.netchannelmodule.http.Callback
        public void onFailure(IResponse iResponse) {
            CduKeyModel.this.mNeedRequestCduKey = true;
            EventBus.getDefault().post(new SSLKeyEvent(2));
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
                LogUtils.v(TAG, "dealResponse mInputHardwareId:" + this.mInputHardwareId + ", result.length:" + result.length() + ", result:" + result);
                if (this.mInputHardwareId != null) {
                    FileUtil.write(Constant.PATH_SDCARD + this.mInputHardwareId, result);
                } else {
                    saveCDUKey(new JSONObject(result));
                }
                return;
            }
            this.mNeedRequestCduKey = true;
            LogUtils.v(TAG, "dealResponse code=" + code);
            EventBus.getDefault().post(new SSLKeyEvent(3));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
