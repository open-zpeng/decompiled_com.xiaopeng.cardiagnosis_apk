package com.xiaopeng.cardiagnosis.mqttcmd;

import android.content.Context;
import com.xiaopeng.aftersales.manager.RepairModeListener;
import com.xiaopeng.commonfunc.bean.MqttAfterSalesCmd;
import com.xiaopeng.commonfunc.utils.AfterSalesHelper;
import com.xiaopeng.commonfunc.utils.OTAServiceHelper;
import com.xiaopeng.commonfunc.utils.SystemPropertyUtil;
import com.xiaopeng.lib.utils.LogUtils;
import com.xiaopeng.libconfig.ipc.bean.MqttMsgBase;
import com.xiaopeng.xmlconfig.Support;
/* loaded from: classes4.dex */
public class MqttCheckMode extends MqttCmdHandler {
    private static final String CHECK_MODE_KEY_FOLDER = "/cache/aftersales";
    private static final String KEY_JOB_NUMBER = "jobnum";
    private static final String KEY_VIN = "vin";
    private static final String MQTT_CMD_TYPE_ENTER_CHECK_MODE = "6001";
    private static final String MQTT_CMD_TYPE_QUIT_CHECK_MODE = "6002";
    private static final String STEP_DEFAULT = "0";
    private static final String SUFFIX_CHECKMODE_KEY = ".xpkey";
    private static final boolean SUPPORT_SPEED_LIMIT = Support.Feature.getBoolean(Support.Feature.SUPPORT_SPEED_LIMIT);
    private RepairModeListener mRepairModeListener;
    private String mStep;

    public MqttCheckMode(Context context, CmdResponser responser) {
        super(context, responser);
        this.mStep = "0";
        this.mRepairModeListener = new RepairModeListener() { // from class: com.xiaopeng.cardiagnosis.mqttcmd.-$$Lambda$MqttCheckMode$RzZLiM8xqveuitfQxxUamA0GwA4
            public final void onRepairModeChanged(boolean z, int i) {
                MqttCheckMode.this.lambda$new$0$MqttCheckMode(z, i);
            }
        };
        this.CLASS_NAME = "MqttCheckMode";
    }

    @Override // com.xiaopeng.cardiagnosis.mqttcmd.MqttCmdHandler
    public void init() {
        super.init();
        AfterSalesHelper.getAfterSalesManager().registerRepairModeListener(this.mRepairModeListener);
    }

    public /* synthetic */ void lambda$new$0$MqttCheckMode(boolean onoff, int switchResult) {
        if (switchResult == 0) {
            if (MQTT_CMD_TYPE_ENTER_CHECK_MODE.equalsIgnoreCase(this.mStep) || MQTT_CMD_TYPE_QUIT_CHECK_MODE.equalsIgnoreCase(this.mStep)) {
                this.mStep = "0";
                this.mCmdResponser.responseNG(this.mMqttCmd);
            }
        } else if (switchResult == 1) {
            if (MQTT_CMD_TYPE_ENTER_CHECK_MODE.equalsIgnoreCase(this.mStep) || MQTT_CMD_TYPE_QUIT_CHECK_MODE.equalsIgnoreCase(this.mStep)) {
                this.mStep = "0";
                this.mCmdResponser.responseOK(this.mMqttCmd, "");
            }
        }
    }

    /* JADX WARN: Can't fix incorrect switch cases order, some code will duplicate */
    @Override // com.xiaopeng.cardiagnosis.mqttcmd.MqttCmdHandler
    public synchronized boolean handleCommand(MqttMsgBase<MqttAfterSalesCmd> cmd) {
        boolean z = false;
        if (super.handleCommand(cmd)) {
            String jobNum = cmd.getMsgContent().getCmd_param();
            LogUtils.d(this.CLASS_NAME, "jobNum: " + jobNum);
            String cmd_type = cmd.getMsgContent().getCmd_type();
            switch (cmd_type.hashCode()) {
                case 1656379:
                    if (cmd_type.equals(MQTT_CMD_TYPE_ENTER_CHECK_MODE)) {
                        break;
                    }
                    z = true;
                    break;
                case 1656380:
                    if (cmd_type.equals(MQTT_CMD_TYPE_QUIT_CHECK_MODE)) {
                        z = true;
                        break;
                    }
                    z = true;
                    break;
                default:
                    z = true;
                    break;
            }
            if (!z) {
                this.mStep = MQTT_CMD_TYPE_ENTER_CHECK_MODE;
                if (AfterSalesHelper.getAfterSalesManager().getRepairMode()) {
                    this.mStep = "0";
                    this.mCmdResponser.responseOK(this.mMqttCmd, "");
                } else if (!SUPPORT_SPEED_LIMIT) {
                    AfterSalesHelper.getAfterSalesManager().enableRepairMode();
                } else {
                    AfterSalesHelper.getAfterSalesManager().enableRepairModeWithKeyId(jobNum);
                }
            } else if (z) {
                this.mStep = MQTT_CMD_TYPE_QUIT_CHECK_MODE;
                if (!AfterSalesHelper.getAfterSalesManager().getRepairMode()) {
                    this.mStep = "0";
                    this.mCmdResponser.responseOK(this.mMqttCmd, "");
                } else if (!SUPPORT_SPEED_LIMIT) {
                    AfterSalesHelper.getAfterSalesManager().disableRepairMode();
                } else if (SystemPropertyUtil.getIGONsetLimit()) {
                    LogUtils.e(this.CLASS_NAME, "is igon set limit");
                    this.mCmdResponser.responseNG(this.mMqttCmd);
                } else {
                    int vcuMode = OTAServiceHelper.getVcuMode();
                    LogUtils.d(this.CLASS_NAME, "vcu mode: " + vcuMode + ", speed limit mode: " + AfterSalesHelper.getAfterSalesManager().getSpeedLimitMode());
                    if (vcuMode != 2) {
                        if (OTAServiceHelper.setVcuModel(2, OTAServiceHelper.OTHER)) {
                            LogUtils.d(this.CLASS_NAME, "now vcu in normal mode");
                            if (AfterSalesHelper.getAfterSalesManager().getSpeedLimitMode()) {
                                AfterSalesHelper.getAfterSalesManager().recordSpeedLimitOff();
                            }
                            AfterSalesHelper.getAfterSalesManager().disableRepairMode();
                        } else {
                            LogUtils.e(this.CLASS_NAME, "set vcu normal mode fail");
                            this.mCmdResponser.responseNG(this.mMqttCmd);
                        }
                    } else {
                        if (AfterSalesHelper.getAfterSalesManager().getSpeedLimitMode()) {
                            AfterSalesHelper.getAfterSalesManager().recordSpeedLimitOff();
                        }
                        AfterSalesHelper.getAfterSalesManager().disableRepairMode();
                    }
                }
            } else {
                responseNoCmdType(cmd);
            }
            return true;
        }
        return false;
    }

    @Override // com.xiaopeng.cardiagnosis.mqttcmd.MqttCmdHandler
    public void destroy() {
        AfterSalesHelper.getAfterSalesManager().unregisterRepairModeListener(this.mRepairModeListener);
        super.destroy();
    }
}
