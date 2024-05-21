package com.xiaopeng.commonfunc.bean.event;

import com.google.gson.annotations.SerializedName;
/* loaded from: classes4.dex */
public class MCUBurnBean {
    public static final int BURNING = 1;
    public static final int BURN_ERROR = 3;
    public static final int BURN_FINISH = 2;
    public static final int TYPE_MCU = 1;
    public static final int TYPE_SCU = 2;
    @SerializedName("process")
    private final int process;
    @SerializedName("state")
    private final int state;
    @SerializedName("type")
    private final int type;

    public MCUBurnBean(int state, int process, int type) {
        this.state = state;
        this.process = process;
        this.type = type;
    }

    public int getState() {
        return this.state;
    }

    public int getProcess() {
        return this.process;
    }

    public int getType() {
        return this.type;
    }

    public String toString() {
        String str = "state :" + this.state + "\n";
        return (str + "process :" + this.process + "\n") + "type :" + this.type + "\n";
    }
}
