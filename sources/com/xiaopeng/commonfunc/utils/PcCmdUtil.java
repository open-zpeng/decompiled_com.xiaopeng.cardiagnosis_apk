package com.xiaopeng.commonfunc.utils;

import com.xiaopeng.commonfunc.Constant;
/* loaded from: classes4.dex */
public class PcCmdUtil {
    public static final String CMD_PREFIX = "XPENG";
    public static final String MSG_NOT_PARSE = "NOT PARSE CAN\r\n";
    public static final String MSG_SOCKET_CONNECTED = "XPENG TOOLS CONNECTION COMPLETED\r\n";
    public static final String RESPONSE_CMD_NA = "XPENG+CMD Error:NA\r\n";
    public static final String arg0 = "0";
    public static final String arg1 = "1";
    public static final String arg2 = "2";

    public static String responseString(String cmd, String arg, String input) {
        String result = "XPENG+" + cmd + Constant.COLON_STRING + arg + Constant.SEPARATOR_STRING + input + ",OK\r\n";
        return result;
    }

    public static String responseNG(String cmd, String arg) {
        String result = "XPENG+" + cmd + Constant.COLON_STRING + arg + ",NG\r\n";
        return result;
    }

    public static String responseOK(String cmd, String arg) {
        String result = "XPENG+" + cmd + Constant.COLON_STRING + arg + ",OK\r\n";
        return result;
    }

    public static String responseNA(String cmd, String arg) {
        String result = "XPENG+" + cmd + Constant.COLON_STRING + arg + ",NA\r\n";
        return result;
    }
}
