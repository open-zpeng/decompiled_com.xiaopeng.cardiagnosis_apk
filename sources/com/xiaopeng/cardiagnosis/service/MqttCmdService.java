package com.xiaopeng.cardiagnosis.service;

import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.HandlerThread;
import android.os.IBinder;
import android.os.Looper;
import android.text.TextUtils;
import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;
import com.google.gson.reflect.TypeToken;
import com.xiaopeng.cardiagnosis.mqttcmd.CmdParser;
import com.xiaopeng.cardiagnosis.mqttcmd.CmdResponser;
import com.xiaopeng.commonfunc.bean.MqttAfterSalesCmd;
import com.xiaopeng.commonfunc.utils.EventBusUtil;
import com.xiaopeng.lib.framework.moduleinterface.ipcmodule.IIpcService;
import com.xiaopeng.lib.utils.LogUtils;
import com.xiaopeng.libconfig.ipc.IpcConfig;
import com.xiaopeng.libconfig.ipc.bean.MqttMsgBase;
import java.util.concurrent.LinkedBlockingQueue;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;
/* loaded from: classes4.dex */
public class MqttCmdService extends Service {
    private static final int MAX_QUEUE_SIZE = 10;
    private static final String TAG = "MqttCmdService";
    private static final String TAG_RESPONSER = "AfterSalesMqttCmdResponser";
    private CmdParser mCmdParser;
    private CmdResponser mCmdResponser;
    private MqttCmdThread mMqttCmdThread;
    private LinkedBlockingQueue<MqttMsgBase<MqttAfterSalesCmd>> mQueue;

    public static void start(Context context) {
        Intent intent = new Intent(context, MqttCmdService.class);
        context.startService(intent);
    }

    @Subscribe(threadMode = ThreadMode.BACKGROUND)
    public void onReceiveMqttCmd(IIpcService.IpcMessageEvent event) {
        LogUtils.i(TAG, "onReceiveMqttCmd event=" + event);
        if (event == null) {
            return;
        }
        String name = event.getSenderPackageName();
        Bundle bundle = event.getPayloadData();
        int msgID = event.getMsgID();
        if (TextUtils.isEmpty(name) || bundle == null) {
            return;
        }
        char c = 65535;
        if (name.hashCode() == 1131510802 && name.equals(IpcConfig.App.DEVICE_COMMUNICATION)) {
            c = 0;
        }
        if (c == 0 && msgID == 6004) {
            String data = bundle.getString(IpcConfig.IPCKey.STRING_MSG);
            if (TextUtils.isEmpty(data)) {
                return;
            }
            LogUtils.i(TAG, "onReceiveMqttCmd data : " + data);
            MqttMsgBase<MqttAfterSalesCmd> cmd = null;
            try {
                cmd = (MqttMsgBase) new Gson().fromJson(data, new TypeToken<MqttMsgBase<MqttAfterSalesCmd>>() { // from class: com.xiaopeng.cardiagnosis.service.MqttCmdService.1
                }.getType());
            } catch (JsonSyntaxException e) {
                e.printStackTrace();
            }
            if (cmd != null) {
                try {
                    this.mQueue.add(cmd);
                    this.mCmdResponser.responseReciveCmd(cmd);
                } catch (Exception e2) {
                    LogUtils.e(TAG, "add to queue fail :" + e2.toString());
                }
            }
        }
    }

    @Override // android.app.Service
    public void onCreate() {
        super.onCreate();
        this.mQueue = new LinkedBlockingQueue<>(10);
        HandlerThread thread = new HandlerThread(TAG_RESPONSER);
        thread.start();
        Looper looper = thread.getLooper();
        this.mCmdResponser = new CmdResponser(this, looper);
        this.mCmdParser = new CmdParser(this, this.mCmdResponser, looper);
        this.mMqttCmdThread = new MqttCmdThread(this);
        this.mMqttCmdThread.start();
        EventBusUtil.registerEventBus(this);
        LogUtils.d(TAG, "service created");
    }

    @Override // android.app.Service
    public int onStartCommand(Intent intent, int flags, int startId) {
        return 1;
    }

    @Override // android.app.Service
    public void onDestroy() {
        LogUtils.d(TAG, "onDestroy");
        super.onDestroy();
        EventBusUtil.unregisterEventBus(this);
        this.mQueue.clear();
    }

    @Override // android.app.Service
    public IBinder onBind(Intent intent) {
        return null;
    }

    /* loaded from: classes4.dex */
    private class MqttCmdThread extends Thread {
        private final Context mContext;

        public MqttCmdThread(Context context) {
            this.mContext = context;
        }

        /*  JADX ERROR: JadxOverflowException in pass: RegionMakerVisitor
            jadx.core.utils.exceptions.JadxOverflowException: Regions count limit reached
            	at jadx.core.utils.ErrorsCounter.addError(ErrorsCounter.java:56)
            	at jadx.core.utils.ErrorsCounter.error(ErrorsCounter.java:30)
            	at jadx.core.dex.attributes.nodes.NotificationAttrNode.addError(NotificationAttrNode.java:18)
            */
        /* JADX WARN: Removed duplicated region for block: B:8:0x0033  */
        @Override // java.lang.Thread, java.lang.Runnable
        /*
            Code decompiled incorrectly, please refer to instructions dump.
            To view partially-correct add '--show-bad-code' argument
        */
        public void run() {
            /*
                r3 = this;
            L0:
                java.lang.String r0 = "MqttCmdService"
                java.lang.String r1 = "try to take cmd from queue"
                com.xiaopeng.lib.utils.LogUtils.d(r0, r1)     // Catch: java.lang.InterruptedException -> L23
                com.xiaopeng.cardiagnosis.service.MqttCmdService r0 = com.xiaopeng.cardiagnosis.service.MqttCmdService.this     // Catch: java.lang.InterruptedException -> L23
                java.util.concurrent.LinkedBlockingQueue r0 = com.xiaopeng.cardiagnosis.service.MqttCmdService.access$000(r0)     // Catch: java.lang.InterruptedException -> L23
                java.lang.Object r0 = r0.take()     // Catch: java.lang.InterruptedException -> L23
                com.xiaopeng.libconfig.ipc.bean.MqttMsgBase r0 = (com.xiaopeng.libconfig.ipc.bean.MqttMsgBase) r0     // Catch: java.lang.InterruptedException -> L23
                com.xiaopeng.cardiagnosis.service.MqttCmdService r1 = com.xiaopeng.cardiagnosis.service.MqttCmdService.this     // Catch: java.lang.InterruptedException -> L23
                com.xiaopeng.cardiagnosis.mqttcmd.CmdParser r1 = com.xiaopeng.cardiagnosis.service.MqttCmdService.access$200(r1)     // Catch: java.lang.InterruptedException -> L23
                com.xiaopeng.cardiagnosis.service.MqttCmdService r2 = com.xiaopeng.cardiagnosis.service.MqttCmdService.this     // Catch: java.lang.InterruptedException -> L23
                com.xiaopeng.cardiagnosis.mqttcmd.CmdResponser r2 = com.xiaopeng.cardiagnosis.service.MqttCmdService.access$100(r2)     // Catch: java.lang.InterruptedException -> L23
                r1.runCmd(r0, r2)     // Catch: java.lang.InterruptedException -> L23
                goto L27
            L23:
                r0 = move-exception
                r0.printStackTrace()
            L27:
                com.xiaopeng.cardiagnosis.service.MqttCmdService r0 = com.xiaopeng.cardiagnosis.service.MqttCmdService.this
                com.xiaopeng.cardiagnosis.mqttcmd.CmdResponser r0 = com.xiaopeng.cardiagnosis.service.MqttCmdService.access$100(r0)
                boolean r0 = r0.isWaitingResponse()
                if (r0 == 0) goto L3e
                r0 = 1000(0x3e8, double:4.94E-321)
                sleep(r0)     // Catch: java.lang.InterruptedException -> L39
            L38:
                goto L27
            L39:
                r0 = move-exception
                r0.printStackTrace()
                goto L38
            L3e:
                goto L0
            */
            throw new UnsupportedOperationException("Method not decompiled: com.xiaopeng.cardiagnosis.service.MqttCmdService.MqttCmdThread.run():void");
        }
    }
}
