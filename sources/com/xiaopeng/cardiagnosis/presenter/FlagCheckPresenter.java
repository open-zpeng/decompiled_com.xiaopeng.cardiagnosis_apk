package com.xiaopeng.cardiagnosis.presenter;

import com.google.gson.Gson;
import com.xiaopeng.commonfunc.Constant;
import com.xiaopeng.commonfunc.model.car.McuModel;
import com.xiaopeng.commonfunc.utils.DataHelp;
import com.xiaopeng.commonfunc.utils.SystemPropertyUtil;
import com.xiaopeng.lib.framework.module.Module;
import com.xiaopeng.lib.framework.moduleinterface.netchannelmodule.http.IHttp;
import com.xiaopeng.lib.framework.moduleinterface.netchannelmodule.http.IResponse;
import com.xiaopeng.lib.framework.netchannelmodule.NetworkChannelsEntry;
import com.xiaopeng.lib.http.server.ServerBean;
import com.xiaopeng.lib.utils.LogUtils;
import com.xiaopeng.lib.utils.ThreadUtils;
import com.xiaopeng.lib.utils.config.CommonConfig;
import java.util.HashMap;
import java.util.Map;
/* loaded from: classes4.dex */
public class FlagCheckPresenter {
    private static final String TAG = "FlagCheckPresenter";
    private static final String URL_CHECK_CAR_USAGE = CommonConfig.HTTP_HOST + "/flow/cv2/vehicle/isFactoryUsage";
    private final McuModel mMcuModel = new McuModel(TAG);

    public void destroy() {
        this.mMcuModel.onDestroy();
    }

    public void checkFactoryMode() {
        ThreadUtils.postDelayed(0, new Runnable() { // from class: com.xiaopeng.cardiagnosis.presenter.-$$Lambda$FlagCheckPresenter$GkzLW-yfBn0LEro76OzhUPvW70c
            @Override // java.lang.Runnable
            public final void run() {
                FlagCheckPresenter.this.lambda$checkFactoryMode$0$FlagCheckPresenter();
            }
        }, 10000L);
    }

    public /* synthetic */ void lambda$checkFactoryMode$0$FlagCheckPresenter() {
        if (this.mMcuModel.isFactoryMode() && isCarNeedDisableFactoryMode()) {
            this.mMcuModel.disableFactoryMode();
        }
    }

    private boolean isCarNeedDisableFactoryMode() {
        boolean z;
        boolean result = false;
        String vin = SystemPropertyUtil.getVIN();
        if (vin.length() > 0) {
            Map<String, String> param = new HashMap<>();
            param.put(Constant.HTTP_KEY_VIN, vin);
            try {
                IHttp http = (IHttp) Module.get(NetworkChannelsEntry.class).get(IHttp.class);
                IResponse response = http.bizHelper().post(URL_CHECK_CAR_USAGE, new Gson().toJson(param)).needAuthorizationInfo().enableSecurityEncoding().build().execute();
                ServerBean serverBean = DataHelp.getServerBean(response);
                if (serverBean.getCode() == 200) {
                    if (serverBean.getData().equals("false")) {
                        z = true;
                        result = z;
                    }
                }
                z = false;
                result = z;
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        } else {
            LogUtils.e(TAG, "Vin length is not correct : " + vin);
        }
        LogUtils.i(TAG, "isCarNeedDisableFactoryMode : " + result);
        return result;
    }
}
