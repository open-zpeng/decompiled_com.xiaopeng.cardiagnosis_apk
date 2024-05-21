package com.xiaopeng.commonfunc.model.keystore;

import android.content.Context;
import android.util.Base64;
import com.xiaopeng.lib.utils.LogUtils;
import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.security.KeyStore;
import java.security.cert.Certificate;
/* loaded from: classes4.dex */
public class KeyStoreModel {
    private static final String FILE_CA_CERT = "index_kstp.html";
    private static final String FILE_CA_CERT_V18 = "index_kstp_eu.html";
    private static final String KEYSTORE_BKS = "BKS";
    private static final String KEYSTORE_PKCS12 = "PKCS12";
    private static final String KEY_STORE_CACERT_ALIAS = "ca";
    private static final String KEY_STORE_CACERT_ALIAS_KEY = "server";
    private static final String KEY_STORE_CACERT_PASSWORD = "chengzi";
    private static final String KEY_STORE_CLIENT_ALIAS = "client";
    private static final String KEY_STORE_CLIENT_ALIAS_KEY = "alias";
    private static final String KEY_STORE_CLIENT_PASSWORD = "chengzi";
    private static final String TAG = "KeyStoreModel";
    private static final String XPENG_ANDROID_KEY_STORE = "XpengAndroidKeyStore";
    private Context mContext;
    private KeyStore mKeyStore;

    public KeyStoreModel(Context context) {
        this.mContext = context;
        try {
            this.mKeyStore = KeyStore.getInstance("XpengAndroidKeyStore");
            this.mKeyStore.load(null);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public boolean CreateCaCert() {
        LogUtils.i(TAG, "CreateCaCert");
        try {
            KeyStore tks = KeyStore.getInstance(KEYSTORE_BKS);
            InputStream tsIn = this.mContext.getResources().getAssets().open(FILE_CA_CERT);
            tks.load(tsIn, "chengzi".toCharArray());
            Certificate cert = tks.getCertificate(KEY_STORE_CACERT_ALIAS_KEY);
            this.mKeyStore.setCertificateEntry(KEY_STORE_CACERT_ALIAS, cert);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean CreateV18CaCert() {
        LogUtils.i(TAG, "CreateV18CaCert");
        try {
            KeyStore tks = KeyStore.getInstance(KEYSTORE_BKS);
            InputStream tsIn = this.mContext.getResources().getAssets().open(FILE_CA_CERT_V18);
            tks.load(tsIn, "chengzi".toCharArray());
            Certificate cert = tks.getCertificate(KEY_STORE_CACERT_ALIAS_KEY);
            this.mKeyStore.setCertificateEntry(KEY_STORE_CACERT_ALIAS, cert);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    public void CreateClientCert(String pkcs12) {
        try {
            KeyStore ksClient = KeyStore.getInstance(KEYSTORE_PKCS12);
            InputStream ksIn = new ByteArrayInputStream(Base64.decode(pkcs12, 0));
            ksClient.load(ksIn, "chengzi".toCharArray());
            KeyStore.Entry entry = ksClient.getEntry("alias", null);
            this.mKeyStore.setEntry(KEY_STORE_CLIENT_ALIAS, entry, null);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public boolean isCaCertExist() {
        boolean result = false;
        try {
            result = this.mKeyStore.isCertificateEntry(KEY_STORE_CACERT_ALIAS);
        } catch (Exception e) {
            e.printStackTrace();
        }
        LogUtils.i(TAG, "isCaCertExist " + result);
        return result;
    }

    public boolean isClientCertExist() {
        boolean result = false;
        try {
            result = this.mKeyStore.isKeyEntry(KEY_STORE_CLIENT_ALIAS);
        } catch (Exception e) {
            e.printStackTrace();
        }
        LogUtils.i(TAG, "isClientCertExist " + result);
        return result;
    }
}
