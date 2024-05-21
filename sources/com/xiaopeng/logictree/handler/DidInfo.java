package com.xiaopeng.logictree.handler;

import android.app.Application;
import com.xiaopeng.commonfunc.model.diagnosis.DidModel;
import com.xiaopeng.lib.utils.ThreadUtils;
import com.xiaopeng.logictree.IssueInfo;
import com.xiaopeng.logictree.LogicTreeHelper;
/* loaded from: classes5.dex */
public class DidInfo extends LogicActionHandler {
    public DidInfo(Application application) {
        super(application);
        this.CLASS_NAME = "DidInfo";
    }

    @Override // com.xiaopeng.logictree.handler.LogicActionHandler
    public synchronized String handleCommand(IssueInfo issueInfo) {
        super.handleCommand(issueInfo);
        if (checkArgu(this.argus, new String[]{"1"})) {
            ThreadUtils.execute(new Runnable() { // from class: com.xiaopeng.logictree.handler.-$$Lambda$DidInfo$bve3ZHb_6jHtMZFU8UBsjNcpw58
                @Override // java.lang.Runnable
                public final void run() {
                    DidInfo.this.lambda$handleCommand$0$DidInfo();
                }
            });
        }
        return null;
    }

    public /* synthetic */ void lambda$handleCommand$0$DidInfo() {
        Object value = DidModel.getDidInfo(Integer.parseInt(this.argus[1]), Integer.parseInt(this.argus[2]), Integer.parseInt(this.argus[3]), this.argus[4]);
        if (value != null) {
            if (value instanceof String) {
                LogicTreeHelper.responseResult((String) value);
                return;
            } else if (value instanceof Integer) {
                LogicTreeHelper.responseResult((Integer) value);
                return;
            } else if (value instanceof Long) {
                LogicTreeHelper.responseResult((Long) value);
                return;
            } else {
                return;
            }
        }
        LogicTreeHelper.responseNoResult();
    }

    @Override // com.xiaopeng.logictree.handler.LogicActionHandler
    public void destroy() {
        super.destroy();
    }
}
