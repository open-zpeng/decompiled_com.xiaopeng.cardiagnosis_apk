package com.xiaopeng.commonfunc.bean.car;
/* loaded from: classes4.dex */
public class McuPmStatusMsg {
    public int mPmAwake;
    public int mSysPmStatus;

    public McuPmStatusMsg(int[] data) {
        this.mPmAwake = data[0];
        this.mSysPmStatus = data[1];
    }

    public String toString() {
        String str = "mSysPmStatus=" + this.mSysPmStatus + "\n mPmAwake=" + this.mPmAwake;
        return str;
    }
}
