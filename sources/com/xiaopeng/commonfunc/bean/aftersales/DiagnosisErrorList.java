package com.xiaopeng.commonfunc.bean.aftersales;

import com.xiaopeng.commonfunc.Constant;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
/* loaded from: classes4.dex */
public class DiagnosisErrorList {
    private static final DiagnosisName UNKNOWN_DIAGNOSIS_NAME = new DiagnosisName("未知", Constant.UNKNOWN_STRING);
    private List<DiagnosisData> mDiagnosisDataList;
    private final Map<Integer, DiagnosisName> mErrorCodeList;

    public DiagnosisErrorList(Map<Integer, DiagnosisName> mErrorCodeList) {
        this.mErrorCodeList = mErrorCodeList;
    }

    private void newDiagnosisDataList() {
        this.mDiagnosisDataList = new ArrayList();
    }

    public void addErrorCode(int errorCode, DiagnosisName name) {
        this.mErrorCodeList.put(Integer.valueOf(errorCode), name);
    }

    public DiagnosisName getDiagnosisName(int errorCode) {
        DiagnosisName name = this.mErrorCodeList.get(Integer.valueOf(errorCode));
        if (name == null) {
            return UNKNOWN_DIAGNOSIS_NAME;
        }
        return name;
    }

    public void addDiagnosisData(DiagnosisData data) {
        if (this.mDiagnosisDataList == null) {
            newDiagnosisDataList();
        }
        this.mDiagnosisDataList.add(data);
    }

    public void clearDiagnosisData() {
        if (this.mDiagnosisDataList == null) {
            newDiagnosisDataList();
        }
        this.mDiagnosisDataList.clear();
    }

    public DiagnosisData getDiagnosisData(int index) {
        if (this.mDiagnosisDataList == null) {
            newDiagnosisDataList();
        }
        return this.mDiagnosisDataList.get(index);
    }

    public int getDiagnosisDataSize() {
        if (this.mDiagnosisDataList == null) {
            newDiagnosisDataList();
        }
        return this.mDiagnosisDataList.size();
    }
}
