package com.xiaopeng.commonfunc.utils.tftp;

import com.xiaopeng.commonfunc.Constant;
import java.net.DatagramPacket;
import java.net.InetAddress;
/* loaded from: classes4.dex */
public abstract class TFTPPacket {
    public static final int ACKNOWLEDGEMENT = 4;
    public static final int DATA = 3;
    public static final int DEFAULT_SEGMENT_SIZE = 512;
    public static final int ERROR = 5;
    static final int MIN_PACKET_SIZE = 4;
    public static final int OPTIONS_ACKNOWLEDGEMENT = 6;
    public static final int READ_REQUEST = 1;
    public static final int WRITE_REQUEST = 2;
    InetAddress _address;
    int _port;
    int _segmentSize;
    int _type;

    /* JADX INFO: Access modifiers changed from: package-private */
    public abstract DatagramPacket _newDatagram(DatagramPacket datagramPacket, byte[] bArr);

    public abstract DatagramPacket newDatagram();

    /* JADX INFO: Access modifiers changed from: package-private */
    public TFTPPacket(int type, InetAddress address, int port) {
        this._type = type;
        this._address = address;
        this._port = port;
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public TFTPPacket(int type, InetAddress address, int port, int segmentSize) {
        this._type = type;
        this._address = address;
        this._port = port;
        this._segmentSize = segmentSize;
    }

    public static final TFTPPacket newTFTPPacket(DatagramPacket datagram) throws TFTPPacketException {
        if (datagram.getLength() < 4) {
            throw new TFTPPacketException("Bad packet. Datagram data length is too short.");
        }
        byte[] data = datagram.getData();
        int segmentSize = datagram.getLength() - 4;
        switch (data[1]) {
            case 1:
                TFTPPacket packet = new TFTPReadRequestPacket(segmentSize, datagram);
                return packet;
            case 2:
                TFTPPacket packet2 = new TFTPWriteRequestPacket(segmentSize, datagram);
                return packet2;
            case 3:
                TFTPPacket packet3 = new TFTPDataPacket(datagram);
                return packet3;
            case 4:
            case 6:
                TFTPPacket packet4 = new TFTPAckPacket(datagram);
                return packet4;
            case 5:
                TFTPPacket packet5 = new TFTPErrorPacket(datagram);
                return packet5;
            default:
                throw new TFTPPacketException("Bad packet.  Invalid TFTP operator code.");
        }
    }

    public final int getType() {
        return this._type;
    }

    public final InetAddress getAddress() {
        return this._address;
    }

    public final void setAddress(InetAddress address) {
        this._address = address;
    }

    public final int getPort() {
        return this._port;
    }

    public final void setPort(int port) {
        this._port = port;
    }

    public void setSegmentSize(int size) {
        this._segmentSize = size;
    }

    public String toString() {
        return this._address + Constant.SPACE_STRING + this._port + Constant.SPACE_STRING + this._type + Constant.SPACE_STRING + this._segmentSize;
    }
}
