package com.xiaopeng.commonfunc.bean.car;

import com.google.gson.annotations.SerializedName;
/* loaded from: classes4.dex */
public class TboxBean {
    @SerializedName("Request")
    private int mRequest;
    @SerializedName("Status")
    private int mStatus;
    @SerializedName("StringValue1")
    private String mStringValue1;
    @SerializedName("StringValue2")
    private String mStringValue2;

    public TboxBean(int mRequest, int mStatus, String mStringValue1, String mStringValue2) {
        this.mRequest = mRequest;
        this.mStatus = mStatus;
        this.mStringValue1 = mStringValue1;
        this.mStringValue2 = mStringValue2;
    }

    public int getRequest() {
        return this.mRequest;
    }

    public void setRequest(int mRequest) {
        this.mRequest = mRequest;
    }

    public int getStatus() {
        return this.mStatus;
    }

    public void setStatus(int mStatus) {
        this.mStatus = mStatus;
    }

    public String getStringValue1() {
        return this.mStringValue1;
    }

    public void setStringValue1(String mStringValue1) {
        this.mStringValue1 = mStringValue1;
    }

    public String getStringValue2() {
        return this.mStringValue2;
    }

    public void setStringValue2(String mStringValue2) {
        this.mStringValue2 = mStringValue2;
    }

    public String toString() {
        return "TboxBean{mRequest=" + this.mRequest + ", mStatus=" + this.mStatus + ", mStringValue1='" + this.mStringValue1 + "', mStringValue2='" + this.mStringValue2 + "'}";
    }
}
