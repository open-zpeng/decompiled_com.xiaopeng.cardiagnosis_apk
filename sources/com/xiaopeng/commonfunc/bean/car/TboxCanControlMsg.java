package com.xiaopeng.commonfunc.bean.car;
/* loaded from: classes4.dex */
public class TboxCanControlMsg {
    private int Key;
    private String Value;

    public TboxCanControlMsg() {
    }

    public TboxCanControlMsg(int key, String value) {
        this.Key = key;
        this.Value = value;
    }

    public int getKey() {
        return this.Key;
    }

    public String getValue() {
        return this.Value;
    }

    public String toString() {
        return "TboxCanControlMsg{Key=" + this.Key + ", Value='" + this.Value + "'}";
    }
}
