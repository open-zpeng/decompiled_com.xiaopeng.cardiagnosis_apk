package com.xiaopeng.commonfunc.bean.car;
/* loaded from: classes4.dex */
public class McuDebug3Msg {
    public int SWEn5v25;
    public int ant12VEn;
    public int awakeFailTime;
    public int batOn4G;
    public int blPwr;
    public int ctpPwr;
    public int dvrUsbPwr;
    public int fmSt;
    public int frUsbPwr;
    public int gnassSt;
    public int gnssAntPwr;
    public int gnssPwr;
    public int mUsbPwr;
    public int mainPwr;
    public int mcu2CoreBluEn;
    public int mic8vEn;
    public int micSt;
    public int pmOverVolTime;
    public int pmSynFailTime;
    public int pmUnderVolTime;
    public int ps1v8LREn;
    public int ps3v3SwEn;
    public int ps5vSwEn;
    public int psEn;
    public int sleepFailTime;

    public McuDebug3Msg(byte[] data) {
        this.psEn = data[0] & 255;
        this.batOn4G = data[1] & 255;
        this.blPwr = data[2] & 255;
        this.ant12VEn = data[3] & 255;
        this.mic8vEn = data[4] & 255;
        this.SWEn5v25 = data[5] & 255;
        this.ps3v3SwEn = data[6] & 255;
        this.ps1v8LREn = data[7] & 255;
        this.mUsbPwr = data[8] & 255;
        this.frUsbPwr = data[9] & 255;
        this.dvrUsbPwr = data[10] & 255;
        this.ctpPwr = data[11] & 255;
        this.gnssPwr = data[12] & 255;
        this.gnssAntPwr = data[13] & 255;
        this.mainPwr = data[14] & 255;
        this.mcu2CoreBluEn = data[15] & 255;
        this.ps5vSwEn = data[16] & 255;
        this.fmSt = data[17] & 255;
        this.gnassSt = data[18] & 255;
        this.micSt = data[19] & 255;
        this.sleepFailTime = data[20] & 255;
        this.awakeFailTime = data[21] & 255;
        this.pmSynFailTime = data[22] & 255;
        this.pmOverVolTime = data[23] & 255;
        this.pmUnderVolTime = data[24] & 255;
    }
}
