package com.xiaopeng.lib.framework.moduleinterface.aiassistantmodule.sensor;
/* loaded from: classes4.dex */
public interface IContextSensor {
    void getSensorValue(String sensorName, String field, ISensorCallback listener);

    void subscribe(String sensorName, ISensorListener listener);

    void unSubscribe(String sensorName, ISensorListener listener);
}
