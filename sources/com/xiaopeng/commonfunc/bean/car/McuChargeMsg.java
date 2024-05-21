package com.xiaopeng.commonfunc.bean.car;
/* loaded from: classes4.dex */
public class McuChargeMsg {
    public int ChargeMode;
    public int ChargeTime1Day;
    public int ChargeTime1Hour;
    public int ChargeTime1Min;
    public int ChargeTime1Mon;
    public int ChargeTime1Week;
    public int ChargeTime2Day;
    public int ChargeTime2Hour;
    public int ChargeTime2Min;
    public int ChargeTime2Mon;
    public int ChargeTime2Week;
    public int StopChargeSoc;
    public int TimeMode;

    public McuChargeMsg(int[] data) {
        this.ChargeMode = data[0] & 255;
        this.StopChargeSoc = (data[0] >> 8) & 255;
        this.ChargeTime1Week = (data[0] >> 16) & 255;
        this.ChargeTime1Mon = (data[0] >> 24) & 255;
        this.ChargeTime1Day = data[1] & 255;
        this.ChargeTime1Hour = (data[1] >> 8) & 255;
        this.ChargeTime1Min = (data[1] >> 16) & 255;
        this.ChargeTime2Week = (data[1] >> 24) & 255;
        this.ChargeTime2Mon = data[2] & 255;
        this.ChargeTime2Day = (data[2] >> 8) & 255;
        this.ChargeTime2Hour = (data[2] >> 16) & 255;
        this.ChargeTime2Min = (data[2] >> 24) & 255;
        this.TimeMode = data[3] & 255;
    }

    public String toString() {
        StringBuffer sb = new StringBuffer("McuChargeMsg{");
        sb.append("ChargeMode=");
        sb.append(this.ChargeMode);
        sb.append(", StopChargeSoc=");
        sb.append(this.StopChargeSoc);
        sb.append(", ChargeTime1Week=");
        sb.append(this.ChargeTime1Week);
        sb.append(", ChargeTime1Mon=");
        sb.append(this.ChargeTime1Mon);
        sb.append(", ChargeTime1Day=");
        sb.append(this.ChargeTime1Day);
        sb.append(", ChargeTime1Hour=");
        sb.append(this.ChargeTime1Hour);
        sb.append(", ChargeTime1Min=");
        sb.append(this.ChargeTime1Min);
        sb.append(", ChargeTime2Week=");
        sb.append(this.ChargeTime2Week);
        sb.append(", ChargeTime2Mon=");
        sb.append(this.ChargeTime2Mon);
        sb.append(", ChargeTime2Day=");
        sb.append(this.ChargeTime2Day);
        sb.append(", ChargeTime2Hour=");
        sb.append(this.ChargeTime2Hour);
        sb.append(", ChargeTime2Min=");
        sb.append(this.ChargeTime2Min);
        sb.append(", TimeMode=");
        sb.append(this.TimeMode);
        sb.append('}');
        return sb.toString();
    }
}
