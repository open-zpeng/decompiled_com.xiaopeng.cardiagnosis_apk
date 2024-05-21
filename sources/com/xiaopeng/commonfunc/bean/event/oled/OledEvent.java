package com.xiaopeng.commonfunc.bean.event.oled;
/* loaded from: classes4.dex */
public class OledEvent {
    public static final int MUSIC_COMPLETE = 2;
    public static final int MUSIC_ERROR = 3;
    public static final int MUSIC_PLAY = 1;
    public int mMusicPlayStatus;

    public OledEvent(int state) {
        this.mMusicPlayStatus = state;
    }
}
