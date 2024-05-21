package com.xiaopeng.logictree.handler;

import android.app.Application;
import android.content.Context;
import com.xiaopeng.lib.utils.LogUtils;
import com.xiaopeng.logictree.IssueInfo;
import java.util.Arrays;
/* loaded from: classes5.dex */
public abstract class LogicActionHandler {
    protected static final String DELIMITER = ",";
    protected String CLASS_NAME = "LogicActionHandler";
    protected String[] argus = null;
    protected Context context;
    protected Application mApplication;
    protected IssueInfo mIssueInfo;

    public LogicActionHandler(Application application) {
        this.context = application.getApplicationContext();
        this.mApplication = application;
    }

    public synchronized String handleCommand(IssueInfo issueInfo) {
        this.argus = issueInfo.getLogicTree().getParam().split(",", -1);
        this.mIssueInfo = issueInfo;
        LogUtils.i(this.CLASS_NAME, "argus : %s", Arrays.toString(this.argus));
        return null;
    }

    /* JADX INFO: Access modifiers changed from: protected */
    public boolean checkArgu(String[] argu, String[] argv) {
        for (int i = 0; i < argv.length; i++) {
            if (!argu[i].equalsIgnoreCase(argv[i])) {
                return false;
            }
        }
        return true;
    }

    public void destroy() {
        LogUtils.i(this.CLASS_NAME, "destroy");
    }
}
