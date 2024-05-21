package com.xiaopeng.commonfunc.bean.car;
/* loaded from: classes4.dex */
public class EcuUpdateReq {
    private String ImgLocation;
    private String Target;

    public EcuUpdateReq(String target, String imgLocation) {
        this.Target = target;
        this.ImgLocation = imgLocation;
    }

    public String getTarget() {
        return this.Target;
    }

    public void setTarget(String target) {
        this.Target = target;
    }

    public String getImgLocation() {
        return this.ImgLocation;
    }

    public void setImgLocation(String imgLocation) {
        this.ImgLocation = imgLocation;
    }

    public String toString() {
        return "EcuUpdateReq{Target='" + this.Target + "', ImgLocation='" + this.ImgLocation + "'}";
    }
}
