package com.xiaopeng.commonfunc.bean.oled;

import com.google.gson.annotations.SerializedName;
/* loaded from: classes4.dex */
public class CustomOledMusicBook {
    @SerializedName("duration")
    private int duration;
    @SerializedName("note")
    private int note;

    public int getNote() {
        return this.note;
    }

    public void setNote(int note) {
        this.note = note;
    }

    public int getDuration() {
        return this.duration;
    }

    public void setDuration(int duration) {
        this.duration = duration;
    }
}
