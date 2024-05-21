package com.xiaopeng.commonfunc.bean.car;

import android.util.Log;
import java.util.Arrays;
/* loaded from: classes4.dex */
public class AndroidPsuOtaMsg {
    public int otaSt;
    public int psuResetReq;
    public int reserved0;
    public int reserved1;
    public int reserved2;
    public int wakeUpHour;
    public int wakeUpMin;
    public int wakeUpSecond;
    public int wakeUpSetReq;
    public int wakeUpSetType;

    public int[] packToIntArray() {
        int[] data = new int[4];
        data[0] = this.psuResetReq & 255;
        data[0] = data[0] | ((this.wakeUpSetReq & 255) << 8);
        data[0] = data[0] | ((this.wakeUpSetType & 255) << 16);
        data[0] = data[0] | ((this.wakeUpHour & 255) << 24);
        data[1] = this.wakeUpMin & 255;
        data[1] = data[1] | ((this.wakeUpSecond & 255) << 8);
        data[1] = data[1] | ((this.otaSt & 255) << 16);
        data[1] = data[1] | ((this.reserved0 & 255) << 24);
        data[2] = this.reserved1 & 255;
        data[2] = data[2] | ((this.reserved2 & 255) << 8);
        Log.d("AndroidPsuOtaMsg", "packToIntArray data = " + Arrays.toString(data));
        return data;
    }
}
