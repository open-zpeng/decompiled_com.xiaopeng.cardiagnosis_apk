package com.xiaopeng.commonfunc.bean.car;
/* loaded from: classes4.dex */
public class AndroidDugReq {
    public int dugMcuPrintReq;
    public int dugMcuPrintStateReq;
    public int dugSt;
    public int dugUpReq;

    public int[] packToIntArray() {
        int[] data = {this.dugUpReq, this.dugMcuPrintReq, this.dugMcuPrintStateReq, this.dugSt};
        return data;
    }

    public String toString() {
        String str = "dugUpReq=" + this.dugUpReq + "\n dugMcuPrintReq=" + this.dugMcuPrintReq + "\n dugMcuPrintStateReq=" + this.dugMcuPrintStateReq + "\n dugSt=" + this.dugSt;
        return str;
    }
}
