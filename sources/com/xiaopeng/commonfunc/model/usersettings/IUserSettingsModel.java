package com.xiaopeng.commonfunc.model.usersettings;
/* loaded from: classes4.dex */
public interface IUserSettingsModel {
    void grabUploadCan(String str);

    boolean isDebugOn();

    boolean isUdiskReadOnly();

    void setAutoCatchLog(boolean z);

    void setConsoleStatus(boolean z);

    void setDebugStatus(boolean z);

    void setShowDialog(boolean z);

    void setUdiskReadOnly(boolean z);
}
