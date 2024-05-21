package com.xiaopeng.commonfunc.bean.car;

import java.util.ArrayList;
import java.util.List;
/* loaded from: classes4.dex */
public class TboxUmtsBand {
    private int TDSCDMA_BAND_A;
    private int TDSCDMA_BAND_F;
    private int WCDMA_2100;
    private int WCDMA_900;

    public int getTDSCDMA_BAND_A() {
        return this.TDSCDMA_BAND_A;
    }

    public int getTDSCDMA_BAND_F() {
        return this.TDSCDMA_BAND_F;
    }

    public int getWCDMA_2100() {
        return this.WCDMA_2100;
    }

    public int getWCDMA_900() {
        return this.WCDMA_900;
    }

    public String toString() {
        return "TboxUmtsBand{TDSCDMA_BAND_A=" + this.TDSCDMA_BAND_A + ", TDSCDMA_BAND_F=" + this.TDSCDMA_BAND_F + ", WCDMA_2100=" + this.WCDMA_2100 + ", WCDMA_900=" + this.WCDMA_900 + '}';
    }

    public List<ModemBandEntity> toBandList() {
        List<ModemBandEntity> mItems = new ArrayList<>();
        mItems.add(new ModemBandEntity("TDSCDMA_BAND_A", this.TDSCDMA_BAND_A));
        mItems.add(new ModemBandEntity("TDSCDMA_BAND_F", this.TDSCDMA_BAND_F));
        mItems.add(new ModemBandEntity("WCDMA_2100", this.WCDMA_2100));
        mItems.add(new ModemBandEntity("WCDMA_900", this.WCDMA_900));
        return mItems;
    }

    public void setBandValue(String key, int value) {
        if ("TDSCDMA_BAND_A".equals(key)) {
            this.TDSCDMA_BAND_A = value;
        } else if ("TDSCDMA_BAND_F".equals(key)) {
            this.TDSCDMA_BAND_F = value;
        } else if ("WCDMA_2100".equals(key)) {
            this.WCDMA_2100 = value;
        } else if ("WCDMA_900".equals(key)) {
            this.WCDMA_900 = value;
        }
    }
}
