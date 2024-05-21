package com.xiaopeng.cardiagnosis.mqttcmd;

import android.content.Context;
import android.text.TextUtils;
import androidx.annotation.RequiresApi;
import com.google.gson.Gson;
import com.xiaopeng.cardiagnosis.CarApplication;
import com.xiaopeng.cardiagnosis.bean.ParamLogicTree;
import com.xiaopeng.commonfunc.bean.MqttAfterSalesCmd;
import com.xiaopeng.commonfunc.bean.event.CommonEvent;
import com.xiaopeng.commonfunc.utils.AfterSalesHelper;
import com.xiaopeng.commonfunc.utils.EventBusUtil;
import com.xiaopeng.lib.utils.LogUtils;
import com.xiaopeng.lib.utils.crypt.AESUtils;
import com.xiaopeng.libconfig.ipc.bean.MqttMsgBase;
import com.xiaopeng.logictree.LogicTreeHelper;
import com.xiaopeng.logictree.LogicTreeInfo;
import com.xiaopeng.logictree.LogicTreeParser;
import com.xiaopeng.logictree.bean.LogicInterActiveData;
import com.xiaopeng.logictree.bean.LogicResponseData;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;
/* loaded from: classes4.dex */
public class MqttLogicTree extends MqttCmdHandler {
    private static final String MQTT_CMD_TYPE_EXEC_LOGIC_TREE = "7001";
    private static final String MQTT_CMD_TYPE_INTERACTIVE = "7002";
    private static final int STEP_DUMMY = 0;
    private static final int STEP_EXEC_LOGICTREE = 10003;
    private static final int STEP_INIT_LOGICTREE = 10001;
    private static final int STEP_UPGRADE = 10002;
    private static final int STEP_WAIT_INTERACTIVE_RESPONSE = 1004;
    private LogicTreeParser mLogicTreeParser;
    private String mMsgId;
    private ParamLogicTree mParamLogicTree;
    private int mStep;

    @Subscribe(threadMode = ThreadMode.BACKGROUND)
    @RequiresApi(api = 24)
    public void onEvent(CommonEvent event) {
        LogUtils.i(this.CLASS_NAME, "step[%d] %s", Integer.valueOf(this.mStep), event);
        switch (event.getState()) {
            case 10007:
                if (this.mStep == 10002) {
                    this.mStep = 0;
                    responseUpgradeLogicTreeFail(this.mMqttCmd);
                    return;
                }
                return;
            case 10008:
                if (this.mStep == 10002) {
                    this.mStep = 10001;
                    this.mLogicTreeParser.initLogicTreeList();
                    return;
                }
                return;
            case 10009:
                if (this.mStep == 10001) {
                    if (this.mLogicTreeParser.isLogicTreeNeedUpdate()) {
                        this.mStep = 0;
                        responseUpgradeLogicTreeFail(this.mMqttCmd);
                        return;
                    }
                    execLogicTreeDiagnosis();
                    return;
                }
                return;
            default:
                return;
        }
    }

    @Subscribe(threadMode = ThreadMode.BACKGROUND)
    @RequiresApi(api = 24)
    public void onEvent(LogicResponseData data) {
        LogUtils.i(this.CLASS_NAME, "step[%d] %s", Integer.valueOf(this.mStep), data);
        if (this.mStep == 10003) {
            String response = new Gson().toJson(data);
            if (TextUtils.isEmpty(data.getMsgId())) {
                this.mStep = 0;
                responseString(this.mMqttCmd, "", response);
                return;
            }
            this.mStep = 1004;
            this.mMsgId = data.getMsgId();
            responseInterActive(this.mMqttCmd, response);
        }
    }

    public MqttLogicTree(Context context, CmdResponser responser) {
        super(context, responser);
        this.mStep = 0;
        this.mMsgId = null;
        this.CLASS_NAME = "MqttLogicTree";
    }

    @Override // com.xiaopeng.cardiagnosis.mqttcmd.MqttCmdHandler
    public void init() {
        super.init();
        EventBusUtil.registerEventBus(this);
        this.mLogicTreeParser = new LogicTreeParser();
        this.mLogicTreeParser.registerLogicActionHandler(CarApplication.getApplication());
        this.mLogicTreeParser.initLogicTreeList();
    }

    /* JADX WARN: Can't fix incorrect switch cases order, some code will duplicate */
    @Override // com.xiaopeng.cardiagnosis.mqttcmd.MqttCmdHandler
    public synchronized boolean handleCommand(MqttMsgBase<MqttAfterSalesCmd> cmd) {
        if (cmd.getMsgContent().getCmd_type() == null) {
            LogUtils.e(this.CLASS_NAME, "cmd type is null");
            responseNoCmdType(cmd);
            return false;
        }
        String cmdType = AESUtils.decrypt(cmd.getMsgContent().getCmd_type(), AfterSalesHelper.getAfterSalesManager().getAuthPass());
        LogUtils.i(this.CLASS_NAME, "cmdType[%s] getCmd_type[%s] getAuthPass[%s]", cmdType, cmd.getMsgContent().getCmd_type(), AfterSalesHelper.getAfterSalesManager().getAuthPass());
        if (cmdType == null) {
            responseNoCmdType(cmd);
            return false;
        }
        char c = 65535;
        switch (cmdType.hashCode()) {
            case 1686170:
                if (cmdType.equals(MQTT_CMD_TYPE_EXEC_LOGIC_TREE)) {
                    c = 0;
                    break;
                }
                break;
            case 1686171:
                if (cmdType.equals(MQTT_CMD_TYPE_INTERACTIVE)) {
                    c = 1;
                    break;
                }
                break;
        }
        if (c == 0) {
            if (this.mStep == 0 && this.mLogicTreeParser.isDiagnosisFinish()) {
                if (this.mLogicTreeParser.getLogicTreeInfoList() == null) {
                    responseLogicTreeInitFail(cmd);
                } else {
                    this.mMqttCmd = cmd;
                    this.mParamLogicTree = (ParamLogicTree) new Gson().fromJson(cmd.getMsgContent().getCmd_param(), (Class<Object>) ParamLogicTree.class);
                    this.mLogicTreeParser.getIssueInfo().setEntry(1);
                    this.mLogicTreeParser.setCloudVersion(this.mParamLogicTree.getDownloadPath(), this.mParamLogicTree.getVersion());
                    if (this.mLogicTreeParser.isLogicTreeNeedUpdate()) {
                        this.mStep = 10002;
                        this.mLogicTreeParser.upgradeLogicTreeViaCloud(CarApplication.getApplication());
                    } else {
                        execLogicTreeDiagnosis();
                    }
                }
            }
            responseLastDiagnosisNoFinish(cmd);
        } else if (c == 1) {
            LogicInterActiveData data = (LogicInterActiveData) new Gson().fromJson(cmd.getMsgContent().getCmd_param(), (Class<Object>) LogicInterActiveData.class);
            if (this.mStep == 1004 && this.mMsgId != null && this.mMsgId.equalsIgnoreCase(data.getMsgId())) {
                this.mMqttCmd = cmd;
                this.mStep = 10003;
                this.mMsgId = null;
                LogicTreeHelper.responseResult(data.getOption());
            } else {
                LogUtils.e(this.CLASS_NAME, "mStep[%d] mMsgId[%s] data.getMsgId()[%s]", Integer.valueOf(this.mStep), this.mMsgId, data.getMsgId());
                responseLogicActionMisMatch(cmd);
            }
        } else {
            responseNoCmdType(cmd);
        }
        return true;
    }

    private void execLogicTreeDiagnosis() {
        LogicTreeInfo info = this.mLogicTreeParser.getLogicTreeInfoByName(this.mParamLogicTree.getIssueName());
        if (info == null) {
            responseNoSuchLogicTree(this.mMqttCmd);
            return;
        }
        this.mStep = 10003;
        this.mLogicTreeParser.process(info, this.mParamLogicTree.getStartTime(), this.mParamLogicTree.getEndTime(), 1);
    }

    @Override // com.xiaopeng.cardiagnosis.mqttcmd.MqttCmdHandler
    public void destroy() {
        super.destroy();
        this.mLogicTreeParser.unregisterLogicActionHandler();
        EventBusUtil.unregisterEventBus(this);
        this.mStep = 0;
        this.mLogicTreeParser.forceFinishDiagnosis();
    }
}
