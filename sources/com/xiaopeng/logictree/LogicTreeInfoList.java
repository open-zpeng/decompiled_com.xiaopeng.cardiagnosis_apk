package com.xiaopeng.logictree;

import com.google.gson.annotations.SerializedName;
import java.util.Arrays;
/* loaded from: classes5.dex */
public class LogicTreeInfoList {
    @SerializedName("trees")
    private LogicTreeInfo[] mLogicTreeList;
    @SerializedName("project")
    private String mProject;
    @SerializedName("version")
    private String mVersion;

    public LogicTreeInfoList(String mProject, String mVersion, LogicTreeInfo[] mLogicTreeList) {
        this.mProject = mProject;
        this.mVersion = mVersion;
        this.mLogicTreeList = mLogicTreeList;
    }

    public String getProject() {
        return this.mProject;
    }

    public String getVersion() {
        return this.mVersion;
    }

    public LogicTreeInfo[] getLogicTreeList() {
        return this.mLogicTreeList;
    }

    public String toString() {
        return "LogicTreeInfoList{mProject='" + this.mProject + "', mVersion='" + this.mVersion + "', mLogicTreeList=" + Arrays.toString(this.mLogicTreeList) + '}';
    }
}
