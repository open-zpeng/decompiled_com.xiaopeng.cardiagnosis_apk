package com.xiaopeng.commonfunc.bean.car;

import com.google.gson.annotations.SerializedName;
import com.xiaopeng.commonfunc.Constant;
/* loaded from: classes4.dex */
public class DidRequest {
    @SerializedName(Constant.KEY_ADDRESS)
    private int mAddress;
    @SerializedName("did")
    private int mDid;

    public DidRequest(int address, int did) {
        this.mAddress = address;
        this.mDid = did;
    }

    public int getAddress() {
        return this.mAddress;
    }

    public void setAddress(int mAddress) {
        this.mAddress = mAddress;
    }

    public int getDid() {
        return this.mDid;
    }

    public void setDid(int mDid) {
        this.mDid = mDid;
    }

    public String toString() {
        return "DidRequest{mAddress=" + this.mAddress + ", mDid=" + this.mDid + '}';
    }
}
