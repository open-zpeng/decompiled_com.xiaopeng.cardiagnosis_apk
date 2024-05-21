package com.xiaopeng.logictree;
/* loaded from: classes5.dex */
public class IssueInfo {
    private static IssueInfo sIssueInfo;
    private long mEndTime;
    private int mEntry;
    private String mIssueName;
    private LogicTree mLogicTree;
    private String mLogicVersion;
    private long mStartTime;

    public IssueInfo(long mStartTime, long mEndTime, LogicTree mLogicTree, String mIssueName, int mEntry, String mLogicVersion) {
        this.mStartTime = mStartTime;
        this.mEndTime = mEndTime;
        this.mLogicTree = mLogicTree;
        this.mIssueName = mIssueName;
        this.mEntry = mEntry;
        this.mLogicVersion = mLogicVersion;
    }

    public static IssueInfo getInstance() {
        if (sIssueInfo == null) {
            sIssueInfo = new IssueInfo(0L, 0L, null, null, 0, null);
        }
        return sIssueInfo;
    }

    public long getStartTime() {
        return this.mStartTime;
    }

    public void setStartTime(long mStartTime) {
        this.mStartTime = mStartTime;
    }

    public long getEndTime() {
        return this.mEndTime;
    }

    public void setEndTime(long mEndTime) {
        this.mEndTime = mEndTime;
    }

    public LogicTree getLogicTree() {
        return this.mLogicTree;
    }

    public void setLogicTree(LogicTree mLogicTree) {
        this.mLogicTree = mLogicTree;
    }

    public String getIssueName() {
        return this.mIssueName;
    }

    public void setIssueName(String mIssueName) {
        this.mIssueName = mIssueName;
    }

    public int getEntry() {
        return this.mEntry;
    }

    public void setEntry(int mEntry) {
        this.mEntry = mEntry;
    }

    public String getLogicVersion() {
        return this.mLogicVersion;
    }

    public void setLogicVersion(String mLogicVersion) {
        this.mLogicVersion = mLogicVersion;
    }

    public String toString() {
        return "IssueInfo{mStartTime=" + this.mStartTime + ", mEndTime=" + this.mEndTime + ", mLogicTree=" + this.mLogicTree + ", mIssueName='" + this.mIssueName + "'}";
    }
}
