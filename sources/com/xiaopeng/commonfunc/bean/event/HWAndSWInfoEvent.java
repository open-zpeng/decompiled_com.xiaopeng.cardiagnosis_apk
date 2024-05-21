package com.xiaopeng.commonfunc.bean.event;
/* loaded from: classes4.dex */
public class HWAndSWInfoEvent {
    public static final int GET_PART_CODE_ERROR = 2;
    public static final int REQUEST_PARAM_ERROR = 1;
    private final int state;

    public HWAndSWInfoEvent(int state) {
        this.state = state;
    }

    public int getState() {
        return this.state;
    }
}
