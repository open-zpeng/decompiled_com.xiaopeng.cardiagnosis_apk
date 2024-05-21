package com.xiaopeng.commonfunc.system.listener;

import android.content.Context;
import android.os.Handler;
import android.os.Message;
import android.os.storage.StorageEventListener;
import com.xiaopeng.commonfunc.utils.FileUtil;
import com.xiaopeng.lib.utils.LogUtils;
import java.lang.ref.WeakReference;
/* loaded from: classes4.dex */
public class XpStorageEventListener extends StorageEventListener {
    public static final int MSG_MOUNTED = 1;
    public static final int MSG_TF_MOUNTED = 3;
    public static final int MSG_UNMOUNTED = 2;
    private static final String TAG = "XpStorageEventListener";
    private static final String TF_PATH = "/storage/extsd";
    private final Context mContext;
    private final WeakReference<Handler> mHandlerWeakReference;
    private String mStoragePathString;

    public XpStorageEventListener(Handler handler, Context context) {
        this.mHandlerWeakReference = new WeakReference<>(handler);
        this.mContext = context;
    }

    public String getStoragePath() {
        return this.mStoragePathString;
    }

    public void onStorageStateChanged(String path, String oldState, String newState) {
        super.onStorageStateChanged(path, oldState, newState);
        LogUtils.d(TAG, "path = " + path + ", oldState = " + oldState + ", newState = " + newState);
        if (newState.equals("mounted")) {
            if (this.mHandlerWeakReference.get() != null) {
                if (TF_PATH.equals(path)) {
                    Message message = this.mHandlerWeakReference.get().obtainMessage();
                    message.what = 3;
                    message.sendToTarget();
                    return;
                }
                this.mStoragePathString = FileUtil.getUDiskPath(this.mContext);
                Message message2 = this.mHandlerWeakReference.get().obtainMessage();
                message2.what = 1;
                message2.sendToTarget();
            }
        } else if ((newState.equals("unmounted") || newState.equals("ejecting")) && this.mHandlerWeakReference.get() != null && !TF_PATH.equals(path)) {
            this.mStoragePathString = null;
            Message message3 = this.mHandlerWeakReference.get().obtainMessage();
            message3.what = 2;
            message3.sendToTarget();
        }
    }
}
