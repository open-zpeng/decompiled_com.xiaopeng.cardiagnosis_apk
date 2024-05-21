package com.xiaopeng.commonfunc.utils;

import android.text.TextUtils;
import com.xiaopeng.commonfunc.Constant;
import com.xiaopeng.lib.utils.LogUtils;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.file.Files;
import java.nio.file.OpenOption;
import java.nio.file.Path;
import java.nio.file.Paths;
import org.apache.commons.net.ftp.FTPClient;
import org.apache.commons.net.ftp.FTPClientConfig;
import org.apache.commons.net.ftp.FTPFile;
/* loaded from: classes4.dex */
public class FtpSender {
    private static final String TAG = "FtpSender";
    private static final int TIMEOUT = 60000;

    public static boolean sendFile(String localFileName, String server, int port, String username, String password, String ftppath) {
        boolean sendRet = false;
        FTPClient ftpClient = null;
        try {
        } catch (IOException e) {
            e.printStackTrace();
        }
        try {
            try {
                ftpClient = getFTPClient(server, port, username, password);
            } catch (Exception e2) {
                e2.printStackTrace();
                if (ftpClient != null && ftpClient.isConnected()) {
                    ftpClient.disconnect();
                }
            }
            if (ftpClient == null) {
                LogUtils.e(TAG, "getFTPClient fail........ ");
                return false;
            }
            ftpClient.setFileType(2);
            sendRet = upload(ftpClient, localFileName, ftppath);
            ftpClient.logout();
            if (ftpClient.isConnected()) {
                ftpClient.disconnect();
            }
            return sendRet;
        } finally {
            if (ftpClient != null && ftpClient.isConnected()) {
                try {
                    ftpClient.disconnect();
                } catch (IOException e3) {
                    e3.printStackTrace();
                }
            }
        }
    }

    public static boolean sendFile(String[] files, String server, int port, String username, String password, String ftppath) {
        int i;
        boolean sendRet = true;
        FTPClient ftpClient = null;
        try {
            try {
                try {
                    ftpClient = getFTPClient(server, port, username, password);
                } catch (Exception e) {
                    e.printStackTrace();
                    if (ftpClient != null && ftpClient.isConnected()) {
                        ftpClient.disconnect();
                    }
                }
            } catch (IOException e2) {
                e2.printStackTrace();
            }
            if (ftpClient == null) {
                LogUtils.e(TAG, "getFTPClient fail........ ");
                return false;
            }
            ftpClient.setFileType(2);
            for (String file : files) {
                if (!upload(ftpClient, file, ftppath)) {
                    sendRet = false;
                }
            }
            ftpClient.logout();
            if (ftpClient.isConnected()) {
                ftpClient.disconnect();
            }
            return sendRet;
        } finally {
            if (ftpClient != null && ftpClient.isConnected()) {
                try {
                    ftpClient.disconnect();
                } catch (IOException e3) {
                    e3.printStackTrace();
                }
            }
        }
    }

    public static boolean sendFiles(String localFolder, String server, int port, String username, String password, String ftppath, String[] excludeList) {
        boolean sendRet = false;
        FTPClient ftpClient = null;
        try {
            try {
                try {
                    ftpClient = getFTPClient(server, port, username, password);
                } finally {
                    if (ftpClient != null && ftpClient.isConnected()) {
                        try {
                            ftpClient.disconnect();
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                }
            } catch (Exception e2) {
                e2.printStackTrace();
                if (ftpClient != null && ftpClient.isConnected()) {
                    ftpClient.disconnect();
                }
            }
        } catch (IOException e3) {
            e3.printStackTrace();
        }
        if (ftpClient == null) {
            LogUtils.e(TAG, "getFTPClient fail........ ");
            return false;
        }
        ftpClient.setFileType(2);
        delDir(ftpClient, ftppath);
        sendRet = refreshFileList(localFolder, ftppath, ftpClient, localFolder, excludeList);
        ftpClient.logout();
        if (ftpClient.isConnected()) {
            ftpClient.disconnect();
        }
        return sendRet;
    }

    private static boolean download(FTPClient ftpClient, String source, String dest) {
        boolean flag = false;
        try {
            OutputStream outputStream = Files.newOutputStream(Paths.get(dest, new String[0]), new OpenOption[0]);
            flag = ftpClient.retrieveFile(source, outputStream);
            if (outputStream != null) {
                $closeResource(null, outputStream);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return flag;
    }

    private static /* synthetic */ void $closeResource(Throwable x0, AutoCloseable x1) {
        if (x0 == null) {
            x1.close();
            return;
        }
        try {
            x1.close();
        } catch (Throwable th) {
            x0.addSuppressed(th);
        }
    }

    private static boolean upload(FTPClient ftpClient, String source, String dest) {
        boolean flag = false;
        Path path = Paths.get(source, new String[0]);
        try {
            InputStream inputStream = Files.newInputStream(path, new OpenOption[0]);
            ftpClient.changeWorkingDirectory(dest);
            LogUtils.d(TAG, "storeFile: " + path.getFileName().toString());
            flag = ftpClient.storeFile(path.getFileName().toString(), inputStream);
            LogUtils.d(TAG, "storeFile flag: " + flag);
            if (inputStream != null) {
                $closeResource(null, inputStream);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return flag;
    }

    private static FTPClient getFTPClient(String server, int port, String username, String password) throws IOException {
        FTPClient ftpClient = new FTPClient();
        ftpClient.setConnectTimeout(60000);
        ftpClient.setDataTimeout(60000);
        FTPClientConfig config = new FTPClientConfig();
        config.setServerTimeZoneId("Asia/Shanghai");
        ftpClient.configure(config);
        if (port != -1) {
            ftpClient.connect(server, port);
        } else {
            ftpClient.connect(server);
        }
        ftpClient.login(username, password);
        ftpClient.setRemoteVerificationEnabled(false);
        return ftpClient;
    }

    private static boolean refreshFileList(String filePath, String remotePath, FTPClient ftpClient, String prefix, String[] excludeList) {
        boolean res = true;
        try {
            File dir = new File(filePath);
            File[] files = dir.listFiles();
            if (files == null) {
                return false;
            }
            for (int i = 0; i < files.length; i++) {
                if (files[i].isDirectory()) {
                    if (!refreshFileList(files[i].getAbsolutePath(), remotePath, ftpClient, prefix, excludeList)) {
                        res = false;
                    }
                } else {
                    String sourceFile = files[i].getPath();
                    if (!DataHelp.containString(excludeList, sourceFile)) {
                        String excessivePath = sourceFile.substring(prefix.length());
                        String remotePathCurrent = remotePath + excessivePath.substring(0, excessivePath.indexOf(files[i].getName()));
                        LogUtils.d(TAG, "sourceFile : " + sourceFile + ", excessivePath: " + excessivePath + ", remotePathCurrent: " + remotePathCurrent + ", prefix : " + prefix);
                        if (!process(sourceFile, remotePathCurrent, ftpClient)) {
                            res = false;
                        }
                    }
                }
            }
            return res;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    private static boolean process(String sourceFile, String remotePath, FTPClient ftpClient) throws Exception {
        File file = new File(sourceFile);
        if (TextUtils.isEmpty(remotePath)) {
            remotePath = file.getParent();
        }
        if (!file.exists()) {
            throw new IOException("file not exist: " + sourceFile);
        }
        FileInputStream is = new FileInputStream(file);
        changeMakeWorkingDir(remotePath, ftpClient);
        boolean res = ftpClient.storeFile(file.getName(), is);
        is.close();
        LogUtils.d(TAG, "FTP[" + file.getName() + "]upload result : " + res);
        return res;
    }

    private static void changeMakeWorkingDir(String path, FTPClient ftpClient) throws IOException {
        String[] dirs = path.split(File.separator);
        ftpClient.changeWorkingDirectory(File.separator);
        for (String dir : dirs) {
            if (!TextUtils.isEmpty(dir)) {
                ftpClient.makeDirectory(dir);
            }
            ftpClient.changeWorkingDirectory(dir);
        }
    }

    public static boolean delDir(FTPClient client, String dirPath) {
        try {
            FTPFile[] dirs = client.listFiles(dirPath);
            for (FTPFile ftpFile : dirs) {
                String name = ftpFile.getName();
                String childPath = dirPath + File.separator + name;
                if (ftpFile.isDirectory()) {
                    if (!name.equals(Constant.DOT_STRING) && !name.equals("..")) {
                        delDir(client, childPath);
                    }
                } else {
                    try {
                        client.deleteFile(childPath);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }
            try {
                return client.removeDirectory(dirPath);
            } catch (IOException e2) {
                LogUtils.e(TAG, "listFiles fail : " + e2);
                return false;
            }
        } catch (IOException e3) {
            LogUtils.e(TAG, "listFiles fail : " + e3);
            return false;
        }
    }
}
