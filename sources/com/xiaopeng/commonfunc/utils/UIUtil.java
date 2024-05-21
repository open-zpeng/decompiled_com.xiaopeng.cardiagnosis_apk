package com.xiaopeng.commonfunc.utils;

import android.content.Context;
import android.text.Html;
import android.text.Spanned;
import android.text.TextUtils;
import android.util.SparseArray;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import com.xiaopeng.lib.utils.LogUtils;
import com.xiaopeng.lib.utils.ThreadUtils;
import com.xiaopeng.xui.app.XDialog;
import com.xiaopeng.xui.app.XDialogInterface;
import com.xiaopeng.xui.app.XDialogSystemType;
import com.xiaopeng.xui.app.XToast;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
/* loaded from: classes4.dex */
public class UIUtil {
    public static final String BLACK = "#000000";
    public static final String BLUE = "#0000ff";
    public static final String GREEN = "#00ff00";
    public static final String RED = "#ff0000";
    private static final String TAG = "UIUtil";
    public static final String WHITE = "#ffffff";

    public static SparseArray<Button> createUI(ViewGroup viewGroup, Context context, View.OnClickListener l, HashMap<Integer, String> functions, int sumPerRow, int backgroundres) {
        int i;
        float f;
        SparseArray<Button> buttonArray = new SparseArray<>();
        viewGroup.removeAllViews();
        LinearLayout layout = new LinearLayout(context);
        float f2 = 1.0f;
        LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(-1, -2, 1.0f);
        int i2 = 5;
        int i3 = 0;
        layoutParams.setMargins(0, 5, 0, 5);
        layout.setLayoutParams(layoutParams);
        layout.setOrientation(0);
        layout.setWeightSum(sumPerRow * 1.0f);
        layout.setBaselineAligned(false);
        int i4 = 0;
        for (Map.Entry<Integer, String> entry : functions.entrySet()) {
            i4++;
            Button button = new Button(context);
            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(i3, 100, f2);
            params.setMargins(i2, i2, i2, i2);
            button.setLayoutParams(params);
            button.setId(entry.getKey().intValue());
            button.setTextColor(-16777216);
            button.setTextSize(30.0f);
            button.setText(entry.getValue());
            button.setBackgroundResource(backgroundres);
            button.setOnClickListener(l);
            LogUtils.d(TAG, "createUI", "Create Button. id=" + entry.getKey() + ", name=" + entry.getValue());
            layout.addView(button);
            buttonArray.put(entry.getKey().intValue(), button);
            if (i4 % sumPerRow != 0) {
                i = 0;
                f = 1.0f;
            } else {
                viewGroup.addView(layout);
                layout = new LinearLayout(context);
                layout.setLayoutParams(layoutParams);
                i = 0;
                layout.setOrientation(0);
                f = 1.0f;
                layout.setWeightSum(sumPerRow * 1.0f);
                layout.setBaselineAligned(false);
            }
            i2 = 5;
            float f3 = f;
            i3 = i;
            f2 = f3;
        }
        if (i4 % sumPerRow != 0) {
            viewGroup.addView(layout);
        }
        return buttonArray;
    }

    public static void createTextViewList(ViewGroup viewGroup, Context context, ArrayList<String> values, int sumPerRow) {
        viewGroup.removeAllViews();
        LinearLayout layout = new LinearLayout(context);
        LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(-1, -2, 1.0f);
        layoutParams.setMargins(0, 5, 0, 5);
        layout.setLayoutParams(layoutParams);
        layout.setOrientation(0);
        layout.setWeightSum(sumPerRow * 1.0f);
        int i = 0;
        Iterator<String> it = values.iterator();
        while (it.hasNext()) {
            String data = it.next();
            i++;
            TextView textView = new TextView(context);
            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(0, -2, 1.0f);
            params.setMargins(5, 5, 5, 5);
            textView.setLayoutParams(params);
            textView.setTextColor(-1);
            textView.setTextSize(32.0f);
            textView.setText(data);
            layout.addView(textView);
            if (i % sumPerRow == 0) {
                viewGroup.addView(layout);
                layout = new LinearLayout(context);
                layout.setLayoutParams(layoutParams);
                layout.setOrientation(0);
                layout.setWeightSum(sumPerRow * 1.0f);
            }
        }
        if (i % sumPerRow != 0) {
            viewGroup.addView(layout);
        }
    }

    public static void createTextViewList(ViewGroup viewGroup, Context context, Map<Integer, String> map, int row, int col) {
        viewGroup.removeAllViews();
        LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(-1, -2, 1.0f);
        layoutParams.setMargins(0, 5, 0, 5);
        for (Integer id : map.keySet()) {
            LinearLayout layout = new LinearLayout(context);
            layout.setLayoutParams(layoutParams);
            layout.setOrientation(0);
            layout.setWeightSum(col * 1.0f);
            for (int i = 0; i < col; i++) {
                TextView textView = new TextView(context);
                LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(0, -2, 1.0f);
                params.setMargins(5, 5, 5, 5);
                textView.setLayoutParams(params);
                textView.setTextSize(28.0f);
                textView.setTextColor(-1);
                if (i == 0) {
                    textView.setId(id.intValue());
                    textView.setText(map.get(id));
                } else {
                    textView.setId(id.intValue() + (i * row));
                    if (i == col - 1) {
                        textView.setGravity(5);
                    } else {
                        textView.setGravity(17);
                    }
                }
                layout.addView(textView);
            }
            viewGroup.addView(layout);
        }
    }

    public static Spanned makeStringToSpanned(String other, String html, String color, Boolean prefix) {
        String str = "<font color=\"" + color + "\">" + html + "</font>";
        if (other != null) {
            if (prefix == null || prefix.booleanValue()) {
                return Html.fromHtml(other + str);
            }
            return Html.fromHtml(str + other);
        }
        return Html.fromHtml(str);
    }

    public static void showToast(final Context context, final int resId) {
        ThreadUtils.runOnMainThread(new Runnable() { // from class: com.xiaopeng.commonfunc.utils.-$$Lambda$UIUtil$0YGdAeAdUKGGUDoGG1qeqrJ_Qig
            @Override // java.lang.Runnable
            public final void run() {
                XToast.show(context.getString(resId));
            }
        });
    }

    public static void showToast(final String toast) {
        ThreadUtils.runOnMainThread(new Runnable() { // from class: com.xiaopeng.commonfunc.utils.-$$Lambda$UIUtil$TyI1LM_P9Ttpc13hBNiGJC1U3rI
            @Override // java.lang.Runnable
            public final void run() {
                UIUtil.lambda$showToast$1(toast);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public static /* synthetic */ void lambda$showToast$1(String toast) {
        if (!TextUtils.isEmpty(toast)) {
            XToast.show(toast);
        }
    }

    public static void showInfoConfirmation(final Context context, final int titleResid, final int messageResid, final int confirmResid) {
        ThreadUtils.runOnMainThread(new Runnable() { // from class: com.xiaopeng.commonfunc.utils.-$$Lambda$UIUtil$HmmYtckd7YsEqRm7uUh3BpPkJEM
            @Override // java.lang.Runnable
            public final void run() {
                UIUtil.lambda$showInfoConfirmation$2(context, titleResid, messageResid, confirmResid);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public static /* synthetic */ void lambda$showInfoConfirmation$2(Context context, int titleResid, int messageResid, int confirmResid) {
        XDialog dialog = new XDialog(context);
        dialog.setTitle(titleResid).setMessage(messageResid).setPositiveButton(DataHelp.getString(context, confirmResid), (XDialogInterface.OnClickListener) null).setSystemDialog(XDialogSystemType.TYPE_SYSTEM_DIALOG).show();
    }

    public static boolean checkUdiskWrittable(String toast) {
        if (AfterSalesHelper.getAfterSalesManager().getRepairMode() || !SystemPropertyUtil.isUdiskReadOnly()) {
            return true;
        }
        showToast(toast);
        return false;
    }
}
