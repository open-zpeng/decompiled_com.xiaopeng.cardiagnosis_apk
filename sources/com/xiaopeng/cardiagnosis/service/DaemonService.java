package com.xiaopeng.cardiagnosis.service;

import android.app.Service;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.IBinder;
import android.util.Log;
import com.xiaopeng.cardiagnosis.job.CarJobManager;
import com.xiaopeng.cardiagnosis.policy.CarRebootPolicy;
import com.xiaopeng.cardiagnosis.presenter.AfterSalesPresenter;
import com.xiaopeng.cardiagnosis.presenter.FlagCheckPresenter;
import com.xiaopeng.commonfunc.Constant;
/* loaded from: classes4.dex */
public class DaemonService extends Service {
    private static final String TAG = "DaemonService";
    private AfterSalesPresenter mAfterSalesPresenter;
    private final BroadcastReceiver mBroadcastReceiver = new BroadcastReceiver() { // from class: com.xiaopeng.cardiagnosis.service.DaemonService.1
        @Override // android.content.BroadcastReceiver
        public void onReceive(Context context, Intent intent) {
            char c;
            String action = intent.getAction();
            Log.d(DaemonService.TAG, "onReceive, action--->" + action);
            int hashCode = action.hashCode();
            if (hashCode != -1454123155) {
                if (hashCode == 2141198304 && action.equals(Constant.ACTION_SHOW_CHECKMODE_DIALOG)) {
                    c = 1;
                }
                c = 65535;
            } else {
                if (action.equals("android.intent.action.SCREEN_ON")) {
                    c = 0;
                }
                c = 65535;
            }
            if (c == 0) {
                DaemonService.this.mCarJobManager.scheduleClearCacheJob();
            } else if (c == 1) {
                DaemonService.this.mAfterSalesPresenter.updateCheckModeDiag();
            }
        }
    };
    private CarJobManager mCarJobManager;
    private CarRebootPolicy mCarRebootPolicy;
    private FlagCheckPresenter mFlagCheckPresenter;

    public static void start(Context context) {
        Intent intent = new Intent(context, DaemonService.class);
        context.startService(intent);
    }

    @Override // android.app.Service
    public void onCreate() {
        super.onCreate();
        Log.d(TAG, "onCreate");
        this.mCarJobManager = new CarJobManager();
        this.mCarJobManager.scheduleClearCacheJob();
        this.mCarRebootPolicy = new CarRebootPolicy();
        this.mAfterSalesPresenter = new AfterSalesPresenter(this);
        this.mFlagCheckPresenter = new FlagCheckPresenter();
        this.mFlagCheckPresenter.checkFactoryMode();
        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction("android.intent.action.SCREEN_ON");
        intentFilter.addAction(Constant.ACTION_SHOW_CHECKMODE_DIALOG);
        registerReceiver(this.mBroadcastReceiver, intentFilter);
    }

    @Override // android.app.Service
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override // android.app.Service
    public void onDestroy() {
        super.onDestroy();
        Log.d(TAG, "onDestroy");
        unregisterReceiver(this.mBroadcastReceiver);
        this.mCarRebootPolicy.destroy();
        this.mAfterSalesPresenter.destroy();
        this.mFlagCheckPresenter.destroy();
    }
}
