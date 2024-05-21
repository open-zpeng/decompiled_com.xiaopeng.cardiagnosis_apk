package com.xiaopeng.commonfunc.utils;

import android.app.ActivityManager;
import android.content.ComponentName;
import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.SystemProperties;
import com.xiaopeng.commonfunc.Constant;
import java.text.SimpleDateFormat;
import java.util.List;
/* loaded from: classes4.dex */
public class BugHunterUtils {
    private static final String APPROVED_DEVICE_INFO = "XiaoPeng";

    public static String getSystemVersion() {
        return SystemProperties.get("ro.product.firmware", "unknown");
    }

    public static String getDevice() {
        return "xp/" + SystemProperties.get("persist.sys.hardware_id");
    }

    public static String getSoftwareId() {
        return SystemProperties.get("ro.xiaopeng.software");
    }

    public static String getMCUVer() {
        return SystemProperties.get("sys.mcu.version");
    }

    public static long getUid() {
        return SystemProperties.getLong("persist.sys.account_uid", -1L);
    }

    public static String getVersionName(Context context) {
        return getPackageInfo(context, null).versionName;
    }

    public static String getVersionName(Context context, String packageName) {
        return getPackageInfo(context, packageName).versionName;
    }

    public static int getVersionCode(Context context) {
        return getPackageInfo(context, null).versionCode;
    }

    public static int getVersionCode(Context context, String packageName) {
        return getPackageInfo(context, packageName).versionCode;
    }

    private static PackageInfo getPackageInfo(Context context, String packageName) {
        try {
            PackageManager pm = context.getPackageManager();
            PackageInfo pi = pm.getPackageInfo(packageName != null ? packageName : context.getPackageName(), 16384);
            return pi;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public static String formatTime(long time) {
        return new SimpleDateFormat(TimeUtil.DATE_FORMAT_YYYYMMDDHHMMSS_WITH_SEPARATOR).format(Long.valueOf(time));
    }

    public static String getNetworkType(Context context) {
        ConnectivityManager connectMgr = (ConnectivityManager) context.getSystemService("connectivity");
        NetworkInfo info = connectMgr.getActiveNetworkInfo();
        if (info != null) {
            if (info.getType() == 0) {
                switch (info.getSubtype()) {
                    case 1:
                    case 2:
                    case 4:
                    case 7:
                        return "2G";
                    case 3:
                    case 5:
                    case 6:
                    case 8:
                    case 9:
                    case 10:
                    case 12:
                    case 14:
                    case 15:
                        return "3G";
                    case 11:
                    default:
                        return "";
                    case 13:
                        return Constant.BURNTEST.TEST_4G;
                }
            } else if (info.getType() == 1) {
                return Constant.BURNTEST.TEST_WIFI;
            } else {
                return "";
            }
        }
        return "";
    }

    public boolean isApplicationInBackground(Context context) {
        ActivityManager am = (ActivityManager) context.getSystemService("activity");
        List<ActivityManager.RunningTaskInfo> tasks = am.getRunningTasks(1);
        if (tasks != null && !tasks.isEmpty()) {
            ComponentName topActivity = tasks.get(0).topActivity;
            if (!topActivity.getPackageName().equals(context.getPackageName())) {
                return true;
            }
        }
        return false;
    }

    public static String getVin() {
        return SystemProperties.get("sys.xiaopeng.vin", "");
    }

    public static String getVid() {
        return SystemProperties.get("persist.sys.vehicle_id", "");
    }
}
