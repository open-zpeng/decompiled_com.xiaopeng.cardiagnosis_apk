package com.xiaopeng.commonfunc.model.car;

import android.car.hardware.avm.CarAvmManager;
import com.xiaopeng.lib.utils.LogUtils;
/* loaded from: classes4.dex */
public class AvmModel extends CommonCarModel<CarAvmManager> {
    public AvmModel(String name) {
        super(name);
    }

    @Override // com.xiaopeng.commonfunc.model.car.CommonCarModel
    protected String getServiceName() {
        return "xp_avm";
    }

    public void setAvmCalibration(int type) {
        String str = this.TAG;
        LogUtils.d(str, "setAvmCalibration type:" + type);
        try {
            CarAvmManager carAvmManager = getCarManager();
            if (carAvmManager != null) {
                carAvmManager.setAvmCalibration(type);
            } else {
                LogUtils.e(this.TAG, "setAvmCalibration carAvmManager == null");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
