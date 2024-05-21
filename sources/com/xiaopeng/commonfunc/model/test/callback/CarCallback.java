package com.xiaopeng.commonfunc.model.test.callback;

import android.car.hardware.CarPropertyValue;
/* loaded from: classes4.dex */
public interface CarCallback {
    void handleChangeEvent(CarPropertyValue carPropertyValue);

    void handleErrorEvent(int i, int i2);
}
