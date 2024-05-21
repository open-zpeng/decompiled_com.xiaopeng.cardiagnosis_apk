package com.xiaopeng.commonfunc.bean.event;
/* loaded from: classes4.dex */
public class ModemUpgradeEvent {
    private boolean isSuccess;

    public ModemUpgradeEvent(boolean isSucess) {
        this.isSuccess = isSucess;
    }

    public boolean isSuccess() {
        return this.isSuccess;
    }

    public void setSuccess(boolean success) {
        this.isSuccess = success;
    }
}
