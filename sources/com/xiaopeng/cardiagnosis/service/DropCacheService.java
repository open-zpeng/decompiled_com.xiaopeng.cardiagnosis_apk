package com.xiaopeng.cardiagnosis.service;

import android.app.IntentService;
import android.content.Context;
import android.content.Intent;
import com.xiaopeng.commonfunc.utils.MemoryUtil;
/* loaded from: classes4.dex */
public class DropCacheService extends IntentService {
    public DropCacheService() {
        super("DropCacheService");
    }

    public static void startDropCache(Context context) {
        Intent intent = new Intent(context, DropCacheService.class);
        context.startService(intent);
    }

    @Override // android.app.IntentService
    protected void onHandleIntent(Intent intent) {
        if (intent != null) {
            try {
                Thread.sleep(30000L);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            MemoryUtil.getInstance().dropCache();
        }
    }
}
