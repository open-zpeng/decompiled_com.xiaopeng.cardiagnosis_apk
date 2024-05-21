package com.xiaopeng.commonfunc.bean.car;
/* loaded from: classes4.dex */
public class McuOta1Msg {
    public int McuAckArdRstNotify;
    public int McuAckOtaTestAck;
    public int McuPacketSt;
    public int McuProCondStAck;

    public McuOta1Msg(int[] data) {
        this.McuProCondStAck = data[0];
        this.McuPacketSt = data[1];
        this.McuAckOtaTestAck = data[2];
        this.McuAckArdRstNotify = data[3];
    }

    public String toString() {
        String str = "McuProCondStAck=" + this.McuProCondStAck;
        return str;
    }
}
