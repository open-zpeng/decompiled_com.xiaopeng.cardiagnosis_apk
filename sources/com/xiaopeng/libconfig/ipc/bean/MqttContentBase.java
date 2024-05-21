package com.xiaopeng.libconfig.ipc.bean;
/* loaded from: classes5.dex */
public class MqttContentBase<T> {
    public static final int CODE_SUCCESS = 200;
    public static final int RESPONSE_TYPE_RECIEVED = 1;
    public static final int RESPONSE_TYPE_SUCCESS = 99;
    private int code;
    private T data;
    private int respType;

    public int getCode() {
        return this.code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public int getRespType() {
        return this.respType;
    }

    public void setRespType(int respType) {
        this.respType = respType;
    }

    public T getData() {
        return this.data;
    }

    public void setData(T data) {
        this.data = data;
    }
}
