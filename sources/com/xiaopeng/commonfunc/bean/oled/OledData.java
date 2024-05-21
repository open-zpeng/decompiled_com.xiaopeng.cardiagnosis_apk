package com.xiaopeng.commonfunc.bean.oled;

import com.google.gson.annotations.SerializedName;
import com.xiaopeng.commonfunc.Constant;
/* loaded from: classes4.dex */
public class OledData {
    @SerializedName(Constant.BT_NAME)
    private String btName;
    @SerializedName("CDU_OELD_DynamicModeCfg")
    private int cduOledDynamicMode;

    public String getBtName() {
        return this.btName;
    }

    public void setBtName(String btName) {
        this.btName = btName;
    }

    public int getCduOledDynamicMode() {
        return this.cduOledDynamicMode;
    }

    public void setCduOledDynamicMode(int cduOledDynamicMode) {
        this.cduOledDynamicMode = cduOledDynamicMode;
    }
}
