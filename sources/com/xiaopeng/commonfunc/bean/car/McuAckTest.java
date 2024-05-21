package com.xiaopeng.commonfunc.bean.car;

import android.util.Log;
import java.util.Arrays;
/* loaded from: classes4.dex */
public class McuAckTest {
    public float BAT_VOL_DET_RESULT;
    public int BAT_VOL_DET_VD;
    public int CAN_TEST_RESULT;
    public int EEPROM_TEST_RESULT;
    public int FMANT_TEST_RESULT;
    public int GNSSANT_TEST_RESULT;
    public int GNSS_COM_TEST_RESULT;
    public int MIC_TEST_RESULT;
    public int TEMP_DET_RESULT;
    public int TEMP_DET_VD;

    public McuAckTest(int[] data) {
        this.EEPROM_TEST_RESULT = data[0] & 255;
        this.CAN_TEST_RESULT = (data[0] >> 8) & 255;
        this.MIC_TEST_RESULT = (data[0] >> 16) & 255;
        this.GNSSANT_TEST_RESULT = (data[0] >> 24) & 255;
        this.FMANT_TEST_RESULT = data[1] & 255;
        this.TEMP_DET_VD = (data[1] >> 8) & 255;
        this.TEMP_DET_RESULT = (byte) ((data[1] >> 16) & 255);
        this.BAT_VOL_DET_VD = (data[1] >> 24) & 255;
        this.BAT_VOL_DET_RESULT = (float) ((data[2] & 65535) / 100.0d);
        this.GNSS_COM_TEST_RESULT = (data[2] >> 16) & 255;
        Log.d("McuAckTest", "McuAckTest data = " + Arrays.toString(data));
    }

    public String toString() {
        String str = "EEPROM_TEST_RESULT=" + this.EEPROM_TEST_RESULT + "\n CAN_TEST_RESULT=" + this.CAN_TEST_RESULT + "\n MIC_TEST_RESULT=" + this.MIC_TEST_RESULT + "\n GNSSANT_TEST_RESULT=" + this.GNSSANT_TEST_RESULT + "\n FMANT_TEST_RESULT=" + this.FMANT_TEST_RESULT + "\n TEMP_DET_VD=" + this.TEMP_DET_VD + "\n TEMP_DET_RESULT=" + this.TEMP_DET_RESULT + "\n BAT_VOL_DET_VD=" + this.BAT_VOL_DET_VD + "\n BAT_VOL_DET_RESULT=" + this.BAT_VOL_DET_RESULT + "\n GNSS_COM_TEST_RESULT=" + this.GNSS_COM_TEST_RESULT;
        return str;
    }
}
