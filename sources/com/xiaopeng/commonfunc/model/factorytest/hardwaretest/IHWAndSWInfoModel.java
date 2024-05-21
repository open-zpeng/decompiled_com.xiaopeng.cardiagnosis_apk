package com.xiaopeng.commonfunc.model.factorytest.hardwaretest;

import android.content.Context;
import com.xiaopeng.lib.http.ICallback;
/* loaded from: classes4.dex */
public interface IHWAndSWInfoModel {
    void generateCarCodeBitmap(int i);

    void generateCodeBitmap(int i);

    void generateNumBitmap(int i);

    String getDeviceCode();

    String getHWInfo();

    void getPsuVersion();

    String getSWInfo();

    String getVehicleId();

    void init(Context context);

    void onDestroy();

    void requestCarCode(ICallback<String, String> iCallback);
}
