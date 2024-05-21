package com.xiaopeng.commonfunc.system.runnable;

import com.xiaopeng.commonfunc.Constant;
import com.xiaopeng.commonfunc.bean.event.CommonEvent;
import com.xiaopeng.commonfunc.utils.ProcessUtil;
import com.xiaopeng.lib.utils.LogUtils;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import org.greenrobot.eventbus.EventBus;
/* loaded from: classes4.dex */
public class PingTask implements Runnable {
    private static final String TAG = "PingTask";
    private int mPackagesize;
    private final String mSite;
    private boolean result = false;

    public PingTask(String site, int packagesize) {
        this.mSite = site;
        this.mPackagesize = packagesize;
    }

    @Override // java.lang.Runnable
    public void run() {
        EventBus eventBus;
        CommonEvent commonEvent;
        LogUtils.d(TAG, "sendPing : " + this.mSite + ", mPackagesize:" + this.mPackagesize);
        int i = this.mPackagesize;
        if (i > 65535) {
            this.mPackagesize = 65535;
        } else if (i < 0) {
            this.mPackagesize = 8;
        }
        Process process = ProcessUtil.execProcess("ping -s " + this.mPackagesize + ProcessUtil.SHELL_COMMAND_PING_TIMEOUT + "60" + Constant.SPACE_STRING + this.mSite);
        if (process == null) {
            EventBus.getDefault().post(new CommonEvent(10001, null));
            return;
        }
        BufferedReader br = null;
        try {
            try {
                br = new BufferedReader(new InputStreamReader(process.getInputStream()));
                LogUtils.d(TAG, "sendPing readline");
                while (true) {
                    String line = br.readLine();
                    if (line != null) {
                        LogUtils.d(TAG, "sendPing line" + line);
                        this.result = line.toLowerCase().indexOf(Constant.PING_RESULT_TTL_LOWERCASE) > 0;
                        EventBus.getDefault().post(new CommonEvent(10001, line));
                    } else {
                        try {
                            break;
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                }
                br.close();
                process.destroy();
                ProcessUtil.killProcess("ping", this.mSite);
                eventBus = EventBus.getDefault();
                commonEvent = new CommonEvent(10001, null);
            } catch (Exception e2) {
                e2.printStackTrace();
                if (br != null) {
                    try {
                        br.close();
                    } catch (IOException e3) {
                        e3.printStackTrace();
                    }
                }
                process.destroy();
                ProcessUtil.killProcess("ping", this.mSite);
                eventBus = EventBus.getDefault();
                commonEvent = new CommonEvent(10001, null);
            }
            eventBus.post(commonEvent);
        } catch (Throwable th) {
            if (br != null) {
                try {
                    br.close();
                } catch (IOException e4) {
                    e4.printStackTrace();
                }
            }
            process.destroy();
            ProcessUtil.killProcess("ping", this.mSite);
            EventBus.getDefault().post(new CommonEvent(10001, null));
            throw th;
        }
    }
}
