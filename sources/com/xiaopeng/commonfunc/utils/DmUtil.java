package com.xiaopeng.commonfunc.utils;

import com.xiaopeng.commonfunc.bean.factorytest.TestResultItem;
/* loaded from: classes4.dex */
public class DmUtil {
    public static final byte[] ARGU_00_00 = {0, 0};
    public static final byte[] ARGU_00_01 = {0, 1};
    public static final byte[] ARGU_00_02 = {0, 2};
    public static final byte[] ARGU_01_00 = {1, 0};
    public static final byte[] ARGU_01_01 = {1, 1};
    public static final byte[] ARGU_01_02 = {1, 2};
    public static final byte[] ARGU_01_03 = {1, 3};
    public static final byte[] ARGU_01_04 = {1, 4};
    public static final byte[] ARGU_01_05 = {1, 5};
    public static final byte[] ARGU_01_06 = {1, 6};
    public static final byte[] ARGU_02_00 = {2, 0};
    public static final byte[] ARGU_02_01 = {2, 1};
    public static final byte[] ARGU_02_02 = {2, 2};
    public static final byte[] ARGU_02_03 = {2, 3};
    public static final byte[] ARGU_02_04 = {2, 4};
    public static final byte[] ARGU_02_05 = {2, 5};
    public static final byte[] ARGU_02_06 = {2, 6};
    public static final byte[] ARGU_04_00 = {4, 0};
    public static final byte[] ARGU_04_01 = {4, 1};
    public static final byte[] ARGU_04_02 = {4, 2};
    public static final byte[] ARGU_04_03 = {4, 3};
    public static final byte[] ARGU_04_04 = {4, 4};
    public static final byte[] ARGU_04_05 = {4, 5};
    public static final byte[] ARGU_04_06 = {4, 6};
    public static final byte[] ARGU_04_07 = {4, 7};
    public static final byte[] ARGU_04_08 = {4, 8};
    public static final byte[] ARGU_04_09 = {4, 9};
    public static final byte[] ARGU_04_0A = {4, 10};
    public static final byte[] ARGU_04_10 = {4, 16};
    public static final byte[] ENTER_EOL_CMD = {16, 96};
    public static final byte[] QUIT_EOL_CMD = {16, 0};
    private static final byte[] PREFIX_DM_COMMAND = {49, 1};
    private static final byte[] RESPONSE_SUCCESS_HANDLE = {113, 3};
    private static final byte[] RESPONSE_POSITIVE = {4};
    private static final byte[] RESPONSE_NEGATIVE = {5};
    private static final byte[] RESPONSE_NOTAPPLICABLE = {6};
    private static final byte[] RESPONSE_CMD_NOT_SUPPORT = {Byte.MAX_VALUE, 49, 18};
    private static final byte[] MSG_IM_READY = {82, TestResultItem.RESULT_ENTER, 65, 68, 89};
    private static final byte[] ENTER_EOL_SUCCESS = {16, 96, 4};
    private static final byte[] QUIT_EOL_SUCCESS = {16, 0, 4};

    /* loaded from: classes4.dex */
    public static class AgingTest {
        public static final byte[] NOTEST = {TestResultItem.RESULT_NOTEST};
        public static final byte[] ENTER = {TestResultItem.RESULT_ENTER};
        public static final byte[] PASS = {TestResultItem.RESULT_PASS};
        public static final byte[] ERROR_UNKNOW = {48};
        public static final byte[] ERROR_RESET = {49};
        public static final byte[] ERROR_DDR = {50};
        public static final byte[] ERROR_ATP = {51};
        public static final byte[] CMD_NAME = {-64, 13};
    }

    /* loaded from: classes4.dex */
    public static class BackLightTest {
        public static final byte[] CMD_NAME = {-64, 11};
    }

    /* loaded from: classes4.dex */
    public static class BluetoothTest {
        public static final byte[] STATUS_ON = {79, TestResultItem.RESULT_NOTEST};
        public static final byte[] STATUS_OFF = {79, TestResultItem.RESULT_FAIL, TestResultItem.RESULT_FAIL};
        public static final byte[] CMD_NAME = {-64, 3};
    }

    /* loaded from: classes4.dex */
    public static class CameraTest {
        public static final byte[] CMD_NAME = {-64, 12};
    }

    /* loaded from: classes4.dex */
    public static class DabTest {
        public static final byte[] CMD_NAME = {-64, 31};
    }

    /* loaded from: classes4.dex */
    public static class ExtAmpTest {
        public static final byte[] CMD_NAME = {-64, 22};
    }

    /* loaded from: classes4.dex */
    public static class FactoryMode {
        public static final byte[] CMD_NAME = {-64, 24};
        public static final byte[] STATUS_ON = {79, TestResultItem.RESULT_NOTEST};
        public static final byte[] STATUS_OFF = {79, TestResultItem.RESULT_FAIL, TestResultItem.RESULT_FAIL};
    }

    /* loaded from: classes4.dex */
    public static class Faildump {
        public static final byte[] CMD_NAME = {-64, 15};
    }

    /* loaded from: classes4.dex */
    public static class FanTest {
        public static final byte[] CMD_NAME = {-64, 30};
    }

    /* loaded from: classes4.dex */
    public static class FeedbackTest {
        public static final byte[] CMD_NAME = {-64, 18};
    }

    /* loaded from: classes4.dex */
    public static class FmTest {
        public static final byte[] CMD_NAME = {-64, 4};
    }

    /* loaded from: classes4.dex */
    public static class HwParam {
        public static final byte[] CMD_NAME = {-64, 19};
    }

    /* loaded from: classes4.dex */
    public static class IndivTest {
        public static final byte[] CMD_NAME = {-64, 27};
    }

    /* loaded from: classes4.dex */
    public static class KeyTest {
        public static final byte[] CMD_NAME = {-64, 29};
        public static final int RUN_KEYTEST_ACTIVITY = 0;
        public static final int STOP_KEYTEST_ACTIVITY = 1;
    }

    /* loaded from: classes4.dex */
    public static class LcdTest {
        public static final byte[] CMD_NAME = {-64, 21};
        public static final int RUN_DISPLAY = 1;
        public static final int STOP_DISPLAY = 0;
    }

    /* loaded from: classes4.dex */
    public static class LoopTest {
        public static final byte[] CMD_NAME = {-64, 5};
    }

    /* loaded from: classes4.dex */
    public static class NaviTest {
        public static final byte[] CMD_NAME = {-64, 26};
    }

    /* loaded from: classes4.dex */
    public static class PhyTest {
        public static final byte[] CMD_NAME = {-64, 9};
    }

    /* loaded from: classes4.dex */
    public static class SecurityTest {
        public static final byte[] CMD_NAME = {-64, 20};
    }

    /* loaded from: classes4.dex */
    public static class SpdifTest {
        public static final byte[] CMD_NAME = {-64, 17};
    }

    /* loaded from: classes4.dex */
    public static class SpkTest {
        public static final byte[] CMD_NAME = {-64, 7};
    }

    /* loaded from: classes4.dex */
    public static class TempTest {
        public static final byte[] CMD_NAME = {-64, 10};
    }

    /* loaded from: classes4.dex */
    public static class TestResult {
        public static final byte[] CMD_NAME = {-64, 14};
    }

    /* loaded from: classes4.dex */
    public static class TouchTest {
        public static final byte[] CMD_NAME = {-64, 28};
        public static final int RUN_TOUCH = 0;
        public static final int STOP_TOUCH = 1;
    }

    /* loaded from: classes4.dex */
    public static class UsbTest {
        public static final byte[] CMD_NAME = {-64, 8};
    }

    /* loaded from: classes4.dex */
    public static class VersName {
        public static final byte[] CMD_NAME = {-64, 1};
    }

    /* loaded from: classes4.dex */
    public static class WifiTest {
        public static final byte[] WLAN_STATUS_ON = {79, TestResultItem.RESULT_NOTEST};
        public static final byte[] WLAN_STATUS_OFF = {79, TestResultItem.RESULT_FAIL, TestResultItem.RESULT_FAIL};
        public static final byte[] CMD_NAME = {-64, 2};
    }

    public static byte[] responseWithValue(byte[] cmd, byte[] arg, byte[] value) {
        return DataHelp.byteMerger(RESPONSE_SUCCESS_HANDLE, cmd, RESPONSE_POSITIVE, DataHelp.byteSub(arg, 0, 2), value);
    }

    public static byte[] responseNG(byte[] cmd, byte[] arg) {
        return DataHelp.byteMerger(RESPONSE_SUCCESS_HANDLE, cmd, RESPONSE_NEGATIVE, DataHelp.byteSub(arg, 0, 2));
    }

    public static byte[] responseOK(byte[] cmd, byte[] arg) {
        return DataHelp.byteMerger(RESPONSE_SUCCESS_HANDLE, cmd, RESPONSE_POSITIVE, DataHelp.byteSub(arg, 0, 2));
    }

    public static byte[] responseNA(byte[] cmd, byte[] arg) {
        return DataHelp.byteMerger(RESPONSE_SUCCESS_HANDLE, cmd, RESPONSE_NOTAPPLICABLE, DataHelp.byteSub(arg, 0, 2));
    }

    public static byte[] responseCmdNotSupport() {
        return RESPONSE_CMD_NOT_SUPPORT;
    }

    public static byte[] responseIMReady() {
        return MSG_IM_READY;
    }

    public static byte[] responseEnterEOLSuccess() {
        return ENTER_EOL_SUCCESS;
    }

    public static byte[] responseQuitEOLSuccess() {
        return QUIT_EOL_SUCCESS;
    }
}
