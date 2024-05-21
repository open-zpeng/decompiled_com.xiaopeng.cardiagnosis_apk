package com.xiaopeng.commonfunc.bean.car;

import com.google.gson.annotations.SerializedName;
import com.xiaopeng.commonfunc.Constant;
import com.xiaopeng.commonfunc.model.security.PsuKeyModel;
/* loaded from: classes4.dex */
public class DidResponse {
    @SerializedName(Constant.KEY_ADDRESS)
    private int mAddress;
    @SerializedName("code")
    private int mCode;
    @SerializedName("did")
    private int mDid;
    @SerializedName(PsuKeyModel.RESPONSE_MESSAGE)
    private String mMessage;
    @SerializedName("value")
    private String mValue;

    public DidResponse(int mAddress, int mDid, String mValue, int mCode, String mMessage) {
        this.mAddress = mAddress;
        this.mDid = mDid;
        this.mValue = mValue;
        this.mCode = mCode;
        this.mMessage = mMessage;
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

    public String getValue() {
        return this.mValue;
    }

    public void setValue(String mValue) {
        this.mValue = mValue;
    }

    public int getCode() {
        return this.mCode;
    }

    public void setCode(int mCode) {
        this.mCode = mCode;
    }

    public String getMessage() {
        return this.mMessage;
    }

    public void setMessage(String mMessage) {
        this.mMessage = mMessage;
    }

    public String toString() {
        return "DidResponse{mAddress=" + this.mAddress + ", mDid=" + this.mDid + ", mValue=" + this.mValue + ", mCode=" + this.mCode + ", mMessage=" + this.mMessage + '}';
    }
}
