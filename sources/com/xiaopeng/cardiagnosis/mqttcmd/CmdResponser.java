package com.xiaopeng.cardiagnosis.mqttcmd;

import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.xiaopeng.cardiagnosis.CarApplication;
import com.xiaopeng.cardiagnosis.IpcRouterService;
import com.xiaopeng.cardiagnosis.R;
import com.xiaopeng.commonfunc.bean.MqttAfterSalesCmd;
import com.xiaopeng.commonfunc.bean.MqttAfterSalesResult;
import com.xiaopeng.commonfunc.utils.SystemPropertyUtil;
import com.xiaopeng.lib.utils.LogUtils;
import com.xiaopeng.libconfig.ipc.IpcConfig;
import com.xiaopeng.libconfig.ipc.bean.MqttContentBase;
import com.xiaopeng.libconfig.ipc.bean.MqttMsgBase;
/* loaded from: classes4.dex */
public class CmdResponser extends Handler {
    private static final int EVENT_RESPONSE_TIMEOUT = 100001;
    private static final int RESPONSE_TYPE_INTERACTIVE = 2;
    private static final String TAG = "CmdResponser";
    private MqttMsgBase<MqttAfterSalesCmd> mCmd;
    private Context mContext;

    public CmdResponser(Context context, Looper looper) {
        super(looper);
        this.mContext = context;
    }

    @Override // android.os.Handler
    public void handleMessage(Message msg) {
        MqttMsgBase<MqttAfterSalesCmd> mqttMsgBase;
        if (msg.what == 100001 && (mqttMsgBase = this.mCmd) != null) {
            responseTimeout(mqttMsgBase);
        }
    }

    public boolean isWaitingResponse() {
        return hasMessages(100001);
    }

    public void setTimeOut(MqttMsgBase<MqttAfterSalesCmd> cmd, long timeout) {
        sendEmptyMessageDelayed(100001, timeout);
    }

    public void setmCmd(MqttMsgBase<MqttAfterSalesCmd> cmd) {
        this.mCmd = cmd;
    }

    public void responseReciveCmd(MqttMsgBase<MqttAfterSalesCmd> cmd) {
        MqttContentBase<MqttAfterSalesResult> result = new MqttContentBase<>();
        result.setCode(200);
        result.setRespType(1);
        sendDiagResponse2Mqtt(cmd, result, false);
    }

    public void responseInterActive(MqttMsgBase<MqttAfterSalesCmd> cmd, String value) {
        MqttContentBase<MqttAfterSalesResult> result = new MqttContentBase<>();
        result.setCode(200);
        result.setRespType(2);
        MqttAfterSalesResult res = new MqttAfterSalesResult(1, "", value, "");
        result.setData(res);
        sendDiagResponse2Mqtt(cmd, result, true);
    }

    public void responseString(MqttMsgBase<MqttAfterSalesCmd> cmd, String ossPath, String value) {
        MqttContentBase<MqttAfterSalesResult> result = new MqttContentBase<>();
        result.setCode(200);
        result.setRespType(99);
        MqttAfterSalesResult res = new MqttAfterSalesResult(1, ossPath, value, "");
        result.setData(res);
        sendDiagResponse2Mqtt(cmd, result, true);
    }

    public void responseOK(MqttMsgBase<MqttAfterSalesCmd> cmd, String ossPath) {
        MqttContentBase<MqttAfterSalesResult> result = new MqttContentBase<>();
        result.setCode(200);
        result.setRespType(99);
        MqttAfterSalesResult res = new MqttAfterSalesResult(1, ossPath, "", "");
        result.setData(res);
        sendDiagResponse2Mqtt(cmd, result, true);
    }

    public void responseOK_SPEC(MqttMsgBase<MqttAfterSalesCmd> cmd, String ossPath) {
        MqttContentBase<MqttAfterSalesResult> result = new MqttContentBase<>();
        result.setCode(200);
        result.setRespType(99);
        MqttAfterSalesResult res = new MqttAfterSalesResult(1, ossPath, "", "");
        result.setData(res);
        sendDiagResponse2Mqtt(cmd, result, false);
    }

    public void responseNG(MqttMsgBase<MqttAfterSalesCmd> cmd) {
        MqttContentBase<MqttAfterSalesResult> result = new MqttContentBase<>();
        result.setCode(200);
        result.setRespType(99);
        MqttAfterSalesResult res = new MqttAfterSalesResult(2, "", "", this.mContext.getString(R.string.mqtt_operation_fail));
        result.setData(res);
        sendDiagResponse2Mqtt(cmd, result, true);
    }

    public void responseNA(MqttMsgBase<MqttAfterSalesCmd> cmd) {
        MqttContentBase<MqttAfterSalesResult> result = new MqttContentBase<>();
        result.setCode(200);
        result.setRespType(99);
        MqttAfterSalesResult res = new MqttAfterSalesResult(3, "", "", this.mContext.getString(R.string.mqtt_operation_unsupported));
        result.setData(res);
        sendDiagResponse2Mqtt(cmd, result, true);
    }

    public void responseNeedAuth(MqttMsgBase<MqttAfterSalesCmd> cmd) {
        MqttContentBase<MqttAfterSalesResult> result = new MqttContentBase<>();
        result.setCode(200);
        result.setRespType(99);
        MqttAfterSalesResult res = new MqttAfterSalesResult(4, "", "", this.mContext.getString(R.string.mqtt_operation_need_authmode));
        result.setData(res);
        sendDiagResponse2Mqtt(cmd, result, true);
    }

    public void responseNotPLevel(MqttMsgBase<MqttAfterSalesCmd> cmd) {
        MqttContentBase<MqttAfterSalesResult> result = new MqttContentBase<>();
        result.setCode(200);
        result.setRespType(99);
        MqttAfterSalesResult res = new MqttAfterSalesResult(5, "", "", this.mContext.getString(R.string.mqtt_operation_not_under_p_level));
        result.setData(res);
        sendDiagResponse2Mqtt(cmd, result, true);
    }

    public void responseTimeout(MqttMsgBase<MqttAfterSalesCmd> cmd) {
        MqttContentBase<MqttAfterSalesResult> result = new MqttContentBase<>();
        result.setCode(200);
        result.setRespType(99);
        MqttAfterSalesResult res = new MqttAfterSalesResult(6, "", "", this.mContext.getString(R.string.mqtt_operation_timeout));
        result.setData(res);
        sendDiagResponse2Mqtt(cmd, result, true);
    }

    public void responseNoCmdType(MqttMsgBase<MqttAfterSalesCmd> cmd) {
        MqttContentBase<MqttAfterSalesResult> result = new MqttContentBase<>();
        result.setCode(200);
        result.setRespType(99);
        MqttAfterSalesResult res = new MqttAfterSalesResult(7, "", "", this.mContext.getString(R.string.mqtt_operation_find_cmd_type_fail));
        result.setData(res);
        sendDiagResponse2Mqtt(cmd, result, true);
    }

    public void responseUploadOssFail(MqttMsgBase<MqttAfterSalesCmd> cmd) {
        MqttContentBase<MqttAfterSalesResult> result = new MqttContentBase<>();
        result.setCode(200);
        result.setRespType(99);
        MqttAfterSalesResult res = new MqttAfterSalesResult(8, "", "", this.mContext.getString(R.string.mqtt_operation_fail_upload_to_oss));
        result.setData(res);
        sendDiagResponse2Mqtt(cmd, result, true);
    }

    public void responseVinMismatch(MqttMsgBase<MqttAfterSalesCmd> cmd) {
        MqttContentBase<MqttAfterSalesResult> result = new MqttContentBase<>();
        result.setCode(200);
        result.setRespType(99);
        MqttAfterSalesResult res = new MqttAfterSalesResult(9, "", "", this.mContext.getString(R.string.mqtt_operation_vin_mismatch));
        result.setData(res);
        sendDiagResponse2Mqtt(cmd, result, true);
    }

    public void responseUpgradeLogicTreeFail(MqttMsgBase<MqttAfterSalesCmd> cmd) {
        MqttContentBase<MqttAfterSalesResult> result = new MqttContentBase<>();
        result.setCode(200);
        result.setRespType(99);
        MqttAfterSalesResult res = new MqttAfterSalesResult(10, "", "", this.mContext.getString(R.string.mqtt_operation_fail_upgrade_logictree));
        result.setData(res);
        sendDiagResponse2Mqtt(cmd, result, true);
    }

    public void responseLogicActionMisMatch(MqttMsgBase<MqttAfterSalesCmd> cmd) {
        MqttContentBase<MqttAfterSalesResult> result = new MqttContentBase<>();
        result.setCode(200);
        result.setRespType(99);
        MqttAfterSalesResult res = new MqttAfterSalesResult(11, "", "", this.mContext.getString(R.string.mqtt_operation_logic_msgid_mismatch));
        result.setData(res);
        sendDiagResponse2Mqtt(cmd, result, true);
    }

    public void responseNoSuchLogicTree(MqttMsgBase<MqttAfterSalesCmd> cmd) {
        MqttContentBase<MqttAfterSalesResult> result = new MqttContentBase<>();
        result.setCode(200);
        result.setRespType(99);
        MqttAfterSalesResult res = new MqttAfterSalesResult(12, "", "", this.mContext.getString(R.string.mqtt_operation_no_such_logic_tree));
        result.setData(res);
        sendDiagResponse2Mqtt(cmd, result, true);
    }

    public void responseLastDiagnosisNoFinish(MqttMsgBase<MqttAfterSalesCmd> cmd) {
        MqttContentBase<MqttAfterSalesResult> result = new MqttContentBase<>();
        result.setCode(200);
        result.setRespType(99);
        MqttAfterSalesResult res = new MqttAfterSalesResult(13, "", "", this.mContext.getString(R.string.mqtt_operation_last_diagnosis_not_finish));
        result.setData(res);
        sendDiagResponse2Mqtt(cmd, result, true);
    }

    public void responseLogicTreeInitFail(MqttMsgBase<MqttAfterSalesCmd> cmd) {
        MqttContentBase<MqttAfterSalesResult> result = new MqttContentBase<>();
        result.setCode(200);
        result.setRespType(99);
        MqttAfterSalesResult res = new MqttAfterSalesResult(14, "", "", this.mContext.getString(R.string.mqtt_operation_logictree_init_fail));
        result.setData(res);
        sendDiagResponse2Mqtt(cmd, result, true);
    }

    private synchronized void sendDiagResponse2Mqtt(MqttMsgBase<MqttAfterSalesCmd> cmd, MqttContentBase<MqttAfterSalesResult> result, boolean toRmTimeout) {
        if (toRmTimeout) {
            if (!cmd.equals(this.mCmd)) {
                LogUtils.e(TAG, "targetCmd : " + this.mCmd + "currentCmd : " + cmd);
                return;
            }
            this.mCmd = null;
            removeMessages(100001);
        }
        MqttMsgBase<MqttContentBase<MqttAfterSalesResult>> response = new MqttMsgBase<>(SystemPropertyUtil.getHardwareId());
        response.setMsgType(1);
        response.setMsgRef(cmd.getMsgId());
        response.setServiceType(cmd.getServiceType());
        response.setMsgContent(result);
        Bundle bundle = new Bundle();
        String res = new Gson().toJson(response);
        bundle.putString(IpcConfig.IPCKey.STRING_MSG, res);
        CarApplication.getIPCService().sendData(IpcConfig.AfterSalesConfig.IPC_DIAG_RESPONSE, bundle, IpcConfig.App.DEVICE_COMMUNICATION);
        JsonObject jsonObject = new JsonObject();
        jsonObject.addProperty("senderPackageName", CarApplication.getApplication().getPackageName());
        jsonObject.addProperty(IpcConfig.IPCKey.STRING_MSG, res);
        IpcRouterService.sendData(IpcConfig.AfterSalesConfig.IPC_DIAG_RESPONSE, jsonObject.toString(), IpcConfig.App.DEVICE_COMMUNICATION);
        LogUtils.i(TAG, "sendDiagResponse2Mqtt : " + res);
    }
}
