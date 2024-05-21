package com.xiaopeng.cardiagnosis;

import android.app.Application;
import android.content.Context;
import android.content.Intent;
import com.xiaopeng.commonfunc.utils.AfterSalesHelper;
import com.xiaopeng.commonfunc.utils.CarHelper;
import com.xiaopeng.datalog.DataLogModuleEntry;
import com.xiaopeng.lib.framework.ipcmodule.IpcModuleEntry;
import com.xiaopeng.lib.framework.module.Module;
import com.xiaopeng.lib.framework.moduleinterface.ipcmodule.IIpcService;
import com.xiaopeng.lib.framework.moduleinterface.netchannelmodule.http.IHttp;
import com.xiaopeng.lib.framework.netchannelmodule.NetworkChannelsEntry;
import com.xiaopeng.lib.framework.netchannelmodule.common.TrafficeStaFlagInterceptor;
import com.xiaopeng.lib.http.HttpsUtils;
import com.xiaopeng.lib.utils.LogUtils;
import com.xiaopeng.lib.utils.ProcessUtils;
import com.xiaopeng.libconfig.ipc.IpcConfig;
import com.xiaopeng.xmlconfig.XMLDataStorage;
import com.xiaopeng.xui.Xui;
import org.greenrobot.eventbus.EventBus;
/* loaded from: classes4.dex */
public class CarApplication extends Application {
    private static final boolean DBG = false;
    private static final String TAG = "XpCarDiagnosis";
    private static Application sApplication;

    public static boolean isDBG() {
        return false;
    }

    public static Context getContext() {
        return sApplication.getApplicationContext();
    }

    public static Application getApplication() {
        return sApplication;
    }

    public static IIpcService getIPCService() {
        return (IIpcService) Module.get(IpcModuleEntry.class).get(IIpcService.class);
    }

    @Override // android.app.Application
    public void onCreate() {
        super.onCreate();
        sApplication = this;
        long time = System.currentTimeMillis();
        if (getPackageName().endsWith(ProcessUtils.getCurProcessName())) {
            try {
                LogUtils.d(TAG, "start init application");
                parseXmlConfig();
                CarHelper.init(getApplicationContext());
                AfterSalesHelper.init(getApplicationContext());
                Xui.init(this);
                HttpsUtils.init(this, true);
                registerModule();
            } catch (Throwable e) {
                e.printStackTrace();
            }
            LogUtils.d(TAG, "onCreate time = " + (System.currentTimeMillis() - time));
        }
        Intent intent = new Intent("com.xiaopeng.cardiagnosis.service.StorageClearService");
        intent.setPackage(IpcConfig.App.APP_CAR_DIAGNOSIS);
        intent.putExtra("isFirstStart", true);
        startService(intent);
    }

    private void parseXmlConfig() {
        if (!XMLDataStorage.instance().parseXML(this)) {
            LogUtils.e(TAG, "XML config parsing was failed");
        }
    }

    @Override // android.app.Application
    public void onTerminate() {
        super.onTerminate();
        if (getPackageName().endsWith(ProcessUtils.getCurProcessName())) {
            HttpsUtils.destroy();
            CarHelper.deinit();
            AfterSalesHelper.deinit();
        }
    }

    private void registerModule() {
        removeEventBusLog();
        Module.register(IpcModuleEntry.class, new IpcModuleEntry(this));
        Module.register(NetworkChannelsEntry.class, new NetworkChannelsEntry());
        Module.register(DataLogModuleEntry.class, new DataLogModuleEntry(this));
        IHttp http = (IHttp) Module.get(NetworkChannelsEntry.class).get(IHttp.class);
        http.config().applicationContext(this).addInterceptor(new TrafficeStaFlagInterceptor()).apply();
        getIPCService().init();
    }

    private void removeEventBusLog() {
        try {
            EventBus.builder().sendNoSubscriberEvent(false).logNoSubscriberMessages(false).logSubscriberExceptions(false).installDefaultEventBus();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
