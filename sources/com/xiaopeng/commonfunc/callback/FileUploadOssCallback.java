package com.xiaopeng.commonfunc.callback;
/* loaded from: classes4.dex */
public interface FileUploadOssCallback {
    void onFailure(String str, String str2, Exception exc);

    void onStart(String str, String str2);

    void onSuccess(String str, String str2);
}
