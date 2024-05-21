package com.xiaopeng.commonfunc.utils;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Paint;
import android.graphics.Rect;
import android.hardware.display.DisplayManager;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Display;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.ListAdapter;
import android.widget.ListView;
/* loaded from: classes4.dex */
public class ScreenUtils {
    private static final String TAG = "ScreenUtils";
    private static int width = -1;
    private static int height = -1;

    private static void init(Context context) {
        Resources res = context.getResources();
        DisplayMetrics metrics = res.getDisplayMetrics();
        width = metrics.widthPixels;
        height = metrics.heightPixels;
    }

    public static int getScreenWidth(Context context) {
        if (width < 0) {
            init(context);
        }
        return width;
    }

    public static int getScreenHeight(Context context) {
        if (height < 0) {
            init(context);
        }
        return height;
    }

    public static int dip2px(Context context, float dipValue) {
        float density = context.getResources().getDisplayMetrics().density;
        return (int) ((dipValue * density) + 0.5f);
    }

    public static int px2dip(Context context, float pxValue) {
        float density = context.getResources().getDisplayMetrics().density;
        return (int) ((pxValue / density) + 0.5f);
    }

    public static int sp2px(Context context, float spValue) {
        float fontScale = context.getResources().getDisplayMetrics().scaledDensity;
        return (int) ((spValue * fontScale) + 0.5f);
    }

    public static int px2sp(Context context, float pxValue) {
        float fontScale = context.getResources().getDisplayMetrics().scaledDensity;
        return (int) ((pxValue / fontScale) + 0.5f);
    }

    public static float getTextWidth(String text) {
        Paint paint = new Paint(1);
        return paint.measureText(text);
    }

    public static float getTextHeight(String text) {
        Paint pFont = new Paint();
        Rect rect = new Rect();
        pFont.getTextBounds(text, 0, 1, rect);
        return rect.height();
    }

    public static int setListViewHeightBasedOnChildren(ListView listView) {
        ListAdapter listAdapter = listView.getAdapter();
        if (listAdapter == null) {
            return 0;
        }
        int totalHeight = 0;
        for (int i = 0; i < listAdapter.getCount(); i++) {
            View listItem = listAdapter.getView(i, null, listView);
            listItem.measure(0, 0);
            totalHeight += listItem.getMeasuredHeight();
        }
        ViewGroup.LayoutParams params = listView.getLayoutParams();
        params.height = (listView.getDividerHeight() * (listAdapter.getCount() - 1)) + totalHeight;
        listView.setLayoutParams(params);
        return totalHeight;
    }

    public static void hideSystemUi(Context context, Window window) {
    }

    public static void showSystemUi(Context context, Window window) {
    }

    public static int getStatusBarHeight(Context context) {
        int resourceId1 = context.getResources().getIdentifier("status_bar_height", "dimen", "android");
        int resourceId2 = context.getResources().getIdentifier("status_bar_bottom_height", "dimen", "android");
        try {
            int statusHeight = context.getResources().getDimensionPixelSize(resourceId1) + context.getResources().getDimensionPixelSize(resourceId2);
            return statusHeight;
        } catch (Exception e) {
            Log.d("UIUtils", "e: " + e.getMessage());
            return 0;
        }
    }

    public static int getStatusBarBottomHeight(Context context) {
        int resourceId = context.getResources().getIdentifier("status_bar_bottom_height", "dimen", "android");
        try {
            int statusHeight = context.getResources().getDimensionPixelSize(resourceId);
            return statusHeight;
        } catch (Exception e) {
            Log.d("UIUtils", "e: " + e.getMessage());
            return 0;
        }
    }

    public static int getIcmDisplayId(Context context) {
        int icmDisplayId = Integer.MIN_VALUE;
        DisplayManager displayManager = (DisplayManager) context.getSystemService("display");
        Display[] displays = displayManager.getDisplays();
        if (displays == null || displays.length == 0) {
            Log.d(TAG, "no exist display!");
            return Integer.MIN_VALUE;
        }
        int i = 0;
        while (true) {
            if (i >= displays.length) {
                break;
            }
            Log.d(TAG, "display " + displays[i].toString());
            if (displays[i].getType() != 6) {
                i++;
            } else {
                Log.d(TAG, "exist ICM! displayId = " + displays[i].getDisplayId());
                icmDisplayId = displays[i].getDisplayId();
                break;
            }
        }
        if (icmDisplayId == Integer.MIN_VALUE) {
            Log.d(TAG, "no exist ICM!");
        }
        return icmDisplayId;
    }
}
