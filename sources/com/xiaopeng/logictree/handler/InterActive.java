package com.xiaopeng.logictree.handler;

import android.app.Application;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ListAdapter;
import android.widget.ListView;
import com.xiaopeng.commonfunc.Constant;
import com.xiaopeng.commonfunc.bean.event.CommonEvent;
import com.xiaopeng.commonfunc.callback.FileUploadCallback;
import com.xiaopeng.commonfunc.utils.DataHelp;
import com.xiaopeng.commonfunc.utils.FileUploader;
import com.xiaopeng.lib.utils.LogUtils;
import com.xiaopeng.lib.utils.ThreadUtils;
import com.xiaopeng.logictree.IssueInfo;
import com.xiaopeng.logictree.LogicTreeHelper;
import com.xiaopeng.logictree.R;
import com.xiaopeng.logictree.bean.LogicResponseData;
import com.xiaopeng.xui.app.XDialog;
import com.xiaopeng.xui.app.XDialogInterface;
import com.xiaopeng.xui.app.XDialogSystemType;
import com.xiaopeng.xui.widget.XTextView;
import org.greenrobot.eventbus.EventBus;
/* loaded from: classes5.dex */
public class InterActive extends LogicActionHandler {
    private FileUploadCallback mFileUploadCallback;
    private FileUploader mFileUploader;
    private XDialog mInterActiveDialog;
    protected LogicActionCallback mLogicActionCallback;

    public InterActive(Application application) {
        super(application);
        this.mLogicActionCallback = new LogicActionCallback() { // from class: com.xiaopeng.logictree.handler.InterActive.1
            @Override // com.xiaopeng.logictree.handler.LogicActionCallback
            public void onResult(String result) {
                LogicTreeHelper.responseResult(result);
                if (InterActive.this.mInterActiveDialog != null) {
                    InterActive.this.mInterActiveDialog.dismiss();
                    InterActive.this.mInterActiveDialog = null;
                }
            }
        };
        this.mFileUploadCallback = new FileUploadCallback() { // from class: com.xiaopeng.logictree.handler.InterActive.2
            @Override // com.xiaopeng.commonfunc.callback.FileUploadCallback
            public void onSuccess(String path, String password) {
                InterActive.this.handleInterActive(path, password);
            }

            @Override // com.xiaopeng.commonfunc.callback.FileUploadCallback
            public void onFailure() {
                LogicTreeHelper.responseNoResult();
            }
        };
        this.CLASS_NAME = "InterActive";
        this.mFileUploader = new FileUploader(this.context, this.mFileUploadCallback);
    }

    @Override // com.xiaopeng.logictree.handler.LogicActionHandler
    public synchronized String handleCommand(IssueInfo issueInfo) {
        super.handleCommand(issueInfo);
        if (checkArgu(this.argus, new String[]{"1"})) {
            handleInterActive("", "");
        } else if (checkArgu(this.argus, new String[]{"2"})) {
            String[] paths = this.argus[2].split(Constant.SEMICOLON_STRING);
            if (issueInfo.getEntry() == 1) {
                this.mFileUploader.setDirection(1);
                this.mFileUploader.uploadFile2Cloud(this.mApplication, Constant.AFTERSALES_CACHE, paths, issueInfo.getIssueName(), 1);
            } else {
                this.mFileUploader.setDirection(2);
                this.mFileUploader.copyData2Udisk(paths, issueInfo.getIssueName(), 1);
            }
        }
        return null;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void handleInterActive(final String downloadLink, final String password) {
        final StringBuffer buffer = new StringBuffer();
        if (this.mIssueInfo.getEntry() == 1) {
            if (this.argus.length > 1) {
                buffer.append(this.argus[1]);
                if (!TextUtils.isEmpty(password) && !TextUtils.isEmpty(downloadLink)) {
                    buffer.append("\n");
                    buffer.append(this.context.getString(R.string.data_decrypt_password, password));
                    buffer.append("\n");
                    buffer.append(this.context.getString(R.string.data_download_path));
                }
                LogUtils.i(this.CLASS_NAME, "interactive : %s", buffer.toString());
                EventBus.getDefault().post(new LogicResponseData(buffer.toString(), DataHelp.getRandomString(32), downloadLink, this.mIssueInfo.getLogicTree().getResult()));
                return;
            }
            return;
        }
        ThreadUtils.runOnMainThread(new Runnable() { // from class: com.xiaopeng.logictree.handler.-$$Lambda$InterActive$48yf6yW-Zt4WnF-XDEkQTGOo3Bc
            @Override // java.lang.Runnable
            public final void run() {
                InterActive.this.lambda$handleInterActive$1$InterActive(buffer, password, downloadLink);
            }
        });
    }

    public /* synthetic */ void lambda$handleInterActive$1$InterActive(StringBuffer buffer, String password, String downloadLink) {
        this.mInterActiveDialog = new XDialog(this.context);
        View view = LayoutInflater.from(this.context).inflate(R.layout.dialog_logictree_interactive, this.mInterActiveDialog.getContentView(), false);
        if (this.argus.length > 1) {
            buffer.append(this.argus[1]);
            if (!TextUtils.isEmpty(password) && !TextUtils.isEmpty(downloadLink)) {
                buffer.append("\n");
                buffer.append(this.context.getString(R.string.data_udisk_path, downloadLink));
                buffer.append("\n");
                buffer.append(this.context.getString(R.string.udisk_data_decrypt_password));
            }
            buffer.append(this.context.getString(R.string.tips_confirmation_for_diagnosis));
            LogUtils.i(this.CLASS_NAME, "interactive : %s", buffer.toString());
            ((XTextView) view.findViewById(R.id.interactive_purpose)).setText(buffer.toString());
        }
        ((ListView) view.findViewById(R.id.interactive_selector)).setAdapter((ListAdapter) new InterActiveAdapter(this.mIssueInfo.getLogicTree().getResult(), this.mLogicActionCallback));
        this.mInterActiveDialog.setTitle(R.string.info_confirmation).setCustomView(view, false);
        this.mInterActiveDialog.setCloseVisibility(true);
        this.mInterActiveDialog.setOnCloseListener(new XDialogInterface.OnCloseListener() { // from class: com.xiaopeng.logictree.handler.-$$Lambda$InterActive$gqM5eYGaLpT4uAw4k95HvOgc-aE
            @Override // com.xiaopeng.xui.app.XDialogInterface.OnCloseListener
            public final boolean onClose(XDialog xDialog) {
                return InterActive.lambda$handleInterActive$0(xDialog);
            }
        });
        this.mInterActiveDialog.setCanceledOnTouchOutside(false);
        this.mInterActiveDialog.setSystemDialog(XDialogSystemType.TYPE_SYSTEM_DIALOG);
        this.mInterActiveDialog.show();
        EventBus.getDefault().post(new CommonEvent(10003, null));
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public static /* synthetic */ boolean lambda$handleInterActive$0(XDialog xDialog) {
        EventBus.getDefault().post(new CommonEvent(10002, null));
        return false;
    }

    @Override // com.xiaopeng.logictree.handler.LogicActionHandler
    public void destroy() {
        super.destroy();
        this.mFileUploader.destroy();
        XDialog xDialog = this.mInterActiveDialog;
        if (xDialog != null) {
            xDialog.dismiss();
            this.mInterActiveDialog = null;
        }
    }
}
