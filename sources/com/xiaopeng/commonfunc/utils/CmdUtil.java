package com.xiaopeng.commonfunc.utils;

import android.util.Log;
import java.io.Closeable;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileOutputStream;
/* loaded from: classes4.dex */
public final class CmdUtil {
    private static final String TAG = "RootCmdUtil";

    public static void execRootCmdAndSave(String path) {
        File file = new File(path);
        DataOutputStream dos = null;
        DataInputStream dis = null;
        try {
            try {
                Process p = Runtime.getRuntime().exec("/system/bin/sh");
                dos = new DataOutputStream(p.getOutputStream());
                dis = new DataInputStream(p.getInputStream());
                dos.writeBytes("du /data |sort -nr |head -n20\n");
                dos.writeBytes(ShellUtils.COMMAND_EXIT);
                dos.flush();
                if (!file.exists()) {
                    file.getParentFile().mkdirs();
                    file.createNewFile();
                }
                byte[] buffer = new byte[1024];
                FileOutputStream out = new FileOutputStream(file);
                while (true) {
                    int len = dis.read(buffer);
                    if (len == -1) {
                        break;
                    }
                    out.write(buffer, 0, len);
                }
                out.flush();
                p.waitFor();
            } catch (Exception e) {
                Log.i(TAG, e.toString());
            }
        } finally {
            closeQuietly(dos);
            closeQuietly(dis);
        }
    }

    private static void closeQuietly(Closeable closeable) {
        if (closeable != null) {
            try {
                closeable.close();
            } catch (Exception e) {
                Log.i(TAG, e.toString());
            }
        }
    }
}
