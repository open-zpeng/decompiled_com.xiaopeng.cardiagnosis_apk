package com.xiaopeng.commonfunc.bean.car;
/* loaded from: classes4.dex */
public class EcuUpdateResult {
    public static final String FAIL_REASON_SEND_FILE_FAIL = "fail send file to QNX";
    public static final String RESULT_FAIL = "fail";
    public static final String RESULT_MORE = "more";
    public static final String RESULT_SUCCESS = "success";
    private String Reason;
    private String Result;
    private String Target;

    public EcuUpdateResult(String target, String result, String reason) {
        this.Target = target;
        this.Result = result;
        this.Reason = reason;
    }

    public String getTarget() {
        return this.Target;
    }

    public void setTarget(String target) {
        this.Target = target;
    }

    public String getResult() {
        return this.Result;
    }

    public void setResult(String result) {
        this.Result = result;
    }

    public String getReason() {
        return this.Reason;
    }

    public void setReason(String reason) {
        this.Reason = reason;
    }

    public String toString() {
        return "EcuUpdateResult{Target='" + this.Target + "', Result='" + this.Result + "', Reason='" + this.Reason + "'}";
    }
}
