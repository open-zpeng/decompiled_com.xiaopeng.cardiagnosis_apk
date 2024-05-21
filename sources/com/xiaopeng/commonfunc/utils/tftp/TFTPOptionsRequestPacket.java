package com.xiaopeng.commonfunc.utils.tftp;

import java.net.DatagramPacket;
import java.net.InetAddress;
import java.nio.ByteBuffer;
/* loaded from: classes4.dex */
public abstract class TFTPOptionsRequestPacket extends TFTPRequestPacket {
    private static final byte[] _blockSizeBytes = {98, 108, 107, 115, 105, 122, 101};
    private static final byte[] _fileSizeBytes = {116, 115, 105, 122, 101};
    protected long _fileSize;

    /* JADX INFO: Access modifiers changed from: package-private */
    public TFTPOptionsRequestPacket(InetAddress destination, int port, int type, int segmentSize, String filename, long fileSize, int mode) {
        super(destination, port, type, segmentSize, filename, mode);
        this._fileSize = fileSize;
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public TFTPOptionsRequestPacket(int type, int segmentSize, DatagramPacket datagram) throws TFTPPacketException {
        super(type, segmentSize, datagram);
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    @Override // com.xiaopeng.commonfunc.utils.tftp.TFTPRequestPacket, com.xiaopeng.commonfunc.utils.tftp.TFTPPacket
    public DatagramPacket _newDatagram(DatagramPacket datagram, byte[] data) {
        byte[] optionsData = createOptionsData();
        System.arraycopy(optionsData, 0, data, 0, optionsData.length);
        datagram.setAddress(this._address);
        datagram.setPort(this._port);
        datagram.setData(data);
        datagram.setLength(optionsData.length);
        return datagram;
    }

    private byte[] createOptionsData() {
        int filenameLength = this._filename.length();
        int modeLength = _modeBytes[this._mode].length;
        int blockSizeNameLength = _blockSizeBytes.length;
        String blockSizeValue = String.valueOf(this._segmentSize);
        int blkSizeLength = blockSizeValue.getBytes().length;
        int fileSizeNameLength = _fileSizeBytes.length;
        int fileSizeLength = String.valueOf(this._fileSize).getBytes().length;
        int capacity = filenameLength + 2 + 1 + modeLength + blockSizeNameLength + 1 + blkSizeLength + 1 + fileSizeNameLength + 1 + fileSizeLength + 1;
        ByteBuffer bytebuffer = ByteBuffer.allocate(capacity);
        bytebuffer.put((byte) 0);
        bytebuffer.put((byte) this._type);
        bytebuffer.put(this._filename.getBytes());
        bytebuffer.put((byte) 0);
        bytebuffer.put(_modeBytes[this._mode]);
        bytebuffer.put(_blockSizeBytes);
        bytebuffer.put((byte) 0);
        bytebuffer.put(blockSizeValue.getBytes());
        bytebuffer.put((byte) 0);
        bytebuffer.put(_fileSizeBytes);
        bytebuffer.put((byte) 0);
        bytebuffer.put(String.valueOf(this._fileSize).getBytes());
        bytebuffer.put((byte) 0);
        return bytebuffer.array();
    }

    @Override // com.xiaopeng.commonfunc.utils.tftp.TFTPRequestPacket, com.xiaopeng.commonfunc.utils.tftp.TFTPPacket
    public final DatagramPacket newDatagram() {
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
}
