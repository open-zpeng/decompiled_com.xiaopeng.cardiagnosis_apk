package com.xiaopeng.commonfunc.utils;

import android.app.Activity;
import android.hardware.Camera;
import android.view.SurfaceHolder;
import java.io.IOException;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
/* loaded from: classes4.dex */
public class CameraUtils {
    public static final int CAMERA_360_OR_BACK = 1;
    public static final int CAMERA_DRIVING_RECORDER = 2;
    public static final int CAMERA_STOP = -1;
    public static final int CAMERA_TOP = 0;
    public static final int DEFAULT_HEIGHT = 720;
    public static final int DEFAULT_WIDTH = 1280;
    public static final int DESIRED_PREVIEW_FPS = 25;
    public static final int DRIVING_RECORDER_STOP = -2;
    public static final int HIGH_HEIGHT = 1080;
    public static final int HIGH_WIDTH = 1920;
    private static final String TAG = "CameraUtils";
    private static Camera mCamera;
    private static int mCameraPreviewFps;
    private static int mCameraID = 1;
    private static int mOrientation = 0;

    public static void openFrontCamera(int expectFps) {
        if (mCamera != null) {
            throw new RuntimeException("camera already initialized!");
        }
        Camera.CameraInfo info = new Camera.CameraInfo();
        int numCameras = Camera.getNumberOfCameras();
        int i = 0;
        while (true) {
            if (i >= numCameras) {
                break;
            }
            Camera.getCameraInfo(i, info);
            if (info.facing != 1) {
                i++;
            } else {
                mCamera = Camera.open(i);
                mCameraID = info.facing;
                break;
            }
        }
        if (mCamera == null) {
            mCamera = Camera.open();
            mCameraID = 0;
        }
        Camera camera = mCamera;
        if (camera == null) {
            throw new RuntimeException("Unable to open camera");
        }
        Camera.Parameters parameters = camera.getParameters();
        mCameraPreviewFps = chooseFixedPreviewFps(parameters, expectFps * 1000);
        parameters.setRecordingHint(true);
        mCamera.setParameters(parameters);
        setPreviewSize(mCamera, DEFAULT_WIDTH, DEFAULT_HEIGHT);
        setPictureSize(mCamera, DEFAULT_WIDTH, DEFAULT_HEIGHT);
        mCamera.setDisplayOrientation(mOrientation);
    }

    public static void openCamera(int cameraID, int expectFps, int width, int height) {
        if (mCamera != null) {
            throw new RuntimeException("camera already initialized!");
        }
        mCamera = Camera.open(cameraID);
        Camera camera = mCamera;
        if (camera == null) {
            throw new RuntimeException("Unable to open camera");
        }
        mCameraID = cameraID;
        Camera.Parameters parameters = camera.getParameters();
        mCameraPreviewFps = chooseFixedPreviewFps(parameters, expectFps * 1000);
        parameters.setRecordingHint(true);
        mCamera.setParameters(parameters);
        setPreviewSize(mCamera, width, height);
        setPictureSize(mCamera, width, height);
        mCamera.setDisplayOrientation(mOrientation);
    }

    public static void startPreviewDisplay(SurfaceHolder holder) {
        Camera camera = mCamera;
        if (camera == null) {
            throw new IllegalStateException("Camera must be set when start preview");
        }
        try {
            camera.setPreviewDisplay(holder);
            mCamera.startPreview();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void switchCamera(int cameraID, SurfaceHolder holder) {
        if (mCameraID == cameraID && mCamera != null) {
            return;
        }
        mCameraID = cameraID;
        releaseCamera();
        openCamera(cameraID, 25, DEFAULT_WIDTH, DEFAULT_HEIGHT);
        startPreviewDisplay(holder);
    }

    public static void releaseCamera() {
        Camera camera = mCamera;
        if (camera != null) {
            camera.stopPreview();
            mCamera.release();
            mCamera = null;
        }
    }

    public static void startPreview() {
        Camera camera = mCamera;
        if (camera != null) {
            camera.startPreview();
        }
    }

    public static void stopPreview() {
        Camera camera = mCamera;
        if (camera != null) {
            camera.stopPreview();
        }
    }

    public static void takePicture(Camera.ShutterCallback shutterCallback, Camera.PictureCallback rawCallback, Camera.PictureCallback pictureCallback) {
        Camera camera = mCamera;
        if (camera != null) {
            camera.takePicture(shutterCallback, rawCallback, pictureCallback);
        }
    }

    public static void setPreviewSize(Camera camera, int expectWidth, int expectHeight) {
        Camera.Parameters parameters = camera.getParameters();
        parameters.setPreviewSize(expectWidth, expectHeight);
        camera.setParameters(parameters);
    }

    public static Camera.Size getPreviewSize() {
        Camera camera = mCamera;
        if (camera != null) {
            return camera.getParameters().getPreviewSize();
        }
        return null;
    }

    public static void setPictureSize(Camera camera, int expectWidth, int expectHeight) {
        Camera.Parameters parameters = camera.getParameters();
        Camera.Size size = calculatePerfectSize(parameters.getSupportedPictureSizes(), expectWidth, expectHeight);
        parameters.setPictureSize(size.width, size.height);
        camera.setParameters(parameters);
    }

    public static Camera.Size getPictureSize() {
        Camera camera = mCamera;
        if (camera != null) {
            return camera.getParameters().getPictureSize();
        }
        return null;
    }

    public static Camera.Size calculatePerfectSize(List<Camera.Size> sizes, int expectWidth, int expectHeight) {
        sortList(sizes);
        Camera.Size result = sizes.get(0);
        boolean widthOrHeight = false;
        for (Camera.Size size : sizes) {
            if (size.width == expectWidth && size.height == expectHeight) {
                return size;
            }
            if (size.width == expectWidth) {
                widthOrHeight = true;
                if (Math.abs(result.height - expectHeight) > Math.abs(size.height - expectHeight)) {
                    result = size;
                }
            } else if (size.height == expectHeight) {
                widthOrHeight = true;
                if (Math.abs(result.width - expectWidth) > Math.abs(size.width - expectWidth)) {
                    result = size;
                }
            } else if (!widthOrHeight && Math.abs(result.width - expectWidth) > Math.abs(size.width - expectWidth) && Math.abs(result.height - expectHeight) > Math.abs(size.height - expectHeight)) {
                result = size;
            }
        }
        return result;
    }

    private static void sortList(List<Camera.Size> list) {
        Collections.sort(list, new Comparator<Camera.Size>() { // from class: com.xiaopeng.commonfunc.utils.CameraUtils.1
            @Override // java.util.Comparator
            public int compare(Camera.Size pre, Camera.Size after) {
                if (pre.width > after.width) {
                    return 1;
                }
                if (pre.width < after.width) {
                    return -1;
                }
                return 0;
            }
        });
    }

    public static int chooseFixedPreviewFps(Camera.Parameters parameters, int expectedThoudandFps) {
        List<int[]> supportedFps = parameters.getSupportedPreviewFpsRange();
        for (int[] entry : supportedFps) {
            if (entry[0] == entry[1] && entry[0] == expectedThoudandFps) {
                parameters.setPreviewFpsRange(entry[0], entry[1]);
                return entry[0];
            }
        }
        int[] temp = new int[2];
        parameters.getPreviewFpsRange(temp);
        if (temp[0] == temp[1]) {
            int guess = temp[0];
            return guess;
        }
        int guess2 = temp[1] / 2;
        return guess2;
    }

    public static int calculateCameraPreviewOrientation(Activity activity) {
        int result;
        Camera.CameraInfo info = new Camera.CameraInfo();
        Camera.getCameraInfo(mCameraID, info);
        int rotation = activity.getWindowManager().getDefaultDisplay().getRotation();
        int degrees = 0;
        if (rotation != 0) {
            if (rotation == 1) {
                degrees = 90;
            } else if (rotation == 2) {
                degrees = 180;
            } else if (rotation == 3) {
                degrees = 270;
            }
        } else {
            degrees = 0;
        }
        if (info.facing == 1) {
            int result2 = (info.orientation + degrees) % 360;
            result = (360 - result2) % 360;
        } else {
            result = ((info.orientation - degrees) + 360) % 360;
        }
        mOrientation = result;
        return result;
    }

    public static int getCameraID() {
        return mCameraID;
    }

    public static int getPreviewOrientation() {
        return mOrientation;
    }

    public static int getCameraPreviewThousandFps() {
        return mCameraPreviewFps;
    }
}
