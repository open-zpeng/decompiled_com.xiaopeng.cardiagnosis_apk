package com.ta.utdid2.b.a;
/* compiled from: IntUtils.java */
/* loaded from: classes4.dex */
public class e {
    public static byte[] getBytes(int i) {
        byte[] bArr = {(byte) ((r1 >> 8) % 256), (byte) (r1 % 256), (byte) (r1 % 256), (byte) (i % 256)};
        int i2 = i >> 8;
        int i3 = i2 >> 8;
        return bArr;
    }
}
