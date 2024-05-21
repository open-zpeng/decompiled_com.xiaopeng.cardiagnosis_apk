package com.xiaopeng.commonfunc.bean.http;

import com.google.gson.annotations.SerializedName;
/* loaded from: classes4.dex */
public class TboxCaCert {
    @SerializedName("ca1")
    private String mCa1;
    @SerializedName("ca2")
    private String mCa2;
    @SerializedName("euca")
    private String mEuCa;

    public TboxCaCert(String mEuCa, String mCa1, String mCa2) {
        this.mEuCa = mEuCa;
        this.mCa1 = mCa1;
        this.mCa2 = mCa2;
    }

    public String getEuCa() {
        return this.mEuCa;
    }

    public void setEuCa(String mEuCa) {
        this.mEuCa = mEuCa;
    }

    public String getCa1() {
        return this.mCa1;
    }

    public void setCa1(String mCa1) {
        this.mCa1 = mCa1;
    }

    public String getCa2() {
        return this.mCa2;
    }

    public void setCa2(String mCa2) {
        this.mCa2 = mCa2;
    }
}
