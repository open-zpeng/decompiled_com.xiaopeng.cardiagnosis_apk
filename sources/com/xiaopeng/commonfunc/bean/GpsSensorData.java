package com.xiaopeng.commonfunc.bean;
/* loaded from: classes4.dex */
public class GpsSensorData {
    private double mAcc_X;
    private double mAcc_Y;
    private double mAcc_Z;
    private double mGyro_X;
    private double mGyro_Y;
    private double mGyro_Z;

    public GpsSensorData(double mAcc_X, double mAcc_Y, double mAcc_Z, double mGyro_X, double mGyro_Y, double mGyro_Z) {
        this.mAcc_X = mAcc_X;
        this.mAcc_Y = mAcc_Y;
        this.mAcc_Z = mAcc_Z;
        this.mGyro_X = mGyro_X;
        this.mGyro_Y = mGyro_Y;
        this.mGyro_Z = mGyro_Z;
    }

    public double getAcc_X() {
        return this.mAcc_X;
    }

    public void setAcc_X(double mAcc_X) {
        this.mAcc_X = mAcc_X;
    }

    public double getAcc_Y() {
        return this.mAcc_Y;
    }

    public void setAcc_Y(double mAcc_Y) {
        this.mAcc_Y = mAcc_Y;
    }

    public double getAcc_Z() {
        return this.mAcc_Z;
    }

    public void setAcc_Z(double mAcc_Z) {
        this.mAcc_Z = mAcc_Z;
    }

    public double getGyro_X() {
        return this.mGyro_X;
    }

    public void setGyro_X(double mGyro_X) {
        this.mGyro_X = mGyro_X;
    }

    public double getGyro_Y() {
        return this.mGyro_Y;
    }

    public void setGyro_Y(double mGyro_Y) {
        this.mGyro_Y = mGyro_Y;
    }

    public double getGyro_Z() {
        return this.mGyro_Z;
    }

    public void setGyro_Z(double mGyro_Z) {
        this.mGyro_Z = mGyro_Z;
    }

    public String toString() {
        return "GpsSensorData{mAcc_X=" + this.mAcc_X + ", mAcc_Y=" + this.mAcc_Y + ", mAcc_Z=" + this.mAcc_Z + ", mGyro_X=" + this.mGyro_X + ", mGyro_Y=" + this.mGyro_Y + ", mGyro_Z=" + this.mGyro_Z + '}';
    }
}
