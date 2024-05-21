package com.xiaopeng.commonfunc.bean.http;

import com.google.gson.annotations.SerializedName;
import com.xiaopeng.commonfunc.Constant;
/* loaded from: classes4.dex */
public class CellularBean {
    @SerializedName("flowUnit")
    private String mFlowUnit;
    @SerializedName(Constant.HTTP_KEY_ICCID)
    private String mIccid;
    @SerializedName("plateNo")
    private String mPlateNo;
    @SerializedName("respCode")
    private String mRespCode;
    @SerializedName("respDesc")
    private String mRespDesc;
    @SerializedName("restFlow")
    private String mRestFlow;
    @SerializedName("success")
    private boolean mSuccess;
    @SerializedName("totalFlow")
    private String mTotalFlow;
    @SerializedName("updateTime")
    private String mUpdateTime;
    @SerializedName("vehicleType")
    private String mVehicleType;
    @SerializedName(Constant.HTTP_KEY_VIN)
    private String mVin;

    public String getPlateNo() {
        return this.mPlateNo;
    }

    public void setPlateNo(String plateNo) {
        this.mPlateNo = plateNo;
    }

    public String getVin() {
        return this.mVin;
    }

    public void setVin(String vin) {
        this.mVin = vin;
    }

    public String getVehicleType() {
        return this.mVehicleType;
    }

    public void setVehicleType(String vehicleType) {
        this.mVehicleType = vehicleType;
    }

    public String getIccid() {
        return this.mIccid;
    }

    public void setIccid(String iccid) {
        this.mIccid = iccid;
    }

    public String getTotalFlow() {
        return this.mTotalFlow;
    }

    public void setTotalFlow(String totalFlow) {
        this.mTotalFlow = totalFlow;
    }

    public String getRestFlow() {
        return this.mRestFlow;
    }

    public void setRestFlow(String restFlow) {
        this.mRestFlow = restFlow;
    }

    public String getUpdateTime() {
        return this.mUpdateTime;
    }

    public void setUpdateTime(String updateTime) {
        this.mUpdateTime = updateTime;
    }

    public String getFlowUnit() {
        return this.mFlowUnit;
    }

    public void setFlowUnit(String flowUnit) {
        this.mFlowUnit = flowUnit;
    }

    public String getRespCode() {
        return this.mRespCode;
    }

    public void setRespCode(String respCode) {
        this.mRespCode = respCode;
    }

    public int getIntCode() {
        try {
            return Integer.valueOf(this.mRespCode).intValue();
        } catch (Exception e) {
            return 0;
        }
    }

    public String getRespDesc() {
        return this.mRespDesc;
    }

    public void setRespDesc(String respDesc) {
        this.mRespDesc = respDesc;
    }

    public boolean isSuccess() {
        return this.mSuccess;
    }

    public void setSuccess(boolean success) {
        this.mSuccess = success;
    }

    public String toString() {
        return "CellularBean{mRespCode='" + this.mRespCode + "', mRespDesc='" + this.mRespDesc + "', mPlateNo='" + this.mPlateNo + "', mVin='" + this.mVin + "', mVehicleType='" + this.mVehicleType + "', mIccid='" + this.mIccid + "', mTotalFlow='" + this.mTotalFlow + "', mRestFlow='" + this.mRestFlow + "', mFlowUnit='" + this.mFlowUnit + "', mUpdateTime='" + this.mUpdateTime + "', mSuccess=" + this.mSuccess + '}';
    }
}
