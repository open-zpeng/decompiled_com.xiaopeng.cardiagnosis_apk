package com.xiaopeng.cardiagnosis.runnable;

import com.xiaopeng.cardiagnosis.exception.DownloadErrorException;
import com.xiaopeng.commonfunc.utils.TimeUtil;
import com.xiaopeng.lib.framework.moduleinterface.netchannelmodule.http.IHttp;
import com.xiaopeng.lib.framework.moduleinterface.netchannelmodule.http.IResponse;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.RandomAccessFile;
/* loaded from: classes4.dex */
public class FileDownloader implements Runnable {
    private static final int BUFFER_SIZE = 8192;
    private static final String TAG = "FileDownloader";
    private volatile boolean mCanceled;
    private final Listener mDownloadListener;
    private final String mDownloadUrl;
    private Exception mException;
    private volatile boolean mFailed;
    private final IHttp mHttp;
    private final String mOutputPath;
    private volatile boolean mSuccess;

    /* loaded from: classes4.dex */
    public interface Listener {
        void onCanceled();

        void onError(DownloadErrorException downloadErrorException);

        void onFailure(Exception exc);

        void onProgress(long j, long j2, byte[] bArr, int i);

        void onStarted(long j);

        void onSuccess(String str);
    }

    public FileDownloader(IHttp http, String downloadUrl, String outputPath, Listener listener) {
        this.mHttp = http;
        this.mDownloadUrl = downloadUrl;
        this.mOutputPath = outputPath + File.separator + TimeUtil.getDate() + ".zip";
        this.mDownloadListener = listener;
    }

    public void cancel() {
        this.mCanceled = true;
    }

    @Override // java.lang.Runnable
    public void run() {
        RandomAccessFile output;
        Throwable th;
        long downloadedSize;
        int n;
        long downloadedSize2 = 0;
        try {
            IResponse response = this.mHttp.get(this.mDownloadUrl).execute();
            try {
                if (response == null || response.byteStream() == null) {
                    throw new IOException("Response is empty");
                }
                if (response.code() >= 400) {
                    try {
                        if (response.code() <= 499) {
                            DownloadErrorException downloadErrorException = new DownloadErrorException(response.code(), response.body());
                            throw downloadErrorException;
                        }
                    } catch (DownloadErrorException e) {
                        e = e;
                        this.mException = e;
                        this.mFailed = true;
                        this.mDownloadListener.onError(e);
                        return;
                    } catch (Exception e2) {
                        e = e2;
                        this.mException = e;
                        this.mFailed = true;
                        this.mDownloadListener.onFailure(e);
                        return;
                    }
                }
                long contentLength = response.getRawResponse().body().contentLength() + 0;
                this.mDownloadListener.onStarted(contentLength);
                byte[] buffer = new byte[8192];
                InputStream inputStream = response.byteStream();
                try {
                    RandomAccessFile output2 = new RandomAccessFile(this.mOutputPath, "rws");
                    try {
                        output2.seek(0L);
                    } catch (Throwable th2) {
                        output = output2;
                        th = th2;
                        downloadedSize = 0;
                    }
                    while (true) {
                        try {
                            n = inputStream.read(buffer);
                        } catch (Throwable th3) {
                            downloadedSize = downloadedSize2;
                            output = output2;
                            th = th3;
                        }
                        if (n <= 0) {
                            InputStream inputStream2 = inputStream;
                            try {
                                $closeResource(null, output2);
                                try {
                                    $closeResource(null, inputStream2);
                                    this.mSuccess = true;
                                    this.mDownloadListener.onSuccess(this.mOutputPath);
                                    return;
                                } catch (DownloadErrorException e3) {
                                    e = e3;
                                    this.mException = e;
                                    this.mFailed = true;
                                    this.mDownloadListener.onError(e);
                                    return;
                                } catch (Exception e4) {
                                    e = e4;
                                    this.mException = e;
                                    this.mFailed = true;
                                    this.mDownloadListener.onFailure(e);
                                    return;
                                }
                            } catch (Throwable th4) {
                                throw th4;
                            }
                        }
                        try {
                            if (this.mCanceled) {
                                try {
                                    this.mDownloadListener.onCanceled();
                                    try {
                                        $closeResource(null, output2);
                                        $closeResource(null, inputStream);
                                        return;
                                    } catch (Throwable th5) {
                                        throw th5;
                                    }
                                } catch (Throwable th6) {
                                    downloadedSize = downloadedSize2;
                                    output = output2;
                                    th = th6;
                                }
                            } else {
                                output2.write(buffer, 0, n);
                                long downloadedSize3 = downloadedSize2 + n;
                                output = output2;
                                downloadedSize = downloadedSize3;
                                InputStream inputStream3 = inputStream;
                                try {
                                    this.mDownloadListener.onProgress(contentLength, downloadedSize3, buffer, n);
                                    inputStream = inputStream3;
                                    output2 = output;
                                    downloadedSize2 = downloadedSize;
                                } catch (Throwable th7) {
                                    th = th7;
                                }
                            }
                        } catch (Throwable th8) {
                            downloadedSize = downloadedSize2;
                            output = output2;
                            th = th8;
                        }
                        try {
                            throw th;
                        } catch (Throwable th9) {
                            try {
                                $closeResource(th, output);
                                throw th9;
                            } catch (Throwable th10) {
                                throw th10;
                            }
                        }
                    }
                } catch (Throwable th11) {
                    throw th11;
                }
            }
        } catch (DownloadErrorException e5) {
            e = e5;
        } catch (Exception e6) {
            e = e6;
        }
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

    public boolean isSuccess() {
        return this.mSuccess;
    }

    public boolean isFailed() {
        return this.mFailed;
    }

    public boolean isCanceled() {
        return this.mCanceled;
    }

    public Exception getException() {
        return this.mException;
    }
}
