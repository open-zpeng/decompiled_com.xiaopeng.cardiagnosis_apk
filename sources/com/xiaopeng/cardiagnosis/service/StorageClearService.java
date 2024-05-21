package com.xiaopeng.cardiagnosis.service;

import android.app.IntentService;
import android.content.Intent;
import android.util.Log;
import com.xiaopeng.commonfunc.utils.CmdUtil;
import com.xiaopeng.commonfunc.utils.DeleteFileUtil;
import com.xiaopeng.commonfunc.utils.FileUploadHelper;
import com.xiaopeng.commonfunc.utils.StorageUtil;
/* loaded from: classes4.dex */
public class StorageClearService extends IntentService {
    private final String TAG;
    private int tryCount;

    public StorageClearService() {
        super("StorageClearService");
        this.TAG = "StorageClearService";
        this.tryCount = 0;
    }

    private void pushLog() {
        Log.i("StorageClearService", "pushLog");
        if (!FileUploadHelper.checkCanSend(getApplicationContext())) {
            Log.i("StorageClearService", "return no send");
            return;
        }
        String path = getApplicationContext().getFilesDir().getAbsolutePath() + "/storage.log";
        try {
            CmdUtil.execRootCmdAndSave(path);
            FileUploadHelper.sendStatDataAndUploadFiles(getApplicationContext(), path);
            DeleteFileUtil.deleteSystemLog();
        } catch (Exception e) {
            Log.i("StorageClearService", "pushError=" + e.toString());
            int i = this.tryCount;
            if (i < 3) {
                this.tryCount = i + 1;
                pushLog();
            }
        }
    }

    @Override // android.app.IntentService
    protected void onHandleIntent(Intent intent) {
        boolean isfirst = intent.getBooleanExtra("isFirstStart", false);
        if (isfirst && !StorageUtil.checkStorage(10)) {
            Log.i("StorageClearService", "check storage is not low");
        } else {
            pushLog();
        }
    }

    @Override // android.app.IntentService, android.app.Service
    public void onCreate() {
        super.onCreate();
        Log.i("StorageClearService", "onCreate");
    }

    @Override // android.app.IntentService, android.app.Service
    public void onDestroy() {
        super.onDestroy();
        Log.i("StorageClearService", "onDestroy");
    }
}
