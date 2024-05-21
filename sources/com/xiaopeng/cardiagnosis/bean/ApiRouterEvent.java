package com.xiaopeng.cardiagnosis.bean;

import com.google.gson.annotations.SerializedName;
import com.xiaopeng.cardiagnosis.CarApplication;
import com.xiaopeng.libconfig.ipc.IpcConfig;
/* loaded from: classes4.dex */
public class ApiRouterEvent {
    @SerializedName("senderPackageName")
    private String mSenderPackageName;
    @SerializedName(IpcConfig.IPCKey.STRING_MSG)
    private String mStringMsg;

    public ApiRouterEvent(String mSenderPackageName, String mStringMsg) {
        this.mSenderPackageName = mSenderPackageName;
        this.mStringMsg = mStringMsg;
    }

    public ApiRouterEvent(String mStringMsg) {
        this.mStringMsg = mStringMsg;
        this.mSenderPackageName = CarApplication.getApplication().getPackageName();
    }

    public String getSenderPackageName() {
        return this.mSenderPackageName;
    }

    public void setSenderPackageName(String mSenderPackageName) {
        this.mSenderPackageName = mSenderPackageName;
    }

    public String getStringMsg() {
        return this.mStringMsg;
    }

    public void setStringMsg(String mStringMsg) {
        this.mStringMsg = mStringMsg;
    }

    public String toString() {
        return "ApiRouterEvent{mSenderPackageName='" + this.mSenderPackageName + "', mStringMsg='" + this.mStringMsg + "'}";
    }
}
