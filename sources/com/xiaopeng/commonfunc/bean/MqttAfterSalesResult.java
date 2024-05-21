package com.xiaopeng.commonfunc.bean;
/* loaded from: classes4.dex */
public class MqttAfterSalesResult {
    public static final int OPERATION_FAIL = 2;
    public static final int OPERATION_FAIL_UPGRADE_LOGIC_TREE = 10;
    public static final int OPERATION_FAIL_UPLOAD_TO_OSS = 8;
    public static final int OPERATION_FAIL_VIN_MISMATCH = 9;
    public static final int OPERATION_FIND_CMD_TYPE_FAIL = 7;
    public static final int OPERATION_LAST_DIAGNOSIS_NO_FINISH = 13;
    public static final int OPERATION_LOGICTREE_INIT_FAIL = 14;
    public static final int OPERATION_LOGIC_MSGID_MISMATCH = 11;
    public static final int OPERATION_NEEDAUTHMODE = 4;
    public static final int OPERATION_NOTUNDERPLEVEL = 5;
    public static final int OPERATION_NO_SUCH_LOGIC_TREE = 12;
    public static final int OPERATION_SUCCESS = 1;
    public static final int OPERATION_TIMEOUT = 6;
    public static final int OPERATION_UNSUPPORTED = 3;
    private int cmd_result;
    private String cmd_result_file;
    private String cmd_result_info;
    private String cmd_result_string;

    public MqttAfterSalesResult(int cmd_result, String cmd_result_file, String cmd_result_string, String cmd_result_info) {
        this.cmd_result = cmd_result;
        this.cmd_result_file = cmd_result_file;
        this.cmd_result_string = cmd_result_string;
        this.cmd_result_info = cmd_result_info;
    }

    public int getCmd_result() {
        return this.cmd_result;
    }

    public void setCmd_result(int cmd_result) {
        this.cmd_result = cmd_result;
    }

    public String getCmd_result_file() {
        return this.cmd_result_file;
    }

    public void setCmd_result_file(String cmd_result_file) {
        this.cmd_result_file = cmd_result_file;
    }

    public String getCmd_result_string() {
        return this.cmd_result_string;
    }

    public void setCmd_result_string(String cmd_result_string) {
        this.cmd_result_string = cmd_result_string;
    }

    public String getCmd_result_info() {
        return this.cmd_result_info;
    }

    public void setCmd_result_info(String cmd_result_info) {
        this.cmd_result_info = cmd_result_info;
    }

    public String toString() {
        return "MqttAfterSalesResult{cmd_result=" + this.cmd_result + ", cmd_result_file='" + this.cmd_result_file + "', cmd_result_string='" + this.cmd_result_string + "', cmd_result_info='" + this.cmd_result_info + "'}";
    }
}
