package com.xiaopeng.commonfunc.bean.smartdrive;

import com.google.gson.annotations.SerializedName;
import com.xiaopeng.commonfunc.Constant;
/* loaded from: classes4.dex */
public class SmartDriveData {
    @SerializedName(Constant.BT_NAME)
    private String btName;
    @SerializedName("CDU_SCU_Test")
    private int cduScuTest;

    public String getBtName() {
        return this.btName;
    }

    public void setBtName(String btName) {
        this.btName = btName;
    }

    public int getCduScuTest() {
        return this.cduScuTest;
    }

    public void setCduScuTest(int cduScuTest) {
        this.cduScuTest = cduScuTest;
    }
}
