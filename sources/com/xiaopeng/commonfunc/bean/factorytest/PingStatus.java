package com.xiaopeng.commonfunc.bean.factorytest;
/* loaded from: classes4.dex */
public class PingStatus {
    private int mFailTimes;
    private boolean mIsPingEthRunning;
    private int mSuccessTimes;

    public PingStatus(boolean mIsPingEthRunning, int mSuccessTimes, int mFailTimes) {
        this.mIsPingEthRunning = mIsPingEthRunning;
        this.mSuccessTimes = mSuccessTimes;
        this.mFailTimes = mFailTimes;
    }

    public boolean isPingEthRunning() {
        return this.mIsPingEthRunning;
    }

    public void setPingEthRunning(boolean mIsPingEthRunning) {
        this.mIsPingEthRunning = mIsPingEthRunning;
    }

    public int getSuccessTimes() {
        return this.mSuccessTimes;
    }

    public void increaseSuccessTimes() {
        this.mSuccessTimes++;
    }

    public int getFailTimes() {
        return this.mFailTimes;
    }

    public void increaseFailTimes() {
        this.mFailTimes++;
    }
}
