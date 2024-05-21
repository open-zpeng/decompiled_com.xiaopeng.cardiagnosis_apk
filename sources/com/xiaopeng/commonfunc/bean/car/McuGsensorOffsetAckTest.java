package com.xiaopeng.commonfunc.bean.car;
/* loaded from: classes4.dex */
public class McuGsensorOffsetAckTest {
    public int offset_x;
    public int offset_y;
    public int offset_z;

    public McuGsensorOffsetAckTest(int[] data) {
        this.offset_x = data[0];
        this.offset_y = data[1];
        this.offset_z = data[2];
    }

    public String toString() {
        return "McuGsensorOffsetAckTest{offset_x=" + this.offset_x + ", offset_y=" + this.offset_y + ", offset_z=" + this.offset_z + '}';
    }
}
