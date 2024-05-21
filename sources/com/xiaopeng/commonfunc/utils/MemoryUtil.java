package com.xiaopeng.commonfunc.utils;

import android.content.Context;
import android.os.Looper;
import android.os.SystemClock;
import android.text.TextUtils;
import android.util.Log;
import android.util.SparseArray;
import android.util.SparseLongArray;
import com.xiaopeng.commonfunc.Constant;
import com.xiaopeng.lib.security.xmartv1.XmartV1Constants;
import com.xiaopeng.libconfig.ipc.IpcConfig;
import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
/* loaded from: classes4.dex */
public class MemoryUtil {
    private static final String[] APP_WHITE_LIST = {"com.xiaopeng.montecarlo", IpcConfig.App.CAR_OTA, "com.telenav.app.arp", "com.xiaopeng.napa", IpcConfig.App.SPEECH_PACKAGE, Constant.PACKAGE_NAME_INSTRUMENT};
    private static final int KEY_COMPACT_MEMORY = 1002;
    private static final int KEY_DROP_CACHE = 1001;
    private static final int MAX_MEM_LIMIT = 512000;
    private static final long MAX_TIME_LIMIT = 600000;
    private static final String MEM_LEAK_DETECT_FILE = "/proc/xmonitor/memleak";
    private static final String TAG = "MemoryUtil";
    private final SparseLongArray mLongArray;
    private final Object mObject;

    private MemoryUtil() {
        this.mObject = new Object();
        this.mLongArray = new SparseLongArray(2);
    }

    public static MemoryUtil getInstance() {
        return SingleInstance.INSTANCE;
    }

    public void killMemLeakPid(int pid) {
        if (Looper.myLooper() == Looper.getMainLooper()) {
            throw new RuntimeException("should work in a worker thread");
        }
        File file = new File(MEM_LEAK_DETECT_FILE);
        BufferedWriter bufferedWriter = null;
        FileWriter fileWriter = null;
        try {
            try {
                fileWriter = new FileWriter(file);
                bufferedWriter = new BufferedWriter(fileWriter);
                bufferedWriter.write(pid + "");
                bufferedWriter.flush();
                Log.i(TAG, "kill pid: " + pid);
            } catch (Exception e) {
                Log.e(TAG, "killMemLeakPid-->" + e.getMessage());
            }
        } finally {
            libcore.io.IoUtils.closeQuietly(bufferedWriter);
            libcore.io.IoUtils.closeQuietly(fileWriter);
        }
    }

    public boolean isMemLarge(Context context, int pid, int memory) {
        String[] strArr;
        String processName = ProcessUtil.getAppName(context, pid);
        int temp = memory * 4;
        if (temp <= MAX_MEM_LIMIT) {
            return false;
        }
        for (String process : APP_WHITE_LIST) {
            if (processName.contains(process)) {
                Log.i(TAG, "processName: " + processName + ", white list app: " + process);
                return false;
            }
        }
        return true;
    }

    public SparseArray<Integer> getMemLeakInfo() {
        if (Looper.myLooper() == Looper.getMainLooper()) {
            throw new RuntimeException("should work in a worker thread");
        }
        File file = new File(MEM_LEAK_DETECT_FILE);
        BufferedReader bufferedReader = null;
        FileReader fileReader = null;
        SparseArray<Integer> memoryInfo = new SparseArray<>();
        try {
            try {
                fileReader = new FileReader(file);
                bufferedReader = new BufferedReader(fileReader);
                String string = bufferedReader.readLine();
                Log.i(TAG, "meminfo--> " + string);
                String[] info = string.split(Constant.SEPARATOR_STRING);
                if (info.length > 1) {
                    memoryInfo.put(Integer.decode(info[0]).intValue(), Integer.decode(info[1]));
                }
            } catch (Exception e) {
                Log.e(TAG, "getMemLeakInfo-->" + e.getMessage());
            }
            return memoryInfo;
        } finally {
            libcore.io.IoUtils.closeQuietly(bufferedReader);
            libcore.io.IoUtils.closeQuietly(fileReader);
        }
    }

    public void dropCache() {
        if (Looper.myLooper() == Looper.getMainLooper()) {
            throw new RuntimeException("dropCache should work in a worker thread");
        }
        synchronized (this.mObject) {
            long time = this.mLongArray.get(1001);
            long currentTime = SystemClock.uptimeMillis();
            this.mLongArray.put(1001, currentTime);
            if (time == 0 || currentTime - time >= MAX_TIME_LIMIT) {
                Log.i(TAG, "dropCache");
                writeCmdToFile("1", "/proc/xmonitor/drop_inactive_cache");
            }
        }
    }

    private void writeCmdToFile(String cmd, String fileName) {
        if (cmd == null || TextUtils.isEmpty(fileName)) {
            return;
        }
        try {
            FileOutputStream fileOutputStream = new FileOutputStream(fileName);
            BufferedOutputStream outputStream = new BufferedOutputStream(fileOutputStream);
            try {
                outputStream.write(cmd.getBytes(XmartV1Constants.UTF8_ENCODING));
                outputStream.flush();
                $closeResource(null, outputStream);
                $closeResource(null, fileOutputStream);
            } catch (Throwable th) {
                try {
                    throw th;
                } catch (Throwable th2) {
                    $closeResource(th, outputStream);
                    throw th2;
                }
            }
        } catch (Exception e) {
            Log.w(TAG, "writeCmdToFile error: " + e.getMessage());
        }
    }

    private static /* synthetic */ void $closeResource(Throwable x0, AutoCloseable x1) {
        if (x0 == null) {
            x1.close();
            return;
        }
        try {
            x1.close();
        } catch (Throwable th) {
            x0.addSuppressed(th);
        }
    }

    public void compactMemory() {
        synchronized (this.mObject) {
            long time = this.mLongArray.get(1002);
            long currentTime = SystemClock.uptimeMillis();
            this.mLongArray.put(1002, currentTime);
            if (time == 0 || currentTime - time >= MAX_TIME_LIMIT) {
                Log.i(TAG, "compactMemory");
            }
        }
    }

    /* loaded from: classes4.dex */
    private static class SingleInstance {
        private static final MemoryUtil INSTANCE = new MemoryUtil();

        private SingleInstance() {
        }
    }
}
