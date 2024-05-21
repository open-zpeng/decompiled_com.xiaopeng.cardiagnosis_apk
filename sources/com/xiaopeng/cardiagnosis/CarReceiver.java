package com.xiaopeng.cardiagnosis;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import com.xiaopeng.cardiagnosis.service.AutoStorageClearService;
import com.xiaopeng.cardiagnosis.service.DaemonService;
import com.xiaopeng.cardiagnosis.service.DealCatonService;
import com.xiaopeng.cardiagnosis.service.DropCacheService;
import com.xiaopeng.cardiagnosis.service.MqttCmdService;
/* loaded from: classes4.dex */
public class CarReceiver extends BroadcastReceiver {
    @Override // android.content.BroadcastReceiver
    public void onReceive(Context context, Intent intent) {
        String action = intent.getAction();
        if ("android.intent.action.BOOT_COMPLETED".equals(action)) {
            Log.i("CarDiagnosis", "action--->" + action);
            DaemonService.start(context);
            MqttCmdService.start(context);
            DropCacheService.startDropCache(context);
            DealCatonService.start(context);
            AutoStorageClearService.start(context);
        }
    }
}
