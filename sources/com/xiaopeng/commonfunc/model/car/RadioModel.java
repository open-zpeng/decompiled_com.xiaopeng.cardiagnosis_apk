package com.xiaopeng.commonfunc.model.car;

import android.car.CarNotConnectedException;
import android.car.hardware.CarPropertyValue;
import android.car.hardware.radio.CarRadioManager;
import com.xiaopeng.lib.utils.LogUtils;
import org.greenrobot.eventbus.EventBus;
/* loaded from: classes4.dex */
public class RadioModel {
    public static final int DEFAULT_FM = 87500;
    public static final int DEFAULT_VOLUME = 100;
    public static final int FM_GAP = 100;
    public static final String PATH_READ_FM_RSSI = "/sys/audio/tunerlevel";
    public static final int STATE_CLOSED = 0;
    public static final int STATE_OPEN = 1;
    private static RadioModel sRadioModel;
    final String TAG = "RadioModel";
    private final CarRadioManager.CarRadioEventCallback mCarRadioEventCallback = new CarRadioManager.CarRadioEventCallback() { // from class: com.xiaopeng.commonfunc.model.car.RadioModel.1
        public void onChangeEvent(CarPropertyValue value) {
            LogUtils.d("RadioModel", "onChangeEvent val.getValue(): " + value.getValue());
            EventBus.getDefault().post(value);
        }

        public void onErrorEvent(int propertyId, int zone) {
            LogUtils.d("RadioModel", "onErrorEvent propertyId:" + propertyId + " , zone:" + zone);
        }
    };
    private CarRadioManager mRadioManager;

    private RadioModel() {
        getCarManager();
        registerCallback();
    }

    public static RadioModel getInstance() {
        if (sRadioModel == null) {
            sRadioModel = new RadioModel();
        }
        return sRadioModel;
    }

    private void getCarManager() {
    }

    public void registerCallback() {
        LogUtils.d("RadioModel", "registerCallback mRadioManager");
        try {
            if (this.mRadioManager == null) {
                LogUtils.e("RadioModel", "registerCallback mRadioManager == null");
            } else {
                this.mRadioManager.registerCallback(this.mCarRadioEventCallback);
            }
        } catch (CarNotConnectedException e) {
            e.printStackTrace();
        }
    }

    public boolean openFm() {
        LogUtils.d("RadioModel", "openFm");
        try {
            if (this.mRadioManager == null) {
                LogUtils.e("RadioModel", "openFm mRadioManager is null");
                return true;
            }
            this.mRadioManager.setPowerOnTunner();
            return true;
        } catch (Exception e) {
            LogUtils.e("RadioModel", "openFm failed! " + e);
            return false;
        }
    }

    public boolean closeFm() {
        LogUtils.d("RadioModel", "closeFm");
        try {
            if (this.mRadioManager == null) {
                LogUtils.e("RadioModel", "closeFm mRadioManager is null");
                return true;
            }
            this.mRadioManager.setPowerOffTunner();
            return true;
        } catch (Exception e) {
            LogUtils.e("RadioModel", "closeFm failed! " + e);
            return false;
        }
    }

    public boolean setRadioFrequency(int band, int frequency) {
        LogUtils.d("RadioModel", "setRadioFrequency band:" + band + ", frequency:" + frequency);
        try {
            if (this.mRadioManager != null) {
                this.mRadioManager.setRadioFrequency(band, frequency);
                return true;
            }
            LogUtils.e("RadioModel", "setRadioFrequency mRadioManager is null");
            return true;
        } catch (Exception e) {
            LogUtils.e("RadioModel", "setRadioFrequency failed! " + e);
            return false;
        }
    }

    public boolean searchStationUp() {
        LogUtils.d("RadioModel", "searchStationUp");
        try {
            if (this.mRadioManager == null) {
                LogUtils.e("RadioModel", "searchStationUp mRadioManager is null");
                return true;
            }
            this.mRadioManager.setRadioSearchStationUp();
            return true;
        } catch (Exception e) {
            LogUtils.e("RadioModel", "searchStationUp! " + e);
            return false;
        }
    }

    public boolean searchStationDown() {
        LogUtils.d("RadioModel", "searchStationDown");
        try {
            if (this.mRadioManager == null) {
                LogUtils.e("RadioModel", "searchStationDown mRadioManager is null");
                return true;
            }
            this.mRadioManager.setRadioSearchStationDown();
            return true;
        } catch (Exception e) {
            LogUtils.e("RadioModel", "searchStationDown failed! " + e);
            return false;
        }
    }

    public void setRadioBand(int band) {
        LogUtils.d("RadioModel", "setRadioBand band:" + band);
        try {
            if (this.mRadioManager != null) {
                this.mRadioManager.setRadioBand(band);
            } else {
                LogUtils.e("RadioModel", "setRadioBand mRadioManager is null");
            }
        } catch (Exception e) {
            LogUtils.e("RadioModel", "setRadioBand failed! " + e);
        }
    }

    public void setRadioVolumePercent(int channel, int vol) {
        LogUtils.d("RadioModel", "setRadioVolumePercent channel:" + channel + ", vol:" + vol);
        try {
            if (this.mRadioManager != null) {
                this.mRadioManager.setRadioVolumePercent(channel, vol);
            } else {
                LogUtils.e("RadioModel", "setRadioVolumePercent mRadioManager is null");
            }
        } catch (Exception e) {
            LogUtils.e("RadioModel", "setRadioVolumePercent failed! " + e);
        }
    }

    public void startRadioVolumeAutoFocus(int percent) {
        LogUtils.d("RadioModel", "setRadioVolumeAutoFocus percent:" + percent);
        try {
            if (this.mRadioManager != null) {
                this.mRadioManager.setRadioVolumeAutoFocus(percent);
            } else {
                LogUtils.e("RadioModel", "setRadioVolumeAutoFocus mRadioManager is null");
            }
        } catch (Exception e) {
            LogUtils.e("RadioModel", "setRadioVolumeAutoFocus failed! " + e);
        }
    }

    public void setFmVolume(int channel, int volume) {
        LogUtils.d("RadioModel", "setFmVolume channel:" + channel + ", volume:" + volume);
        try {
            if (this.mRadioManager != null) {
                this.mRadioManager.setFmVolume(channel, volume);
            } else {
                LogUtils.e("RadioModel", "setFmVolume mRadioManager is null");
            }
        } catch (Exception e) {
            LogUtils.e("RadioModel", "setFmVolume failed! " + e);
        }
    }

    public int[] getRadioFrequency() {
        try {
            if (this.mRadioManager == null) {
                return null;
            }
            int[] result = this.mRadioManager.getRadioFrequency();
            return result;
        } catch (Exception e) {
            LogUtils.e("RadioModel", "getRadioFrequency failed! " + e);
            return null;
        }
    }

    public void setStartFullBandScan() {
        LogUtils.d("RadioModel", "setStartFullBandScan");
        try {
            if (this.mRadioManager == null) {
                LogUtils.e("RadioModel", "setStartFullBandScan mRadioManager is null");
            } else {
                this.mRadioManager.setStartFullBandScan();
            }
        } catch (Exception e) {
            LogUtils.e("RadioModel", "setStartFullBandScan failed! " + e);
        }
    }

    public void setStopFullBandScan() {
        LogUtils.d("RadioModel", "setStopFullBandScan");
        try {
            if (this.mRadioManager == null) {
                LogUtils.e("RadioModel", "setStopFullBandScan mRadioManager is null");
            } else {
                this.mRadioManager.setStopFullBandScan();
            }
        } catch (Exception e) {
            LogUtils.e("RadioModel", "setStopFullBandScan failed! " + e);
        }
    }
}
