package com.xiaopeng.commonfunc.bean;

import com.google.gson.annotations.SerializedName;
import com.xiaopeng.commonfunc.Constant;
/* loaded from: classes4.dex */
public class ParamCheckMode {
    @SerializedName("download_path")
    private String mDownloadPath;
    @SerializedName("jobNum")
    private String mJobNum;
    @SerializedName(Constant.HTTP_KEY_VIN)
    private String mVin;

    public ParamCheckMode(String mDownloadPath, String mJobNum, String mVin) {
        this.mDownloadPath = mDownloadPath;
        this.mJobNum = mJobNum;
        this.mVin = mVin;
    }

    public String getDownloadPath() {
        return this.mDownloadPath;
    }

    public void setDownloadPath(String mDownloadPath) {
        this.mDownloadPath = mDownloadPath;
    }

    public String getJobNum() {
        return this.mJobNum;
    }

    public void setJobNum(String mJobNum) {
        this.mJobNum = mJobNum;
    }

    public String getVin() {
        return this.mVin;
    }

    public void setVin(String mVin) {
        this.mVin = mVin;
    }

    public String toString() {
        return "ParamCheckMode{mDownloadPath='" + this.mDownloadPath + "', mJobNum='" + this.mJobNum + "', mVin='" + this.mVin + "'}";
    }
}
