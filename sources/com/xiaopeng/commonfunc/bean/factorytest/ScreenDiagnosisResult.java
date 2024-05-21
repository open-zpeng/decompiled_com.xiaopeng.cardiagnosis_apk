package com.xiaopeng.commonfunc.bean.factorytest;

import java.util.List;
/* loaded from: classes4.dex */
public class ScreenDiagnosisResult {
    private String mCurrentResult;
    private List<ErrorItem> mErrorList;
    private long mTestCounts;

    public ScreenDiagnosisResult(long mTestCounts, String mCurrentResult, List<ErrorItem> mErrorList) {
        this.mTestCounts = mTestCounts;
        this.mCurrentResult = mCurrentResult;
        this.mErrorList = mErrorList;
    }

    public ScreenDiagnosisResult(long mTestCounts) {
        this.mTestCounts = mTestCounts;
        this.mCurrentResult = "";
        this.mErrorList = null;
    }

    public long getTestCounts() {
        return this.mTestCounts;
    }

    public String getCurrentResult() {
        return this.mCurrentResult;
    }

    public synchronized void setCurrentResult(String result) {
        this.mCurrentResult = result;
    }

    public List<ErrorItem> getErrorList() {
        return this.mErrorList;
    }

    public synchronized void setErrorList(List<ErrorItem> mErrorList) {
        this.mErrorList = mErrorList;
    }

    public synchronized void increaseTestCount() {
        this.mTestCounts++;
    }

    public synchronized void updateErrorResult(int index, String happen_time) {
        this.mErrorList.get(index).setLastHappen(happen_time);
        this.mErrorList.get(index).increaseErrorTimes();
    }

    /* loaded from: classes4.dex */
    public class ErrorItem {
        private int error_times;
        private String last_happen;
        private String name;

        public ErrorItem(String name, int error_times, String last_happen) {
            this.name = name;
            this.error_times = error_times;
            this.last_happen = last_happen;
        }

        public String getName() {
            return this.name;
        }

        public synchronized void setName(String name) {
            this.name = name;
        }

        public int getErrorTimes() {
            return this.error_times;
        }

        public synchronized void increaseErrorTimes() {
            this.error_times++;
        }

        public String getLastHappen() {
            return this.last_happen;
        }

        public synchronized void setLastHappen(String last_happen) {
            this.last_happen = last_happen;
        }
    }
}
