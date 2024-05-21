package com.xiaopeng.logictree.handler;

import android.app.Application;
import android.text.TextUtils;
import com.xiaopeng.commonfunc.Constant;
import com.xiaopeng.commonfunc.bean.event.CommonEvent;
import com.xiaopeng.commonfunc.callback.FileUploadCallback;
import com.xiaopeng.commonfunc.utils.ActivityUtil;
import com.xiaopeng.commonfunc.utils.AfterSalesHelper;
import com.xiaopeng.commonfunc.utils.FileUploader;
import com.xiaopeng.commonfunc.utils.FileUtil;
import com.xiaopeng.commonfunc.utils.ProcessUtil;
import com.xiaopeng.commonfunc.utils.TimeUtil;
import com.xiaopeng.lib.utils.LogUtils;
import com.xiaopeng.lib.utils.ThreadUtils;
import com.xiaopeng.logictree.IssueInfo;
import com.xiaopeng.logictree.LogicTreeHelper;
import com.xiaopeng.logictree.R;
import com.xiaopeng.logictree.bean.LogicResponseData;
import com.xiaopeng.xui.app.XDialog;
import com.xiaopeng.xui.app.XDialogInterface;
import com.xiaopeng.xui.app.XDialogSystemType;
import java.util.ArrayList;
import java.util.List;
import org.greenrobot.eventbus.EventBus;
/* loaded from: classes5.dex */
public class DisplayHandleResult extends LogicActionHandler {
    private static final String ACTIVITY_NAME_GRABLOG = "com.xiaopeng.devtools.view.log.GrabLogActivity";
    private static final String ONE_CLICK_DIAGNOSIS_REBOOT = "ONE CLICK DIAGNOSIS REBOOT";
    private static final String PACKAGE_NAME_DEVTOOLS = "com.xiaopeng.devtools";
    private static final String PATH_TBOX_LOG = "/cache/aftersales/tboxlog";
    private static final String TAG_TBOX_LOG = "tboxlog";
    private static final int TIME_UNIT = 1000;
    private int mDelayTime;
    private String mDisplayMessage;
    private FileUploadCallback mFileUploadCallback;
    private FileUploader mFileUploader;

    public DisplayHandleResult(Application application) {
        super(application);
        this.mDelayTime = -1;
        this.mFileUploadCallback = new FileUploadCallback() { // from class: com.xiaopeng.logictree.handler.DisplayHandleResult.1
            @Override // com.xiaopeng.commonfunc.callback.FileUploadCallback
            public void onSuccess(String path, String password) {
                DisplayHandleResult.this.handleDisplayResult(path);
            }

            @Override // com.xiaopeng.commonfunc.callback.FileUploadCallback
            public void onFailure() {
                DisplayHandleResult.this.handleDisplayResult(null);
            }
        };
        this.CLASS_NAME = "DisplayHandleResult";
        this.mFileUploader = new FileUploader(this.context, this.mFileUploadCallback);
    }

    @Override // com.xiaopeng.logictree.handler.LogicActionHandler
    public synchronized String handleCommand(IssueInfo issueInfo) {
        String logPath;
        super.handleCommand(issueInfo);
        if (checkArgu(this.argus, new String[]{"1"})) {
            this.mDisplayMessage = this.argus[1];
            logPath = this.argus.length > 2 ? this.argus[2] : null;
        } else if (checkArgu(this.argus, new String[]{"2"})) {
            try {
                this.mDelayTime = Integer.parseInt(this.argus[2]);
            } catch (NumberFormatException e) {
                e.printStackTrace();
            }
            this.mDisplayMessage = String.format(this.argus[1], Integer.valueOf(this.mDelayTime));
            logPath = this.argus.length > 3 ? this.argus[3] : null;
        } else {
            this.mDisplayMessage = "";
            logPath = null;
        }
        recordLogicAction(issueInfo, this.mDisplayMessage);
        String[] paths = getWantedLogs(issueInfo, logPath);
        if (issueInfo.getEntry() == 1) {
            this.mFileUploader.setDirection(1);
            this.mFileUploader.uploadFile2Cloud(this.mApplication, Constant.AFTERSALES_CACHE, paths, issueInfo.getIssueName(), 2);
        } else {
            this.mFileUploader.setDirection(2);
            this.mFileUploader.copyData2Udisk(paths, issueInfo.getIssueName(), 2);
        }
        return null;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void handleDisplayResult(String downloadLink) {
        String str = this.CLASS_NAME;
        LogUtils.i(str, this.mIssueInfo.getIssueName() + " Handle Result : %s", this.mDisplayMessage);
        final StringBuffer buffer = new StringBuffer();
        if (this.mIssueInfo.getEntry() == 1) {
            buffer.append(this.mDisplayMessage);
            buffer.append("\n");
            if (!TextUtils.isEmpty(downloadLink)) {
                buffer.append(this.context.getString(R.string.data_download_path));
            } else {
                buffer.append(this.context.getString(R.string.log_data_transfer_fail));
            }
            EventBus.getDefault().post(new LogicResponseData(buffer.toString(), "", downloadLink, null));
            LogicTreeHelper.responseOK();
        } else {
            buffer.append(this.mDisplayMessage);
            buffer.append("\n");
            if (TextUtils.isEmpty(downloadLink)) {
                buffer.append(this.context.getString(R.string.log_data_transfer_fail));
            } else {
                buffer.append(this.context.getString(R.string.data_udisk_path, downloadLink));
            }
            ThreadUtils.runOnMainThread(new Runnable() { // from class: com.xiaopeng.logictree.handler.-$$Lambda$DisplayHandleResult$5OeHZGAfY1qTFJEpRkx6GXnLu0c
                @Override // java.lang.Runnable
                public final void run() {
                    DisplayHandleResult.this.lambda$handleDisplayResult$3$DisplayHandleResult(buffer);
                }
            });
        }
        int i = this.mDelayTime;
        if (i > 0) {
            ThreadUtils.postBackground(new Runnable() { // from class: com.xiaopeng.logictree.handler.-$$Lambda$DisplayHandleResult$iR-hhWDLqd_6rnBcwg5zNOzNQVU
                @Override // java.lang.Runnable
                public final void run() {
                    DisplayHandleResult.this.lambda$handleDisplayResult$4$DisplayHandleResult();
                }
            }, i * 1000);
        }
    }

    public /* synthetic */ void lambda$handleDisplayResult$3$DisplayHandleResult(StringBuffer buffer) {
        XDialog dialog = new XDialog(this.context).setTitle(this.mIssueInfo.getIssueName()).setMessage(buffer.toString()).setPositiveButton(this.context.getString(R.string.dialog_confirm), new XDialogInterface.OnClickListener() { // from class: com.xiaopeng.logictree.handler.-$$Lambda$DisplayHandleResult$u7tptGFf42OBMz9qtWzUYtrelno
            @Override // com.xiaopeng.xui.app.XDialogInterface.OnClickListener
            public final void onClick(XDialog xDialog, int i) {
                xDialog.dismiss();
            }
        });
        dialog.setNegativeButton(this.context.getString(R.string.run_catch_log_activity), new XDialogInterface.OnClickListener() { // from class: com.xiaopeng.logictree.handler.-$$Lambda$DisplayHandleResult$7mq0jhhKpSqqAAdmTJacZvDDlsU
            @Override // com.xiaopeng.xui.app.XDialogInterface.OnClickListener
            public final void onClick(XDialog xDialog, int i) {
                DisplayHandleResult.this.lambda$handleDisplayResult$1$DisplayHandleResult(xDialog, i);
            }
        });
        LogUtils.d(this.CLASS_NAME, "show dialog");
        dialog.setSystemDialog(XDialogSystemType.TYPE_SYSTEM_DIALOG);
        dialog.show();
        EventBus.getDefault().post(new CommonEvent(10003, null));
        ThreadUtils.execute(new Runnable() { // from class: com.xiaopeng.logictree.handler.-$$Lambda$DisplayHandleResult$UbzntAaGIn8dzhYyFanFOX2EmFo
            @Override // java.lang.Runnable
            public final void run() {
                DisplayHandleResult.this.lambda$handleDisplayResult$2$DisplayHandleResult();
            }
        });
        LogicTreeHelper.responseOK();
    }

    public /* synthetic */ void lambda$handleDisplayResult$1$DisplayHandleResult(XDialog xDialog, int i) {
        ActivityUtil.startActivity(this.context, "com.xiaopeng.devtools", ACTIVITY_NAME_GRABLOG);
        xDialog.dismiss();
    }

    public /* synthetic */ void lambda$handleDisplayResult$2$DisplayHandleResult() {
        String fileName = this.mIssueInfo.getIssueName() + Constant.DATA_SEPARATOR_STRING + TimeUtil.getDate();
        ProcessUtil.screenCap(Constant.AFTERSALES_LOG, fileName);
    }

    public /* synthetic */ void lambda$handleDisplayResult$4$DisplayHandleResult() {
        ProcessUtil.reboot(this.context, ONE_CLICK_DIAGNOSIS_REBOOT);
    }

    private String[] getWantedLogs(IssueInfo issueInfo, String logPath) {
        List<String> pathList = new ArrayList<>();
        pathList.add("/data/Log/log0");
        if (!TextUtils.isEmpty(logPath)) {
            String[] pathsInArgus = logPath.split(Constant.SEMICOLON_STRING);
            for (int i = 0; i < pathsInArgus.length; i++) {
                String str = pathsInArgus[i];
                char c = 65535;
                if (str.hashCode() == -1511905523 && str.equals(TAG_TBOX_LOG)) {
                    c = 0;
                }
                if (c == 0) {
                    ProcessUtil.copyTboxLog(PATH_TBOX_LOG);
                    pathList.add(PATH_TBOX_LOG);
                } else {
                    pathList.add(pathsInArgus[i]);
                }
            }
        }
        List<String> filterList = FileUtil.getFileList(Constant.DATA_LOG, Constant.DATA_LOG_MAIN, "log[1-7]", false, issueInfo.getStartTime(), issueInfo.getEndTime());
        if (!filterList.isEmpty()) {
            pathList.addAll(filterList);
            filterList.clear();
        }
        return (String[]) pathList.toArray(new String[pathList.size()]);
    }

    private void recordLogicAction(IssueInfo issueInfo, String mDisplayMessage) {
        String entry = Constant.UNKNOWN_STRING;
        int entry2 = issueInfo.getEntry();
        if (entry2 == 1) {
            entry = this.context.getString(R.string.entry_remote_diagnosis);
        } else if (entry2 == 2) {
            entry = this.context.getString(R.string.entry_check_mode);
        } else if (entry2 == 3) {
            entry = this.context.getString(R.string.entry_tester_ui);
        }
        AfterSalesHelper.getAfterSalesManager().recordLogicAction(issueInfo.getIssueName(), mDisplayMessage.replaceAll("\n", "").replaceAll("\r", ""), TimeUtil.timeStamp2Date(issueInfo.getStartTime() / 1000, TimeUtil.DATE_FORMAT_YYYYMMDDHHMM), TimeUtil.timeStamp2Date(issueInfo.getEndTime() / 1000, TimeUtil.DATE_FORMAT_YYYYMMDDHHMM), TimeUtil.timeStamp2Date(System.currentTimeMillis() / 1000, null), entry, issueInfo.getLogicVersion());
    }

    @Override // com.xiaopeng.logictree.handler.LogicActionHandler
    public void destroy() {
        super.destroy();
        this.mFileUploader.destroy();
    }
}
