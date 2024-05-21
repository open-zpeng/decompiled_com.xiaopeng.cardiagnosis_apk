package com.xiaopeng.cardiagnosis;

import android.net.Uri;
import android.os.Bundle;
import android.os.RemoteException;
import com.google.gson.Gson;
import com.xiaopeng.cardiagnosis.bean.ApiRouterEvent;
import com.xiaopeng.lib.apirouter.ApiRouter;
import com.xiaopeng.lib.apirouter.server.IServicePublisher;
import com.xiaopeng.lib.apirouter.server.Publish;
import com.xiaopeng.lib.framework.moduleinterface.ipcmodule.IIpcService;
import com.xiaopeng.lib.utils.LogUtils;
import com.xiaopeng.libconfig.ipc.IpcConfig;
import org.greenrobot.eventbus.EventBus;
/* loaded from: classes4.dex */
public class IpcRouterService implements IServicePublisher {
    private static final String TAG = "CarDiagnosisIpcRouter";

    public static void sendData(int id, String bundle, String pkgName) {
        Uri.Builder builder = new Uri.Builder();
        Uri targetUrl = builder.authority(pkgName + ".IpcRouterService").path("onReceiverData").appendQueryParameter("id", String.valueOf(id)).appendQueryParameter("bundle", bundle).build();
        try {
            ApiRouter.route(targetUrl);
        } catch (RemoteException e) {
            e.printStackTrace();
        }
    }

    @Publish
    public void onReceiverData(int id, String bundle) {
        LogUtils.i(TAG, "onReceiverData id" + id + "\tbundle\t" + bundle);
        ApiRouterEvent apiRouterEvent = (ApiRouterEvent) new Gson().fromJson(bundle, (Class<Object>) ApiRouterEvent.class);
        LogUtils.d(TAG, apiRouterEvent.toString());
        IIpcService.IpcMessageEvent ipcMessageEvent = new IIpcService.IpcMessageEvent();
        ipcMessageEvent.setMsgID(id);
        ipcMessageEvent.setSenderPackageName(apiRouterEvent.getSenderPackageName());
        Bundle payload = new Bundle();
        payload.putString(IpcConfig.IPCKey.STRING_MSG, apiRouterEvent.getStringMsg());
        ipcMessageEvent.setPayloadData(payload);
        if (EventBus.getDefault().hasSubscriberForEvent(IIpcService.IpcMessageEvent.class)) {
            EventBus.getDefault().post(ipcMessageEvent);
        } else {
            EventBus.getDefault().postSticky(ipcMessageEvent);
        }
    }
}
