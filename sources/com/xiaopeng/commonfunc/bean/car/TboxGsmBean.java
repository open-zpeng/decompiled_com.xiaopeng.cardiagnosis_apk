package com.xiaopeng.commonfunc.bean.car;

import com.xiaopeng.commonfunc.Constant;
/* loaded from: classes4.dex */
public class TboxGsmBean {
    public String ARFCN;
    public String CID;
    public String Frequency;
    public String LAC;
    public String RxLevel;
    public String RxQual;
    public String Type = "GSM";

    public String getType() {
        return this.Type;
    }

    public String getFrequency() {
        return this.Frequency;
    }

    public String getARFCN() {
        return this.ARFCN;
    }

    public String getLAC() {
        return this.LAC;
    }

    public String getCID() {
        return this.CID;
    }

    public String getRxLevel() {
        return this.RxLevel;
    }

    public String getRxQual() {
        return this.RxQual;
    }

    public String toString() {
        return "Type : " + this.Type + Constant.SENTENCE_SEPERATOR_FOUR_RETURN + "Frequency : " + this.Frequency + Constant.SENTENCE_SEPERATOR_FOUR_RETURN + "ARFCN : " + this.ARFCN + Constant.SENTENCE_SEPERATOR_FOUR_RETURN + "LAC : " + this.LAC + Constant.SENTENCE_SEPERATOR_FOUR_RETURN + "CID : " + this.CID + Constant.SENTENCE_SEPERATOR_FOUR_RETURN + "RxLevel : " + this.RxLevel + Constant.SENTENCE_SEPERATOR_FOUR_RETURN + "RxQual : " + this.RxQual;
    }
}
