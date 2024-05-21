package com.xiaopeng.commonfunc.model;

import android.content.Context;
import android.text.TextUtils;
import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;
import com.google.gson.stream.JsonReader;
import com.xiaopeng.commonfunc.Constant;
import com.xiaopeng.commonfunc.bean.factorytest.HereVmapInfo;
import com.xiaopeng.commonfunc.bean.factorytest.VmapConfig;
import com.xiaopeng.commonfunc.bean.http.UuidBean;
import com.xiaopeng.commonfunc.callback.NaviCallback;
import com.xiaopeng.commonfunc.utils.AfterSalesHelper;
import com.xiaopeng.commonfunc.utils.FileUtil;
import com.xiaopeng.commonfunc.utils.HttpUtil;
import com.xiaopeng.lib.framework.moduleinterface.netchannelmodule.http.Callback;
import com.xiaopeng.lib.framework.moduleinterface.netchannelmodule.http.IResponse;
import com.xiaopeng.lib.utils.LogUtils;
import com.xiaopeng.xmlconfig.Support;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.io.InputStreamReader;
/* loaded from: classes4.dex */
public class NaviModel {
    private static final String REGEX_STOCK_UUID = "^[A-Z0-9]{6}HW[A-Z0-9]{24}$";
    private static final String REGEX_UUID = "^[A-Z0-9]{6}UI[A-Z0-9]{24}$";
    private static final String TAG = "NaviModel";
    public static final String VENDOR_AUTONAV = "Autonav";
    public static final String VENDOR_HERE = "Here";
    private final Context mContext;
    private NaviCallback mNaviCallback;
    private VmapConfig mVmapConfig;
    public static final String PATH_NAVI_MAP_CONFIG = Support.Path.getFilePath(Support.Path.NAVI_MAP_CONFIG);
    public static final String PATH_OLD_NAVI_MAP_VERSION = Support.Path.getFilePath(Support.Path.OLD_NAVI_MAP_VERSION);
    private static final String PATH_UUID = Support.Path.getFilePath(Support.Path.NAVI_UUID);
    private static final String MODEL_NAME = Support.Feature.getString(Support.Feature.MODEL_NAME);

    public NaviModel(NaviCallback callback, Context context) {
        this.mNaviCallback = callback;
        this.mContext = context;
        initVmapConfig();
    }

    public NaviModel(Context context) {
        this.mContext = context;
        initVmapConfig();
    }

    private void initVmapConfig() {
        try {
            this.mVmapConfig = (VmapConfig) new Gson().fromJson(FileUtil.read(PATH_NAVI_MAP_CONFIG), (Class<Object>) VmapConfig.class);
        } catch (JsonSyntaxException e) {
            e.printStackTrace();
        }
        VmapConfig vmapConfig = this.mVmapConfig;
        if (vmapConfig != null) {
            LogUtils.d(TAG, vmapConfig.toString());
        }
    }

    public void genUuid() {
        LogUtils.d(TAG, "genUuid");
        AfterSalesHelper.recordRepairModeAction(AfterSalesHelper.REPAIRMODE_ACTION_GEN_NAVI_UUID, AfterSalesHelper.REPAIRMODE_ACTION_TRIGGERED);
        HttpUtil.genUuid(new Callback() { // from class: com.xiaopeng.commonfunc.model.NaviModel.1
            @Override // com.xiaopeng.lib.framework.moduleinterface.netchannelmodule.http.Callback
            public void onSuccess(IResponse iResponse) {
                LogUtils.d(NaviModel.TAG, "genUuid onSuccess");
                if (iResponse.code() == 200) {
                    UuidBean uuidBean = (UuidBean) new Gson().fromJson(iResponse.body(), (Class<Object>) UuidBean.class);
                    LogUtils.i(NaviModel.TAG, "genUuid onSuccess: uuidBean = " + uuidBean);
                    if (uuidBean.getCode() == 200) {
                        String uuid = uuidBean.getData().getUuid();
                        if (NaviModel.this.isUuidValid(uuid) && FileUtil.writeNCreateFile(NaviModel.PATH_UUID, uuid)) {
                            LogUtils.i(NaviModel.TAG, "get uuid success ");
                            AfterSalesHelper.recordRepairModeAction(AfterSalesHelper.REPAIRMODE_ACTION_GEN_NAVI_UUID, "success");
                        }
                    }
                }
                NaviModel.this.updateUuid();
            }

            @Override // com.xiaopeng.lib.framework.moduleinterface.netchannelmodule.http.Callback
            public void onFailure(IResponse iResponse) {
                NaviModel.this.updateUuid();
            }
        });
    }

    public boolean isNeedUuid() {
        if (this.mVmapConfig == null) {
            String str = MODEL_NAME;
            return str.charAt(str.length() - 1) != 'V';
        }
        return VENDOR_AUTONAV.equalsIgnoreCase(readVmapVendor());
    }

    public boolean isUuidValid(String uuid) {
        return uuid != null && (uuid.matches(REGEX_UUID) || uuid.matches(REGEX_STOCK_UUID));
    }

    public String readUuid() {
        return FileUtil.read(PATH_UUID);
    }

    public String readVmapVer() {
        char c;
        String readVmapVendor = readVmapVendor();
        int hashCode = readVmapVendor.hashCode();
        if (hashCode != 2245648) {
            if (hashCode == 1018351860 && readVmapVendor.equals(VENDOR_AUTONAV)) {
                c = 0;
            }
            c = 65535;
        } else {
            if (readVmapVendor.equals(VENDOR_HERE)) {
                c = 1;
            }
            c = 65535;
        }
        if (c == 0) {
            if (this.mVmapConfig != null) {
                String res = readVmapVendor() + Constant.DATA_SEPARATOR_STRING + readRegion() + Constant.DATA_SEPARATOR_STRING + readCreateTime();
                return res;
            }
            return Constant.NA_STRING;
        } else if (c == 1) {
            HereVmapInfo info = null;
            try {
                InputStream inputStream = new FileInputStream(new File(Support.Path.getFilePath(Support.Path.NAVI_MAP_VERSION)));
                info = (HereVmapInfo) new Gson().fromJson(new JsonReader(new InputStreamReader(inputStream)), HereVmapInfo.class);
            } catch (JsonSyntaxException e) {
                e.printStackTrace();
            } catch (FileNotFoundException e2) {
                e2.printStackTrace();
            }
            if (info != null) {
                LogUtils.d(TAG, info.toString());
                String res2 = info.getMapSupplier() + Constant.DATA_SEPARATOR_STRING + info.getRegion() + Constant.DATA_SEPARATOR_STRING + info.getMapVer();
                return res2;
            }
            return Constant.NA_STRING;
        } else {
            String res3 = FileUtil.read(PATH_OLD_NAVI_MAP_VERSION);
            if (TextUtils.isEmpty(res3)) {
                return Constant.NA_STRING;
            }
            return res3;
        }
    }

    public String readCreateTime() {
        int value = 0;
        VmapConfig vmapConfig = this.mVmapConfig;
        if (vmapConfig != null) {
            value = vmapConfig.getVersion();
        }
        return value != 0 ? String.valueOf(value) : Constant.NA_STRING;
    }

    public String readVmapVendor() {
        String res = null;
        VmapConfig vmapConfig = this.mVmapConfig;
        if (vmapConfig != null) {
            res = vmapConfig.getVendor();
        }
        return res != null ? res : Constant.NA_STRING;
    }

    public String readRegion() {
        String res = null;
        VmapConfig vmapConfig = this.mVmapConfig;
        if (vmapConfig != null) {
            res = vmapConfig.getRegion();
        }
        return res != null ? res : Constant.NA_STRING;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void updateUuid() {
        NaviCallback naviCallback = this.mNaviCallback;
        if (naviCallback != null) {
            naviCallback.updateUuid();
        }
    }
}
