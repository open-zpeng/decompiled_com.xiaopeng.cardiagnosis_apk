package com.xiaopeng.commonfunc.bean.http;

import com.google.gson.annotations.SerializedName;
/* loaded from: classes4.dex */
public class TboxCertKey {
    @SerializedName("bsk")
    private CertKey mBLeSecKey;
    @SerializedName("certkeys")
    private CertKey[] mCertKeys;

    public TboxCertKey(CertKey[] mCertKeys, CertKey mBLeSecKey) {
        this.mCertKeys = mCertKeys;
        this.mBLeSecKey = mBLeSecKey;
    }

    public CertKey[] getCertKeys() {
        return this.mCertKeys;
    }

    public void setCertKeys(CertKey[] mCertKeys) {
        this.mCertKeys = mCertKeys;
    }

    public CertKey getBLeSecKey() {
        return this.mBLeSecKey;
    }

    public void setBLeSecKey(CertKey mBLeSecKey) {
        this.mBLeSecKey = mBLeSecKey;
    }
}
