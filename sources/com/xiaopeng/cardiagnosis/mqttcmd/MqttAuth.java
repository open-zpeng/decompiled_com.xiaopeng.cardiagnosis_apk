package com.xiaopeng.cardiagnosis.mqttcmd;

import android.content.Context;
import com.xiaopeng.aftersales.manager.AuthModeListener;
import com.xiaopeng.commonfunc.bean.MqttAfterSalesCmd;
import com.xiaopeng.commonfunc.utils.AfterSalesHelper;
import com.xiaopeng.lib.utils.crypt.AESUtils;
import com.xiaopeng.libconfig.ipc.bean.MqttMsgBase;
/* loaded from: classes4.dex */
public class MqttAuth extends MqttCmdHandler {
    private static final String MQTT_CMD_TYPE_QUIT_AUTH = "5001";
    private final AuthModeListener mAuthModeListener;

    public /* synthetic */ void lambda$new$0$MqttAuth(boolean onoff, int switchResult) {
        if (switchResult == 1 && !onoff) {
            this.mCmdResponser.responseOK(this.mMqttCmd, "");
        }
    }

    public MqttAuth(Context context, CmdResponser responser) {
        super(context, responser);
        this.mAuthModeListener = new AuthModeListener() { // from class: com.xiaopeng.cardiagnosis.mqttcmd.-$$Lambda$MqttAuth$xNee-A8P0_KSBCbu_XuWqlAHGSc
            public final void onAuthModeChanged(boolean z, int i) {
                MqttAuth.this.lambda$new$0$MqttAuth(z, i);
            }
        };
        this.CLASS_NAME = "MqttAuth";
    }

    @Override // com.xiaopeng.cardiagnosis.mqttcmd.MqttCmdHandler
    public void init() {
        super.init();
        AfterSalesHelper.getAfterSalesManager().registerAuthModeListener(this.mAuthModeListener);
    }

    @Override // com.xiaopeng.cardiagnosis.mqttcmd.MqttCmdHandler
    public synchronized boolean handleCommand(MqttMsgBase<MqttAfterSalesCmd> cmd) {
        boolean z = false;
        if (super.handleCommand(cmd)) {
            String cmdType = AESUtils.decrypt(cmd.getMsgContent().getCmd_type(), AfterSalesHelper.getAfterSalesManager().getAuthPass());
            if (cmdType == null) {
                responseNoCmdType(cmd);
                return false;
            }
            if (cmdType.hashCode() != 1626588 || !cmdType.equals(MQTT_CMD_TYPE_QUIT_AUTH)) {
                z = true;
            }
            if (!z) {
                AfterSalesHelper.getAfterSalesManager().disableAuthMode();
            } else {
                responseNoCmdType(cmd);
            }
            return true;
        }
        return false;
    }

    @Override // com.xiaopeng.cardiagnosis.mqttcmd.MqttCmdHandler
    public void destroy() {
        AfterSalesHelper.getAfterSalesManager().unregisterAuthModeListener(this.mAuthModeListener);
        super.destroy();
    }
}
