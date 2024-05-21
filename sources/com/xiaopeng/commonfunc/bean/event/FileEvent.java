package com.xiaopeng.commonfunc.bean.event;
/* loaded from: classes4.dex */
public class FileEvent {
    public static final int DELETE_SUCCESS = 1;
    private int status;

    public FileEvent(int i) {
        this.status = i;
    }

    public int getStatus() {
        return this.status;
    }

    public void setStatus(int status) {
        this.status = status;
    }
}
