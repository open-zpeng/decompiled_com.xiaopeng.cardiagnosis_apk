package com.xiaopeng.libconfig.remotecontrol;
/* loaded from: classes5.dex */
public class CommandItem {
    private int cmd_type;
    private float cmd_value;
    private String fileUrl;

    public int getCmd_type() {
        return this.cmd_type;
    }

    public void setCmd_type(int cmd_type) {
        this.cmd_type = cmd_type;
    }

    public float getCmd_value() {
        return this.cmd_value;
    }

    public void setCmd_value(float cmd_value) {
        this.cmd_value = cmd_value;
    }

    public String getFileUrl() {
        return this.fileUrl;
    }

    public void setFileUrl(String fileUrl) {
        this.fileUrl = fileUrl;
    }

    public String toString() {
        return "CommandItem{cmd_type=" + this.cmd_type + ", cmd_value=" + this.cmd_value + ", fileUrl=" + this.fileUrl + '}';
    }
}
