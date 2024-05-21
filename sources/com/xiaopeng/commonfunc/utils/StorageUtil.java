package com.xiaopeng.commonfunc.utils;

import android.os.Environment;
import android.util.Log;
import java.io.File;
/* loaded from: classes4.dex */
public class StorageUtil {
    public static final int AUTO_CLEAN_STORAGE_LOW_PERCENTAGE = 5;
    public static final int NEW_STORAGE_LOW_PERCENTAGE = 10;
    private static final String TAG = "StorageUtil";

    public static boolean checkStorage(int lowPercentage) {
        File file = Environment.getDataDirectory();
        Log.d(TAG, file.getPath());
        if (file.getUsableSpace() < (file.getTotalSpace() * lowPercentage) / 100) {
            return true;
        }
        return false;
    }
}
