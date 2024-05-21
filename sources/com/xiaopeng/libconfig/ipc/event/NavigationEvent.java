package com.xiaopeng.libconfig.ipc.event;
/* loaded from: classes5.dex */
public class NavigationEvent {
    private int index;
    private boolean state;

    public NavigationEvent setState(boolean state) {
        this.state = state;
        return this;
    }

    public NavigationEvent setIndex(int index) {
        this.index = index;
        return this;
    }

    public boolean getState() {
        return this.state;
    }

    public int getIndex() {
        return this.index;
    }

    public String toString() {
        return "{\"state\":" + this.state + ",\"index\":" + this.index + "}";
    }
}
