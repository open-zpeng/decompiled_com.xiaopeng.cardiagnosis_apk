package com.xiaopeng.commonfunc.model.test;
/* loaded from: classes4.dex */
public interface ISoundModel {
    void destroy(int i);

    String getAudioPaStatus();

    int getCurrentMediaVolume();

    int getMediaMaxVolume();

    int getTTSMaxVolume();

    void onVolumeChanged(int i, int i2);

    void setMediaVolume(int i);

    void setTTSVolume(int i);

    void testTrack(int i);

    void testTrack(int i, boolean z);
}
