package com.xiaopeng.logictree;

import com.google.gson.annotations.SerializedName;
/* loaded from: classes5.dex */
public class LogicTreeInfo {
    @SerializedName("logicTreePath")
    private String mLogicTreePath;
    @SerializedName("name")
    private String mName;

    public LogicTreeInfo(String mName, String mLogicTreePath) {
        this.mName = mName;
        this.mLogicTreePath = mLogicTreePath;
    }

    public String getName() {
        return this.mName;
    }

    public String getLogicTreePath() {
        return this.mLogicTreePath;
    }

    public String toString() {
        return "LogicTreeInfo{mName='" + this.mName + "', mLogicTreePath='" + this.mLogicTreePath + "'}";
    }
}
