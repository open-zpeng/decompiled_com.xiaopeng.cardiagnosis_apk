package com.xiaopeng.libconfig.ipc.cfc;

import android.os.SystemProperties;
import android.text.TextUtils;
/* loaded from: classes5.dex */
public class CFCHelper {
    private static final String SIGNAL_CFC_SPLIT = ",";
    private static final String SYS_PROPERTY_CFC = "persist.sys.xiaopeng.cfc_info";

    private static int getCFC(int type) {
        String[] cfcArray;
        String cfcInfo = SystemProperties.get(SYS_PROPERTY_CFC);
        if (TextUtils.isEmpty(cfcInfo) || (cfcArray = cfcInfo.split(",")) == null || type >= cfcArray.length) {
            return 0;
        }
        try {
            int cfc = Integer.parseInt(cfcArray[type]);
            return cfc;
        } catch (NumberFormatException e) {
            return 0;
        }
    }

    public static int getVehicleType() {
        return getCFC(0);
    }

    public static int getProductAddr() {
        return getCFC(1);
    }

    public static int getInterior() {
        return getCFC(2);
    }

    public static int getBodyColor() {
        int color = getCFC(3);
        if (color == 2 || color == 4 || color == 5 || color == 7 || color == 12 || color == 13 || color == 10 || color == 9 || color == 8) {
            return color;
        }
        return 1;
    }

    public static int getProductStage() {
        return getCFC(4);
    }

    public static int getConfigCode() {
        return getCFC(5);
    }

    public static int getSSBState() {
        return getCFC(13);
    }
}
