package com.xiaopeng.commonfunc.model.security;

import android.content.Context;
import com.xiaopeng.commonfunc.callback.SecurityCallback;
import com.xiaopeng.commonfunc.utils.SystemPropertyUtil;
import com.xiaopeng.lib.http.ICallback;
import com.xiaopeng.lib.utils.LogUtils;
import com.xiaopeng.lib.utils.ThreadUtils;
import com.xiaopeng.xmlconfig.Support;
/* loaded from: classes4.dex */
public class SecurityModel {
    private static final String RESPONSE_OK = "ok";
    private static final String TAG = "TestSecurityPresenter";
    private CduKeyModel mCduKeyModel;
    private Context mContext;
    private PsuKeyModel mPsuKeyModel;
    private SecurityCallback mSecurityCallback;
    private WifiKeyModel mWifiKeyModel;

    public SecurityModel(Context context, SecurityCallback callback) {
        this.mContext = context;
        this.mSecurityCallback = callback;
        this.mCduKeyModel = new CduKeyModel(context);
        this.mWifiKeyModel = new WifiKeyModel(context);
        this.mPsuKeyModel = new PsuKeyModel(context);
    }

    public void onDestroy() {
        this.mCduKeyModel.onDestroy();
        this.mWifiKeyModel.onDestroy();
        this.mPsuKeyModel.onDestroy();
    }

    public boolean checkPsuKeyExist() {
        boolean res = this.mPsuKeyModel.isPsuKeyExist();
        LogUtils.i(TAG, "checkPsuKeyExist res:" + res);
        return res;
    }

    public void verifyPsuKey() {
        LogUtils.i(TAG, "verifyPsuKey");
        ThreadUtils.execute(new Runnable() { // from class: com.xiaopeng.commonfunc.model.security.-$$Lambda$SecurityModel$F5x6XBxJfOO90W_bnqklvnrQA0M
            @Override // java.lang.Runnable
            public final void run() {
                SecurityModel.this.lambda$verifyPsuKey$0$SecurityModel();
            }
        });
    }

    public /* synthetic */ void lambda$verifyPsuKey$0$SecurityModel() {
        boolean result = this.mPsuKeyModel.verifyPsuKey();
        responseSecurityResult(2, result ? 1 : 2);
    }

    public boolean checkWifiKeyExist() {
        boolean res = this.mWifiKeyModel.isWifiKeyExist();
        LogUtils.i(TAG, "checkWifiKeyExist res:" + res);
        return res;
    }

    public void verifyWifiKey() {
        if (!checkWifiKeyExist()) {
            responseSecurityResult(3, 2);
            return;
        }
        String password = CertVerifyModel.WIFI_CLIENT_CERT_KEY_PASSWORD + SystemPropertyUtil.getHardwareId().substring(0, 8);
        LogUtils.i(TAG, "verifyWifiKey password:" + password);
        new CertVerifyModel(this.mContext, WifiKeyModel.WIFI_CLIENT_CERT_PATH, WifiKeyModel.WIFI_ROOT_BKS_CERT_PATH, Support.Url.getUrl(Support.Url.CHECK_WIFI_KEY), password).verifyCert(new ICallback<String, String>() { // from class: com.xiaopeng.commonfunc.model.security.SecurityModel.1
            @Override // com.xiaopeng.lib.http.ICallback
            public void onSuccess(String s) {
                int code = 0;
                try {
                    code = Integer.parseInt(s);
                } catch (NumberFormatException e) {
                    e.printStackTrace();
                }
                SecurityModel.this.responseSecurityResult(3, code == 200 ? 1 : 2);
            }

            @Override // com.xiaopeng.lib.http.ICallback
            public void onError(String s) {
                SecurityModel.this.responseSecurityResult(3, 2);
            }
        });
    }

    public void verifyCduKey() {
        ThreadUtils.execute(new Runnable() { // from class: com.xiaopeng.commonfunc.model.security.-$$Lambda$SecurityModel$1aqpYZ-5GTDIxY_rMM9dIb7vVok
            @Override // java.lang.Runnable
            public final void run() {
                SecurityModel.this.lambda$verifyCduKey$1$SecurityModel();
            }
        });
    }

    public /* synthetic */ void lambda$verifyCduKey$1$SecurityModel() {
        boolean result = this.mCduKeyModel.verifyCduKey() && this.mCduKeyModel.verifyV18CduKey();
        responseSecurityResult(1, result ? 1 : 2);
    }

    public void verifyV18CduKey() {
        ThreadUtils.execute(new Runnable() { // from class: com.xiaopeng.commonfunc.model.security.-$$Lambda$SecurityModel$zqU7w9SKqS_nipvKLBDbmRASRdw
            @Override // java.lang.Runnable
            public final void run() {
                SecurityModel.this.lambda$verifyV18CduKey$2$SecurityModel();
            }
        });
    }

    public /* synthetic */ void lambda$verifyV18CduKey$2$SecurityModel() {
        int i;
        boolean result = this.mCduKeyModel.verifyV18CduKey();
        if (result) {
            i = 1;
        } else {
            i = 2;
        }
        responseSecurityResult(1, i);
    }

    public boolean changeV18CaCert() {
        return this.mCduKeyModel.changeV18CaCert();
    }

    public boolean changeChnCaCert() {
        return this.mCduKeyModel.changeChnCaCert();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void responseSecurityResult(int keytype, int result) {
        SecurityCallback securityCallback = this.mSecurityCallback;
        if (securityCallback != null) {
            securityCallback.onReceiveResult(keytype, result);
        }
    }

    public boolean checkCduKey() {
        return this.mCduKeyModel.isCduKeyExist();
    }
}
