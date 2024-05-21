package com.xiaopeng.commonfunc.bean.aftersales;
/* loaded from: classes4.dex */
public class RealtimeStatus {
    private final String[] mStatus;
    private String mTitle;

    public RealtimeStatus(String mTitle, String[] mStatus) {
        this.mTitle = mTitle;
        this.mStatus = mStatus;
    }

    public String getTitle() {
        return this.mTitle;
    }

    public void setTitle(String mTitle) {
        this.mTitle = mTitle;
    }

    public String[] getStatus() {
        return this.mStatus;
    }

    public void setStatus(int index, String value) {
        this.mStatus[index] = value;
    }
}
