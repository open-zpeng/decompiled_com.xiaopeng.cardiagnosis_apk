package com.xiaopeng.commonfunc.model.factorytest.hardwaretest;

import android.content.Context;
import com.xiaopeng.commonfunc.utils.FileUtil;
import com.xiaopeng.xmlconfig.Support;
import java.io.File;
/* loaded from: classes4.dex */
public class USBModel {
    private static final String TAG = "USBModel";
    public static final int TBOX_PRODUCT_ID = 1077;
    public static final int TBOX_VENDOR_ID = 11388;
    private static final String FACE_USB_PATH = Support.Case.getPath(Support.Case.FACE_USB);
    private static final String DVR_USB_PATH = Support.Case.getPath(Support.Case.DVR_USB);
    private static final String DCM_USB_PATH = Support.Case.getPath(Support.Case.DCM_USB);
    private static final String MEDIA_USB_PATH = Support.Case.getPath(Support.Case.MEDIA_USB);
    private boolean mIsFaceHasCheck = false;
    private boolean mIsDvrHasCheck = false;
    private boolean mIsDcmHasCheck = false;
    private boolean mIsMediaHasCheck = false;

    private boolean isUSBInsert(String path) {
        File file = new File(path);
        return file.exists() && file.isDirectory();
    }

    public boolean isFaceUSBInsert() {
        boolean flag = isUSBInsert(FACE_USB_PATH);
        if (flag) {
            this.mIsFaceHasCheck = true;
        }
        return flag;
    }

    public boolean isDvrUSBInsert() {
        boolean flag = isUSBInsert(DVR_USB_PATH);
        if (flag) {
            this.mIsDvrHasCheck = true;
        }
        return flag;
    }

    public boolean isDcmUSBInsert() {
        boolean flag = isUSBInsert(DCM_USB_PATH);
        if (flag) {
            this.mIsDcmHasCheck = true;
        }
        return flag;
    }

    public boolean isMediaUSBInsert() {
        boolean flag = isUSBInsert(MEDIA_USB_PATH);
        if (flag) {
            this.mIsMediaHasCheck = true;
        }
        return flag;
    }

    public boolean isMediaUSBInsertForRW() {
        return isUSBInsert(MEDIA_USB_PATH);
    }

    public boolean isFaceHasCheck() {
        return this.mIsFaceHasCheck;
    }

    public boolean isDvrHasCheck() {
        return this.mIsDvrHasCheck;
    }

    public boolean isDcmHasCheck() {
        return this.mIsDcmHasCheck;
    }

    public boolean isMediaHasCheck() {
        return this.mIsMediaHasCheck;
    }

    public boolean isTboxAttached(Context context) {
        return FileUtil.isUsbDeviceAttached(context, TBOX_VENDOR_ID, TBOX_PRODUCT_ID);
    }
}
