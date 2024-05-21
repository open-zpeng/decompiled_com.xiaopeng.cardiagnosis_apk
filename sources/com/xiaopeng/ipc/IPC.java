package com.xiaopeng.ipc;

import android.os.Binder;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.RemoteException;
import com.xiaopeng.ipc.IPCCallback;
/* loaded from: classes4.dex */
public interface IPC extends IInterface {
    void registerClient(String str, IPCCallback iPCCallback) throws RemoteException;

    void sendData(String str, IpcMessage ipcMessage) throws RemoteException;

    void unregisterClient(String str, IPCCallback iPCCallback) throws RemoteException;

    /* loaded from: classes4.dex */
    public static abstract class Stub extends Binder implements IPC {
        private static final String DESCRIPTOR = "com.xiaopeng.ipc.IPC";
        static final int TRANSACTION_registerClient = 1;
        static final int TRANSACTION_sendData = 3;
        static final int TRANSACTION_unregisterClient = 2;

        public Stub() {
            attachInterface(this, DESCRIPTOR);
        }

        public static IPC asInterface(IBinder obj) {
            if (obj == null) {
                return null;
            }
            IInterface iin = obj.queryLocalInterface(DESCRIPTOR);
            if (iin != null && (iin instanceof IPC)) {
                return (IPC) iin;
            }
            return new Proxy(obj);
        }

        @Override // android.os.IInterface
        public IBinder asBinder() {
            return this;
        }

        @Override // android.os.Binder
        public boolean onTransact(int code, Parcel data, Parcel reply, int flags) throws RemoteException {
            IpcMessage _arg1;
            if (code == 1) {
                data.enforceInterface(DESCRIPTOR);
                String _arg0 = data.readString();
                IPCCallback _arg12 = IPCCallback.Stub.asInterface(data.readStrongBinder());
                registerClient(_arg0, _arg12);
                reply.writeNoException();
                return true;
            } else if (code == 2) {
                data.enforceInterface(DESCRIPTOR);
                String _arg02 = data.readString();
                IPCCallback _arg13 = IPCCallback.Stub.asInterface(data.readStrongBinder());
                unregisterClient(_arg02, _arg13);
                reply.writeNoException();
                return true;
            } else if (code != 3) {
                if (code == 1598968902) {
                    reply.writeString(DESCRIPTOR);
                    return true;
                }
                return super.onTransact(code, data, reply, flags);
            } else {
                data.enforceInterface(DESCRIPTOR);
                String _arg03 = data.readString();
                if (data.readInt() != 0) {
                    _arg1 = IpcMessage.CREATOR.createFromParcel(data);
                } else {
                    _arg1 = null;
                }
                sendData(_arg03, _arg1);
                reply.writeNoException();
                return true;
            }
        }

        /* loaded from: classes4.dex */
        private static class Proxy implements IPC {
            private IBinder mRemote;

            Proxy(IBinder remote) {
                this.mRemote = remote;
            }

            @Override // android.os.IInterface
            public IBinder asBinder() {
                return this.mRemote;
            }

            public String getInterfaceDescriptor() {
                return Stub.DESCRIPTOR;
            }

            @Override // com.xiaopeng.ipc.IPC
            public void registerClient(String appId, IPCCallback client) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeString(appId);
                    _data.writeStrongBinder(client != null ? client.asBinder() : null);
                    this.mRemote.transact(1, _data, _reply, 0);
                    _reply.readException();
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // com.xiaopeng.ipc.IPC
            public void unregisterClient(String appId, IPCCallback client) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeString(appId);
                    _data.writeStrongBinder(client != null ? client.asBinder() : null);
                    this.mRemote.transact(2, _data, _reply, 0);
                    _reply.readException();
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // com.xiaopeng.ipc.IPC
            public void sendData(String appId, IpcMessage payloadData) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeString(appId);
                    if (payloadData != null) {
                        _data.writeInt(1);
                        payloadData.writeToParcel(_data, 0);
                    } else {
                        _data.writeInt(0);
                    }
                    this.mRemote.transact(3, _data, _reply, 0);
                    _reply.readException();
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }
        }
    }
}
