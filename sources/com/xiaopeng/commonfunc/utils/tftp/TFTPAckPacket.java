package com.xiaopeng.commonfunc.utils.tftp;

import java.net.DatagramPacket;
import java.net.InetAddress;
/* loaded from: classes4.dex */
public final class TFTPAckPacket extends TFTPPacket {
    int _blockNumber;

    public TFTPAckPacket(InetAddress destination, int port, int blockNumber) {
        super(4, destination, port);
        this._blockNumber = blockNumber;
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public TFTPAckPacket(DatagramPacket datagram) throws TFTPPacketException {
        super(4, datagram.getAddress(), datagram.getPort());
        byte[] data = datagram.getData();
        if ((getType() != 4 || (data[1] != 4 && data[1] != 6)) && getType() != data[1]) {
            throw new TFTPPacketException("TFTP operator code does not match type.");
        }
        if (data[1] == 6) {
            return;
        }
        this._blockNumber = ((data[2] & 255) << 8) | (data[3] & 255);
    }

    @Override // com.xiaopeng.commonfunc.utils.tftp.TFTPPacket
    DatagramPacket _newDatagram(DatagramPacket datagram, byte[] data) {
        data[0] = 0;
        data[1] = (byte) this._type;
        int i = this._blockNumber;
        data[2] = (byte) ((65535 & i) >> 8);
        data[3] = (byte) (i & 255);
        datagram.setAddress(this._address);
        datagram.setPort(this._port);
        datagram.setData(data);
        datagram.setLength(4);
        return datagram;
    }

    @Override // com.xiaopeng.commonfunc.utils.tftp.TFTPPacket
    public DatagramPacket newDatagram() {
        int i = this._blockNumber;
        byte[] data = {0, (byte) this._type, (byte) ((65535 & i) >> 8), (byte) (i & 255)};
        return new DatagramPacket(data, data.length, this._address, this._port);
    }

    public int getBlockNumber() {
        return this._blockNumber;
    }

    public void setBlockNumber(int blockNumber) {
        this._blockNumber = blockNumber;
    }

    @Override // com.xiaopeng.commonfunc.utils.tftp.TFTPPacket
    public String toString() {
        return super.toString() + " ACK " + this._blockNumber;
    }
}
