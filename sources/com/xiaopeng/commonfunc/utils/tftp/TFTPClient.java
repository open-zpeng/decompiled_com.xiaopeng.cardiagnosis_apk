package com.xiaopeng.commonfunc.utils.tftp;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.InetAddress;
import java.net.UnknownHostException;
/* loaded from: classes4.dex */
public class TFTPClient extends TFTP {
    public static final int DEFAULT_MAX_TIMEOUTS = 5;
    private static final String TAG = "TFTPClient";
    private long totalBytesReceived = 0;
    private long totalBytesSent = 0;
    private int __maxTimeouts = 5;

    public int getMaxTimeouts() {
        return this.__maxTimeouts;
    }

    public void setMaxTimeouts(int numTimeouts) {
        if (numTimeouts < 1) {
            this.__maxTimeouts = 1;
        } else {
            this.__maxTimeouts = numTimeouts;
        }
    }

    public long getTotalBytesReceived() {
        return this.totalBytesReceived;
    }

    public long getTotalBytesSent() {
        return this.totalBytesSent;
    }

    /*  JADX ERROR: JadxRuntimeException in pass: BlockProcessor
        jadx.core.utils.exceptions.JadxRuntimeException: Found unreachable blocks
        	at jadx.core.dex.visitors.blocks.DominatorTree.sortBlocks(DominatorTree.java:35)
        	at jadx.core.dex.visitors.blocks.DominatorTree.compute(DominatorTree.java:25)
        	at jadx.core.dex.visitors.blocks.BlockProcessor.computeDominators(BlockProcessor.java:202)
        	at jadx.core.dex.visitors.blocks.BlockProcessor.processBlocksTree(BlockProcessor.java:45)
        	at jadx.core.dex.visitors.blocks.BlockProcessor.visit(BlockProcessor.java:39)
        */
    public int receiveFile(java.lang.String r24, int r25, java.io.OutputStream r26, java.net.InetAddress r27, int r28, int r29) throws java.io.IOException {
        /*
            Method dump skipped, instructions count: 1166
            To view this dump add '--comments-level debug' option
        */
        throw new UnsupportedOperationException("Method not decompiled: com.xiaopeng.commonfunc.utils.tftp.TFTPClient.receiveFile(java.lang.String, int, java.io.OutputStream, java.net.InetAddress, int, int):int");
    }

    public int receiveFile(String filename, int mode, OutputStream output, String hostname, int port, int segmentSize) throws UnknownHostException, IOException {
        return receiveFile(filename, mode, output, InetAddress.getByName(hostname), port, segmentSize);
    }

    public int receiveFile(String filename, int mode, OutputStream output, InetAddress host, int segmentSize) throws IOException {
        return receiveFile(filename, mode, output, host, 69, segmentSize);
    }

    public int receiveFile(String filename, int mode, OutputStream output, String hostname, int segmentSize) throws UnknownHostException, IOException {
        return receiveFile(filename, mode, output, InetAddress.getByName(hostname), 69, segmentSize);
    }

    /*  JADX ERROR: JadxRuntimeException in pass: BlockProcessor
        jadx.core.utils.exceptions.JadxRuntimeException: Unreachable block: B:37:0x00e2
        	at jadx.core.dex.visitors.blocks.BlockProcessor.checkForUnreachableBlocks(BlockProcessor.java:81)
        	at jadx.core.dex.visitors.blocks.BlockProcessor.processBlocksTree(BlockProcessor.java:47)
        	at jadx.core.dex.visitors.blocks.BlockProcessor.visit(BlockProcessor.java:39)
        */
    public void sendFile(java.lang.String r26, int r27, java.io.InputStream r28, java.net.InetAddress r29, int r30, int r31) throws java.io.IOException {
        /*
            Method dump skipped, instructions count: 907
            To view this dump add '--comments-level debug' option
        */
        throw new UnsupportedOperationException("Method not decompiled: com.xiaopeng.commonfunc.utils.tftp.TFTPClient.sendFile(java.lang.String, int, java.io.InputStream, java.net.InetAddress, int, int):void");
    }

    public void sendFile(String filename, int mode, InputStream input, String hostname, int port, int segmentSize) throws UnknownHostException, IOException {
        sendFile(filename, mode, input, InetAddress.getByName(hostname), port, segmentSize);
    }

    public void sendFile(String filename, int mode, InputStream input, InetAddress host, int segmentSize) throws IOException {
        sendFile(filename, mode, input, host, 69, segmentSize);
    }

    public void sendFile(String filename, int mode, InputStream input, String hostname, int segmentSize) throws UnknownHostException, IOException {
        sendFile(filename, mode, input, InetAddress.getByName(hostname), 69, segmentSize);
    }

    public void sendFile(String filename, int mode, InputStream input, String hostname) throws IOException {
        sendFile(filename, mode, input, InetAddress.getByName(hostname), 69, 512);
    }
}
