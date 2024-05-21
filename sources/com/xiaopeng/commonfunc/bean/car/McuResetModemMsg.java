package com.xiaopeng.commonfunc.bean.car;
/* loaded from: classes4.dex */
public class McuResetModemMsg {
    public int mMcuMsgType;
    public int mMcuRstAck;
    public int mMcuRstLvl;

    public McuResetModemMsg(int[] data) {
        this.mMcuMsgType = data[0];
        this.mMcuRstLvl = data[1];
        this.mMcuRstAck = data[2];
    }

    public String toString() {
        String str = "mMcuMsgType=" + this.mMcuMsgType;
        return str;
    }
}
