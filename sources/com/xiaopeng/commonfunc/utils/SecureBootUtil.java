package com.xiaopeng.commonfunc.utils;

import android.text.TextUtils;
import com.xiaopeng.xmlconfig.Support;
/* loaded from: classes4.dex */
public class SecureBootUtil {
    private static final String EFUSE_PATH = "sys/efuse/efuse_enable";
    public static final boolean SUPPORT_SECURE_BOOT = Support.Case.getEnabled(Support.Case.SECURE_BOOT);

    public static int getEfuseStatus() {
        String status = FileUtil.read(EFUSE_PATH);
        if (TextUtils.isEmpty(status)) {
            return -1;
        }
        int res = Integer.parseInt(status);
        return res;
    }
}
