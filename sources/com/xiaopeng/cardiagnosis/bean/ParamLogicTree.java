package com.xiaopeng.cardiagnosis.bean;

import com.google.gson.annotations.SerializedName;
/* loaded from: classes4.dex */
public class ParamLogicTree {
    @SerializedName("downloadPath")
    private String mDownloadPath;
    @SerializedName("issueEndTime")
    private long mEndTime;
    @SerializedName("issueName")
    private String mIssueName;
    @SerializedName("issueStartTime")
    private long mStartTime;
    @SerializedName("version")
    private String mVersion;

    public ParamLogicTree(String mIssueName, long mStartTime, long mEndTime, String mDownloadPath, String mVersion) {
        this.mIssueName = mIssueName;
        this.mStartTime = mStartTime;
        this.mEndTime = mEndTime;
        this.mDownloadPath = mDownloadPath;
        this.mVersion = mVersion;
    }

    public String getIssueName() {
        return this.mIssueName;
    }

    public void setIssueName(String mIssueName) {
        this.mIssueName = mIssueName;
    }

    public long getStartTime() {
        return this.mStartTime;
    }

    public void setStartTime(long mStartTime) {
        this.mStartTime = mStartTime;
    }

    public long getEndTime() {
        return this.mEndTime;
    }

    public void setEndTime(long mEndTime) {
        this.mEndTime = mEndTime;
    }

    public String getDownloadPath() {
        return this.mDownloadPath;
    }

    public void setDownloadPath(String mDownloadPath) {
        this.mDownloadPath = mDownloadPath;
    }

    public String getVersion() {
        return this.mVersion;
    }

    public void setVersion(String mVersion) {
        this.mVersion = mVersion;
    }

    public String toString() {
        return "ParamLogicTree{mIssueName='" + this.mIssueName + "', mStartTime=" + this.mStartTime + ", mEndTime=" + this.mEndTime + ", mDownloadPath='" + this.mDownloadPath + "', mVersion='" + this.mVersion + "'}";
    }
}
