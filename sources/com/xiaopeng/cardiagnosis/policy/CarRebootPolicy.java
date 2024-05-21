package com.xiaopeng.cardiagnosis.policy;

import android.app.AlarmManager;
import android.car.hardware.CarPropertyValue;
import android.net.Uri;
import android.os.HandlerThread;
import android.os.PowerManager;
import android.os.SystemClock;
import android.os.SystemProperties;
import android.util.Log;
import com.xiaopeng.cardiagnosis.CarApplication;
import com.xiaopeng.cardiagnosis.WorkHandler;
import com.xiaopeng.commonfunc.Constant;
import com.xiaopeng.commonfunc.model.car.BcmModel;
import com.xiaopeng.commonfunc.model.car.CarEventChangedListener;
import com.xiaopeng.commonfunc.model.car.VcuModel;
import com.xiaopeng.lib.apirouter.ApiRouter;
import java.util.Collections;
/* loaded from: classes4.dex */
public class CarRebootPolicy {
    private static final String TAG = "CarRebootPolicy";
    private final AlarmManager mAlarmManager;
    private final BcmModel mBcmModel;
    private final WorkHandler mHandler;
    private boolean mIsCarCharging;
    private boolean mIsCarLock;
    private final VcuModel mVcuModel;
    private static final long SCREEN_RUNTIME = SystemProperties.getLong("sys.screen_time", 86400000);
    private static final long CHECK_REBOOT_POLICY_INTERVAL = SystemProperties.getLong("sys.check_policy_time", 600000);
    private final AlarmManager.OnAlarmListener mAlarmListener = new AlarmManager.OnAlarmListener() { // from class: com.xiaopeng.cardiagnosis.policy.CarRebootPolicy.1
        @Override // android.app.AlarmManager.OnAlarmListener
        public void onAlarm() {
            boolean isRebootPolicyMeet = CarRebootPolicy.this.isRebootPolicyMeet();
            Log.i(CarRebootPolicy.TAG, "onAlarm, isRebootPolicyMeet: " + isRebootPolicyMeet);
            if (!isRebootPolicyMeet) {
                CarRebootPolicy.this.tryToRebootPolicyTask();
                return;
            }
            PowerManager pManager = (PowerManager) CarApplication.getContext().getSystemService("power");
            pManager.reboot(CarRebootPolicy.TAG);
        }
    };
    private final CarEventChangedListener mEventChange = new CarEventChangedListener() { // from class: com.xiaopeng.cardiagnosis.policy.CarRebootPolicy.2
        @Override // com.xiaopeng.commonfunc.model.car.CarEventChangedListener
        public void onChangeEvent(CarPropertyValue carPropertyValue) {
            int id = carPropertyValue.getPropertyId();
            Object propertyValue = carPropertyValue.getValue();
            if (id == 557847081) {
                int value = ((Integer) propertyValue).intValue();
                CarRebootPolicy.this.handleChargeStatus(value);
            } else if (id == 557849647) {
                int value2 = ((Integer) propertyValue).intValue();
                CarRebootPolicy.this.handleBcmATWS(value2);
            }
        }
    };

    public CarRebootPolicy() {
        HandlerThread handlerThread = new HandlerThread(TAG);
        handlerThread.start();
        this.mHandler = new WorkHandler(handlerThread.getLooper());
        this.mAlarmManager = (AlarmManager) CarApplication.getContext().getSystemService("alarm");
        this.mBcmModel = new BcmModel(TAG);
        this.mVcuModel = new VcuModel(TAG);
        this.mBcmModel.registerPropCallback(Collections.singletonList(557849647), this.mEventChange);
        this.mVcuModel.registerPropCallback(Collections.singletonList(557847081), this.mEventChange);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void handleChargeStatus(final int status) {
        this.mHandler.optPost(new Runnable() { // from class: com.xiaopeng.cardiagnosis.policy.CarRebootPolicy.3
            @Override // java.lang.Runnable
            public void run() {
                if (status == 2) {
                    if (!CarRebootPolicy.this.mIsCarCharging) {
                        CarRebootPolicy.this.mIsCarCharging = true;
                        CarRebootPolicy.this.tryToRebootPolicyTask();
                    }
                } else if (CarRebootPolicy.this.mIsCarCharging) {
                    CarRebootPolicy.this.mIsCarCharging = false;
                    CarRebootPolicy.this.cancelRebootPolicyTask();
                }
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void handleBcmATWS(final int status) {
        this.mHandler.optPost(new Runnable() { // from class: com.xiaopeng.cardiagnosis.policy.CarRebootPolicy.4
            @Override // java.lang.Runnable
            public void run() {
                if (status != 0) {
                    if (CarRebootPolicy.this.mIsCarLock) {
                        CarRebootPolicy.this.cancelRebootPolicyTask();
                        CarRebootPolicy.this.mIsCarLock = false;
                    }
                } else if (!CarRebootPolicy.this.mIsCarLock) {
                    CarRebootPolicy.this.tryToRebootPolicyTask();
                    CarRebootPolicy.this.mIsCarLock = true;
                }
            }
        });
    }

    private boolean isInOTA() {
        Uri.Builder builder = new Uri.Builder();
        builder.authority("com.xiaopeng.ota.OTAService").path(Constant.OTA.GET_OTA_STATE);
        int state = 1;
        try {
            state = ((Integer) ApiRouter.route(builder.build())).intValue();
        } catch (Exception e) {
            Log.e(TAG, "isInOTA-->" + e.getMessage());
        }
        return state == 2;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void cancelRebootPolicyTask() {
        this.mHandler.optPost(new Runnable() { // from class: com.xiaopeng.cardiagnosis.policy.CarRebootPolicy.5
            @Override // java.lang.Runnable
            public void run() {
                Log.i(CarRebootPolicy.TAG, "cancelRebootPolicyTask");
                CarRebootPolicy.this.mAlarmManager.cancel(CarRebootPolicy.this.mAlarmListener);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void tryToRebootPolicyTask() {
        this.mHandler.optPost(new Runnable() { // from class: com.xiaopeng.cardiagnosis.policy.CarRebootPolicy.6
            @Override // java.lang.Runnable
            public void run() {
                boolean isInCharging = CarRebootPolicy.this.mVcuModel.isInCharging();
                boolean isCarLock = CarRebootPolicy.this.mBcmModel.isCarLock();
                Log.i(CarRebootPolicy.TAG, "tryToRebootPolicyTask, isInCharging: " + isInCharging + ", isCarLock: " + isCarLock);
                if (isInCharging && isCarLock) {
                    CarRebootPolicy.this.cancelRebootPolicyTask();
                    Log.i(CarRebootPolicy.TAG, "schedule reboot alarm");
                    CarRebootPolicy.this.mAlarmManager.setExact(3, SystemClock.elapsedRealtime() + CarRebootPolicy.CHECK_REBOOT_POLICY_INTERVAL, "tryToRebootPolicyTask", CarRebootPolicy.this.mAlarmListener, CarRebootPolicy.this.mHandler);
                }
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public boolean isRebootPolicyMeet() {
        boolean isInCharging = this.mVcuModel.isInCharging();
        boolean isCarLock = this.mBcmModel.isCarLock();
        long time = SystemClock.elapsedRealtime();
        boolean isOTA = isInOTA();
        Log.i(TAG, "isRebootPolicyMeet, isInCharging: " + isInCharging + ", isCarLock: " + isCarLock + ", time: " + time + ", isOTA: " + isOTA);
        return isInCharging && isCarLock && time > SCREEN_RUNTIME && !isOTA;
    }

    public void destroy() {
        this.mBcmModel.onDestroy();
        this.mVcuModel.onDestroy();
        this.mAlarmManager.cancel(this.mAlarmListener);
    }
}
