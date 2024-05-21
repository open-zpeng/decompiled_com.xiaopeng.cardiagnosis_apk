package com.xiaopeng.commonfunc.bean.event.signal;
/* loaded from: classes4.dex */
public class FmEvent {
    private final String mFmFrequency;
    private final String mFmState;
    private final String mFmStrength;

    public FmEvent(String fmState, String fmFrequency, String fmStrength) {
        this.mFmState = fmState;
        this.mFmFrequency = fmFrequency;
        this.mFmStrength = fmStrength;
    }
}
