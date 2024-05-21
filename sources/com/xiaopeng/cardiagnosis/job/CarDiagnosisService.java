package com.xiaopeng.cardiagnosis.job;

import android.app.job.JobParameters;
import android.app.job.JobService;
import android.os.Handler;
import android.os.HandlerThread;
import android.os.Message;
import android.util.Log;
import android.util.SparseArray;
import com.xiaopeng.cardiagnosis.CarApplication;
import com.xiaopeng.commonfunc.utils.MemoryUtil;
/* loaded from: classes4.dex */
public class CarDiagnosisService extends JobService {
    private static final int MESSAGE_CAR_DIAGNOSIS = 1;
    private static final String TAG = "CarDiagnosis";
    private Handler mHandler;

    @Override // android.app.Service
    public void onCreate() {
        super.onCreate();
        HandlerThread handlerThread = new HandlerThread("CarDiagnosisThread");
        handlerThread.start();
        this.mHandler = new Handler(handlerThread.getLooper()) { // from class: com.xiaopeng.cardiagnosis.job.CarDiagnosisService.1
            @Override // android.os.Handler
            public void handleMessage(Message msg) {
                super.handleMessage(msg);
                if (msg.what == 1) {
                    CarDiagnosisService.this.detectMemory();
                    CarDiagnosisService.this.dropCache();
                    CarDiagnosisService.this.jobFinished((JobParameters) msg.obj, false);
                    Log.i(CarDiagnosisService.TAG, "jobFinished");
                }
            }
        };
    }

    @Override // android.app.job.JobService
    public boolean onStartJob(JobParameters params) {
        Log.i(TAG, "onStartJob");
        Message message = this.mHandler.obtainMessage(1);
        message.obj = params;
        this.mHandler.sendMessage(message);
        return true;
    }

    @Override // android.app.job.JobService
    public boolean onStopJob(JobParameters params) {
        Log.i(TAG, "onStopJob");
        return false;
    }

    @Override // android.app.Service
    public void onDestroy() {
        super.onDestroy();
        Log.i(TAG, "onDestroy");
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void detectMemory() {
        SparseArray<Integer> memoryInfo = MemoryUtil.getInstance().getMemLeakInfo();
        if (CarApplication.isDBG()) {
            Log.d(TAG, memoryInfo.toString());
        }
        int pid = memoryInfo.keyAt(0);
        int memory = memoryInfo.valueAt(0).intValue();
        if (memoryInfo.size() > 0 && MemoryUtil.getInstance().isMemLarge(CarApplication.getContext(), pid, memory)) {
            MemoryUtil.getInstance().killMemLeakPid(pid);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void dropCache() {
        MemoryUtil.getInstance().dropCache();
        try {
            Thread.sleep(5000L);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        MemoryUtil.getInstance().compactMemory();
        try {
            Thread.sleep(3000L);
        } catch (InterruptedException e2) {
            e2.printStackTrace();
        }
    }
}
