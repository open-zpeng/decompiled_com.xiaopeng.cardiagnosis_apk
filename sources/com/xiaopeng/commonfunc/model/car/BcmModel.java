package com.xiaopeng.commonfunc.model.car;

import android.car.hardware.bcm.CarBcmManager;
import com.xiaopeng.lib.utils.LogUtils;
/* loaded from: classes4.dex */
public class BcmModel extends CommonCarModel<CarBcmManager> {
    public BcmModel(String name) {
        super(name);
    }

    @Override // com.xiaopeng.commonfunc.model.car.CommonCarModel
    protected String getServiceName() {
        return "xp_bcm";
    }

    public int[] getAlsInitializationStudyAndErrorState() {
        LogUtils.d(this.TAG, "getAlsInitializationStudyAndErrorState");
        int[] status = null;
        try {
            CarBcmManager carBcmManager = getCarManager();
            if (carBcmManager != null) {
                status = carBcmManager.getAlsInitializationStudyAndErrorState();
            } else {
                LogUtils.e(this.TAG, "getAlsInitializationStudyAndErrorState carBcmManager == null");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return status;
    }

    public boolean isCarLock() {
        CarBcmManager carBcmManager = getCarManager();
        if (carBcmManager != null) {
            try {
                return carBcmManager.getAtwsState() == 0;
            } catch (Exception e) {
                String str = this.TAG;
                LogUtils.e(str, "isCarLock-->" + e.getMessage());
            }
        }
        return false;
    }

    public void setRepairModeStatus(boolean status) {
        CarBcmManager carBcmManager = getCarManager();
        if (carBcmManager != null) {
            try {
                carBcmManager.setMaintainModeSw(status ? 1 : 0);
            } catch (Exception e) {
                String str = this.TAG;
                LogUtils.e(str, "setRepairModeStatus-->" + e.getMessage());
            }
        }
    }

    public boolean sendCMSLogCtrlReq(int channel, int req) {
        String str = this.TAG;
        LogUtils.i(str, "sendCMSLogCtrlReq channel: " + channel + " req: " + req);
        CarBcmManager carBcmManager = getCarManager();
        if (carBcmManager == null) {
            return false;
        }
        try {
            carBcmManager.sendCMSLogCtrlReq(channel, req);
            return true;
        } catch (Exception e) {
            String str2 = this.TAG;
            LogUtils.e(str2, "sendCMSLogCtrlReq-->" + e.getMessage());
            return false;
        }
    }
}
