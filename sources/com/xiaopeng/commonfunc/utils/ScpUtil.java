package com.xiaopeng.commonfunc.utils;

import android.text.TextUtils;
import com.xiaopeng.commonfunc.Constant;
import com.xiaopeng.lib.utils.LogUtils;
import com.xiaopeng.xmlconfig.Support;
/* loaded from: classes4.dex */
public class ScpUtil {
    private static final String CMD_FILE_SYNC = "test_remote_send DLT_SYNC";
    private static final String CMD_MODEM_LOG_START = "xp_modem_manager_test_1 -m";
    private static final String CMD_MODEM_LOG_STOP = "xp_modem_manager_test_1 -M";
    private static final String CMD_NOFITY_ROUTE_STATE = "touch /tmp/noSecurity";
    private static final String CMD_SSH_ICM = "/system/bin/ssh -v -o StrictHostKeyChecking=no root@172.20.1.40";
    private static final String CMD_SSH_QNX = "/system/bin/ssh -v -o StrictHostKeyChecking=no root@172.20.1.40";
    private static final String CMD_SSH_TBOX = "/system/bin/ssh -v -o StrictHostKeyChecking=no root@192.168.225.1";
    public static final String FILE_SCP_PUBKEY = "/data/etc/ssh/id_rsa";
    public static final String HOST_AVM = "172.20.1.57";
    public static final String HOST_ETH_TBOX = "172.20.1.44";
    public static final String HOST_ICM = "172.20.1.40";
    public static final String HOST_NCM_ICM = "[fe80::22ff:fe44:6688%xpusb0]";
    public static final String HOST_QNX = "172.20.1.40";
    public static final String HOST_TBOX = Support.Case.getString(Support.Case.TBOX_CAN_DATA_SERVER_IP);
    private static final String PATH_SCP_BIN = "/system/bin/scp";
    private static final String PATH_SSH_BIN = "/system/bin/ssh";
    private static final String QNX_VAR_FOLDER = "/var/";
    public static final String SCP_CMD_SUCCESS_RESULT = "Exit status 0";
    private static final String SHELL_COMMAND_PS_GREP = "ps -elf | grep ";
    private static final String SHELL_COMMAND_RM = "rm -f ";
    private static final String SHELL_COMPRESS_GZ = "tar -cvzf ";
    private static final String SHELL_COMPRESS_TAR = "tar -cvf ";
    public static final String SSH_AVM_USER = "xp";
    public static final String SSH_ROOT_USER = "root";
    public static final String SSH_XP_USER = "xptest";
    private static final String TAG = "ScpUtil";

    public static String genScpToServerCmd(String user, String hostIp, String srcPath, String destPath, String publicKey) {
        StringBuilder builder = new StringBuilder(PATH_SCP_BIN);
        builder.append(" -v -o StrictHostKeyChecking=no ");
        if (FileUtil.isExistFilePath(publicKey)) {
            builder.append(" -i ");
            builder.append(publicKey);
            builder.append(Constant.SPACE_STRING);
        }
        builder.append(srcPath);
        builder.append(Constant.SPACE_STRING);
        builder.append(user);
        builder.append(Constant.AT_STRING);
        builder.append(hostIp);
        builder.append(Constant.COLON_STRING);
        builder.append(destPath);
        builder.append(Constant.SPACE_STRING);
        return builder.toString();
    }

    public static String genScpFromServerCmd(String user, String hostIp, String[] srcLogPath, String destLogPath, String publicKey) {
        if (srcLogPath.length <= 0 || TextUtils.isEmpty(destLogPath)) {
            LogUtils.e(TAG, "srcLogPath is empty");
            return null;
        }
        StringBuilder builder = new StringBuilder(PATH_SCP_BIN);
        builder.append(" -v -o StrictHostKeyChecking=no -r ");
        if (FileUtil.isExistFilePath(publicKey)) {
            builder.append(" -i ");
            builder.append(publicKey);
            builder.append(Constant.SPACE_STRING);
        }
        for (String srcLog : srcLogPath) {
            builder.append(user);
            builder.append(Constant.AT_STRING);
            builder.append(hostIp);
            builder.append(Constant.COLON_STRING);
            builder.append(srcLog);
            builder.append(Constant.SPACE_STRING);
        }
        builder.append(destLogPath);
        return builder.toString();
    }

    public static String genScpFromServerCmd(String user, String hostIp, String srcLogPath, String destLogPath, String publicKey) {
        if (TextUtils.isEmpty(srcLogPath) || TextUtils.isEmpty(destLogPath)) {
            LogUtils.e(TAG, "path is empty");
            return null;
        }
        StringBuilder builder = new StringBuilder(PATH_SCP_BIN);
        builder.append(" -v -o StrictHostKeyChecking=no -r ");
        if (FileUtil.isExistFilePath(publicKey)) {
            builder.append(" -i ");
            builder.append(publicKey);
            builder.append(Constant.SPACE_STRING);
        }
        builder.append(user);
        builder.append(Constant.AT_STRING);
        builder.append(hostIp);
        builder.append(Constant.COLON_STRING);
        builder.append(srcLogPath);
        builder.append(Constant.SPACE_STRING);
        builder.append(destLogPath);
        return builder.toString();
    }

    public static String genQnxMkDir(String folder) {
        return "/system/bin/ssh -v -o StrictHostKeyChecking=no root@172.20.1.40" + Constant.SPACE_STRING + Constant.DOUBLE_QUOTA + "mkdir " + folder + Constant.DOUBLE_QUOTA;
    }

    public static String genQnxRmDir(String folder) {
        return "/system/bin/ssh -v -o StrictHostKeyChecking=no root@172.20.1.40" + Constant.SPACE_STRING + Constant.DOUBLE_QUOTA + "rm -rf " + folder + Constant.DOUBLE_QUOTA;
    }

    public static String genQnxDf() {
        return "/system/bin/ssh -v -o StrictHostKeyChecking=no root@172.20.1.40" + Constant.SPACE_STRING + Constant.DOUBLE_QUOTA + AfterSalesHelper.REPAIRMODE_ACTION_DF + Constant.DOUBLE_QUOTA;
    }

    public static String getQnxVarRestRoom() {
        String[] buff = ProcessUtil.execReturnLine(genQnxDf(), QNX_VAR_FOLDER);
        if (buff == null || buff.length <= 3) {
            return null;
        }
        String res = buff[3];
        return res;
    }

    public static String genTboxDltSync(String user, String hostIp, String publicKey) {
        return composeSshCommand(user, hostIp, CMD_FILE_SYNC, publicKey);
    }

    public static String genStartModemLogging(String user, String hostIp, String publicKey) {
        return composeSshCommand(user, hostIp, CMD_MODEM_LOG_START, publicKey);
    }

    public static String genStopModemLogging(String user, String hostIp, String publicKey) {
        return composeSshCommand(user, hostIp, CMD_MODEM_LOG_STOP, publicKey);
    }

    public static String genNotifyTboxRouteReady(String user, String hostIp, String publicKey) {
        return composeSshCommand(user, hostIp, CMD_NOFITY_ROUTE_STATE, publicKey);
    }

    public static String genCheckModemStatus(String user, String hostIp, String publicKey) {
        return composeSshCommand(user, hostIp, "ps -elf | grep diag_mdlog", publicKey);
    }

    public static String genCompressFile(String user, String hostIp, String publicKey, String destPath, String[] srcPath) {
        if (srcPath == null || srcPath.length <= 0 || TextUtils.isEmpty(destPath)) {
            LogUtils.e(TAG, "srcPath or destPath is empty");
            return null;
        }
        StringBuilder builder = new StringBuilder();
        for (String str : srcPath) {
            builder.append(Constant.SPACE_STRING);
            builder.append(str);
        }
        return composeSshCommand(user, hostIp, SHELL_COMPRESS_GZ + destPath + builder.toString(), publicKey);
    }

    public static String genTarFile(String user, String hostIp, String publicKey, String destPath, String[] srcPath) {
        if (srcPath == null || srcPath.length <= 0 || TextUtils.isEmpty(destPath)) {
            LogUtils.e(TAG, "srcPath or destPath is empty");
            return null;
        }
        StringBuilder builder = new StringBuilder();
        for (String str : srcPath) {
            builder.append(Constant.SPACE_STRING);
            builder.append(str);
        }
        return composeSshCommand(user, hostIp, SHELL_COMPRESS_TAR + destPath + builder.toString(), publicKey);
    }

    public static String genRemoveFile(String user, String hostIp, String destPath, String publicKey) {
        return composeSshCommand(user, hostIp, SHELL_COMMAND_RM + destPath, publicKey);
    }

    private static String composeSshCommand(String user, String hostIp, String remoteCmd, String publicKey) {
        StringBuilder builder = new StringBuilder(PATH_SSH_BIN);
        builder.append(" -v -o StrictHostKeyChecking=no ");
        if (FileUtil.isExistFilePath(publicKey)) {
            builder.append(" -i ");
            builder.append(publicKey);
            builder.append(Constant.SPACE_STRING);
        }
        builder.append(user);
        builder.append(Constant.AT_STRING);
        builder.append(hostIp);
        builder.append(Constant.SPACE_STRING);
        builder.append(Constant.DOUBLE_QUOTA);
        builder.append(remoteCmd);
        builder.append(Constant.DOUBLE_QUOTA);
        return builder.toString();
    }
}
