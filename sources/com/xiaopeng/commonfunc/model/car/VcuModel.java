package com.xiaopeng.commonfunc.model.car;

import android.car.hardware.vcu.CarVcuManager;
import com.xiaopeng.lib.utils.LogUtils;
/* loaded from: classes4.dex */
public class VcuModel extends CommonCarModel<CarVcuManager> {
    public VcuModel(String name) {
        super(name);
    }

    @Override // com.xiaopeng.commonfunc.model.car.CommonCarModel
    protected String getServiceName() {
        return "xp_vcu";
    }

    public boolean isInCharging() {
        CarVcuManager carVcuManager = getCarManager();
        if (carVcuManager != null) {
            try {
                return carVcuManager.getChargeStatus() == 2;
            } catch (Exception e) {
                String str = this.TAG;
                LogUtils.e(str, "isInCharging-->" + e.getMessage());
            }
        }
        return false;
    }

    public boolean isUnderLevelP() {
        CarVcuManager carVcuManager = getCarManager();
        if (carVcuManager == null) {
            return false;
        }
        try {
            boolean isLevelP = carVcuManager.getDisplayGearLevel() == 4;
            return isLevelP;
        } catch (Exception e) {
            String str = this.TAG;
            LogUtils.e(str, "isUnderLevelP -->" + e.getMessage());
            return false;
        }
    }
}
