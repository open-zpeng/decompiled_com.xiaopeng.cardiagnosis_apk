package com.xiaopeng.commonfunc.bean.event.signal;
/* loaded from: classes4.dex */
public class GpsLocationEvent {
    private final String locationTime;
    private final String mLocation;

    public GpsLocationEvent(String locationTime, String location) {
        this.locationTime = locationTime;
        this.mLocation = location;
    }

    public String getLocationTime() {
        return this.locationTime;
    }

    public String getLocation() {
        return this.mLocation;
    }
}
