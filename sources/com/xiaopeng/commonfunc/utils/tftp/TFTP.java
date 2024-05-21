package com.xiaopeng.commonfunc.utils.tftp;

import java.io.IOException;
import java.io.InterruptedIOException;
import java.net.DatagramPacket;
import java.net.SocketException;
import org.apache.commons.net.DatagramSocketClient;
/* loaded from: classes4.dex */
public class TFTP extends DatagramSocketClient {
    public static final int ASCII_MODE = 0;
    public static final int BINARY_MODE = 1;
    public static final int DEFAULT_PORT = 69;
    public static final int DEFAULT_TIMEOUT = 60000;
    public static final int IMAGE_MODE = 1;
    public static final int NETASCII_MODE = 0;
    public static final int OCTET_MODE = 1;
    public static final int SEGMENT_SEZE = 8192;
    private byte[] __receiveBuffer;
    private DatagramPacket __receiveDatagram;
    private DatagramPacket __sendDatagram;
    byte[] _sendBuffer;

    public TFTP() {
        setDefaultTimeout(DEFAULT_TIMEOUT);
        this.__receiveBuffer = null;
        this.__receiveDatagram = null;
    }

    public static final String getModeName(int mode) {
        return TFTPRequestPacket._modeStrings[mode];
    }

    int getPacketSize(int segmentSize) {
        return segmentSize + 4;
    }

    public final void discardPackets(int segmentSize) throws IOException {
        int packetSize = getPacketSize(segmentSize);
        DatagramPacket datagram = new DatagramPacket(new byte[packetSize], packetSize);
        int to = getSoTimeout();
        setSoTimeout(1);
        while (true) {
            try {
                this._socket_.receive(datagram);
            } catch (InterruptedIOException | SocketException e) {
                setSoTimeout(to);
                return;
            }
        }
    }

    public final TFTPPacket bufferedReceive() throws IOException, InterruptedIOException, SocketException, TFTPPacketException {
        this.__receiveDatagram.setData(this.__receiveBuffer);
        this.__receiveDatagram.setLength(this.__receiveBuffer.length);
        this._socket_.receive(this.__receiveDatagram);
        TFTPPacket newTFTPPacket = TFTPPacket.newTFTPPacket(this.__receiveDatagram);
        trace("<", newTFTPPacket);
        return newTFTPPacket;
    }

    public final void bufferedSend(TFTPPacket packet) throws IOException {
        trace(">", packet);
        this._socket_.send(packet._newDatagram(this.__sendDatagram, this._sendBuffer));
    }

    public final void beginBufferedOps(int segmentSize) {
        int packetSize = getPacketSize(segmentSize);
        this.__receiveBuffer = new byte[packetSize];
        byte[] bArr = this.__receiveBuffer;
        this.__receiveDatagram = new DatagramPacket(bArr, bArr.length);
        this._sendBuffer = new byte[packetSize];
        byte[] bArr2 = this._sendBuffer;
        this.__sendDatagram = new DatagramPacket(bArr2, bArr2.length);
    }

    public final void endBufferedOps() {
        this.__receiveBuffer = null;
        this.__receiveDatagram = null;
        this._sendBuffer = null;
        this.__sendDatagram = null;
    }

    public final void send(TFTPPacket packet) throws IOException {
        trace(">", packet);
        this._socket_.send(packet.newDatagram());
    }

    public final TFTPPacket receive(int segmentSize) throws IOException, InterruptedIOException, SocketException, TFTPPacketException {
        int packetSize = getPacketSize(segmentSize);
        DatagramPacket packet = new DatagramPacket(new byte[packetSize], packetSize);
        this._socket_.receive(packet);
        TFTPPacket newTFTPPacket = TFTPPacket.newTFTPPacket(packet);
        trace("<", newTFTPPacket);
        return newTFTPPacket;
    }

    protected void trace(String direction, TFTPPacket packet) {
    }
}
