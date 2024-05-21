package com.xiaopeng.commonfunc.system.runnable;

import com.xiaopeng.commonfunc.utils.FileUtil;
import com.xiaopeng.lib.utils.LogUtils;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.nio.channels.FileChannel;
import java.nio.channels.FileLock;
import org.greenrobot.eventbus.EventBus;
/* loaded from: classes4.dex */
public class CopyFileTask implements Runnable {
    private static final String TAG = "CopyFileTask";
    private Callback mCallback;
    private final String mDestPath;
    private final long mEnd;
    RandomAccessFile mIn;
    RandomAccessFile mOut;
    private final String mSrcPath;
    private final long mStart;

    /* loaded from: classes4.dex */
    public interface Callback {
        void onCopyResult(boolean z);
    }

    public CopyFileTask(String srcPath, String destPath, long start, long end) {
        this.mSrcPath = srcPath;
        this.mDestPath = destPath;
        this.mStart = start;
        this.mEnd = end;
    }

    public CopyFileTask(String srcPath, String destPath, long start, long end, Callback callback) {
        this.mSrcPath = srcPath;
        this.mDestPath = destPath;
        this.mStart = start;
        this.mEnd = end;
        this.mCallback = callback;
    }

    @Override // java.lang.Runnable
    public void run() {
        Integer num;
        FileChannel inChannel;
        FileChannel outChannel;
        if (FileUtil.isExistFilePath(this.mSrcPath)) {
            try {
                try {
                    if (FileUtil.isExistFileParentPath(this.mDestPath)) {
                        try {
                            long beginTime = System.currentTimeMillis();
                            LogUtils.d(TAG, "beginTime = " + beginTime + " mSrcPath = " + this.mSrcPath + "destPath = " + this.mDestPath);
                            this.mIn = new RandomAccessFile(this.mSrcPath, "r");
                            this.mOut = new RandomAccessFile(this.mDestPath, "rw");
                            this.mIn.seek(this.mStart);
                            this.mOut.seek(this.mStart);
                            inChannel = this.mIn.getChannel();
                            outChannel = this.mOut.getChannel();
                            num = 2;
                        } catch (Exception e) {
                            e = e;
                            num = 2;
                        }
                        try {
                            FileLock lock = outChannel.lock(this.mStart, this.mEnd - this.mStart, false);
                            inChannel.transferTo(this.mStart, this.mEnd - this.mStart, outChannel);
                            lock.release();
                            this.mOut.close();
                            this.mIn.close();
                            long endTime = System.currentTimeMillis();
                            if (this.mCallback != null) {
                                this.mCallback.onCopyResult(true);
                            } else {
                                EventBus.getDefault().post(1);
                            }
                            LogUtils.d(TAG, "endTime = " + endTime + " mSrcPath = " + this.mSrcPath + "destPath = " + this.mDestPath);
                            if (this.mOut != null) {
                                this.mOut.close();
                            }
                            if (this.mIn != null) {
                                this.mIn.close();
                                return;
                            }
                            return;
                        } catch (Exception e2) {
                            e = e2;
                            if (this.mCallback != null) {
                                this.mCallback.onCopyResult(false);
                            } else {
                                EventBus.getDefault().post(num);
                            }
                            e.printStackTrace();
                            if (this.mOut != null) {
                                this.mOut.close();
                            }
                            if (this.mIn != null) {
                                this.mIn.close();
                                return;
                            }
                            return;
                        }
                    }
                } catch (Throwable e3) {
                    try {
                        if (this.mOut != null) {
                            this.mOut.close();
                        }
                        if (this.mIn != null) {
                            this.mIn.close();
                        }
                    } catch (IOException e4) {
                        e4.printStackTrace();
                    }
                    throw e3;
                }
            } catch (IOException e5) {
                e5.printStackTrace();
                return;
            }
        }
        Callback callback = this.mCallback;
        if (callback != null) {
            callback.onCopyResult(false);
        } else {
            EventBus.getDefault().post(2);
        }
    }
}
