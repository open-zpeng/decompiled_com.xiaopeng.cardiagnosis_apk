package com.xiaopeng.commonfunc.bean.http;

import com.xiaopeng.commonfunc.Constant;
/* loaded from: classes4.dex */
public class SimStatus {
    private String mActivate;
    private String mRestFlow;
    private String mTotalFlow;

    public SimStatus() {
        resetData();
    }

    public void resetData() {
        this.mActivate = Constant.LOADING_STRING;
        this.mTotalFlow = Constant.LOADING_STRING;
        this.mRestFlow = Constant.LOADING_STRING;
    }

    public String getActivate() {
        return this.mActivate;
    }

    public void setActivate(String mActivate) {
        this.mActivate = mActivate;
    }

    public String getTotalFlow() {
        return this.mTotalFlow;
    }

    public void setTotalFlow(String mTotalFlow) {
        this.mTotalFlow = mTotalFlow;
    }

    public String getRestFlow() {
        return this.mRestFlow;
    }

    public void setRestFlow(String mRestFlow) {
        this.mRestFlow = mRestFlow;
    }
}
