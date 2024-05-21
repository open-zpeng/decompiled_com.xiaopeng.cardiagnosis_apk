package com.xiaopeng.commonfunc.bean.car;
/* loaded from: classes4.dex */
public class McuPsuOtaMsg {
    public int mAndroidSleepTime;
    public int mRevert0;
    public int mRevert1;
    public int mRevert2;

    public McuPsuOtaMsg(int[] data) {
        this.mAndroidSleepTime = data[0];
        this.mRevert0 = data[1];
        this.mRevert1 = data[2];
        this.mRevert2 = data[3];
    }

    public String toString() {
        String str = "mAndroidSleepTime=" + this.mAndroidSleepTime;
        return str;
    }
}
