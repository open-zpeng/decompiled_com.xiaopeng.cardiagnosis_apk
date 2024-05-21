package com.xiaopeng.commonfunc.bean.http;

import com.google.gson.annotations.SerializedName;
/* loaded from: classes4.dex */
public class CertKey {
    @SerializedName("cert")
    private String mCert;
    @SerializedName("index")
    private int mIndex;
    @SerializedName("key")
    private String mKey;

    public CertKey(String mCert, String mKey, int mIndex) {
        this.mCert = mCert;
        this.mKey = mKey;
        this.mIndex = mIndex;
    }

    public String getCert() {
        return this.mCert;
    }

    public void setCert(String mCert) {
        this.mCert = mCert;
    }

    public String getKey() {
        return this.mKey;
    }

    public void setKey(String mKey) {
        this.mKey = mKey;
    }

    public int getIndex() {
        return this.mIndex;
    }

    public void setIndex(int mIndex) {
        this.mIndex = mIndex;
    }
}
