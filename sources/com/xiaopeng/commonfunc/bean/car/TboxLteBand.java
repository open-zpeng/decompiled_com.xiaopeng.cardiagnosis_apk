package com.xiaopeng.commonfunc.bean.car;

import java.util.ArrayList;
import java.util.List;
/* loaded from: classes4.dex */
public class TboxLteBand {
    private int Band1;
    private int Band3;
    private int Band34;
    private int Band38;
    private int Band39;
    private int Band40;
    private int Band41;
    private int Band5;
    private int Band8;

    public int getBand1() {
        return this.Band1;
    }

    public int getBand3() {
        return this.Band3;
    }

    public int getBand5() {
        return this.Band5;
    }

    public int getBand8() {
        return this.Band8;
    }

    public int getBand34() {
        return this.Band34;
    }

    public int getBand38() {
        return this.Band38;
    }

    public int getBand39() {
        return this.Band39;
    }

    public int getBand40() {
        return this.Band40;
    }

    public int getBand41() {
        return this.Band41;
    }

    public String toString() {
        return "TboxLteBand{Band1=" + this.Band1 + ", Band3=" + this.Band3 + ", Band5=" + this.Band5 + ", Band8=" + this.Band8 + ", Band34=" + this.Band34 + ", Band38=" + this.Band38 + ", Band39=" + this.Band39 + ", Band40=" + this.Band40 + ", Band41=" + this.Band41 + '}';
    }

    public List<ModemBandEntity> toBandList() {
        List<ModemBandEntity> mItems = new ArrayList<>();
        mItems.add(new ModemBandEntity("Band1", this.Band1));
        mItems.add(new ModemBandEntity("Band3", this.Band3));
        mItems.add(new ModemBandEntity("Band5", this.Band5));
        mItems.add(new ModemBandEntity("Band8", this.Band8));
        mItems.add(new ModemBandEntity("Band34", this.Band8));
        mItems.add(new ModemBandEntity("Band38", this.Band8));
        mItems.add(new ModemBandEntity("Band39", this.Band8));
        mItems.add(new ModemBandEntity("Band40", this.Band8));
        mItems.add(new ModemBandEntity("Band41", this.Band8));
        return mItems;
    }

    public void setBandValue(String key, int value) {
        if ("Band1".equals(key)) {
            this.Band1 = value;
        } else if ("Band3".equals(key)) {
            this.Band3 = value;
        } else if ("Band5".equals(key)) {
            this.Band5 = value;
        } else if ("Band8".equals(key)) {
            this.Band8 = value;
        } else if ("Band34".equals(key)) {
            this.Band34 = value;
        } else if ("Band38".equals(key)) {
            this.Band38 = value;
        } else if ("Band39".equals(key)) {
            this.Band39 = value;
        } else if ("Band40".equals(key)) {
            this.Band40 = value;
        } else if ("Band41".equals(key)) {
            this.Band41 = value;
        }
    }
}
