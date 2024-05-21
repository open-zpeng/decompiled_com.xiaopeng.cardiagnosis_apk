package com.xiaopeng.commonfunc.model.security;

import android.content.Context;
import android.util.Base64;
import com.google.gson.Gson;
import com.xiaopeng.commonfunc.bean.event.SSLKeyEvent;
import com.xiaopeng.commonfunc.model.keystore.KeyStoreModel;
import com.xiaopeng.commonfunc.model.security.PsuKeyModel;
import com.xiaopeng.commonfunc.utils.FileUtil;
import com.xiaopeng.commonfunc.utils.SystemPropertyUtil;
import com.xiaopeng.lib.framework.module.Module;
import com.xiaopeng.lib.framework.moduleinterface.netchannelmodule.http.Callback;
import com.xiaopeng.lib.framework.moduleinterface.netchannelmodule.http.IHttp;
import com.xiaopeng.lib.framework.moduleinterface.netchannelmodule.http.IResponse;
import com.xiaopeng.lib.framework.netchannelmodule.NetworkChannelsEntry;
import com.xiaopeng.lib.utils.LogUtils;
import com.xiaopeng.lib.utils.MD5Utils;
import com.xiaopeng.lib.utils.ThreadUtils;
import com.xiaopeng.lib.utils.config.EnvConfig;
import com.xiaopeng.xmlconfig.Support;
import java.util.HashMap;
import java.util.Map;
import org.greenrobot.eventbus.EventBus;
import org.json.JSONObject;
/* loaded from: classes4.dex */
public class PsuKeyModel {
    private static final String CAR_TYPE = "car_type";
    private static final String CDU_ID = "cdu_id";
    private static final String PSU_ID = "psu_id";
    private static final String PSU_SECRET_ID = "p7kGkaPX2nfZx4Ne";
    private static final String PSU_SECRET_KEY = "PnBaj4jz2dh58nJfijfk4CYcTBCJwF4R";
    public static final String RESPONSE_BIN = "bin";
    public static final String RESPONSE_CODE = "code";
    public static final String RESPONSE_DATA = "data";
    public static final String RESPONSE_MESSAGE = "message";
    public static final String RESPONSE_TDESKEY = "tdeskey";
    private static final String SECRET_ID = "secret_id";
    private static final String SIGN = "sign";
    private static final String TAG = "PsuKeyModel";
    private static final String TIMESTAMP = "timestamp";
    private boolean mIsChecking;
    private final KeyStoreModel mKeyStoreModel;
    private final PsoModel mPsoModel;
    private static final String PSO_SERVER_CAR_TYPE = Support.Feature.getString(Support.Feature.PSO_SERVER_CAR_TYPE);
    public static String sPsuWebHost = EnvConfig.getString("psu_host", "http://10.0.11.90", "http://pso-int.xiaopeng.com");
    private boolean mNeedRequestPsuKey = true;
    private boolean mHasResetPsuKey = false;
    private boolean mIsSet2Keystore = false;

    public PsuKeyModel(Context context) {
        this.mKeyStoreModel = new KeyStoreModel(context);
        this.mPsoModel = new PsoModel(context);
    }

    public static void savePSUKey(JSONObject jsonObject) {
        try {
            String key = jsonObject.getString(RESPONSE_TDESKEY);
            LogUtils.v(TAG, "savePSUKey key.length=" + key.length() + ", key.md5:" + MD5Utils.getMD5(key) + ", key:" + key);
            FileUtil.writeNCreateFile(Support.Path.getFilePath(Support.Path.PSO_KEY), key);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void onDestroy() {
    }

    private synchronized boolean sendBin2PSU(JSONObject jsonObject) {
        boolean result;
        result = false;
        try {
            String bin = jsonObject.getString("bin");
            LogUtils.v(TAG, "sendBin2PSU bin.length=" + bin.length() + " mHasResetPsuKey = " + this.mHasResetPsuKey);
            Base64.decode(bin, 0);
            if (!this.mHasResetPsuKey) {
                this.mHasResetPsuKey = true;
                result = false;
                if (0 == 0) {
                    EventBus.getDefault().post(new SSLKeyEvent(4));
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        LogUtils.v(TAG, "sendBin2PSU result =" + result);
        return result;
    }

    public void asyncGetKey(final Runnable callback) {
        LogUtils.v(TAG, "asyncGetKey");
        if (this.mIsChecking) {
            return;
        }
        this.mIsChecking = true;
        this.mNeedRequestPsuKey = true;
        this.mIsSet2Keystore = false;
        ThreadUtils.execute(new Runnable() { // from class: com.xiaopeng.commonfunc.model.security.-$$Lambda$PsuKeyModel$dZOcg79kp_pU0tNqnADp9xraFuo
            @Override // java.lang.Runnable
            public final void run() {
                PsuKeyModel.this.lambda$asyncGetKey$0$PsuKeyModel(callback);
            }
        });
    }

    /* JADX WARN: Code restructure failed: missing block: B:12:0x0038, code lost:
        com.xiaopeng.lib.utils.LogUtils.i(com.xiaopeng.commonfunc.model.security.PsuKeyModel.TAG, "get key");
     */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct add '--show-bad-code' argument
    */
    public /* synthetic */ void lambda$asyncGetKey$0$PsuKeyModel(java.lang.Runnable r9) {
        /*
            r8 = this;
            java.lang.String r0 = "PsuKeyModel"
            r1 = 5
            r2 = 0
        L4:
            int r3 = r2 + 1
            r4 = 5
            r5 = 0
            if (r2 >= r4) goto L53
            java.lang.StringBuilder r2 = new java.lang.StringBuilder     // Catch: java.lang.Throwable -> L40 java.lang.Exception -> L42
            r2.<init>()     // Catch: java.lang.Throwable -> L40 java.lang.Exception -> L42
            java.lang.String r4 = "mNeedRequestPsuKey = "
            r2.append(r4)     // Catch: java.lang.Throwable -> L40 java.lang.Exception -> L42
            boolean r4 = r8.mNeedRequestPsuKey     // Catch: java.lang.Throwable -> L40 java.lang.Exception -> L42
            r2.append(r4)     // Catch: java.lang.Throwable -> L40 java.lang.Exception -> L42
            java.lang.String r2 = r2.toString()     // Catch: java.lang.Throwable -> L40 java.lang.Exception -> L42
            com.xiaopeng.lib.utils.LogUtils.i(r0, r2)     // Catch: java.lang.Throwable -> L40 java.lang.Exception -> L42
            boolean r2 = r8.mNeedRequestPsuKey     // Catch: java.lang.Throwable -> L40 java.lang.Exception -> L42
            if (r2 == 0) goto L29
            r8.mNeedRequestPsuKey = r5     // Catch: java.lang.Throwable -> L40 java.lang.Exception -> L42
            r8.reqPsuKey()     // Catch: java.lang.Throwable -> L40 java.lang.Exception -> L42
        L29:
            r6 = 10500(0x2904, double:5.1877E-320)
            java.lang.Thread.sleep(r6)     // Catch: java.lang.Throwable -> L40 java.lang.Exception -> L42
            boolean r2 = r8.isPsuKeyExist()     // Catch: java.lang.Throwable -> L40 java.lang.Exception -> L42
            if (r2 == 0) goto L3e
            boolean r2 = r8.mIsSet2Keystore     // Catch: java.lang.Throwable -> L40 java.lang.Exception -> L42
            if (r2 == 0) goto L3e
            java.lang.String r2 = "get key"
            com.xiaopeng.lib.utils.LogUtils.i(r0, r2)     // Catch: java.lang.Throwable -> L40 java.lang.Exception -> L42
            goto L53
        L3e:
            r2 = r3
            goto L4
        L40:
            r0 = move-exception
            goto L4b
        L42:
            r0 = move-exception
            r0.printStackTrace()     // Catch: java.lang.Throwable -> L40
            r8.mIsChecking = r5
            if (r9 == 0) goto L5a
            goto L57
        L4b:
            r8.mIsChecking = r5
            if (r9 == 0) goto L52
            com.xiaopeng.lib.utils.ThreadUtils.execute(r9)
        L52:
            throw r0
        L53:
            r8.mIsChecking = r5
            if (r9 == 0) goto L5a
        L57:
            com.xiaopeng.lib.utils.ThreadUtils.execute(r9)
        L5a:
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: com.xiaopeng.commonfunc.model.security.PsuKeyModel.lambda$asyncGetKey$0$PsuKeyModel(java.lang.Runnable):void");
    }

    public boolean isPsuKeyExist() {
        return FileUtil.isExistFilePath(Support.Path.getFilePath(Support.Path.PSO_KEY)) && this.mPsoModel.checkIfPreseted();
    }

    public boolean verifyPsuKey() {
        return this.mPsoModel.verifyCertKey(Support.Path.getFilePath(Support.Path.PSO_KEY));
    }

    private void reqPsuKey() {
        Map<String, String> param = new HashMap<>();
        String cduid = SystemPropertyUtil.getHardwareId();
        long time = System.currentTimeMillis() / 1000;
        String psuid = this.mPsoModel.getPsuId();
        if (psuid == null) {
            EventBus.getDefault().post(new SSLKeyEvent(7));
        }
        String sign = sign(cduid, psuid, time);
        param.put(CDU_ID, cduid);
        param.put(PSU_ID, psuid);
        param.put(CAR_TYPE, PSO_SERVER_CAR_TYPE);
        param.put("timestamp", String.valueOf(time));
        param.put(SECRET_ID, PSU_SECRET_ID);
        param.put("sign", sign);
        IHttp http = (IHttp) Module.get(NetworkChannelsEntry.class).get(IHttp.class);
        http.post(sPsuWebHost + Support.Url.getUrl(Support.Url.REQUEST_PSU_KEY)).uploadJson(new Gson().toJson(param)).execute(new AnonymousClass1());
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    /* renamed from: com.xiaopeng.commonfunc.model.security.PsuKeyModel$1  reason: invalid class name */
    /* loaded from: classes4.dex */
    public class AnonymousClass1 implements Callback {
        AnonymousClass1() {
        }

        public /* synthetic */ void lambda$onSuccess$0$PsuKeyModel$1(IResponse iResponse) {
            PsuKeyModel.this.dealResponse(iResponse.body());
        }

        @Override // com.xiaopeng.lib.framework.moduleinterface.netchannelmodule.http.Callback
        public void onSuccess(final IResponse iResponse) {
            ThreadUtils.execute(new Runnable() { // from class: com.xiaopeng.commonfunc.model.security.-$$Lambda$PsuKeyModel$1$MbRPQU5i1tXw1tsQYaghaIdAupo
                @Override // java.lang.Runnable
                public final void run() {
                    PsuKeyModel.AnonymousClass1.this.lambda$onSuccess$0$PsuKeyModel$1(iResponse);
                }
            });
        }

        @Override // com.xiaopeng.lib.framework.moduleinterface.netchannelmodule.http.Callback
        public void onFailure(IResponse iResponse) {
            PsuKeyModel.this.mNeedRequestPsuKey = true;
            EventBus.getDefault().post(new SSLKeyEvent(11));
        }
    }

    public void repairPsu(final String psuid, final Callback callback) {
        ThreadUtils.execute(new Runnable() { // from class: com.xiaopeng.commonfunc.model.security.-$$Lambda$PsuKeyModel$72jarv2tHTxc9GLPRiUbCJrbDlU
            @Override // java.lang.Runnable
            public final void run() {
                PsuKeyModel.this.lambda$repairPsu$1$PsuKeyModel(psuid, callback);
            }
        });
    }

    public /* synthetic */ void lambda$repairPsu$1$PsuKeyModel(String psuid, Callback callback) {
        Map<String, String> param = new HashMap<>();
        String cduid = SystemPropertyUtil.getHardwareId();
        long time = System.currentTimeMillis() / 1000;
        String sign = sign(cduid, psuid, time);
        param.put(CDU_ID, cduid);
        param.put(PSU_ID, psuid);
        param.put("timestamp", String.valueOf(time));
        param.put(SECRET_ID, PSU_SECRET_ID);
        param.put("sign", sign);
        IHttp http = (IHttp) Module.get(NetworkChannelsEntry.class).get(IHttp.class);
        http.post(sPsuWebHost + Support.Url.getUrl(Support.Url.REQUEST_REPAIR_PSU)).uploadJson(new Gson().toJson(param)).execute(callback);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void dealResponse(String response) {
        try {
            JSONObject jsonObject = new JSONObject(response);
            int code = jsonObject.getInt("code");
            if (code == 200) {
                String result = jsonObject.getString("data");
                byte[] keybin = Base64.decode(result, 0);
                if (FileUtil.writeNCreateFile(Support.Path.getFilePath(Support.Path.PSO_KEY), keybin) && this.mPsoModel.presetCertKey(Support.Path.getFilePath(Support.Path.PSO_KEY))) {
                    this.mIsSet2Keystore = true;
                }
                return;
            }
            String psuMessage = jsonObject.getString(RESPONSE_MESSAGE);
            LogUtils.v(TAG, "psuMessage =" + psuMessage);
            EventBus.getDefault().post(new SSLKeyEvent(11, psuMessage));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public String sign(String cduid, String psuid, long timestamp) {
        if (psuid != null) {
            return MD5Utils.getMD5("cdu_id=" + cduid + "&psu_id=" + psuid + PSU_SECRET_KEY + timestamp);
        }
        return MD5Utils.getMD5("cdu_id=" + cduid + PSU_SECRET_KEY + timestamp);
    }
}
