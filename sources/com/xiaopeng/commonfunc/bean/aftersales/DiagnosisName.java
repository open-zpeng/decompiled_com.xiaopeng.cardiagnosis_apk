package com.xiaopeng.commonfunc.bean.aftersales;
/* loaded from: classes4.dex */
public class DiagnosisName {
    private final String mErrorNameCn;
    private final String mErrorNameEng;

    public DiagnosisName(String errorNameCn, String errorNameEng) {
        this.mErrorNameCn = errorNameCn;
        this.mErrorNameEng = errorNameEng;
    }

    public String getErrorNameCn() {
        return this.mErrorNameCn;
    }

    public String getErrorNameEng() {
        return this.mErrorNameEng;
    }
}
