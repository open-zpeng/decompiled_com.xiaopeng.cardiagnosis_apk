package com.xiaopeng.commonfunc.system.listener;

import android.hardware.input.InputManager;
import android.view.KeyEvent;
import com.xiaopeng.IXPKeyListener;
import com.xiaopeng.lib.utils.LogUtils;
import com.xiaopeng.lib.utils.ThreadUtils;
/* loaded from: classes4.dex */
public class KeyEventListener extends IXPKeyListener.Stub {
    private static final String TAG = "CommonFuncKeyEventListener";
    private final InputManager mInputManager = InputManager.getInstance();
    private final KeyUpCallback mKeyUpCallback;

    /* loaded from: classes4.dex */
    public interface KeyUpCallback {
        void onKeyUp(KeyEvent keyEvent);
    }

    public KeyEventListener(KeyUpCallback callback) {
        this.mKeyUpCallback = callback;
    }

    public void register() {
        InputManager inputManager = this.mInputManager;
        if (inputManager != null) {
            inputManager.registerListener(this, TAG, true);
        }
    }

    public int notify(final KeyEvent event, String extra) {
        int keycode = event.getKeyCode();
        int action = event.getAction();
        LogUtils.d(TAG, "notify keycode=" + keycode + " action=" + action);
        if (action == 1 && this.mKeyUpCallback != null) {
            ThreadUtils.runOnMainThread(new Runnable() { // from class: com.xiaopeng.commonfunc.system.listener.-$$Lambda$KeyEventListener$SEU0EiQiByGOL7W4dPO2CAi5-l0
                @Override // java.lang.Runnable
                public final void run() {
                    KeyEventListener.this.lambda$notify$0$KeyEventListener(event);
                }
            });
            return 0;
        }
        return 0;
    }

    public /* synthetic */ void lambda$notify$0$KeyEventListener(KeyEvent event) {
        this.mKeyUpCallback.onKeyUp(event);
    }
}
