package com.xiaopeng.commonfunc.bean.car;
/* loaded from: classes4.dex */
public class McuTempInfo {
    private float mcuTemp = -1.0f;
    private float battTemp = -1.0f;
    private float pcbTemp = -1.0f;

    public float getMcuTemp() {
        return this.mcuTemp;
    }

    public void setMcuTemp(float mcuTemp) {
        this.mcuTemp = mcuTemp;
    }

    public float getBattTemp() {
        return this.battTemp;
    }

    public void setBattTemp(float battTemp) {
        this.battTemp = battTemp;
    }

    public float getPcbTemp() {
        return this.pcbTemp;
    }

    public void setPcbTemp(float pcbTemp) {
        this.pcbTemp = pcbTemp;
    }

    public String toString() {
        return "McuTempInfo{mcuTemp=" + this.mcuTemp + ", battTemp=" + this.battTemp + ", pcbTemp=" + this.pcbTemp + '}';
    }
}
