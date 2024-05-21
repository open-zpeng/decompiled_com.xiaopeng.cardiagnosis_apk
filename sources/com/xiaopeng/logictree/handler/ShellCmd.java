package com.xiaopeng.logictree.handler;

import android.app.Application;
import com.xiaopeng.aftersales.manager.ShellCmdListener;
import com.xiaopeng.commonfunc.utils.AfterSalesHelper;
/* loaded from: classes5.dex */
public class ShellCmd extends LogicActionHandler {
    private final ShellCmdListener mShellCmdListener;

    /* JADX WARN: Can't fix incorrect switch cases order, some code will duplicate */
    /* JADX WARN: Code restructure failed: missing block: B:31:0x0070, code lost:
        if (r0.equals("1") != false) goto L7;
     */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct add '--show-bad-code' argument
    */
    public /* synthetic */ void lambda$new$0$ShellCmd(int r12, java.lang.String r13, boolean r14) {
        /*
            Method dump skipped, instructions count: 750
            To view this dump add '--comments-level debug' option
        */
        throw new UnsupportedOperationException("Method not decompiled: com.xiaopeng.logictree.handler.ShellCmd.lambda$new$0$ShellCmd(int, java.lang.String, boolean):void");
    }

    public ShellCmd(Application application) {
        super(application);
        this.mShellCmdListener = new ShellCmdListener() { // from class: com.xiaopeng.logictree.handler.-$$Lambda$ShellCmd$mQDPtul5h5GjJYHmXeN7-q5u1jk
            public final void onShellResponse(int i, String str, boolean z) {
                ShellCmd.this.lambda$new$0$ShellCmd(i, str, z);
            }
        };
        this.CLASS_NAME = "ShellCmd";
        AfterSalesHelper.getAfterSalesManager().registerShellCmdListener(this.mShellCmdListener);
    }

    /* JADX WARN: Removed duplicated region for block: B:41:0x009a  */
    /* JADX WARN: Removed duplicated region for block: B:42:0x009c A[Catch: all -> 0x0120, TryCatch #0 {, blocks: (B:3:0x0001, B:5:0x000b, B:7:0x002a, B:40:0x0097, B:42:0x009c, B:44:0x00c0, B:46:0x00ca, B:47:0x00df, B:49:0x00ed, B:50:0x00f5, B:52:0x00fa, B:54:0x0101, B:55:0x010c, B:9:0x002f, B:12:0x003b, B:15:0x0046, B:18:0x0050, B:21:0x005a, B:24:0x0064, B:27:0x006e, B:30:0x0078, B:33:0x0082, B:36:0x008c, B:57:0x011a), top: B:64:0x0001 }] */
    /* JADX WARN: Removed duplicated region for block: B:47:0x00df A[Catch: all -> 0x0120, TryCatch #0 {, blocks: (B:3:0x0001, B:5:0x000b, B:7:0x002a, B:40:0x0097, B:42:0x009c, B:44:0x00c0, B:46:0x00ca, B:47:0x00df, B:49:0x00ed, B:50:0x00f5, B:52:0x00fa, B:54:0x0101, B:55:0x010c, B:9:0x002f, B:12:0x003b, B:15:0x0046, B:18:0x0050, B:21:0x005a, B:24:0x0064, B:27:0x006e, B:30:0x0078, B:33:0x0082, B:36:0x008c, B:57:0x011a), top: B:64:0x0001 }] */
    /* JADX WARN: Removed duplicated region for block: B:55:0x010c A[Catch: all -> 0x0120, TryCatch #0 {, blocks: (B:3:0x0001, B:5:0x000b, B:7:0x002a, B:40:0x0097, B:42:0x009c, B:44:0x00c0, B:46:0x00ca, B:47:0x00df, B:49:0x00ed, B:50:0x00f5, B:52:0x00fa, B:54:0x0101, B:55:0x010c, B:9:0x002f, B:12:0x003b, B:15:0x0046, B:18:0x0050, B:21:0x005a, B:24:0x0064, B:27:0x006e, B:30:0x0078, B:33:0x0082, B:36:0x008c, B:57:0x011a), top: B:64:0x0001 }] */
    @Override // com.xiaopeng.logictree.handler.LogicActionHandler
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct add '--show-bad-code' argument
    */
    public synchronized java.lang.String handleCommand(com.xiaopeng.logictree.IssueInfo r10) {
        /*
            Method dump skipped, instructions count: 338
            To view this dump add '--comments-level debug' option
        */
        throw new UnsupportedOperationException("Method not decompiled: com.xiaopeng.logictree.handler.ShellCmd.handleCommand(com.xiaopeng.logictree.IssueInfo):java.lang.String");
    }

    @Override // com.xiaopeng.logictree.handler.LogicActionHandler
    public void destroy() {
        AfterSalesHelper.getAfterSalesManager().unregisterShellCmdListener(this.mShellCmdListener);
        super.destroy();
    }
}
