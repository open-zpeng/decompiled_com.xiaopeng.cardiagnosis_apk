package com.xiaopeng.lib.framework.ipcmodule;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.Handler;
import android.os.HandlerThread;
import android.os.IBinder;
import android.os.Looper;
import android.os.Message;
import android.os.RemoteException;
import android.os.TransactionTooLargeException;
import android.util.Log;
import com.xiaopeng.ipc.IPC;
import com.xiaopeng.ipc.IPCCallback;
import com.xiaopeng.ipc.IpcMessage;
import com.xiaopeng.lib.framework.moduleinterface.ipcmodule.IIpcService;
import com.xiaopeng.lib.utils.info.AppInfoUtils;
import java.security.InvalidParameterException;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import org.greenrobot.eventbus.EventBus;
/* loaded from: classes4.dex */
public class IpcServiceImpl implements IIpcService {
    private static final int IPC_MIN_REGISTER_INTERVAL = 500;
    private static final int IPC_SERVICE_BIND_INTERVAL = 1000;
    private static final int MSG_BIND_SERVICE = 1;
    private static final int MSG_UNBIND_SERVICE = 2;
    private static final String PACKAGE_SPLIT = ";";
    private static final String TAG = "IpcServiceImpl";
    private volatile IPC ipcService;
    private volatile Context mContext;
    private IBinder.DeathRecipient mDeathRecipient;
    private ExecutorService mExecutorService;
    private IpcHandler mHandler;
    private HandlerThread mHandlerThread;
    private IPCCallback mIPCCallback;
    private long mLastRegisterTime;
    private ConcurrentLinkedQueue<IPCData> mQueue;
    private Runnable mSendingRunnable;
    private ServiceConnection mServiceConnection;

    /* JADX INFO: Access modifiers changed from: private */
    public void registerIpcClient(IPC service) throws RemoteException {
        StringBuilder sb = new StringBuilder();
        sb.append("registerIpcClient:\t service !=null:\t");
        sb.append(service != null);
        sb.append("");
        sb.append(AppInfoUtils.getProcessName());
        Log.i(TAG, sb.toString());
        if (service != null) {
            service.registerClient(AppInfoUtils.getProcessName(), this.mIPCCallback);
            this.mLastRegisterTime = System.currentTimeMillis();
        }
    }

    private IpcServiceImpl() {
        this.mLastRegisterTime = 0L;
        this.mQueue = new ConcurrentLinkedQueue<>();
        this.mExecutorService = Executors.newSingleThreadExecutor();
        this.mIPCCallback = new IPCCallback.Stub() { // from class: com.xiaopeng.lib.framework.ipcmodule.IpcServiceImpl.1
            @Override // com.xiaopeng.ipc.IPCCallback
            public void onReceive(IpcMessage message) throws RemoteException {
                Log.i(IpcServiceImpl.TAG, "onReceive:\t sender " + message.getPackageName() + "\thasSubscriberForEvent:\t" + EventBus.getDefault().hasSubscriberForEvent(IIpcService.IpcMessageEvent.class));
                IIpcService.IpcMessageEvent messageEvent = new IIpcService.IpcMessageEvent();
                messageEvent.setPayloadData(message.getPayloadData());
                messageEvent.setMsgID(message.getMsgId());
                messageEvent.setSenderPackageName(message.getPackageName());
                if (EventBus.getDefault().hasSubscriberForEvent(IIpcService.IpcMessageEvent.class)) {
                    Log.i(IpcServiceImpl.TAG, "post messageEvent");
                    EventBus.getDefault().post(messageEvent);
                    return;
                }
                Log.i(IpcServiceImpl.TAG, "postSticky messageEvent");
                EventBus.getDefault().postSticky(messageEvent);
            }
        };
        this.mSendingRunnable = new Runnable() { // from class: com.xiaopeng.lib.framework.ipcmodule.IpcServiceImpl.2
            @Override // java.lang.Runnable
            public void run() {
                while (!IpcServiceImpl.this.mQueue.isEmpty()) {
                    if (IpcServiceImpl.this.ipcService != null) {
                        IPCData ipcData = (IPCData) IpcServiceImpl.this.mQueue.peek();
                        if (ipcData != null) {
                            try {
                                synchronized (IpcServiceImpl.class) {
                                    StringBuilder sb = new StringBuilder();
                                    sb.append("sendData+ipcService!=null:\t");
                                    sb.append(IpcServiceImpl.this.ipcService != null);
                                    sb.append(":\tappIds:\t");
                                    sb.append(ipcData.getAppIds());
                                    sb.append("\tdata:\t");
                                    sb.append(ipcData.getData());
                                    Log.i(IpcServiceImpl.TAG, sb.toString());
                                    if (IpcServiceImpl.this.ipcService != null) {
                                        IpcServiceImpl.this.ipcService.sendData(ipcData.getAppIds(), ipcData.getData());
                                        IpcServiceImpl.this.mQueue.poll();
                                    }
                                }
                            } catch (RemoteException e) {
                                e.printStackTrace();
                                Log.e(IpcServiceImpl.TAG, "Send Data error, e-->" + e.getMessage());
                                if (e instanceof TransactionTooLargeException) {
                                    Log.e(IpcServiceImpl.TAG, "Too large data: " + ipcData);
                                    IpcServiceImpl.this.mQueue.poll();
                                }
                            }
                        }
                    } else {
                        IpcServiceImpl.this.sendBindServiceMessage();
                        try {
                            Thread.sleep(1000L);
                        } catch (InterruptedException e2) {
                            e2.printStackTrace();
                        }
                    }
                }
            }
        };
        this.mDeathRecipient = new IBinder.DeathRecipient() { // from class: com.xiaopeng.lib.framework.ipcmodule.IpcServiceImpl.3
            @Override // android.os.IBinder.DeathRecipient
            public void binderDied() {
                synchronized (IpcServiceImpl.class) {
                    if (IpcServiceImpl.this.ipcService == null) {
                        return;
                    }
                    IpcServiceImpl.this.ipcService.asBinder().unlinkToDeath(IpcServiceImpl.this.mDeathRecipient, 0);
                    IpcServiceImpl.this.ipcService = null;
                    IpcServiceImpl.this.sendBindServiceMessage();
                }
            }
        };
        this.mServiceConnection = new ServiceConnection() { // from class: com.xiaopeng.lib.framework.ipcmodule.IpcServiceImpl.4
            @Override // android.content.ServiceConnection
            public void onServiceConnected(ComponentName name, IBinder service) {
                Log.d(IpcServiceImpl.TAG, "onServiceConnected");
                synchronized (IpcServiceImpl.class) {
                    IpcServiceImpl.this.ipcService = IPC.Stub.asInterface(service);
                    try {
                        IpcServiceImpl.this.registerIpcClient(IpcServiceImpl.this.ipcService);
                        service.linkToDeath(IpcServiceImpl.this.mDeathRecipient, 0);
                    } catch (RemoteException ex) {
                        Log.i(IpcServiceImpl.TAG, "onServiceConnected exception");
                        ex.printStackTrace();
                    }
                }
            }

            @Override // android.content.ServiceConnection
            public void onServiceDisconnected(ComponentName name) {
                Log.d(IpcServiceImpl.TAG, "onServiceDisconnected ipcService-->" + IpcServiceImpl.this.ipcService);
                synchronized (IpcServiceImpl.class) {
                    if (IpcServiceImpl.this.ipcService == null) {
                        return;
                    }
                    try {
                        IpcServiceImpl.this.ipcService.unregisterClient(AppInfoUtils.getProcessName(), IpcServiceImpl.this.mIPCCallback);
                        IpcServiceImpl.this.ipcService.asBinder().unlinkToDeath(IpcServiceImpl.this.mDeathRecipient, 0);
                    } catch (RemoteException e) {
                        Log.i(IpcServiceImpl.TAG, "onServiceDisconnected exception");
                        e.printStackTrace();
                    }
                    IpcServiceImpl.this.ipcService = null;
                }
            }
        };
        this.mHandlerThread = new HandlerThread("ipcmodule");
        this.mHandlerThread.start();
        this.mHandler = new IpcHandler(this.mHandlerThread.getLooper());
    }

    public static IpcServiceImpl getInstance() {
        return IpcModuleServiceSingle.INSTANCE;
    }

    protected void sendBindServiceMessage() {
        if (this.mHandler.hasMessages(1)) {
            this.mHandler.removeMessages(1);
        }
        this.mHandler.sendEmptyMessage(1);
    }

    protected void sendUnbindServiceMessage() {
        if (this.mHandler.hasMessages(2)) {
            this.mHandler.removeMessages(2);
        }
        this.mHandler.sendEmptyMessage(2);
    }

    public void init(Context context) {
        Log.d(TAG, "init context:" + context);
        if (this.mContext == null) {
            this.mContext = context;
        }
        sendBindServiceMessage();
    }

    @Override // com.xiaopeng.lib.framework.moduleinterface.ipcmodule.IIpcService
    public void init() {
        boolean moreThanInterval = System.currentTimeMillis() - this.mLastRegisterTime > 500;
        Log.i(TAG, "init--> moreThanInterval:\t" + moreThanInterval);
        if (moreThanInterval) {
            try {
                synchronized (IpcServiceImpl.class) {
                    registerIpcClient(this.ipcService);
                }
            } catch (RemoteException ex) {
                Log.i(TAG, "registerIpcClient:\t exception" + ex.getMessage());
                ex.printStackTrace();
            }
        }
    }

    @Override // com.xiaopeng.lib.framework.moduleinterface.ipcmodule.IIpcService
    public void sendData(int msgId, Bundle payloadData, String... appIds) {
        if (this.mContext == null) {
            throw new IllegalStateException("please init context");
        }
        if (appIds.length == 0) {
            throw new InvalidParameterException("destination is null");
        }
        StringBuilder appIdBuffer = new StringBuilder();
        int appIdCount = appIds.length;
        int index = 0;
        for (String appId : appIds) {
            index++;
            appIdBuffer.append(appId);
            if (index < appIdCount) {
                appIdBuffer.append(";");
            }
        }
        IPCData ipcData = new IPCData();
        IpcMessage ipcMessage = new IpcMessage();
        ipcMessage.setPackageName(this.mContext.getPackageName());
        ipcMessage.setMsgId(msgId);
        ipcMessage.setPayloadData(payloadData);
        ipcData.setData(ipcMessage);
        ipcData.setAppIds(appIdBuffer.toString());
        this.mQueue.offer(ipcData);
        this.mExecutorService.execute(this.mSendingRunnable);
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* loaded from: classes4.dex */
    public class IpcHandler extends Handler {
        public IpcHandler(Looper looper) {
            super(looper);
        }

        @Override // android.os.Handler
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            int i = msg.what;
            if (i == 1) {
                Log.d(IpcServiceImpl.TAG, "bind ServiceIntent");
                bindRemoteService();
            } else if (i == 2) {
                Log.d(IpcServiceImpl.TAG, "unbind ServiceIntent");
                unbindRemoteService();
            }
        }

        private void bindRemoteService() {
            Log.d(IpcServiceImpl.TAG, "mContext = " + IpcServiceImpl.this.mContext + ", ipcService = " + IpcServiceImpl.this.ipcService);
            if (IpcServiceImpl.this.mContext != null && IpcServiceImpl.this.ipcService == null) {
                Intent intent = new Intent("com.xiaopeng.ipc.IPCAidl");
                intent.setPackage("com.xiaopeng.ipc");
                IpcServiceImpl.this.mContext.bindService(intent, IpcServiceImpl.this.mServiceConnection, 1);
            }
        }

        private void unbindRemoteService() {
            if (IpcServiceImpl.this.mContext != null) {
                IpcServiceImpl.this.mContext.unbindService(IpcServiceImpl.this.mServiceConnection);
                IpcServiceImpl.this.mServiceConnection.onServiceDisconnected(null);
            }
        }
    }

    /* loaded from: classes4.dex */
    private class IPCData {
        private String appIds;
        private IpcMessage data;

        private IPCData() {
        }

        public IpcMessage getData() {
            return this.data;
        }

        public void setData(IpcMessage data) {
            this.data = data;
        }

        public String getAppIds() {
            return this.appIds;
        }

        public void setAppIds(String appIds) {
            this.appIds = appIds;
        }

        public String toString() {
            return "IPCData{data='" + this.data + "', appIds='" + this.appIds + "'}";
        }
    }

    /* loaded from: classes4.dex */
    private static class IpcModuleServiceSingle {
        private static final IpcServiceImpl INSTANCE = new IpcServiceImpl();

        private IpcModuleServiceSingle() {
        }
    }
}
