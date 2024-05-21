package com.xiaopeng.commonfunc.utils.tftp;

import com.xiaopeng.commonfunc.Constant;
import java.net.DatagramPacket;
import java.net.InetAddress;
/* loaded from: classes4.dex */
public final class TFTPDataPacket extends TFTPPacket {
    public static final int MAX_DATA_LENGTH = 8192;
    public static final int MIN_DATA_LENGTH = 0;
    int _blockNumber;
    byte[] _data;
    int _length;
    int _offset;

    public TFTPDataPacket(InetAddress destination, int port, int blockNumber, byte[] data, int offset, int length) {
        super(3, destination, port);
        this._blockNumber = blockNumber;
        this._data = data;
        this._offset = offset;
        if (length > 8192) {
            this._length = 8192;
        } else {
            this._length = length;
        }
    }

    public TFTPDataPacket(InetAddress destination, int port, int blockNumber, byte[] data) {
        this(destination, port, blockNumber, data, 0, data.length);
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public TFTPDataPacket(DatagramPacket datagram) throws TFTPPacketException {
        super(3, datagram.getAddress(), datagram.getPort());
        this._data = datagram.getData();
        this._offset = 4;
        int type = getType();
        byte[] bArr = this._data;
        if (type != bArr[1]) {
            throw new TFTPPacketException("TFTP operator code does not match type.");
        }
        this._blockNumber = ((bArr[2] & 255) << 8) | (bArr[3] & 255);
        this._length = datagram.getLength() - 4;
        if (this._length > 8192) {
            this._length = 8192;
        }
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    @Override // com.xiaopeng.commonfunc.utils.tftp.TFTPPacket
    public DatagramPacket _newDatagram(DatagramPacket datagram, byte[] data) {
        data[0] = 0;
        data[1] = (byte) this._type;
        int i = this._blockNumber;
        data[2] = (byte) ((65535 & i) >> 8);
        data[3] = (byte) (i & 255);
        byte[] bArr = this._data;
        if (data != bArr) {
            System.arraycopy(bArr, this._offset, data, 4, this._length);
        }
        datagram.setAddress(this._address);
        datagram.setPort(this._port);
        datagram.setData(data);
        datagram.setLength(this._length + 4);
        return datagram;
    }

    @Override // com.xiaopeng.commonfunc.utils.tftp.TFTPPacket
    public DatagramPacket newDatagram() {
        byte[] data = new byte[this._length + 4];
        data[0] = 0;
        data[1] = (byte) this._type;
        int i = this._blockNumber;
        data[2] = (byte) ((65535 & i) >> 8);
        data[3] = (byte) (i & 255);
        System.arraycopy(this._data, this._offset, data, 4, this._length);
        return new DatagramPacket(data, this._length + 4, this._address, this._port);
    }

    public int getBlockNumber() {
        return this._blockNumber;
    }

    public void setBlockNumber(int blockNumber) {
        this._blockNumber = blockNumber;
    }

    public void setData(byte[] data, int offset, int length) {
        this._data = data;
        this._offset = offset;
        this._length = length;
        if (length > 8192) {
            this._length = 8192;
        } else {
            this._length = length;
        }
    }

    public int getDataLength() {
        return this._length;
    }

    public int getDataOffset() {
        return this._offset;
    }

    public byte[] getData() {
        return this._data;
    }

    @Override // com.xiaopeng.commonfunc.utils.tftp.TFTPPacket
    public String toString() {
        return super.toString() + " DATA " + this._blockNumber + Constant.SPACE_STRING + this._length;
    }
}
