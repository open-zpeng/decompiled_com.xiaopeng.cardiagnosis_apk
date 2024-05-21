package com.xiaopeng.logictree.handler;

import android.app.Application;
import com.xiaopeng.commonfunc.Constant;
import com.xiaopeng.commonfunc.bean.aftersales.DiagnosisData;
import com.xiaopeng.commonfunc.model.diagnosis.DiagnosisCodeModel;
import com.xiaopeng.lib.utils.info.BuildInfoUtils;
import com.xiaopeng.logictree.IssueInfo;
import com.xiaopeng.logictree.LogicTreeHelper;
import java.util.LinkedList;
import java.util.List;
/* loaded from: classes5.dex */
public class DiagnosisCode extends LogicActionHandler {
    private static final long DIAGNOSIS_CODE_ONE_DAY_GAP = 115200000;
    private static final long DIAGNOSIS_CODE_TIME_GAP = 1800000;
    private final DiagnosisCodeModel mDiagnosisCodeModel;

    public DiagnosisCode(Application application) {
        super(application);
        this.CLASS_NAME = "DiagnosisCode";
        this.mDiagnosisCodeModel = new DiagnosisCodeModel(this.context);
        this.mDiagnosisCodeModel.initData();
    }

    @Override // com.xiaopeng.logictree.handler.LogicActionHandler
    public synchronized String handleCommand(IssueInfo issueInfo) {
        super.handleCommand(issueInfo);
        if (checkArgu(this.argus, new String[]{"1"})) {
            long startTime = issueInfo.getStartTime();
            long endTime = issueInfo.getEndTime();
            if (endTime - startTime < DIAGNOSIS_CODE_TIME_GAP) {
                startTime = endTime - DIAGNOSIS_CODE_TIME_GAP;
            }
            List<Integer> data = this.mDiagnosisCodeModel.getDiagnosisBetweenTime(this.argus[1], startTime, endTime);
            if (data.size() == 0) {
                data.add(0);
            }
            LogicTreeHelper.responseResult(data.stream().mapToInt($$Lambda$UV1wDVoVlbcxpr8zevj_aMFtUGw.INSTANCE).toArray());
        } else if (checkArgu(this.argus, new String[]{"2"})) {
            long startTime2 = issueInfo.getStartTime();
            long endTime2 = issueInfo.getEndTime();
            if (endTime2 - startTime2 < DIAGNOSIS_CODE_TIME_GAP) {
                startTime2 = endTime2 - DIAGNOSIS_CODE_TIME_GAP;
            }
            List<DiagnosisData> dataList = this.mDiagnosisCodeModel.getDiagnosisDataBetweenTime(this.argus[1], startTime2, endTime2);
            List<String> list = new LinkedList<>();
            for (DiagnosisData data2 : dataList) {
                String temp = data2.getErrorCode() + Constant.VERTIAL_BAR_STRING + data2.getErrorMsg();
                if (!list.contains(temp)) {
                    list.add(temp);
                }
            }
            if (list.size() == 0) {
                list.add("");
            }
            LogicTreeHelper.responseResult((String[]) list.toArray(new String[list.size()]));
        } else if (checkArgu(this.argus, new String[]{"3"})) {
            long currentTime = System.currentTimeMillis();
            List<Integer> data3 = this.mDiagnosisCodeModel.getDiagnosisBetweenTime(this.argus[1], currentTime - DIAGNOSIS_CODE_TIME_GAP, currentTime);
            if (data3.size() == 0) {
                data3.add(0);
            }
            LogicTreeHelper.responseResult(data3.stream().mapToInt($$Lambda$UV1wDVoVlbcxpr8zevj_aMFtUGw.INSTANCE).toArray());
        } else if (checkArgu(this.argus, new String[]{BuildInfoUtils.BID_LAN})) {
            long currentTime2 = System.currentTimeMillis();
            List<DiagnosisData> dataList2 = this.mDiagnosisCodeModel.getDiagnosisDataBetweenTime(this.argus[1], currentTime2 - DIAGNOSIS_CODE_TIME_GAP, currentTime2);
            List<String> list2 = new LinkedList<>();
            for (DiagnosisData data4 : dataList2) {
                String temp2 = data4.getErrorCode() + Constant.VERTIAL_BAR_STRING + data4.getErrorMsg();
                if (!list2.contains(temp2)) {
                    list2.add(temp2);
                }
            }
            if (list2.size() == 0) {
                list2.add("");
            }
            LogicTreeHelper.responseResult((String[]) list2.toArray(new String[list2.size()]));
        } else if (checkArgu(this.argus, new String[]{BuildInfoUtils.BID_PT_SPECIAL_1})) {
            long startTime3 = issueInfo.getStartTime();
            long endTime3 = issueInfo.getEndTime();
            if (endTime3 - startTime3 < DIAGNOSIS_CODE_ONE_DAY_GAP) {
                startTime3 = endTime3 - DIAGNOSIS_CODE_ONE_DAY_GAP;
            }
            List<Integer> data5 = this.mDiagnosisCodeModel.getDiagnosisBetweenTime(this.argus[1], startTime3, endTime3);
            if (data5.size() == 0) {
                data5.add(0);
            }
            LogicTreeHelper.responseResult(data5.stream().mapToInt($$Lambda$UV1wDVoVlbcxpr8zevj_aMFtUGw.INSTANCE).toArray());
        } else if (checkArgu(this.argus, new String[]{BuildInfoUtils.BID_PT_SPECIAL_2})) {
            long startTime4 = issueInfo.getStartTime();
            long endTime4 = issueInfo.getEndTime();
            if (endTime4 - startTime4 < DIAGNOSIS_CODE_ONE_DAY_GAP) {
                startTime4 = endTime4 - DIAGNOSIS_CODE_ONE_DAY_GAP;
            }
            List<DiagnosisData> dataList3 = this.mDiagnosisCodeModel.getDiagnosisDataBetweenTime(this.argus[1], startTime4, endTime4);
            List<String> list3 = new LinkedList<>();
            for (DiagnosisData data6 : dataList3) {
                String temp3 = data6.getErrorCode() + Constant.VERTIAL_BAR_STRING + data6.getErrorMsg();
                if (!list3.contains(temp3)) {
                    list3.add(temp3);
                }
            }
            if (list3.size() == 0) {
                list3.add("");
            }
            LogicTreeHelper.responseResult((String[]) list3.toArray(new String[list3.size()]));
        }
        return null;
    }

    @Override // com.xiaopeng.logictree.handler.LogicActionHandler
    public void destroy() {
        super.destroy();
        this.mDiagnosisCodeModel.onDestroy();
    }
}
