package com.xiaopeng.cardiagnosis.service;

import android.app.ActivityManager;
import android.app.Service;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.os.IBinder;
import android.os.PowerManager;
import android.os.RemoteException;
import android.os.SystemClock;
import android.os.UserHandle;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.util.Log;
import com.xiaopeng.cardiagnosis.CarApplication;
import com.xiaopeng.cardiagnosis.aidl.IDealCaton;
import com.xiaopeng.commonfunc.utils.BugHunterUtils;
import com.xiaopeng.commonfunc.utils.MD5Utils;
import com.xiaopeng.datalog.DataLogModuleEntry;
import com.xiaopeng.lib.framework.module.IModuleEntry;
import com.xiaopeng.lib.framework.module.Module;
import com.xiaopeng.lib.framework.moduleinterface.datalogmodule.IDataLog;
import com.xiaopeng.lib.framework.moduleinterface.datalogmodule.IMoleEventBuilder;
import com.xiaopeng.lib.utils.info.DeviceInfoUtils;
import java.io.File;
import java.io.RandomAccessFile;
import java.util.List;
import net.lingala.zip4j.util.InternalZipConstants;
import org.json.JSONObject;
/* loaded from: classes4.dex */
public class DealCatonService extends Service {
    private static final String CATON_EVENT = "perf_caton";
    private static final String KEY_ANR_FLAG = "anr";
    private static final String KEY_APP_NAME = "appName";
    private static final String KEY_APP_VER = "appVer";
    private static final String KEY_EVENT = "_event";
    private static final String KEY_EVENT_TIME = "_time";
    private static final String KEY_MCU_VER = "_mcuver";
    private static final String KEY_MEM_INFO = "memInfo";
    private static final String KEY_MODULE = "_module";
    private static final String KEY_MODULE_VER = "_module_version";
    private static final String KEY_NETWORK = "_network";
    private static final String KEY_STACK_INFO = "stackInfo";
    private static final String KEY_STACK_MD5 = "md5";
    private static final String KEY_STUCK_TIME = "elapseTime";
    private static final String KEY_SYSTEM_BOOT_TIME = "_st_time";
    public static boolean LOG_ENABLED = true;
    private static final String TAG = "ReportService";
    private static boolean dumpToSdCardFlag = false;
    private static boolean isScreenOn = false;
    private static final String outputDir = "/sdcard/Log";
    private final IDealCaton.Stub binder = new IDealCaton.Stub() { // from class: com.xiaopeng.cardiagnosis.service.DealCatonService.1
        public void dealCaton(String stackTraces, String stackTracesforMd5, boolean needCheckAnr, String packageName, long stuckElapseTime, long threadElapseTime) {
            String memInfo;
            String anr;
            if (DealCatonService.isScreenOn) {
                String md5 = DealCatonService.calcStackTraceMd5(stackTracesforMd5);
                if (DealCatonService.LOG_ENABLED) {
                    String memInfo2 = DealCatonService.printStackTrace(md5, packageName, stackTraces, stuckElapseTime, threadElapseTime);
                    memInfo = memInfo2;
                } else {
                    memInfo = "";
                }
                if (needCheckAnr) {
                    String anr2 = DealCatonService.this.checkAnr();
                    anr = anr2;
                } else {
                    anr = "";
                }
                boolean isAnr = !TextUtils.isEmpty(anr);
                try {
                    DealCatonService.reportCaton(packageName, Boolean.valueOf(isAnr), md5, stuckElapseTime, stackTraces, memInfo, DealCatonService.getVersionName(packageName));
                } catch (Exception e) {
                }
                if (DealCatonService.dumpToSdCardFlag) {
                    String msg = DealCatonService.getJsonStuckLog(packageName, DealCatonService.getVersionName(packageName), stuckElapseTime, md5, stackTraces);
                    byte[] msgBytes = msg.getBytes();
                    if (DealCatonService.dumpToSdCardFlag) {
                        DealCatonService.dumpCatonInfo("/sdcard/Log/caton", packageName, msgBytes);
                    }
                }
            }
        }
    };
    private final BroadcastReceiver mBroadcastReceiver = new BroadcastReceiver() { // from class: com.xiaopeng.cardiagnosis.service.DealCatonService.2
        @Override // android.content.BroadcastReceiver
        public void onReceive(Context context, Intent intent) {
            char c;
            String action = intent.getAction();
            Log.d(DealCatonService.TAG, "onReceive, action--->" + action);
            int hashCode = action.hashCode();
            if (hashCode != -2128145023) {
                if (hashCode == -1454123155 && action.equals("android.intent.action.SCREEN_ON")) {
                    c = 0;
                }
                c = 65535;
            } else {
                if (action.equals("android.intent.action.SCREEN_OFF")) {
                    c = 1;
                }
                c = 65535;
            }
            if (c == 0) {
                Log.i(DealCatonService.TAG, "isScreenOn true");
                boolean unused = DealCatonService.isScreenOn = true;
            } else if (c == 1) {
                Log.i(DealCatonService.TAG, "isScreenOn false");
                boolean unused2 = DealCatonService.isScreenOn = false;
            }
        }
    };

    public static void log(String tag, String msg) {
        if (LOG_ENABLED) {
            Log.e(tag, msg);
        }
    }

    private void initCaton(boolean enableDumpFile) {
        if (enableDumpFile) {
            dumpToSdCardFlag = true;
            return;
        }
        File dumpFlagFile = new File("/sdcard/Log/catondumpflag");
        if (dumpFlagFile.exists()) {
            dumpToSdCardFlag = true;
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static void reportCaton(String packageName, Boolean isAnr, String md5, long stuckElapseTime, String stack, String memInfo, String versionName) {
        Log.e(TAG, "reportCaton");
        IDataLog dataLogService = null;
        IModuleEntry moduleEntry = Module.get(DataLogModuleEntry.class);
        if (moduleEntry == null) {
            Log.e(TAG, "<<<< error, can not get DataLogModuleEntry");
        } else {
            try {
                dataLogService = (IDataLog) moduleEntry.get(IDataLog.class);
            } catch (Throwable t) {
                t.printStackTrace();
                Log.e(TAG, "<<<< error, get IDataLog module occurred an exception, " + t.getMessage());
            }
        }
        if (dataLogService == null) {
            Log.e(TAG, "<<<< upload caton log fail, can not get IDataLog module!");
            return;
        }
        boolean international = DeviceInfoUtils.isInternationalVer();
        if (!international) {
            Log.e(TAG, "reportCaton module");
            IMoleEventBuilder builder = dataLogService.buildMoleEvent();
            builder.setEvent(CATON_EVENT).setPageId("P00010").setButtonId("B001").setProperty(KEY_APP_NAME, packageName).setProperty(KEY_APP_VER, versionName).setProperty(KEY_ANR_FLAG, isAnr.booleanValue()).setProperty(KEY_STACK_MD5, md5).setProperty(KEY_STUCK_TIME, Long.valueOf(stuckElapseTime)).setProperty(KEY_STACK_INFO, stack).setProperty(KEY_MEM_INFO, memInfo);
            dataLogService.sendStatData(builder.build());
        }
    }

    /*  JADX ERROR: JadxRuntimeException in pass: BlockProcessor
        jadx.core.utils.exceptions.JadxRuntimeException: Unreachable block: B:27:0x00c8
        	at jadx.core.dex.visitors.blocks.BlockProcessor.checkForUnreachableBlocks(BlockProcessor.java:81)
        	at jadx.core.dex.visitors.blocks.BlockProcessor.processBlocksTree(BlockProcessor.java:47)
        	at jadx.core.dex.visitors.blocks.BlockProcessor.visit(BlockProcessor.java:39)
        */
    /* JADX INFO: Access modifiers changed from: private */
    public static java.lang.String printStackTrace(java.lang.String r17, java.lang.String r18, java.lang.String r19, long r20, long r22) {
        /*
            r1 = r18
            org.json.JSONObject r0 = new org.json.JSONObject
            r0.<init>()
            r2 = r0
            r3 = 0
            r5 = 0
            r7 = 0
            java.lang.StringBuffer r0 = new java.lang.StringBuffer
            java.lang.String r9 = ""
            r0.<init>(r9)
            r9 = r0
            android.app.IActivityManager r0 = android.app.ActivityManager.getService()     // Catch: android.os.RemoteException -> L38
            if (r0 == 0) goto L37
            android.app.ActivityManager$MemoryInfo r0 = new android.app.ActivityManager$MemoryInfo     // Catch: android.os.RemoteException -> L38
            r0.<init>()     // Catch: android.os.RemoteException -> L38
            android.app.IActivityManager r10 = android.app.ActivityManager.getService()     // Catch: android.os.RemoteException -> L38
            r10.getMemoryInfo(r0)     // Catch: android.os.RemoteException -> L38
            long r10 = r0.availMem     // Catch: android.os.RemoteException -> L38
            r12 = 1048576(0x100000, double:5.180654E-318)
            long r10 = r10 / r12
            r3 = r10
            long r10 = r0.totalMem     // Catch: android.os.RemoteException -> L38
            long r10 = r10 / r12
            r5 = r10
            long r10 = r0.threshold     // Catch: android.os.RemoteException -> L38
            long r10 = r10 / r12
            r7 = r10
        L37:
            goto L39
        L38:
            r0 = move-exception
        L39:
            java.lang.String r0 = "availMem:"
            r9.append(r0)
            r9.append(r3)
            java.lang.String r0 = "totalMem:"
            r9.append(r0)
            r9.append(r5)
            java.lang.String r0 = "threshold:"
            r9.append(r0)
            r9.append(r7)
            java.lang.StringBuilder r0 = new java.lang.StringBuilder
            r0.<init>()
            r10 = r0
            java.lang.String r0 = "\n----------------caton log [ "
            r10.append(r0)
            boolean r0 = android.text.TextUtils.isEmpty(r18)
            if (r0 != 0) goto L65
            r10.append(r1)
        L65:
            java.lang.String r0 = " ]"
            r10.append(r0)
            java.lang.String r0 = "\n"
            r10.append(r0)
            r11 = r19
            r10.append(r11)
            java.text.SimpleDateFormat r0 = new java.text.SimpleDateFormat
            java.lang.String r12 = "YYYY/MM/dd HH:mm:ss"
            r0.<init>(r12)
            r12 = r0
            java.lang.String r0 = "md5"
            r13 = r17
            r2.put(r0, r13)     // Catch: org.json.JSONException -> Lc6
            java.lang.String r0 = "pkgName"
            r2.put(r0, r1)     // Catch: org.json.JSONException -> Lc6
            java.lang.String r0 = "time"
            java.util.Calendar r14 = java.util.Calendar.getInstance()     // Catch: org.json.JSONException -> Lc6
            java.util.Date r14 = r14.getTime()     // Catch: org.json.JSONException -> Lc6
            java.lang.String r14 = r12.format(r14)     // Catch: org.json.JSONException -> Lc6
            r2.put(r0, r14)     // Catch: org.json.JSONException -> Lc6
            java.lang.String r0 = "ElapseTime"
            r14 = r20
            r2.put(r0, r14)     // Catch: org.json.JSONException -> Lc4
            java.lang.String r0 = "threadElapseTime"
            r16 = r12
            r11 = r22
            r2.put(r0, r11)     // Catch: org.json.JSONException -> Lc2
            java.lang.String r0 = "availMem"
            r2.put(r0, r3)     // Catch: org.json.JSONException -> Lc2
            java.lang.String r0 = "totalMem"
            r2.put(r0, r5)     // Catch: org.json.JSONException -> Lc2
            java.lang.String r0 = "threshold"
            r2.put(r0, r7)     // Catch: org.json.JSONException -> Lc2
            java.lang.String r0 = "catonLog"
            java.lang.String r1 = r10.toString()     // Catch: org.json.JSONException -> Lc2
            r2.put(r0, r1)     // Catch: org.json.JSONException -> Lc2
            goto Ld4
        Lc2:
            r0 = move-exception
            goto Ld1
        Lc4:
            r0 = move-exception
            goto Lcd
        Lc6:
            r0 = move-exception
            goto Lcb
        Lc8:
            r0 = move-exception
            r13 = r17
        Lcb:
            r14 = r20
        Lcd:
            r16 = r12
            r11 = r22
        Ld1:
            r0.printStackTrace()
        Ld4:
            java.lang.String r0 = r2.toString()
            java.lang.String r1 = "ReportService"
            log(r1, r0)
            java.lang.String r0 = r9.toString()
            return r0
        */
        throw new UnsupportedOperationException("Method not decompiled: com.xiaopeng.cardiagnosis.service.DealCatonService.printStackTrace(java.lang.String, java.lang.String, java.lang.String, long, long):java.lang.String");
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static void dumpCatonInfo(String logDir, String packageName, byte[] bytes) {
        File catonDir = new File(logDir);
        if (!catonDir.exists()) {
            boolean dirMade = catonDir.mkdirs();
            if (dirMade) {
                boolean setReadable = catonDir.setReadable(true, false);
                boolean setWritable = catonDir.setWritable(true, false);
                boolean setExecutable = catonDir.setExecutable(true, false);
                Log.d(TAG, "caton LogDir setReadable: " + setReadable + "; setWritable: " + setWritable + "; setExecutable: " + setExecutable);
            } else {
                Log.w(TAG, "make caton LogDir failed");
            }
        }
        Log.w(TAG, "begin dump to file");
        File catonFile = new File(logDir + InternalZipConstants.ZIP_FILE_SEPARATOR + packageName + ".log");
        RandomAccessFile randomFile = null;
        try {
            try {
                try {
                    randomFile = new RandomAccessFile(catonFile, "rw");
                    long fileLength = randomFile.length();
                    randomFile.seek(fileLength);
                    randomFile.write(bytes);
                    randomFile.writeBytes("\n\n");
                    randomFile.getFD().sync();
                    randomFile.close();
                } catch (Exception e) {
                    Log.w(TAG, e.toString());
                    if (randomFile != null) {
                        randomFile.close();
                    }
                }
            } catch (Throwable th) {
                if (randomFile != null) {
                    try {
                        randomFile.close();
                    } catch (Exception e2) {
                    }
                }
                throw th;
            }
        } catch (Exception e3) {
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static String calcStackTraceMd5(String stacktraceInfo) {
        String newInfo;
        int pos = stacktraceInfo.indexOf("\n");
        if (pos > 0) {
            newInfo = stacktraceInfo.substring(pos + 1);
        } else {
            newInfo = stacktraceInfo;
        }
        return MD5Utils.getMD5(newInfo);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static String getJsonStuckLog(String pkgName, String appVer, long elapseTime, String stackMd5, String stackInfo) {
        JSONObject jsonObj = new JSONObject();
        try {
            jsonObj.putOpt("_event", CATON_EVENT);
            jsonObj.putOpt("_module", "perf");
            jsonObj.putOpt("_mcuver", BugHunterUtils.getMCUVer());
            jsonObj.putOpt("_module_version", appVer);
            jsonObj.putOpt("_st_time", Long.valueOf(SystemClock.uptimeMillis() / 1000));
            jsonObj.putOpt("_time", Long.valueOf(System.currentTimeMillis()));
            jsonObj.putOpt("_network", BugHunterUtils.getNetworkType(CarApplication.getContext()));
            jsonObj.putOpt(KEY_APP_NAME, pkgName);
            jsonObj.putOpt(KEY_APP_VER, appVer);
            jsonObj.putOpt(KEY_ANR_FLAG, false);
            jsonObj.putOpt(KEY_STUCK_TIME, Long.valueOf(elapseTime));
            jsonObj.putOpt(KEY_STACK_MD5, stackMd5);
            jsonObj.putOpt(KEY_STACK_INFO, stackInfo);
            return jsonObj.toString();
        } catch (Throwable t) {
            Log.e(TAG, "error in function getJsonCatonLog, " + t.getMessage());
            return "";
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static String getVersionName(String packageName) {
        PackageInfo packageInfo = getPackageInfo(packageName);
        return packageInfo == null ? "" : packageInfo.versionName;
    }

    @Nullable
    private static PackageInfo getPackageInfo(String packageName) {
        try {
            PackageManager pm = CarApplication.getContext().getPackageManager();
            PackageInfo pi = pm.getPackageInfo(packageName != null ? packageName : CarApplication.getContext().getPackageName(), 16384);
            return pi;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public String checkAnr() {
        try {
            if (ActivityManager.getService() != null) {
                ActivityManager.MemoryInfo memoryInfo = new ActivityManager.MemoryInfo();
                ActivityManager.getService().getMemoryInfo(memoryInfo);
                List<ActivityManager.ProcessErrorStateInfo> errorStateInfos = ActivityManager.getService().getProcessesInErrorState();
                if (errorStateInfos != null) {
                    for (ActivityManager.ProcessErrorStateInfo info : errorStateInfos) {
                        if (info.condition == 2) {
                            StringBuilder anrInfo = new StringBuilder();
                            anrInfo.append(info.processName);
                            anrInfo.append("\n");
                            anrInfo.append(info.shortMsg);
                            anrInfo.append("\n");
                            anrInfo.append(info.longMsg);
                            log(TAG, anrInfo.toString());
                            return anrInfo.toString();
                        }
                    }
                    return "";
                }
                return "";
            }
            return "";
        } catch (RemoteException e) {
            return "";
        }
    }

    public static void start(Context context) {
        Intent intent = new Intent(context, DealCatonService.class);
        context.startService(intent);
    }

    @Override // android.app.Service
    public void onCreate() {
        super.onCreate();
        initCaton(false);
        initSreen();
        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction("android.intent.action.SCREEN_ON");
        intentFilter.addAction("android.intent.action.SCREEN_OFF");
        CarApplication.getContext().registerReceiverAsUser(this.mBroadcastReceiver, UserHandle.ALL, intentFilter, null, null);
        Log.d(TAG, "onCreate");
    }

    private static void initSreen() {
        PowerManager pm = (PowerManager) CarApplication.getContext().getSystemService("power");
        isScreenOn = pm.isScreenOn();
    }

    @Override // android.app.Service
    public IBinder onBind(Intent intent) {
        return this.binder;
    }

    @Override // android.app.Service
    public void onDestroy() {
        super.onDestroy();
        Log.d(TAG, "onDestroy");
    }
}
