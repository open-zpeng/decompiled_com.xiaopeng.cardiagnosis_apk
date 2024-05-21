package com.xiaopeng.commonfunc.model.car;

import android.car.hardware.tbox.CarTboxManager;
import com.google.gson.Gson;
import com.xiaopeng.commonfunc.bean.car.TboxCanControlIPMsg;
import com.xiaopeng.commonfunc.bean.car.TboxCanControlMsg;
import com.xiaopeng.commonfunc.bean.car.TboxModemBand;
import com.xiaopeng.lib.utils.LogUtils;
/* loaded from: classes4.dex */
public class TboxModel extends CommonCarModel<CarTboxManager> {
    public static final int BATTERY_CELL_CONNECTED = 1;
    public static final int BATTERY_CELL_DISCONNECTED = 0;
    public static final int BATTERY_CHARGING = 1;
    public static final int BATTERY_DISCHARGING = 1;
    public static final int BATTERY_NOT_CHARGING = 0;
    public static final int BATTERY_NOT_DISCHARGING = 0;
    public static final int BATTERY_NOT_STEPUP = 0;
    public static final int BATTERY_STEPUP = 1;
    public static final int SIM_BLOCKED = 5;
    public static final int SIM_BUSY = 4;
    public static final int SIM_NOT_INSERTED = 0;
    public static final int SIM_PIN_REQ = 2;
    public static final int SIM_PUK_REQ = 3;
    public static final int SIM_READY = 1;
    public static final int SIM_UNKNOWN = 6;

    public TboxModel(String name) {
        super(name);
    }

    @Override // com.xiaopeng.commonfunc.model.car.CommonCarModel
    protected String getServiceName() {
        return "xp_tbox";
    }

    public void setGpsReset(int type) {
        String str = this.TAG;
        LogUtils.d(str, "setGpsReset type:" + type);
        try {
            CarTboxManager carTboxManager = getCarManager();
            if (carTboxManager != null) {
                carTboxManager.setGpsReset(type);
            } else {
                String str2 = this.TAG;
                LogUtils.e(str2, "setGpsReset carTboxManager == null type = " + type);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public int getGpsResetResp() {
        int gpsResp = -1;
        try {
            CarTboxManager carTboxManager = getCarManager();
            if (carTboxManager != null) {
                gpsResp = carTboxManager.getGpsResetResp();
            } else {
                LogUtils.e(this.TAG, "getGpsResetResp carTboxManager == null");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        String str = this.TAG;
        LogUtils.d(str, "getGpsResetResp gpsResp:" + gpsResp);
        return gpsResp;
    }

    public void getSimStatusAsync() {
        LogUtils.d(this.TAG, "getSimStatusAsync");
        try {
            CarTboxManager carTboxManager = getCarManager();
            if (carTboxManager != null) {
                carTboxManager.getSimStatusAsync();
            } else {
                LogUtils.e(this.TAG, "getSimStatusAsync carTboxManager == null");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void requestTBoxModemStatus() {
        LogUtils.d(this.TAG, "requestTBoxModemStatus");
        try {
            CarTboxManager carTboxManager = getCarManager();
            if (carTboxManager != null) {
                carTboxManager.requestTboxModemStatus();
            } else {
                LogUtils.e(this.TAG, "requestTBoxModemStatus carTboxManager == null");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void requestTBoxBandModemStatus() {
        LogUtils.d(this.TAG, "requestTBoxBandModemStatus");
        try {
            CarTboxManager carTboxManager = getCarManager();
            if (carTboxManager != null) {
                carTboxManager.requestTboxBandModemStatus();
            } else {
                LogUtils.e(this.TAG, "requestTBoxBandModemStatus carTboxManager == null");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void setTBoxBandModem(TboxModemBand bands) {
        String str = this.TAG;
        LogUtils.d(str, "setTBoxBandModem bands:" + bands.toString());
        try {
            CarTboxManager carTboxManager = getCarManager();
            if (carTboxManager != null) {
                carTboxManager.setTboxBandModem(new Gson().toJson(bands));
            } else {
                LogUtils.e(this.TAG, "setTBoxBandModem carTboxManager == null");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void setTboxCanControlMsg(TboxCanControlMsg msg) {
        String str = this.TAG;
        LogUtils.d(str, "setTboxCanControlMsg msg:" + msg.toString());
        try {
            CarTboxManager carTboxManager = getCarManager();
            if (carTboxManager != null) {
                carTboxManager.setTboxCanControlMsg(new Gson().toJson(msg));
            } else {
                LogUtils.e(this.TAG, "setTboxCanControlMsg carTboxManager == null");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void setTboxCanControlMsg(TboxCanControlIPMsg msg) {
        String str = this.TAG;
        LogUtils.d(str, "setTboxCanControlMsg msg:" + msg.toString());
        try {
            CarTboxManager carTboxManager = getCarManager();
            if (carTboxManager != null) {
                carTboxManager.setTboxCanControlMsg(new Gson().toJson(msg));
            } else {
                LogUtils.e(this.TAG, "setTboxCanControlMsg carTboxManager == null");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public String getDvBattMsg() {
        String res = null;
        try {
            CarTboxManager carTboxManager = getCarManager();
            if (carTboxManager != null) {
                res = carTboxManager.getDvBattMsg();
            } else {
                LogUtils.e(this.TAG, "getDvBattMsg carTboxManager == null");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        String str = this.TAG;
        LogUtils.d(str, "getDvBattMsg res:" + res);
        return res;
    }

    public void stopCharge() {
        LogUtils.d(this.TAG, "stopCharge");
        try {
            CarTboxManager carTboxManager = getCarManager();
            if (carTboxManager != null) {
                carTboxManager.stopCharge();
            } else {
                LogUtils.e(this.TAG, "stopCharge carTboxManager == null");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void startCharge() {
        LogUtils.d(this.TAG, "startCharge");
        try {
            CarTboxManager carTboxManager = getCarManager();
            if (carTboxManager != null) {
                carTboxManager.startCharge();
            } else {
                LogUtils.e(this.TAG, "startCharge carTboxManager == null");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void startTboxCertInstall() {
        LogUtils.d(this.TAG, "startTboxCertInstall");
        try {
            CarTboxManager carTboxManager = getCarManager();
            if (carTboxManager != null) {
                carTboxManager.startTboxCertInstall();
            } else {
                LogUtils.e(this.TAG, "startTboxCertInstall carTboxManager == null");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void startTboxCertVerify() {
        LogUtils.d(this.TAG, "startTboxCertVerify");
        try {
            CarTboxManager carTboxManager = getCarManager();
            if (carTboxManager != null) {
                carTboxManager.startTboxCertVerify();
            } else {
                LogUtils.e(this.TAG, "startTboxCertVerify carTboxManager == null");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void setDvTestReq(int req) {
        String str = this.TAG;
        LogUtils.d(str, "setDvTestReq req:" + req);
        try {
            CarTboxManager carTboxManager = getCarManager();
            if (carTboxManager != null) {
                carTboxManager.setDvTestReq(req);
            } else {
                LogUtils.e(this.TAG, "setDvTestReq carTboxManager == null");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void setDvTempSamplingPeriod(int second) {
        String str = this.TAG;
        LogUtils.d(str, "setDvTempSamplingPeriod second:" + second);
        try {
            CarTboxManager carTboxManager = getCarManager();
            if (carTboxManager != null) {
                carTboxManager.setDvTempSamplingPeriod(second);
            } else {
                LogUtils.e(this.TAG, "setDvTempSamplingPeriod carTboxManager == null");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public String getDvTempMsg() {
        String res = null;
        try {
            CarTboxManager carTboxManager = getCarManager();
            if (carTboxManager != null) {
                res = carTboxManager.getDvTempMsg();
            } else {
                LogUtils.e(this.TAG, "getDvTempMsg carTboxManager == null");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        String str = this.TAG;
        LogUtils.d(str, "getDvTempMsg res:" + res);
        return res;
    }

    public void sendUpgradingTboxByUdiskReq(String msg) {
        String str = this.TAG;
        LogUtils.d(str, "sendUpgradingTboxByUdiskReq msg:" + msg);
        try {
            CarTboxManager carTboxManager = getCarManager();
            if (carTboxManager != null) {
                carTboxManager.sendUpgradingTboxByUdiskReq(msg);
            } else {
                LogUtils.e(this.TAG, "sendUpgradingTboxByUdiskReq carTboxManager == null");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void sendRenewalPartsRequest(String msg) {
        String str = this.TAG;
        LogUtils.d(str, "sendRenewalPartsRequest msg:" + msg);
        try {
            CarTboxManager carTboxManager = getCarManager();
            if (carTboxManager != null) {
                carTboxManager.sendRenewalPartsRequest(msg);
            } else {
                LogUtils.e(this.TAG, "sendRenewalPartsRequest carTboxManager == null");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void sendMultiBleRenewalRequest(String msg) {
        String str = this.TAG;
        LogUtils.d(str, "sendMultiBleRenewalRequest msg:" + msg);
        try {
            CarTboxManager carTboxManager = getCarManager();
            if (carTboxManager != null) {
                carTboxManager.sendMultiBleRenewalRequest(msg);
            } else {
                LogUtils.e(this.TAG, "sendMultiBleRenewalRequest carTboxManager == null");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void setRepairMode(boolean onoff) {
        try {
            CarTboxManager carTboxManager = getCarManager();
            if (carTboxManager != null) {
                carTboxManager.setRepairMode(onoff ? 1 : 0);
                String str = this.TAG;
                LogUtils.i(str, "setRepairMode to tbox :" + onoff);
            } else {
                LogUtils.e(this.TAG, "setRepairMode to tbox error:mCarTboxManager is null ");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void sendFactoryPreCert(String msg) {
        String str = this.TAG;
        LogUtils.d(str, "sendFactoryPreCert msg:" + msg);
        try {
            CarTboxManager carTboxManager = getCarManager();
            if (carTboxManager != null) {
                carTboxManager.sendFactoryPreCert(msg);
            } else {
                LogUtils.e(this.TAG, "sendFactoryPreCert carTboxManager == null");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void setRoutingForTbox(int cmd) {
        String str = this.TAG;
        LogUtils.d(str, "setRoutingForTbox cmd:" + cmd);
        try {
            CarTboxManager carTboxManager = getCarManager();
            if (carTboxManager != null) {
                carTboxManager.setRoutingForTbox(cmd);
            } else {
                LogUtils.e(this.TAG, "setRoutingForTbox carTboxManager == null");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void setTboxModemCapture(int cmd) {
        String str = this.TAG;
        LogUtils.d(str, "setTboxModemCapture cmd:" + cmd);
        try {
            CarTboxManager carTboxManager = getCarManager();
            if (carTboxManager != null) {
                carTboxManager.setTboxModemCapture(cmd);
            } else {
                LogUtils.e(this.TAG, "setTboxModemCapture carTboxManager == null");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void sendCopyTboxLogRequest(int type) {
        String str = this.TAG;
        LogUtils.d(str, "sendCopyTboxLogRequest type:" + type);
        try {
            CarTboxManager carTboxManager = getCarManager();
            if (carTboxManager != null) {
                carTboxManager.sendCopyTboxLogRequest(type);
            } else {
                LogUtils.e(this.TAG, "sendCopyTboxLogRequest carTboxManager == null");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void sendFinishTboxLogRequest() {
        LogUtils.d(this.TAG, "sendFinishTboxLogRequest");
        try {
            CarTboxManager carTboxManager = getCarManager();
            if (carTboxManager != null) {
                carTboxManager.sendFinishTboxLogRequest();
            } else {
                LogUtils.e(this.TAG, "sendFinishTboxLogRequest carTboxManager == null");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
