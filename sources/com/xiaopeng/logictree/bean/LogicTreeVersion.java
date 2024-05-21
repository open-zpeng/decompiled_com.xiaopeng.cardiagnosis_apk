package com.xiaopeng.logictree.bean;

import com.google.gson.annotations.SerializedName;
/* loaded from: classes5.dex */
public class LogicTreeVersion {
    @SerializedName("logicTreeUrl")
    private final String mUrl;
    @SerializedName("version")
    private final String mVersion;

    public LogicTreeVersion(String mUrl, String mVersion) {
        this.mUrl = mUrl;
        this.mVersion = mVersion;
    }

    public String getUrl() {
        return this.mUrl;
    }

    public String getVersion() {
        return this.mVersion;
    }
}
