package com.xiaopeng.commonfunc.utils.tftp;

import com.xiaopeng.commonfunc.Constant;
import java.net.DatagramPacket;
import java.net.InetAddress;
/* loaded from: classes4.dex */
public final class TFTPReadRequestPacket extends TFTPRequestPacket {
    public TFTPReadRequestPacket(InetAddress destination, int port, int segmentSize, String filename, int mode) {
        super(destination, port, 1, segmentSize, filename, mode);
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public TFTPReadRequestPacket(int segmentSize, DatagramPacket datagram) throws TFTPPacketException {
        super(1, segmentSize, datagram);
    }

    @Override // com.xiaopeng.commonfunc.utils.tftp.TFTPPacket
    public String toString() {
        return super.toString() + " RRQ " + getFilename() + Constant.SPACE_STRING + TFTP.getModeName(getMode());
    }
}
