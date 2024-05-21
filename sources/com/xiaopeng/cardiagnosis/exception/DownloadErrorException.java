package com.xiaopeng.cardiagnosis.exception;
/* loaded from: classes4.dex */
public class DownloadErrorException extends Exception {
    private final int code;

    public DownloadErrorException(int code, String message) {
        super(message);
        this.code = code;
    }

    public DownloadErrorException(int code, String message, Throwable throwable) {
        super(message, throwable);
        this.code = code;
    }

    public int getCode() {
        return this.code;
    }
}
