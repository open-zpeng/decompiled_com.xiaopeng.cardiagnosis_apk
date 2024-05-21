package com.xiaopeng.xui;

import android.app.Application;
import android.content.Context;
import android.util.Log;
import com.xiaopeng.xpui.BuildConfig;
import com.xiaopeng.xui.drawable.shimmer.XShimmer;
import com.xiaopeng.xui.utils.XLogUtils;
/* loaded from: classes5.dex */
public class Xui {
    private static Application mApp;
    private static boolean sDialogFullScreen;
    private static boolean sFontScaleDynamicChangeEnable;
    private static boolean sVuiEnable;

    public static void init(Application app) {
        mApp = app;
        Log.i("xpui", BuildConfig.BUILD_VERSION);
        XShimmer.msGlobalEnable = false;
    }

    public static Context getContext() {
        Application application = mApp;
        if (application == null) {
            throw new RuntimeException("Xui must be call Xui#init()!");
        }
        return application;
    }

    public static boolean isVuiEnable() {
        return sVuiEnable;
    }

    public static void setVuiEnable(boolean vuiEnable) {
        sVuiEnable = vuiEnable;
    }

    public static boolean isFontScaleDynamicChangeEnable() {
        return sFontScaleDynamicChangeEnable;
    }

    public static void setFontScaleDynamicChangeEnable(boolean fontScaleDynamicChangeEnable) {
        sFontScaleDynamicChangeEnable = fontScaleDynamicChangeEnable;
    }

    public static boolean isDialogFullScreen() {
        return sDialogFullScreen;
    }

    public static void setDialogFullScreen(boolean dialogFullScreen) {
        sDialogFullScreen = dialogFullScreen;
    }

    public static void setLogLevel(int level) {
        XLogUtils.setLogLevel(level);
    }

    public static void release() {
    }

    public static void clear() {
    }
}
