package com.xiaopeng.commonfunc.utils;

import com.xiaopeng.commonfunc.Constant;
/* loaded from: classes4.dex */
public class AtUtil {
    public static final String MSG_SOCKET_CONNECTED = "CONNECTION COMPLETED\r\n";
    public static final String arg0 = "0";
    public static final String arg1 = "1";
    public static final String arg2 = "2";

    /* loaded from: classes4.dex */
    public static class BattTest {
        public static final String CMD_NAME = "BATTTEST";
    }

    /* loaded from: classes4.dex */
    public static class BtTest {
        public static final String BT_STATUS_OFF = "OFF";
        public static final String BT_STATUS_ON = "ON";
        public static final int CLOSING = 2;
        public static final String CMD_NAME = "BTTEST";
        public static final int CONNECTING = 5;
        public static final int OPENING = 1;
        public static final int PAIRING = 4;
        public static final int PENDING = 0;
        public static final int SEARCHING = 3;
    }

    /* loaded from: classes4.dex */
    public static class CanTest {
        public static final String CMD_NAME = "CANTEST";
    }

    /* loaded from: classes4.dex */
    public static class EepromTest {
        public static final String CMD_NAME = "EEPROMTEST";
    }

    /* loaded from: classes4.dex */
    public static class FailDump {
        public static final int CORE_POOL_SIZE = 2;
        public static final String[] LOG_PATH = {"/sdcard/Log/log0/", "/sdcard/Log/mcu/log0.asc", "/data/anr/"};
        public static final String SDCARD_LOG_PATH = "/sdcard/Log/";
    }

    /* loaded from: classes4.dex */
    public static class FanTest {
        public static final int CLOSING = 2;
        public static final String CMD_NAME = "FANTEST";
        public static final int OPENING = 1;
        public static final int PENDING = 0;
    }

    /* loaded from: classes4.dex */
    public static class FmTest {
        public static final String CMD_NAME = "FMTEST";
        public static final int RADIO_AM = 3;
        public static final int RADIO_FM = 0;
    }

    /* loaded from: classes4.dex */
    public static class Gsensor {
        public static final String CMD_NAME = "GSENSOR";
        public static final int GETTING_OFFSET = 2;
        public static final int PENDING = 0;
        public static final int READING_STATUS = 1;
        public static final int SAMPLE_DATA_COUNT = 20;
    }

    /* loaded from: classes4.dex */
    public static class LcdTest {
        public static final int RUN_DISPLAY = 1;
        public static final int STOP_DISPLAY = 0;
    }

    /* loaded from: classes4.dex */
    public static class LteTest {
        public static final String LTE_STATUS_OFF = "OFF";
        public static final String LTE_STATUS_ON = "ON";
    }

    /* loaded from: classes4.dex */
    public static class McuTest {
        public static final String CMD_NAME = "MCUTEST";
    }

    /* loaded from: classes4.dex */
    public static class QrCode {
        public static final int CLOSE_VIEW_CDU_INFO_QRCODE = 1;
        public static final int OPEN_VIEW_CDU_INFO_QRCODE = 0;
    }

    /* loaded from: classes4.dex */
    public static class SpkTest {
        public static final String DSP_VOLUME_DEFAULT = "26";
        public static final String DSP_VOLUME_MAX = "32";
    }

    /* loaded from: classes4.dex */
    public static class TouchTest {
        public static final int RUN_TOUCH = 0;
        public static final int STOP_TOUCH = 1;
    }

    /* loaded from: classes4.dex */
    public static class Versname {
        public static final String CMD_NAME = "VERSNAME";
    }

    /* loaded from: classes4.dex */
    public static class WifiTest {
        public static final int CLOSING = 2;
        public static final String CMD_NAME = "WIFITEST";
        public static final int OPENING = 1;
        public static final int PENDING = 0;
        public static final String WLAN_STATUS_OFF = "OFF";
        public static final String WLAN_STATUS_ON = "ON";
    }

    public static String responseString(String cmd, String arg, String input) {
        String result = "\r\n+" + cmd + Constant.COLON_STRING + arg + Constant.SEPARATOR_STRING + input + ",OK\r\n";
        return result;
    }

    public static String responseNG(String cmd, String arg) {
        String result = "\r\n+" + cmd + Constant.COLON_STRING + arg + ",NG\r\n";
        return result;
    }

    public static String responseOK(String cmd, String arg) {
        String result = "\r\n+" + cmd + Constant.COLON_STRING + arg + ",OK\r\n";
        return result;
    }

    public static String responseNA(String cmd, String arg) {
        String result = "\r\n+" + cmd + Constant.COLON_STRING + arg + ",NA\r\n";
        return result;
    }
}
