package com.xiaopeng.commonfunc.bean.car;

import com.google.gson.annotations.SerializedName;
import com.xiaopeng.commonfunc.Constant;
/* loaded from: classes4.dex */
public class TboxCanControlButton {
    private int Color;
    private int Key;
    private String Label;
    @SerializedName("Value")
    private Value mValue;

    public int getKey() {
        return this.Key;
    }

    public int getColor() {
        return this.Color;
    }

    public String getLabel() {
        return this.Label;
    }

    public Value getValue() {
        return this.mValue;
    }

    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("TboxCanControlButton{Key=");
        sb.append(this.Key);
        sb.append(", Color=");
        sb.append(this.Color);
        sb.append(", Label='");
        sb.append(this.Label);
        sb.append('\'');
        sb.append(", mValue=");
        Value value = this.mValue;
        sb.append(value != null ? value.toString() : Constant.SPACE_STRING);
        sb.append('}');
        return sb.toString();
    }
}
