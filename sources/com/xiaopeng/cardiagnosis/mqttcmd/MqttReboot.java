package com.xiaopeng.cardiagnosis.mqttcmd;

import android.content.Context;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.xiaopeng.cardiagnosis.runnable.Sleep;
import com.xiaopeng.commonfunc.bean.MqttAfterSalesCmd;
import com.xiaopeng.commonfunc.utils.AfterSalesHelper;
import com.xiaopeng.commonfunc.utils.FileUtil;
import com.xiaopeng.commonfunc.utils.ProcessUtil;
import com.xiaopeng.lib.utils.ThreadUtils;
import com.xiaopeng.lib.utils.crypt.AESUtils;
import com.xiaopeng.libconfig.ipc.bean.MqttMsgBase;
/* loaded from: classes4.dex */
public class MqttReboot extends MqttCmdHandler {
    private static final String MQTT_CMD_TYPE_REBOOT = "2001";
    private static final String PATH_AFTERSALES_MQTT_REBOOT_TAG = "/mnt/vmap/aftersales_diag/mqtt_reboot";
    private static final String REASON_MQTT_REBOOT = "Diagnostic platform request reboot";

    public MqttReboot(Context context, final CmdResponser responser) {
        super(context, responser);
        this.CLASS_NAME = "MqttReboot";
        ThreadUtils.execute(new Runnable() { // from class: com.xiaopeng.cardiagnosis.mqttcmd.-$$Lambda$MqttReboot$njOb4OCmI-GDV5sJpJD8eBs7DOc
            @Override // java.lang.Runnable
            public final void run() {
                MqttReboot.this.lambda$new$0$MqttReboot(responser);
            }
        });
    }

    public /* synthetic */ void lambda$new$0$MqttReboot(CmdResponser responser) {
        if (FileUtil.isExistFilePath(PATH_AFTERSALES_MQTT_REBOOT_TAG)) {
            MqttMsgBase<MqttAfterSalesCmd> cmd = (MqttMsgBase) new Gson().fromJson(FileUtil.read(PATH_AFTERSALES_MQTT_REBOOT_TAG), new TypeToken<MqttMsgBase<MqttAfterSalesCmd>>() { // from class: com.xiaopeng.cardiagnosis.mqttcmd.MqttReboot.1
            }.getType());
            responser.responseOK_SPEC(cmd, "");
            FileUtil.deleteFile(PATH_AFTERSALES_MQTT_REBOOT_TAG);
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
            if (cmdType.hashCode() != 1537215 || !cmdType.equals(MQTT_CMD_TYPE_REBOOT)) {
                z = true;
            }
            if (!z) {
                FileUtil.writeNCreateFile(PATH_AFTERSALES_MQTT_REBOOT_TAG, new Gson().toJson(cmd));
                Sleep.sleep(1000L);
                ProcessUtil.reboot(this.context, REASON_MQTT_REBOOT);
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
