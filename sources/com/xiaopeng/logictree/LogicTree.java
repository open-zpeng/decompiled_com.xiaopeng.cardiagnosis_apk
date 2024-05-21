package com.xiaopeng.logictree;

import com.google.gson.annotations.SerializedName;
import com.xiaopeng.commonfunc.Constant;
import java.util.Arrays;
/* loaded from: classes5.dex */
public class LogicTree {
    @SerializedName(Constant.ACTION)
    private String mAction;
    @SerializedName("defaultAction")
    private LogicTree mDefaultAction;
    @SerializedName("nextActions")
    private LogicTree[] mNextAction;
    @SerializedName("noResultAction")
    private LogicTree mNoResultAction;
    @SerializedName("param")
    private String mParam;
    @SerializedName("parseMethod")
    private int mParseMethod;
    @SerializedName("results")
    private String[] mResult;

    public LogicTree(String mAction, String mParam, int mParseMethod, String[] mResult, LogicTree[] mNextAction, LogicTree mDefaultAction, LogicTree mNoResultAction) {
        this.mAction = mAction;
        this.mParam = mParam;
        this.mParseMethod = mParseMethod;
        this.mResult = mResult;
        this.mNextAction = mNextAction;
        this.mDefaultAction = mDefaultAction;
        this.mNoResultAction = mNoResultAction;
    }

    public String getAction() {
        return this.mAction;
    }

    public String getParam() {
        return this.mParam;
    }

    public String[] getResult() {
        return this.mResult;
    }

    public LogicTree getNextAction(int index) {
        return this.mNextAction[index];
    }

    public int getActionSize() {
        return this.mNextAction.length;
    }

    public LogicTree getDefaultAction() {
        return this.mDefaultAction;
    }

    public int getParseMethod() {
        return this.mParseMethod;
    }

    public LogicTree getNoResultAction() {
        return this.mNoResultAction;
    }

    public String toString() {
        return "LogicTree{mAction='" + this.mAction + "', mParam='" + this.mParam + "', mParseMethod=" + this.mParseMethod + ", mResult=" + Arrays.toString(this.mResult) + ", mNextAction=" + Arrays.toString(this.mNextAction) + ", mDefaultAction=" + this.mDefaultAction + ", mNoResultAction=" + this.mNoResultAction + '}';
    }
}
