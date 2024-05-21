package com.xiaopeng.commonfunc.utils.tftp;

import java.net.DatagramPacket;
import java.net.InetAddress;
import java.util.Locale;
/* loaded from: classes4.dex */
public abstract class TFTPRequestPacket extends TFTPPacket {
    protected static final byte[][] _modeBytes = {new byte[]{110, 101, 116, 97, 115, 99, 105, 105, 0}, new byte[]{111, 99, 116, 101, 116, 0}};
    static final String[] _modeStrings = {"netascii", "octet"};
    protected String _filename;
    protected int _mode;

    /* JADX INFO: Access modifiers changed from: package-private */
    public TFTPRequestPacket(InetAddress destination, int port, int type, int segmentSize, String filename, int mode) {
        super(type, destination, port, segmentSize);
        this._filename = filename;
        this._mode = mode;
        this._segmentSize = segmentSize;
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public TFTPRequestPacket(int type, int segmentSize, DatagramPacket datagram) throws TFTPPacketException {
        super(type, datagram.getAddress(), datagram.getPort(), segmentSize);
        this._segmentSize = segmentSize;
        byte[] data = datagram.getData();
        if (getType() != data[1]) {
            throw new TFTPPacketException("TFTP operator code does not match type.");
        }
        StringBuilder buffer = new StringBuilder();
        int index = 2;
        int length = datagram.getLength();
        while (index < length && data[index] != 0) {
            buffer.append((char) data[index]);
            index++;
        }
        this._filename = buffer.toString();
        if (index >= length) {
            throw new TFTPPacketException("Bad filename and mode format.");
        }
        buffer.setLength(0);
        for (int index2 = index + 1; index2 < length && data[index2] != 0; index2++) {
            buffer.append((char) data[index2]);
        }
        String modeString = buffer.toString().toLowerCase(Locale.ENGLISH);
        int length2 = _modeStrings.length;
        int mode = 0;
        int index3 = 0;
        while (true) {
            if (index3 < length2) {
                if (!modeString.equals(_modeStrings[index3])) {
                    index3++;
                } else {
                    mode = index3;
                    break;
                }
            } else {
                break;
            }
        }
        this._mode = mode;
        if (index3 >= length2) {
            throw new TFTPPacketException("Unrecognized TFTP transfer mode: " + modeString);
        }
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    @Override // com.xiaopeng.commonfunc.utils.tftp.TFTPPacket
    public DatagramPacket _newDatagram(DatagramPacket datagram, byte[] data) {
        int fileLength = this._filename.length();
        int modeLength = _modeBytes[this._mode].length;
        data[0] = 0;
        data[1] = (byte) this._type;
        System.arraycopy(this._filename.getBytes(), 0, data, 2, fileLength);
        data[fileLength + 2] = 0;
        System.arraycopy(_modeBytes[this._mode], 0, data, fileLength + 3, modeLength);
        datagram.setAddress(this._address);
        datagram.setPort(this._port);
        datagram.setData(data);
        datagram.setLength(fileLength + modeLength + 3);
        return datagram;
    }

    @Override // com.xiaopeng.commonfunc.utils.tftp.TFTPPacket
    public DatagramPacket newDatagram() {
        int fileLength = this._filename.length();
        int modeLength = _modeBytes[this._mode].length;
        byte[] data = new byte[fileLength + modeLength + 4];
        data[0] = 0;
        data[1] = (byte) this._type;
        System.arraycopy(this._filename.getBytes(), 0, data, 2, fileLength);
        data[fileLength + 2] = 0;
        System.arraycopy(_modeBytes[this._mode], 0, data, fileLength + 3, modeLength);
        return new DatagramPacket(data, data.length, this._address, this._port);
    }

    public final int getMode() {
        return this._mode;
    }

    public final String getFilename() {
        return this._filename;
    }
}
