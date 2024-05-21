package com.xiaopeng.commonfunc.utils;

import android.content.ComponentName;
import android.content.Context;
import android.content.ServiceConnection;
import android.os.IBinder;
import com.xiaopeng.lib.utils.LogUtils;
import com.xiaopeng.xuimanager.XUIManager;
import com.xiaopeng.xuimanager.XUIServiceNotConnectedException;
import com.xiaopeng.xuimanager.mediacenter.MediaCenterManager;
/* loaded from: classes4.dex */
public class XUIHelper {
    private static final String TAG = "XUIHelper";
    private static final ServiceConnection sServiceConnection = new ServiceConnection() { // from class: com.xiaopeng.commonfunc.utils.XUIHelper.1
        @Override // android.content.ServiceConnection
        public void onServiceConnected(ComponentName componentName, IBinder iBinder) {
            LogUtils.i(XUIHelper.TAG, "onServiceConnected, name: " + componentName + ", service: " + iBinder);
        }

        @Override // android.content.ServiceConnection
        public void onServiceDisconnected(ComponentName componentName) {
            LogUtils.i(XUIHelper.TAG, "onServiceDisconnected, name: " + componentName);
        }
    };
    private static XUIManager sXUIManager;

    public static XUIManager getXUIManager() {
        return sXUIManager;
    }

    public static MediaCenterManager getMediaCenterManager() {
        try {
            if (!isXUIConnected()) {
                return null;
            }
            MediaCenterManager mediaCenterManager = (MediaCenterManager) sXUIManager.getXUIServiceManager("mediacenter");
            return mediaCenterManager;
        } catch (XUIServiceNotConnectedException e) {
            e.printStackTrace();
            return null;
        }
    }

    private static boolean isXUIConnected() {
        XUIManager xUIManager = sXUIManager;
        return xUIManager != null && xUIManager.isConnected();
    }

    public static void init(Context context) {
        sXUIManager = XUIManager.createXUIManager(context, sServiceConnection);
        sXUIManager.connect();
    }

    public static void deinit() {
        XUIManager xUIManager = sXUIManager;
        if (xUIManager != null) {
            xUIManager.disconnect();
        }
    }
}
