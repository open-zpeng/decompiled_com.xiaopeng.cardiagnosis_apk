package com.xiaopeng.commonfunc.utils;

import android.app.ActivityManager;
import android.content.Context;
import android.os.PowerManager;
import com.xiaopeng.commonfunc.Constant;
import com.xiaopeng.lib.utils.LogUtils;
import com.xiaopeng.lib.utils.ThreadUtils;
import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;
/* loaded from: classes4.dex */
public class ProcessUtil {
    private static final int DEFAULT_PING_PACKETS = 4;
    private static final String I2CGET_COMMAND = "i2cget -f -y ";
    public static final String INSMOD_DRIVER_CMD = "insmod";
    public static final String MODEM_LOG_COMMAND = "qxdm -o /data/Log/log0/factory -t /dev/ttyUSB3 -c /system/etc/default.bin -s 500";
    public static final String MODEM_LOG_PROCESS = "qxdm";
    private static final String PHYTOOL_COMMAND_GET_LINK_STAT = "get link_status";
    private static final String PHYTOOL_COMMAND_GET_MASTERSLAVE_MODE = "get master_cfg";
    private static final String PHYTOOL_COMMAND_GET_SQI = "get sqi";
    private static final String PHYTOOL_COMMAND_SET_MASTER_CFG = "set master_cfg";
    private static final String PHYTOOL_PROCESS = "phytool";
    private static final String PING_COMMAND = "ping -c ";
    public static final String PING_PROCESS = "ping";
    private static final int PING_TIMEOUT = 5000;
    private static final String REGEX_SPACE = "\\s+";
    public static final String RESULT_BYTES_IN = "bytes in";
    public static final String RESULT_ERROR = "error";
    public static final String RESULT_FAIL = "fail";
    public static final String RESULT_PUSHED = "pushed";
    public static final String RMMOD_DRIVER_CMD = "rmmod";
    private static final String RM_RF_COMMAND = "rm -rf ";
    public static final String SHELL_CMD_SCREENCAP = "screencap -p ";
    public static final String SHELL_COMMAND_ADB_PUSH = "adb push";
    private static final String SHELL_COMMAND_BIN_SH = "/bin/sh";
    private static final String SHELL_COMMAND_CTRL = "-c";
    private static final String SHELL_COMMAND_KILL = "kill -9 ";
    public static final String SHELL_COMMAND_PING = "ping";
    public static final String SHELL_COMMAND_PING_PACKAGESIZE = " -s ";
    public static final String SHELL_COMMAND_PING_TIMEOUT = " -W ";
    private static final String SHELL_COMMAND_PS_GREP = "ps -elf | grep ";
    public static final String SYNC_COMMAND = "sync";
    private static final String TAG = "ProcessUtil";
    public static final String TESTWIFI_24B = "b";
    public static final String TESTWIFI_24G = "g";
    public static final String TESTWIFI_24N = "n";
    public static final String TESTWIFI_PROCESS = "test_wifi.sh";

    public static void screenCap(String folderPath, String issueName) {
        execProcess(SHELL_CMD_SCREENCAP + folderPath + File.separator + issueName + ".png");
    }

    /* JADX WARN: Code restructure failed: missing block: B:15:0x0081, code lost:
        if (r2 == null) goto L12;
     */
    /* JADX WARN: Code restructure failed: missing block: B:9:0x0079, code lost:
        r1 = r4.split("\\s+");
     */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct add '--show-bad-code' argument
    */
    public static java.lang.String[] getProcessInfo(java.lang.String r9) {
        /*
            java.lang.String r0 = "ProcessUtil"
            r1 = 0
            r2 = 0
            r3 = 0
            java.lang.StringBuilder r4 = new java.lang.StringBuilder     // Catch: java.lang.Throwable -> L7b java.io.IOException -> L7d
            r4.<init>()     // Catch: java.lang.Throwable -> L7b java.io.IOException -> L7d
            java.lang.String r5 = "getProcessInfo processName = ps -elf | grep "
            r4.append(r5)     // Catch: java.lang.Throwable -> L7b java.io.IOException -> L7d
            r4.append(r9)     // Catch: java.lang.Throwable -> L7b java.io.IOException -> L7d
            java.lang.String r4 = r4.toString()     // Catch: java.lang.Throwable -> L7b java.io.IOException -> L7d
            com.xiaopeng.lib.utils.LogUtils.d(r0, r4)     // Catch: java.lang.Throwable -> L7b java.io.IOException -> L7d
            java.lang.Runtime r4 = java.lang.Runtime.getRuntime()     // Catch: java.lang.Throwable -> L7b java.io.IOException -> L7d
            r5 = 3
            java.lang.String[] r5 = new java.lang.String[r5]     // Catch: java.lang.Throwable -> L7b java.io.IOException -> L7d
            r6 = 0
            java.lang.String r7 = "/bin/sh"
            r5[r6] = r7     // Catch: java.lang.Throwable -> L7b java.io.IOException -> L7d
            r6 = 1
            java.lang.String r7 = "-c"
            r5[r6] = r7     // Catch: java.lang.Throwable -> L7b java.io.IOException -> L7d
            r6 = 2
            java.lang.StringBuilder r7 = new java.lang.StringBuilder     // Catch: java.lang.Throwable -> L7b java.io.IOException -> L7d
            r7.<init>()     // Catch: java.lang.Throwable -> L7b java.io.IOException -> L7d
            java.lang.String r8 = "ps -elf | grep "
            r7.append(r8)     // Catch: java.lang.Throwable -> L7b java.io.IOException -> L7d
            r7.append(r9)     // Catch: java.lang.Throwable -> L7b java.io.IOException -> L7d
            java.lang.String r7 = r7.toString()     // Catch: java.lang.Throwable -> L7b java.io.IOException -> L7d
            r5[r6] = r7     // Catch: java.lang.Throwable -> L7b java.io.IOException -> L7d
            java.lang.Process r4 = r4.exec(r5)     // Catch: java.lang.Throwable -> L7b java.io.IOException -> L7d
            r2 = r4
            java.io.BufferedReader r4 = new java.io.BufferedReader     // Catch: java.lang.Throwable -> L7b java.io.IOException -> L7d
            java.io.InputStreamReader r5 = new java.io.InputStreamReader     // Catch: java.lang.Throwable -> L7b java.io.IOException -> L7d
            java.io.InputStream r6 = r2.getInputStream()     // Catch: java.lang.Throwable -> L7b java.io.IOException -> L7d
            r5.<init>(r6)     // Catch: java.lang.Throwable -> L7b java.io.IOException -> L7d
            r4.<init>(r5)     // Catch: java.lang.Throwable -> L7b java.io.IOException -> L7d
            r3 = r4
        L52:
            java.lang.String r4 = r3.readLine()     // Catch: java.lang.Throwable -> L7b java.io.IOException -> L7d
            r5 = r4
            if (r4 == 0) goto L7a
            java.lang.StringBuilder r4 = new java.lang.StringBuilder     // Catch: java.lang.Throwable -> L7b java.io.IOException -> L7d
            r4.<init>()     // Catch: java.lang.Throwable -> L7b java.io.IOException -> L7d
            java.lang.String r6 = "getProcessInfo result "
            r4.append(r6)     // Catch: java.lang.Throwable -> L7b java.io.IOException -> L7d
            r4.append(r5)     // Catch: java.lang.Throwable -> L7b java.io.IOException -> L7d
            java.lang.String r4 = r4.toString()     // Catch: java.lang.Throwable -> L7b java.io.IOException -> L7d
            com.xiaopeng.lib.utils.LogUtils.d(r0, r4)     // Catch: java.lang.Throwable -> L7b java.io.IOException -> L7d
            boolean r4 = r5.contains(r9)     // Catch: java.lang.Throwable -> L7b java.io.IOException -> L7d
            if (r4 == 0) goto L52
            java.lang.String r4 = "\\s+"
            java.lang.String[] r4 = r5.split(r4)     // Catch: java.lang.Throwable -> L7b java.io.IOException -> L7d
            r1 = r4
        L7a:
            goto L83
        L7b:
            r0 = move-exception
            goto La7
        L7d:
            r4 = move-exception
            r4.printStackTrace()     // Catch: java.lang.Throwable -> L7b
            if (r2 == 0) goto L86
        L83:
            r2.destroy()
        L86:
            com.xiaopeng.commonfunc.utils.IoUtils.closeQuietly(r3)
            java.lang.StringBuilder r4 = new java.lang.StringBuilder
            r4.<init>()
            java.lang.String r5 = "getProcessInfo processName = "
            r4.append(r5)
            r4.append(r9)
            java.lang.String r5 = ", res = "
            r4.append(r5)
            r4.append(r1)
            java.lang.String r4 = r4.toString()
            com.xiaopeng.lib.utils.LogUtils.d(r0, r4)
            return r1
        La7:
            if (r2 == 0) goto Lac
            r2.destroy()
        Lac:
            com.xiaopeng.commonfunc.utils.IoUtils.closeQuietly(r3)
            throw r0
        */
        throw new UnsupportedOperationException("Method not decompiled: com.xiaopeng.commonfunc.utils.ProcessUtil.getProcessInfo(java.lang.String):java.lang.String[]");
    }

    /* JADX WARN: Code restructure failed: missing block: B:17:0x0090, code lost:
        if (r3 == null) goto L20;
     */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct add '--show-bad-code' argument
    */
    public static java.lang.String[] getProcessInfo(java.lang.String r10, java.lang.String r11) {
        /*
            java.lang.String r0 = ", param = "
            java.lang.String r1 = "ProcessUtil"
            r2 = 0
            r3 = 0
            r4 = 0
            java.lang.StringBuilder r5 = new java.lang.StringBuilder     // Catch: java.lang.Throwable -> L8a java.io.IOException -> L8c
            r5.<init>()     // Catch: java.lang.Throwable -> L8a java.io.IOException -> L8c
            java.lang.String r6 = "getProcessInfo processName = ps -elf | grep "
            r5.append(r6)     // Catch: java.lang.Throwable -> L8a java.io.IOException -> L8c
            r5.append(r10)     // Catch: java.lang.Throwable -> L8a java.io.IOException -> L8c
            r5.append(r0)     // Catch: java.lang.Throwable -> L8a java.io.IOException -> L8c
            r5.append(r11)     // Catch: java.lang.Throwable -> L8a java.io.IOException -> L8c
            java.lang.String r5 = r5.toString()     // Catch: java.lang.Throwable -> L8a java.io.IOException -> L8c
            com.xiaopeng.lib.utils.LogUtils.d(r1, r5)     // Catch: java.lang.Throwable -> L8a java.io.IOException -> L8c
            java.lang.Runtime r5 = java.lang.Runtime.getRuntime()     // Catch: java.lang.Throwable -> L8a java.io.IOException -> L8c
            r6 = 3
            java.lang.String[] r6 = new java.lang.String[r6]     // Catch: java.lang.Throwable -> L8a java.io.IOException -> L8c
            r7 = 0
            java.lang.String r8 = "/bin/sh"
            r6[r7] = r8     // Catch: java.lang.Throwable -> L8a java.io.IOException -> L8c
            r7 = 1
            java.lang.String r8 = "-c"
            r6[r7] = r8     // Catch: java.lang.Throwable -> L8a java.io.IOException -> L8c
            r7 = 2
            java.lang.StringBuilder r8 = new java.lang.StringBuilder     // Catch: java.lang.Throwable -> L8a java.io.IOException -> L8c
            r8.<init>()     // Catch: java.lang.Throwable -> L8a java.io.IOException -> L8c
            java.lang.String r9 = "ps -elf | grep "
            r8.append(r9)     // Catch: java.lang.Throwable -> L8a java.io.IOException -> L8c
            r8.append(r10)     // Catch: java.lang.Throwable -> L8a java.io.IOException -> L8c
            java.lang.String r8 = r8.toString()     // Catch: java.lang.Throwable -> L8a java.io.IOException -> L8c
            r6[r7] = r8     // Catch: java.lang.Throwable -> L8a java.io.IOException -> L8c
            java.lang.Process r5 = r5.exec(r6)     // Catch: java.lang.Throwable -> L8a java.io.IOException -> L8c
            r3 = r5
            java.io.BufferedReader r5 = new java.io.BufferedReader     // Catch: java.lang.Throwable -> L8a java.io.IOException -> L8c
            java.io.InputStreamReader r6 = new java.io.InputStreamReader     // Catch: java.lang.Throwable -> L8a java.io.IOException -> L8c
            java.io.InputStream r7 = r3.getInputStream()     // Catch: java.lang.Throwable -> L8a java.io.IOException -> L8c
            r6.<init>(r7)     // Catch: java.lang.Throwable -> L8a java.io.IOException -> L8c
            r5.<init>(r6)     // Catch: java.lang.Throwable -> L8a java.io.IOException -> L8c
            r4 = r5
        L5a:
            java.lang.String r5 = r4.readLine()     // Catch: java.lang.Throwable -> L8a java.io.IOException -> L8c
            r6 = r5
            if (r5 == 0) goto L89
            java.lang.StringBuilder r5 = new java.lang.StringBuilder     // Catch: java.lang.Throwable -> L8a java.io.IOException -> L8c
            r5.<init>()     // Catch: java.lang.Throwable -> L8a java.io.IOException -> L8c
            java.lang.String r7 = "getProcessInfo result = "
            r5.append(r7)     // Catch: java.lang.Throwable -> L8a java.io.IOException -> L8c
            r5.append(r6)     // Catch: java.lang.Throwable -> L8a java.io.IOException -> L8c
            java.lang.String r5 = r5.toString()     // Catch: java.lang.Throwable -> L8a java.io.IOException -> L8c
            com.xiaopeng.lib.utils.LogUtils.d(r1, r5)     // Catch: java.lang.Throwable -> L8a java.io.IOException -> L8c
            boolean r5 = r6.contains(r10)     // Catch: java.lang.Throwable -> L8a java.io.IOException -> L8c
            if (r5 == 0) goto L5a
            boolean r5 = r6.contains(r11)     // Catch: java.lang.Throwable -> L8a java.io.IOException -> L8c
            if (r5 == 0) goto L5a
            java.lang.String r5 = "\\s+"
            java.lang.String[] r5 = r6.split(r5)     // Catch: java.lang.Throwable -> L8a java.io.IOException -> L8c
            r2 = r5
            goto L5a
        L89:
            goto L92
        L8a:
            r0 = move-exception
            goto Lbc
        L8c:
            r5 = move-exception
            r5.printStackTrace()     // Catch: java.lang.Throwable -> L8a
            if (r3 == 0) goto L95
        L92:
            r3.destroy()
        L95:
            com.xiaopeng.commonfunc.utils.IoUtils.closeQuietly(r4)
            java.lang.StringBuilder r5 = new java.lang.StringBuilder
            r5.<init>()
            java.lang.String r6 = "getProcessInfo processName = "
            r5.append(r6)
            r5.append(r10)
            r5.append(r0)
            r5.append(r11)
            java.lang.String r0 = ", res = "
            r5.append(r0)
            r5.append(r2)
            java.lang.String r0 = r5.toString()
            com.xiaopeng.lib.utils.LogUtils.d(r1, r0)
            return r2
        Lbc:
            if (r3 == 0) goto Lc1
            r3.destroy()
        Lc1:
            com.xiaopeng.commonfunc.utils.IoUtils.closeQuietly(r4)
            throw r0
        */
        throw new UnsupportedOperationException("Method not decompiled: com.xiaopeng.commonfunc.utils.ProcessUtil.getProcessInfo(java.lang.String, java.lang.String):java.lang.String[]");
    }

    public static void killProcess(String processName) {
        Process mProcess = null;
        String[] mProcessInfo = getProcessInfo(processName);
        LogUtils.d(TAG, "killProcess processName = " + processName + ",  mProcessInfo = " + Arrays.toString(mProcessInfo));
        if (mProcessInfo != null) {
            try {
                if (mProcessInfo.length > 1) {
                    try {
                        try {
                            Runtime runtime = Runtime.getRuntime();
                            mProcess = runtime.exec(SHELL_COMMAND_KILL + Integer.parseInt(mProcessInfo[1]));
                            mProcess.waitFor();
                        } catch (IOException e) {
                            e.printStackTrace();
                            if (mProcess == null) {
                                return;
                            }
                        }
                    } catch (InterruptedException e2) {
                        e2.printStackTrace();
                        if (mProcess == null) {
                            return;
                        }
                    }
                    mProcess.destroy();
                }
            } catch (Throwable th) {
                if (mProcess != null) {
                    mProcess.destroy();
                }
                throw th;
            }
        }
    }

    public static void killProcess(String processName, String param) {
        Process mProcess = null;
        String[] mProcessInfo = getProcessInfo(processName, param);
        LogUtils.d(TAG, "killProcess processName = " + processName + ", param = " + param + ",  mProcessInfo = " + Arrays.toString(mProcessInfo));
        if (mProcessInfo != null) {
            try {
                if (mProcessInfo.length > 1) {
                    try {
                        Runtime runtime = Runtime.getRuntime();
                        mProcess = runtime.exec(SHELL_COMMAND_KILL + Integer.parseInt(mProcessInfo[1]));
                        mProcess.waitFor();
                    } catch (IOException e) {
                        e.printStackTrace();
                        if (mProcess == null) {
                            return;
                        }
                    } catch (InterruptedException e2) {
                        e2.printStackTrace();
                        if (mProcess == null) {
                            return;
                        }
                    }
                    mProcess.destroy();
                }
            } catch (Throwable th) {
                if (mProcess != null) {
                    mProcess.destroy();
                }
                throw th;
            }
        }
    }

    public static boolean runAdbPushFile(String src, String dest) {
        boolean res = false;
        StringBuilder resultBuf = new StringBuilder();
        try {
            execProcessWithoutSh("su").waitFor();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        Process process = execProcessWithoutSh("busybox tftp -l /mnt/media_rw/289F-9F58/tbox/TBOX_Local_Upgrade_V0.4.8.zip -r /mnt/sdcard/tftpboot/update.zip -p 192.168.225.1");
        boolean z = false;
        if (process == null) {
            return false;
        }
        BufferedReader br = null;
        try {
            try {
                br = new BufferedReader(new InputStreamReader(process.getInputStream()));
                LogUtils.d(TAG, "runAdbPushFile readline");
                while (true) {
                    String line = br.readLine();
                    if (line == null) {
                        break;
                    }
                    LogUtils.d(TAG, "runAdbPushFile line" + line);
                    resultBuf.append(line);
                }
                if (!resultBuf.toString().isEmpty()) {
                    if (resultBuf.toString().contains(RESULT_PUSHED)) {
                        if (resultBuf.toString().contains(RESULT_BYTES_IN)) {
                            z = true;
                        }
                    }
                    res = z;
                }
            } catch (Exception e2) {
                LogUtils.d(TAG, "fail to runAdbPushFile src:" + src + ", dest:" + dest);
                e2.printStackTrace();
            }
            return res;
        } finally {
            IoUtils.closeQuietly(br);
            process.destroy();
        }
    }

    public static Process execProcess(String cmd) {
        Process process = null;
        try {
            ProcessBuilder processBuilder = new ProcessBuilder(SHELL_COMMAND_BIN_SH, SHELL_COMMAND_CTRL, cmd);
            processBuilder.redirectErrorStream(true);
            process = processBuilder.start();
            LogUtils.d(TAG, "execProcess:" + cmd);
            return process;
        } catch (IOException e) {
            e.printStackTrace();
            return process;
        }
    }

    public static Process execProcessWithoutSh(String cmd) {
        Process process = null;
        try {
            ProcessBuilder processBuilder = new ProcessBuilder(cmd);
            processBuilder.redirectErrorStream(true);
            process = processBuilder.start();
            LogUtils.d(TAG, "execProcessWithoutSh:" + cmd);
            return process;
        } catch (IOException e) {
            e.printStackTrace();
            return process;
        }
    }

    public static Process execCommand(String cmd) {
        LogUtils.d(TAG, "execCommand: " + cmd);
        Process process = null;
        BufferedReader br = null;
        InputStreamReader isr = null;
        try {
            try {
                process = execProcess(cmd);
                isr = new InputStreamReader(process.getInputStream());
                br = new BufferedReader(isr);
                while (true) {
                    String line = br.readLine();
                    if (line != null) {
                        LogUtils.d(TAG, "readLine(): " + line);
                    } else {
                        process.waitFor();
                        IoUtils.closeQuietly(isr);
                        IoUtils.closeQuietly(br);
                        process.destroy();
                        return process;
                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
                IoUtils.closeQuietly(isr);
                IoUtils.closeQuietly(br);
                if (process != null) {
                    process.destroy();
                }
                return null;
            }
        } catch (Throwable th) {
            IoUtils.closeQuietly(isr);
            IoUtils.closeQuietly(br);
            if (process != null) {
                process.destroy();
            }
            throw th;
        }
    }

    /* JADX WARN: Code restructure failed: missing block: B:17:0x006e, code lost:
        if (r2 == null) goto L17;
     */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct add '--show-bad-code' argument
    */
    public static boolean execCommand(java.lang.String r8, java.lang.String r9) {
        /*
            java.lang.StringBuilder r0 = new java.lang.StringBuilder
            r0.<init>()
            java.lang.String r1 = "execCommand: "
            r0.append(r1)
            r0.append(r8)
            java.lang.String r0 = r0.toString()
            java.lang.String r1 = "ProcessUtil"
            com.xiaopeng.lib.utils.LogUtils.d(r1, r0)
            r0 = 0
            r2 = 0
            r3 = 0
            r4 = 0
            java.lang.Process r5 = execProcess(r8)     // Catch: java.lang.Throwable -> L62 java.lang.Exception -> L64
            r2 = r5
            java.io.InputStreamReader r5 = new java.io.InputStreamReader     // Catch: java.lang.Throwable -> L62 java.lang.Exception -> L64
            java.io.InputStream r6 = r2.getInputStream()     // Catch: java.lang.Throwable -> L62 java.lang.Exception -> L64
            r5.<init>(r6)     // Catch: java.lang.Throwable -> L62 java.lang.Exception -> L64
            r4 = r5
            java.io.BufferedReader r5 = new java.io.BufferedReader     // Catch: java.lang.Throwable -> L62 java.lang.Exception -> L64
            r5.<init>(r4)     // Catch: java.lang.Throwable -> L62 java.lang.Exception -> L64
            r3 = r5
            r5 = 0
        L30:
            java.lang.String r6 = r3.readLine()     // Catch: java.lang.Throwable -> L62 java.lang.Exception -> L64
            r5 = r6
            if (r6 == 0) goto L53
            java.lang.StringBuilder r6 = new java.lang.StringBuilder     // Catch: java.lang.Throwable -> L62 java.lang.Exception -> L64
            r6.<init>()     // Catch: java.lang.Throwable -> L62 java.lang.Exception -> L64
            java.lang.String r7 = "readLine(): "
            r6.append(r7)     // Catch: java.lang.Throwable -> L62 java.lang.Exception -> L64
            r6.append(r5)     // Catch: java.lang.Throwable -> L62 java.lang.Exception -> L64
            java.lang.String r6 = r6.toString()     // Catch: java.lang.Throwable -> L62 java.lang.Exception -> L64
            com.xiaopeng.lib.utils.LogUtils.d(r1, r6)     // Catch: java.lang.Throwable -> L62 java.lang.Exception -> L64
            boolean r6 = r5.contains(r9)     // Catch: java.lang.Throwable -> L62 java.lang.Exception -> L64
            if (r6 == 0) goto L30
            r0 = 1
            goto L30
        L53:
            r2.waitFor()     // Catch: java.lang.Throwable -> L62 java.lang.Exception -> L64
            com.xiaopeng.commonfunc.utils.IoUtils.closeQuietly(r4)
            com.xiaopeng.commonfunc.utils.IoUtils.closeQuietly(r3)
        L5e:
            r2.destroy()
            goto L71
        L62:
            r1 = move-exception
            goto L72
        L64:
            r1 = move-exception
            r1.printStackTrace()     // Catch: java.lang.Throwable -> L62
            com.xiaopeng.commonfunc.utils.IoUtils.closeQuietly(r4)
            com.xiaopeng.commonfunc.utils.IoUtils.closeQuietly(r3)
            if (r2 == 0) goto L71
            goto L5e
        L71:
            return r0
        L72:
            com.xiaopeng.commonfunc.utils.IoUtils.closeQuietly(r4)
            com.xiaopeng.commonfunc.utils.IoUtils.closeQuietly(r3)
            if (r2 == 0) goto L7d
            r2.destroy()
        L7d:
            throw r1
        */
        throw new UnsupportedOperationException("Method not decompiled: com.xiaopeng.commonfunc.utils.ProcessUtil.execCommand(java.lang.String, java.lang.String):boolean");
    }

    public static void removeFile(String path) {
        LogUtils.d(TAG, "removeFile: " + path);
        Process process = null;
        try {
            try {
                process = execProcess(RM_RF_COMMAND + path);
                process.waitFor();
            } catch (Exception e) {
                e.printStackTrace();
                if (process == null) {
                    return;
                }
            }
            process.destroy();
        } catch (Throwable th) {
            if (process != null) {
                process.destroy();
            }
            throw th;
        }
    }

    /* JADX WARN: Code restructure failed: missing block: B:17:0x0073, code lost:
        if (r2 == null) goto L13;
     */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct add '--show-bad-code' argument
    */
    public static java.lang.String[] execReturnLine(java.lang.String r8, java.lang.String r9) {
        /*
            java.lang.StringBuilder r0 = new java.lang.StringBuilder
            r0.<init>()
            java.lang.String r1 = "execReturnLine: "
            r0.append(r1)
            r0.append(r8)
            java.lang.String r0 = r0.toString()
            java.lang.String r1 = "ProcessUtil"
            com.xiaopeng.lib.utils.LogUtils.d(r1, r0)
            r0 = 0
            r2 = 0
            r3 = 0
            r4 = 0
            java.lang.Process r5 = execProcess(r8)     // Catch: java.lang.Throwable -> L67 java.lang.Exception -> L69
            r2 = r5
            java.io.InputStreamReader r5 = new java.io.InputStreamReader     // Catch: java.lang.Throwable -> L67 java.lang.Exception -> L69
            java.io.InputStream r6 = r2.getInputStream()     // Catch: java.lang.Throwable -> L67 java.lang.Exception -> L69
            r5.<init>(r6)     // Catch: java.lang.Throwable -> L67 java.lang.Exception -> L69
            r4 = r5
            java.io.BufferedReader r5 = new java.io.BufferedReader     // Catch: java.lang.Throwable -> L67 java.lang.Exception -> L69
            r5.<init>(r4)     // Catch: java.lang.Throwable -> L67 java.lang.Exception -> L69
            r3 = r5
            r5 = 0
        L30:
            java.lang.String r6 = r3.readLine()     // Catch: java.lang.Throwable -> L67 java.lang.Exception -> L69
            r5 = r6
            if (r6 == 0) goto L58
            java.lang.StringBuilder r6 = new java.lang.StringBuilder     // Catch: java.lang.Throwable -> L67 java.lang.Exception -> L69
            r6.<init>()     // Catch: java.lang.Throwable -> L67 java.lang.Exception -> L69
            java.lang.String r7 = "readLine(): "
            r6.append(r7)     // Catch: java.lang.Throwable -> L67 java.lang.Exception -> L69
            r6.append(r5)     // Catch: java.lang.Throwable -> L67 java.lang.Exception -> L69
            java.lang.String r6 = r6.toString()     // Catch: java.lang.Throwable -> L67 java.lang.Exception -> L69
            com.xiaopeng.lib.utils.LogUtils.d(r1, r6)     // Catch: java.lang.Throwable -> L67 java.lang.Exception -> L69
            boolean r6 = r5.contains(r9)     // Catch: java.lang.Throwable -> L67 java.lang.Exception -> L69
            if (r6 == 0) goto L30
            java.lang.String r1 = "\\s+"
            java.lang.String[] r1 = r5.split(r1)     // Catch: java.lang.Throwable -> L67 java.lang.Exception -> L69
            r0 = r1
        L58:
            r2.waitFor()     // Catch: java.lang.Throwable -> L67 java.lang.Exception -> L69
            com.xiaopeng.commonfunc.utils.IoUtils.closeQuietly(r4)
            com.xiaopeng.commonfunc.utils.IoUtils.closeQuietly(r3)
        L63:
            r2.destroy()
            goto L76
        L67:
            r1 = move-exception
            goto L77
        L69:
            r1 = move-exception
            r1.printStackTrace()     // Catch: java.lang.Throwable -> L67
            com.xiaopeng.commonfunc.utils.IoUtils.closeQuietly(r4)
            com.xiaopeng.commonfunc.utils.IoUtils.closeQuietly(r3)
            if (r2 == 0) goto L76
            goto L63
        L76:
            return r0
        L77:
            com.xiaopeng.commonfunc.utils.IoUtils.closeQuietly(r4)
            com.xiaopeng.commonfunc.utils.IoUtils.closeQuietly(r3)
            if (r2 == 0) goto L82
            r2.destroy()
        L82:
            throw r1
        */
        throw new UnsupportedOperationException("Method not decompiled: com.xiaopeng.commonfunc.utils.ProcessUtil.execReturnLine(java.lang.String, java.lang.String):java.lang.String[]");
    }

    public static boolean runI2cRead(String i2c, String chipid) {
        Process process = execProcess(I2CGET_COMMAND + i2c);
        if (process == null) {
            return false;
        }
        BufferedReader br = null;
        try {
            try {
                br = new BufferedReader(new InputStreamReader(process.getInputStream()));
                String line = br.readLine();
                LogUtils.d(TAG, "runI2cRead line:" + line + ", chipid:" + chipid);
                result = line.equals(chipid);
                try {
                    br.close();
                } catch (IOException e) {
                    e = e;
                    e.printStackTrace();
                    process.destroy();
                    return result;
                }
            } catch (Exception e2) {
                LogUtils.d(TAG, "fail to run i2cget");
                e2.printStackTrace();
                if (br != null) {
                    try {
                        br.close();
                    } catch (IOException e3) {
                        e = e3;
                        e.printStackTrace();
                        process.destroy();
                        return result;
                    }
                }
            }
            process.destroy();
            return result;
        } catch (Throwable th) {
            if (br != null) {
                try {
                    br.close();
                } catch (IOException e4) {
                    e4.printStackTrace();
                }
            }
            process.destroy();
            throw th;
        }
    }

    public static String getPhyLinkStat() {
        return getShellCmdResult("phytool get link_status");
    }

    public static String getPhyMasterSlaveMode() {
        return getShellCmdResult("phytool get master_cfg");
    }

    public static String getPhySqi() {
        return getShellCmdResult("phytool get sqi");
    }

    private static String getShellCmdResult(String cmd) {
        String result = "";
        Process process = execProcess(cmd);
        if (process == null) {
            return "";
        }
        BufferedReader br = null;
        try {
            try {
                br = new BufferedReader(new InputStreamReader(process.getInputStream()));
                result = br.readLine();
                LogUtils.d(TAG, "getShellCmdResult result:" + result);
                try {
                    br.close();
                } catch (IOException e) {
                    e = e;
                    e.printStackTrace();
                    process.destroy();
                    return result;
                }
            } catch (Exception e2) {
                LogUtils.d(TAG, "fail to getShellCmdResult");
                e2.printStackTrace();
                if (br != null) {
                    try {
                        br.close();
                    } catch (IOException e3) {
                        e = e3;
                        e.printStackTrace();
                        process.destroy();
                        return result;
                    }
                }
            }
            process.destroy();
            return result;
        } catch (Throwable th) {
            if (br != null) {
                try {
                    br.close();
                } catch (IOException e4) {
                    e4.printStackTrace();
                }
            }
            process.destroy();
            throw th;
        }
    }

    /* JADX WARN: Code restructure failed: missing block: B:17:0x0099, code lost:
        if (r2.toString().indexOf(com.xiaopeng.commonfunc.Constant.PING_RESULT_TTL_UPPERCASE) > 0) goto L22;
     */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct add '--show-bad-code' argument
    */
    public static boolean sendPing(java.lang.String r11) {
        /*
            java.lang.String r0 = "ProcessUtil"
            r1 = 0
            java.lang.StringBuilder r2 = new java.lang.StringBuilder
            r2.<init>()
            java.lang.StringBuilder r3 = new java.lang.StringBuilder
            r3.<init>()
            java.lang.String r4 = "ping -c 4 "
            r3.append(r4)
            r3.append(r11)
            java.lang.String r3 = r3.toString()
            java.lang.Process r4 = execProcess(r3)
            r5 = 0
            if (r4 != 0) goto L21
            return r5
        L21:
            r6 = 0
            com.xiaopeng.commonfunc.utils.-$$Lambda$ProcessUtil$QCbomy7BrZGkXESTSJiLmfzXJ0w r7 = new com.xiaopeng.commonfunc.utils.-$$Lambda$ProcessUtil$QCbomy7BrZGkXESTSJiLmfzXJ0w
            r7.<init>()
            r8 = 5000(0x1388, double:2.4703E-320)
            com.xiaopeng.lib.utils.ThreadUtils.postDelayed(r5, r7, r8)
            java.io.BufferedReader r7 = new java.io.BufferedReader     // Catch: java.lang.Throwable -> La4 java.lang.Exception -> La6
            java.io.InputStreamReader r8 = new java.io.InputStreamReader     // Catch: java.lang.Throwable -> La4 java.lang.Exception -> La6
            java.io.InputStream r9 = r4.getInputStream()     // Catch: java.lang.Throwable -> La4 java.lang.Exception -> La6
            r8.<init>(r9)     // Catch: java.lang.Throwable -> La4 java.lang.Exception -> La6
            r7.<init>(r8)     // Catch: java.lang.Throwable -> La4 java.lang.Exception -> La6
            r6 = r7
            r7 = 0
            java.lang.String r8 = "sendPing readline"
            com.xiaopeng.lib.utils.LogUtils.d(r0, r8)     // Catch: java.lang.Throwable -> La4 java.lang.Exception -> La6
        L41:
            r8 = 4
            if (r7 >= r8) goto L65
            java.lang.String r8 = r6.readLine()     // Catch: java.lang.Throwable -> La4 java.lang.Exception -> La6
            r9 = r8
            if (r8 == 0) goto L65
            java.lang.StringBuilder r8 = new java.lang.StringBuilder     // Catch: java.lang.Throwable -> La4 java.lang.Exception -> La6
            r8.<init>()     // Catch: java.lang.Throwable -> La4 java.lang.Exception -> La6
            java.lang.String r10 = "sendPing line"
            r8.append(r10)     // Catch: java.lang.Throwable -> La4 java.lang.Exception -> La6
            r8.append(r9)     // Catch: java.lang.Throwable -> La4 java.lang.Exception -> La6
            java.lang.String r8 = r8.toString()     // Catch: java.lang.Throwable -> La4 java.lang.Exception -> La6
            com.xiaopeng.lib.utils.LogUtils.d(r0, r8)     // Catch: java.lang.Throwable -> La4 java.lang.Exception -> La6
            r2.append(r9)     // Catch: java.lang.Throwable -> La4 java.lang.Exception -> La6
            int r7 = r7 + 1
            goto L41
        L65:
            java.lang.StringBuilder r8 = new java.lang.StringBuilder     // Catch: java.lang.Throwable -> La4 java.lang.Exception -> La6
            r8.<init>()     // Catch: java.lang.Throwable -> La4 java.lang.Exception -> La6
            java.lang.String r9 = "sendPing resultBuf = "
            r8.append(r9)     // Catch: java.lang.Throwable -> La4 java.lang.Exception -> La6
            r8.append(r2)     // Catch: java.lang.Throwable -> La4 java.lang.Exception -> La6
            java.lang.String r8 = r8.toString()     // Catch: java.lang.Throwable -> La4 java.lang.Exception -> La6
            com.xiaopeng.lib.utils.LogUtils.d(r0, r8)     // Catch: java.lang.Throwable -> La4 java.lang.Exception -> La6
            java.lang.String r8 = r2.toString()     // Catch: java.lang.Throwable -> La4 java.lang.Exception -> La6
            boolean r8 = r8.isEmpty()     // Catch: java.lang.Throwable -> La4 java.lang.Exception -> La6
            if (r8 != 0) goto L9d
            java.lang.String r8 = r2.toString()     // Catch: java.lang.Throwable -> La4 java.lang.Exception -> La6
            java.lang.String r9 = "ttl"
            int r8 = r8.indexOf(r9)     // Catch: java.lang.Throwable -> La4 java.lang.Exception -> La6
            if (r8 > 0) goto L9b
            java.lang.String r8 = r2.toString()     // Catch: java.lang.Throwable -> La4 java.lang.Exception -> La6
            java.lang.String r9 = "TTL"
            int r0 = r8.indexOf(r9)     // Catch: java.lang.Throwable -> La4 java.lang.Exception -> La6
            if (r0 <= 0) goto L9c
        L9b:
            r5 = 1
        L9c:
            r1 = r5
        L9d:
            r6.close()     // Catch: java.io.IOException -> La2
            goto Lc9
        La2:
            r0 = move-exception
            goto Lc5
        La4:
            r0 = move-exception
            goto Ld2
        La6:
            r5 = move-exception
            java.lang.StringBuilder r7 = new java.lang.StringBuilder     // Catch: java.lang.Throwable -> La4
            r7.<init>()     // Catch: java.lang.Throwable -> La4
            java.lang.String r8 = "fail to ping "
            r7.append(r8)     // Catch: java.lang.Throwable -> La4
            r7.append(r11)     // Catch: java.lang.Throwable -> La4
            java.lang.String r7 = r7.toString()     // Catch: java.lang.Throwable -> La4
            com.xiaopeng.lib.utils.LogUtils.d(r0, r7)     // Catch: java.lang.Throwable -> La4
            r5.printStackTrace()     // Catch: java.lang.Throwable -> La4
            if (r6 == 0) goto Lc9
            r6.close()     // Catch: java.io.IOException -> Lc4
            goto Lc9
        Lc4:
            r0 = move-exception
        Lc5:
            r0.printStackTrace()
            goto Lca
        Lc9:
        Lca:
            r4.destroy()
            killProcess(r3)
            return r1
        Ld2:
            if (r6 == 0) goto Ldd
            r6.close()     // Catch: java.io.IOException -> Ld8
            goto Ldd
        Ld8:
            r5 = move-exception
            r5.printStackTrace()
            goto Lde
        Ldd:
        Lde:
            r4.destroy()
            killProcess(r3)
            throw r0
        */
        throw new UnsupportedOperationException("Method not decompiled: com.xiaopeng.commonfunc.utils.ProcessUtil.sendPing(java.lang.String):boolean");
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public static /* synthetic */ void lambda$sendPing$0(Process process) {
        LogUtils.d(TAG, "sendPing timeout");
        process.destroy();
    }

    public static boolean sendPing(String site, int pingCount, int expectedVal) {
        boolean isConnected = false;
        new StringBuilder();
        if (pingCount < 1 || expectedVal > pingCount) {
            return false;
        }
        String cmd = PING_COMMAND + pingCount + Constant.SPACE_STRING + site;
        final Process process = execProcess(cmd);
        if (process == null) {
            return false;
        }
        BufferedReader br = null;
        ThreadUtils.postDelayed(0, new Runnable() { // from class: com.xiaopeng.commonfunc.utils.-$$Lambda$ProcessUtil$lIwNHVyE9rJURj6N5s2ojneZSOo
            @Override // java.lang.Runnable
            public final void run() {
                ProcessUtil.lambda$sendPing$1(process);
            }
        }, (pingCount + 1) * 1000);
        try {
            try {
                br = new BufferedReader(new InputStreamReader(process.getInputStream()));
                LogUtils.d(TAG, "sendPing readline");
                for (int lineCount = 0; lineCount <= pingCount; lineCount++) {
                    String line = br.readLine();
                    if (line == null) {
                        break;
                    }
                    LogUtils.d(TAG, "sendPing line" + line);
                    if (line.contains(Constant.PING_RESULT_TTL_LOWERCASE) || line.contains(Constant.PING_RESULT_TTL_UPPERCASE)) {
                        expectedVal--;
                    }
                }
                isConnected = expectedVal == 0;
                try {
                    br.close();
                } catch (IOException e) {
                    e = e;
                    e.printStackTrace();
                    process.destroy();
                    killProcess(cmd);
                    return isConnected;
                }
            } catch (Throwable th) {
                if (br != null) {
                    try {
                        br.close();
                    } catch (IOException e2) {
                        e2.printStackTrace();
                    }
                }
                process.destroy();
                killProcess(cmd);
                throw th;
            }
        } catch (Exception e3) {
            LogUtils.d(TAG, "fail to ping " + site);
            e3.printStackTrace();
            if (br != null) {
                try {
                    br.close();
                } catch (IOException e4) {
                    e = e4;
                    e.printStackTrace();
                    process.destroy();
                    killProcess(cmd);
                    return isConnected;
                }
            }
        }
        process.destroy();
        killProcess(cmd);
        return isConnected;
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public static /* synthetic */ void lambda$sendPing$1(Process process) {
        LogUtils.d(TAG, "sendPing timeout");
        process.destroy();
    }

    public static boolean runCommand(String cmd) {
        StringBuilder resultBuf = new StringBuilder();
        Process process = execProcess(cmd);
        if (process == null) {
            return false;
        }
        BufferedReader br = null;
        try {
            try {
                try {
                    br = new BufferedReader(new InputStreamReader(process.getInputStream()));
                    LogUtils.d(TAG, "runCommand");
                    while (true) {
                        String line = br.readLine();
                        if (line == null) {
                            break;
                        }
                        LogUtils.d(TAG, "runCommand line:" + line);
                        resultBuf.append(line);
                    }
                    br.close();
                    process.waitFor();
                    res = process.exitValue() == 0;
                    process.destroy();
                } catch (Exception e) {
                    LogUtils.d(TAG, "fail to runCommand cmd: " + cmd);
                    e.printStackTrace();
                    if (br != null) {
                        br.close();
                    }
                    process.waitFor();
                    res = process.exitValue() == 0;
                    process.destroy();
                }
            } catch (Exception e2) {
                e2.printStackTrace();
            }
            LogUtils.d(TAG, "runCommand cmd:" + cmd + ", res:" + res);
            return res;
        } catch (Throwable th) {
            if (br != null) {
                try {
                    br.close();
                } catch (Exception e3) {
                    e3.printStackTrace();
                    throw th;
                }
            }
            process.waitFor();
            if (process.exitValue() == 0) {
            }
            process.destroy();
            throw th;
        }
    }

    public static boolean isServiceRunning(Context context, String serviceName) {
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

    public static void reboot(final Context context, final String reason) {
        ThreadUtils.execute(new Runnable() { // from class: com.xiaopeng.commonfunc.utils.-$$Lambda$ProcessUtil$svT7Q1DdJMIcsm7P3eQMGRg3eLc
            @Override // java.lang.Runnable
            public final void run() {
                ProcessUtil.lambda$reboot$2(context, reason);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public static /* synthetic */ void lambda$reboot$2(Context context, String reason) {
        PowerManager mPowerManager = (PowerManager) context.getSystemService("power");
        mPowerManager.reboot(reason);
    }

    public static String getAppName(Context context, int pID) {
        ActivityManager am = (ActivityManager) context.getSystemService("activity");
        List<ActivityManager.RunningAppProcessInfo> runningApps = am.getRunningAppProcesses();
        if (runningApps == null || runningApps.size() <= 0) {
            return "";
        }
        for (ActivityManager.RunningAppProcessInfo info : runningApps) {
            if (info.pid == pID) {
                String processName = info.processName;
                return processName;
            }
        }
        return "";
    }

    public static boolean changePemToPkcs12(String keyPemPath, String certPemPath, String password, String outPath) {
        return runCommand("openssl pkcs12 -export -inkey " + keyPemPath + " -in " + certPemPath + " -password pass:\"" + password + "\" -out " + outPath);
    }

    public static void copyTboxLog(String destPath) {
        FileUtil.deleteFolderFile(destPath);
        FileUtil.mkDir(destPath);
        String cmd = ScpUtil.genTboxDltSync(ScpUtil.SSH_ROOT_USER, ScpUtil.HOST_TBOX, ScpUtil.FILE_SCP_PUBKEY);
        execCommand(cmd);
        String cmd2 = ScpUtil.genCompressFile(ScpUtil.SSH_ROOT_USER, ScpUtil.HOST_TBOX, ScpUtil.FILE_SCP_PUBKEY, Constant.TBOX_ALL_LOG, Constant.TBOX_LOG_DIR);
        execCommand(cmd2);
        String cmd3 = ScpUtil.genScpFromServerCmd(ScpUtil.SSH_ROOT_USER, ScpUtil.HOST_TBOX, Constant.TBOX_ALL_LOG, destPath, ScpUtil.FILE_SCP_PUBKEY);
        execCommand(cmd3);
        String cmd4 = ScpUtil.genRemoveFile(ScpUtil.SSH_ROOT_USER, ScpUtil.HOST_TBOX, Constant.TBOX_ALL_LOG, ScpUtil.FILE_SCP_PUBKEY);
        execCommand(cmd4);
    }
}
