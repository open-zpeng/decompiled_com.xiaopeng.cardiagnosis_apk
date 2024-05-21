package com.xiaopeng.commonfunc.model.factorytest.hardwaretest;

import android.content.Context;
import android.graphics.Bitmap;
import android.os.Handler;
import android.os.Message;
import android.text.TextUtils;
import com.google.gson.Gson;
import com.xiaopeng.commonfunc.Constant;
import com.xiaopeng.commonfunc.bean.event.HWAndSWInfoEvent;
import com.xiaopeng.commonfunc.callback.HWAndSWInfoCallBack;
import com.xiaopeng.commonfunc.model.car.McuModel;
import com.xiaopeng.commonfunc.utils.DataHelp;
import com.xiaopeng.commonfunc.utils.QRCodeUtil;
import com.xiaopeng.commonfunc.utils.SystemPropertyUtil;
import com.xiaopeng.lib.framework.module.Module;
import com.xiaopeng.lib.framework.moduleinterface.netchannelmodule.http.Callback;
import com.xiaopeng.lib.framework.moduleinterface.netchannelmodule.http.IHttp;
import com.xiaopeng.lib.framework.moduleinterface.netchannelmodule.http.IResponse;
import com.xiaopeng.lib.framework.netchannelmodule.NetworkChannelsEntry;
import com.xiaopeng.lib.http.ICallback;
import com.xiaopeng.lib.http.server.ServerBean;
import com.xiaopeng.lib.utils.LogUtils;
import com.xiaopeng.lib.utils.config.CommonConfig;
import com.xiaopeng.lib.utils.info.BuildInfoUtils;
import com.xiaopeng.xmlconfig.Support;
import java.lang.ref.WeakReference;
import java.util.HashMap;
import java.util.Map;
import org.greenrobot.eventbus.EventBus;
import org.json.JSONObject;
/* loaded from: classes4.dex */
public class HWAndSWInfoModel implements IHWAndSWInfoModel {
    private static final int MAX_TIMEOUT = 3000;
    private static final int MESSAGE_GENERATE_CAR_CODE = 3;
    private static final int MESSAGE_GENERATE_DEVICE_CODE = 1;
    private static final int MESSAGE_GENERATE_DEVICE_NUM = 2;
    private static final String TAG = "HWAndSWInfoModel";
    private HWAndSWInfoCallBack mHWAndSWInfoCallBack;
    private static final Boolean SEPARATE_UPLOAD_TBOX = Boolean.valueOf(Support.Feature.getBoolean(Support.Feature.SEPARATE_UPLOAD_TBOX));
    private static final String URL_CAR_CODE = CommonConfig.HTTP_HOST + Support.Url.getUrl(Support.Url.CAR_CODE);
    private final Object mLock = new Object();
    private final Handler mHandler = new Handler() { // from class: com.xiaopeng.commonfunc.model.factorytest.hardwaretest.HWAndSWInfoModel.1
        @Override // android.os.Handler
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            Bitmap bitmap = (Bitmap) msg.obj;
            int i = msg.what;
            if (i == 1) {
                if (bitmap != null) {
                    HWAndSWInfoModel.this.updateDeviceCodeBitmap(bitmap);
                }
            } else if (i == 2) {
                if (bitmap != null) {
                    HWAndSWInfoModel.this.updateDeviceNumBitmap(bitmap);
                }
            } else if (i == 3 && bitmap != null) {
                HWAndSWInfoModel.this.updateCarCodeBitmap(bitmap);
            }
        }
    };

    public HWAndSWInfoModel(HWAndSWInfoCallBack callBack) {
        this.mHWAndSWInfoCallBack = callBack;
    }

    public HWAndSWInfoModel() {
    }

    @Override // com.xiaopeng.commonfunc.model.factorytest.hardwaretest.IHWAndSWInfoModel
    public void init(Context context) {
    }

    @Override // com.xiaopeng.commonfunc.model.factorytest.hardwaretest.IHWAndSWInfoModel
    public String getHWInfo() {
        return SystemPropertyUtil.getHwVersion();
    }

    @Override // com.xiaopeng.commonfunc.model.factorytest.hardwaretest.IHWAndSWInfoModel
    public String getSWInfo() {
        return SystemPropertyUtil.getSwVersion();
    }

    @Override // com.xiaopeng.commonfunc.model.factorytest.hardwaretest.IHWAndSWInfoModel
    public String getDeviceCode() {
        String hardwareId = SystemPropertyUtil.getHardwareId();
        return TextUtils.isEmpty(hardwareId) ? Support.Properties.PROPERTIES_DEFAULT_STRING : hardwareId;
    }

    @Override // com.xiaopeng.commonfunc.model.factorytest.hardwaretest.IHWAndSWInfoModel
    public void generateCodeBitmap(int width) {
        new Thread(new GenerateRunnable(1, width, this.mHandler)).start();
    }

    @Override // com.xiaopeng.commonfunc.model.factorytest.hardwaretest.IHWAndSWInfoModel
    public void generateNumBitmap(int width) {
        new Thread(new GenerateRunnable(2, width, this.mHandler)).start();
    }

    @Override // com.xiaopeng.commonfunc.model.factorytest.hardwaretest.IHWAndSWInfoModel
    public String getVehicleId() {
        String vehicleId = String.valueOf(SystemPropertyUtil.getVehicleId());
        return TextUtils.isEmpty(vehicleId) ? Support.Properties.PROPERTIES_DEFAULT_STRING : vehicleId;
    }

    @Override // com.xiaopeng.commonfunc.model.factorytest.hardwaretest.IHWAndSWInfoModel
    public void generateCarCodeBitmap(final int width) {
        requestCarCode(new ICallback<String, String>() { // from class: com.xiaopeng.commonfunc.model.factorytest.hardwaretest.HWAndSWInfoModel.2
            @Override // com.xiaopeng.lib.http.ICallback
            public void onSuccess(String data) {
                HWAndSWInfoModel hWAndSWInfoModel = HWAndSWInfoModel.this;
                new Thread(new GenerateRunnable(3, width, data, hWAndSWInfoModel.mHandler)).start();
            }

            @Override // com.xiaopeng.lib.http.ICallback
            public void onError(String err) {
                EventBus.getDefault().post(new HWAndSWInfoEvent(1));
            }
        });
    }

    @Override // com.xiaopeng.commonfunc.model.factorytest.hardwaretest.IHWAndSWInfoModel
    public void getPsuVersion() {
    }

    @Override // com.xiaopeng.commonfunc.model.factorytest.hardwaretest.IHWAndSWInfoModel
    public void requestCarCode(final ICallback<String, String> callback) {
        Map<String, String> param = new HashMap<>();
        if (!SEPARATE_UPLOAD_TBOX.booleanValue()) {
            param.put(Constant.HTTP_KEY_ICCID, SystemPropertyUtil.getIccid());
        }
        param.put("cduId", SystemPropertyUtil.getHardwareId());
        param.put("partsNo", SystemPropertyUtil.getPartNumber());
        param.put("hVer", SystemPropertyUtil.getHwVersion());
        param.put("sVer", BuildInfoUtils.getSystemVersion());
        LogUtils.d(TAG, "requestCarCode param = " + param.toString());
        IHttp http = (IHttp) Module.get(NetworkChannelsEntry.class).get(IHttp.class);
        http.cancelTag(URL_CAR_CODE);
        http.bizHelper().post(URL_CAR_CODE, new Gson().toJson(param)).build().tag(URL_CAR_CODE).execute(new Callback() { // from class: com.xiaopeng.commonfunc.model.factorytest.hardwaretest.HWAndSWInfoModel.3
            @Override // com.xiaopeng.lib.framework.moduleinterface.netchannelmodule.http.Callback
            public void onSuccess(IResponse iResponse) {
                ServerBean bean = DataHelp.getServerBean(iResponse);
                if (bean.getCode() == 200) {
                    String content = bean.getData();
                    LogUtils.d(HWAndSWInfoModel.TAG, "" + content);
                    if (TextUtils.isEmpty(content)) {
                        ICallback iCallback = callback;
                        if (iCallback != null) {
                            iCallback.onError(null);
                            return;
                        }
                        return;
                    }
                    try {
                        JSONObject jsonObject = new JSONObject(content);
                        if (callback != null) {
                            callback.onSuccess(jsonObject.getString("qrCode"));
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                        ICallback iCallback2 = callback;
                        if (iCallback2 != null) {
                            iCallback2.onError(null);
                        }
                    }
                }
            }

            @Override // com.xiaopeng.lib.framework.moduleinterface.netchannelmodule.http.Callback
            public void onFailure(IResponse iResponse) {
                ICallback iCallback = callback;
                if (iCallback != null) {
                    iCallback.onError(null);
                }
            }
        });
    }

    private void asyncGetPartCode(ICallback<String, String> callback) {
        synchronized (this.mLock) {
            String data = new McuModel(TAG).getFactoryDisplayTypeMsgToMcu();
            if (!TextUtils.isEmpty(data)) {
                if (callback != null) {
                    callback.onSuccess(data);
                }
            } else if (callback != null) {
                callback.onError(null);
            }
        }
    }

    @Override // com.xiaopeng.commonfunc.model.factorytest.hardwaretest.IHWAndSWInfoModel
    public void onDestroy() {
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* loaded from: classes4.dex */
    public class GenerateRunnable implements Runnable {
        private String mData;
        private final WeakReference<Handler> mWeakReference;
        private final int mWhat;
        private final int mWidth;

        public GenerateRunnable(int what, int width, Handler handler) {
            this.mWhat = what;
            this.mWidth = width;
            this.mWeakReference = new WeakReference<>(handler);
        }

        public GenerateRunnable(int what, int width, String data, Handler handler) {
            this.mWhat = what;
            this.mWidth = width;
            this.mData = data;
            this.mWeakReference = new WeakReference<>(handler);
        }

        @Override // java.lang.Runnable
        public void run() {
            int i = this.mWhat;
            if (i == 1) {
                String deviceCode = HWAndSWInfoModel.this.getDeviceCode();
                int i2 = this.mWidth;
                Bitmap bitmap = QRCodeUtil.createQRImage(deviceCode, i2, i2, null);
                sendMsg(bitmap);
            } else if (i == 2) {
                String iccid = SystemPropertyUtil.getIccid();
                int i3 = this.mWidth;
                Bitmap bitmap2 = QRCodeUtil.createQRImage(iccid, i3, i3, null);
                sendMsg(bitmap2);
            } else if (i == 3) {
                String str = this.mData;
                int i4 = this.mWidth;
                Bitmap bitmap3 = QRCodeUtil.createQRImage(str, i4, i4, null);
                sendMsg(bitmap3);
            }
        }

        private void sendMsg(Bitmap bitmap) {
            if (this.mWeakReference.get() != null && bitmap != null) {
                Message message = this.mWeakReference.get().obtainMessage();
                message.what = this.mWhat;
                message.obj = bitmap;
                this.mWeakReference.get().sendMessage(message);
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void updateDeviceCodeBitmap(Bitmap bitmap) {
        HWAndSWInfoCallBack hWAndSWInfoCallBack = this.mHWAndSWInfoCallBack;
        if (hWAndSWInfoCallBack != null) {
            hWAndSWInfoCallBack.updateDeviceCodeBitmap(bitmap);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void updateDeviceNumBitmap(Bitmap bitmap) {
        HWAndSWInfoCallBack hWAndSWInfoCallBack = this.mHWAndSWInfoCallBack;
        if (hWAndSWInfoCallBack != null) {
            hWAndSWInfoCallBack.updateDeviceNumBitmap(bitmap);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void updateCarCodeBitmap(Bitmap bitmap) {
        HWAndSWInfoCallBack hWAndSWInfoCallBack = this.mHWAndSWInfoCallBack;
        if (hWAndSWInfoCallBack != null) {
            hWAndSWInfoCallBack.updateCarCodeBitmap(bitmap);
        }
    }
}
