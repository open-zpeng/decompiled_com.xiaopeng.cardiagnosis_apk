package com.xiaopeng.commonfunc.model;

import android.content.Context;
import android.media.AudioManager;
import com.xiaopeng.commonfunc.model.car.McuModel;
import com.xiaopeng.commonfunc.model.car.TboxModel;
import com.xiaopeng.lib.utils.LogUtils;
/* loaded from: classes4.dex */
public class ModeModel {
    private static final String AUDIO_DTS_VER_PREFIX = "DTS VER=";
    private static final String AUDIO_PARAM_DTS_VER = "DTS VER";
    private static final String PARAMETER_FTM_OFF = "ftm_mode=false";
    private static final String PARAMETER_FTM_ON = "ftm_mode=true";
    private static final String TAG = "ModeModel";
    private final AudioManager mAudioManager;
    private final McuModel mMcuModel = new McuModel(TAG);
    private final TboxModel mTboxModel = new TboxModel(TAG);

    public ModeModel(Context context) {
        this.mAudioManager = (AudioManager) context.getSystemService("audio");
    }

    public void enterAudioTestMode() {
        LogUtils.i(TAG, "enterAudioTestMode");
        this.mAudioManager.setParameters(PARAMETER_FTM_ON);
    }

    public void exitAudioTestMode() {
        LogUtils.i(TAG, "enterAudioTestMode");
        this.mAudioManager.setParameters(PARAMETER_FTM_OFF);
    }

    public void enterTboxTestMode() {
        this.mTboxModel.setDvTestReq(1);
    }

    public void exitTboxTestMode() {
        this.mTboxModel.setDvTestReq(0);
    }

    public void enterMcuTestMode() {
        this.mMcuModel.setDvTestReq(1);
    }

    public void exitMcuTestMode() {
        this.mMcuModel.setDvTestReq(0);
    }

    public String getDtsVer() {
        return this.mAudioManager.getParameters(AUDIO_PARAM_DTS_VER).replace(AUDIO_DTS_VER_PREFIX, "");
    }
}
