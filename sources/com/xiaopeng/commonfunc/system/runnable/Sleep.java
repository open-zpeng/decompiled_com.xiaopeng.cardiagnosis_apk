package com.xiaopeng.commonfunc.system.runnable;

import com.xiaopeng.lib.utils.LogUtils;
/* loaded from: classes4.dex */
public class Sleep implements Runnable {
    private static final String TAG = "Sleep";
    final Object _lock = new Object();
    private long _timeout;

    private Sleep(long timeout) {
        this._timeout = 1000L;
        this._timeout = timeout;
    }

    public static void sleep(long timeout) {
        LogUtils.d(TAG, "sleep start  timeout: " + timeout);
        Thread thread = new Thread(new Sleep(timeout));
        try {
            thread.start();
            thread.join();
        } catch (InterruptedException e) {
            LogUtils.e(TAG, e.toString());
        }
        LogUtils.d(TAG, "sleep end  timeout: " + timeout);
    }

    @Override // java.lang.Runnable
    public void run() {
        synchronized (this._lock) {
            try {
                this._lock.wait(this._timeout);
            } catch (InterruptedException e) {
                LogUtils.e(TAG, e.toString());
            }
        }
    }
}
