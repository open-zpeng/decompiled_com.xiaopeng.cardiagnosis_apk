package com.xiaopeng.commonfunc.bean;

import com.google.gson.annotations.SerializedName;
/* loaded from: classes4.dex */
public class ParamRepairMode {
    @SerializedName("entry")
    private String mEntry;
    @SerializedName("exit")
    private String mExit;
    @SerializedName("status")
    private boolean mStatus;

    public ParamRepairMode(boolean mStatus, String mEntry, String mExit) {
        this.mStatus = mStatus;
        this.mEntry = mEntry;
        this.mExit = mExit;
    }

    public boolean getStatus() {
        return this.mStatus;
    }

    public void setStatus(boolean mStatus) {
        this.mStatus = mStatus;
    }

    public String getEntry() {
        return this.mEntry;
    }

    public void setEntry(String mEntry) {
        this.mEntry = mEntry;
    }

    public String getExit() {
        return this.mExit;
    }

    public void setExit(String mExit) {
        this.mExit = mExit;
    }

    public String toString() {
        return "ParamRepairMode{mStatus=" + this.mStatus + ", mEntry='" + this.mEntry + "', mExit='" + this.mExit + "'}";
    }
}
