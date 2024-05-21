package com.xiaopeng.commonfunc.utils;

import android.os.Environment;
import java.io.File;
/* loaded from: classes4.dex */
public class DeleteFileUtil {
    private static final String DATALOG = Environment.getDataDirectory().getAbsolutePath() + "/Log";
    private static final String Log0 = "log0";
    private static final String TAG = "DeleteFileUtil";

    public static void deleteSystemLog() {
        File dir = new File(DATALOG);
        String[] names = dir.list();
        if (names == null) {
            return;
        }
        for (String s : names) {
            File file = new File(dir, s);
            if (file.isDirectory()) {
                if (!isLog0Path(file.getName())) {
                    deleteDirWihtFile(file);
                }
            } else {
                file.delete();
            }
        }
    }

    private static void deleteDirWihtFile(File dir) {
        String[] names;
        if (dir == null) {
            return;
        }
        try {
            if (dir.exists() && dir.isDirectory() && (names = dir.list()) != null) {
                for (String s : names) {
                    File file = new File(dir, s);
                    if (file.isFile()) {
                        file.delete();
                    } else if (file.isDirectory()) {
                        deleteDirWihtFile(file);
                    }
                }
                dir.delete();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static boolean isLog0Path(String path) {
        return path.toLowerCase().endsWith(Log0);
    }

    public static long deleteStorage(String path, String suffix) {
        long totalSize = 0;
        File dir = new File(path);
        String[] names = dir.list();
        if (names == null) {
            return 0L;
        }
        for (String s : names) {
            File file = new File(dir, s);
            if (file.isDirectory()) {
                totalSize += deleteFileInterrupt(file, suffix);
            } else if (!Thread.currentThread().isInterrupted() && isSuffixmatch(file, suffix)) {
                totalSize += file.length();
                file.delete();
            }
        }
        return totalSize;
    }

    public static long deleteFileInterrupt(File dir, String suffix) {
        String[] names;
        long totalSize = 0;
        if (dir != null) {
            try {
            } catch (Exception e) {
                e.printStackTrace();
            }
            if (!dir.exists() || !dir.isDirectory() || (names = dir.list()) == null) {
                return 0L;
            }
            for (String s : names) {
                File file = new File(dir, s);
                if (file.isFile()) {
                    if (!Thread.currentThread().isInterrupted() && isSuffixmatch(file, suffix)) {
                        totalSize += file.length();
                        file.delete();
                    }
                } else if (file.isDirectory()) {
                    totalSize += deleteFileInterrupt(file, suffix);
                }
            }
            dir.delete();
            return totalSize;
        }
        return 0L;
    }

    private static boolean isSuffixmatch(File file, String suffix) {
        if (file.getName().endsWith(suffix)) {
            return true;
        }
        return false;
    }
}
