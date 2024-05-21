package com.xiaopeng.commonfunc.utils.internal;

import android.os.Build;
/* loaded from: classes4.dex */
public final class IndivInternalFactory {
    public static IIndivInternal getIndivHelperImpl() {
        if (Build.VERSION.SDK_INT == 19) {
            return null;
        }
        return XmartV1IndivHelper.getInstance();
    }
}
