package com.xiaopeng.commonfunc.utils.tftp;

import com.xiaopeng.commonfunc.Constant;
import java.net.DatagramPacket;
import java.net.InetAddress;
/* loaded from: classes4.dex */
public final class TFTPOptionsWriteRequestPacket extends TFTPOptionsRequestPacket {
    public TFTPOptionsWriteRequestPacket(InetAddress destination, int port, int segmentSize, String filename, long fileSize, int mode) {
        super(destination, port, 2, segmentSize, filename, fileSize, mode);
    }

    TFTPOptionsWriteRequestPacket(int segmentSize, DatagramPacket datagram) throws TFTPPacketException {
        super(2, segmentSize, datagram);
    }

    @Override // com.xiaopeng.commonfunc.utils.tftp.TFTPPacket
    public String toString() {
        return super.toString() + " WRQ " + getFilename() + Constant.SPACE_STRING + TFTP.getModeName(getMode());
    }
}
