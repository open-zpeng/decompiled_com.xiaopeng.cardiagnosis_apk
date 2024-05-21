package com.xiaopeng.commonfunc.model.usersettings;

import android.content.Context;
import android.debug.AdbManager;
import android.os.SystemProperties;
import android.util.Log;
import com.xiaopeng.commonfunc.Constant;
import com.xiaopeng.commonfunc.system.runnable.Sleep;
import com.xiaopeng.commonfunc.utils.ProcessUtil;
import com.xiaopeng.commonfunc.utils.SystemPropertyUtil;
import com.xiaopeng.lib.utils.LogUtils;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.Socket;
import org.apache.commons.net.tftp.TFTP;
/* loaded from: classes4.dex */
public class UserSettingsModel implements IUserSettingsModel {
    private static final String CATCH_LOG_D = "catch_log_d";
    private static final String SYNC = "sync";
    private static final String TAG = "UserSettingsModel";
    private static Context mContext;

    private UserSettingsModel() {
    }

    public static UserSettingsModel getInstance(Context context) {
        mContext = context.getApplicationContext();
        return UserSettingsSingle.INSTANCE;
    }

    @Override // com.xiaopeng.commonfunc.model.usersettings.IUserSettingsModel
    public boolean isUdiskReadOnly() {
        return SystemPropertyUtil.isUdiskReadOnly();
    }

    @Override // com.xiaopeng.commonfunc.model.usersettings.IUserSettingsModel
    public void setUdiskReadOnly(boolean enable) {
        SystemPropertyUtil.setUdiskReadOnly(enable);
        if (!isDebugOn()) {
            setDebugStatus(true);
            Sleep.sleep(3000L);
            setDebugStatus(false);
            Sleep.sleep(3000L);
        }
    }

    @Override // com.xiaopeng.commonfunc.model.usersettings.IUserSettingsModel
    public boolean isDebugOn() {
        AdbManager adbManager = (AdbManager) mContext.getSystemService("adb");
        try {
            boolean isDebugOn = adbManager.isDebugEnabled();
            return isDebugOn;
        } catch (Exception e) {
            LogUtils.w(TAG, "isDebugOn: " + e.getMessage());
            return false;
        }
    }

    @Override // com.xiaopeng.commonfunc.model.usersettings.IUserSettingsModel
    public void setDebugStatus(boolean status) {
        AdbManager adbManager = (AdbManager) mContext.getSystemService("adb");
        try {
            adbManager.setDebugStatus(status);
            ProcessUtil.execCommand("sync");
        } catch (Exception e) {
            LogUtils.w(TAG, "setDebugStatus: " + e.getMessage());
        }
    }

    @Override // com.xiaopeng.commonfunc.model.usersettings.IUserSettingsModel
    public void setConsoleStatus(boolean status) {
        if (status) {
            SystemPropertyUtil.startConsole();
        } else {
            SystemPropertyUtil.stopConsole();
        }
    }

    @Override // com.xiaopeng.commonfunc.model.usersettings.IUserSettingsModel
    public void setAutoCatchLog(boolean auto) {
        LogUtils.i(TAG, "setAutoCatchLog " + auto);
        SystemPropertyUtil.setGrabLogOnOff(auto);
        if (auto) {
            SystemPropertyUtil.restartService(CATCH_LOG_D);
        } else {
            SystemPropertyUtil.stopService(CATCH_LOG_D);
        }
    }

    @Override // com.xiaopeng.commonfunc.model.usersettings.IUserSettingsModel
    public void setShowDialog(boolean show) {
        LogUtils.i(TAG, "setShowDialog " + show);
        SystemPropertyUtil.setShowDialog(show);
    }

    @Override // com.xiaopeng.commonfunc.model.usersettings.IUserSettingsModel
    public void grabUploadCan(String type) {
        SocketRunnable runnable = new SocketRunnable(type);
        new Thread(runnable, "UploadCanThread").start();
    }

    /* loaded from: classes4.dex */
    private static class UserSettingsSingle {
        private static final UserSettingsModel INSTANCE = new UserSettingsModel();

        private UserSettingsSingle() {
        }
    }

    /* loaded from: classes4.dex */
    private class SocketRunnable implements Runnable {
        private static final String CAN_SEND_HEAD = "CAN send ";
        private static final int DEFAULT_LOCAL_PORT = 18881;
        private static final String LOCAL_ADDRESS = "127.0.0.1";
        private final int mPort = SystemProperties.getInt("sys.mcudebug.port", (int) DEFAULT_LOCAL_PORT);
        private final String mType;

        public SocketRunnable(String type) {
            this.mType = type;
        }

        @Override // java.lang.Runnable
        public void run() {
            Socket socket = null;
            BufferedWriter writer = null;
            try {
                try {
                    try {
                        InetAddress inetAddress = InetAddress.getByName(LOCAL_ADDRESS);
                        socket = new Socket();
                        socket.connect(new InetSocketAddress(inetAddress, this.mPort), TFTP.DEFAULT_TIMEOUT);
                        writer = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));
                        writer.write(CAN_SEND_HEAD + this.mType + Constant.DOT_STRING);
                        writer.flush();
                        try {
                            writer.close();
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                        socket.close();
                    } catch (Throwable th) {
                        if (writer != null) {
                            try {
                                writer.close();
                            } catch (IOException e2) {
                                e2.printStackTrace();
                            }
                        }
                        if (socket != null) {
                            try {
                                socket.close();
                            } catch (IOException e3) {
                                Log.e(UserSettingsModel.TAG, e3.getMessage());
                            }
                        }
                        throw th;
                    }
                } catch (Exception e4) {
                    Log.e(UserSettingsModel.TAG, e4.getMessage());
                    if (writer != null) {
                        try {
                            writer.close();
                        } catch (IOException e5) {
                            e5.printStackTrace();
                        }
                    }
                    if (socket != null) {
                        socket.close();
                    }
                }
            } catch (IOException e6) {
                Log.e(UserSettingsModel.TAG, e6.getMessage());
            }
        }
    }
}
