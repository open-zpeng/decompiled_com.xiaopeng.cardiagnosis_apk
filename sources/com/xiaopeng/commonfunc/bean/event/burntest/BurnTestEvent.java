package com.xiaopeng.commonfunc.bean.event.burntest;
/* loaded from: classes4.dex */
public class BurnTestEvent {
    public int position;
    public boolean testPass;

    public BurnTestEvent(int position, boolean testPass) {
        this.position = position;
        this.testPass = testPass;
    }

    public String toString() {
        return "BurnTestEvent{position=" + this.position + ", testPass=" + this.testPass + '}';
    }
}
