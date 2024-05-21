package com.xiaopeng.commonfunc.bean.car;
/* loaded from: classes4.dex */
public class AndroidTest {
    public int batVolDet;
    public int canTest;
    public int eepromTest;
    public int fmAntTest;
    public int gnssAntTest;
    public int gnssComDet;
    public int micTest;
    public int tempDet;

    public int[] packToIntArray() {
        int[] data = new int[4];
        data[0] = this.eepromTest & 255;
        data[0] = data[0] | ((this.canTest & 255) << 8);
        data[0] = data[0] | ((this.micTest & 255) << 16);
        data[0] = data[0] | ((this.gnssAntTest & 255) << 24);
        data[1] = this.fmAntTest & 255;
        data[1] = data[1] | ((this.tempDet & 255) << 8);
        data[1] = data[1] | ((this.batVolDet & 255) << 16);
        data[1] = data[1] | ((this.gnssComDet & 255) << 24);
        return data;
    }

    public String toString() {
        String str = "eepromTest=" + this.eepromTest + "\n canTest=" + this.canTest + "\n micTest=" + this.micTest + "\n gnssAntTest=" + this.gnssAntTest + "\n fmAntTest=" + this.fmAntTest + "\n tempDet=" + this.tempDet + "\n batVolDet=" + this.batVolDet + "\n gnssComDet=" + this.gnssComDet;
        return str;
    }
}
