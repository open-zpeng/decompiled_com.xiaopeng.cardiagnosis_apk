package com.xiaopeng.logictree.handler;

import android.app.Application;
import android.text.TextUtils;
import com.xiaopeng.commonfunc.system.runnable.Sleep;
import com.xiaopeng.commonfunc.utils.FileUtil;
import com.xiaopeng.lib.utils.LogUtils;
import com.xiaopeng.logictree.IssueInfo;
import com.xiaopeng.logictree.LogicTreeHelper;
/* loaded from: classes5.dex */
public class UsbInfo extends LogicActionHandler {
    public UsbInfo(Application application) {
        super(application);
        this.CLASS_NAME = "UsbInfo";
    }

    @Override // com.xiaopeng.logictree.handler.LogicActionHandler
    public synchronized String handleCommand(IssueInfo issueInfo) {
        super.handleCommand(issueInfo);
        if (checkArgu(this.argus, new String[]{"1"})) {
            LogUtils.i(this.CLASS_NAME, "check whether tbox attached ? ");
            if (FileUtil.isUsbDeviceAttached(this.context, Integer.parseInt(this.argus[1]), Integer.parseInt(this.argus[2]))) {
                LogicTreeHelper.responseOK();
            } else {
                LogicTreeHelper.responseNG();
            }
        } else if (checkArgu(this.argus, new String[]{"2"})) {
            LogUtils.i(this.CLASS_NAME, "check whether udisk attached ? ");
            if (!TextUtils.isEmpty(FileUtil.getUDiskPath(this.context))) {
                LogicTreeHelper.responseOK();
            } else {
                LogicTreeHelper.responseNG();
            }
        } else if (checkArgu(this.argus, new String[]{"3"})) {
            LogUtils.i(this.CLASS_NAME, "get udisk fsType");
            Sleep.sleep(3000L);
            String fstype = FileUtil.getUDiskFsType(this.context);
            if (!TextUtils.isEmpty(fstype)) {
                LogicTreeHelper.responseResult(fstype);
            } else {
                LogicTreeHelper.responseNoResult();
            }
        }
        return null;
    }

    @Override // com.xiaopeng.logictree.handler.LogicActionHandler
    public void destroy() {
        super.destroy();
    }
}
