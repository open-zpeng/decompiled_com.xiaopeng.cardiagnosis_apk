package com.xiaopeng.commonfunc.bean.car;
/* loaded from: classes4.dex */
public class TboxModemBand {
    private TboxGsmBand GSM;
    private TboxLteBand LTE;
    private TboxUmtsBand UMTS;

    public TboxModemBand() {
    }

    public TboxModemBand(TboxGsmBand GSM, TboxUmtsBand UMTS, TboxLteBand LTE) {
        this.GSM = GSM;
        this.UMTS = UMTS;
        this.LTE = LTE;
    }

    public TboxGsmBand getGSM() {
        return this.GSM;
    }

    public TboxUmtsBand getUMTS() {
        return this.UMTS;
    }

    public TboxLteBand getLTE() {
        return this.LTE;
    }
}
