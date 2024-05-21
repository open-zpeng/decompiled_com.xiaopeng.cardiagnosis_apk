package com.xiaopeng.commonfunc.bean.event.signal;
/* loaded from: classes4.dex */
public class GpsNumSnrEvent {
    private final int count;
    private final String snrString;

    public GpsNumSnrEvent(int count, String snrString) {
        this.count = count;
        this.snrString = snrString;
    }

    public int getCount() {
        return this.count;
    }

    public String getSnrString() {
        return this.snrString;
    }
}
