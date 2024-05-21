package com.xiaopeng.commonfunc.bean.car;

import com.xiaopeng.commonfunc.Constant;
/* loaded from: classes4.dex */
public class TboxApn {
    public int apn1;
    public int apn2;
    public int apn3;
    public int rssi;
    public String imsi = Constant.UNKNOWN_STRING;
    public String imei = Constant.UNKNOWN_STRING;

    public String getImsi() {
        return this.imsi;
    }

    public int getRssi() {
        return this.rssi;
    }

    public int getApn1() {
        return this.apn1;
    }

    public int getApn2() {
        return this.apn2;
    }

    public int getApn3() {
        return this.apn3;
    }

    public String getImei() {
        return this.imei;
    }

    public String toString() {
        return "rssi : " + this.rssi + Constant.SENTENCE_SEPERATOR_FOUR_RETURN + "apn1 : " + this.apn1 + Constant.SENTENCE_SEPERATOR_FOUR_RETURN + "apn2 : " + this.apn2 + Constant.SENTENCE_SEPERATOR_FOUR_RETURN + "apn3 : " + this.apn3;
    }
}
