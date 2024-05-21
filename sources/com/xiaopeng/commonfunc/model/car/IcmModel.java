package com.xiaopeng.commonfunc.model.car;

import android.car.hardware.icm.CarIcmManager;
import com.xiaopeng.lib.utils.LogUtils;
/* loaded from: classes4.dex */
public class IcmModel extends CommonCarModel<CarIcmManager> {
    public IcmModel(String name) {
        super(name);
    }

    @Override // com.xiaopeng.commonfunc.model.car.CommonCarModel
    protected String getServiceName() {
        return "xp_icm";
    }

    public void sendUpdateRequest(String str) {
        try {
            CarIcmManager carIcmManager = getCarManager();
            if (carIcmManager != null) {
                carIcmManager.sendUpdateRequest(str);
                String str2 = this.TAG;
                LogUtils.d(str2, "sendIcmUpdateRequest :" + str);
            } else {
                LogUtils.e(this.TAG, "sendIcmUpdateRequest error:carIcmManager is null ");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void setUpdateFileTransferStatus(int sendRet) {
        try {
            CarIcmManager carIcmManager = getCarManager();
            if (carIcmManager != null) {
                carIcmManager.setUpdateFileTransferStatus(sendRet);
                String str = this.TAG;
                LogUtils.d(str, "setUpdateFileTransferStatus :" + sendRet);
            } else {
                LogUtils.e(this.TAG, "sendIcmUpdateRequest error:carIcmManager is null ");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void sendLogCompressRequest(String req) {
        String str = this.TAG;
        LogUtils.d(str, "sendLogCompressRequest :" + req);
        try {
            CarIcmManager carIcmManager = getCarManager();
            if (carIcmManager != null) {
                carIcmManager.sendLogCompressRequest(req);
            } else {
                LogUtils.e(this.TAG, "sendLogCompressRequest error:mCarIcmManager is null ");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
