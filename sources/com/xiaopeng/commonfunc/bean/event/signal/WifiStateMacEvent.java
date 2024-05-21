package com.xiaopeng.commonfunc.bean.event.signal;

import com.xiaopeng.commonfunc.Constant;
/* loaded from: classes4.dex */
public class WifiStateMacEvent {
    private String mac;
    private int state;

    public WifiStateMacEvent() {
        this.state = 0;
        this.mac = Constant.UNKNOWN_STRING;
    }

    public WifiStateMacEvent(int state, String mac) {
        this.state = state;
        this.mac = mac;
    }

    public int getState() {
        return this.state;
    }

    public void setState(int state) {
        this.state = state;
    }

    public String getMac() {
        return this.mac;
    }

    public void setMac(String mac) {
        this.mac = mac;
    }

    public String toString() {
        return "WifiStateMacEvent{state=" + this.state + ", mac='" + this.mac + "'}";
    }
}
