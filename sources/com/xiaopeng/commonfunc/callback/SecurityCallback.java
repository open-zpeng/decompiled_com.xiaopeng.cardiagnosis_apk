package com.xiaopeng.commonfunc.callback;
/* loaded from: classes4.dex */
public interface SecurityCallback {
    public static final int KEY_TYPE_CHECK_CDU_KEY = 1;
    public static final int KEY_TYPE_CHECK_INDIV = 0;
    public static final int KEY_TYPE_CHECK_PSO_KEY = 2;
    public static final int KEY_TYPE_CHECK_TBOX_KEY = 4;
    public static final int KEY_TYPE_CHECK_V18_CDU_KEY = 5;
    public static final int KEY_TYPE_CHECK_WIFI_KEY = 3;
    public static final int KEY_TYPE_GET_INDIV = 6;
    public static final int KEY_TYPE_UNKNOWN = -1;
    public static final int RESULT_FAIL = 2;
    public static final int RESULT_PASS = 1;
    public static final int RESULT_UNKNOWN = 3;

    void onReceiveResult(int i, int i2);
}
