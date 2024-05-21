package com.xiaopeng.commonfunc.bean.car;
/* loaded from: classes4.dex */
public class TboxUpdateReq {
    public String File;
    public int Key;

    public TboxUpdateReq(int key, String file) {
        this.Key = key;
        this.File = file;
    }

    public int getKey() {
        return this.Key;
    }

    public String getFile() {
        return this.File;
    }

    public String toString() {
        return "TboxUpdateReq{Key=" + this.Key + ", File='" + this.File + "'}";
    }
}
