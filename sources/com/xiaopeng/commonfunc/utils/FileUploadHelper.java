package com.xiaopeng.commonfunc.utils;

import android.app.Application;
import android.car.Car;
import android.content.Context;
import android.os.PowerManager;
import android.support.annotation.NonNull;
import com.google.gson.Gson;
import com.xiaopeng.commonfunc.Constant;
import com.xiaopeng.commonfunc.callback.FileUploadOssCallback;
import com.xiaopeng.datalog.DataLogModuleEntry;
import com.xiaopeng.datalog.helper.SharedPreferenceHelper;
import com.xiaopeng.lib.framework.module.Module;
import com.xiaopeng.lib.framework.moduleinterface.datalogmodule.IDataLog;
import com.xiaopeng.lib.framework.moduleinterface.datalogmodule.IMoleEventBuilder;
import com.xiaopeng.lib.framework.moduleinterface.netchannelmodule.remotestorage.Callback;
import com.xiaopeng.lib.framework.moduleinterface.netchannelmodule.remotestorage.IRemoteStorage;
import com.xiaopeng.lib.framework.moduleinterface.netchannelmodule.remotestorage.StorageException;
import com.xiaopeng.lib.framework.netchannelmodule.NetworkChannelsEntry;
import com.xiaopeng.lib.utils.DateUtils;
import com.xiaopeng.lib.utils.LogUtils;
import com.xiaopeng.lib.utils.crypt.AESUtils;
import com.xiaopeng.lib.utils.info.BuildInfoUtils;
import java.io.File;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import net.lingala.zip4j.util.InternalZipConstants;
/* loaded from: classes4.dex */
public class FileUploadHelper {
    private static final String AES_KEY = "@!chxpzi#0109$+/";
    private static final String BUCKET_AND_ENDPOINT;
    private static final String BUCKET_NAME;
    private static final String CRASH_EVEN_NAME = "bug_error";
    private static final String DATE_PATTERN_STR = "yyyyMMdd";
    private static final String KEY_LAST_LOW_TIME = "last_low_timestamp";
    private static final int LOG_TYPE_EXCEPTION = 2;
    public static final String SECURITY_BUCKET_NAME = "xp-security";
    private static final int SRC_TYPE_SYSTEM = 1;
    private static final int SYSTEM_SERVER_FAULT = 3;
    private static final String TAG = "FileUploadHelper";

    static {
        BUCKET_NAME = BuildInfoUtils.isLanVersion() ? "xp-log-local" : Constant.BUCKET_NAME;
        BUCKET_AND_ENDPOINT = "https://" + BUCKET_NAME + ".oss-cn-hangzhou.aliyuncs.com/";
    }

    public static void sendStatDataAndUploadFiles(Context context, String uploadFile) {
        if (!checkCanSend(context)) {
            return;
        }
        IDataLog mDataLog = (IDataLog) Module.get(DataLogModuleEntry.class).get(IDataLog.class);
        IMoleEventBuilder builder = mDataLog.buildMoleEvent();
        String[] uploadInfo = makeUploadInfoAndTargetFile(uploadFile);
        String logAddress = uploadInfo[0];
        String uploadTargetFile = uploadInfo[1];
        builder.setEvent(CRASH_EVEN_NAME).setPageId("P00009").setButtonId(BuriedPointUtils.STORAGE_CLEAN_BUTTONID);
        PowerManager powerManager = (PowerManager) context.getSystemService("power");
        builder.setProperty("pm_status", powerManager.isScreenOn()).setProperty("mBrief", "system storage low").setProperty("mRecTime", new SimpleDateFormat(TimeUtil.DATE_FORMAT_YYYYMMDDHHMMSS_WITH_SEPARATOR).format(Long.valueOf(System.currentTimeMillis()))).setProperty("mTitle", "system storage low").setProperty("mType", (Number) 3).setProperty("devInfo", "").setProperty("logType", (Number) 2).setProperty("srcName", "com.xiaopeng.xmart.system").setProperty("srcType", (Number) 1).setProperty("appVer", BuildInfoUtils.getSystemVersion()).setProperty(Constant.KEY_ADDRESS, logAddress).setProperty("rebootReason", "");
        List<String> filePathList = new ArrayList<>();
        filePathList.add(uploadTargetFile.replace(".zip", "_en.zip"));
        mDataLog.sendStatData(builder.build());
        LogUtils.d(TAG, "uploadFile2Cloud address = " + logAddress + " mTitle = system storage low");
        mDataLog.sendFiles(filePathList);
        SharedPreferenceHelper sp = SharedPreferenceHelper.getInstance(context);
        sp.putLong(KEY_LAST_LOW_TIME, System.currentTimeMillis());
        deleteLocalFile(new File(uploadFile), true);
    }

    public static boolean checkCanSend(Context context) {
        SharedPreferenceHelper sp = SharedPreferenceHelper.getInstance(context);
        long lastCrashTime = sp.getLong(KEY_LAST_LOW_TIME, 0L);
        return !isSameDay(lastCrashTime, System.currentTimeMillis());
    }

    private static boolean isSameDay(long t1, long t2) {
        Date d1 = new Date(t1);
        Date d2 = new Date(t2);
        SimpleDateFormat sdf = new SimpleDateFormat(DATE_PATTERN_STR);
        String s1 = sdf.format(d1);
        String s2 = sdf.format(d2);
        return s1.equals(s2);
    }

    private static void deleteLocalFile(File dstZipFile, boolean encryptResult) {
        if (encryptResult) {
            try {
                dstZipFile.delete();
                System.gc();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    private static String generateRemoteUrl(long timeStamp, String objectKeyDir) {
        String objectKey = objectKeyDir.substring(objectKeyDir.indexOf(InternalZipConstants.ZIP_FILE_SEPARATOR) + 1) + InternalZipConstants.ZIP_FILE_SEPARATOR + timeStamp + "_en.zip";
        return BUCKET_AND_ENDPOINT + objectKey;
    }

    @NonNull
    private static String generateFilePath(long timeStamp, String objectKeyDir) {
        String dstFileDirPath = "/sdcard/Log/upload-zip/" + objectKeyDir;
        File dstFileDir = new File(dstFileDirPath);
        if (!dstFileDir.exists()) {
            dstFileDir.mkdirs();
        }
        return dstFileDirPath + InternalZipConstants.ZIP_FILE_SEPARATOR + timeStamp + ".zip";
    }

    private static String[] makeUploadInfoAndTargetFile(String filePath) {
        long timeStamp = System.currentTimeMillis();
        String objectKeyDir = BUCKET_NAME + "/log/" + BuildInfoUtils.getSystemVersion() + InternalZipConstants.ZIP_FILE_SEPARATOR + DateUtils.formatDate7(timeStamp) + InternalZipConstants.ZIP_FILE_SEPARATOR + com.xiaopeng.lib.utils.SystemPropertyUtil.getHardwareId();
        String address = generateRemoteUrl(timeStamp, objectKeyDir);
        String dstFilePath = generateFilePath(timeStamp, objectKeyDir);
        File dstZipFile = null;
        try {
            List<String> filePaths = new ArrayList<>();
            filePaths.add(filePath);
            dstZipFile = com.xiaopeng.lib.utils.ZipUtils.zipMultiFiles(dstFilePath, filePaths);
        } catch (Exception e) {
            e.printStackTrace();
        }
        File encryptFile = new File(dstFilePath.replace(".zip", "_en.zip"));
        boolean encryptResult = AESUtils.encrypt(dstZipFile, encryptFile, "@!chxpzi#0109$+/");
        deleteLocalFile(dstZipFile, encryptResult);
        return new String[]{address, dstFilePath};
    }

    public static void uploadFile2Oss(Application application, String filePath, final FileUploadOssCallback callback) {
        IRemoteStorage storage = (IRemoteStorage) Module.get(NetworkChannelsEntry.class).get(IRemoteStorage.class);
        long timeStamp = System.currentTimeMillis();
        String objectKey = Constant.XMART_CDU_SERVICE_LOG_PREFIX + BuildInfoUtils.getSystemVersion() + File.separatorChar + DateUtils.formatDate7(timeStamp) + File.separatorChar + com.xiaopeng.lib.utils.SystemPropertyUtil.getVehicleId() + File.separatorChar + FileUtil.getFileName(filePath);
        Map<String, String> callbackParam = new HashMap<>();
        callbackParam.put(Constant.KEY_CALLBACK_URL, Constant.REMOTE_FEEKBACK_CALLBACK_URL);
        Map<String, String> param = new HashMap<>();
        param.put("app_id", "xmart:appid:002");
        param.put(Constant.KEY_DEVICE, com.xiaopeng.lib.utils.SystemPropertyUtil.getHardwareId());
        param.put("timestamp", String.valueOf(timeStamp));
        param.put(Constant.KEY_SID, com.xiaopeng.lib.utils.SystemPropertyUtil.getSoftwareId());
        param.put("type", String.valueOf(1));
        param.put(Constant.KEY_ADDRESS, Constant.SECURITY_BUCKET_ENDPOINT + objectKey);
        param.put(Constant.KEY_TIMER, String.valueOf(timeStamp));
        param.put(Constant.KEY_VMODEL, Car.getXpCduType());
        String sign = IndivHelper.sign(application.getApplicationContext(), param, timeStamp);
        param.put("sign", sign);
        LogUtils.d(TAG, new Gson().toJson(param));
        callbackParam.put(Constant.KEY_CALLBACK_BODY, new Gson().toJson(param));
        callbackParam.put(Constant.KEY_CALLBACK_BODY_TYPE, "application/json");
        try {
            storage.initWithContext(application);
            storage.needCertified(true);
            storage.uploadWithPathAndCallback(SECURITY_BUCKET_NAME, objectKey, filePath, new Callback() { // from class: com.xiaopeng.commonfunc.utils.FileUploadHelper.1
                @Override // com.xiaopeng.lib.framework.moduleinterface.netchannelmodule.remotestorage.Callback
                public void onStart(String s, String s1) {
                    FileUploadOssCallback fileUploadOssCallback = FileUploadOssCallback.this;
                    if (fileUploadOssCallback != null) {
                        fileUploadOssCallback.onStart(s, s1);
                    }
                }

                @Override // com.xiaopeng.lib.framework.moduleinterface.netchannelmodule.remotestorage.Callback
                public void onSuccess(String s, String s1) {
                    FileUploadOssCallback fileUploadOssCallback = FileUploadOssCallback.this;
                    if (fileUploadOssCallback != null) {
                        fileUploadOssCallback.onSuccess(s, s1);
                    }
                }

                @Override // com.xiaopeng.lib.framework.moduleinterface.netchannelmodule.remotestorage.Callback
                public void onFailure(String s, String s1, StorageException e) {
                    FileUploadOssCallback fileUploadOssCallback = FileUploadOssCallback.this;
                    if (fileUploadOssCallback != null) {
                        fileUploadOssCallback.onFailure(s, s1, e);
                    }
                }
            }, callbackParam);
        } catch (Exception e) {
            if (callback != null) {
                callback.onFailure(null, null, e);
            }
        }
    }
}
