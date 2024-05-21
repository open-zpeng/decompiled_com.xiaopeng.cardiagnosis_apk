package com.xiaopeng.commonfunc.bean;

import com.google.gson.annotations.SerializedName;
import com.xiaopeng.commonfunc.Constant;
/* loaded from: classes4.dex */
public class CheckModeStatus {
    @SerializedName("cduid")
    private String mCduId;
    @SerializedName("key")
    private String mKey;
    @SerializedName(Constant.HTTP_KEY_MAINTAIN_MODE)
    private ParamRepairMode mMaintainMode;
    @SerializedName(Constant.HTTP_KEY_SPEED_LIMIT_MODE)
    private ParamRepairMode mSpeedLimitMode;
    @SerializedName(Constant.HTTP_KEY_VIN)
    private String mVin;

    public CheckModeStatus(String mVin, String mCduId, String mKey, ParamRepairMode mMaintainMode, ParamRepairMode mSpeedLimitMode) {
        this.mVin = mVin;
        this.mCduId = mCduId;
        this.mKey = mKey;
        this.mMaintainMode = mMaintainMode;
        this.mSpeedLimitMode = mSpeedLimitMode;
    }

    public String getVin() {
        return this.mVin;
    }

    public void setVin(String mVin) {
        this.mVin = mVin;
    }

    public String getCduId() {
        return this.mCduId;
    }

    public void setCduId(String mCduId) {
        this.mCduId = mCduId;
    }

    public String getKey() {
        return this.mKey;
    }

    public void setKey(String mKey) {
        this.mKey = mKey;
    }

    public ParamRepairMode getMaintainMode() {
        return this.mMaintainMode;
    }

    public void setMaintainMode(ParamRepairMode mMaintainMode) {
        this.mMaintainMode = mMaintainMode;
    }

    public ParamRepairMode getSpeedLimitMode() {
        return this.mSpeedLimitMode;
    }

    public void setSpeedLimitMode(ParamRepairMode mSpeedLimitMode) {
        this.mSpeedLimitMode = mSpeedLimitMode;
    }
}
