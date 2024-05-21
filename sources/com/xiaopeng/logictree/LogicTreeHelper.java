package com.xiaopeng.logictree;

import com.xiaopeng.lib.utils.LogUtils;
import com.xiaopeng.xmlconfig.Support;
import java.util.Arrays;
import org.greenrobot.eventbus.EventBus;
/* loaded from: classes5.dex */
public class LogicTreeHelper {
    public static final String DEFAULT_LOGIC_TREE_PATH = "/system/etc/aftersales";
    public static final String DIAGNOSIS_LOGIC_TREE_LIST = "logictree_list.json";
    public static final int ENTRY_CHECK_MODE_UI = 2;
    public static final int ENTRY_REMOTE_DIAGNOSIS_PLATFORM = 1;
    public static final int ENTRY_TESTER_UI = 3;
    public static final int INDEX_DEFAULT_LOGIC_ACTION = -1;
    public static final int INDEX_NO_RESULT_LOGIC_ACTION = -2;
    public static final String LOGICTREE_CACHE_FOLDER = "/cache/aftersales/logictree";
    public static final String LOGICTREE_SUFFIX = ".zip";
    public static final String LOGICTREE_UPGRADE_FOLDER = Support.Path.getFilePath(Support.Path.LOGICTREE_UPGRADE_FOLDER);
    public static final String LOGIC_ACTION_RESPONSE_EXEC_NO_RESULT = "LOGIC_ACTION_RESPONSE_EXEC_NO_RESULT";
    public static final String LOGIC_ACTION_RESPONSE_RESULT_FAIL = "LOGIC_ACTION_RESPONSE_RESULT_FAIL";
    public static final String LOGIC_ACTION_RESPONSE_RESULT_NA = "LOGIC_ACTION_RESPONSE_RESULT_NA";
    public static final String LOGIC_ACTION_RESPONSE_RESULT_OK = "LOGIC_ACTION_RESPONSE_RESULT_OK";
    public static final int PARSE_RESULT_ALL_CONTAINS = 3;
    public static final int PARSE_RESULT_ALL_EQUALS = 1;
    public static final int PARSE_RESULT_ALL_NOT_CONTAINS = 4;
    public static final int PARSE_RESULT_ALL_NOT_EQUALS = 2;
    public static final int PARSE_RESULT_AND_EQUALS = 10;
    public static final int PARSE_RESULT_AND_GREATER = 17;
    public static final int PARSE_RESULT_BIT_GREATER_OR_EQUALS = 13;
    public static final int PARSE_RESULT_GREATER = 8;
    public static final int PARSE_RESULT_GREATER_OR_EQUALS = 9;
    public static final int PARSE_RESULT_HAS_ONE_CONTAINS = 6;
    public static final int PARSE_RESULT_HAS_ONE_CONTAINS_ONE_NEXT = 20;
    public static final int PARSE_RESULT_HAS_ONE_EQUALS = 5;
    public static final int PARSE_RESULT_HAS_ONE_NOT_CONTAINS = 7;
    public static final int PARSE_RESULT_OR_EQUALS = 11;
    public static final int PARSE_RESULT_OR_GREATER = 18;
    public static final int PARSE_RESULT_RANGE = 14;
    public static final int PARSE_RESULT_RANGE_WITHOUT_BOUNDARY = 16;
    public static final int PARSE_RESULT_REGEX_MATCH = 15;
    public static final int PARSE_RESULT_XOR_EQUALS = 12;
    public static final int PARSE_RESULT_XOR_GREATER = 19;
    public static final String TAG = "LogicTreeHelper";

    public static void responseResult(String result) {
        LogUtils.i(TAG, "responseResult : %s", result);
        EventBus.getDefault().post(new LogicActionResult(result));
    }

    public static void responseResult(Integer result) {
        LogUtils.i(TAG, "responseResult : %d", result);
        EventBus.getDefault().post(new LogicActionResult(result));
    }

    public static void responseResult(Long result) {
        LogUtils.i(TAG, "responseResult : %ld", result);
        EventBus.getDefault().post(new LogicActionResult(result));
    }

    public static void responseResult(int[] result) {
        LogUtils.i(TAG, "responseResult : %s", Arrays.toString(result));
        EventBus.getDefault().post(new LogicActionResult(result));
    }

    public static void responseResult(String[] result) {
        LogUtils.i(TAG, "responseResult : %s", Arrays.toString(result));
        EventBus.getDefault().post(new LogicActionResult(result));
    }

    public static void responseNG() {
        LogUtils.i(TAG, "responseResult : LOGIC_ACTION_RESPONSE_RESULT_FAIL");
        EventBus.getDefault().post(new LogicActionResult(LOGIC_ACTION_RESPONSE_RESULT_FAIL));
    }

    public static void responseNA() {
        LogUtils.i(TAG, "responseResult : LOGIC_ACTION_RESPONSE_RESULT_NA");
        EventBus.getDefault().post(new LogicActionResult(LOGIC_ACTION_RESPONSE_RESULT_NA));
    }

    public static void responseOK() {
        LogUtils.i(TAG, "responseResult : LOGIC_ACTION_RESPONSE_RESULT_OK");
        EventBus.getDefault().post(new LogicActionResult(LOGIC_ACTION_RESPONSE_RESULT_OK));
    }

    public static void responseNoResult() {
        LogUtils.i(TAG, "responseResult : LOGIC_ACTION_RESPONSE_EXEC_NO_RESULT");
        EventBus.getDefault().post(new LogicActionResult(LOGIC_ACTION_RESPONSE_EXEC_NO_RESULT));
    }

    public static void responseResult(boolean isDiagnosis, String result) {
        if (isDiagnosis) {
            responseResult(result);
        }
    }

    public static void responseResult(boolean isDiagnosis, int[] result) {
        if (isDiagnosis) {
            responseResult(result);
        }
    }

    public static void responseNG(boolean isDiagnosis) {
        if (isDiagnosis) {
            responseNG();
        }
    }

    public static void responseNA(boolean isDiagnosis) {
        if (isDiagnosis) {
            responseNA();
        }
    }

    public static void responseOK(boolean isDiagnosis) {
        if (isDiagnosis) {
            responseOK();
        }
    }

    public static void responseNoResult(boolean isDiagnosis) {
        if (isDiagnosis) {
            responseNoResult();
        }
    }
}
