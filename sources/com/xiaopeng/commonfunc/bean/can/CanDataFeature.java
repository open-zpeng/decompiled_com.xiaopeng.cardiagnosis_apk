package com.xiaopeng.commonfunc.bean.can;
/* loaded from: classes4.dex */
public class CanDataFeature {
    private int minLength = 0;
    private int minEachCanLength = 0;
    private int startpoint = 0;
    private int channelbitoffset = 0;
    private byte canidbit = 0;
    private boolean withValidbit = false;
    private boolean withTimestamp = false;
    private boolean withTriggerEvent = false;
    private boolean isDLC = false;

    public boolean isDLC() {
        return this.isDLC;
    }

    public void setDLC(boolean DLC) {
        this.isDLC = DLC;
    }

    public boolean isWithTriggerEvent() {
        return this.withTriggerEvent;
    }

    public void setWithTriggerEvent(boolean withTriggerEvent) {
        this.withTriggerEvent = withTriggerEvent;
    }

    public boolean isWithTimestamp() {
        return this.withTimestamp;
    }

    public void setWithTimestamp(boolean withTimestamp) {
        this.withTimestamp = withTimestamp;
    }

    public int getMinLength() {
        return this.minLength;
    }

    public void setMinLength(int minLength) {
        this.minLength = minLength;
    }

    public int getMinEachCanLength() {
        return this.minEachCanLength;
    }

    public void setMinEachCanLength(int minEachCanLength) {
        this.minEachCanLength = minEachCanLength;
    }

    public int getStartpoint() {
        return this.startpoint;
    }

    public void setStartpoint(int startpoint) {
        this.startpoint = startpoint;
    }

    public int getChannelbitoffset() {
        return this.channelbitoffset;
    }

    public void setChannelbitoffset(int channelbitoffset) {
        this.channelbitoffset = channelbitoffset;
    }

    public byte getCanidbit() {
        return this.canidbit;
    }

    public void setCanidbit(byte canidbit) {
        this.canidbit = canidbit;
    }

    public boolean isWithValidbit() {
        return this.withValidbit;
    }

    public void setWithValidbit(boolean withValidbit) {
        this.withValidbit = withValidbit;
    }
}
