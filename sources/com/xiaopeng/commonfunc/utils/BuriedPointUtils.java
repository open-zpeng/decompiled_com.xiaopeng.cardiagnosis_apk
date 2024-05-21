package com.xiaopeng.commonfunc.utils;

import android.util.Log;
import com.xiaopeng.commonfunc.Constant;
import com.xiaopeng.datalog.DataLogModuleEntry;
import com.xiaopeng.lib.framework.module.Module;
import com.xiaopeng.lib.framework.moduleinterface.datalogmodule.IDataLog;
import com.xiaopeng.lib.framework.moduleinterface.datalogmodule.IMoleEvent;
import com.xiaopeng.lib.framework.moduleinterface.datalogmodule.IMoleEventBuilder;
import com.xiaopeng.lib.utils.ThreadUtils;
import java.util.HashMap;
import java.util.Map;
import java.util.function.BiConsumer;
/* loaded from: classes4.dex */
public class BuriedPointUtils {
    public static final String COUNT_KEY = "count";
    public static final String MODULE = "cardiagnosis";
    public static final String SIZE_KEY = "size";
    public static final String STORAGE_CLEAN_BUTTONID = "B002";
    public static final String STORAGE_CLEAN_PAGEID = "P11113";
    private static final String TAG = "BuriedPointUtils";

    public static void sendPageStateDataLog(String pageID, String buttonID, long size, int value) {
        Log.d(TAG, "sendPageStateDataLog:" + pageID + Constant.SEPARATOR_STRING + buttonID + Constant.SEPARATOR_STRING + size + Constant.SEPARATOR_STRING + value);
        Map<String, Object> data = new HashMap<>();
        data.put(SIZE_KEY, Long.valueOf(size));
        data.put(COUNT_KEY, Integer.valueOf(value));
        sendDataLog(MODULE, pageID, buttonID, data);
    }

    public static void sendDataLog(final String module, final String pageID, final String buttonID, final Map<String, Object> data) {
        Log.d(TAG, "sendDataLog:" + pageID + Constant.SEPARATOR_STRING + buttonID);
        ThreadUtils.execute(new Runnable() { // from class: com.xiaopeng.commonfunc.utils.-$$Lambda$BuriedPointUtils$297da_jkwQCERyx2wvnp6TMf8kQ
            @Override // java.lang.Runnable
            public final void run() {
                BuriedPointUtils.lambda$sendDataLog$1(module, pageID, buttonID, data);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public static /* synthetic */ void lambda$sendDataLog$1(String module, final String pageID, final String buttonID, Map data) {
        IDataLog dataLog = (IDataLog) Module.get(DataLogModuleEntry.class).get(IDataLog.class);
        final IMoleEventBuilder iMoleEventBuilder = dataLog.buildMoleEvent().setModule(module).setPageId(pageID).setButtonId(buttonID);
        data.forEach(new BiConsumer() { // from class: com.xiaopeng.commonfunc.utils.-$$Lambda$BuriedPointUtils$LpbgJT1JUuCtuCFaDqjy6LZKO2c
            @Override // java.util.function.BiConsumer
            public final void accept(Object obj, Object obj2) {
                BuriedPointUtils.lambda$sendDataLog$0(pageID, buttonID, iMoleEventBuilder, (String) obj, obj2);
            }
        });
        IMoleEvent event = iMoleEventBuilder.build();
        if (event != null) {
            dataLog.sendStatData(event);
        }
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public static /* synthetic */ void lambda$sendDataLog$0(String pageID, String buttonID, IMoleEventBuilder iMoleEventBuilder, String key, Object value) {
        Log.d(TAG, "sendDataLog:" + pageID + Constant.SEPARATOR_STRING + buttonID + Constant.SEPARATOR_STRING + key + Constant.SEPARATOR_STRING + value);
        if (value instanceof String) {
            iMoleEventBuilder.setProperty(key, (String) value);
        } else if (value instanceof Number) {
            iMoleEventBuilder.setProperty(key, (Number) value);
        } else if (value instanceof Boolean) {
            iMoleEventBuilder.setProperty(key, ((Boolean) value).booleanValue());
        } else if (value instanceof Character) {
            iMoleEventBuilder.setProperty(key, ((Character) value).charValue());
        } else {
            Log.d(TAG, "sendDataLog: can not case type");
        }
    }
}
