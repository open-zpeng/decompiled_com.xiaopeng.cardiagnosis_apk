package com.xiaopeng.logictree.bean;

import com.google.gson.annotations.SerializedName;
import java.util.Arrays;
/* loaded from: classes5.dex */
public class LogicResponseData {
    @SerializedName("link")
    private final String mLink;
    @SerializedName("msg")
    private final String mMsg;
    @SerializedName("msgId")
    private final String mMsgId;
    @SerializedName("option")
    private final String[] mOptions;

    public LogicResponseData(String mMsg, String mMsgId, String mLink, String[] mOptions) {
        this.mMsg = mMsg;
        this.mMsgId = mMsgId;
        this.mLink = mLink;
        this.mOptions = mOptions;
    }

    public String getMsg() {
        return this.mMsg;
    }

    public String getMsgId() {
        return this.mMsgId;
    }

    public String getLink() {
        return this.mLink;
    }

    public String[] getOptions() {
        return this.mOptions;
    }

    public String toString() {
        return "LogicResponseData{mMsg='" + this.mMsg + "', mMsgId='" + this.mMsgId + "', mLink='" + this.mLink + "', mOptions=" + Arrays.toString(this.mOptions) + '}';
    }
}
