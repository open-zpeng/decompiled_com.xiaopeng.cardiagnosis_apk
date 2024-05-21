package com.xiaopeng.commonfunc.bean.car;

import com.xiaopeng.commonfunc.Constant;
/* loaded from: classes4.dex */
public class TboxLteBean {
    public String Band;
    public String CID;
    public String EARFCN;
    public String PCI;
    public String RRCStatus;
    public String RSRP;
    public String RSRQ;
    public String SINR;
    public String TAC;
    public String Type = "LTE";

    public String getType() {
        return this.Type;
    }

    public String getRRCStatus() {
        return this.RRCStatus;
    }

    public String getBand() {
        return this.Band;
    }

    public String getEARFCN() {
        return this.EARFCN;
    }

    public String getPCI() {
        return this.PCI;
    }

    public String getTAC() {
        return this.TAC;
    }

    public String getCID() {
        return this.CID;
    }

    public String getRSRP() {
        return this.RSRP;
    }

    public String getRSRQ() {
        return this.RSRQ;
    }

    public String getSINR() {
        return this.SINR;
    }

    public String toString() {
        return "Type : " + this.Type + Constant.SENTENCE_SEPERATOR_FOUR_RETURN + "RRCStatus : " + this.RRCStatus + Constant.SENTENCE_SEPERATOR_FOUR_RETURN + "Band : " + this.Band + Constant.SENTENCE_SEPERATOR_FOUR_RETURN + "EARFCN : " + this.EARFCN + Constant.SENTENCE_SEPERATOR_FOUR_RETURN + "PCI : " + this.PCI + Constant.SENTENCE_SEPERATOR_FOUR_RETURN + "TAC : " + this.TAC + Constant.SENTENCE_SEPERATOR_FOUR_RETURN + "CID : " + this.CID + Constant.SENTENCE_SEPERATOR_FOUR_RETURN + "RSRP : " + this.RSRP + Constant.SENTENCE_SEPERATOR_FOUR_RETURN + "RSRQ : " + this.RSRQ + Constant.SENTENCE_SEPERATOR_FOUR_RETURN + "SINR : " + this.SINR;
    }
}
