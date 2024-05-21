package com.xiaopeng.commonfunc.bean.event;

import com.xiaopeng.commonfunc.utils.DataHelp;
/* loaded from: classes4.dex */
public class CameraResultEvent {
    private int mCameraId;
    private String mPath;
    private final boolean mResult;
    private final int mStep;

    public CameraResultEvent(int step, int cameraId, boolean result) {
        this.mStep = step;
        this.mCameraId = cameraId;
        this.mResult = result;
    }

    public CameraResultEvent(int step, boolean result) {
        this.mStep = step;
        this.mResult = result;
    }

    public CameraResultEvent(int step, boolean result, String path) {
        this.mStep = step;
        this.mResult = result;
        this.mPath = path;
    }

    public int getStep() {
        return this.mStep;
    }

    public int getCameraId() {
        return this.mCameraId;
    }

    public boolean getResult() {
        return this.mResult;
    }

    public String getPath() {
        return this.mPath;
    }

    public byte[] getDmOpenCamArgu() {
        byte[] res = {1, DataHelp.intToByte(this.mCameraId)};
        return res;
    }
}
