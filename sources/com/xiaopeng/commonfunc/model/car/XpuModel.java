package com.xiaopeng.commonfunc.model.car;

import android.car.hardware.xpu.CarXpuManager;
import com.xiaopeng.lib.utils.LogUtils;
/* loaded from: classes4.dex */
public class XpuModel extends CommonCarModel<CarXpuManager> {
    public XpuModel(String name) {
        super(name);
    }

    @Override // com.xiaopeng.commonfunc.model.car.CommonCarModel
    protected String getServiceName() {
        return "xp_xpu";
    }

    public void sendXpuUpdateRequest(String str) {
        try {
            CarXpuManager carXpuManager = getCarManager();
            if (carXpuManager != null) {
                carXpuManager.sendUpdateRequest(str);
            } else {
                LogUtils.e(this.TAG, "sendIcmUpdateRequest error:carXpuManager is null ");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void sendFtpTransferResult(int sendRet) {
        try {
            CarXpuManager carXpuManager = getCarManager();
            if (carXpuManager != null) {
                carXpuManager.setUpdateFileTransferStatus(sendRet);
            } else {
                LogUtils.e(this.TAG, "sendIcmUpdateRequest error:carXpuManager is null ");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void setNedcSwitch(int onOff) {
        try {
            CarXpuManager carXpuManager = getCarManager();
            if (carXpuManager != null) {
                carXpuManager.setNedcSwitch(onOff);
            } else {
                LogUtils.e(this.TAG, "setNedcSwitch error:carXpuManager is null ");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
