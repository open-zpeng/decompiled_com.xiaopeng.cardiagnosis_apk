package com.xiaopeng.logictree;

import android.app.Application;
import android.content.Context;
import android.support.annotation.RequiresApi;
import android.text.TextUtils;
import com.google.gson.Gson;
import com.google.gson.stream.JsonReader;
import com.xiaopeng.aftersales.manager.AfterSalesManager;
import com.xiaopeng.aftersales.manager.LogicTreeUpgrader;
import com.xiaopeng.commonfunc.Constant;
import com.xiaopeng.commonfunc.bean.event.CommonEvent;
import com.xiaopeng.commonfunc.utils.AfterSalesHelper;
import com.xiaopeng.commonfunc.utils.DataHelp;
import com.xiaopeng.commonfunc.utils.EventBusUtil;
import com.xiaopeng.commonfunc.utils.FileUploadHelper;
import com.xiaopeng.commonfunc.utils.FileUtil;
import com.xiaopeng.commonfunc.utils.SystemPropertyUtil;
import com.xiaopeng.lib.framework.module.Module;
import com.xiaopeng.lib.framework.moduleinterface.netchannelmodule.http.IHttp;
import com.xiaopeng.lib.framework.moduleinterface.netchannelmodule.http.IResponse;
import com.xiaopeng.lib.framework.moduleinterface.netchannelmodule.remotestorage.Callback;
import com.xiaopeng.lib.framework.moduleinterface.netchannelmodule.remotestorage.IRemoteStorage;
import com.xiaopeng.lib.framework.moduleinterface.netchannelmodule.remotestorage.StorageException;
import com.xiaopeng.lib.framework.netchannelmodule.NetworkChannelsEntry;
import com.xiaopeng.lib.http.server.ServerBean;
import com.xiaopeng.lib.utils.LogUtils;
import com.xiaopeng.lib.utils.ThreadUtils;
import com.xiaopeng.lib.utils.config.CommonConfig;
import com.xiaopeng.logictree.bean.LogicTreeVersion;
import com.xiaopeng.logictree.handler.BluetoothLogicAction;
import com.xiaopeng.logictree.handler.DiagnosisCode;
import com.xiaopeng.logictree.handler.DidInfo;
import com.xiaopeng.logictree.handler.DisplayHandleResult;
import com.xiaopeng.logictree.handler.GpsInfo;
import com.xiaopeng.logictree.handler.InterActive;
import com.xiaopeng.logictree.handler.LogicActionHandler;
import com.xiaopeng.logictree.handler.NetworkInfo;
import com.xiaopeng.logictree.handler.Security;
import com.xiaopeng.logictree.handler.ShellCmd;
import com.xiaopeng.logictree.handler.UsbInfo;
import com.xiaopeng.xmlconfig.Support;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;
/* loaded from: classes5.dex */
public class LogicTreeParser {
    private static final String ACTION_BLUETOOTH = "bluetooth";
    private static final String ACTION_DIAGNOSIS_CODE = "diagnosiscode";
    private static final String ACTION_DID_INFO = "didinfo";
    private static final String ACTION_DISPLAY_RESULT = "displayresult";
    private static final String ACTION_GPS_INFO = "gpsinfo";
    private static final String ACTION_INTERACTIVE = "interactive";
    private static final String ACTION_NETWORK_INFO = "networkinfo";
    private static final String ACTION_SECURITY = "security";
    private static final String ACTION_SHELL_CMD = "shellcmd";
    private static final String ACTION_USB_INFO = "usbinfo";
    private static final String LOGICTREE_FOLDER = "/cache/aftersales/logictree";
    private static final String LOGICTREE_TEMP_FILE = "/cache/aftersales/logictree/temp_logictree.zip";
    public static final String TAG = "LogicTreeParser";
    private static final String URL_LOGICTREE_CLOUD_VERSION = CommonConfig.HTTP_HOST + "/flow/cv2/vehicle/cdu/cdu_diagnosis_logic_tree";
    private AfterSalesManager mAfterSalesManager;
    private String mCloudVersion;
    private Context mContext;
    private String mDownloadPath;
    private String mLogicTreePath;
    private volatile LogicTreeInfoList mLogicTreeInfoList = null;
    private LogicTreeUpgrader mLogicTreeUpgrader = new LogicTreeUpgrader() { // from class: com.xiaopeng.logictree.-$$Lambda$LogicTreeParser$2hNoUrEx5I_SiqT6ffxN2qHjMvY
        public final void onUpgradeStatus(boolean z) {
            EventBus.getDefault().post(new CommonEvent(status ? 10008 : 10007, null));
        }
    };
    private final HashMap<String, LogicActionHandler> mLogicActionHandlers = new HashMap<>();
    private final IssueInfo mIssueInfo = IssueInfo.getInstance();

    public boolean getCloudVer() {
        boolean res = true;
        this.mCloudVersion = null;
        this.mDownloadPath = null;
        Map<String, String> param = new HashMap<>(1);
        param.put(Constant.HTTP_KEY_VIN, SystemPropertyUtil.getVIN());
        try {
            IHttp http = (IHttp) Module.get(NetworkChannelsEntry.class).get(IHttp.class);
            IResponse response = http.bizHelper().post(URL_LOGICTREE_CLOUD_VERSION, new Gson().toJson(param)).needAuthorizationInfo().enableSecurityEncoding().build().execute();
            ServerBean serverBean = DataHelp.getServerBean(response);
            LogUtils.d(TAG, "serverBean code : " + serverBean.getCode() + "  data : " + serverBean.getData() + " msg : " + serverBean.getMsg());
            if (serverBean.getCode() != 200 && TextUtils.isEmpty(serverBean.getData())) {
                res = false;
                this.mCloudVersion = null;
                this.mDownloadPath = null;
            } else {
                LogicTreeVersion logicTreeVersion = (LogicTreeVersion) new Gson().fromJson(serverBean.getData(), (Class<Object>) LogicTreeVersion.class);
                this.mCloudVersion = logicTreeVersion.getVersion();
                this.mDownloadPath = logicTreeVersion.getUrl();
            }
            LogUtils.i(TAG, "getCloudVer version : " + this.mCloudVersion + " path : " + this.mDownloadPath);
            return res;
        } catch (Exception ex) {
            ex.printStackTrace();
            return false;
        }
    }

    public void setCloudVersion(String url, String version) {
        this.mCloudVersion = version;
        this.mDownloadPath = url;
    }

    public void initLogicTreeList() {
        this.mLogicTreePath = LogicTreeHelper.DEFAULT_LOGIC_TREE_PATH;
        try {
            try {
                this.mLogicTreeInfoList = (LogicTreeInfoList) new Gson().fromJson(new JsonReader(new InputStreamReader(new FileInputStream(LogicTreeHelper.DEFAULT_LOGIC_TREE_PATH + File.separator + LogicTreeHelper.DIAGNOSIS_LOGIC_TREE_LIST))), LogicTreeInfoList.class);
            } catch (Exception e) {
                e.printStackTrace();
                this.mLogicTreeInfoList = null;
            }
            String upgradeLogicTreePath = LogicTreeHelper.LOGICTREE_UPGRADE_FOLDER + File.separator + LogicTreeHelper.DIAGNOSIS_LOGIC_TREE_LIST;
            if (FileUtil.isExistFilePath(upgradeLogicTreePath)) {
                LogicTreeInfoList logicTreeInfoList = (LogicTreeInfoList) new Gson().fromJson(new JsonReader(new InputStreamReader(new FileInputStream(upgradeLogicTreePath))), LogicTreeInfoList.class);
                if (this.mLogicTreeInfoList != null && (logicTreeInfoList == null || logicTreeInfoList.getVersion().compareToIgnoreCase(this.mLogicTreeInfoList.getVersion()) <= 0 || !Support.Feature.getString(Support.Feature.MODEL_NAME).equalsIgnoreCase(logicTreeInfoList.getProject()))) {
                    if (logicTreeInfoList == null) {
                        LogUtils.e(TAG, "logicTreeInfoList is null");
                    } else {
                        LogUtils.e(TAG, "logicTreeInfoList version[%s] project[%s]", logicTreeInfoList.getVersion(), logicTreeInfoList.getProject());
                    }
                }
                this.mLogicTreeInfoList = logicTreeInfoList;
                this.mLogicTreePath = LogicTreeHelper.LOGICTREE_UPGRADE_FOLDER;
            }
            LogUtils.i(TAG, this.mLogicTreeInfoList.toString());
        } catch (Exception e2) {
            e2.printStackTrace();
        }
        if (this.mIssueInfo.getEntry() == 1) {
            EventBus.getDefault().post(new CommonEvent(10009, null));
        } else {
            EventBus.getDefault().post(new CommonEvent(10004, null));
        }
    }

    public void upgradeLogicTreeViaCloud(Application application) {
        if (TextUtils.isEmpty(this.mDownloadPath)) {
            LogUtils.e(TAG, "upgradeLogicTreeViaCloud with empty downloadPath");
            EventBus.getDefault().post(new CommonEvent(10007, null));
            return;
        }
        IRemoteStorage storage = (IRemoteStorage) Module.get(NetworkChannelsEntry.class).get(IRemoteStorage.class);
        try {
            storage.initWithContext(application);
            storage.downloadWithPathAndCallback(FileUploadHelper.SECURITY_BUCKET_NAME, this.mDownloadPath, LOGICTREE_TEMP_FILE, new Callback() { // from class: com.xiaopeng.logictree.LogicTreeParser.1
                @Override // com.xiaopeng.lib.framework.moduleinterface.netchannelmodule.remotestorage.Callback
                public void onStart(String s, String s1) {
                    LogUtils.d(LogicTreeParser.TAG, "downloadWithPathAndCallback onStart s:" + s + ", s1:" + s1);
                }

                @Override // com.xiaopeng.lib.framework.moduleinterface.netchannelmodule.remotestorage.Callback
                public void onSuccess(String s, String s1) {
                    LogUtils.i(LogicTreeParser.TAG, "downloadWithPathAndCallback onSuccess s:" + s + ", s1:" + s1);
                    LogicTreeParser.this.upgradeLogicTree(LogicTreeParser.LOGICTREE_TEMP_FILE, false);
                }

                @Override // com.xiaopeng.lib.framework.moduleinterface.netchannelmodule.remotestorage.Callback
                public void onFailure(String s, String s1, StorageException e) {
                    LogUtils.e(LogicTreeParser.TAG, "downloadWithPathAndCallback onFailure s:" + s + ", s1:" + s1 + ", e:" + e.toString());
                    EventBus.getDefault().post(new CommonEvent(10007, null));
                }
            });
        } catch (Exception e) {
            e.printStackTrace();
            EventBus.getDefault().post(new CommonEvent(10007, null));
        }
    }

    public void upgradeLogicTree(final String zipFileOrFolder, final boolean isFolder) {
        ThreadUtils.execute(new Runnable() { // from class: com.xiaopeng.logictree.-$$Lambda$LogicTreeParser$RrMUbFvlVtr16nYzzMHW5spw6U8
            @Override // java.lang.Runnable
            public final void run() {
                LogicTreeParser.this.lambda$upgradeLogicTree$0$LogicTreeParser(isFolder, zipFileOrFolder);
            }
        });
    }

    public /* synthetic */ void lambda$upgradeLogicTree$0$LogicTreeParser(boolean isFolder, String zipFileOrFolder) {
        String logictreeZip;
        if (isFolder) {
            String logictreeZip2 = FileUtil.getFileNameWithSuffixNContains(zipFileOrFolder, ".zip", Support.Properties.get(Support.Properties.XML_MODEL));
            logictreeZip = zipFileOrFolder + File.separatorChar + logictreeZip2;
        } else {
            logictreeZip = zipFileOrFolder;
        }
        if (FileUtil.isExistFilePath(logictreeZip)) {
            FileUtil.unzipMultiFile(logictreeZip, "/cache/aftersales/logictree");
            if (!isFolder) {
                FileUtil.deleteFile(logictreeZip);
            }
            this.mAfterSalesManager.requestUpgradeLogicTree("/cache/aftersales/logictree");
            return;
        }
        EventBus.getDefault().post(new CommonEvent(10007, null));
    }

    public synchronized void registerLogicActionHandler(Application application) {
        LogUtils.i(TAG, "Register Logic Action handler");
        this.mContext = application.getApplicationContext();
        this.mAfterSalesManager = AfterSalesHelper.getAfterSalesManager();
        this.mLogicActionHandlers.put(ACTION_INTERACTIVE, new InterActive(application));
        this.mLogicActionHandlers.put(ACTION_SHELL_CMD, new ShellCmd(application));
        this.mLogicActionHandlers.put(ACTION_DIAGNOSIS_CODE, new DiagnosisCode(application));
        this.mLogicActionHandlers.put(ACTION_DISPLAY_RESULT, new DisplayHandleResult(application));
        this.mLogicActionHandlers.put(ACTION_SECURITY, new Security(application));
        this.mLogicActionHandlers.put(ACTION_NETWORK_INFO, new NetworkInfo(application));
        this.mLogicActionHandlers.put(ACTION_USB_INFO, new UsbInfo(application));
        this.mLogicActionHandlers.put(ACTION_DID_INFO, new DidInfo(application));
        this.mLogicActionHandlers.put(ACTION_BLUETOOTH, new BluetoothLogicAction(application));
        this.mLogicActionHandlers.put(ACTION_GPS_INFO, new GpsInfo(application));
        EventBusUtil.registerEventBus(this);
        this.mAfterSalesManager.addLogicTreeUpgrader(this.mLogicTreeUpgrader);
    }

    public synchronized void unregisterLogicActionHandler() {
        if (!this.mLogicActionHandlers.isEmpty()) {
            LogUtils.i(TAG, "unregister Logic Action handler");
            for (String key : this.mLogicActionHandlers.keySet()) {
                LogicActionHandler handler = this.mLogicActionHandlers.get(key);
                if (handler != null) {
                    handler.destroy();
                }
            }
            this.mLogicActionHandlers.clear();
        }
        this.mLogicTreeInfoList = null;
        this.mLogicTreePath = null;
        this.mDownloadPath = null;
        this.mCloudVersion = null;
        this.mAfterSalesManager.removeLogicTreeUpgrader(this.mLogicTreeUpgrader);
        EventBusUtil.unregisterEventBus(this);
    }

    public String getCloudVersion() {
        return this.mCloudVersion;
    }

    public LogicTreeInfoList getLogicTreeInfoList() {
        return this.mLogicTreeInfoList;
    }

    public boolean isDiagnosisFinish() {
        return this.mIssueInfo.getLogicTree() == null;
    }

    public void forceFinishDiagnosis() {
        this.mIssueInfo.setLogicTree(null);
    }

    public boolean isLogicTreeNeedUpdate() {
        boolean res = false;
        if (this.mCloudVersion != null) {
            res = this.mLogicTreeInfoList.getVersion().compareToIgnoreCase(this.mCloudVersion) < 0;
        }
        LogUtils.i(TAG, "local version[%s], cloud version[%s] isLogicTreeNeedUpdate[%s]", this.mLogicTreeInfoList.getVersion(), this.mCloudVersion, Boolean.valueOf(res));
        return res;
    }

    public LogicTreeInfo getLogicTreeInfoByName(String name) {
        LogicTreeInfo[] logicTreeList;
        if (this.mLogicTreeInfoList != null) {
            for (LogicTreeInfo info : this.mLogicTreeInfoList.getLogicTreeList()) {
                if (info.getName().equals(name)) {
                    return info;
                }
            }
            return null;
        }
        return null;
    }

    @Subscribe(threadMode = ThreadMode.BACKGROUND)
    @RequiresApi(api = 24)
    public void onEvent(LogicActionResult logicActionResult) {
        int index = -1;
        if (this.mIssueInfo.getLogicTree() != null && this.mIssueInfo.getLogicTree().getResult() != null && this.mIssueInfo.getLogicTree().getResult().length > 0) {
            if (logicActionResult.getResult() instanceof String) {
                String stringValue = ((String) logicActionResult.getResult()).toLowerCase();
                if (LogicTreeHelper.LOGIC_ACTION_RESPONSE_EXEC_NO_RESULT.equalsIgnoreCase(stringValue)) {
                    index = -2;
                } else {
                    for (int i = 0; i < this.mIssueInfo.getLogicTree().getResult().length; i++) {
                        int parseMethod = this.mIssueInfo.getLogicTree().getParseMethod();
                        if (parseMethod != 15) {
                            if (parseMethod != 20) {
                                switch (parseMethod) {
                                    case 1:
                                        if (stringValue.equalsIgnoreCase(this.mIssueInfo.getLogicTree().getResult()[i])) {
                                            index = i;
                                            break;
                                        }
                                        break;
                                    case 2:
                                        index = i;
                                        String[] split = this.mIssueInfo.getLogicTree().getResult()[i].split(Constant.SEPARATOR_STRING);
                                        int length = split.length;
                                        int i2 = 0;
                                        while (true) {
                                            if (i2 < length) {
                                                String result = split[i2];
                                                if (!stringValue.equalsIgnoreCase(result)) {
                                                    i2++;
                                                } else {
                                                    index = -1;
                                                    break;
                                                }
                                            } else {
                                                break;
                                            }
                                        }
                                    case 3:
                                        index = i;
                                        String[] split2 = this.mIssueInfo.getLogicTree().getResult()[i].split(Constant.SEPARATOR_STRING);
                                        int length2 = split2.length;
                                        int i3 = 0;
                                        while (true) {
                                            if (i3 < length2) {
                                                String result2 = split2[i3];
                                                if (stringValue.contains(result2)) {
                                                    i3++;
                                                } else {
                                                    index = -1;
                                                    break;
                                                }
                                            } else {
                                                break;
                                            }
                                        }
                                    case 4:
                                        index = i;
                                        String[] split3 = this.mIssueInfo.getLogicTree().getResult()[i].split(Constant.SEPARATOR_STRING);
                                        int length3 = split3.length;
                                        int i4 = 0;
                                        while (true) {
                                            if (i4 < length3) {
                                                String result3 = split3[i4];
                                                if (!stringValue.contains(result3)) {
                                                    i4++;
                                                } else {
                                                    index = -1;
                                                    break;
                                                }
                                            } else {
                                                break;
                                            }
                                        }
                                    case 5:
                                        String[] split4 = this.mIssueInfo.getLogicTree().getResult()[i].split(Constant.SEPARATOR_STRING);
                                        int length4 = split4.length;
                                        int i5 = 0;
                                        while (true) {
                                            if (i5 < length4) {
                                                String result4 = split4[i5];
                                                if (!stringValue.equalsIgnoreCase(result4)) {
                                                    i5++;
                                                } else {
                                                    index = i;
                                                    break;
                                                }
                                            } else {
                                                break;
                                            }
                                        }
                                    case 6:
                                        String[] split5 = this.mIssueInfo.getLogicTree().getResult()[i].split(Constant.SEPARATOR_STRING);
                                        int length5 = split5.length;
                                        int i6 = 0;
                                        while (true) {
                                            if (i6 < length5) {
                                                String result5 = split5[i6];
                                                if (!stringValue.contains(result5)) {
                                                    i6++;
                                                } else {
                                                    index = i;
                                                    break;
                                                }
                                            } else {
                                                break;
                                            }
                                        }
                                    case 7:
                                        String[] split6 = this.mIssueInfo.getLogicTree().getResult()[i].split(Constant.SEPARATOR_STRING);
                                        int length6 = split6.length;
                                        int i7 = 0;
                                        while (true) {
                                            if (i7 < length6) {
                                                String result6 = split6[i7];
                                                if (stringValue.contains(result6)) {
                                                    i7++;
                                                } else {
                                                    index = i;
                                                    break;
                                                }
                                            } else {
                                                break;
                                            }
                                        }
                                    case 8:
                                        if (stringValue.compareToIgnoreCase(this.mIssueInfo.getLogicTree().getResult()[i]) > 0) {
                                            index = i;
                                            break;
                                        }
                                        break;
                                    case 9:
                                        if (stringValue.compareToIgnoreCase(this.mIssueInfo.getLogicTree().getResult()[i]) >= 0) {
                                            index = i;
                                            break;
                                        }
                                        break;
                                }
                            } else {
                                String[] split7 = this.mIssueInfo.getLogicTree().getResult()[i].split(Constant.SEPARATOR_STRING);
                                int length7 = split7.length;
                                int i8 = 0;
                                while (true) {
                                    if (i8 < length7) {
                                        String result7 = split7[i8];
                                        if (!stringValue.contains(result7)) {
                                            i8++;
                                        } else {
                                            index = 0;
                                        }
                                    }
                                }
                            }
                        } else if (stringValue.matches(this.mIssueInfo.getLogicTree().getResult()[i])) {
                            index = i;
                        }
                        if (index != i) {
                        }
                    }
                }
            } else {
                int i9 = 7;
                int i10 = 6;
                if (logicActionResult.getResult() instanceof int[]) {
                    List<Integer> integerList = (List) Arrays.stream((int[]) logicActionResult.getResult()).boxed().collect(Collectors.toList());
                    int i11 = 0;
                    while (i11 < this.mIssueInfo.getLogicTree().getResult().length) {
                        int parseMethod2 = this.mIssueInfo.getLogicTree().getParseMethod();
                        if (parseMethod2 != 3) {
                            if (parseMethod2 != 4) {
                                if (parseMethod2 != i10) {
                                    if (parseMethod2 != i9) {
                                        if (parseMethod2 == 10) {
                                            index = i11;
                                            String[] results = this.mIssueInfo.getLogicTree().getResult()[i11].split(Constant.SEMICOLON_STRING);
                                            int temp = 0;
                                            while (true) {
                                                if (temp < results.length) {
                                                    String[] splits = results[temp].split(Constant.SEPARATOR_STRING);
                                                    int value1 = Integer.parseInt(splits[0]);
                                                    int value2 = Integer.parseInt(splits[1]);
                                                    if ((integerList.get(temp).intValue() & value1) == value2) {
                                                        temp++;
                                                    } else {
                                                        index = -1;
                                                    }
                                                }
                                            }
                                        } else {
                                            switch (parseMethod2) {
                                                case 17:
                                                    index = i11;
                                                    String[] results2 = this.mIssueInfo.getLogicTree().getResult()[i11].split(Constant.SEMICOLON_STRING);
                                                    int temp2 = 0;
                                                    while (true) {
                                                        if (temp2 < results2.length) {
                                                            String[] splits2 = results2[temp2].split(Constant.SEPARATOR_STRING);
                                                            int value12 = Integer.parseInt(splits2[0]);
                                                            int value22 = Integer.parseInt(splits2[1]);
                                                            if ((integerList.get(temp2).intValue() & value12) > value22) {
                                                                temp2++;
                                                            } else {
                                                                index = -1;
                                                                break;
                                                            }
                                                        } else {
                                                            break;
                                                        }
                                                    }
                                                case 18:
                                                    index = i11;
                                                    String[] results3 = this.mIssueInfo.getLogicTree().getResult()[i11].split(Constant.SEMICOLON_STRING);
                                                    int temp3 = 0;
                                                    while (true) {
                                                        if (temp3 < results3.length) {
                                                            String[] splits3 = results3[temp3].split(Constant.SEPARATOR_STRING);
                                                            int value13 = Integer.parseInt(splits3[0]);
                                                            int value23 = Integer.parseInt(splits3[1]);
                                                            if ((integerList.get(temp3).intValue() | value13) > value23) {
                                                                temp3++;
                                                            } else {
                                                                index = -1;
                                                                break;
                                                            }
                                                        } else {
                                                            break;
                                                        }
                                                    }
                                                case 19:
                                                    index = i11;
                                                    String[] results4 = this.mIssueInfo.getLogicTree().getResult()[i11].split(Constant.SEMICOLON_STRING);
                                                    int temp4 = 0;
                                                    while (true) {
                                                        if (temp4 < results4.length) {
                                                            String[] splits4 = results4[temp4].split(Constant.SEPARATOR_STRING);
                                                            int value14 = Integer.parseInt(splits4[0]);
                                                            int value24 = Integer.parseInt(splits4[1]);
                                                            if ((integerList.get(temp4).intValue() ^ value14) > value24) {
                                                                temp4++;
                                                            } else {
                                                                index = -1;
                                                                break;
                                                            }
                                                        } else {
                                                            break;
                                                        }
                                                    }
                                            }
                                        }
                                    } else {
                                        String[] split8 = this.mIssueInfo.getLogicTree().getResult()[i11].split(Constant.SEPARATOR_STRING);
                                        int length8 = split8.length;
                                        int i12 = 0;
                                        while (true) {
                                            if (i12 < length8) {
                                                String result8 = split8[i12];
                                                if (integerList.contains(Integer.valueOf(result8))) {
                                                    i12++;
                                                } else {
                                                    index = i11;
                                                }
                                            }
                                        }
                                    }
                                } else {
                                    String[] split9 = this.mIssueInfo.getLogicTree().getResult()[i11].split(Constant.SEPARATOR_STRING);
                                    int length9 = split9.length;
                                    int i13 = 0;
                                    while (true) {
                                        if (i13 < length9) {
                                            String result9 = split9[i13];
                                            if (!integerList.contains(Integer.valueOf(result9))) {
                                                i13++;
                                            } else {
                                                index = i11;
                                            }
                                        }
                                    }
                                }
                            } else {
                                index = i11;
                                String[] split10 = this.mIssueInfo.getLogicTree().getResult()[i11].split(Constant.SEPARATOR_STRING);
                                int length10 = split10.length;
                                int i14 = 0;
                                while (true) {
                                    if (i14 < length10) {
                                        String result10 = split10[i14];
                                        if (!integerList.contains(Integer.valueOf(result10))) {
                                            i14++;
                                        } else {
                                            index = -1;
                                        }
                                    }
                                }
                            }
                        } else {
                            index = i11;
                            String[] split11 = this.mIssueInfo.getLogicTree().getResult()[i11].split(Constant.SEPARATOR_STRING);
                            int length11 = split11.length;
                            int i15 = 0;
                            while (true) {
                                if (i15 < length11) {
                                    String result11 = split11[i15];
                                    if (integerList.contains(Integer.valueOf(result11))) {
                                        i15++;
                                    } else {
                                        index = -1;
                                    }
                                }
                            }
                        }
                        if (index != i11) {
                            i11++;
                            i9 = 7;
                            i10 = 6;
                        }
                    }
                } else if (logicActionResult.getResult() instanceof Integer) {
                    int value = ((Integer) logicActionResult.getResult()).intValue();
                    for (int i16 = 0; i16 < this.mIssueInfo.getLogicTree().getResult().length; i16++) {
                        int parseMethod3 = this.mIssueInfo.getLogicTree().getParseMethod();
                        if (parseMethod3 != 1) {
                            if (parseMethod3 != 16) {
                                switch (parseMethod3) {
                                    case 8:
                                        if (Integer.parseInt(this.mIssueInfo.getLogicTree().getResult()[i16]) < value) {
                                            index = i16;
                                            break;
                                        } else {
                                            continue;
                                        }
                                    case 9:
                                        if (Integer.parseInt(this.mIssueInfo.getLogicTree().getResult()[i16]) <= value) {
                                            index = i16;
                                            break;
                                        } else {
                                            continue;
                                        }
                                    case 10:
                                        String[] splits5 = this.mIssueInfo.getLogicTree().getResult()[i16].split(Constant.SEPARATOR_STRING);
                                        int value15 = Integer.parseInt(splits5[0]);
                                        int value25 = Integer.parseInt(splits5[1]);
                                        if ((value & value15) == value25) {
                                            index = i16;
                                            break;
                                        } else {
                                            continue;
                                        }
                                    case 11:
                                        String[] splits6 = this.mIssueInfo.getLogicTree().getResult()[i16].split(Constant.SEPARATOR_STRING);
                                        int value16 = Integer.parseInt(splits6[0]);
                                        int value26 = Integer.parseInt(splits6[1]);
                                        if ((value | value16) == value26) {
                                            index = i16;
                                            break;
                                        } else {
                                            continue;
                                        }
                                    case 12:
                                        String[] splits7 = this.mIssueInfo.getLogicTree().getResult()[i16].split(Constant.SEPARATOR_STRING);
                                        int value17 = Integer.parseInt(splits7[0]);
                                        int value27 = Integer.parseInt(splits7[1]);
                                        if ((value ^ value17) == value27) {
                                            index = i16;
                                            break;
                                        } else {
                                            continue;
                                        }
                                    case 13:
                                        String[] splits8 = this.mIssueInfo.getLogicTree().getResult()[i16].split(Constant.SEPARATOR_STRING);
                                        int bytevalue = Integer.parseInt(splits8[0]);
                                        int value18 = Integer.parseInt(splits8[1]);
                                        int value28 = Integer.parseInt(splits8[2]);
                                        if (((value & bytevalue) >> value18) >= value28) {
                                            index = i16;
                                            break;
                                        } else {
                                            continue;
                                        }
                                    case 14:
                                        String[] splits9 = this.mIssueInfo.getLogicTree().getResult()[i16].split(Constant.SEMICOLON_STRING);
                                        int length12 = splits9.length;
                                        int i17 = 0;
                                        while (true) {
                                            if (i17 < length12) {
                                                String split12 = splits9[i17];
                                                String[] ranges = split12.split(Constant.SEPARATOR_STRING);
                                                int value19 = Integer.parseInt(ranges[0]);
                                                int value29 = Integer.parseInt(ranges[1]);
                                                if (value < value19 || value > value29) {
                                                    i17++;
                                                } else {
                                                    index = i16;
                                                    break;
                                                }
                                            } else {
                                                continue;
                                            }
                                        }
                                        break;
                                }
                            } else {
                                String[] splits10 = this.mIssueInfo.getLogicTree().getResult()[i16].split(Constant.SEMICOLON_STRING);
                                int length13 = splits10.length;
                                int i18 = 0;
                                while (true) {
                                    if (i18 < length13) {
                                        String split13 = splits10[i18];
                                        String[] ranges2 = split13.split(Constant.SEPARATOR_STRING);
                                        int value110 = Integer.parseInt(ranges2[0]);
                                        int value210 = Integer.parseInt(ranges2[1]);
                                        if (value <= value110 || value >= value210) {
                                            i18++;
                                        } else {
                                            index = i16;
                                        }
                                    }
                                }
                            }
                        } else if (Integer.parseInt(this.mIssueInfo.getLogicTree().getResult()[i16]) == value) {
                            index = i16;
                        }
                    }
                } else if (logicActionResult.getResult() instanceof Long) {
                    long value3 = ((Long) logicActionResult.getResult()).longValue();
                    for (int i19 = 0; i19 < this.mIssueInfo.getLogicTree().getResult().length; i19++) {
                        int parseMethod4 = this.mIssueInfo.getLogicTree().getParseMethod();
                        if (parseMethod4 == 1) {
                            if (Long.parseLong(this.mIssueInfo.getLogicTree().getResult()[i19]) == value3) {
                                index = i19;
                            }
                        } else {
                            switch (parseMethod4) {
                                case 8:
                                    if (Long.parseLong(this.mIssueInfo.getLogicTree().getResult()[i19]) < value3) {
                                        index = i19;
                                        break;
                                    } else {
                                        continue;
                                    }
                                case 9:
                                    if (Long.parseLong(this.mIssueInfo.getLogicTree().getResult()[i19]) <= value3) {
                                        index = i19;
                                        break;
                                    } else {
                                        continue;
                                    }
                                case 10:
                                    String[] splits11 = this.mIssueInfo.getLogicTree().getResult()[i19].split(Constant.SEPARATOR_STRING);
                                    int value111 = Integer.parseInt(splits11[0]);
                                    int value211 = Integer.parseInt(splits11[1]);
                                    if ((value111 & value3) == value211) {
                                        index = i19;
                                        break;
                                    } else {
                                        continue;
                                    }
                                case 11:
                                    String[] splits12 = this.mIssueInfo.getLogicTree().getResult()[i19].split(Constant.SEPARATOR_STRING);
                                    int value112 = Integer.parseInt(splits12[0]);
                                    int value212 = Integer.parseInt(splits12[1]);
                                    if ((value112 | value3) == value212) {
                                        index = i19;
                                        break;
                                    } else {
                                        continue;
                                    }
                                case 12:
                                    String[] splits13 = this.mIssueInfo.getLogicTree().getResult()[i19].split(Constant.SEPARATOR_STRING);
                                    int value113 = Integer.parseInt(splits13[0]);
                                    int value213 = Integer.parseInt(splits13[1]);
                                    if ((value113 ^ value3) == value213) {
                                        index = i19;
                                        break;
                                    } else {
                                        continue;
                                    }
                            }
                        }
                    }
                } else if (logicActionResult.getResult() instanceof Double) {
                    double value4 = ((Double) logicActionResult.getResult()).doubleValue();
                    for (int i20 = 0; i20 < this.mIssueInfo.getLogicTree().getResult().length; i20++) {
                        int parseMethod5 = this.mIssueInfo.getLogicTree().getParseMethod();
                        if (parseMethod5 != 1) {
                            if (parseMethod5 == 8) {
                                if (Double.parseDouble(this.mIssueInfo.getLogicTree().getResult()[i20]) < value4) {
                                    index = i20;
                                }
                            } else if (parseMethod5 == 9 && Double.parseDouble(this.mIssueInfo.getLogicTree().getResult()[i20]) <= value4) {
                                index = i20;
                            }
                        } else if (Double.parseDouble(this.mIssueInfo.getLogicTree().getResult()[i20]) == value4) {
                            index = i20;
                        }
                    }
                } else if (logicActionResult.getResult() instanceof String[]) {
                    List<String> stringList = Arrays.asList((String[]) logicActionResult.getResult());
                    for (int i21 = 0; i21 < this.mIssueInfo.getLogicTree().getResult().length; i21++) {
                        int parseMethod6 = this.mIssueInfo.getLogicTree().getParseMethod();
                        if (parseMethod6 == 3) {
                            index = i21;
                            String[] split14 = this.mIssueInfo.getLogicTree().getResult()[i21].split(Constant.SEPARATOR_STRING);
                            int length14 = split14.length;
                            int i22 = 0;
                            while (true) {
                                if (i22 >= length14) {
                                    break;
                                }
                                String result12 = split14[i22];
                                if (stringList.contains(result12)) {
                                    i22++;
                                } else {
                                    index = -1;
                                    break;
                                }
                            }
                        } else if (parseMethod6 == 4) {
                            index = i21;
                            String[] split15 = this.mIssueInfo.getLogicTree().getResult()[i21].split(Constant.SEPARATOR_STRING);
                            int length15 = split15.length;
                            int i23 = 0;
                            while (true) {
                                if (i23 >= length15) {
                                    break;
                                }
                                String result13 = split15[i23];
                                if (!stringList.contains(result13)) {
                                    i23++;
                                } else {
                                    index = -1;
                                    break;
                                }
                            }
                        } else if (parseMethod6 == 6) {
                            String[] split16 = this.mIssueInfo.getLogicTree().getResult()[i21].split(Constant.SEPARATOR_STRING);
                            int length16 = split16.length;
                            int i24 = 0;
                            while (true) {
                                if (i24 >= length16) {
                                    break;
                                }
                                String result14 = split16[i24];
                                if (!stringList.contains(result14)) {
                                    i24++;
                                } else {
                                    index = i21;
                                    break;
                                }
                            }
                        } else if (parseMethod6 == 7) {
                            String[] split17 = this.mIssueInfo.getLogicTree().getResult()[i21].split(Constant.SEPARATOR_STRING);
                            int length17 = split17.length;
                            int i25 = 0;
                            while (true) {
                                if (i25 >= length17) {
                                    break;
                                }
                                String result15 = split17[i25];
                                if (stringList.contains(result15)) {
                                    i25++;
                                } else {
                                    index = i21;
                                    break;
                                }
                            }
                        } else if (parseMethod6 == 15) {
                            Iterator<String> it = stringList.iterator();
                            while (true) {
                                if (it.hasNext()) {
                                    if (it.next().matches(this.mIssueInfo.getLogicTree().getResult()[i21])) {
                                        index = i21;
                                        break;
                                    }
                                } else {
                                    break;
                                }
                            }
                        }
                        if (index == i21) {
                            break;
                        }
                    }
                }
            }
        }
        LogUtils.i(TAG, "index : %d", Integer.valueOf(index));
        if (index == -2) {
            IssueInfo issueInfo = this.mIssueInfo;
            issueInfo.setLogicTree(issueInfo.getLogicTree().getNoResultAction());
        } else if (index == -1) {
            IssueInfo issueInfo2 = this.mIssueInfo;
            issueInfo2.setLogicTree(issueInfo2.getLogicTree().getDefaultAction());
        } else if (index < this.mIssueInfo.getLogicTree().getActionSize()) {
            IssueInfo issueInfo3 = this.mIssueInfo;
            issueInfo3.setLogicTree(issueInfo3.getLogicTree().getNextAction(index));
        } else {
            IssueInfo issueInfo4 = this.mIssueInfo;
            issueInfo4.setLogicTree(issueInfo4.getLogicTree().getDefaultAction());
        }
        handleLogicAction();
    }

    public boolean process(LogicTreeInfo logicTreeInfo, long startTime, long endTime, int entry) {
        LogUtils.i(TAG, "process logicTreePath[%s] startTime[%d] endTime[%d]", logicTreeInfo.getLogicTreePath(), Long.valueOf(startTime), Long.valueOf(endTime));
        if (this.mIssueInfo.getLogicTree() == null) {
            try {
                IssueInfo issueInfo = this.mIssueInfo;
                Gson gson = new Gson();
                issueInfo.setLogicTree((LogicTree) gson.fromJson(new JsonReader(new InputStreamReader(new FileInputStream(this.mLogicTreePath + File.separator + logicTreeInfo.getLogicTreePath()))), LogicTree.class));
                this.mIssueInfo.setIssueName(logicTreeInfo.getName());
                this.mIssueInfo.setStartTime(startTime);
                this.mIssueInfo.setEndTime(endTime);
                this.mIssueInfo.setLogicVersion(this.mLogicTreeInfoList.getVersion());
                this.mIssueInfo.setEntry(entry);
                boolean result = handleLogicAction();
                return result;
            } catch (Exception e) {
                EventBus.getDefault().post(new CommonEvent(10006, this.mContext.getString(R.string.tips_logictree_parse_fail)));
                dismissLoadingDialog();
                e.printStackTrace();
                return false;
            }
        }
        EventBus.getDefault().post(new CommonEvent(10006, this.mContext.getString(R.string.tips_for_wait_last_diagnosis_done)));
        dismissLoadingDialog();
        LogUtils.e(TAG, "it was processing other logic tree, plz trigger again later");
        return false;
    }

    public boolean handleLogicAction() {
        if (this.mIssueInfo.getLogicTree() != null) {
            String action = this.mIssueInfo.getLogicTree().getAction();
            final LogicActionHandler handler = this.mLogicActionHandlers.get(action);
            if (handler != null) {
                EventBus.getDefault().post(new CommonEvent(10005, null));
                ThreadUtils.execute(new Runnable() { // from class: com.xiaopeng.logictree.-$$Lambda$LogicTreeParser$MKeUbKXtgQDcDUToRiYU3gvDDt4
                    @Override // java.lang.Runnable
                    public final void run() {
                        LogicTreeParser.this.lambda$handleLogicAction$2$LogicTreeParser(handler);
                    }
                });
                return true;
            }
            dismissLoadingDialog();
            this.mIssueInfo.setLogicTree(null);
            if (TextUtils.isEmpty(action)) {
                return false;
            }
            EventBus.getDefault().post(new CommonEvent(10006, this.mContext.getString(R.string.tips_logictree_parse_fail)));
            LogUtils.i(TAG, "dont support this action [%s]", action);
            return false;
        }
        dismissLoadingDialog();
        LogUtils.i(TAG, "end of issue diagnosis");
        return false;
    }

    public /* synthetic */ void lambda$handleLogicAction$2$LogicTreeParser(LogicActionHandler handler) {
        handler.handleCommand(this.mIssueInfo);
    }

    public IssueInfo getIssueInfo() {
        return this.mIssueInfo;
    }

    private void dismissLoadingDialog() {
        EventBus.getDefault().post(new CommonEvent(10003, null));
    }
}
