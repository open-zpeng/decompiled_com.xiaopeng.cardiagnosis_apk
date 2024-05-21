package com.xiaopeng.cardiagnosis.mqttcmd;

import android.content.Context;
import android.text.TextUtils;
import com.google.gson.Gson;
import com.xiaopeng.aftersales.manager.EncryptShListener;
import com.xiaopeng.cardiagnosis.CarApplication;
import com.xiaopeng.commonfunc.Constant;
import com.xiaopeng.commonfunc.bean.MqttAfterSalesCmd;
import com.xiaopeng.commonfunc.bean.ParamEncryptSh;
import com.xiaopeng.commonfunc.callback.FileUploadOssCallback;
import com.xiaopeng.commonfunc.utils.AfterSalesHelper;
import com.xiaopeng.commonfunc.utils.FileUploadHelper;
import com.xiaopeng.commonfunc.utils.FileUtil;
import com.xiaopeng.lib.framework.module.Module;
import com.xiaopeng.lib.framework.moduleinterface.netchannelmodule.remotestorage.Callback;
import com.xiaopeng.lib.framework.moduleinterface.netchannelmodule.remotestorage.IRemoteStorage;
import com.xiaopeng.lib.framework.moduleinterface.netchannelmodule.remotestorage.StorageException;
import com.xiaopeng.lib.framework.netchannelmodule.NetworkChannelsEntry;
import com.xiaopeng.lib.utils.FileUtils;
import com.xiaopeng.lib.utils.LogUtils;
import com.xiaopeng.lib.utils.MD5Utils;
import com.xiaopeng.lib.utils.crypt.AESUtils;
import com.xiaopeng.libconfig.ipc.bean.MqttMsgBase;
import java.io.File;
import java.io.FileNotFoundException;
/* loaded from: classes4.dex */
public class MqttEncryptSh extends MqttCmdHandler {
    private static final String ENCRYPT_SH_TOOL_FOLDER = "/cache/aftersales";
    private static final String ENCRYPT_SH_TOOL_TEMP_FILE = "/cache/aftersales/temp_encrypt_sh.zip";
    private static final String MQTT_CMD_TYPE_EXEC_ENCRYPT_SH = "0001";
    private final EncryptShListener mEncryptShListener;

    public /* synthetic */ void lambda$new$0$MqttEncryptSh(int errorcode, String resultPath, String outputPath, boolean isCloudCmd) {
        if (!isCloudCmd) {
            return;
        }
        if (errorcode == 0) {
            if (!TextUtils.isEmpty(outputPath)) {
                uploadFile(outputPath);
            } else {
                responseOK(this.mMqttCmd, "");
            }
        } else {
            responseNG(this.mMqttCmd);
        }
        FileUtils.deleteFile(ENCRYPT_SH_TOOL_TEMP_FILE);
    }

    public MqttEncryptSh(Context context, CmdResponser responser) {
        super(context, responser);
        this.mEncryptShListener = new EncryptShListener() { // from class: com.xiaopeng.cardiagnosis.mqttcmd.-$$Lambda$MqttEncryptSh$7tsnzsxuINV_BrsEFpuGjsbXfwc
            public final void onEncryptShResponse(int i, String str, String str2, boolean z) {
                MqttEncryptSh.this.lambda$new$0$MqttEncryptSh(i, str, str2, z);
            }
        };
        this.CLASS_NAME = "MqttEncryptSh";
    }

    @Override // com.xiaopeng.cardiagnosis.mqttcmd.MqttCmdHandler
    public void init() {
        super.init();
        AfterSalesHelper.getAfterSalesManager().registerEncryptShListener(this.mEncryptShListener);
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
            if (cmdType.hashCode() != 1477633 || !cmdType.equals(MQTT_CMD_TYPE_EXEC_ENCRYPT_SH)) {
                z = true;
            }
            if (!z) {
                ParamEncryptSh param = (ParamEncryptSh) new Gson().fromJson(cmd.getMsgContent().getCmd_param(), (Class<Object>) ParamEncryptSh.class);
                downloadEncryptSh(param);
            } else {
                responseNoCmdType(cmd);
            }
            return true;
        }
        return false;
    }

    @Override // com.xiaopeng.cardiagnosis.mqttcmd.MqttCmdHandler
    public void destroy() {
        AfterSalesHelper.getAfterSalesManager().unregisterEncryptShListener(this.mEncryptShListener);
        super.destroy();
    }

    private void downloadEncryptSh(final ParamEncryptSh param) {
        IRemoteStorage storage = (IRemoteStorage) Module.get(NetworkChannelsEntry.class).get(IRemoteStorage.class);
        try {
            storage.initWithContext(CarApplication.getApplication());
            storage.downloadWithPathAndCallback(FileUploadHelper.SECURITY_BUCKET_NAME, param.getDownload_path(), ENCRYPT_SH_TOOL_TEMP_FILE, new Callback() { // from class: com.xiaopeng.cardiagnosis.mqttcmd.MqttEncryptSh.1
                @Override // com.xiaopeng.lib.framework.moduleinterface.netchannelmodule.remotestorage.Callback
                public void onStart(String s, String s1) {
                    String str = MqttEncryptSh.this.CLASS_NAME;
                    LogUtils.d(str, "downloadWithPathAndCallback onStart s:" + s + ", s1:" + s1);
                }

                @Override // com.xiaopeng.lib.framework.moduleinterface.netchannelmodule.remotestorage.Callback
                public void onSuccess(String s, String s1) {
                    String str = MqttEncryptSh.this.CLASS_NAME;
                    LogUtils.i(str, "downloadWithPathAndCallback onSuccess s:" + s + ", s1:" + s1);
                    try {
                        if (param.getMd5().equals(MD5Utils.getFileMd5(new File(MqttEncryptSh.ENCRYPT_SH_TOOL_TEMP_FILE)))) {
                            FileUtil.unzipMultiFile(MqttEncryptSh.ENCRYPT_SH_TOOL_TEMP_FILE, "/cache/aftersales");
                            AfterSalesHelper.getAfterSalesManager().executeEncryptSh("/cache/aftersales", true);
                        } else {
                            LogUtils.e(MqttEncryptSh.this.CLASS_NAME, "FILE md5 not correct");
                            MqttEncryptSh.this.responseNG(MqttEncryptSh.this.mMqttCmd);
                        }
                    } catch (FileNotFoundException e) {
                        MqttEncryptSh mqttEncryptSh = MqttEncryptSh.this;
                        mqttEncryptSh.responseNG(mqttEncryptSh.mMqttCmd);
                        e.printStackTrace();
                    }
                }

                @Override // com.xiaopeng.lib.framework.moduleinterface.netchannelmodule.remotestorage.Callback
                public void onFailure(String s, String s1, StorageException e) {
                    String str = MqttEncryptSh.this.CLASS_NAME;
                    LogUtils.e(str, "downloadWithPathAndCallback onFailure s:" + s + ", s1:" + s1 + ", e:" + e.toString());
                    MqttEncryptSh mqttEncryptSh = MqttEncryptSh.this;
                    mqttEncryptSh.responseNG(mqttEncryptSh.mMqttCmd);
                }
            });
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void uploadFile(final String filePath) {
        FileUploadHelper.uploadFile2Oss(CarApplication.getApplication(), filePath, new FileUploadOssCallback() { // from class: com.xiaopeng.cardiagnosis.mqttcmd.MqttEncryptSh.2
            @Override // com.xiaopeng.commonfunc.callback.FileUploadOssCallback
            public void onStart(String s, String s1) {
                String str = MqttEncryptSh.this.CLASS_NAME;
                LogUtils.d(str, "uploadLog onStart s:" + s + ", s1:" + s1);
            }

            @Override // com.xiaopeng.commonfunc.callback.FileUploadOssCallback
            public void onSuccess(String s, String s1) {
                String str = MqttEncryptSh.this.CLASS_NAME;
                LogUtils.i(str, "uploadLog onSuccess s:" + s + ", s1:" + s1);
                MqttEncryptSh.this.removeUploadFolder(filePath);
                String res = s.replaceFirst(Constant.SECURITY_BUCKET_ENDPOINT, "");
                if (res.equalsIgnoreCase(s)) {
                    res = s.replaceFirst(Constant.XP_SECURITY_BUCKET_ENDPOINT, "");
                }
                MqttEncryptSh mqttEncryptSh = MqttEncryptSh.this;
                mqttEncryptSh.responseOK(mqttEncryptSh.mMqttCmd, res);
            }

            @Override // com.xiaopeng.commonfunc.callback.FileUploadOssCallback
            public void onFailure(String s, String s1, Exception e) {
                String str = MqttEncryptSh.this.CLASS_NAME;
                LogUtils.e(str, "uploadLog onFailure s:" + s + ", s1:" + s1 + ", e:" + e.toString());
                MqttEncryptSh.this.removeUploadFolder(filePath);
                MqttEncryptSh mqttEncryptSh = MqttEncryptSh.this;
                mqttEncryptSh.responseUploadOssFail(mqttEncryptSh.mMqttCmd);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void removeUploadFolder(String path) {
        LogUtils.d(this.CLASS_NAME, "removeUploadFolder");
        FileUtils.deleteFile(path);
    }
}
