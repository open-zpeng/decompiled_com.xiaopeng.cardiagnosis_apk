package com.xiaopeng.commonfunc.bean.car;
/* loaded from: classes4.dex */
public class McuReportSpeedPwm {
    public int PWM_FRQ_RESULT;
    public int SPEED_RESULT;

    public McuReportSpeedPwm(int[] data) {
        this.SPEED_RESULT = data[0];
        this.PWM_FRQ_RESULT = data[1];
    }

    public String toString() {
        String str = "SPEED_RESULT=" + this.SPEED_RESULT + "\n PWM_FRQ_RESULT=" + this.PWM_FRQ_RESULT;
        return str;
    }
}
