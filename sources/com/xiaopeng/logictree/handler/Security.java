package com.xiaopeng.logictree.handler;

import android.app.Application;
import com.xiaopeng.commonfunc.Constant;
import com.xiaopeng.commonfunc.callback.SecurityCallback;
import com.xiaopeng.commonfunc.model.indiv.IndivModel;
import com.xiaopeng.commonfunc.model.security.SecurityModel;
import com.xiaopeng.lib.utils.LogUtils;
import com.xiaopeng.lib.utils.info.BuildInfoUtils;
import com.xiaopeng.logictree.IssueInfo;
import com.xiaopeng.logictree.LogicTreeHelper;
/* loaded from: classes5.dex */
public class Security extends LogicActionHandler {
    private final IndivModel mIndivModel;
    private SecurityCallback mSecurityCallback;
    private final SecurityModel mSecurityModel;
    private int mStep;

    public /* synthetic */ void lambda$new$0$Security(int keytype, int result) {
        if (this.mStep == keytype) {
            this.mStep = -1;
            if (result == 1) {
                LogicTreeHelper.responseOK();
            } else {
                LogicTreeHelper.responseNG();
            }
        }
    }

    public Security(Application application) {
        super(application);
        this.mStep = -1;
        this.mSecurityCallback = new SecurityCallback() { // from class: com.xiaopeng.logictree.handler.-$$Lambda$Security$Fmk-yRn7uFej6vRZBeuJG_7fjwQ
            @Override // com.xiaopeng.commonfunc.callback.SecurityCallback
            public final void onReceiveResult(int i, int i2) {
                Security.this.lambda$new$0$Security(i, i2);
            }
        };
        this.CLASS_NAME = "Security";
        this.mSecurityModel = new SecurityModel(this.context, this.mSecurityCallback);
        this.mIndivModel = new IndivModel(this.context, this.mSecurityCallback);
        this.mIndivModel.init();
    }

    @Override // com.xiaopeng.logictree.handler.LogicActionHandler
    public synchronized String handleCommand(IssueInfo issueInfo) {
        super.handleCommand(issueInfo);
        if (checkArgu(this.argus, new String[]{"1"})) {
            LogUtils.i(this.CLASS_NAME, "verifyCduKey");
            this.mStep = 1;
            this.mSecurityModel.verifyCduKey();
        } else if (checkArgu(this.argus, new String[]{"2"})) {
            LogUtils.i(this.CLASS_NAME, "verifyV18CduKey");
            this.mStep = 5;
            this.mSecurityModel.verifyV18CduKey();
        } else if (checkArgu(this.argus, new String[]{"3"})) {
            LogUtils.i(this.CLASS_NAME, "verifyWifiKey");
            this.mStep = 3;
            this.mSecurityModel.verifyWifiKey();
        } else if (checkArgu(this.argus, new String[]{BuildInfoUtils.BID_LAN})) {
            LogUtils.i(this.CLASS_NAME, "verifyPsuKey");
            this.mStep = 2;
            this.mSecurityModel.verifyPsuKey();
        } else if (checkArgu(this.argus, new String[]{BuildInfoUtils.BID_PT_SPECIAL_1})) {
            LogUtils.i(this.CLASS_NAME, "changeV18CaCert");
            if (this.mSecurityModel.changeV18CaCert()) {
                LogicTreeHelper.responseOK();
            } else {
                LogicTreeHelper.responseNG();
            }
        } else if (checkArgu(this.argus, new String[]{BuildInfoUtils.BID_PT_SPECIAL_2})) {
            LogUtils.i(this.CLASS_NAME, "changeChnCaCert");
            if (this.mSecurityModel.changeChnCaCert()) {
                LogicTreeHelper.responseOK();
            } else {
                LogicTreeHelper.responseNG();
            }
        } else if (checkArgu(this.argus, new String[]{"7"})) {
            LogUtils.i(this.CLASS_NAME, "checkIndiv");
            this.mStep = 0;
            this.mIndivModel.checkIndiv();
        } else if (checkArgu(this.argus, new String[]{"8"})) {
            LogUtils.i(this.CLASS_NAME, "asyncInitIndivService");
            this.mStep = 6;
            this.mIndivModel.clearLocalIndividualData();
            this.mIndivModel.asyncInitIndivService();
        } else if (checkArgu(this.argus, new String[]{Constant.ALPHA_9})) {
            LogUtils.i(this.CLASS_NAME, "isTeeInitSuccess");
            if (this.mIndivModel.isTeeInitSuccess()) {
                LogicTreeHelper.responseOK();
            } else {
                LogicTreeHelper.responseNG();
            }
        }
        return null;
    }

    @Override // com.xiaopeng.logictree.handler.LogicActionHandler
    public void destroy() {
        this.mSecurityModel.onDestroy();
        this.mIndivModel.deinit();
        super.destroy();
    }
}
