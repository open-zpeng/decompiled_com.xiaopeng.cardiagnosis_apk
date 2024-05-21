package com.xiaopeng.commonfunc.bean.factorytest;

import com.google.gson.annotations.SerializedName;
/* loaded from: classes4.dex */
public class HereVmapInfo {
    @SerializedName("Content license level")
    private String mContentLicense;
    @SerializedName("Jira")
    private String mJira;
    @SerializedName("MapSupplier")
    private String mMapSupplier;
    @SerializedName("Map Version")
    private String mMapVer;
    @SerializedName("Part Number")
    private String mPartNumber;
    @SerializedName("Region")
    private String mRegion;
    @SerializedName("Transaction tag")
    private String mTransTag;

    public HereVmapInfo(String mRegion, String mTransTag, String mMapSupplier, String mMapVer, String mContentLicense, String mPartNumber, String mJira) {
        this.mRegion = mRegion;
        this.mTransTag = mTransTag;
        this.mMapSupplier = mMapSupplier;
        this.mMapVer = mMapVer;
        this.mContentLicense = mContentLicense;
        this.mPartNumber = mPartNumber;
        this.mJira = mJira;
    }

    public String getRegion() {
        return this.mRegion;
    }

    public void setRegion(String mRegion) {
        this.mRegion = mRegion;
    }

    public String getTransTag() {
        return this.mTransTag;
    }

    public void setTransTag(String mTransTag) {
        this.mTransTag = mTransTag;
    }

    public String getMapSupplier() {
        return this.mMapSupplier;
    }

    public void setMapSupplier(String mMapSupplier) {
        this.mMapSupplier = mMapSupplier;
    }

    public String getMapVer() {
        return this.mMapVer;
    }

    public void setMapVer(String mMapVer) {
        this.mMapVer = mMapVer;
    }

    public String getContentLicense() {
        return this.mContentLicense;
    }

    public void setContentLicense(String mContentLicense) {
        this.mContentLicense = mContentLicense;
    }

    public String getPartNumber() {
        return this.mPartNumber;
    }

    public void setPartNumber(String mPartNumber) {
        this.mPartNumber = mPartNumber;
    }

    public String getJira() {
        return this.mJira;
    }

    public void setJira(String mJira) {
        this.mJira = mJira;
    }

    public String toString() {
        return "HereVmapInfo{mRegion='" + this.mRegion + "', mTransTag='" + this.mTransTag + "', mMapSupplier='" + this.mMapSupplier + "', mMapVer='" + this.mMapVer + "', mContentLicense='" + this.mContentLicense + "', mPartNumber='" + this.mPartNumber + "', mJira='" + this.mJira + "'}";
    }
}
