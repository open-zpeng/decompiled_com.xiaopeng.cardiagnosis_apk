package com.xiaopeng.commonfunc.bean.aftersales;
/* loaded from: classes4.dex */
public class DiagnosisData {
    private final int mErrorCode;
    private final String mErrorMsg;
    private final String mTriggerTime;
    private final String mVersion;

    public DiagnosisData(int errorCode, String triggerTime, String errorMsg, String version) {
        this.mErrorCode = errorCode;
        this.mTriggerTime = triggerTime;
        this.mErrorMsg = errorMsg;
        this.mVersion = version;
    }

    public int getErrorCode() {
        return this.mErrorCode;
    }

    public String getTriggerTime() {
        return this.mTriggerTime;
    }

    public String getErrorMsg() {
        return this.mErrorMsg;
    }

    public String getVersion() {
        return this.mVersion;
    }

    public String toString() {
        return "DiagnosisData{mErrorCode=" + this.mErrorCode + ", mTriggerTime='" + this.mTriggerTime + "', mErrorMsg='" + this.mErrorMsg + "', mVersion='" + this.mVersion + "'}";
    }
}
