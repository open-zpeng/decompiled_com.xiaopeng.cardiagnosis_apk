package com.xiaopeng.lib.framework.moduleinterface.accountmodule;
/* loaded from: classes4.dex */
public interface ICallback<T, K> {
    void onFail(K error);

    void onSuccess(T data);
}
