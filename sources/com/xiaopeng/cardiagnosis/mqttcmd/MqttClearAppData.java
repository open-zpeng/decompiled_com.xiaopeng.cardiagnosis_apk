package com.xiaopeng.cardiagnosis.mqttcmd;

import android.app.ActivityManager;
import android.content.Context;
import android.content.pm.IPackageDataObserver;
import com.google.gson.Gson;
import com.xiaopeng.commonfunc.bean.MqttAfterSalesCmd;
import com.xiaopeng.commonfunc.bean.ParamClearApp;
import com.xiaopeng.commonfunc.utils.AfterSalesHelper;
import com.xiaopeng.lib.utils.LogUtils;
import com.xiaopeng.lib.utils.crypt.AESUtils;
import com.xiaopeng.libconfig.ipc.bean.MqttMsgBase;
/* loaded from: classes4.dex */
public class MqttClearAppData extends MqttCmdHandler {
    private static final String MQTT_CMD_TYPE_CLEAR_APP_DATA = "3001";
    private ClearUserDataObserver mClearDataObserver;

    public MqttClearAppData(Context context, CmdResponser responser) {
        super(context, responser);
        this.CLASS_NAME = "MqttClearAppData";
    }

    @Override // com.xiaopeng.cardiagnosis.mqttcmd.MqttCmdHandler
    public void init() {
        super.init();
        this.mClearDataObserver = new ClearUserDataObserver();
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
            if (cmdType.hashCode() != 1567006 || !cmdType.equals(MQTT_CMD_TYPE_CLEAR_APP_DATA)) {
                z = true;
            }
            if (!z) {
                ParamClearApp param = (ParamClearApp) new Gson().fromJson(cmd.getMsgContent().getCmd_param(), (Class<Object>) ParamClearApp.class);
                boolean res = false;
                try {
                    ActivityManager am = (ActivityManager) this.context.getSystemService("activity");
                    res = am.clearApplicationUserData(param.getPackage_name(), this.mClearDataObserver);
                } catch (Exception e) {
                    e.printStackTrace();
                }
                if (!res) {
                    responseNG(cmd);
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
        this.mClearDataObserver = null;
        super.destroy();
    }

    /* loaded from: classes4.dex */
    class ClearUserDataObserver extends IPackageDataObserver.Stub {
        ClearUserDataObserver() {
        }

        public void onRemoveCompleted(String packageName, boolean succeeded) {
            String str = MqttClearAppData.this.CLASS_NAME;
            LogUtils.i(str, "ClearUserDataObserver onRemoveCompleted packageName:" + packageName + ", succeeded:" + succeeded);
            ParamClearApp param = (ParamClearApp) new Gson().fromJson(MqttClearAppData.this.mMqttCmd.getMsgContent().getCmd_param(), (Class<Object>) ParamClearApp.class);
            if (packageName.equals(param.getPackage_name())) {
                if (succeeded) {
                    MqttClearAppData mqttClearAppData = MqttClearAppData.this;
                    mqttClearAppData.responseOK(mqttClearAppData.mMqttCmd, "");
                    return;
                }
                MqttClearAppData mqttClearAppData2 = MqttClearAppData.this;
                mqttClearAppData2.responseNG(mqttClearAppData2.mMqttCmd);
            }
        }
    }
}
