package com.xiaopeng.libconfig.ipc.bean;
/* loaded from: classes5.dex */
public class MqttMsgBase<T> {
    public static final int CONTROL_MSG = 2;
    public static final int HEART_BEAT_MSG = 4;
    public static final int OPERATION_FEEDBACK_MSG = 1;
    public static final int SERVER_MSG = 3;
    public static final int UNREAD_MSG = 0;
    private T msg_content;
    private String msg_id;
    private String msg_ref;
    private int msg_type;
    private int service_type = -1;

    public MqttMsgBase(int vid) {
        StringBuilder builder = new StringBuilder();
        builder.append(20);
        builder.append(vid);
        builder.append(System.currentTimeMillis());
        builder.append((int) (Math.random() * 10.0d));
        builder.append((int) (Math.random() * 10.0d));
        this.msg_id = builder.toString();
    }

    public MqttMsgBase(String cduid) {
        this.msg_id = 20 + cduid + System.currentTimeMillis() + ((int) (Math.random() * 10.0d)) + ((int) (Math.random() * 10.0d));
    }

    public void setMsgId(String msgId) {
        this.msg_id = msgId;
    }

    public String getMsgId() {
        return this.msg_id;
    }

    public String getMsgRef() {
        return this.msg_ref;
    }

    public void setMsgRef(String msgRef) {
        this.msg_ref = msgRef;
    }

    public int getMsgType() {
        return this.msg_type;
    }

    public void setMsgType(int msgType) {
        this.msg_type = msgType;
    }

    public int getServiceType() {
        return this.service_type;
    }

    public void setServiceType(int serviceType) {
        this.service_type = serviceType;
    }

    public T getMsgContent() {
        return this.msg_content;
    }

    public void setMsgContent(T msgContent) {
        this.msg_content = msgContent;
    }

    public String toString() {
        return "MqttMsgBase{msg_id='" + this.msg_id + "', msg_ref='" + this.msg_ref + "', msg_type=" + this.msg_type + ", service_type=" + this.service_type + ", msg_content=" + this.msg_content + '}';
    }
}
