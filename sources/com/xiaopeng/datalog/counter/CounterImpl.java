package com.xiaopeng.datalog.counter;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Handler;
import android.os.Message;
import android.preference.PreferenceManager;
import android.text.TextUtils;
import com.xiaopeng.commonfunc.Constant;
import com.xiaopeng.datalog.DataLogModuleEntry;
import com.xiaopeng.lib.framework.module.Module;
import com.xiaopeng.lib.framework.moduleinterface.datalogmodule.ICounter;
import com.xiaopeng.lib.framework.moduleinterface.datalogmodule.IDataLog;
import com.xiaopeng.lib.framework.moduleinterface.datalogmodule.IMoleEvent;
import com.xiaopeng.lib.framework.moduleinterface.datalogmodule.IMoleEventBuilder;
import com.xiaopeng.lib.utils.LogUtils;
import com.xiaopeng.lib.utils.ThreadUtils;
import com.xiaopeng.lib.utils.info.DeviceInfoUtils;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicLong;
/* loaded from: classes4.dex */
public class CounterImpl implements ICounter, Handler.Callback {
    private static final String KEY_TIME = "time";
    private static final int MESSAGE_APPLY = 1;
    private static final long MESSAGE_APPLY_DELAY = 10000;
    private static final String TAG = "CounterImpl";
    private final Map<String, AtomicInteger> mCountMap;
    private boolean mDebug;
    private final SharedPreferences.Editor mEditor;
    private Handler mHandler;
    private final long mInterval;
    private final String mName;
    private final SharedPreferences mPreferences;
    private AtomicLong mTime;

    /* JADX INFO: Access modifiers changed from: package-private */
    public CounterImpl(Context context, String name, long interval) {
        if (TextUtils.isEmpty(name)) {
            throw new IllegalArgumentException("name can't be empty!");
        }
        this.mName = name;
        this.mInterval = interval;
        this.mCountMap = new HashMap();
        this.mPreferences = PreferenceManager.getDefaultSharedPreferences(context);
        this.mEditor = this.mPreferences.edit();
        long time = this.mPreferences.getLong(generatePrefKey("time"), System.currentTimeMillis());
        this.mTime = new AtomicLong(time);
        this.mHandler = new Handler(ThreadUtils.getLooper(0), this);
        initValuesFromPref();
        report(false);
    }

    private void initValuesFromPref() {
        Map<String, ?> prefMap = this.mPreferences.getAll();
        LogUtils.d(TAG, "initValuesFromPref prefMap:" + prefMap);
        for (String k : prefMap.keySet()) {
            String key = generateKeyFromPrefKey(k);
            if (!TextUtils.isEmpty(key) && !key.equals("time")) {
                Object value = prefMap.get(k);
                if (value instanceof Integer) {
                    LogUtils.d(TAG, "initValuesFromPref key:" + key + " value:" + value);
                    AtomicInteger count = new AtomicInteger(((Integer) value).intValue());
                    this.mCountMap.put(key, count);
                }
            }
        }
    }

    @Override // android.os.Handler.Callback
    public boolean handleMessage(Message msg) {
        if (msg.what == 1) {
            if (this.mDebug) {
                LogUtils.d(TAG, "mEditor.apply()");
            }
            this.mEditor.apply();
        }
        return true;
    }

    @Override // com.xiaopeng.lib.framework.moduleinterface.datalogmodule.ICounter
    public void debug(boolean debug) {
        this.mDebug = debug;
    }

    @Override // com.xiaopeng.lib.framework.moduleinterface.datalogmodule.ICounter
    public synchronized int count(String key) {
        return count(key, 1);
    }

    @Override // com.xiaopeng.lib.framework.moduleinterface.datalogmodule.ICounter
    public synchronized int count(String key, int inc) {
        int result;
        AtomicInteger count = this.mCountMap.get(key);
        String prefKey = generatePrefKey(key);
        if (count == null) {
            int value = 0;
            try {
                value = this.mPreferences.getInt(prefKey, 0);
            } catch (ClassCastException e) {
                LogUtils.w(TAG, "mPreferences.getInt(" + prefKey + ") error!", e);
            }
            if (this.mDebug) {
                LogUtils.v(TAG, "count " + this.mName + ", load key:" + prefKey + " from pref, value is " + value);
            }
            count = new AtomicInteger(value);
            this.mCountMap.put(key, count);
        }
        int value2 = count.get();
        result = value2 + inc;
        count.set(result);
        this.mEditor.putInt(prefKey, result);
        if (this.mDebug) {
            LogUtils.v(TAG, "count " + this.mName + Constant.SPACE_STRING + key + Constant.COLON_STRING + result + ", cache is:" + this.mCountMap);
        }
        if (!this.mHandler.hasMessages(1)) {
            this.mHandler.sendEmptyMessageDelayed(1, MESSAGE_APPLY_DELAY);
        }
        report(result == Integer.MAX_VALUE);
        return result;
    }

    private String generatePrefKey(String key) {
        return this.mName + Constant.DATA_SEPARATOR_STRING + key;
    }

    private String generateKeyFromPrefKey(String prefKey) {
        if (TextUtils.isEmpty(prefKey)) {
            return null;
        }
        String prefix = this.mName + Constant.DATA_SEPARATOR_STRING;
        if (prefKey.startsWith(prefix)) {
            return prefKey.substring(prefix.length());
        }
        return null;
    }

    private void report(boolean force) {
        long now = System.currentTimeMillis();
        final long time = this.mTime.get();
        long j = this.mInterval;
        long currentInterval = now / j;
        long lastInterval = time / j;
        if (lastInterval < currentInterval || force) {
            final Map<String, Integer> reportMap = new HashMap<>();
            for (String key : this.mCountMap.keySet()) {
                AtomicInteger count = this.mCountMap.get(key);
                reportMap.put(key, Integer.valueOf(count.get()));
                count.set(0);
                String prefKey = generatePrefKey(key);
                this.mEditor.putInt(prefKey, 0);
            }
            ThreadUtils.post(2, new Runnable() { // from class: com.xiaopeng.datalog.counter.CounterImpl.1
                @Override // java.lang.Runnable
                public void run() {
                    LogUtils.i(CounterImpl.TAG, Constant.STRING_LEFT_BRACKETS + CounterImpl.this.mName + " counter] send count event, name:" + CounterImpl.this.mName + " values:" + reportMap);
                    IDataLog dataLogService = (IDataLog) Module.get(DataLogModuleEntry.class).get(IDataLog.class);
                    IMoleEventBuilder event = dataLogService.buildMoleEvent().setPageId("P00012").setButtonId("B001").setEvent(CounterImpl.this.mName);
                    for (String key2 : reportMap.keySet()) {
                        event.setProperty(key2, (Number) reportMap.get(key2));
                    }
                    event.setProperty("time", Long.valueOf(time));
                    IMoleEvent event1 = event.build();
                    LogUtils.d(CounterImpl.TAG, "start sendStatData:" + event1.toJson());
                    boolean international = DeviceInfoUtils.isInternationalVer();
                    if (!international) {
                        dataLogService.sendStatData(event1);
                    }
                }
            });
            if (!this.mHandler.hasMessages(1)) {
                this.mHandler.sendEmptyMessageDelayed(1, MESSAGE_APPLY_DELAY);
            }
        }
        this.mTime.set(now);
        this.mEditor.putLong(generatePrefKey("time"), now);
    }
}
