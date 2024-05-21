package com.xiaopeng.commonfunc.model.car;

import android.car.hardware.imu.CarImuManager;
/* loaded from: classes4.dex */
public class ImuModel extends CommonCarModel<CarImuManager> {
    public ImuModel(String name) {
        super(name);
    }

    @Override // com.xiaopeng.commonfunc.model.car.CommonCarModel
    protected String getServiceName() {
        return "xp_imu";
    }
}
