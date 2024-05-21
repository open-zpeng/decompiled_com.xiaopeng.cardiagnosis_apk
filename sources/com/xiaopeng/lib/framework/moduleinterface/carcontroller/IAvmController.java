package com.xiaopeng.lib.framework.moduleinterface.carcontroller;
/* loaded from: classes4.dex */
public interface IAvmController extends ILifeCycle, com.xiaopeng.lib.framework.moduleinterface.carcontroller.v2extend.IAvmController {

    /* loaded from: classes4.dex */
    public static class CameraAngleEventMsg extends AbstractEventMsg<Float> {
    }

    /* loaded from: classes4.dex */
    public static class CameraDisplayModeEventMsg extends AbstractEventMsg<Integer> {
    }

    /* loaded from: classes4.dex */
    public static class CameraFrontRadarEventMsg extends AbstractEventMsg<byte[]> {
    }

    /* loaded from: classes4.dex */
    public static class CameraHeightEventMsg extends AbstractEventMsg<Boolean> {
    }

    /* loaded from: classes4.dex */
    public static class CameraPanoEventMsg extends AbstractEventMsg<Boolean> {
    }

    /* loaded from: classes4.dex */
    public static class CameraRoofEventMsg extends AbstractEventMsg<Boolean> {
    }

    /* loaded from: classes4.dex */
    public static class CameraRoofMoveStateEventMsg extends AbstractEventMsg<Integer> {
    }

    /* loaded from: classes4.dex */
    public static class CameraRoofPosEventMsg extends AbstractEventMsg<Integer> {
    }

    /* loaded from: classes4.dex */
    public static class CameraRoofStateEventMsg extends AbstractEventMsg<Integer> {
    }

    /* loaded from: classes4.dex */
    public static class CameraSteerAngleEventMsg extends AbstractEventMsg<Float> {
    }

    /* loaded from: classes4.dex */
    public static class CameraTailRadarEventMsg extends AbstractEventMsg<byte[]> {
    }

    int getCameraAngle() throws Exception;

    int getCameraDisplayMode() throws Exception;

    int[] getCameraFailInfo() throws Exception;

    boolean getCameraHeight() throws Exception;

    float[] getFrontRadarData() throws Exception;

    boolean getHasPanoCamera() throws Exception;

    boolean getHasRoofCamera() throws Exception;

    int getRoofCameraPosition() throws Exception;

    int getRoofCameraState() throws Exception;

    int getRoofMoveCameraState() throws Exception;

    float getSteerWheelRotationAngle() throws Exception;

    float[] getTailRadarData() throws Exception;

    void setCameraAngle(int targetAngle) throws Exception;

    void setCameraDisplayMode(int type) throws Exception;

    void setCameraHeight(boolean isUp) throws Exception;
}
