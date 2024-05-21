package com.xiaopeng.commonfunc.bean.event;

import com.google.gson.annotations.SerializedName;
/* loaded from: classes4.dex */
public class BoardCastEvent {
    public static final String DATA_FAIL = "1";
    public static final String DATA_SUCCESS = "0";
    public static final int TYPE_OTA_CDU = 0;
    public static final int TYPE_OTA_ICM = 3;
    public static final int TYPE_OTA_MCU = 1;
    public static final int TYPE_OTA_PSU = 2;
    @SerializedName("data")
    private final String data;
    @SerializedName("type")
    private final int type;

    public BoardCastEvent(int type, String data) {
        this.type = type;
        this.data = data;
    }

    public int getType() {
        return this.type;
    }

    public String getData() {
        return this.data;
    }
}
