package com.xiaopeng.lib.framework.moduleinterface.locationmodule;
/* loaded from: classes4.dex */
public interface IStateEvent {

    /* loaded from: classes4.dex */
    public enum TYPE {
        BOUND,
        UNBOUND
    }

    TYPE type();
}
