package com.xiaopeng.cardiagnosis.service;

import android.app.Service;
import android.car.hardware.CarPropertyValue;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.HandlerThread;
import android.os.IBinder;
import android.text.TextUtils;
import android.util.Log;
import com.google.gson.Gson;
import com.xiaopeng.cardiagnosis.WorkHandler;
import com.xiaopeng.cardiagnosis.service.AutoStorageClearService;
import com.xiaopeng.commonfunc.Constant;
import com.xiaopeng.commonfunc.bean.storage.StorageList;
import com.xiaopeng.commonfunc.model.car.BcmModel;
import com.xiaopeng.commonfunc.model.car.CarEventChangedListener;
import com.xiaopeng.commonfunc.model.car.VcuModel;
import com.xiaopeng.commonfunc.utils.AfterSalesHelper;
import com.xiaopeng.commonfunc.utils.BuriedPointUtils;
import com.xiaopeng.commonfunc.utils.DeleteFileUtil;
import com.xiaopeng.commonfunc.utils.StorageUtil;
import com.xiaopeng.lib.utils.ThreadUtils;
import com.xiaopeng.util.xpTextUtils;
import com.xiaopeng.xmlconfig.Support;
import java.io.File;
import java.util.Collections;
import org.json.JSONObject;
/* loaded from: classes4.dex */
public class AutoStorageClearService extends Service {
    private static final String TAG = "AutoStorageClearService";
    private BcmModel mBcmModel;
    private WorkHandler mHandler;
    private boolean mIsCarCharging;
    private boolean mIsCarLock;
    private VcuModel mVcuModel;
    private static Thread thread = null;
    private static boolean loadConfig = false;
    private static StorageList storageList = null;
    private static volatile long clearSize = 0;
    private static volatile int count = 0;
    private static final boolean SUPPORT_AUTO_CLEAR = Support.Feature.getBoolean(Support.Feature.SUPPORT_AUTO_CLEAR);
    private final BroadcastReceiver mBroadcastReceiver = new BroadcastReceiver() { // from class: com.xiaopeng.cardiagnosis.service.AutoStorageClearService.2
        @Override // android.content.BroadcastReceiver
        public void onReceive(Context context, Intent intent) {
            String action = intent.getAction();
            Log.d(AutoStorageClearService.TAG, "onReceive, action--->" + action);
            if (((action.hashCode() == 1933359394 && action.equals(Constant.ACTION_CALLBACK_SETTING_CLEAR)) ? (char) 0 : (char) 65535) == 0) {
                AutoStorageClearService.this.handleSettingClearBack();
            }
        }
    };
    private final CarEventChangedListener mEventChange = new CarEventChangedListener() { // from class: com.xiaopeng.cardiagnosis.service.AutoStorageClearService.3
        @Override // com.xiaopeng.commonfunc.model.car.CarEventChangedListener
        public void onChangeEvent(CarPropertyValue carPropertyValue) {
            Log.d(AutoStorageClearService.TAG, "CarEventChangedListener ");
            int id = carPropertyValue.getPropertyId();
            Object propertyValue = carPropertyValue.getValue();
            if (id == 557847081) {
                int value = ((Integer) propertyValue).intValue();
                Log.d(AutoStorageClearService.TAG, "CarEventChangedListener handleChargeStatus");
                AutoStorageClearService.this.handleChargeStatus(value);
            } else if (id == 557849647) {
                int value2 = ((Integer) propertyValue).intValue();
                Log.d(AutoStorageClearService.TAG, "CarEventChangedListener handleBcmATWS");
                AutoStorageClearService.this.handleBcmATWS(value2);
            }
        }
    };
    private Runnable clearRunnable = new AnonymousClass7();

    public static void start(Context context) {
        Intent intent = new Intent(context, AutoStorageClearService.class);
        context.startService(intent);
    }

    @Override // android.app.Service
    public void onCreate() {
        super.onCreate();
        Log.i(TAG, "onCreate");
        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction(Constant.ACTION_CALLBACK_SETTING_CLEAR);
        registerReceiver(this.mBroadcastReceiver, intentFilter);
        HandlerThread handlerThread = new HandlerThread(TAG);
        handlerThread.start();
        this.mHandler = new WorkHandler(handlerThread.getLooper());
        this.mBcmModel = new BcmModel(TAG);
        this.mVcuModel = new VcuModel(TAG);
        this.mBcmModel.registerPropCallback(Collections.singletonList(557849647), this.mEventChange);
        this.mVcuModel.registerPropCallback(Collections.singletonList(557847081), this.mEventChange);
        this.mHandler.optPost(new Runnable() { // from class: com.xiaopeng.cardiagnosis.service.AutoStorageClearService.1
            @Override // java.lang.Runnable
            public void run() {
                ClearStoragePolicyPolicy.loadClearStoragePolicy();
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void handleChargeStatus(final int status) {
        Log.d(TAG, "handleChargeStatus " + status);
        this.mHandler.optPost(new Runnable() { // from class: com.xiaopeng.cardiagnosis.service.AutoStorageClearService.4
            @Override // java.lang.Runnable
            public void run() {
                if (status == 2) {
                    if (!AutoStorageClearService.this.mIsCarCharging) {
                        AutoStorageClearService.this.mIsCarCharging = true;
                        AutoStorageClearService.this.notifySettingClear();
                    }
                } else if (AutoStorageClearService.this.mIsCarCharging) {
                    AutoStorageClearService.this.mIsCarCharging = false;
                    AutoStorageClearService.this.cancelAutoClearPolicyTask();
                }
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void handleBcmATWS(final int status) {
        Log.d(TAG, "handleBcmATWS " + status);
        this.mHandler.optPost(new Runnable() { // from class: com.xiaopeng.cardiagnosis.service.AutoStorageClearService.5
            @Override // java.lang.Runnable
            public void run() {
                if (status != 0) {
                    if (AutoStorageClearService.this.mIsCarLock) {
                        AutoStorageClearService.this.cancelAutoClearPolicyTask();
                        AutoStorageClearService.this.mIsCarLock = false;
                    }
                } else if (!AutoStorageClearService.this.mIsCarLock) {
                    AutoStorageClearService.this.notifySettingClear();
                    AutoStorageClearService.this.mIsCarLock = true;
                }
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void notifySettingClear() {
        if (!SUPPORT_AUTO_CLEAR) {
            return;
        }
        boolean isInCharging = this.mVcuModel.isInCharging();
        boolean isCarLock = this.mBcmModel.isCarLock();
        Log.d(TAG, "notifySettingClear, isInCharging: " + isInCharging + ", isCarLock: " + isCarLock);
        if (isInCharging && isCarLock) {
            ThreadUtils.runOnMainThread(new Runnable() { // from class: com.xiaopeng.cardiagnosis.service.-$$Lambda$AutoStorageClearService$3LLbas81fQt54_jdKrPW3vgrhvk
                @Override // java.lang.Runnable
                public final void run() {
                    AutoStorageClearService.this.lambda$notifySettingClear$0$AutoStorageClearService();
                }
            });
        }
    }

    public /* synthetic */ void lambda$notifySettingClear$0$AutoStorageClearService() {
        Intent intent = new Intent(Constant.ACTION_NOTIFY_SETTING_AOTU_CLEAR);
        intent.setPackage(Constant.PACKAGE_NAME_SETTING);
        sendBroadcast(intent);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void handleSettingClearBack() {
        if (SUPPORT_AUTO_CLEAR && StorageUtil.checkStorage(5)) {
            if (loadConfig) {
                tryAutoClearPolicyTask();
            } else {
                this.mHandler.optPost(new Runnable() { // from class: com.xiaopeng.cardiagnosis.service.AutoStorageClearService.6
                    @Override // java.lang.Runnable
                    public void run() {
                        ClearStoragePolicyPolicy.loadClearStoragePolicy();
                    }
                });
            }
        }
    }

    /* loaded from: classes4.dex */
    protected static class ClearStoragePolicyPolicy {
        private static final String POLICY_FILE_SYSTEM = "/system/etc/clearstorageconfig/clearstorageconfig.json";

        protected ClearStoragePolicyPolicy() {
        }

        public static void loadClearStoragePolicy() {
            try {
                new StringBuilder();
                File systemFile = new File(POLICY_FILE_SYSTEM);
                String content = xpTextUtils.getValue(systemFile);
                if (!TextUtils.isEmpty(content)) {
                    StorageList unused = AutoStorageClearService.storageList = (StorageList) new Gson().fromJson(content, (Class<Object>) StorageList.class);
                    new JSONObject(content);
                    boolean unused2 = AutoStorageClearService.loadConfig = true;
                }
            } catch (Exception e) {
                Log.i(AutoStorageClearService.TAG, "loadProcessPolicy e=" + e);
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void cancelAutoClearPolicyTask() {
        if (SUPPORT_AUTO_CLEAR && thread != null) {
            Log.d(TAG, "cancelAutoClearPolicyTask ");
            thread.interrupt();
            thread = null;
        }
    }

    private void tryAutoClearPolicyTask() {
        if (!SUPPORT_AUTO_CLEAR) {
            return;
        }
        boolean isInCharging = this.mVcuModel.isInCharging();
        boolean isCarLock = this.mBcmModel.isCarLock();
        Log.d(TAG, "tryAutoClearPolicyTask, isInCharging: " + isInCharging + ", isCarLock: " + isCarLock);
        if (isInCharging && isCarLock && thread == null) {
            Log.d(TAG, "tryAutoClearPolicyTask, start");
            count++;
            thread = new Thread(this.clearRunnable, "ClearStorage");
            thread.start();
        }
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    /* renamed from: com.xiaopeng.cardiagnosis.service.AutoStorageClearService$7  reason: invalid class name */
    /* loaded from: classes4.dex */
    public class AnonymousClass7 implements Runnable {
        AnonymousClass7() {
        }

        @Override // java.lang.Runnable
        public void run() {
            long unused = AutoStorageClearService.clearSize = 0L;
            if (StorageUtil.checkStorage(5)) {
                Log.d(AutoStorageClearService.TAG, AfterSalesHelper.REPAIRMODE_ACTION_CLEAR_LOG);
                AutoStorageClearService.clearSize += AutoStorageClearService.this.cleanData(AutoStorageClearService.storageList.getLoglist());
            }
            if (StorageUtil.checkStorage(5)) {
                Log.d(AutoStorageClearService.TAG, "clear cache");
                AutoStorageClearService.clearSize += AutoStorageClearService.this.cleanData(AutoStorageClearService.storageList.getCachelist());
            }
            if (StorageUtil.checkStorage(5)) {
                Log.d(AutoStorageClearService.TAG, "clear file");
                AutoStorageClearService.clearSize += AutoStorageClearService.this.cleanData(AutoStorageClearService.storageList.getBigfilelist());
            }
            BuriedPointUtils.sendPageStateDataLog(BuriedPointUtils.STORAGE_CLEAN_PAGEID, BuriedPointUtils.STORAGE_CLEAN_BUTTONID, AutoStorageClearService.clearSize, AutoStorageClearService.count);
            if (StorageUtil.checkStorage(5)) {
                ThreadUtils.runOnMainThread(new Runnable() { // from class: com.xiaopeng.cardiagnosis.service.-$$Lambda$AutoStorageClearService$7$gGYckiqq_p_gpvbNpBOl2OOYtj0
                    @Override // java.lang.Runnable
                    public final void run() {
                        AutoStorageClearService.AnonymousClass7.this.lambda$run$0$AutoStorageClearService$7();
                    }
                });
            }
        }

        public /* synthetic */ void lambda$run$0$AutoStorageClearService$7() {
            Log.d(AutoStorageClearService.TAG, "send  ACTION_NOTIFY_STORAGE_FULL ");
            Intent intent = new Intent(Constant.ACTION_NOTIFY_STORAGE_FULL);
            intent.setPackage(Constant.PACKAGE_NAME_SETTING);
            AutoStorageClearService.this.sendBroadcast(intent);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public long cleanData(StorageList.StorageData[] dataList) {
        long totalSize = 0;
        for (StorageList.StorageData data : dataList) {
            totalSize += DeleteFileUtil.deleteStorage(data.getPath(), data.getSuffix());
            Log.d(TAG, " clear path = " + data.getPath() + " suffix = " + data.getSuffix() + " totalSize = " + totalSize);
        }
        return totalSize;
    }

    @Override // android.app.Service
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override // android.app.Service
    public void onDestroy() {
        super.onDestroy();
        Log.i(TAG, "onDestroy");
        this.mBcmModel.onDestroy();
        this.mVcuModel.onDestroy();
    }
}
