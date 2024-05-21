package com.xiaopeng.xui.utils;

import android.app.Dialog;
import android.content.Context;
import android.view.Window;
import android.view.WindowManager;
import com.xiaopeng.xpui.R;
/* loaded from: classes5.dex */
public class XDialogUtils {
    @Deprecated
    public static void setHorizontalCenter(Dialog dialog) {
        Window window;
        if (dialog != null && (window = dialog.getWindow()) != null) {
            WindowManager.LayoutParams lp = window.getAttributes();
            lp.gravity = 1;
            lp.x = 0;
            window.setAttributes(lp);
        }
    }

    @Deprecated
    public static void setHorizontal(Dialog dialog, int xOffset) {
        Window window;
        if (dialog != null && (window = dialog.getWindow()) != null) {
            WindowManager.LayoutParams lp = window.getAttributes();
            lp.gravity = 8388611;
            lp.x = xOffset;
            window.setAttributes(lp);
        }
    }

    @Deprecated
    public static void setVerticalCenter(Dialog dialog, int yOffset) {
        Window window;
        if (dialog != null && (window = dialog.getWindow()) != null) {
            WindowManager.LayoutParams lp = window.getAttributes();
            lp.gravity = 16;
            lp.y = 0;
            window.setAttributes(lp);
        }
    }

    @Deprecated
    public static void setVertical(Dialog dialog, int yOffset) {
        Window window;
        if (dialog != null && (window = dialog.getWindow()) != null) {
            WindowManager.LayoutParams lp = window.getAttributes();
            lp.gravity = 48;
            lp.y = yOffset;
            window.setAttributes(lp);
        }
    }

    @Deprecated
    public static void setVerticalHorizontal(Dialog dialog, int xOffset, int yOffset) {
        Window window;
        if (dialog != null && (window = dialog.getWindow()) != null) {
            WindowManager.LayoutParams lp = window.getAttributes();
            lp.gravity = 8388659;
            lp.x = xOffset;
            lp.y = yOffset;
            window.setAttributes(lp);
        }
    }

    public static Dialog createThemeDialog(Context context) {
        return new Dialog(context, R.style.XAppTheme_XDialog);
    }

    public static void setSystemDialog(Dialog dialog, int type) {
        if (dialog != null && dialog.getWindow() != null) {
            dialog.getWindow().setType(type);
        }
    }

    public static void handleSoftInput(Dialog dialog) {
        if (dialog != null && dialog.getWindow() != null) {
            dialog.getWindow().setSoftInputMode(16);
        }
    }

    public static void requestFullScreen(Dialog dialog) {
        if (dialog != null && dialog.getWindow() != null) {
            dialog.getWindow().requestFeature(15);
        }
    }
}
