package com.xiaopeng.commonfunc.bean.factorytest;
/* loaded from: classes4.dex */
public class ScanDevice {
    private String mAddress;
    private String mCod;
    private String mName;
    private int mRssi;

    public ScanDevice(String name, String address, String cod, int rssi) {
        this.mName = name;
        this.mAddress = address;
        this.mCod = cod;
        this.mRssi = rssi;
    }

    public int getRssi() {
        return this.mRssi;
    }

    public void setRssi(int rssi) {
        this.mRssi = rssi;
    }

    public String getName() {
        return this.mName;
    }

    public void setName(String name) {
        this.mName = name;
    }

    public String getAddress() {
        return this.mAddress;
    }

    public void setAddress(String address) {
        this.mAddress = address;
    }

    public String getCod() {
        return this.mCod;
    }

    public void setCod(String cod) {
        this.mCod = cod;
    }
}
