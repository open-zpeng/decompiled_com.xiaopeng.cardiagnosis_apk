package com.xiaopeng.commonfunc.bean.aftersales;

import com.alibaba.sdk.android.oss.common.RequestParameters;
import com.google.gson.annotations.SerializedName;
import com.xiaopeng.commonfunc.Constant;
/* loaded from: classes4.dex */
public class BleList {
    @SerializedName(Constant.HTT_KEY_MAC)
    private String mMainMac;
    @SerializedName("slaveMacList")
    private Ble[] mSlaveList;
    @SerializedName(Constant.HTT_KEY_TIME)
    private long mTime;
    @SerializedName(Constant.HTTP_KEY_VIN)
    private String mVin;

    public BleList(String mMainMac, Ble[] mSlaveList) {
        this.mMainMac = mMainMac;
        this.mSlaveList = mSlaveList;
    }

    public String getMainMac() {
        return this.mMainMac;
    }

    public void setMainMac(String mMainMac) {
        this.mMainMac = mMainMac;
    }

    public Ble[] getSlaveList() {
        return this.mSlaveList;
    }

    public void setSlaveList(Ble[] mSlaveList) {
        this.mSlaveList = mSlaveList;
    }

    public String getVin() {
        return this.mVin;
    }

    public void setVin(String mVin) {
        this.mVin = mVin;
    }

    public long getTime() {
        return this.mTime;
    }

    public void setTime(long mTime) {
        this.mTime = mTime;
    }

    /* loaded from: classes4.dex */
    public class Ble {
        @SerializedName(RequestParameters.POSITION)
        private int mPosition;
        @SerializedName(Constant.HTT_KEY_MAC)
        private String mSlaveMac;

        public Ble(String mSlaveMac, int mPosition) {
            this.mSlaveMac = mSlaveMac;
            this.mPosition = mPosition;
        }

        public String getSlaveMac() {
            return this.mSlaveMac;
        }

        public void setSlaveMac(String mSlaveMac) {
            this.mSlaveMac = mSlaveMac;
        }

        public int getPosition() {
            return this.mPosition;
        }

        public void setPosition(int mPosition) {
            this.mPosition = mPosition;
        }
    }
}
