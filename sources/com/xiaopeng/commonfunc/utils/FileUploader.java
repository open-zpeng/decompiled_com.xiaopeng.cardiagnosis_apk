package com.xiaopeng.commonfunc.utils;

import android.app.Application;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.text.TextUtils;
import com.xiaopeng.commonfunc.Constant;
import com.xiaopeng.commonfunc.R;
import com.xiaopeng.commonfunc.bean.event.CommonEvent;
import com.xiaopeng.commonfunc.callback.FileUploadCallback;
import com.xiaopeng.commonfunc.callback.FileUploadOssCallback;
import com.xiaopeng.lib.utils.LogUtils;
import com.xiaopeng.lib.utils.ThreadUtils;
import com.xiaopeng.lib.utils.crypt.AESUtils;
import com.xiaopeng.xui.app.XLoadingDialog;
import java.io.File;
import org.greenrobot.eventbus.EventBus;
/* loaded from: classes4.dex */
public class FileUploader {
    private static final String DEFAULT_PASSWORD = "XIAOPENG";
    private static final int DUMMY = 0;
    public static final int ENCRYPT_FILE_WITH_XP_KEY = 2;
    public static final int ENCRYPT_FILE_WITH_ZIP_KEY = 1;
    private static final int STEP_WAITING_UDISK_INSERT = 1000;
    private static final String TAG = "FileUploader";
    public static final int UPLOAD_TO_CLOUD = 1;
    public static final int UPLOAD_TO_UDISK = 2;
    private Context mContext;
    private int mDirection;
    private int mEncryptMethod;
    private FileUploadCallback mFileUploadCallback;
    private String mUploadFileName;
    private String[] mUploadPath;
    private XLoadingDialog mXLoadingDialog;
    private int mStep = 0;
    private final BroadcastReceiver mBroadcastReceiver = new BroadcastReceiver() { // from class: com.xiaopeng.commonfunc.utils.FileUploader.1
        @Override // android.content.BroadcastReceiver
        public void onReceive(Context mContext, Intent intent) {
            String action = intent.getAction();
            LogUtils.i(FileUploader.TAG, "onReceive action-->" + action);
            if (action.equals("android.intent.action.MEDIA_MOUNTED")) {
                LogUtils.i(FileUploader.TAG, "onReceive mStep [%d]", Integer.valueOf(FileUploader.this.mStep));
                if (FileUploader.this.mStep == 1000) {
                    FileUploader.this.mStep = 0;
                    FileUploader fileUploader = FileUploader.this;
                    fileUploader.copyData2Udisk(fileUploader.mUploadPath, FileUploader.this.mUploadFileName, FileUploader.this.mEncryptMethod);
                }
            }
        }
    };

    public FileUploader(Context context, FileUploadCallback callback) {
        this.mContext = context;
        this.mFileUploadCallback = callback;
        init();
    }

    public void setDirection(int direction) {
        this.mDirection = direction;
    }

    private void init() {
        ThreadUtils.runOnMainThread(new Runnable() { // from class: com.xiaopeng.commonfunc.utils.-$$Lambda$FileUploader$3fCR8zcQW6ht9amfYntbhs2qKzI
            @Override // java.lang.Runnable
            public final void run() {
                FileUploader.this.lambda$init$1$FileUploader();
            }
        });
        IntentFilter filter = new IntentFilter();
        filter.addAction("android.intent.action.MEDIA_MOUNTED");
        filter.addDataScheme("file");
        this.mContext.registerReceiver(this.mBroadcastReceiver, filter);
    }

    public /* synthetic */ void lambda$init$1$FileUploader() {
        this.mXLoadingDialog = new XLoadingDialog(this.mContext, R.style.XAppTheme_XDialog_Loading);
        this.mXLoadingDialog.create();
        this.mXLoadingDialog.setCancelable(true);
        this.mXLoadingDialog.setOnCancelListener(new DialogInterface.OnCancelListener() { // from class: com.xiaopeng.commonfunc.utils.-$$Lambda$FileUploader$4UWxqg5BTXMV8BQGDVtGcIu1GRs
            @Override // android.content.DialogInterface.OnCancelListener
            public final void onCancel(DialogInterface dialogInterface) {
                FileUploader.this.lambda$init$0$FileUploader(dialogInterface);
            }
        });
        this.mXLoadingDialog.setOnTimeOutListener(null);
        this.mXLoadingDialog.setTimeOutCheck(false);
        this.mXLoadingDialog.setTimeOut(0);
        this.mXLoadingDialog.getWindow().setType(2047);
    }

    public /* synthetic */ void lambda$init$0$FileUploader(DialogInterface dialog) {
        this.mStep = 0;
        FileUploadCallback fileUploadCallback = this.mFileUploadCallback;
        if (fileUploadCallback != null) {
            fileUploadCallback.onFailure();
        }
    }

    public void copyData2Udisk(final String[] filePaths, final String uploadFileName, final int encrypthMethod) {
        int i = this.mDirection;
        if (i == 2) {
            LogUtils.i(TAG, "copy diagnosis files to udisk");
            final String udiskPath = FileUtil.getUDiskPath(this.mContext);
            ThreadUtils.runOnMainThread(new Runnable() { // from class: com.xiaopeng.commonfunc.utils.-$$Lambda$FileUploader$PkZ6t0H_S5vfQaVsBac7Nlvysb8
                @Override // java.lang.Runnable
                public final void run() {
                    FileUploader.this.lambda$copyData2Udisk$3$FileUploader(udiskPath, uploadFileName, filePaths, encrypthMethod);
                }
            });
            return;
        }
        LogUtils.e(TAG, "uploadFile2Cloud wrong direction [%d]", Integer.valueOf(i));
    }

    public /* synthetic */ void lambda$copyData2Udisk$3$FileUploader(final String udiskPath, final String uploadFileName, final String[] filePaths, final int encrypthMethod) {
        EventBus.getDefault().post(new CommonEvent(10003, null));
        if (TextUtils.isEmpty(udiskPath)) {
            this.mXLoadingDialog.setMessage(this.mContext.getString(R.string.tips_insert_udisk));
            this.mXLoadingDialog.show();
            this.mUploadFileName = uploadFileName;
            this.mUploadPath = filePaths;
            this.mEncryptMethod = encrypthMethod;
            this.mStep = 1000;
            return;
        }
        this.mXLoadingDialog.setMessage(this.mContext.getString(R.string.tips_copying_data_2_udisk));
        this.mXLoadingDialog.show();
        ThreadUtils.execute(new Runnable() { // from class: com.xiaopeng.commonfunc.utils.-$$Lambda$FileUploader$KMAkuny3lng3Mmd4Pi3d_PYaMx4
            @Override // java.lang.Runnable
            public final void run() {
                FileUploader.this.lambda$copyData2Udisk$2$FileUploader(udiskPath, filePaths, uploadFileName, encrypthMethod);
            }
        });
    }

    public /* synthetic */ void lambda$copyData2Udisk$2$FileUploader(String udiskPath, String[] filePaths, String uploadFileName, int encrypthMethod) {
        EncryptZipFile encryptZipFile = zipNEncryptFiles(udiskPath, filePaths, uploadFileName, encrypthMethod);
        finalizeDialog();
        if (this.mFileUploadCallback != null) {
            if (!TextUtils.isEmpty(encryptZipFile.filePath)) {
                this.mFileUploadCallback.onSuccess(FileUtil.getFileName(encryptZipFile.filePath), encryptZipFile.password);
            } else {
                this.mFileUploadCallback.onFailure();
            }
        }
    }

    private EncryptZipFile zipNEncryptFiles(String path, String[] paths, String uploadFileName, int encrypthMethod) {
        String vin;
        EncryptZipFile res = new EncryptZipFile();
        if (encrypthMethod == 1) {
            if (this.mDirection == 2) {
                String vin2 = SystemPropertyUtil.getVIN();
                vin = vin2.length() > 8 ? vin2.substring(vin2.length() - 8) : DEFAULT_PASSWORD;
            } else {
                vin = DataHelp.getRandomString(8);
            }
            String zipFile = path + File.separator + uploadFileName + Constant.DATA_SEPARATOR_STRING + TimeUtil.getDate() + ".zip";
            if (ZipUtil.zipFiles(paths, zipFile, vin)) {
                res.filePath = zipFile;
                res.password = vin;
            }
        } else if (encrypthMethod == 2) {
            String fileName = uploadFileName + Constant.DATA_SEPARATOR_STRING + TimeUtil.getDate();
            String tempFile = Constant.TEMP_CACHE + File.separator + fileName + ".zip";
            String destFile = path + File.separator + fileName + ".xp";
            if (FileUtil.mkDir(Constant.TEMP_CACHE + File.separator) && ZipUtil.zipFiles(paths, tempFile, null) && AESUtils.encryptFile(Constant.FILE_ENCRYPT_CODE, tempFile, destFile)) {
                res.filePath = destFile;
                res.password = null;
            }
            FileUtil.deleteFile(tempFile);
        }
        return res;
    }

    public void uploadFile2Cloud(final Application app, final String folder, final String[] paths, final String uploadFileName, final int encrypthMethod) {
        int i = this.mDirection;
        if (i == 1) {
            ThreadUtils.execute(new Runnable() { // from class: com.xiaopeng.commonfunc.utils.-$$Lambda$FileUploader$CODPXD0rN4CVzQRU0bzO106nKc4
                @Override // java.lang.Runnable
                public final void run() {
                    FileUploader.this.lambda$uploadFile2Cloud$4$FileUploader(folder, paths, uploadFileName, encrypthMethod, app);
                }
            });
        } else {
            LogUtils.e(TAG, "uploadFile2Cloud wrong direction [%d]", Integer.valueOf(i));
        }
    }

    public /* synthetic */ void lambda$uploadFile2Cloud$4$FileUploader(String folder, String[] paths, String uploadFileName, int encrypthMethod, Application app) {
        final EncryptZipFile encryptZipFile = zipNEncryptFiles(folder, paths, uploadFileName, encrypthMethod);
        FileUploadHelper.uploadFile2Oss(app, encryptZipFile.filePath, new FileUploadOssCallback() { // from class: com.xiaopeng.commonfunc.utils.FileUploader.2
            @Override // com.xiaopeng.commonfunc.callback.FileUploadOssCallback
            public void onStart(String s, String s1) {
                LogUtils.d(FileUploader.TAG, "uploadFile2Oss onStart s:" + s + ", s1:" + s1);
            }

            @Override // com.xiaopeng.commonfunc.callback.FileUploadOssCallback
            public void onSuccess(String s, String s1) {
                LogUtils.i(FileUploader.TAG, "uploadFile2Oss onSuccess s:" + s + ", s1:" + s1);
                if (FileUploader.this.mFileUploadCallback != null) {
                    String res = s.replaceFirst(Constant.SECURITY_BUCKET_ENDPOINT, "");
                    if (res.equalsIgnoreCase(s)) {
                        res = s.replaceFirst(Constant.XP_SECURITY_BUCKET_ENDPOINT, "");
                    }
                    FileUploader.this.mFileUploadCallback.onSuccess(res, encryptZipFile.password);
                }
                FileUtil.deleteFile(encryptZipFile.filePath);
            }

            @Override // com.xiaopeng.commonfunc.callback.FileUploadOssCallback
            public void onFailure(String s, String s1, Exception e) {
                LogUtils.e(FileUploader.TAG, "uploadFile2Oss onFailure s:" + s + ", s1:" + s1 + ", e:" + e.toString());
                if (FileUploader.this.mFileUploadCallback != null) {
                    FileUploader.this.mFileUploadCallback.onFailure();
                }
                FileUtil.deleteFile(encryptZipFile.filePath);
            }
        });
    }

    public void destroy() {
        this.mContext.unregisterReceiver(this.mBroadcastReceiver);
        finalizeDialog();
        this.mXLoadingDialog = null;
    }

    private void finalizeDialog() {
        XLoadingDialog xLoadingDialog = this.mXLoadingDialog;
        if (xLoadingDialog != null && xLoadingDialog.isShowing()) {
            this.mXLoadingDialog.dismiss();
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* loaded from: classes4.dex */
    public class EncryptZipFile {
        public String filePath;
        public String password;

        private EncryptZipFile() {
        }
    }
}
