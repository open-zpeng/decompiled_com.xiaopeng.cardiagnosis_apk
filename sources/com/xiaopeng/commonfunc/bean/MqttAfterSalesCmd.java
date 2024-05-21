package com.xiaopeng.commonfunc.bean;
/* loaded from: classes4.dex */
public class MqttAfterSalesCmd {
    private String cmd_param;
    private String cmd_type;

    public String getCmd_type() {
        return this.cmd_type;
    }

    public void setCmd_type(String cmd_type) {
        this.cmd_type = cmd_type;
    }

    public String getCmd_param() {
        return this.cmd_param;
    }

    public void setCmd_param(String cmd_param) {
        this.cmd_param = cmd_param;
    }

    public String toString() {
        return "MqttAfterSalesCmd{cmd_type='" + this.cmd_type + "', cmd_param='" + this.cmd_param + "'}";
    }
}
