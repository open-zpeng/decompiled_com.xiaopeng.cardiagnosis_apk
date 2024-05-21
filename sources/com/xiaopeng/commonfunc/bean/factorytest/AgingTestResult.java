package com.xiaopeng.commonfunc.bean.factorytest;

import com.xiaopeng.commonfunc.Constant;
import java.lang.reflect.Array;
/* loaded from: classes4.dex */
public class AgingTestResult {
    public int[][] testrecord = (int[][]) Array.newInstance(int.class, 3, 2);

    public AgingTestResult(String data) {
        byte[] bytes = data.getBytes();
        int[][] iArr = this.testrecord;
        iArr[0][0] = bytes[0] - 48;
        iArr[0][1] = bytes[2] - 48;
        iArr[1][0] = bytes[4] - 48;
        iArr[1][1] = bytes[6] - 48;
        iArr[2][0] = bytes[8] - 48;
        iArr[2][1] = bytes[10] - 48;
    }

    public String toString() {
        String str = "iozone     测试次数 ：" + this.testrecord[0][0] + "\niozone     PASS次数 ：" + this.testrecord[0][1] + "\nmemtester  测试次数 ：" + this.testrecord[1][0] + "\nmemtester  PASS次数 ：" + this.testrecord[1][1] + "\ncpu_stress 测试次数 ：" + this.testrecord[2][0] + "\ncpu_stress PASS次数 ：" + this.testrecord[2][1];
        return str;
    }

    public String toStringForAt() {
        String str = "" + this.testrecord[0][0] + Constant.SEPARATOR_STRING + this.testrecord[0][1] + Constant.SEPARATOR_STRING + this.testrecord[1][0] + Constant.SEPARATOR_STRING + this.testrecord[1][1] + Constant.SEPARATOR_STRING + this.testrecord[2][0] + Constant.SEPARATOR_STRING + this.testrecord[2][1];
        return str;
    }

    public byte[] toByteArrayForDm() {
        int[][] iArr = this.testrecord;
        byte[] result = {(byte) (iArr[0][0] & 255), (byte) (iArr[0][1] & 255), (byte) (iArr[1][0] & 255), (byte) (iArr[1][1] & 255), (byte) (iArr[2][0] & 255), (byte) (iArr[2][1] & 255)};
        return result;
    }
}
