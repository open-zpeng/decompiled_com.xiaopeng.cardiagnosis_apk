package com.xiaopeng.commonfunc.bean.car;
/* loaded from: classes4.dex */
public class ModemBandEntity {
    private String mBandName;
    private int mIschecked;

    public ModemBandEntity(String bandName, int ischecked) {
        this.mBandName = bandName;
        this.mIschecked = ischecked;
    }

    public String getBandName() {
        return this.mBandName;
    }

    public void setBandName(String mBandName) {
        this.mBandName = mBandName;
    }

    public boolean ischecked() {
        return this.mIschecked == 1;
    }

    public void setChecked(boolean mIschecked) {
        this.mIschecked = mIschecked ? 1 : 0;
    }
}
