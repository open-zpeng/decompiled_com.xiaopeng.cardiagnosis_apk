package com.xiaopeng.cardiagnosis.mqttcmd;
/* loaded from: classes4.dex */
public class HandlerCombination {
    private MqttCmdHandler handler;
    private boolean needAuthMode;
    private long timeout;

    public HandlerCombination(MqttCmdHandler handler, long timeout, boolean needAuthMode) {
        this.timeout = timeout;
        this.handler = handler;
        this.needAuthMode = needAuthMode;
    }

    public long getTimeout() {
        return this.timeout;
    }

    public void setTimeout(long timeout) {
        this.timeout = timeout;
    }

    public MqttCmdHandler getHandler() {
        return this.handler;
    }

    public void setHandler(MqttCmdHandler handler) {
        this.handler = handler;
    }

    public boolean isNeedAuthMode() {
        return this.needAuthMode;
    }

    public void setNeedAuthMode(boolean needAuthMode) {
        this.needAuthMode = needAuthMode;
    }
}
