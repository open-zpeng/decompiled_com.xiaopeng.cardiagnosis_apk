package com.xiaopeng.cardiagnosis.mqttcmd;

import android.content.Context;
import android.content.Intent;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.xiaopeng.cardiagnosis.runnable.Sleep;
import com.xiaopeng.commonfunc.bean.MqttAfterSalesCmd;
import com.xiaopeng.commonfunc.utils.AfterSalesHelper;
import com.xiaopeng.commonfunc.utils.FileUtil;
import com.xiaopeng.lib.utils.ThreadUtils;
import com.xiaopeng.lib.utils.crypt.AESUtils;
import com.xiaopeng.libconfig.ipc.bean.MqttMsgBase;
/* loaded from: classes4.dex */
public class MqttReset extends MqttCmdHandler {
    private static final String MQTT_CMD_TYPE_RESET = "1001";
    private static final String PATH_AFTERSALES_MQTT_RESET_TAG = "/mnt/vmap/aftersales_diag/mqtt_reset";

    public MqttReset(Context context, final CmdResponser responser) {
        super(context, responser);
        this.CLASS_NAME = "MqttReset";
        ThreadUtils.execute(new Runnable() { // from class: com.xiaopeng.cardiagnosis.mqttcmd.-$$Lambda$MqttReset$K-AfJF62hVIaXbYzbZKWyxZAQ0k
            @Override // java.lang.Runnable
            public final void run() {
                MqttReset.this.lambda$new$0$MqttReset(responser);
            }
        });
    }

    public /* synthetic */ void lambda$new$0$MqttReset(CmdResponser responser) {
        if (FileUtil.isExistFilePath(PATH_AFTERSALES_MQTT_RESET_TAG)) {
            MqttMsgBase<MqttAfterSalesCmd> cmd = (MqttMsgBase) new Gson().fromJson(FileUtil.read(PATH_AFTERSALES_MQTT_RESET_TAG), new TypeToken<MqttMsgBase<MqttAfterSalesCmd>>() { // from class: com.xiaopeng.cardiagnosis.mqttcmd.MqttReset.1
            }.getType());
            responser.responseOK_SPEC(cmd, "");
            FileUtil.deleteFile(PATH_AFTERSALES_MQTT_RESET_TAG);
        }
    }

    @Override // com.xiaopeng.cardiagnosis.mqttcmd.MqttCmdHandler
    public void init() {
        super.init();
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
            if (cmdType.hashCode() != 1507424 || !cmdType.equals(MQTT_CMD_TYPE_RESET)) {
                z = true;
            }
            if (!z) {
                FileUtil.writeNCreateFile(PATH_AFTERSALES_MQTT_RESET_TAG, new Gson().toJson(cmd));
                Sleep.sleep(1000L);
                Intent clearIntent = new Intent("android.intent.action.MASTER_CLEAR");
                clearIntent.addFlags(16777216);
                this.context.sendBroadcast(clearIntent);
            } else {
                responseNoCmdType(cmd);
            }
            return true;
        }
        return false;
    }

    @Override // com.xiaopeng.cardiagnosis.mqttcmd.MqttCmdHandler
    public void destroy() {
        super.destroy();
    }
}
