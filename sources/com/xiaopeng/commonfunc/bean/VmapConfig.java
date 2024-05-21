package com.xiaopeng.commonfunc.bean;
/* loaded from: classes4.dex */
public class VmapConfig {
    private String region;
    private String vendor;
    private int version;

    public VmapConfig(int version, String vendor, String region) {
        this.version = version;
        this.vendor = vendor;
        this.region = region;
    }

    public int getVersion() {
        return this.version;
    }

    public void setVersion(int version) {
        this.version = version;
    }

    public String getVendor() {
        return this.vendor;
    }

    public void setVendor(String vendor) {
        this.vendor = vendor;
    }

    public String getRegion() {
        return this.region;
    }

    public void setRegion(String region) {
        this.region = region;
    }

    public String toString() {
        return "VmapConfig{version=" + this.version + ", vendor='" + this.vendor + "', region='" + this.region + "'}";
    }
}
