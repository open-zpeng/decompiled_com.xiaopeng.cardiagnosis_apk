package com.xiaopeng.commonfunc.bean.can;

import java.util.concurrent.locks.ReentrantLock;
/* loaded from: classes4.dex */
public class CanDataNode {
    private final byte[] data;
    private final int dataLength;
    private String parsingString;
    private final int queque;
    private String triggerEvent;
    private final byte[] type;
    private final ReentrantLock mReentrantLock = new ReentrantLock();
    private boolean isWithTriggerEvent = false;
    private int triggerFlag = 0;
    private boolean isDataNormal = false;
    private boolean isParsed = false;

    public CanDataNode(byte[] data, int dataLength, byte[] type, int queque) {
        this.data = data;
        this.dataLength = dataLength;
        this.type = type;
        this.queque = queque;
    }

    public void lockData() {
        this.mReentrantLock.lock();
    }

    public void unlockData() {
        this.mReentrantLock.unlock();
    }

    public int getTriggerFlag() {
        return this.triggerFlag;
    }

    public void setTriggerFlag(int triggerFlag) {
        this.triggerFlag = triggerFlag;
    }

    public boolean isParsed() {
        return this.isParsed;
    }

    public void setParsed(boolean parsed) {
        this.isParsed = parsed;
    }

    public boolean isDataNormal() {
        return this.isDataNormal;
    }

    public void setDataNormal(boolean dataNormal) {
        this.isDataNormal = dataNormal;
    }

    public boolean isWithTriggerEvent() {
        return this.isWithTriggerEvent;
    }

    public void setWithTriggerEvent(boolean withTriggerEvent) {
        this.isWithTriggerEvent = withTriggerEvent;
    }

    public String getTriggerEvent() {
        return this.triggerEvent;
    }

    public void setTriggerEvent(String triggerEvent) {
        this.triggerEvent = triggerEvent;
    }

    public String getParsingString() {
        return this.parsingString;
    }

    public void setParsingString(String parsingString) {
        this.parsingString = parsingString;
    }

    public byte[] getData() {
        return this.data;
    }

    public int getDataLength() {
        return this.dataLength;
    }

    public byte[] getType() {
        return this.type;
    }

    public int getQueque() {
        return this.queque;
    }
}
