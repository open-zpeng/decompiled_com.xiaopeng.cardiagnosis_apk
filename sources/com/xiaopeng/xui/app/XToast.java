package com.xiaopeng.xui.app;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;
import androidx.annotation.LayoutRes;
import androidx.annotation.StringRes;
import com.xiaopeng.commonfunc.Constant;
import com.xiaopeng.xpui.R;
import com.xiaopeng.xui.Xui;
import com.xiaopeng.xui.utils.XCharacterUtils;
import com.xiaopeng.xui.widget.XTextView;
/* loaded from: classes5.dex */
public class XToast {
    private XToast() {
    }

    private static Context getApplicationContext() {
        return Xui.getContext();
    }

    public static void show(@StringRes int msgRes) {
        show(Xui.getContext().getText(msgRes));
    }

    public static void show(CharSequence msg) {
        int size = charactersSize(msg);
        if (size > 8) {
            showLong(msg);
        } else {
            showShort(msg);
        }
    }

    public static void showShort(@StringRes int msgRes) {
        showShort(Xui.getContext().getText(msgRes));
    }

    public static void showShort(CharSequence msg) {
        show(msg, 0);
    }

    public static void showLong(@StringRes int msgRes) {
        showLong(Xui.getContext().getText(msgRes));
    }

    public static void showLong(CharSequence msg) {
        show(msg, 1);
    }

    private static void show(CharSequence msg, int duration) {
        Toast toast = makeToast(R.layout.x_toast);
        View view = toast.getView();
        XTextView textView = (XTextView) view.findViewById(R.id.textView);
        toast.setDuration(duration);
        textView.setText(msg);
        toast.show();
    }

    private static Toast makeToast(@LayoutRes int layoutId) {
        Context context = getApplicationContext();
        View view = LayoutInflater.from(context).inflate(layoutId, (ViewGroup) null);
        Toast toast = new Toast(context);
        toast.setGravity(8388661, 0, 0);
        toast.setView(view);
        return toast;
    }

    private static int charactersSize(CharSequence msg) {
        if (msg == null) {
            return 0;
        }
        String[] parts = msg.toString().trim().split(Constant.SPACE_STRING);
        int count = 0;
        for (String part : parts) {
            if (part.trim().length() != 0) {
                boolean hasFull = false;
                int lastCharacterType = -1;
                for (int i = 0; i < part.length(); i++) {
                    char c = part.charAt(i);
                    if (XCharacterUtils.isFullAngle(c)) {
                        if (lastCharacterType == 0) {
                            count++;
                        }
                        lastCharacterType = 1;
                        count++;
                        hasFull = true;
                    } else {
                        lastCharacterType = 0;
                    }
                }
                if (hasFull) {
                    if (lastCharacterType == 0) {
                        count++;
                    }
                } else {
                    count++;
                }
            }
        }
        return count;
    }
}
