package com.xiaopeng.lib.framework.ipcmodule.proxy;

import android.content.Context;
import android.os.Bundle;
import com.xiaopeng.lib.framework.ipcmodule.IpcServiceImpl;
import com.xiaopeng.lib.framework.moduleinterface.ipcmodule.IIpcService;
/* loaded from: classes4.dex */
public class IpcServiceProxy implements IIpcService {
    public IpcServiceProxy(Context context) {
        IpcServiceImpl.getInstance().init(context);
    }

    @Override // com.xiaopeng.lib.framework.moduleinterface.ipcmodule.IIpcService
    public void init() {
    }

    @Override // com.xiaopeng.lib.framework.moduleinterface.ipcmodule.IIpcService
    public void sendData(int msgId, Bundle payloadData, String... appIds) {
        IpcServiceImpl.getInstance().sendData(msgId, payloadData, appIds);
    }
}
