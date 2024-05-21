package com.xiaopeng.commonfunc.utils;

import android.text.TextUtils;
import com.xiaopeng.commonfunc.system.runnable.Sleep;
import com.xiaopeng.lib.utils.LogUtils;
import java.io.File;
import java.io.IOException;
import net.lingala.zip4j.ZipFile;
import net.lingala.zip4j.model.ZipParameters;
import net.lingala.zip4j.model.enums.AesKeyStrength;
import net.lingala.zip4j.model.enums.CompressionLevel;
import net.lingala.zip4j.model.enums.CompressionMethod;
import net.lingala.zip4j.model.enums.EncryptionMethod;
/* loaded from: classes4.dex */
public class ZipUtil {
    private static final String TAG = "ZipUtil";

    public static boolean zipFiles(String[] srcPaths, String destPath, String password) {
        ZipFile zipFile;
        boolean res = true;
        if (!TextUtils.isEmpty(password)) {
            zipFile = new ZipFile(destPath, password.toCharArray());
        } else {
            zipFile = new ZipFile(destPath);
        }
        try {
            try {
                try {
                    ZipParameters parameters = new ZipParameters();
                    parameters.setCompressionMethod(CompressionMethod.DEFLATE);
                    parameters.setCompressionLevel(CompressionLevel.NORMAL);
                    if (!TextUtils.isEmpty(password)) {
                        parameters.setEncryptFiles(true);
                        parameters.setEncryptionMethod(EncryptionMethod.AES);
                        parameters.setAesKeyStrength(AesKeyStrength.KEY_STRENGTH_256);
                    }
                    for (String srcPath : srcPaths) {
                        File file = new File(srcPath);
                        boolean isFileExists = file.exists();
                        LogUtils.i(TAG, "srcPath[%s] isFileExists[%s]", srcPath, Boolean.valueOf(isFileExists));
                        if (isFileExists) {
                            if (file.isDirectory()) {
                                zipFile.addFolder(file, parameters);
                            } else {
                                zipFile.addFile(file, parameters);
                            }
                        }
                    }
                    Sleep.sleep(5000L);
                    zipFile.close();
                } catch (Exception e) {
                    e.printStackTrace();
                    res = false;
                    Sleep.sleep(5000L);
                    zipFile.close();
                }
            } catch (Throwable e2) {
                try {
                    Sleep.sleep(5000L);
                    zipFile.close();
                } catch (IOException e3) {
                    e3.printStackTrace();
                }
                throw e2;
            }
        } catch (IOException e4) {
            e4.printStackTrace();
        }
        LogUtils.i(TAG, "zipFiles res[%s] ", Boolean.valueOf(res));
        return res;
    }
}
