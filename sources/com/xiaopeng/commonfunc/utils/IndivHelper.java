package com.xiaopeng.commonfunc.utils;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Handler;
import android.os.HandlerThread;
import android.os.Looper;
import android.os.Message;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import com.xiaopeng.commonfunc.utils.internal.IIndivInternal;
import com.xiaopeng.commonfunc.utils.internal.IndivInternalFactory;
import com.xiaopeng.lib.framework.moduleinterface.netchannelmodule.http.Callback;
import com.xiaopeng.lib.framework.moduleinterface.netchannelmodule.http.IRequest;
import com.xiaopeng.lib.framework.moduleinterface.netchannelmodule.http.IResponse;
import com.xiaopeng.lib.http.ICallback;
import com.xiaopeng.lib.http.server.ServerBean;
import com.xiaopeng.lib.security.ISecurityModule;
import com.xiaopeng.lib.security.SecurityModuleFactory;
import com.xiaopeng.lib.utils.LogUtils;
import com.xiaopeng.lib.utils.NetUtils;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Set;
import org.json.JSONObject;
/* loaded from: classes4.dex */
public final class IndivHelper {
    public static final String ACTION_BROADCAST_ISF_SECURE_STORE_RELOAD = "com.xiaopeng.action.ISF_SECURE_STORE_RELOAD";
    public static final String ACTION_BROADCAST_SECURE_STORE_RELOAD = "com.xiaopeng.action.SECURE_STORE_RELOAD";
    public static final String CAR_APP_SEC = "B638C588DCAD7C1A43E6FB";
    private static final int EVENT_INIT_SECURE_SDK = 1;
    private static final String TAG = "IndivHelper";
    private static Context sContext;
    private static boolean sIsInitSuccess;
    private static boolean sIsInitializing;
    private static ThreadHandler sThreadHandler;
    private static final ISecurityModule sSecurityUtils = SecurityModuleFactory.getSecurityModule();
    private static final IIndivInternal sIndivInternal = IndivInternalFactory.getIndivHelperImpl();

    public static synchronized void initMain(Context context) {
        synchronized (IndivHelper.class) {
            if (sIsInitSuccess) {
                LogUtils.d(TAG, "irdeto sdk has init");
                return;
            }
            sContext = context.getApplicationContext();
            try {
                initThread();
                sSecurityUtils.initForIndividual(sContext);
                sIsInitSuccess = true;
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    private static void registerBroadcast(Context context) {
        IntentFilter filter = new IntentFilter();
        filter.addAction("android.net.conn.CONNECTIVITY_CHANGE");
        context.registerReceiver(new BroadcastReceiver() { // from class: com.xiaopeng.commonfunc.utils.IndivHelper.1
            @Override // android.content.BroadcastReceiver
            public void onReceive(Context context2, Intent intent) {
                if (NetUtils.isNetworkAvailable(context2)) {
                    IndivHelper.asyncInitIndivService();
                }
            }
        }, filter);
    }

    private static void initThread() {
        if (sThreadHandler == null) {
            HandlerThread thread = new HandlerThread("SecureSdkSampleHandler");
            thread.start();
            sThreadHandler = new ThreadHandler(thread.getLooper());
        }
    }

    public static synchronized void asyncInitIndivService() {
        synchronized (IndivHelper.class) {
            if (sThreadHandler == null) {
                LogUtils.d(TAG, "sThreadHandler is null");
                return;
            }
            sThreadHandler.removeMessages(1);
            sThreadHandler.sendEmptyMessageDelayed(1, 1000L);
        }
    }

    @Deprecated
    public static boolean isIrdetoInitSuccess() {
        return sSecurityUtils.isInitAndIndivSuccess();
    }

    public static boolean isInitSuccess() {
        return sSecurityUtils.isInitAndIndivSuccess();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static void processInitService() {
        if (!sIsInitSuccess) {
            LogUtils.d(TAG, "irdeto sdk not init");
            return;
        }
        try {
            if (sSecurityUtils.isInitAndIndivSuccess()) {
                sSecurityUtils.saveMCUSecurityKey(false);
            } else {
                postIndivService();
                sThreadHandler.removeMessages(1);
                sThreadHandler.sendEmptyMessageDelayed(1, 60000L);
            }
        } catch (Exception e) {
            e.printStackTrace();
            sIsInitializing = false;
        }
    }

    @Deprecated
    public static void clearIrdetoBuildFiles() {
        sSecurityUtils.clearIndividualData();
    }

    public static void clearLocalIndividualData() {
        sSecurityUtils.clearIndividualData();
    }

    public static String getLocalFlag() {
        return sSecurityUtils.getIndividualFlag();
    }

    private static void postIndivService() throws Exception {
        if (sIsInitializing) {
            LogUtils.e(TAG, "irdeto sdk indiv is initing");
            return;
        }
        sIsInitializing = true;
        IRequest request = sIndivInternal.buildIndivRequest(sContext, sSecurityUtils);
        if (request == null) {
            sIsInitializing = false;
        } else {
            request.execute(new Callback() { // from class: com.xiaopeng.commonfunc.utils.IndivHelper.2
                @Override // com.xiaopeng.lib.framework.moduleinterface.netchannelmodule.http.Callback
                public void onSuccess(IResponse response) {
                    String data = IndivHelper.sIndivInternal.getIndivDataFromResponse(response);
                    if (!TextUtils.isEmpty(data)) {
                        try {
                            if (IndivHelper.sSecurityUtils.individualWithData(data)) {
                                LogUtils.d(IndivHelper.TAG, "个性化数据处理完成！");
                                IndivHelper.postSaveIndiv();
                            }
                        } catch (Exception ex) {
                            ex.printStackTrace();
                        }
                    }
                    boolean unused = IndivHelper.sIsInitializing = false;
                }

                @Override // com.xiaopeng.lib.framework.moduleinterface.netchannelmodule.http.Callback
                public void onFailure(IResponse response) {
                    boolean unused = IndivHelper.sIsInitializing = false;
                    LogUtils.d(IndivHelper.TAG, "Get individual info failed:" + response.getException());
                }
            });
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static void postSaveIndiv() {
        try {
            IRequest request = sIndivInternal.buildIndivSavingRequest(sContext, sSecurityUtils);
            if (request == null) {
                LogUtils.d(TAG, "No need to post individual saving request.");
                postIndividualProcessing();
                return;
            }
            request.execute(new Callback() { // from class: com.xiaopeng.commonfunc.utils.IndivHelper.3
                @Override // com.xiaopeng.lib.framework.moduleinterface.netchannelmodule.http.Callback
                public void onSuccess(IResponse response) {
                    if (IndivHelper.sIndivInternal.checkIndivSavingResult(response)) {
                        IndivHelper.sThreadHandler.post(new Runnable() { // from class: com.xiaopeng.commonfunc.utils.IndivHelper.3.1
                            @Override // java.lang.Runnable
                            public void run() {
                                try {
                                    synchronized (IndivHelper.class) {
                                        IndivHelper.postIndividualProcessing();
                                    }
                                } catch (Throwable e) {
                                    e.printStackTrace();
                                }
                            }
                        });
                    }
                }

                @Override // com.xiaopeng.lib.framework.moduleinterface.netchannelmodule.http.Callback
                public void onFailure(IResponse response) {
                    LogUtils.d(IndivHelper.TAG, "Failed save individual info to server:" + response.getException());
                }
            });
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static void postIndividualProcessing() {
        sSecurityUtils.saveIndividualFlag();
        sSecurityUtils.saveMCUSecurityKey(true);
        notifyIndividualFinished();
    }

    private static void notifyIndividualFinished() {
        LogUtils.d(TAG, "notifyIndivFinished");
        sIndivInternal.notifyIndivFinished(sContext);
    }

    public static synchronized void destroy() {
        synchronized (IndivHelper.class) {
            sSecurityUtils.destroy();
            if (sThreadHandler != null) {
                sThreadHandler.getLooper().quit();
                sThreadHandler = null;
            }
            sIsInitializing = false;
        }
    }

    public static String encode(String str) {
        return sSecurityUtils.encode(str);
    }

    public static synchronized String decode(String str) {
        String decode;
        synchronized (IndivHelper.class) {
            decode = sSecurityUtils.decode(str);
        }
        return decode;
    }

    public static String getString(String key) {
        return sSecurityUtils.getString(key);
    }

    public static void setString(String key, String value) {
        sSecurityUtils.setString(key, value);
    }

    public static void deleteString(String key) {
        sSecurityUtils.deleteString(key);
    }

    public static void asyncGetMCUSecurityKey(ICallback<byte[], String> callback) {
        sSecurityUtils.asyncGetMCUSecurityKey(callback);
    }

    public static String cryptoDecode(String key, String Iv, byte[] data) {
        return sSecurityUtils.cryptoDecode(key, Iv, data);
    }

    @Nullable
    private static ServerBean getServerBean(IResponse response) {
        try {
            JSONObject jsonObject = new JSONObject(response.body());
            ServerBean bean = new ServerBean();
            bean.setCode(jsonObject.getInt("code"));
            bean.setData(jsonObject.getString("data"));
            try {
                bean.setMsg(jsonObject.getString("msg"));
                return bean;
            } catch (Exception e) {
                return bean;
            }
        } catch (Exception ex) {
            LogUtils.d(TAG, "Failed to parser the response data. response:" + response.body(), ex);
            return null;
        }
    }

    public static String sign(Context context, Map<String, String> kV, long time) {
        String k = sortParameterAndValues(kV);
        return com.xiaopeng.lib.utils.MD5Utils.getMD5("xmart:appid:002" + time + k + "B638C588DCAD7C1A43E6FB").toLowerCase();
    }

    private static String sortParameterAndValues(Map<String, String> parameterMap) {
        if (parameterMap == null) {
            return "";
        }
        Set<String> keySet = parameterMap.keySet();
        List<String> keyList = new ArrayList<>();
        for (String key : keySet) {
            keyList.add(key);
        }
        Collections.sort(keyList);
        StringBuffer sb = new StringBuffer();
        for (String key2 : keyList) {
            if (!"app_id".equals(key2) && !"timestamp".equals(key2) && !"sign".equals(key2)) {
                sb.append(key2);
                String valArr = parameterMap.get(key2);
                sb.append(valArr);
            }
        }
        return sb.toString();
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* loaded from: classes4.dex */
    public static class ThreadHandler extends Handler {
        public ThreadHandler(Looper looper) {
            super(looper);
        }

        @Override // android.os.Handler
        public void handleMessage(Message msg) {
            LogUtils.d(IndivHelper.TAG, "handleMessage, event:" + msg.what);
            if (msg.what == 1) {
                IndivHelper.processInitService();
            }
            super.handleMessage(msg);
        }
    }
}
