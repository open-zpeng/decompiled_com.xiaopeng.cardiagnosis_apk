package com.xiaopeng.xui.vui.floatinglayer;

import android.annotation.TargetApi;
import android.content.Context;
import android.graphics.BitmapFactory;
import android.graphics.ImageDecoder;
import android.graphics.drawable.Drawable;
import androidx.annotation.NonNull;
import com.xiaopeng.xpui.R;
import com.xiaopeng.xui.utils.XLogUtils;
import java.io.IOException;
/* loaded from: classes5.dex */
public class VuiImageDecoderUtils {
    private static final String TAG = VuiImageDecoderUtils.class.getSimpleName();
    private static final String TOUCH_DEFAULT_WEBP = "anim/floating_touch.webp";

    public static boolean isSupportNight(int type) {
        if (type == 1) {
            return true;
        }
        return false;
    }

    public static boolean isSupportAlpha(int type) {
        return false;
    }

    public static int getAnimateTimeOut(int type) {
        if (type != 1) {
            return 1500;
        }
        return 5500;
    }

    @TargetApi(28)
    public static Drawable decoderImage(@NonNull Context context, int type, boolean isNight) {
        String str = TAG;
        XLogUtils.d(str, "decoderImage type : " + type + ", isNight : " + isNight);
        if (type != 1) {
            ImageDecoder.Source source = ImageDecoder.createSource(context.getAssets(), TOUCH_DEFAULT_WEBP);
            try {
                Drawable mAnimatedImageDrawable = ImageDecoder.decodeDrawable(source);
                return mAnimatedImageDrawable;
            } catch (IOException e) {
                XLogUtils.w(TAG, "decodeException:", e);
                return null;
            }
        }
        return new VuiFloatingDrawable(BitmapFactory.decodeResource(context.getResources(), R.drawable.floating_element));
    }

    @TargetApi(28)
    public static Drawable decoderImage(@NonNull Context context, int type) {
        return decoderImage(context, type, false);
    }
}
