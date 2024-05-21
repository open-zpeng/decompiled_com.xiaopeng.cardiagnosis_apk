package com.xiaopeng.commonfunc.utils;

import com.xiaopeng.commonfunc.utils.tftp.TFTPClient;
import com.xiaopeng.commonfunc.utils.tftp.TFTPPacket;
import com.xiaopeng.lib.utils.LogUtils;
import com.xiaopeng.xmlconfig.Support;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.InetAddress;
import java.net.SocketException;
import java.net.UnknownHostException;
/* loaded from: classes4.dex */
public class TftpSender {
    public static final String LCMS_LOG_PATH = "LCMSLog.zip";
    public static final String RCMS_LOG_PATH = "RCMSLog.zip";
    private static final String TAG = "TftpSender";
    public static final String TBOX_LOG_PATH = "/mnt/sdcard/tftpboot/dlt.tar.gz";
    private static final int TIMEOUT = 10000;
    private static final boolean verbose = true;
    public static final String HOST_TBOX = Support.Case.getString(Support.Case.TBOX_CAN_DATA_SERVER_IP);
    public static final boolean TBOX_SUPPORT_SSH = Support.Case.getEnabled(Support.Case.TBOX_SUPPORT_SSH);
    public static final String HOST_LCMS = Support.Case.getString(Support.Case.HOST_LCMS);
    public static final String HOST_RCMS = Support.Case.getString(Support.Case.HOST_RCMS);

    public static void sendFile(String localFileName, String remoteFileName, String host) throws Exception {
        FileInputStream input;
        TFTPClient tftp = new TFTPClient() { // from class: com.xiaopeng.commonfunc.utils.TftpSender.1
            @Override // com.xiaopeng.commonfunc.utils.tftp.TFTP
            protected void trace(String direction, TFTPPacket packet) {
                LogUtils.d(TftpSender.TAG, "direction: " + direction + ", packet:" + packet.toString());
            }
        };
        tftp.setDefaultTimeout(10000);
        try {
            try {
                input = new FileInputStream(localFileName);
                try {
                    tftp.open();
                    tftp.sendFile(remoteFileName, 1, input, InetAddress.getByName(host), 8192);
                    tftp.close();
                    try {
                        input.close();
                    } catch (IOException e) {
                        LogUtils.e(TAG, e);
                    }
                } catch (UnknownHostException e2) {
                    LogUtils.e(TAG, e2);
                    throw new Exception(e2);
                } catch (IOException e3) {
                    LogUtils.e(TAG, e3);
                    throw new Exception(e3);
                }
            } catch (IOException e4) {
                tftp.close();
                LogUtils.e(TAG, e4);
                throw new Exception(e4);
            }
        } catch (Throwable th) {
            tftp.close();
            try {
                input.close();
            } catch (IOException e5) {
                LogUtils.e(TAG, e5);
            }
            throw th;
        }
    }

    public static void receiveFile(String remoteFileName, String localFileName, String host) {
        LogUtils.d(TAG, "receiveFile remoteFileName = " + remoteFileName + " localFileName = " + localFileName + " host = " + host);
        TFTPClient tftp = new TFTPClient() { // from class: com.xiaopeng.commonfunc.utils.TftpSender.2
            @Override // com.xiaopeng.commonfunc.utils.tftp.TFTP
            protected void trace(String direction, TFTPPacket packet) {
                LogUtils.d(TftpSender.TAG, "direction: " + direction + ", packet:" + packet.toString());
            }
        };
        tftp.setDefaultTimeout(10000);
        File file = new File(localFileName);
        try {
            FileOutputStream output = new FileOutputStream(file);
            try {
                tftp.open();
                try {
                    try {
                        try {
                            try {
                                tftp.receiveFile(remoteFileName, 1, output, host, 8192);
                                tftp.close();
                                output.close();
                            } catch (Throwable th) {
                                tftp.close();
                                try {
                                    output.close();
                                } catch (IOException e) {
                                    LogUtils.e("Error: error closing file.");
                                    LogUtils.e(e.getMessage());
                                }
                                throw th;
                            }
                        } catch (IOException e2) {
                            LogUtils.e("Error: I/O exception occurred while receiving file.");
                            LogUtils.e(e2.getMessage());
                            tftp.close();
                            output.close();
                        }
                    } catch (UnknownHostException e3) {
                        LogUtils.e("Error: could not resolve hostname.");
                        LogUtils.e(e3.getMessage());
                        tftp.close();
                        output.close();
                    }
                } catch (IOException e4) {
                    LogUtils.e("Error: error closing file.");
                    LogUtils.e(e4.getMessage());
                }
            } catch (SocketException e5) {
                LogUtils.e("Error: could not open local UDP socket.");
                LogUtils.e(e5.getMessage());
            }
        } catch (IOException e6) {
            tftp.close();
            LogUtils.e("Error: could not open local file for writing.");
            LogUtils.e(e6.getMessage());
        }
    }
}
