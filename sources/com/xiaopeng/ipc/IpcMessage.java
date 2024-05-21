package com.xiaopeng.ipc;

import android.os.Bundle;
import android.os.Parcel;
import android.os.Parcelable;
/* loaded from: classes4.dex */
public class IpcMessage implements Parcelable {
    public static final Parcelable.Creator<IpcMessage> CREATOR = new Parcelable.Creator<IpcMessage>() { // from class: com.xiaopeng.ipc.IpcMessage.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public IpcMessage createFromParcel(Parcel in) {
            return new IpcMessage(in);
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public IpcMessage[] newArray(int size) {
            return new IpcMessage[size];
        }
    };
    int msgId;
    String packageName;
    Bundle payloadData;

    public IpcMessage() {
    }

    protected IpcMessage(Parcel in) {
        this.packageName = in.readString();
        this.msgId = in.readInt();
        this.payloadData = in.readBundle();
    }

    @Override // android.os.Parcelable
    public int describeContents() {
        return 0;
    }

    @Override // android.os.Parcelable
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.packageName);
        dest.writeInt(this.msgId);
        dest.writeBundle(this.payloadData);
    }

    public void readFromParcel(Parcel in) {
        this.packageName = in.readString();
        this.msgId = in.readInt();
        this.payloadData = in.readBundle();
    }

    public String getPackageName() {
        return this.packageName;
    }

    public void setPackageName(String packageName) {
        this.packageName = packageName;
    }

    public int getMsgId() {
        return this.msgId;
    }

    public void setMsgId(int msgId) {
        this.msgId = msgId;
    }

    public Bundle getPayloadData() {
        return this.payloadData;
    }

    public void setPayloadData(Bundle payloadData) {
        this.payloadData = payloadData;
    }
}
