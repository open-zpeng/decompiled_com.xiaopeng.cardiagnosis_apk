package com.xiaopeng.commonfunc.bean.event;
/* loaded from: classes4.dex */
public class RebootPolicyEvent {
    private boolean flag;

    public RebootPolicyEvent(boolean flag) {
        this.flag = flag;
    }

    public boolean isFlag() {
        return this.flag;
    }

    public void setFlag(boolean flag) {
        this.flag = flag;
    }
}
