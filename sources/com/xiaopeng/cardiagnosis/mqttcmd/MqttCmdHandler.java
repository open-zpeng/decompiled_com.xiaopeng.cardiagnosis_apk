package com.xiaopeng.cardiagnosis.mqttcmd;

import android.content.Context;
import com.xiaopeng.commonfunc.bean.MqttAfterSalesCmd;
import com.xiaopeng.lib.utils.LogUtils;
import com.xiaopeng.libconfig.ipc.bean.MqttMsgBase;
/* loaded from: classes4.dex */
public abstract class MqttCmdHandler {
    protected String CLASS_NAME = "MqttCmdHandler";
    protected Context context;
    protected CmdResponser mCmdResponser;
    protected MqttMsgBase<MqttAfterSalesCmd> mMqttCmd;

    public MqttCmdHandler(Context context, CmdResponser responser) {
        this.context = context;
        this.mCmdResponser = responser;
    }

    public void init() {
    }

    public synchronized boolean handleCommand(MqttMsgBase<MqttAfterSalesCmd> cmd) {
        if (cmd.getMsgContent().getCmd_type() == null) {
            LogUtils.e(this.CLASS_NAME, "cmd type is null");
            responseNoCmdType(cmd);
            return false;
        }
        this.mMqttCmd = cmd;
        return true;
    }

    /* JADX INFO: Access modifiers changed from: protected */
    public void responseString(MqttMsgBase<MqttAfterSalesCmd> cmd, String ossPath, String value) {
        this.mCmdResponser.responseString(cmd, ossPath, value);
    }

    /* JADX INFO: Access modifiers changed from: protected */
    public void responseNG(MqttMsgBase<MqttAfterSalesCmd> cmd) {
        this.mCmdResponser.responseNG(cmd);
    }

    /* JADX INFO: Access modifiers changed from: protected */
    public void responseOK(MqttMsgBase<MqttAfterSalesCmd> cmd, String ossPath) {
        this.mCmdResponser.responseOK(cmd, ossPath);
    }

    protected void responseNA(MqttMsgBase<MqttAfterSalesCmd> cmd) {
        this.mCmdResponser.responseNA(cmd);
    }

    /* JADX INFO: Access modifiers changed from: protected */
    public void responseNoCmdType(MqttMsgBase<MqttAfterSalesCmd> cmd) {
        this.mCmdResponser.responseNoCmdType(cmd);
    }

    /* JADX INFO: Access modifiers changed from: protected */
    public void responseUploadOssFail(MqttMsgBase<MqttAfterSalesCmd> cmd) {
        this.mCmdResponser.responseUploadOssFail(cmd);
    }

    protected void responseVinMismatch(MqttMsgBase<MqttAfterSalesCmd> cmd) {
        this.mCmdResponser.responseVinMismatch(cmd);
    }

    /* JADX INFO: Access modifiers changed from: protected */
    public void responseUpgradeLogicTreeFail(MqttMsgBase<MqttAfterSalesCmd> cmd) {
        this.mCmdResponser.responseUpgradeLogicTreeFail(cmd);
    }

    /* JADX INFO: Access modifiers changed from: protected */
    public void responseLogicActionMisMatch(MqttMsgBase<MqttAfterSalesCmd> cmd) {
        this.mCmdResponser.responseLogicActionMisMatch(cmd);
    }

    /* JADX INFO: Access modifiers changed from: protected */
    public void responseNoSuchLogicTree(MqttMsgBase<MqttAfterSalesCmd> cmd) {
        this.mCmdResponser.responseNoSuchLogicTree(cmd);
    }

    /* JADX INFO: Access modifiers changed from: protected */
    public void responseLastDiagnosisNoFinish(MqttMsgBase<MqttAfterSalesCmd> cmd) {
        this.mCmdResponser.responseLastDiagnosisNoFinish(cmd);
    }

    /* JADX INFO: Access modifiers changed from: protected */
    public void responseLogicTreeInitFail(MqttMsgBase<MqttAfterSalesCmd> cmd) {
        this.mCmdResponser.responseLogicTreeInitFail(cmd);
    }

    /* JADX INFO: Access modifiers changed from: protected */
    public void responseInterActive(MqttMsgBase<MqttAfterSalesCmd> cmd, String value) {
        this.mCmdResponser.responseInterActive(cmd, value);
    }

    public void destroy() {
    }
}
