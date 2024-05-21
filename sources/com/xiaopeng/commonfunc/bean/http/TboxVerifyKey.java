package com.xiaopeng.commonfunc.bean.http;

import com.google.gson.annotations.SerializedName;
/* loaded from: classes4.dex */
public class TboxVerifyKey {
    @SerializedName("certkeys")
    private CertKey[] mCertKeys;
    @SerializedName("cas")
    private TboxCaCert mTboxCaCert;

    public TboxVerifyKey(CertKey[] mCertKeys, TboxCaCert mTboxCaCert) {
        this.mCertKeys = mCertKeys;
        this.mTboxCaCert = mTboxCaCert;
    }

    public CertKey[] getCertKeys() {
        return this.mCertKeys;
    }

    public void setCertKeys(CertKey[] mCertKeys) {
        this.mCertKeys = mCertKeys;
    }

    public TboxCaCert getTboxCaCert() {
        return this.mTboxCaCert;
    }

    public void setTboxCaCert(TboxCaCert mTboxCaCert) {
        this.mTboxCaCert = mTboxCaCert;
    }
}
