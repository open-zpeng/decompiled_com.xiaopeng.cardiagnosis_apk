package com.xiaopeng.commonfunc.bean.car;

import java.util.ArrayList;
import java.util.List;
/* loaded from: classes4.dex */
public class TboxGsmBand {
    private int GSM_1800;
    private int GSM_900;

    public int getGSM_900() {
        return this.GSM_900;
    }

    public int getGSM_1800() {
        return this.GSM_1800;
    }

    public String toString() {
        return "TboxGsmBand{GSM_900=" + this.GSM_900 + ", GSM_1800=" + this.GSM_1800 + '}';
    }

    public List<ModemBandEntity> toBandList() {
        List<ModemBandEntity> mItems = new ArrayList<>();
        mItems.add(new ModemBandEntity("GSM_900", this.GSM_900));
        mItems.add(new ModemBandEntity("GSM_1800", this.GSM_1800));
        return mItems;
    }

    public void setBandValue(String key, int value) {
        if ("GSM_900".equals(key)) {
            this.GSM_900 = value;
        } else if ("GSM_1800".equals(key)) {
            this.GSM_1800 = value;
        }
    }
}
