package com.xiaopeng.commonfunc.utils;
/* loaded from: classes4.dex */
public class NumberUtil {
    public static int getNumber(char c) {
        int value = -1;
        int i = Integer.valueOf(c).intValue();
        if (i >= 48 && i <= 57) {
            value = i - 48;
        }
        if (i >= 97) {
            return i - 87;
        }
        return value;
    }
}
