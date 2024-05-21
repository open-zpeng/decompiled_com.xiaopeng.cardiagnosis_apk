package com.xiaopeng.commonfunc.bean.car;
/* loaded from: classes4.dex */
public class TboxUpdateResponse {
    public int Key;
    public int Value;

    public TboxUpdateResponse(int key, int value) {
        this.Key = key;
        this.Value = value;
    }

    public int getKey() {
        return this.Key;
    }

    public int getValue() {
        return this.Value;
    }

    public String toString() {
        return "TboxUpdateResponse{Key=" + this.Key + ", Value=" + this.Value + '}';
    }
}
