package com.xiaopeng.commonfunc.utils;

import com.xiaopeng.commonfunc.Constant;
import com.xiaopeng.xmlconfig.Support;
/* loaded from: classes4.dex */
public class SystemPropertyUtil {
    public static boolean isChnRegion() {
        return Support.Spec.getString(Support.Spec.PROPERTY_VALUE_CHN_REGION).equalsIgnoreCase(Support.Properties.get(Support.Properties.LOCALE_REGION));
    }

    public static int getLogLevel() {
        return Support.Properties.getInt(Support.Properties.LOG_LEVEL, 1);
    }

    public static void setLogLevel(int level) {
        Support.Properties.set(Support.Properties.LOG_LEVEL, String.valueOf(level));
    }

    public static int getAdbTcpPort() {
        return Support.Properties.getInt(Support.Properties.TCP_PORT, -1);
    }

    public static void setAdbTcpPort(int port) {
        Support.Properties.set(Support.Properties.TCP_PORT, String.valueOf(port));
    }

    public static boolean getDebugModeStatus() {
        return Support.Properties.getBoolean(Support.Properties.DEBUG_MODE);
    }

    public static void setDebugMode(boolean enable) {
        Support.Properties.set(Support.Properties.DEBUG_MODE, String.valueOf(enable));
    }

    public static String getProductModel() {
        return Support.Properties.get(Support.Properties.PRODUCT_MODEL);
    }

    public static boolean isUdiskReadOnly() {
        return Support.Properties.getBoolean(Support.Properties.UDISK_READONLY);
    }

    public static void setUdiskReadOnly(boolean enable) {
        Support.Properties.set(Support.Properties.UDISK_READONLY, String.valueOf(enable));
    }

    public static boolean isBtHciDebugMode() {
        return !Support.Spec.getString(Support.Spec.PROPERTY_VALUE_HCILOG_OFF).equalsIgnoreCase(Support.Properties.get(Support.Properties.BTSNOOP_ENABLE, Support.Spec.getString(Support.Spec.PROPERTY_VALUE_HCILOG_OFF)));
    }

    public static void setBtHciDebugMode(boolean enable) {
        Support.Properties.set(Support.Properties.BTSNOOP_ENABLE, enable ? Support.Spec.getString(Support.Spec.PROPERTY_VALUE_HCILOG_ON) : Support.Spec.getString(Support.Spec.PROPERTY_VALUE_HCILOG_OFF));
    }

    public static void setBtHciPath(String path) {
        Support.Properties.set(Support.Properties.BTSNOOP_PATH, path);
    }

    public static void setAdbMtpOn(boolean enable) {
        Support.Properties.set(Support.Properties.USBCONFIG, enable ? Support.Spec.getString(Support.Spec.PROPERTY_VALUE_MTP_ADB) : Support.Spec.getString(Support.Spec.PROPERTY_VALUE_NONE));
    }

    public static String getUsbConfig() {
        return Support.Properties.get(Support.Properties.USBCONFIG);
    }

    public static String getSystemPropertyLteApn() {
        return Support.Properties.get(Support.Properties.LTE_APN);
    }

    public static void setSystemPropertyLteApn(String onoff) {
        Support.Properties.set(Support.Properties.LTE_APN, onoff);
    }

    public static boolean getModemLogStatus() {
        return Support.Properties.getBoolean("MODEM_LOG");
    }

    public static void setModemLog(boolean enable) {
        Support.Properties.set("MODEM_LOG", String.valueOf(enable));
    }

    public static String getSystemPropertyBuildType() {
        return Support.Properties.get(Support.Properties.BUILD_TYPE);
    }

    public static String getSystemPropertyRepairMode() {
        return Support.Properties.get(Support.Properties.REPAIR_MODE);
    }

    public static void setSystemPropertyRepairMode(String onoff) {
        Support.Properties.set(Support.Properties.REPAIR_MODE, onoff);
    }

    public static boolean isScpSupported() {
        return Support.Properties.getBoolean(Support.Properties.SCP_SUPPORTED);
    }

    public static boolean isIcmLoggingSupported() {
        return Support.Properties.getBoolean(Support.Properties.ICM_LOGGING);
    }

    public static boolean isNcmCluster() {
        return Support.Properties.getBoolean(Support.Properties.NCM_CLUSTER);
    }

    public static String getVIN() {
        return Support.Properties.get(Support.Properties.VIN, "");
    }

    public static String getUdiskPath() {
        return Support.Properties.get(Support.Properties.UDISK_PATH, "");
    }

    public static String getHardwareId() {
        return Support.Properties.get(Support.Properties.HARDWARE_ID, "");
    }

    public static int getVehicleId() {
        return Support.Properties.getInt(Support.Properties.VID, -1);
    }

    public static String getIccid() {
        return Support.Properties.get(Support.Properties.ICCID, "");
    }

    public static String getHwVersion() {
        return Support.Properties.get(Support.Properties.HARDWARE_VERSION);
    }

    public static String getSwVersion() {
        return Support.Properties.get(Support.Properties.SOFTWARE_VERSION);
    }

    public static String getMcuVersion() {
        return Support.Properties.get(Support.Properties.MCU_VERSION);
    }

    public static String getTboxVersion() {
        return Support.Properties.get(Support.Properties.TBOX_VERSION);
    }

    public static String getTmcuVersion() {
        return Support.Properties.get(Support.Properties.TMCU_VERSION);
    }

    public static void startConsole() {
        Support.Properties.set(Support.Properties.CONTROL_START, Support.Spec.getString(Support.Spec.PROPERTY_PROCESS_CONSOLE));
    }

    public static void stopConsole() {
        Support.Properties.set(Support.Properties.CONTROL_STOP, Support.Spec.getString(Support.Spec.PROPERTY_PROCESS_CONSOLE));
    }

    public static void startService(String service) {
        Support.Properties.set(Support.Properties.CONTROL_START, service);
    }

    public static void restartService(String service) {
        Support.Properties.set(Support.Properties.CONTROL_RESTART, service);
    }

    public static void stopService(String service) {
        Support.Properties.set(Support.Properties.CONTROL_STOP, service);
    }

    public static boolean getGrabLogOnOff() {
        return Support.Spec.getString(Support.Spec.PROPERTY_VALUE_ON).equalsIgnoreCase(Support.Properties.get(Support.Properties.GRAB_LOG));
    }

    public static void setGrabLogOnOff(boolean onOff) {
        Support.Properties.set(Support.Properties.GRAB_LOG, onOff ? Support.Spec.getString(Support.Spec.PROPERTY_VALUE_ON) : Support.Spec.getString(Support.Spec.PROPERTY_VALUE_OFF));
    }

    public static boolean getShowDialog() {
        return Support.Properties.getBoolean(Support.Properties.SHOW_DIALOG);
    }

    public static void setShowDialog(boolean show) {
        Support.Properties.set(Support.Properties.SHOW_DIALOG, String.valueOf(show));
    }

    public static boolean isDebugBin() {
        return !Support.Spec.getString(Support.Spec.PROPERTY_VALUE_USER).equalsIgnoreCase(Support.Properties.get(Support.Properties.BUILD_TYPE));
    }

    public static boolean getRamdumpOnOff() {
        return Support.Spec.getString(Support.Spec.PROPERTY_VALUE_RAMDUMP_ON).equalsIgnoreCase(Support.Properties.get(Support.Properties.RAMDUMP));
    }

    public static boolean isConsoleRunning() {
        return Support.Spec.getString(Support.Spec.PROPERTY_VALUE_RUNNING).equalsIgnoreCase(Support.Properties.get(Support.Properties.SVC_CONSOLE));
    }

    public static int getCfcIndex() {
        return Support.Properties.getInt(Support.Properties.CFCINDEX, 0);
    }

    public static void setCfcIndex(int value) {
        Support.Properties.set(Support.Properties.CFCINDEX, String.valueOf(value));
    }

    public static boolean isNaviLogOn() {
        return Support.Properties.getBoolean(Support.Properties.NAVI_LOG);
    }

    public static void setNaviLogOnoff(boolean enable) {
        Support.Properties.set(Support.Properties.NAVI_LOG, String.valueOf(enable));
    }

    public static boolean isFactoryBinary() {
        return Support.Spec.getInt(Support.Spec.BUILD_SPECIAL_FACTORY) == Support.Properties.getInt(Support.Properties.BUILD_SPECIAL, 0);
    }

    public static String getPartNumber() {
        return Support.Properties.get(Support.Properties.PARTNUMBER);
    }

    public static boolean isDebugStageIdentification() {
        String stage = Constant.UNKNOWN_STRING;
        String[] vers = Support.Properties.get(Support.Properties.SOFTWARE_VERSION).split(Constant.DATA_SEPARATOR_STRING);
        if (vers.length > 2) {
            stage = vers[vers.length - 1];
        }
        return Constant.DEV_STRING.equals(stage) || Constant.FC_STRING.equals(stage);
    }

    public static boolean isFactoryMode() {
        return Support.Properties.getBoolean(Support.Properties.FACTORY_MODE);
    }

    public static void setFactoryMode(boolean enable) {
        Support.Properties.set(Support.Properties.FACTORY_MODE, String.valueOf(enable));
    }

    public static String getSocEepromVersion() {
        return Support.Properties.get(Support.Properties.SOCEEPROM_MODEL) + Constant.DATA_SEPARATOR_STRING + Support.Properties.get(Support.Properties.SOCEEPROM_CRC);
    }

    public static boolean hasXPU() {
        return Support.Properties.getBoolean(Support.Properties.XPU);
    }

    public static boolean getIGONsetLimit() {
        return Support.Properties.getBoolean(Support.Properties.IG_ON_SET_LIMIT);
    }

    public static void setIGONsetLimit(boolean value) {
        Support.Properties.set(Support.Properties.IG_ON_SET_LIMIT, String.valueOf(value));
    }

    public static boolean isTcpDump() {
        if (Support.Properties.get(Support.Properties.TCPDUMP_TBOX, "").equals(Constant.TCPDUMP_RUNNING) || Support.Properties.get(Support.Properties.TCPDUMP_WLAN, "").equals(Constant.TCPDUMP_RUNNING) || Support.Properties.get(Support.Properties.TCPDUMP_ETH, "").equals(Constant.TCPDUMP_RUNNING)) {
            return true;
        }
        return false;
    }

    public static boolean isDiagMdlogRunning() {
        if (Support.Properties.get(Support.Properties.DIAG_MDLOG, "").equals(Constant.TCPDUMP_RUNNING)) {
            return true;
        }
        return false;
    }

    public static boolean getAuthModeSended() {
        return Support.Properties.getBoolean(Support.Properties.AUTHMODE_SENDED);
    }

    public static void setAuthModeSended(Boolean value) {
        Support.Properties.set(Support.Properties.AUTHMODE_SENDED, String.valueOf(value));
    }

    public static boolean getRepairModeSended() {
        return Support.Properties.getBoolean(Support.Properties.REPAIRMODE_SENDED);
    }

    public static void setRepairModeSended(Boolean value) {
        Support.Properties.set(Support.Properties.REPAIRMODE_SENDED, String.valueOf(value));
    }

    public static boolean getUploadRepairModeSended() {
        return Support.Properties.getBoolean(Support.Properties.UPLOAD_REPAIR_MODE_SENDED);
    }

    public static void setUploadRepairModeSended(Boolean value) {
        Support.Properties.set(Support.Properties.UPLOAD_REPAIR_MODE_SENDED, String.valueOf(value));
    }

    public static int getDevCodeVer() {
        return Support.Properties.getInt(Support.Properties.PROP_CODE_VERSION, 1);
    }
}
