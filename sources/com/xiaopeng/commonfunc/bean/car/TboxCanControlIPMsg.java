package com.xiaopeng.commonfunc.bean.car;
/* loaded from: classes4.dex */
public class TboxCanControlIPMsg {
    private int Key;
    private IPNPort Value;

    public TboxCanControlIPMsg() {
    }

    public TboxCanControlIPMsg(int key, IPNPort value) {
        this.Key = key;
        this.Value = value;
    }

    public int getKey() {
        return this.Key;
    }

    public IPNPort getValue() {
        return this.Value;
    }

    public String toString() {
        return "TboxCanControlIPMsg{Key=" + this.Key + ", Value=" + this.Value + '}';
    }
}
