package com.xiaopeng.commonfunc.bean.event.signal;
/* loaded from: classes4.dex */
public class NeighboringEvent {
    private final String neighboringCellInfo;

    public NeighboringEvent(String neighboringCellInfo) {
        this.neighboringCellInfo = neighboringCellInfo;
    }

    public String getNeighboringCellInfo() {
        return this.neighboringCellInfo;
    }
}
