package com.xiaopeng.commonfunc.bean.event;
/* loaded from: classes4.dex */
public class CameraTestEvent {
    public static final int CAMERA_CLOSE = 0;
    public static final int CAMERA_OPEN = 1;
    public static final int CAMERA_TAKEPIC = 2;
    private int mCameraId;
    private final int mStep;

    public CameraTestEvent(int step, int cameraId) {
        this.mStep = step;
        this.mCameraId = cameraId;
    }

    public CameraTestEvent(int step) {
        this.mStep = step;
    }

    public int getStep() {
        return this.mStep;
    }

    public int getCameraId() {
        return this.mCameraId;
    }
}
