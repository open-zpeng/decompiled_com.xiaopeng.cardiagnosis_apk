package com.xiaopeng.commonfunc.utils;

import org.greenrobot.eventbus.EventBus;
/* loaded from: classes4.dex */
public class EventBusUtil {
    public static final int CAR_SERVICE_CONNECTED = 66442200;
    private static final String TAG = "EventBusUtil";

    public static void registerEventBus(Object subscriber) {
        if (!EventBus.getDefault().isRegistered(subscriber)) {
            EventBus.getDefault().register(subscriber);
        }
    }

    public static void unregisterEventBus(Object subscriber) {
        if (EventBus.getDefault().isRegistered(subscriber)) {
            EventBus.getDefault().unregister(subscriber);
        }
    }
}
