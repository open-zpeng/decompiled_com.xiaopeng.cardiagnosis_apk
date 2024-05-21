package com.xiaopeng.commonfunc.bean.car;
/* loaded from: classes4.dex */
public class McuFaultInfoMsg {
    public int ApmStSynFailCount;
    public int BattRstCount;
    public int EepReadErrCount;
    public int EepWriteErrCount;
    public int HardErrRstCount;
    public int IapCount;
    public int IapFailCount;
    public int RtcErrCount;
    public int SoftRstCount;
    public int UartRxOverFlowCount;
    public int UartTxOverFlowCount;
    public int WdgRstCount;

    public McuFaultInfoMsg(int[] data) {
        this.SoftRstCount = data[0] & 255;
        this.BattRstCount = (data[0] >> 8) & 255;
        this.WdgRstCount = (data[0] >> 16) & 255;
        this.HardErrRstCount = (data[0] >> 24) & 255;
        this.RtcErrCount = data[1] & 255;
        this.EepReadErrCount = (data[1] >> 8) & 255;
        this.EepWriteErrCount = (data[1] >> 16) & 255;
        this.IapFailCount = (data[1] >> 24) & 255;
        this.IapCount = data[2] & 255;
        this.ApmStSynFailCount = (data[2] >> 8) & 255;
        this.UartRxOverFlowCount = (data[2] >> 16) & 255;
        this.UartTxOverFlowCount = (data[2] >> 24) & 255;
    }

    public String toString() {
        String str = "SoftRstCount=" + this.SoftRstCount;
        return str;
    }
}
