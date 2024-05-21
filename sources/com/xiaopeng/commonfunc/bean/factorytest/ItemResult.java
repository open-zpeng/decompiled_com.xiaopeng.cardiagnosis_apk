package com.xiaopeng.commonfunc.bean.factorytest;
/* loaded from: classes4.dex */
public class ItemResult {
    private boolean mIsSucceed;
    private String mItem;
    private String mLog;

    public ItemResult(String item, boolean isSucceed, String log) {
        this.mItem = item;
        this.mIsSucceed = isSucceed;
        this.mLog = log;
    }

    public String getItem() {
        return this.mItem;
    }

    public void setItem(String item) {
        this.mItem = item;
    }

    public boolean isSucceed() {
        return this.mIsSucceed;
    }

    public void setSucceed(boolean succeed) {
        this.mIsSucceed = succeed;
    }

    public String getLog() {
        return this.mLog;
    }

    public void setLog(String log) {
        this.mLog = log;
    }
}
