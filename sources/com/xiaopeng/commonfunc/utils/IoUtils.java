package com.xiaopeng.commonfunc.utils;

import java.io.Closeable;
/* loaded from: classes4.dex */
public final class IoUtils {
    public static void closeQuietly(Closeable closeable) {
        if (closeable != null) {
            try {
                closeable.close();
            } catch (RuntimeException e) {
                throw e;
            } catch (Exception e2) {
            }
        }
    }
}
