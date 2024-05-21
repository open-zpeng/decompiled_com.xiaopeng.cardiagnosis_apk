package com.xiaopeng.commonfunc.bean;

import com.google.gson.annotations.SerializedName;
import com.xiaopeng.commonfunc.Constant;
/* loaded from: classes4.dex */
public class DiagnosisError {
    @SerializedName("errorcode")
    private int mErrorCode;
    @SerializedName("errormsg")
    private String mErrorMsg;
    @SerializedName(Constant.HTTP_KEY_MODULE)
    private int mModule;

    public int getModule() {
        return this.mModule;
    }

    public void setModule(int mModule) {
        this.mModule = mModule;
    }

    public int getErrorCode() {
        return this.mErrorCode;
    }

    public void setErrorCode(int mErrorCode) {
        this.mErrorCode = mErrorCode;
    }

    public String getErrorMsg() {
        return this.mErrorMsg;
    }

    public void setErrorMsg(String mErrorMsg) {
        this.mErrorMsg = mErrorMsg;
    }

    public String toString() {
        return "DiagnosisError{mModule='" + this.mModule + "', mErrorCode='" + this.mErrorCode + "', mErrorMsg='" + this.mErrorMsg + "'}";
    }
}
