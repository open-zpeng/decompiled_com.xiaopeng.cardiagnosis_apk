package com.xiaopeng.lib.apirouter.server;

import android.net.Uri;
import android.os.Binder;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.RemoteException;
import com.xiaopeng.cardiagnosis.IpcRouterService;
/* loaded from: classes4.dex */
public class IpcRouterService_Stub extends Binder implements IInterface {
    public IpcRouterService provider = new IpcRouterService();
    public IpcRouterService_Manifest manifest = new IpcRouterService_Manifest();

    @Override // android.os.IInterface
    public IBinder asBinder() {
        return this;
    }

    @Override // android.os.Binder
    public boolean onTransact(int code, Parcel data, Parcel reply, int flags) throws RemoteException {
        if (code != 0) {
            if (code == 1598968902) {
                reply.writeString(IpcRouterService_Manifest.DESCRIPTOR);
                return true;
            }
            return super.onTransact(code, data, reply, flags);
        }
        data.enforceInterface(IpcRouterService_Manifest.DESCRIPTOR);
        Uri uri = (Uri) Uri.CREATOR.createFromParcel(data);
        try {
            Integer _real0 = (Integer) TransactTranslator.read(uri.getQueryParameter("id"), "int");
            String _real1 = (String) TransactTranslator.read(uri.getQueryParameter("bundle"), "java.lang.String");
            this.provider.onReceiverData(_real0.intValue(), _real1);
            reply.writeNoException();
            TransactTranslator.reply(reply, null);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            reply.writeException(new IllegalStateException(e.getMessage()));
            return true;
        }
    }
}
