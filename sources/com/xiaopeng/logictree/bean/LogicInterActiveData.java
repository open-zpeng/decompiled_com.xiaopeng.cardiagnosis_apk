package com.xiaopeng.logictree.bean;

import com.google.gson.annotations.SerializedName;
/* loaded from: classes5.dex */
public class LogicInterActiveData {
    @SerializedName("msgId")
    private final String mMsgId;
    @SerializedName("option")
    private final String mOption;

    public LogicInterActiveData(String mMsgId, String mOption) {
        this.mMsgId = mMsgId;
        this.mOption = mOption;
    }

    public String getMsgId() {
        return this.mMsgId;
    }

    public String getOption() {
        return this.mOption;
    }
}
