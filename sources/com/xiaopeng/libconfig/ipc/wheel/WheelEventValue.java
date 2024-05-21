package com.xiaopeng.libconfig.ipc.wheel;

import android.text.TextUtils;
import org.json.JSONException;
import org.json.JSONObject;
/* loaded from: classes5.dex */
public class WheelEventValue {
    private int eventCode;
    private int eventValue;
    private int keyCode;
    private int keyMode;

    public WheelEventValue() {
    }

    public WheelEventValue(int kc, int ec, int ev, int km) {
        this.keyCode = kc;
        this.eventCode = ec;
        this.eventValue = ev;
        this.keyMode = km;
    }

    public WheelEventValue(String json) {
        if (TextUtils.isEmpty(json)) {
            return;
        }
        try {
            JSONObject jsonObject = new JSONObject(json);
            this.keyCode = jsonObject.getInt(WheelEvent.KEY_ACTION_KEY_CODE);
            this.eventCode = jsonObject.getInt(WheelEvent.KEY_ACTION_EVENT_CODE);
            this.eventValue = jsonObject.getInt(WheelEvent.KEY_ACTION_EVENT_VALUE);
            this.keyMode = jsonObject.getInt(WheelEvent.KEY_ACTION_KEY_MODE);
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    public int getKeyCode() {
        return this.keyCode;
    }

    public void setKeyCode(int keyCode) {
        this.keyCode = keyCode;
    }

    public int getEventCode() {
        return this.eventCode;
    }

    public void setEventCode(int eventCode) {
        this.eventCode = eventCode;
    }

    public int getEventValue() {
        return this.eventValue;
    }

    public void setEventValue(int eventValue) {
        this.eventValue = eventValue;
    }

    public int getKeyMode() {
        return this.keyMode;
    }

    public void setKeyMode(int keyMode) {
        this.keyMode = keyMode;
    }

    public String toString() {
        return "WheelEventValue{keyCode=" + this.keyCode + ", eventCode=" + this.eventCode + ", eventValue=" + this.eventValue + ", keyMode=" + this.keyMode + '}';
    }
}
