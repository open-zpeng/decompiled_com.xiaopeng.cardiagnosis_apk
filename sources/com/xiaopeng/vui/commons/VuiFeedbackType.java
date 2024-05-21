package com.xiaopeng.vui.commons;

import com.xiaopeng.libconfig.settings.SettingsUtil;
/* loaded from: classes5.dex */
public enum VuiFeedbackType {
    SOUND(SettingsUtil.PAGE_SOUND),
    TTS("Tts");
    
    private String type;

    VuiFeedbackType(String type) {
        this.type = type;
    }

    public String getType() {
        return this.type;
    }
}
