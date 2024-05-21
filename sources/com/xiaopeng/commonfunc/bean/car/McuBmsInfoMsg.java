package com.xiaopeng.commonfunc.bean.car;
/* loaded from: classes4.dex */
public class McuBmsInfoMsg {
    public int mChargeMode;
    public int mCurrent;
    public int mHwVersion;
    public int mJobMode;
    public int mSoc;
    public int mSoh;
    public int mState;
    public int mSwVersion;
    public int mTempature;
    public int mVolatage;

    public McuBmsInfoMsg(int[] data) {
        if (data != null) {
            this.mVolatage = data[0] & 65535;
            this.mCurrent = (data[0] & (-65536)) >> 16;
            this.mSoc = data[1] & 255;
            this.mSoh = (data[1] >> 8) & 255;
            this.mTempature = (byte) ((data[1] >> 16) & 255);
            this.mChargeMode = (data[1] >> 24) & 7;
            this.mHwVersion = (data[1] >> 27) & 31;
            this.mState = (data[2] >> 0) & 255;
            this.mJobMode = (data[2] >> 8) & 3;
            this.mSwVersion = (data[2] >> 10) & 63;
            return;
        }
        this.mVolatage = -1;
        this.mCurrent = -1;
        this.mSoc = -1;
        this.mSoh = -1;
        this.mTempature = -1;
        this.mChargeMode = -1;
        this.mHwVersion = -1;
        this.mState = -1;
        this.mJobMode = -1;
        this.mSwVersion = -1;
    }

    public String toString() {
        String str = "mVolatage=" + this.mVolatage + "\n mCurrent=" + this.mCurrent + "\n mSoc=" + this.mSoc + "\n mSoh=" + this.mSoh + "\n mTempature=" + this.mTempature + "\n mChargeMode=" + this.mChargeMode + "\n mHwVersion=" + this.mHwVersion + "\n mState=" + this.mState + "\n mJobMode=" + this.mJobMode + "\n mSwVersion=" + this.mSwVersion;
        return str;
    }
}
