package com.xiaopeng.lib.http;
/* loaded from: classes4.dex */
public interface ICallback<T, K> {
    void onError(K k);

    void onSuccess(T t);
}
