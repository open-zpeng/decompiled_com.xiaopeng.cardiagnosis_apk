package com.xiaopeng.cardiagnosis.mqttcmd;

import android.content.Context;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import com.xiaopeng.commonfunc.bean.MqttAfterSalesCmd;
import com.xiaopeng.commonfunc.model.car.VcuModel;
import com.xiaopeng.commonfunc.utils.AfterSalesHelper;
import com.xiaopeng.lib.utils.LogUtils;
import com.xiaopeng.libconfig.ipc.bean.MqttMsgBase;
import java.util.HashMap;
/* loaded from: classes4.dex */
public class CmdParser extends Handler {
    private static final int EVENT_WAIT_CMD_TIMEOUT = 200001;
    private static final int MQTT_SERVICE_TYPE_AUTHMODE = 2005;
    private static final int MQTT_SERVICE_TYPE_CHECKMODE = 2006;
    private static final int MQTT_SERVICE_TYPE_CLEARAPPDATA = 2003;
    private static final int MQTT_SERVICE_TYPE_EXECSH = 2000;
    private static final int MQTT_SERVICE_TYPE_LOGICTREE = 2007;
    private static final int MQTT_SERVICE_TYPE_REBOOT = 2002;
    private static final int MQTT_SERVICE_TYPE_RESET = 2001;
    public static final String TAG = "CmdParser";
    private static final long WAITTIME_FOR_CMD = 1800000;
    private final HashMap<Integer, HandlerCombination> mCmdHandlers;
    private boolean mIsCmdHandlerInit;
    private final VcuModel mVcuModel;

    public CmdParser(Context context, CmdResponser responser, Looper looper) {
        super(looper);
        this.mCmdHandlers = new HashMap<>();
        this.mVcuModel = new VcuModel(TAG);
        registerCmdHandler(context, responser);
        this.mIsCmdHandlerInit = false;
    }

    @Override // android.os.Handler
    public void handleMessage(Message msg) {
        if (msg.what == 200001 && this.mIsCmdHandlerInit) {
            deinitCmdHandler();
        }
    }

    private void registerCmdHandler(Context context, CmdResponser responser) {
        LogUtils.d(TAG, "Register command handler");
        this.mCmdHandlers.put(2000, new HandlerCombination(new MqttEncryptSh(context, responser), 600000L, true));
        this.mCmdHandlers.put(2002, new HandlerCombination(new MqttReboot(context, responser), 60000L, true));
        this.mCmdHandlers.put(2001, new HandlerCombination(new MqttReset(context, responser), 60000L, true));
        this.mCmdHandlers.put(2003, new HandlerCombination(new MqttClearAppData(context, responser), 120000L, true));
        this.mCmdHandlers.put(Integer.valueOf((int) MQTT_SERVICE_TYPE_AUTHMODE), new HandlerCombination(new MqttAuth(context, responser), 60000L, true));
        this.mCmdHandlers.put(Integer.valueOf((int) MQTT_SERVICE_TYPE_CHECKMODE), new HandlerCombination(new MqttCheckMode(context, responser), 60000L, false));
        this.mCmdHandlers.put(Integer.valueOf((int) MQTT_SERVICE_TYPE_LOGICTREE), new HandlerCombination(new MqttLogicTree(context, responser), WAITTIME_FOR_CMD, true));
    }

    private void initCmdHandler() {
        LogUtils.d(TAG, "initCmdHandler");
        for (HandlerCombination handler : this.mCmdHandlers.values()) {
            handler.getHandler().init();
        }
        this.mIsCmdHandlerInit = true;
    }

    private void deinitCmdHandler() {
        LogUtils.e(TAG, "deinitCmdHandler");
        for (HandlerCombination handler : this.mCmdHandlers.values()) {
            handler.getHandler().destroy();
        }
        this.mIsCmdHandlerInit = false;
    }

    public void runCmd(MqttMsgBase<MqttAfterSalesCmd> cmd, CmdResponser responser) {
        LogUtils.d(TAG, "runCmd : " + cmd);
        responser.setmCmd(cmd);
        HandlerCombination handlerCombination = this.mCmdHandlers.get(Integer.valueOf(cmd.getServiceType()));
        if (handlerCombination != null) {
            if (!this.mVcuModel.isUnderLevelP()) {
                responser.responseNotPLevel(cmd);
                return;
            } else if (handlerCombination.isNeedAuthMode() && !AfterSalesHelper.getAfterSalesManager().getAuthMode()) {
                responser.responseNeedAuth(cmd);
                return;
            } else {
                MqttCmdHandler handler = handlerCombination.getHandler();
                if (handler != null) {
                    if (!this.mIsCmdHandlerInit) {
                        initCmdHandler();
                    }
                    removeMessages(200001);
                    sendEmptyMessageDelayed(200001, WAITTIME_FOR_CMD);
                    responser.setTimeOut(cmd, handlerCombination.getTimeout());
                    handler.handleCommand(cmd);
                    return;
                }
                responser.responseNA(cmd);
                return;
            }
        }
        responser.responseNA(cmd);
    }
}
