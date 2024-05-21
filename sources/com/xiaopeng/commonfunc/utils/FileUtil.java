package com.xiaopeng.commonfunc.utils;

import android.annotation.SuppressLint;
import android.app.usage.StorageStats;
import android.app.usage.StorageStatsManager;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.hardware.usb.UsbDevice;
import android.hardware.usb.UsbManager;
import android.os.Environment;
import android.os.StatFs;
import android.os.UserHandle;
import android.os.storage.StorageManager;
import android.os.storage.StorageVolume;
import android.os.storage.VolumeInfo;
import android.text.TextUtils;
import com.xiaopeng.commonfunc.Constant;
import com.xiaopeng.commonfunc.bean.event.CopyFileTestResult;
import com.xiaopeng.commonfunc.system.runnable.CopyFileTask;
import com.xiaopeng.commonfunc.system.runnable.Sleep;
import com.xiaopeng.lib.utils.FileUtils;
import com.xiaopeng.lib.utils.LogUtils;
import com.xiaopeng.lib.utils.ThreadUtils;
import java.io.BufferedReader;
import java.io.Closeable;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.FilenameFilter;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.RandomAccessFile;
import java.math.BigInteger;
import java.nio.channels.FileChannel;
import java.nio.channels.FileLock;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;
import java.util.zip.ZipInputStream;
import java.util.zip.ZipOutputStream;
import net.lingala.zip4j.util.InternalZipConstants;
/* loaded from: classes4.dex */
public class FileUtil {
    private static final String TAG = "FileUtil";
    public static final int TF_MOUNT = 2;
    public static final int TF_NOT_EXIST = 0;
    public static final int TF_UNMOUNT = 1;

    public static String getFileName(String path) {
        try {
            String name = new File(path).getName();
            return name;
        } catch (Exception e) {
            e.printStackTrace();
            return "";
        }
    }

    public static void createFile(Context context, String fileName, int id) {
        File file = new File(Environment.getRootDirectory().getParent() + fileName);
        if (file.exists()) {
            return;
        }
        InputStream in = context.getResources().openRawResource(id);
        FileOutputStream out = null;
        byte[] buffer = new byte[1024];
        try {
            out = new FileOutputStream(file);
            while (true) {
                int len = in.read(buffer);
                if (len == -1) {
                    out.flush();
                    return;
                }
                out.write(buffer, 0, len);
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            closeQuietly(out);
            closeQuietly(in);
        }
    }

    /* JADX WARN: Unsupported multi-entry loop pattern (BACK_EDGE: B:7:0x001f -> B:22:0x002f). Please submit an issue!!! */
    public static void write(String path, byte[] data) {
        File out = new File(path);
        FileOutputStream fot = null;
        try {
            try {
                try {
                    fot = new FileOutputStream(out);
                    fot.write(data);
                    fot.flush();
                    fot.getFD().sync();
                    fot.close();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            } catch (Exception e2) {
                e2.printStackTrace();
                if (fot != null) {
                    fot.close();
                }
            }
        } catch (Throwable th) {
            if (fot != null) {
                try {
                    fot.close();
                } catch (Exception e3) {
                    e3.printStackTrace();
                }
            }
            throw th;
        }
    }

    public static boolean write(String path, String value) {
        boolean res = true;
        FileWriter writer = null;
        try {
            try {
                writer = new FileWriter(path);
                writer.write(value);
            } catch (Exception e) {
                e.printStackTrace();
                res = false;
            }
            closeQuietly(writer);
            LogUtils.d(TAG, "write path=" + path + ", value=" + value + ", res=" + res);
            return res;
        } catch (Throwable th) {
            closeQuietly(writer);
            throw th;
        }
    }

    public static boolean write(String path, String value, boolean append) {
        boolean res = true;
        FileWriter writer = null;
        try {
            try {
                writer = new FileWriter(path, append);
                writer.write(value);
            } catch (Exception e) {
                e.printStackTrace();
                res = false;
            }
            closeQuietly(writer);
            LogUtils.d(TAG, "write path=" + path + ", value=" + value + ", res=" + res);
            return res;
        } catch (Throwable th) {
            closeQuietly(writer);
            throw th;
        }
    }

    public static boolean writeNCreateFile(String path, byte[] data) {
        boolean res = true;
        File file = new File(path);
        FileOutputStream fot = null;
        try {
            try {
                if (!file.exists()) {
                    file.getParentFile().mkdirs();
                    file.createNewFile();
                }
                fot = new FileOutputStream(file);
                fot.write(data);
                fot.flush();
                fot.getFD().sync();
            } catch (Exception e) {
                e.printStackTrace();
                res = false;
            }
            return res;
        } finally {
            closeQuietly(fot);
        }
    }

    public static boolean writeNCreateFile(String path, String value) {
        boolean res = true;
        FileWriter writer = null;
        try {
            try {
                try {
                    File file = new File(path);
                    if (!file.exists()) {
                        file.getParentFile().mkdirs();
                        file.createNewFile();
                    }
                    writer = new FileWriter(path);
                    writer.write(value);
                    writer.flush();
                    writer.close();
                } catch (Exception e) {
                    e.printStackTrace();
                    res = false;
                    if (writer != null) {
                        writer.flush();
                        writer.close();
                    }
                }
            } catch (IOException e2) {
                e2.printStackTrace();
                res = false;
            }
            LogUtils.d(TAG, "writeFile path=" + path + ", value=" + value + "res=" + res);
            return res;
        } catch (Throwable th) {
            if (writer != null) {
                try {
                    writer.flush();
                    writer.close();
                } catch (IOException e3) {
                    e3.printStackTrace();
                }
            }
            throw th;
        }
    }

    public static boolean writeNCreateFile(String path, String value, boolean append) {
        boolean res = true;
        FileWriter writer = null;
        try {
            try {
                File file = new File(path);
                if (!file.exists()) {
                    file.getParentFile().mkdirs();
                    file.createNewFile();
                }
                writer = new FileWriter(path, append);
                writer.write(value);
            } catch (Exception e) {
                e.printStackTrace();
                res = false;
            }
            closeQuietly(writer);
            LogUtils.d(TAG, "writeFile path=" + path + ", value=" + value + ", append=" + append + ", res=" + res);
            return res;
        } catch (Throwable th) {
            closeQuietly(writer);
            throw th;
        }
    }

    /* JADX WARN: Unsupported multi-entry loop pattern (BACK_EDGE: B:7:0x001a -> B:24:0x002a). Please submit an issue!!! */
    public static void writeFile(File file, byte[] data) {
        FileOutputStream fot = null;
        try {
            try {
                try {
                    fot = new FileOutputStream(file);
                    fot.write(data);
                    fot.flush();
                    fot.getFD().sync();
                    fot.close();
                } catch (Exception e) {
                    e.printStackTrace();
                    if (fot != null) {
                        fot.close();
                    }
                }
            } catch (Exception e2) {
                e2.printStackTrace();
            }
        } catch (Throwable th) {
            if (fot != null) {
                try {
                    fot.close();
                } catch (Exception e3) {
                    e3.printStackTrace();
                }
            }
            throw th;
        }
    }

    public static long readLongVal(String path) {
        long retVal = -1;
        BufferedReader reader = null;
        try {
            try {
                try {
                    reader = new BufferedReader(new FileReader(path));
                    String value = reader.readLine();
                    if (value != null) {
                        retVal = Long.parseLong(value.trim());
                    }
                    reader.close();
                } catch (Exception e) {
                    e.printStackTrace();
                    if (reader != null) {
                        reader.close();
                    }
                }
            } catch (IOException e2) {
                e2.printStackTrace();
            }
            LogUtils.d(TAG, "readLongVal path=" + path + ", retVal=" + retVal);
            return retVal;
        } catch (Throwable th) {
            if (reader != null) {
                try {
                    reader.close();
                } catch (IOException e3) {
                    e3.printStackTrace();
                }
            }
            throw th;
        }
    }

    /* JADX WARN: Code restructure failed: missing block: B:13:0x002b, code lost:
        if (android.text.TextUtils.isEmpty(r12) == false) goto L27;
     */
    /* JADX WARN: Code restructure failed: missing block: B:14:0x002d, code lost:
        r5 = new java.lang.String[]{r5};
     */
    /* JADX WARN: Code restructure failed: missing block: B:15:0x0032, code lost:
        r5 = r5.split(r12);
     */
    /* JADX WARN: Code restructure failed: missing block: B:16:0x0036, code lost:
        r6 = r5.length;
        r7 = 0;
     */
    /* JADX WARN: Code restructure failed: missing block: B:17:0x0038, code lost:
        if (r7 >= r6) goto L26;
     */
    /* JADX WARN: Code restructure failed: missing block: B:18:0x003a, code lost:
        r8 = r5[r7];
     */
    /* JADX WARN: Code restructure failed: missing block: B:19:0x0040, code lost:
        if (r8.contains(r11) == false) goto L18;
     */
    /* JADX WARN: Code restructure failed: missing block: B:21:0x0048, code lost:
        r0 = r8.replace(r11, "");
     */
    /* JADX WARN: Code restructure failed: missing block: B:22:0x004a, code lost:
        r7 = r7 + 1;
     */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct add '--show-bad-code' argument
    */
    public static java.lang.String read(java.lang.String r10, java.lang.String r11, java.lang.String r12) {
        /*
            r0 = 0
            r1 = 0
            r2 = 0
            r3 = 1
            r4 = 0
            java.io.BufferedReader r5 = new java.io.BufferedReader     // Catch: java.lang.Throwable -> L4e java.lang.Exception -> L50
            java.io.FileReader r6 = new java.io.FileReader     // Catch: java.lang.Throwable -> L4e java.lang.Exception -> L50
            r6.<init>(r10)     // Catch: java.lang.Throwable -> L4e java.lang.Exception -> L50
            r5.<init>(r6)     // Catch: java.lang.Throwable -> L4e java.lang.Exception -> L50
            r2 = r5
        L11:
            java.lang.String r5 = r2.readLine()     // Catch: java.lang.Throwable -> L4e java.lang.Exception -> L50
            r1 = r5
            if (r1 != 0) goto L19
            goto L54
        L19:
            boolean r5 = android.text.TextUtils.isEmpty(r11)     // Catch: java.lang.Throwable -> L4e java.lang.Exception -> L50
            if (r5 == 0) goto L21
            r0 = r1
            goto L54
        L21:
            boolean r5 = r1.contains(r11)     // Catch: java.lang.Throwable -> L4e java.lang.Exception -> L50
            if (r5 == 0) goto L11
            boolean r5 = android.text.TextUtils.isEmpty(r12)     // Catch: java.lang.Throwable -> L4e java.lang.Exception -> L50
            if (r5 == 0) goto L32
            java.lang.String[] r5 = new java.lang.String[r3]     // Catch: java.lang.Throwable -> L4e java.lang.Exception -> L50
            r5[r4] = r1     // Catch: java.lang.Throwable -> L4e java.lang.Exception -> L50
            goto L36
        L32:
            java.lang.String[] r5 = r1.split(r12)     // Catch: java.lang.Throwable -> L4e java.lang.Exception -> L50
        L36:
            int r6 = r5.length     // Catch: java.lang.Throwable -> L4e java.lang.Exception -> L50
            r7 = r4
        L38:
            if (r7 >= r6) goto L4d
            r8 = r5[r7]     // Catch: java.lang.Throwable -> L4e java.lang.Exception -> L50
            boolean r9 = r8.contains(r11)     // Catch: java.lang.Throwable -> L4e java.lang.Exception -> L50
            if (r9 == 0) goto L4a
            java.lang.String r6 = ""
            java.lang.String r6 = r8.replace(r11, r6)     // Catch: java.lang.Throwable -> L4e java.lang.Exception -> L50
            r0 = r6
            goto L4d
        L4a:
            int r7 = r7 + 1
            goto L38
        L4d:
            goto L54
        L4e:
            r3 = move-exception
            goto L6d
        L50:
            r5 = move-exception
            r5.printStackTrace()     // Catch: java.lang.Throwable -> L4e
        L54:
            closeQuietly(r2)
            r5 = 4
            java.lang.Object[] r5 = new java.lang.Object[r5]
            r5[r4] = r10
            r5[r3] = r11
            r3 = 2
            r5[r3] = r12
            r3 = 3
            r5[r3] = r0
            java.lang.String r3 = "FileUtil"
            java.lang.String r4 = "read path[%s] tag[%s] split[%s] value[%s]"
            com.xiaopeng.lib.utils.LogUtils.i(r3, r4, r5)
            return r0
        L6d:
            closeQuietly(r2)
            throw r3
        */
        throw new UnsupportedOperationException("Method not decompiled: com.xiaopeng.commonfunc.utils.FileUtil.read(java.lang.String, java.lang.String, java.lang.String):java.lang.String");
    }

    public static String read(String path) {
        String value = null;
        BufferedReader reader = null;
        try {
            try {
                reader = new BufferedReader(new FileReader(path));
                value = reader.readLine();
                if (value != null) {
                    value = value.trim();
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
            closeQuietly(reader);
            LogUtils.d(TAG, "read path=" + path + ", value=" + value);
            return value;
        } catch (Throwable th) {
            closeQuietly(reader);
            throw th;
        }
    }

    public static String readAll(String path, String separator) {
        StringBuilder sb = new StringBuilder();
        BufferedReader reader = null;
        try {
            try {
                reader = new BufferedReader(new FileReader(path));
                while (true) {
                    String temp_value = reader.readLine();
                    if (temp_value == null) {
                        break;
                    }
                    sb.append(separator);
                    sb.append(temp_value);
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
            closeQuietly(reader);
            LogUtils.d(TAG, "readAll path=" + path + ", value=" + ((Object) sb));
            return sb.toString();
        } catch (Throwable th) {
            closeQuietly(reader);
            throw th;
        }
    }

    public static String readAll(String path) {
        BufferedReader reader = null;
        StringBuilder sb = new StringBuilder();
        try {
            try {
                try {
                    reader = new BufferedReader(new FileReader(path));
                    while (true) {
                        String temp_value = reader.readLine();
                        if (temp_value == null) {
                            break;
                        }
                        sb.append(temp_value);
                        sb.append("\n");
                    }
                    reader.close();
                } catch (Exception e) {
                    e.printStackTrace();
                    if (reader != null) {
                        reader.close();
                    }
                }
            } catch (IOException e2) {
                e2.printStackTrace();
            }
            return sb.toString();
        } catch (Throwable th) {
            if (reader != null) {
                try {
                    reader.close();
                } catch (IOException e3) {
                    e3.printStackTrace();
                }
            }
            throw th;
        }
    }

    public static byte[] readFile(String fileName) {
        byte[] buffer1 = new byte[0];
        InputStream inputStream = null;
        try {
            try {
                inputStream = new FileInputStream(new File(Environment.getRootDirectory().getParent() + fileName));
                byte[] buffer = new byte[4096];
                int len = inputStream.read(buffer);
                buffer1 = new byte[len];
                for (int i = 0; i < len; i++) {
                    buffer1[i] = buffer[i];
                }
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            } catch (IOException e2) {
                e2.printStackTrace();
            }
            return buffer1;
        } finally {
            closeQuietly(inputStream);
        }
    }

    public static int readVal(String path) {
        int retVal = -1;
        BufferedReader reader = null;
        try {
            try {
                reader = new BufferedReader(new FileReader(path));
                String value = reader.readLine();
                if (value != null) {
                    retVal = Integer.parseInt(value.trim());
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
            closeQuietly(reader);
            LogUtils.d(TAG, "readVal path=" + path + ", retVal=" + retVal);
            return retVal;
        } catch (Throwable th) {
            closeQuietly(reader);
            throw th;
        }
    }

    public static double readDoubleVal(String path) {
        double retVal = -1.0d;
        BufferedReader reader = null;
        try {
            try {
                try {
                    reader = new BufferedReader(new FileReader(path));
                    String value = reader.readLine();
                    if (value != null) {
                        retVal = Double.parseDouble(value.trim());
                    }
                    reader.close();
                } catch (Exception e) {
                    e.printStackTrace();
                    if (reader != null) {
                        reader.close();
                    }
                }
            } catch (IOException e2) {
                e2.printStackTrace();
            }
            LogUtils.d(TAG, "readDoubleVal path=" + path + ", retVal=" + retVal);
            return retVal;
        } catch (Throwable th) {
            if (reader != null) {
                try {
                    reader.close();
                } catch (IOException e3) {
                    e3.printStackTrace();
                }
            }
            throw th;
        }
    }

    public static boolean deleteFile(String path) {
        if (path == null || path.length() <= 0) {
            return false;
        }
        File file = new File(path);
        if (!file.exists() || !file.delete()) {
            return false;
        }
        LogUtils.i(TAG, "delete file " + path + " successful");
        return true;
    }

    public static boolean mkDir(String path) {
        boolean res = false;
        if (path != null && path.length() > 0) {
            int index = path.lastIndexOf(InternalZipConstants.ZIP_FILE_SEPARATOR);
            String dir = path.substring(0, index);
            File f = new File(dir);
            res = !f.exists() ? f.mkdirs() : true;
        }
        LogUtils.d(TAG, "mkDir path=" + path + ", res=" + res);
        return res;
    }

    public static void deleteFolderFile(String filePath) {
        File[] files;
        if (!TextUtils.isEmpty(filePath)) {
            try {
                File file = new File(filePath);
                if (file.isDirectory() && (files = file.listFiles()) != null) {
                    for (File file2 : files) {
                        deleteFolderFile(file2.getAbsolutePath());
                    }
                }
                if (!file.isDirectory()) {
                    file.delete();
                    return;
                }
                File[] files2 = file.listFiles();
                if (files2 != null && files2.length == 0) {
                    file.delete();
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    public static void deleteFolderFile(String filePath, String excludePath, boolean removeFolder) {
        File[] files;
        File[] files2;
        if (!TextUtils.isEmpty(filePath) && !filePath.equals(excludePath)) {
            try {
                File file = new File(filePath);
                if (file.isDirectory() && (files2 = file.listFiles()) != null) {
                    for (File file2 : files2) {
                        deleteFolderFile(file2.getAbsolutePath(), excludePath, true);
                    }
                }
                if (!file.isDirectory()) {
                    file.delete();
                } else if (removeFolder && (files = file.listFiles()) != null && files.length == 0) {
                    file.delete();
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    public static void deleteFolderFile(String filePath, String[] excludePaths, boolean removeFolder) {
        File[] files;
        File[] files2;
        if (!TextUtils.isEmpty(filePath) && !DataHelp.isStrArraysContainStr(excludePaths, filePath)) {
            try {
                File file = new File(filePath);
                if (file.isDirectory() && (files2 = file.listFiles()) != null) {
                    for (File file2 : files2) {
                        deleteFolderFile(file2.getAbsolutePath(), excludePaths, true);
                    }
                }
                if (!file.isDirectory()) {
                    file.delete();
                } else if (removeFolder && (files = file.listFiles()) != null && files.length == 0) {
                    file.delete();
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    public static void deleteFolderFile(String[] filePaths, String[] excludePaths, boolean removeFolder) {
        File[] files;
        File[] files2;
        for (String filePath : filePaths) {
            if (!TextUtils.isEmpty(filePath) && !DataHelp.isStrArraysContainStr(excludePaths, filePath)) {
                try {
                    File file = new File(filePath);
                    if (file.isDirectory() && (files2 = file.listFiles()) != null) {
                        for (File file2 : files2) {
                            deleteFolderFile(file2.getAbsolutePath(), excludePaths, true);
                        }
                    }
                    if (!file.isDirectory()) {
                        file.delete();
                    } else if (removeFolder && (files = file.listFiles()) != null && files.length == 0) {
                        file.delete();
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
    }

    public static String getFileNameWithSuffix(String filePath, String suffix) {
        String name = null;
        File fileFolder = new File(filePath);
        if (fileFolder.exists()) {
            File[] filesList = fileFolder.listFiles();
            if (filesList != null) {
                for (int i = 0; i < filesList.length; i++) {
                    if (!filesList[i].isDirectory() && filesList[i].getName().endsWith(suffix)) {
                        name = filesList[i].getName();
                    }
                }
            }
        } else {
            LogUtils.e(TAG, "getFileNameWithSuffix filePath " + filePath + " not exist");
        }
        LogUtils.d(TAG, "getFileNameWithSuffix name " + name);
        return name;
    }

    public static String getFileNameWithSuffixNContains(String filePath, String suffix, String contain) {
        String name = null;
        File fileFolder = new File(filePath);
        if (fileFolder.exists()) {
            File[] filesList = fileFolder.listFiles();
            if (filesList != null) {
                for (int i = 0; i < filesList.length; i++) {
                    if (!filesList[i].isDirectory() && filesList[i].getName().endsWith(suffix) && filesList[i].getName().contains(contain)) {
                        name = filesList[i].getName();
                    }
                }
            }
        } else {
            LogUtils.e(TAG, "getFileNameWithSuffix filePath " + filePath + " not exist");
        }
        LogUtils.d(TAG, "getFileNameWithSuffix name " + name);
        return name;
    }

    public static String getFilePathWithSuffix(String filePath, String suffix) {
        String path = null;
        File fileFolder = new File(filePath);
        if (fileFolder.exists()) {
            File[] filesList = fileFolder.listFiles();
            if (filesList != null) {
                for (int i = 0; i < filesList.length; i++) {
                    if (!filesList[i].isDirectory() && filesList[i].getName().endsWith(suffix)) {
                        path = filesList[i].getPath();
                    }
                }
            }
        } else {
            LogUtils.e(TAG, "getFilePathWithSuffix filePath " + filePath + " not exist");
        }
        LogUtils.d(TAG, "getFilePathWithSuffix name " + path);
        return path;
    }

    public static ArrayList<String> getFileList(String path, String file, final String regex, boolean order, long startTime, long endTime) {
        StringBuilder sb;
        ArrayList<String> paths = new ArrayList<>();
        File[] dirs = new File(path).listFiles(new FilenameFilter() { // from class: com.xiaopeng.commonfunc.utils.-$$Lambda$FileUtil$p9POy-tGWRglhyOV3JrRdFIh5yQ
            @Override // java.io.FilenameFilter
            public final boolean accept(File file2, String str) {
                boolean matches;
                matches = str.matches(regex);
                return matches;
            }
        });
        if (order) {
            Arrays.sort(dirs, new Comparator() { // from class: com.xiaopeng.commonfunc.utils.-$$Lambda$FileUtil$gNg9mrLpurLs3xSAwrLc_8qrn28
                @Override // java.util.Comparator
                public final int compare(Object obj, Object obj2) {
                    int compareTo;
                    compareTo = ((File) obj).getName().compareTo(((File) obj2).getName());
                    return compareTo;
                }
            });
        } else {
            Arrays.sort(dirs, new Comparator() { // from class: com.xiaopeng.commonfunc.utils.-$$Lambda$FileUtil$7hnUx1naZvYXwmgBRdS5QFIFZf4
                @Override // java.util.Comparator
                public final int compare(Object obj, Object obj2) {
                    int compareTo;
                    compareTo = ((File) obj2).getName().compareTo(((File) obj).getName());
                    return compareTo;
                }
            });
        }
        for (File dir : dirs) {
            try {
                sb = new StringBuilder();
                sb.append(dir.getAbsolutePath());
                sb.append(File.separator);
            } catch (Exception e) {
                e = e;
            }
            try {
                sb.append(file);
                String temp = sb.toString();
                if (isExistFilePath(temp)) {
                    long lastModifiedTime = new File(temp).lastModified();
                    if (lastModifiedTime >= startTime) {
                        paths.add(dir.getAbsolutePath());
                    }
                    if (lastModifiedTime > endTime) {
                        break;
                    }
                } else {
                    continue;
                }
            } catch (Exception e2) {
                e = e2;
                e.printStackTrace();
            }
        }
        return paths;
    }

    public static boolean isFileTypeExist(String filePath, String suffix) {
        File fileFolder = new File(filePath);
        if (!fileFolder.exists()) {
            LogUtils.e(TAG, "isFileTypeExist filePath " + filePath + " not exist");
            return false;
        }
        LogUtils.d(TAG, "isFileTypeExist filePath " + filePath + " exist");
        File[] filesList = fileFolder.listFiles();
        if (filesList != null) {
            for (int i = 0; i < filesList.length; i++) {
                LogUtils.d(TAG, "isFileTypeExist filesList[i].getName() =  " + filesList[i].getName());
                if (filesList[i].isDirectory()) {
                    if (isFileTypeExist(filesList[i].getPath(), suffix)) {
                        return true;
                    }
                } else if (filesList[i].getName().endsWith(suffix)) {
                    LogUtils.d(TAG, "isFileTypeExist i find file endswith " + suffix);
                    return true;
                }
            }
        }
        return false;
    }

    public static boolean isFileTypeExist(String filePath, String prefix, String suffix) {
        File fileFolder = new File(filePath);
        if (!fileFolder.exists()) {
            LogUtils.e(TAG, "isFileTypeExist filePath " + filePath + " not exist");
            return false;
        }
        LogUtils.d(TAG, "isFileTypeExist filePath " + filePath + " exist");
        File[] filesList = fileFolder.listFiles();
        if (filesList != null) {
            for (int i = 0; i < filesList.length; i++) {
                LogUtils.d(TAG, "isFileTypeExist filesList[i].getName() =  " + filesList[i].getName());
                if (filesList[i].isDirectory()) {
                    if (isFileTypeExist(filesList[i].getPath(), prefix, suffix)) {
                        return true;
                    }
                } else if (filesList[i].getName().startsWith(prefix) && filesList[i].getName().endsWith(suffix)) {
                    LogUtils.d(TAG, "isFileTypeExist i find file startwith: " + prefix + " ,endswith: " + suffix);
                    return true;
                }
            }
        }
        return false;
    }

    public static boolean isExistFilePath(String path) {
        boolean isExist = false;
        if (!TextUtils.isEmpty(path)) {
            File f = new File(path);
            isExist = f.exists();
        }
        LogUtils.d(TAG, path + " isExistFilePath = " + isExist);
        return isExist;
    }

    public static boolean isExistFileParentPath(String path) {
        boolean isExist = false;
        if (path != null) {
            File f = new File(path).getParentFile();
            isExist = f.exists();
        }
        LogUtils.d(TAG, path + " isExistFileParentPath = " + isExist);
        return isExist;
    }

    public static List<String> listFileName(String path) {
        File[] listFiles;
        List<String> list = new ArrayList<>();
        File filePath = new File(path);
        if (filePath.exists() && filePath.isDirectory() && filePath.listFiles() != null) {
            for (File file : filePath.listFiles()) {
                list.add(file.getName());
                LogUtils.d(TAG, "filepath=" + file.getName());
            }
        }
        return list;
    }

    public static String[] listFilesPathNameFilter(String path, final String name) {
        File fileFolder = new File(path);
        String[] filesList = fileFolder.list(new FilenameFilter() { // from class: com.xiaopeng.commonfunc.utils.-$$Lambda$FileUtil$kl-vMES3VcyRry4JWxxKNjYe--A
            @Override // java.io.FilenameFilter
            public final boolean accept(File file, String str) {
                boolean contains;
                contains = str.contains(name);
                return contains;
            }
        });
        return filesList;
    }

    public static File[] listFilesNameFilter(String path, final String name) {
        File fileFolder = new File(path);
        File[] filesList = fileFolder.listFiles(new FilenameFilter() { // from class: com.xiaopeng.commonfunc.utils.-$$Lambda$FileUtil$4PnDxYS0B4ZQsQf2o5GpZegPuZM
            @Override // java.io.FilenameFilter
            public final boolean accept(File file, String str) {
                boolean contains;
                contains = str.contains(name);
                return contains;
            }
        });
        return filesList;
    }

    public static File[] sortFiles(File[] files) {
        if (files == null) {
            return files;
        }
        for (int i = 0; i < files.length; i++) {
            LogUtils.d(TAG, "Before sort : " + files[i].getName());
        }
        for (int i2 = 0; i2 < files.length - 1; i2++) {
            for (int j = 0; j < (files.length - 1) - i2; j++) {
                if (files[j].lastModified() > files[j + 1].lastModified()) {
                    File temp = files[j];
                    files[j] = files[j + 1];
                    files[j + 1] = temp;
                }
            }
        }
        for (int i3 = 0; i3 < files.length; i3++) {
            LogUtils.d(TAG, "After sort : " + files[i3].getName());
        }
        return files;
    }

    public static int getFilesSize(String path) {
        int filesum = 0;
        File fileFolder = new File(path);
        LogUtils.d(TAG, "getFilesSize path = " + path);
        File[] filesList = fileFolder.listFiles();
        if (filesList != null) {
            for (int i = 0; i < filesList.length; i++) {
                LogUtils.d(TAG, "getFilesSize filesList[i].getName() =  " + filesList[i].getName());
                if (filesList[i].isDirectory()) {
                    filesum += getFilesSize(filesList[i].getPath());
                } else {
                    filesum++;
                }
            }
        }
        LogUtils.d(TAG, "getFilesSize path = " + path + ", filesum =  " + filesum);
        return filesum;
    }

    public static long getFolderLength(String path) {
        long size = 0;
        File fileFolder = new File(path);
        try {
            File[] fileList = fileFolder.listFiles();
            for (int i = 0; i < fileList.length; i++) {
                size = fileList[i].isDirectory() ? size + getFolderLength(fileList[i].getPath()) : size + fileList[i].length();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return size;
    }

    public static long getFileLength(String path) {
        long fileLength = 0;
        try {
            fileLength = new File(path).length();
            LogUtils.d(TAG, "getFileLength path = " + path + ", fileLength =  " + fileLength);
            return fileLength;
        } catch (Exception e) {
            e.printStackTrace();
            return fileLength;
        }
    }

    public static CopyFileTestResult copyFileNGetUsedTime(String srcPath, String destPath) {
        long beginTime;
        Object obj;
        long endTime;
        StringBuilder sb;
        RandomAccessFile in = null;
        RandomAccessFile out = null;
        CopyFileTestResult result = new CopyFileTestResult(0L, false);
        long size = new File(srcPath).length();
        long beginTime2 = System.currentTimeMillis();
        LogUtils.d(TAG, "copyFileNGetUsedTime beginTime = " + beginTime2 + "srcPath = " + srcPath + " destPath = " + destPath + " size = " + size);
        try {
            in = new RandomAccessFile(srcPath, "r");
            out = new RandomAccessFile(destPath, "rw");
            in.seek(0L);
            out.seek(0L);
            FileChannel inChannel = in.getChannel();
            FileChannel outChannel = out.getChannel();
            beginTime = beginTime2;
            obj = TAG;
            try {
                try {
                    FileLock lock = outChannel.lock(0L, size, false);
                    inChannel.transferTo(0L, size, outChannel);
                    out.getFD().sync();
                    lock.release();
                    result.mPass = true;
                    endTime = System.currentTimeMillis();
                    sb = new StringBuilder();
                } catch (Exception e) {
                    e = e;
                    result.mPass = false;
                    e.printStackTrace();
                    endTime = System.currentTimeMillis();
                    sb = new StringBuilder();
                    sb.append("copyFileNGetUsedTime endTime = ");
                    sb.append(endTime);
                    LogUtils.d(obj, sb.toString());
                    long totalTimeRead = endTime - beginTime;
                    result.mUsedTime = totalTimeRead;
                    closeQuietly(out);
                    closeQuietly(in);
                    return result;
                }
            } catch (Throwable th) {
                th = th;
                long endTime2 = System.currentTimeMillis();
                LogUtils.d(obj, "copyFileNGetUsedTime endTime = " + endTime2);
                long totalTimeRead2 = endTime2 - beginTime;
                result.mUsedTime = totalTimeRead2;
                closeQuietly(out);
                closeQuietly(in);
                throw th;
            }
        } catch (Exception e2) {
            e = e2;
            beginTime = beginTime2;
            obj = TAG;
        } catch (Throwable th2) {
            th = th2;
            beginTime = beginTime2;
            obj = TAG;
            long endTime22 = System.currentTimeMillis();
            LogUtils.d(obj, "copyFileNGetUsedTime endTime = " + endTime22);
            long totalTimeRead22 = endTime22 - beginTime;
            result.mUsedTime = totalTimeRead22;
            closeQuietly(out);
            closeQuietly(in);
            throw th;
        }
        sb.append("copyFileNGetUsedTime endTime = ");
        sb.append(endTime);
        LogUtils.d(obj, sb.toString());
        long totalTimeRead3 = endTime - beginTime;
        result.mUsedTime = totalTimeRead3;
        closeQuietly(out);
        closeQuietly(in);
        return result;
    }

    public static FileWriter getFileWriter(String path, boolean append) {
        FileWriter writer = null;
        try {
            writer = new FileWriter(path, append);
        } catch (Exception e) {
            e.printStackTrace();
        }
        LogUtils.d(TAG, "getFileWriter path=" + path + ", append=" + append + ", writer=" + writer);
        return writer;
    }

    public static void closeFileWriter(FileWriter writer) {
        if (writer != null) {
            try {
                writer.flush();
            } catch (IOException e) {
                e.printStackTrace();
            }
            try {
                writer.close();
                LogUtils.i(TAG, "finish closeFileWriter");
            } catch (IOException e2) {
                e2.printStackTrace();
            }
        }
    }

    public static FileInputStream getFileInputStream(String path) {
        FileInputStream fis = null;
        try {
            if (!TextUtils.isEmpty(path)) {
                File file = new File(path);
                fis = new FileInputStream(file);
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        LogUtils.d(TAG, "getFileInputStream path=" + path + ", fis=" + fis);
        return fis;
    }

    public static void closeQuietly(Closeable closeable) {
        if (closeable != null) {
            try {
                closeable.close();
            } catch (Exception e) {
            }
        }
    }

    public static void closeFileInputStream(FileInputStream fis) {
        if (fis != null) {
            try {
                fis.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public static void closeInputStream(InputStream is) {
        if (is != null) {
            try {
                is.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public static void zipFileOrDirectory(ZipOutputStream out, File fileOrDirectory, String curPath) throws IOException {
        FileInputStream in = null;
        try {
            try {
                try {
                    if (fileOrDirectory.isDirectory()) {
                        File[] entries = fileOrDirectory.listFiles();
                        if (entries != null) {
                            for (File file : entries) {
                                zipFileOrDirectory(out, file, curPath + fileOrDirectory.getName() + InternalZipConstants.ZIP_FILE_SEPARATOR);
                            }
                        }
                    } else {
                        byte[] buffer = new byte[4096];
                        in = new FileInputStream(fileOrDirectory);
                        ZipEntry entry = new ZipEntry(curPath + fileOrDirectory.getName());
                        out.putNextEntry(entry);
                        while (true) {
                            int i = in.read(buffer);
                            if (i == -1) {
                                break;
                            }
                            out.write(buffer, 0, i);
                        }
                        out.closeEntry();
                    }
                } catch (Throwable th) {
                    if (0 != 0) {
                        try {
                            in.close();
                        } catch (IOException var14) {
                            var14.printStackTrace();
                        }
                    }
                    throw th;
                }
            } catch (Exception var15) {
                var15.printStackTrace();
                if (0 == 0) {
                    return;
                }
                in.close();
            }
            if (in != null) {
                in.close();
            }
        } catch (IOException var142) {
            var142.printStackTrace();
        }
    }

    public static void zipFileList(List<String> pathList, String destPath) {
        if (pathList == null || pathList.isEmpty()) {
            LogUtils.d(TAG, "zip file List is empty");
            return;
        }
        FileOutputStream fos = createFileOS(destPath);
        if (fos == null) {
            LogUtils.d(TAG, "FileOutputStream is null");
            return;
        }
        ZipOutputStream out = new ZipOutputStream(fos);
        for (int i = 0; i < pathList.size(); i++) {
            try {
                try {
                    try {
                        File sourceFile = new File(pathList.get(i));
                        LogUtils.i(TAG, "source file = " + pathList.get(i) + " isExist = " + sourceFile.exists());
                        if (sourceFile.exists()) {
                            LogUtils.i(TAG, "ziping source file = " + pathList.get(i));
                            com.xiaopeng.lib.utils.ZipUtils.zipFileOrDirectory(out, sourceFile, "");
                        }
                    } catch (IOException var15) {
                        var15.printStackTrace();
                        return;
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                    out.close();
                    return;
                }
            } catch (Throwable th) {
                try {
                    out.close();
                } catch (IOException var152) {
                    var152.printStackTrace();
                }
                throw th;
            }
        }
        out.flush();
        out.close();
        LogUtils.i(TAG, "finish zip file");
        out.close();
    }

    @SuppressLint({"NewApi"})
    public static String checkTFCardState(Context context) {
        StorageManager mStorageManager = StorageManager.from(context);
        StorageVolume[] storageVolumes = mStorageManager.getVolumeList();
        for (StorageVolume storageVolume : storageVolumes) {
            LogUtils.d(TAG, "checkTFCardState storageVolume.getPath() " + storageVolume.getPath());
            if (storageVolume.getDescription(context).toUpperCase().contains("SD")) {
                String storagePath = storageVolume.getPath();
                String state = mStorageManager.getVolumeState(storagePath);
                LogUtils.d(TAG, "checkTFCardState storageVolume.getPath() = " + storageVolume.getPath() + " state = " + state);
                return state;
            }
        }
        return "unknown";
    }

    @SuppressLint({"NewApi"})
    public static long checkTFCardUsedSize(Context mContext) {
        StorageManager mStorageManager = StorageManager.from(mContext);
        StorageVolume[] storageVolumes = mStorageManager.getVolumeList();
        for (StorageVolume storageVolume : storageVolumes) {
            LogUtils.d(TAG, "storageVolume " + storageVolume.getPath());
        }
        for (StorageVolume storageVolume2 : storageVolumes) {
            if (storageVolume2.getDescription(mContext).toUpperCase().contains("SD")) {
                String storagePath = storageVolume2.getPath();
                return (long) Math.floor(FileUtils.getFileSize(storagePath, 3));
            }
        }
        return 0L;
    }

    public static void copyFile(String srcFile, String destFile) {
        File file = new File(srcFile);
        if (!file.exists()) {
            LogUtils.d(TAG, "srcFile  " + srcFile + "is not exist");
            return;
        }
        long fileSize = file.length();
        ThreadUtils.execute(new CopyFileTask(srcFile, destFile, 0L, fileSize));
    }

    public static void copyFile(String srcFile, String destFile, CopyFileTask.Callback callback) {
        File file = new File(srcFile);
        if (!file.exists()) {
            LogUtils.d(TAG, "srcFile  " + srcFile + "is not exist");
            return;
        }
        long fileSize = file.length();
        ThreadUtils.execute(new CopyFileTask(srcFile, destFile, 0L, fileSize, callback));
    }

    public static int copyPath(String srcPath, String destPath) {
        int sum = 0;
        File fileFolder = new File(srcPath);
        if (!mkDir(destPath)) {
            LogUtils.w(TAG, "copyPath mkDir fail ");
        }
        LogUtils.d(TAG, "copyPath srcPath = " + srcPath + " destPath = " + destPath);
        File[] filesList = fileFolder.listFiles();
        if (filesList != null) {
            for (int i = 0; i < filesList.length; i++) {
                LogUtils.d(TAG, "copyPath filesList[i].getName() =  " + filesList[i].getName());
                if (filesList[i].isDirectory()) {
                    String path = filesList[i].getPath();
                    sum += copyPath(path, destPath + filesList[i].getName() + File.separator);
                } else {
                    sum++;
                    String path2 = filesList[i].getPath();
                    ThreadUtils.execute(new CopyFileTask(path2, destPath + filesList[i].getName(), 0L, filesList[i].length()));
                }
            }
        }
        return sum;
    }

    public static long getFileSize(String filePath) {
        File file = new File(filePath);
        long blockSize = 0;
        try {
            if (file.isDirectory()) {
                blockSize = getFileSizes(file);
            } else {
                blockSize = getFileSize(file);
            }
        } catch (Exception var6) {
            var6.printStackTrace();
            LogUtils.e("FileUtils", "getFileSize error : " + filePath);
        }
        return blockSize;
    }

    private static long getFileSize(File file) throws FileNotFoundException {
        if (file != null && file.exists()) {
            return file.length();
        }
        throw new FileNotFoundException();
    }

    private static long getFileSizes(File file) throws Exception {
        long fileSize;
        long size = 0;
        File[] flist = file.listFiles();
        if (flist != null) {
            for (int i = 0; i < flist.length; i++) {
                if (flist[i].isDirectory()) {
                    fileSize = getFileSizes(flist[i]);
                } else {
                    fileSize = getFileSize(flist[i]);
                }
                size += fileSize;
            }
        } else {
            LogUtils.e("FileUtils", "File not exist :" + file.getPath());
        }
        return size;
    }

    public static long getCacheSize(Context mContext, String mPackageName) {
        long size = 0;
        StorageStatsManager storageStatsManager = (StorageStatsManager) mContext.getSystemService("storagestats");
        try {
            StorageStats storageStats = storageStatsManager.queryStatsForPackage(StorageManager.UUID_DEFAULT, mPackageName, UserHandle.of(UserHandle.myUserId()));
            size = storageStats.getCacheBytes();
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e2) {
            e2.printStackTrace();
        }
        LogUtils.d(TAG, "getCacheSize mPackageName: " + mPackageName + ", size : " + size);
        return size;
    }

    public static void unzipMultiFile(String zippath, String outzippath) {
        LogUtils.d(TAG, "unzipMultiFile from : " + zippath + ", to: " + outzippath);
        InputStream input = null;
        OutputStream output = null;
        ZipFile zipFile = null;
        ZipInputStream zipInput = null;
        byte[] buffer = new byte[4096];
        try {
            try {
                try {
                    File file = new File(zippath);
                    zipFile = new ZipFile(file);
                    zipInput = new ZipInputStream(new FileInputStream(file));
                    while (true) {
                        ZipEntry entry = zipInput.getNextEntry();
                        if (entry == null) {
                            break;
                        }
                        File outFile = new File(outzippath + File.separator + entry.getName());
                        if (!outFile.getParentFile().exists()) {
                            outFile.getParentFile().mkdir();
                        }
                        if (!outFile.exists()) {
                            outFile.createNewFile();
                        }
                        input = zipFile.getInputStream(entry);
                        output = new FileOutputStream(outFile);
                        while (true) {
                            int temp = input.read(buffer);
                            if (temp != -1) {
                                output.write(buffer, 0, temp);
                            }
                        }
                        input.close();
                        output.close();
                    }
                    if (input != null) {
                        input.close();
                    }
                    if (output != null) {
                        output.close();
                    }
                    zipFile.close();
                    zipInput.close();
                } catch (Exception e) {
                    e.printStackTrace();
                    if (input != null) {
                        input.close();
                    }
                    if (output != null) {
                        output.close();
                    }
                    if (zipFile != null) {
                        zipFile.close();
                    }
                    if (zipInput != null) {
                        zipInput.close();
                    }
                }
            } catch (IOException e2) {
                e2.printStackTrace();
            }
        } catch (Throwable th) {
            if (input != null) {
                try {
                    input.close();
                } catch (IOException e3) {
                    e3.printStackTrace();
                    throw th;
                }
            }
            if (output != null) {
                output.close();
            }
            if (zipFile != null) {
                zipFile.close();
            }
            if (zipInput != null) {
                zipInput.close();
            }
            throw th;
        }
    }

    @SuppressLint({"NewApi"})
    public static StorageVolume getStorageVolume(Context context, String name) {
        StorageManager mStorageManager = StorageManager.from(context);
        StorageVolume[] storageVolumes = mStorageManager.getVolumeList();
        for (StorageVolume storageVolume : storageVolumes) {
            if (storageVolume.getDescription(context).toUpperCase().contains(name)) {
                return storageVolume;
            }
        }
        return null;
    }

    public static String getStoragePath(Context context, String name) {
        StorageManager mStorageManager = StorageManager.from(context);
        StorageVolume[] storageVolumes = mStorageManager.getVolumeList();
        for (StorageVolume storageVolume : storageVolumes) {
            LogUtils.d(TAG, "getStoragePath ");
        }
        for (StorageVolume storageVolume2 : storageVolumes) {
            if (storageVolume2.getDescription(context).toUpperCase().contains(name)) {
                return storageVolume2.getPath();
            }
        }
        return null;
    }

    public static String getUDiskPath(Context context) {
        StorageManager mStorageManager = StorageManager.from(context);
        List<VolumeInfo> volumeInfoList = mStorageManager.getVolumes();
        for (VolumeInfo volumeInfo : volumeInfoList) {
            LogUtils.i(TAG, "volumeInfo = " + volumeInfo + " , volumeInfo.getDisk() = " + volumeInfo.getDisk());
            if (volumeInfo.getDisk() != null && volumeInfo.getDisk().isUsb()) {
                File file = volumeInfo.getInternalPath();
                if (file != null) {
                    return file.getPath();
                }
                return null;
            }
        }
        return null;
    }

    public static String getUDiskFsType(Context context) {
        String res = null;
        StorageManager mStorageManager = StorageManager.from(context);
        List<VolumeInfo> volumeInfoList = mStorageManager.getVolumes();
        Iterator<VolumeInfo> it = volumeInfoList.iterator();
        while (true) {
            if (!it.hasNext()) {
                break;
            }
            VolumeInfo volumeInfo = it.next();
            LogUtils.i(TAG, "volumeInfo = " + volumeInfo + " , volumeInfo.getDisk() = " + volumeInfo.getDisk());
            if (volumeInfo.getDisk() != null && volumeInfo.getDisk().isUsb()) {
                if (volumeInfo.getState() != 1) {
                    res = volumeInfo.fsType;
                } else {
                    Sleep.sleep(2000L);
                    res = getUDiskFsType(context);
                }
            }
        }
        LogUtils.i(TAG, "getUDiskFsType [%s]", res);
        return res;
    }

    public static String getSDPath() {
        if ("mounted".equalsIgnoreCase(Environment.getExternalStorageState())) {
            return Environment.getExternalStorageDirectory().toString();
        }
        return null;
    }

    public static int getPrimaryStorageSize(Context context) {
        StorageManager mStorageManager = StorageManager.from(context);
        int size = (int) (mStorageManager.getPrimaryStorageSize() / 1000000000);
        LogUtils.d(TAG, "getPrimaryStorageSize:" + size);
        return size;
    }

    public static int getDataAvailableGBytes() {
        return (int) (getDataAvailableBytes() / FileUtils.SIZE_1GB);
    }

    public static long getDataAvailableBytes() {
        return getAvailableBytes(Environment.getDataDirectory().getPath());
    }

    public static long getAvailableBytes(String path) {
        long availableSize = 0;
        try {
            StatFs statFs = new StatFs(path);
            availableSize = statFs.getAvailableBytes();
        } catch (Exception e) {
            LogUtils.e(TAG, e.toString());
        }
        LogUtils.d(TAG, "getAvailableBytes path:" + path + ", availableSize:" + availableSize);
        return availableSize;
    }

    @SuppressLint({"NewApi"})
    public static void runFormat(StorageVolume storageVolume, Context context) {
        LogUtils.d(TAG, "runFormat " + storageVolume.getPath());
        ComponentName formatter = new ComponentName("android", "com.android.internal.os.storage.ExternalStorageFormatter");
        Intent intent = new Intent("com.android.internal.os.storage.FORMAT_ONLY");
        intent.setComponent(formatter);
        intent.putExtra("android.os.storage.extra.STORAGE_VOLUME", storageVolume);
        context.startService(intent);
    }

    public static boolean isUsbDeviceAttached(Context context, int vid, int pid) {
        boolean res = false;
        HashMap<String, UsbDevice> deviceHashMap = ((UsbManager) context.getSystemService("usb")).getDeviceList();
        for (Map.Entry entry : deviceHashMap.entrySet()) {
            UsbDevice device = entry.getValue();
            LogUtils.d(TAG, "isUsbDeviceAttached: " + device);
            if (device.getVendorId() == vid && device.getProductId() == pid) {
                res = true;
            }
        }
        LogUtils.d(TAG, "isUsbDeviceAttached vid:" + vid + ", pid:" + pid + ", res:" + res);
        return res;
    }

    public static String getMemInfoIype(String path, String type) {
        String str;
        try {
            FileReader fileReader = new FileReader(path);
            BufferedReader bufferedReader = new BufferedReader(fileReader, 4096);
            do {
                str = bufferedReader.readLine();
                if (str == null) {
                    break;
                }
            } while (!str.contains(type));
            bufferedReader.close();
            String[] array = str.split(Constant.REGEX_PID_AGING_TEST);
            return array[1];
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            return null;
        } catch (IOException e2) {
            e2.printStackTrace();
            return null;
        }
    }

    public static String getFileMd5(String filepath) {
        RandomAccessFile randomAccessFile = null;
        try {
            try {
                try {
                    File file = TextUtils.isEmpty(filepath) ? null : new File(filepath);
                    MessageDigest messageDigest = MessageDigest.getInstance("MD5");
                    if (file == null) {
                        if (0 != 0) {
                            try {
                                randomAccessFile.close();
                            } catch (IOException var22) {
                                var22.printStackTrace();
                            }
                        }
                        return "";
                    } else if (!file.exists()) {
                        if (0 != 0) {
                            try {
                                randomAccessFile.close();
                            } catch (IOException var222) {
                                var222.printStackTrace();
                            }
                        }
                        return "";
                    } else {
                        randomAccessFile = new RandomAccessFile(file, "r");
                        byte[] bytes = new byte[10485760];
                        while (true) {
                            int len = randomAccessFile.read(bytes);
                            if (len == -1) {
                                break;
                            }
                            messageDigest.update(bytes, 0, len);
                        }
                        BigInteger bigInt = new BigInteger(1, messageDigest.digest());
                        String md5 = bigInt.toString(16);
                        while (md5.length() < 32) {
                            md5 = "0" + md5;
                        }
                        String var7 = md5;
                        try {
                            randomAccessFile.close();
                        } catch (IOException var223) {
                            var223.printStackTrace();
                        }
                        return var7;
                    }
                } catch (IOException var25) {
                    var25.printStackTrace();
                    if (randomAccessFile != null) {
                        try {
                            randomAccessFile.close();
                        } catch (IOException var224) {
                            var224.printStackTrace();
                        }
                    }
                    return "";
                }
            } catch (FileNotFoundException var24) {
                var24.printStackTrace();
                if (randomAccessFile != null) {
                    try {
                        randomAccessFile.close();
                    } catch (IOException var225) {
                        var225.printStackTrace();
                    }
                }
                return "";
            } catch (NoSuchAlgorithmException var23) {
                var23.printStackTrace();
                if (randomAccessFile != null) {
                    try {
                        randomAccessFile.close();
                    } catch (IOException var226) {
                        var226.printStackTrace();
                    }
                }
                return "";
            }
        } catch (Throwable th) {
            if (randomAccessFile != null) {
                try {
                    randomAccessFile.close();
                } catch (IOException var227) {
                    var227.printStackTrace();
                }
            }
            throw th;
        }
    }

    public static boolean coverToFile(String path, int index, byte[] value) {
        RandomAccessFile out = null;
        try {
            try {
                out = new RandomAccessFile(path, "rw");
                out.seek(index);
                out.write(value);
                LogUtils.i(TAG, "coverToFile path: " + path + ", index: " + index + " value: " + Arrays.toString(value));
                try {
                    out.close();
                    return true;
                } catch (IOException e) {
                    return false;
                }
            } catch (Throwable e2) {
                if (out != null) {
                    try {
                        out.close();
                    } catch (IOException e3) {
                        return false;
                    }
                }
                throw e2;
            }
        } catch (Exception e4) {
            LogUtils.d(TAG, "coverToFile Exception" + e4.toString());
            if (out != null) {
                try {
                    out.close();
                } catch (IOException e5) {
                    return false;
                }
            }
            return false;
        }
    }

    public static FileOutputStream createFileOS(String path) {
        File file = new File(path);
        FileOutputStream fos = null;
        try {
            if (!file.exists()) {
                file.getParentFile().mkdirs();
                file.createNewFile();
            }
            fos = new FileOutputStream(file);
        } catch (Exception e) {
            e.printStackTrace();
        }
        LogUtils.d(TAG, "createFileOS path=" + path + ", fos:" + fos);
        return fos;
    }

    public static boolean writeFileOS(FileOutputStream fos, byte[] data) {
        boolean res = false;
        try {
            fos.write(data);
            fos.flush();
            fos.getFD().sync();
            res = true;
        } catch (IOException e) {
            e.printStackTrace();
        }
        LogUtils.d(TAG, "writeFileOS data=" + data + ", res=" + res);
        return res;
    }

    public static boolean closeFileOS(FileOutputStream fos) {
        boolean res = false;
        if (fos != null) {
            try {
                fos.flush();
                fos.getFD().sync();
            } catch (IOException e) {
                e.printStackTrace();
            }
            try {
                fos.close();
                res = true;
            } catch (IOException e2) {
                e2.printStackTrace();
            }
        }
        LogUtils.d(TAG, "closeFileOS res=" + res);
        return res;
    }
}
