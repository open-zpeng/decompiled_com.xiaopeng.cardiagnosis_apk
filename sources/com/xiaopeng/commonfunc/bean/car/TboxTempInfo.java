package com.xiaopeng.commonfunc.bean.car;
/* loaded from: classes4.dex */
public class TboxTempInfo {
    private final int bbat;
    private final int tbox;
    private final int tmcu;

    public TboxTempInfo(int tbox, int tmcu, int bbat) {
        this.tbox = tbox;
        this.tmcu = tmcu;
        this.bbat = bbat;
    }

    public TboxTempInfo() {
        this.tbox = -1;
        this.tmcu = -1;
        this.bbat = -1;
    }

    public int getTbox() {
        return this.tbox;
    }

    public int getTmcu() {
        return this.tmcu;
    }

    public int getBbat() {
        return this.bbat;
    }

    public float getFloatTbox() {
        return this.tbox / 10.0f;
    }

    public float getFloatTmcu() {
        return this.tmcu / 10.0f;
    }

    public float getFloatBbat() {
        return this.bbat / 10.0f;
    }

    public String toString() {
        return "TboxTempInfo{tbox=" + this.tbox + ", tmcu=" + this.tmcu + ", bbat=" + this.bbat + '}';
    }
}
