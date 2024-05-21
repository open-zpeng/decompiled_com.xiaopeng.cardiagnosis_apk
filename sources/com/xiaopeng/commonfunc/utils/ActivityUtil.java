package com.xiaopeng.commonfunc.utils;

import android.app.ActivityManager;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import com.xiaopeng.lib.utils.LogUtils;
import java.util.Iterator;
import java.util.List;
/* loaded from: classes4.dex */
public class ActivityUtil {
    private static final String TAG = "ActivityUtil";

    public static String getTopActivity(Context context) {
        ActivityManager manager = (ActivityManager) context.getSystemService("activity");
        List<ActivityManager.RunningTaskInfo> runningTaskInfos = manager.getRunningTasks(1);
        if (runningTaskInfos != null && runningTaskInfos.size() > 0) {
            return runningTaskInfos.get(0).topActivity.toShortString();
        }
        return null;
    }

    public static String getTopPackageName(Context context) {
        ActivityManager manager = (ActivityManager) context.getSystemService("activity");
        List<ActivityManager.RunningTaskInfo> runningTaskInfos = manager.getRunningTasks(1);
        if (runningTaskInfos != null && runningTaskInfos.size() > 0) {
            return runningTaskInfos.get(0).topActivity.getPackageName();
        }
        return null;
    }

    public static void startActivity(Context context, Class cls) {
        Intent i = new Intent(context, cls);
        i.setFlags(335544320);
        context.startActivity(i);
    }

    public static void startActivity(Context context, Class cls, Bundle bundle) {
        Intent i = new Intent(context, cls);
        i.putExtras(bundle);
        i.setFlags(268468224);
        context.startActivity(i);
    }

    public static void startActivity(Context context, Class cls, int type) {
        Intent intent = new Intent(context, cls);
        intent.putExtra("TYPE", type);
        intent.addFlags(335544320);
        context.startActivity(intent);
    }

    public static void startActivity(Context context, String packageName, String clsName) {
        Intent i = new Intent();
        i.setClassName(packageName, clsName);
        i.setFlags(335544320);
        context.startActivity(i);
    }

    public static boolean finishAndRemoveAllTasks(Context context) {
        ActivityManager am = (ActivityManager) context.getSystemService("activity");
        if (am == null) {
            return false;
        }
        try {
            List<ActivityManager.AppTask> appTasks = am.getAppTasks();
            for (ActivityManager.AppTask appTask : appTasks) {
                LogUtils.w(TAG, "will finish and remove task: id=" + appTask.getTaskInfo().id);
                appTask.finishAndRemoveTask();
            }
            return true;
        } catch (SecurityException e) {
            LogUtils.e(TAG, e.toString());
            return false;
        }
    }

    public static boolean isRunService(Context context, String serviceName) {
        boolean res = false;
        ActivityManager manager = (ActivityManager) context.getSystemService("activity");
        Iterator<ActivityManager.RunningServiceInfo> it = manager.getRunningServices(Integer.MAX_VALUE).iterator();
        while (true) {
            if (!it.hasNext()) {
                break;
            }
            ActivityManager.RunningServiceInfo service = it.next();
            if (serviceName.equals(service.service.getClassName())) {
                res = true;
                break;
            }
        }
        LogUtils.i(TAG, "[%s ] isRunService : %s", serviceName, Boolean.valueOf(res));
        return res;
    }
}
