package com.xiaopeng.commonfunc.bean;

import com.google.gson.annotations.SerializedName;
import com.xiaopeng.commonfunc.model.security.PsuKeyModel;
/* loaded from: classes4.dex */
public class VcuModeResponse {
    @SerializedName("code")
    private Integer mCode;
    @SerializedName(PsuKeyModel.RESPONSE_MESSAGE)
    private String mMessage;
    @SerializedName("mode")
    private Integer mMode;

    public VcuModeResponse(Integer mCode, String mMessage, Integer mMode) {
        this.mCode = mCode;
        this.mMessage = mMessage;
        this.mMode = mMode;
    }

    public Integer getCode() {
        return this.mCode;
    }

    public void setCode(Integer mCode) {
        this.mCode = mCode;
    }

    public String getMessage() {
        return this.mMessage;
    }

    public void setMessage(String mMessage) {
        this.mMessage = mMessage;
    }

    public Integer getMode() {
        return this.mMode;
    }

    public void setMode(Integer mMode) {
        this.mMode = mMode;
    }

    public String toString() {
        return "VcuModeResponse{mCode=" + this.mCode + ", mMessage='" + this.mMessage + "', mMode=" + this.mMode + '}';
    }
}
