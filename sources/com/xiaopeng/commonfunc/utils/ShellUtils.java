package com.xiaopeng.commonfunc.utils;
/* loaded from: classes4.dex */
public class ShellUtils {
    public static final String COMMAND_EXIT = "exit\n";
    public static final String COMMAND_LINE_END = "\n";
    public static final String COMMAND_SH = "sh";
    public static final String COMMAND_SU = "/system/bin/xpeng-su";
    public static final int SHELL_CMD_EXCUTE_SUCCESS = 0;

    private ShellUtils() {
        throw new AssertionError();
    }

    public static CommandResult execCommand(String command, boolean isRoot) {
        return execCommand(new String[]{command}, isRoot, true);
    }

    public static CommandResult execCommand(String command, boolean isRoot, boolean isNeedResultMsg) {
        return execCommand(new String[]{command}, isRoot, isNeedResultMsg);
    }

    /* JADX WARN: Can't wrap try/catch for region: R(13:5|(8:6|7|(1:9)(1:52)|10|11|(4:14|(2:16|17)(2:19|20)|18|12)|21|22)|(12:24|(2:25|(1:27)(1:28))|(2:29|(1:31)(0))|34|(1:36)|(1:38)|40|41|(1:43)(1:49)|(1:45)|46|47)(0)|33|34|(0)|(0)|40|41|(0)(0)|(0)|46|47) */
    /* JADX WARN: Code restructure failed: missing block: B:38:0x00a8, code lost:
        r8 = move-exception;
     */
    /* JADX WARN: Code restructure failed: missing block: B:39:0x00a9, code lost:
        r8.printStackTrace();
     */
    /* JADX WARN: Code restructure failed: missing block: B:57:0x00d0, code lost:
        if (r2 != null) goto L40;
     */
    /* JADX WARN: Code restructure failed: missing block: B:72:0x00ef, code lost:
        if (r2 != null) goto L40;
     */
    /* JADX WARN: Removed duplicated region for block: B:34:0x009f A[Catch: IOException -> 0x00a8, TryCatch #1 {IOException -> 0x00a8, blocks: (B:32:0x009a, B:34:0x009f, B:36:0x00a4), top: B:100:0x009a }] */
    /* JADX WARN: Removed duplicated region for block: B:36:0x00a4 A[Catch: IOException -> 0x00a8, TRY_LEAVE, TryCatch #1 {IOException -> 0x00a8, blocks: (B:32:0x009a, B:34:0x009f, B:36:0x00a4), top: B:100:0x009a }] */
    /* JADX WARN: Removed duplicated region for block: B:76:0x00f6  */
    /* JADX WARN: Removed duplicated region for block: B:77:0x00f8  */
    /* JADX WARN: Removed duplicated region for block: B:80:0x0103  */
    /* JADX WARN: Removed duplicated region for block: B:95:0x0129  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct add '--show-bad-code' argument
    */
    public static com.xiaopeng.commonfunc.utils.ShellUtils.CommandResult execCommand(java.lang.String[] r13, boolean r14, boolean r15) {
        /*
            Method dump skipped, instructions count: 307
            To view this dump add '--comments-level debug' option
        */
        throw new UnsupportedOperationException("Method not decompiled: com.xiaopeng.commonfunc.utils.ShellUtils.execCommand(java.lang.String[], boolean, boolean):com.xiaopeng.commonfunc.utils.ShellUtils$CommandResult");
    }

    /* loaded from: classes4.dex */
    public static class CommandResult {
        public String errorMsg;
        public int result;
        public String successMsg;

        public CommandResult(int result, String successMsg, String errorMsg) {
            this.result = result;
            this.successMsg = successMsg;
            this.errorMsg = errorMsg;
        }
    }
}
