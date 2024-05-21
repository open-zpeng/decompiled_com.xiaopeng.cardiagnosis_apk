package com.xiaopeng.commonfunc.model.car;

import android.car.hardware.mcu.CarMcuManager;
import com.xiaopeng.commonfunc.utils.DataHelp;
import com.xiaopeng.lib.utils.LogUtils;
import java.util.Arrays;
/* loaded from: classes4.dex */
public class McuModel extends CommonCarModel<CarMcuManager> {
    public static final int OTA_ANDROID_ACK_OK = 0;
    public static final int OTA_MCU_ACK_NOT_OK = -1;
    public static final int OTA_MCU_ACK_OK = 0;
    public static final int OTA_MCU_REQ_FIRMWARE_A = 1;
    public static final int OTA_MCU_REQ_FIRMWARE_B = 2;
    public static final int OTA_MCU_REQ_FLASH_DRIVER = 0;
    public static final int OTA_MCU_REQ_MCU_RESET = 4;
    public static final int OTA_MCU_REQ_UPDATE = 1;
    public static final int OTA_MCU_RSP_DRIVER_CHECK_OK = 9;
    public static final int OTA_MCU_RSP_FIRWARE_CHECK_OK = 10;
    public static final int OTA_MCU_RSP_UPDATE_SUCCESS_FINISH = 5;
    public static final int OTA_MCU_SEND_UPDATEFILE_FINISH = 100;

    public McuModel(String name) {
        super(name);
    }

    @Override // com.xiaopeng.commonfunc.model.car.CommonCarModel
    protected String getServiceName() {
        return "xp_mcu";
    }

    public int getOtaMcuReqStatus() {
        LogUtils.d(this.TAG, "getOtaMcuReqStatus");
        int status = -1;
        try {
            CarMcuManager carMcuManager = getCarManager();
            if (carMcuManager != null) {
                status = carMcuManager.getOtaMcuReqStatus();
            } else {
                LogUtils.e(this.TAG, "getOtaMcuReqStatus carMcuManager == null");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return status;
    }

    public void setOtaMcuReqStatus(int data) {
        String str = this.TAG;
        LogUtils.d(str, "setOtaMcuReqStatus data:" + data);
        try {
            CarMcuManager carMcuManager = getCarManager();
            if (carMcuManager != null) {
                carMcuManager.setOtaMcuReqStatus(data);
            } else {
                LogUtils.e(this.TAG, "setOtaMcuReqStatus carMcuManager == null");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void setOtaMcuSendUpdatefile(String file) {
        String str = this.TAG;
        LogUtils.d(str, "setOtaMcuSendUpdatefile file:" + file);
        try {
            CarMcuManager carMcuManager = getCarManager();
            if (carMcuManager != null) {
                carMcuManager.setOtaMcuSendUpdatefile(file);
            } else {
                LogUtils.e(this.TAG, "setOtaMcuSendUpdatefile carMcuManager == null");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void setOtaMcuReqUpdatefile(int data) {
        String str = this.TAG;
        LogUtils.d(str, "setOtaMcuReqUpdatefile data:" + data);
        try {
            CarMcuManager carMcuManager = getCarManager();
            if (carMcuManager != null) {
                carMcuManager.setOtaMcuReqUpdatefile(data);
            } else {
                LogUtils.e(this.TAG, "setOtaMcuReqUpdatefile carMcuManager == null");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void sendFactoryTestMsgToMcu(int[] msg) {
        String str = this.TAG;
        LogUtils.d(str, "sendFactoryTestMsgToMcu msg: " + Arrays.toString(msg));
        try {
            CarMcuManager carMcuManager = getCarManager();
            if (carMcuManager != null) {
                carMcuManager.sendFactoryTestMsgToMcu(msg);
            } else {
                LogUtils.e(this.TAG, "sendFactoryTestMsgToMcu carMcuManager == null");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void sendFactoryPmSilentMsgToMcu(int msg) {
        String str = this.TAG;
        LogUtils.d(str, "sendFactoryPmSilentMsgToMcu msg: " + msg);
        try {
            CarMcuManager carMcuManager = getCarManager();
            if (carMcuManager != null) {
                carMcuManager.sendFactoryPmSilentMsgToMcu(msg);
            } else {
                LogUtils.e(this.TAG, "sendFactoryPmSilentMsgToMcu carMcuManager == null");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public String getFactoryDisplayTypeMsgToMcu() {
        LogUtils.d(this.TAG, "getFactoryDisplayTypeMsgToMcu");
        String res = "";
        try {
            CarMcuManager carMcuManager = getCarManager();
            if (carMcuManager != null) {
                res = carMcuManager.getFactoryDisplayTypeMsgToMcu();
            } else {
                LogUtils.e(this.TAG, "getFactoryDisplayTypeMsgToMcu carMcuManager == null");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return res;
    }

    public void sendFactoryDisplayTypeMsgToMcu(int msg) {
        String str = this.TAG;
        LogUtils.d(str, "sendFactoryDisplayTypeMsgToMcu msg:" + msg);
        try {
            CarMcuManager carMcuManager = getCarManager();
            if (carMcuManager != null) {
                carMcuManager.sendFactoryDisplayTypeMsgToMcu(msg);
            } else {
                LogUtils.e(this.TAG, "sendFactoryDisplayTypeMsgToMcu carMcuManager == null");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void sendFactoryDugReqMsgToMcu(int[] msg) {
        String str = this.TAG;
        LogUtils.d(str, "sendFactoryDugReqMsgToMcu msg: " + Arrays.toString(msg));
        try {
            CarMcuManager carMcuManager = getCarManager();
            if (carMcuManager != null) {
                carMcuManager.sendFactoryDugReqMsgToMcu(msg);
            } else {
                LogUtils.e(this.TAG, "sendFactoryDugReqMsgToMcu carMcuManager == null");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void sendFactoryMcuBmsMsgToMcu(int msg) {
        String str = this.TAG;
        LogUtils.d(str, "sendFactoryMcuBmsMsgToMcu msg:" + msg);
        try {
            CarMcuManager carMcuManager = getCarManager();
            if (carMcuManager != null) {
                carMcuManager.sendFactoryMcuBmsMsgToMcu(msg);
            } else {
                LogUtils.e(this.TAG, "sendFactoryMcuBmsMsgToMcu carMcuManager == null");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void sendFactoryPwrDebugMsgToMcu(int[] msg) {
        String str = this.TAG;
        LogUtils.d(str, "sendFactoryPwrDebugMsgToMcu msg: " + Arrays.toString(msg));
        try {
            CarMcuManager carMcuManager = getCarManager();
            if (carMcuManager != null) {
                carMcuManager.sendFactoryPwrDebugMsgToMcu(msg);
            } else {
                LogUtils.e(this.TAG, "sendFactoryPwrDebugMsgToMcu carMcuManager == null");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void setRepairMode(boolean mode) {
        String str = this.TAG;
        LogUtils.d(str, "setRepairMode mode:" + mode);
        try {
            CarMcuManager carMcuManager = getCarManager();
            if (carMcuManager != null) {
                carMcuManager.setRepairMode(mode ? 1 : 0);
            } else {
                LogUtils.e(this.TAG, "setRepairMode carMcuManager == null");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void setPsuTestReq(int req) {
        String str = this.TAG;
        LogUtils.d(str, "setPsuTestReq req:" + req);
        try {
            CarMcuManager carMcuManager = getCarManager();
            if (carMcuManager != null) {
                carMcuManager.setPsuTestReq(req);
            } else {
                LogUtils.e(this.TAG, "setPsuTestReq carMcuManager == null");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public float getDvMcuTemp() {
        float res = -1.0f;
        try {
            CarMcuManager carMcuManager = getCarManager();
            if (carMcuManager != null) {
                res = carMcuManager.getDvMcuTemp();
            } else {
                LogUtils.e(this.TAG, "getDvMcuTemp carMcuManager == null");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        String str = this.TAG;
        LogUtils.d(str, "getDvMcuTemp res:" + res);
        return res;
    }

    public float getDvBatTemp() {
        float res = -1.0f;
        try {
            CarMcuManager carMcuManager = getCarManager();
            if (carMcuManager != null) {
                res = carMcuManager.getDvBatTemp();
            } else {
                LogUtils.e(this.TAG, "getDvBatTemp carMcuManager == null");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        String str = this.TAG;
        LogUtils.d(str, "getDvBatTemp res:" + res);
        return res;
    }

    public float getDvPcbTemp() {
        float res = -1.0f;
        try {
            CarMcuManager carMcuManager = getCarManager();
            if (carMcuManager != null) {
                res = carMcuManager.getDvPcbTemp();
            } else {
                LogUtils.e(this.TAG, "getDvPcbTemp carMcuManager == null");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        String str = this.TAG;
        LogUtils.d(str, "getDvPcbTemp res:" + res);
        return res;
    }

    public int getChairWelcomeMode() {
        int res = 0;
        try {
            CarMcuManager carMcuManager = getCarManager();
            if (carMcuManager != null) {
                res = carMcuManager.getChairWelcomeMode();
            } else {
                LogUtils.e(this.TAG, "getChairWelcomeMode carMcuManager == null");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        String str = this.TAG;
        LogUtils.d(str, "getChairWelcomeMode res:" + res);
        return res;
    }

    public byte[] getDvBattMsg() {
        byte[] res = null;
        try {
            CarMcuManager carMcuManager = getCarManager();
            if (carMcuManager != null) {
                res = carMcuManager.getDvBattMsg();
            } else {
                LogUtils.e(this.TAG, "getDvBattMsg carMcuManager == null");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        String str = this.TAG;
        LogUtils.d(str, "getDvBattMsg res:" + DataHelp.byteArrayToHexStr(res, true));
        return res;
    }

    public void setDvTestReq(int req) {
        String str = this.TAG;
        LogUtils.d(str, "setDvTestReq req:" + req);
        try {
            CarMcuManager carMcuManager = getCarManager();
            if (carMcuManager != null) {
                carMcuManager.setDvTestReq(req);
            } else {
                LogUtils.e(this.TAG, "setDvTestReq carMcuManager == null");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void setMcuMonitorSwitch(boolean onoff) {
        int i;
        try {
            CarMcuManager carMcuManager = getCarManager();
            if (carMcuManager != null) {
                if (onoff) {
                    i = 0;
                } else {
                    i = 1;
                }
                carMcuManager.setMcuMonitorSwitch(i, 0);
                LogUtils.i(this.TAG, "setMcuMonitorSwitch :" + onoff);
            } else {
                LogUtils.e(this.TAG, "setMcuMonitorSwitch error:mCarMcuManager is null ");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void sendMapVersion(String version) {
        String str = this.TAG;
        LogUtils.i(str, "sendMapVersion version : " + version);
        try {
            CarMcuManager carMcuManager = getCarManager();
            if (carMcuManager != null) {
                carMcuManager.sendMapVersion(version);
            } else {
                LogUtils.e(this.TAG, "sendMapVersion carMcuManager == null");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void disableFactoryMode() {
        LogUtils.i(this.TAG, "disableFactoryMode");
        try {
            CarMcuManager carMcuManager = getCarManager();
            if (carMcuManager != null) {
                carMcuManager.setFactoryModeSwitch(0);
            } else {
                LogUtils.e(this.TAG, "disableFactoryMode carMcuManager == null");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public boolean isFactoryMode() {
        int res = 0;
        try {
            CarMcuManager carMcuManager = getCarManager();
            if (carMcuManager != null) {
                res = carMcuManager.getFactoryModeSwitchStatus();
            } else {
                LogUtils.e(this.TAG, "isFactoryMode carMcuManager == null");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        String str = this.TAG;
        LogUtils.i(str, "isFactoryMode res:" + res);
        return res == 1;
    }

    public boolean isFactoryKeyUnlocked() {
        int res = 0;
        try {
            CarMcuManager carMcuManager = getCarManager();
            if (carMcuManager != null) {
                res = carMcuManager.getTemporaryFactoryStatus();
            } else {
                LogUtils.e(this.TAG, "isFactoryKeyUnlocked carMcuManager == null");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        String str = this.TAG;
        LogUtils.i(str, "isFactoryKeyUnlocked res:" + res);
        return res == 1;
    }

    public void setSocRespDTCInfo(int module, int errCode, int errCodeSt) {
        String str = this.TAG;
        LogUtils.d(str, "DTC Info , module: " + module + ", errCode: " + errCode + ", errCodeSt: " + errCodeSt);
        try {
            CarMcuManager carMcuManager = getCarManager();
            if (carMcuManager != null) {
                carMcuManager.setSocRespDTCInfo(module, errCode, errCodeSt);
            } else {
                LogUtils.e(this.TAG, "setSocRespDTCInfo carMcuManager == null");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
