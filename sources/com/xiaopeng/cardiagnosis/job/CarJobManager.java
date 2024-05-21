package com.xiaopeng.cardiagnosis.job;

import android.app.job.JobInfo;
import android.app.job.JobScheduler;
import android.content.ComponentName;
import android.util.Log;
import com.xiaopeng.cardiagnosis.CarApplication;
/* loaded from: classes4.dex */
public class CarJobManager {
    private static final String TAG = "CarJobManager";

    public void scheduleClearCacheJob() {
        JobScheduler jobScheduler = (JobScheduler) CarApplication.getContext().getSystemService("jobscheduler");
        if (jobScheduler.getPendingJob(10) == null) {
            ComponentName mCarDiagnosisJob = new ComponentName(CarApplication.getContext(), CarDiagnosisService.class);
            JobInfo.Builder infoBuilder = new JobInfo.Builder(10, mCarDiagnosisJob).setRequiresDeviceIdle(true);
            int result = jobScheduler.schedule(infoBuilder.build());
            Log.d(TAG, "scheduleIdleJob: " + result);
        }
    }
}
