package com.xiaopeng.cardiagnosis.presenter;

import android.car.hardware.CarPropertyValue;
import android.content.BroadcastReceiver;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.ConnectivityManager;
import android.net.Network;
import android.os.Handler;
import android.os.HandlerThread;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;
import com.xiaopeng.aftersales.manager.AlertListener;
import com.xiaopeng.aftersales.manager.AuthModeListener;
import com.xiaopeng.aftersales.manager.DiagnosisStatusListener;
import com.xiaopeng.aftersales.manager.LogicActionListener;
import com.xiaopeng.aftersales.manager.RepairModeListener;
import com.xiaopeng.cardiagnosis.R;
import com.xiaopeng.cardiagnosis.presenter.AfterSalesPresenter;
import com.xiaopeng.cardiagnosis.runnable.Sleep;
import com.xiaopeng.commonfunc.Constant;
import com.xiaopeng.commonfunc.bean.CheckModeStatus;
import com.xiaopeng.commonfunc.bean.DiagnosisError;
import com.xiaopeng.commonfunc.bean.ParamRepairMode;
import com.xiaopeng.commonfunc.model.NaviModel;
import com.xiaopeng.commonfunc.model.car.BcmModel;
import com.xiaopeng.commonfunc.model.car.CarEventChangedListener;
import com.xiaopeng.commonfunc.model.car.IcmModel;
import com.xiaopeng.commonfunc.model.car.McuModel;
import com.xiaopeng.commonfunc.model.car.TboxModel;
import com.xiaopeng.commonfunc.model.car.VcuModel;
import com.xiaopeng.commonfunc.utils.ActivityUtil;
import com.xiaopeng.commonfunc.utils.AfterSalesHelper;
import com.xiaopeng.commonfunc.utils.DataHelp;
import com.xiaopeng.commonfunc.utils.HttpUtil;
import com.xiaopeng.commonfunc.utils.OTAServiceHelper;
import com.xiaopeng.commonfunc.utils.ProcessUtil;
import com.xiaopeng.commonfunc.utils.SystemPropertyUtil;
import com.xiaopeng.commonfunc.utils.UIUtil;
import com.xiaopeng.datalog.DataLogModuleEntry;
import com.xiaopeng.lib.framework.module.Module;
import com.xiaopeng.lib.framework.moduleinterface.datalogmodule.IDataLog;
import com.xiaopeng.lib.framework.moduleinterface.datalogmodule.IMoleEventBuilder;
import com.xiaopeng.lib.framework.moduleinterface.netchannelmodule.http.Callback;
import com.xiaopeng.lib.framework.moduleinterface.netchannelmodule.http.IHttp;
import com.xiaopeng.lib.framework.moduleinterface.netchannelmodule.http.IResponse;
import com.xiaopeng.lib.framework.netchannelmodule.NetworkChannelsEntry;
import com.xiaopeng.lib.http.server.ServerBean;
import com.xiaopeng.lib.utils.LogUtils;
import com.xiaopeng.lib.utils.ThreadUtils;
import com.xiaopeng.lib.utils.config.CommonConfig;
import com.xiaopeng.xmlconfig.Support;
import com.xiaopeng.xui.app.XDialog;
import com.xiaopeng.xui.app.XDialogInterface;
import com.xiaopeng.xui.app.XDialogSystemType;
import com.xiaopeng.xui.app.XToast;
import com.xiaopeng.xui.widget.XButton;
import com.xiaopeng.xui.widget.XLinearLayout;
import com.xiaopeng.xui.widget.XLoading;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import org.json.JSONException;
import org.json.JSONObject;
/* loaded from: classes4.dex */
public class AfterSalesPresenter {
    private static final String DATA_MQTT_SERVER = "iot2.xiaopeng.com";
    private static final int FUNC_CANCEL_SPEED_LIMIT = 3;
    private static final int FUNC_ENTER_CHECKMODE = 4;
    private static final int FUNC_ENTER_IVI_DIAGNOSIS = 2;
    private static final int FUNC_QUIT_CHECKMODE = 1;
    private static final String TAG = "AfterSalesPresenter";
    private XButton mBtCancelSpeedLimit;
    private XButton mBtEnterDiagnosis;
    private XDialog mCheckModeDialog;
    private ConnectivityManager mConnectivityManager;
    private Context mContext;
    private Handler mHandler;
    private HandlerThread mHandlerThread;
    private int mLastSpeedLimitStatus;
    private XLoading mLdSpeedLimitStatus;
    private XLinearLayout mLlSpeedLimit;
    private final NaviModel mNaviModel;
    private static final String URL_REPAIR_MODE = HttpUtil.getHost(Support.Url.UPLOAD_REPAIR_MODE) + Support.Url.getUrl(Support.Url.UPLOAD_REPAIR_MODE);
    private static final String URL_DIAGNOSIS_UPLOAD = CommonConfig.HTTP_HOST + Support.Url.getUrl(Support.Url.UPLOAD_ERROR_CODE);
    private static final String URL_SHARE_AUTHMODE = CommonConfig.HTTP_HOST + Support.Url.getUrl(Support.Url.UPLOAD_AUTH_MODE);
    private static final String URL_UPLOAD_REPAIR_MODE_STATUS = HttpUtil.getHost(Support.Url.UPLOAD_REPAIR_MODE_STATUS) + Support.Url.getUrl(Support.Url.UPLOAD_REPAIR_MODE_STATUS);
    private static final String URL_REQ_TARGET_REPAIR_MODE_STATUS = HttpUtil.getHost(Support.Url.REQUEST_TARGET_CHECK_MODE) + Support.Url.getUrl(Support.Url.REQUEST_TARGET_CHECK_MODE);
    private static final boolean SUPPORT_SPEED_LIMIT = Support.Feature.getBoolean(Support.Feature.SUPPORT_SPEED_LIMIT);
    private static final boolean SUPPORT_CAR_DIAGNOSTIC_APP = Support.Feature.getBoolean(Support.Feature.SUPPORT_CAR_DIAGNOSTIC_APP);
    private static final boolean SUPPORT_LCD_DTC = Support.Feature.getBoolean(Support.Feature.SUPPORT_LCD_DTC);
    private static final boolean NEED_NAPA_INIT_FINISH = Support.Feature.getBoolean(Support.Feature.NEED_NAPA_INIT_FINISH);
    private static int TRYNUMBER = 4;
    private boolean mNeedUpdateCheckModeDialog = false;
    private volatile boolean mMqttNetworkAvailable = false;
    private boolean mIsNapaFinish = false;
    private final BroadcastReceiver mBroadcastReceiver = new BroadcastReceiver() { // from class: com.xiaopeng.cardiagnosis.presenter.AfterSalesPresenter.1
        @Override // android.content.BroadcastReceiver
        public void onReceive(Context context, Intent intent) {
            String action = intent.getAction();
            Log.d(AfterSalesPresenter.TAG, "onReceive, action--->" + action);
            if (((action.hashCode() == 1658620318 && action.equals(Constant.ACTION_ACTIVITY_CHANGED)) ? (char) 0 : (char) 65535) == 0) {
                String component = intent.getStringExtra(Constant.EXTRA_COMPONENT);
                LogUtils.i(AfterSalesPresenter.TAG, "component [%s] mNeedUpdateCheckModeDialog[%s]", component, Boolean.valueOf(AfterSalesPresenter.this.mNeedUpdateCheckModeDialog));
                if (!TextUtils.isEmpty(component)) {
                    if (Constant.ACTIVITY_AFTERSALES.equalsIgnoreCase(component)) {
                        AfterSalesPresenter.this.mNeedUpdateCheckModeDialog = true;
                        return;
                    }
                    String packageName = ComponentName.unflattenFromString(component).getPackageName();
                    LogUtils.i(AfterSalesPresenter.TAG, "packageName : " + packageName);
                    if (AfterSalesPresenter.this.mNeedUpdateCheckModeDialog && !Constant.PACKAGE_NAME_OOBE.equalsIgnoreCase(packageName) && !Constant.PACKAGE_NAME_INSTRUMENT.equalsIgnoreCase(packageName) && !"com.xiaopeng.devtools".equalsIgnoreCase(packageName) && !Constant.PACKAGE_NAME_DIAGNOSTIC.equalsIgnoreCase(packageName)) {
                        AfterSalesPresenter.this.updateCheckModeDiag();
                        AfterSalesPresenter.this.mNeedUpdateCheckModeDialog = false;
                    }
                }
            }
        }
    };
    private final CarEventChangedListener mEventChange = new AnonymousClass2();
    private final RepairModeListener mRepairModeListener = new RepairModeListener() { // from class: com.xiaopeng.cardiagnosis.presenter.-$$Lambda$AfterSalesPresenter$TrxrR_z1yCAAkAJrA0GyeR4e7KI
        public final void onRepairModeChanged(boolean z, int i) {
            AfterSalesPresenter.this.lambda$new$1$AfterSalesPresenter(z, i);
        }
    };
    private final AlertListener mAlertListener = new AlertListener() { // from class: com.xiaopeng.cardiagnosis.presenter.-$$Lambda$AfterSalesPresenter$5x8y64okSMEBh9BDzYYm6z9Adf4
        public final void alertDiagnosisError(int i, int i2, long j, String str) {
            AfterSalesPresenter.this.lambda$new$3$AfterSalesPresenter(i, i2, j, str);
        }
    };
    private final DiagnosisStatusListener mDiagnosisStatusListener = new DiagnosisStatusListener() { // from class: com.xiaopeng.cardiagnosis.presenter.-$$Lambda$AfterSalesPresenter$xFo3hMx3P0gHsNe1wniwW4sJgD8
        public final void onDiagnosisStatusChanged(int i, int i2, long j, String str, int i3) {
            AfterSalesPresenter.this.lambda$new$4$AfterSalesPresenter(i, i2, j, str, i3);
        }
    };
    private final AuthModeListener mAuthModeListener = new AuthModeListener() { // from class: com.xiaopeng.cardiagnosis.presenter.-$$Lambda$AfterSalesPresenter$D2Fw3ihwCVqAStT1LoFtCK_VgFo
        public final void onAuthModeChanged(boolean z, int i) {
            AfterSalesPresenter.this.lambda$new$6$AfterSalesPresenter(z, i);
        }
    };
    private LogicActionListener mLogicActionListener = new LogicActionListener() { // from class: com.xiaopeng.cardiagnosis.presenter.-$$Lambda$AfterSalesPresenter$tDl3ZxkHf-1OB3n4KgeGLWXuI9c
        public final void uploadLogicAction(String str, String str2, String str3, String str4, String str5, String str6, String str7) {
            AfterSalesPresenter.this.lambda$new$7$AfterSalesPresenter(str, str2, str3, str4, str5, str6, str7);
        }
    };
    private ConnectivityManager.NetworkCallback mNetworkCallback = new ConnectivityManager.NetworkCallback() { // from class: com.xiaopeng.cardiagnosis.presenter.AfterSalesPresenter.3
        @Override // android.net.ConnectivityManager.NetworkCallback
        public void onAvailable(Network network) {
            LogUtils.i(AfterSalesPresenter.TAG, "NetWorkObserver onAvailable:" + network);
            AfterSalesPresenter.this.updateMqttNetworkStatus();
        }

        @Override // android.net.ConnectivityManager.NetworkCallback
        public void onLost(Network network) {
            LogUtils.i(AfterSalesPresenter.TAG, "NetWorkObserver onLost:" + network);
            AfterSalesPresenter.this.mMqttNetworkAvailable = false;
        }
    };
    private IDataLog mDataLog = (IDataLog) Module.get(DataLogModuleEntry.class).get(IDataLog.class);
    private final TboxModel mTboxModel = new TboxModel(TAG);
    private McuModel mMcuModel = new McuModel(TAG);
    private final IcmModel mIcmModel = new IcmModel(TAG);
    private final BcmModel mBcmModel = new BcmModel(TAG);
    private VcuModel mVcuModel = new VcuModel(TAG);

    /* JADX INFO: Access modifiers changed from: package-private */
    /* renamed from: com.xiaopeng.cardiagnosis.presenter.AfterSalesPresenter$2  reason: invalid class name */
    /* loaded from: classes4.dex */
    public class AnonymousClass2 implements CarEventChangedListener {
        AnonymousClass2() {
        }

        @Override // com.xiaopeng.commonfunc.model.car.CarEventChangedListener
        public void onChangeEvent(CarPropertyValue carPropertyValue) {
            int id = carPropertyValue.getPropertyId();
            Object propertyValue = carPropertyValue.getValue();
            if (id == 554702431) {
                try {
                    DiagnosisError diagnosisError = (DiagnosisError) new Gson().fromJson((String) propertyValue, (Class<Object>) DiagnosisError.class);
                    AfterSalesHelper.getAfterSalesManager().recordDiagnosisError(diagnosisError.getModule(), diagnosisError.getErrorCode(), System.currentTimeMillis(), diagnosisError.getErrorMsg(), false);
                } catch (JsonSyntaxException e) {
                    e.printStackTrace();
                }
            } else if (id == 557846543) {
                if (((Integer) propertyValue).intValue() == 1) {
                    AfterSalesPresenter.this.uploadRepairModeStatusByRegion();
                }
            } else if (id == 557847561) {
                int value = ((Integer) propertyValue).intValue();
                if (value == 1 || value == 2) {
                    AfterSalesPresenter.this.mMcuModel.sendMapVersion(AfterSalesPresenter.this.mNaviModel.readVmapVer());
                    AfterSalesPresenter.this.shareAuthMode(AfterSalesHelper.getAfterSalesManager().getAuthMode());
                    if (!AfterSalesPresenter.SUPPORT_SPEED_LIMIT) {
                        ThreadUtils.execute(new Runnable() { // from class: com.xiaopeng.cardiagnosis.presenter.-$$Lambda$AfterSalesPresenter$2$yd-5jy_5IykHOEHNFXp9_vOVas8
                            @Override // java.lang.Runnable
                            public final void run() {
                                AfterSalesPresenter.AnonymousClass2.this.lambda$onChangeEvent$0$AfterSalesPresenter$2();
                            }
                        });
                    } else {
                        ThreadUtils.execute(new Runnable() { // from class: com.xiaopeng.cardiagnosis.presenter.-$$Lambda$AfterSalesPresenter$2$3bPOV01C5zvIgcZfIr3HuhCtnms
                            @Override // java.lang.Runnable
                            public final void run() {
                                AfterSalesPresenter.AnonymousClass2.this.lambda$onChangeEvent$1$AfterSalesPresenter$2();
                            }
                        });
                    }
                    Support.Properties.set(Support.Properties.IG_ON_TIME, String.valueOf(System.currentTimeMillis()));
                }
            }
        }

        public /* synthetic */ void lambda$onChangeEvent$0$AfterSalesPresenter$2() {
            AfterSalesPresenter.this.uploadRepairModeStatusByRegion();
            AfterSalesPresenter.this.checkTopActivityForCheckModeDiag();
        }

        public /* synthetic */ void lambda$onChangeEvent$1$AfterSalesPresenter$2() {
            if (AfterSalesHelper.getAfterSalesManager().getRepairMode()) {
                LogUtils.i(AfterSalesPresenter.TAG, "IG_ON try to set vcu speed limit mode setIGONsetLimit true");
                SystemPropertyUtil.setIGONsetLimit(true);
                for (int tryCount = 1; OTAServiceHelper.getVcuMode() != 3 && tryCount < AfterSalesPresenter.TRYNUMBER; tryCount++) {
                    LogUtils.i(AfterSalesPresenter.TAG, "IG_ON try to set vcu speed limit mode tryCount : " + tryCount);
                    Sleep.sleep(1000L);
                    AfterSalesPresenter.this.setRepairMode(4, null, OTAServiceHelper.POWERON);
                }
            }
            AfterSalesPresenter.this.uploadRepairModeStatusByRegion();
            AfterSalesPresenter.this.checkTopActivityForCheckModeDiag();
            LogUtils.i(AfterSalesPresenter.TAG, "IG_ON try to set vcu speed limit mode setIGONsetLimit false");
            SystemPropertyUtil.setIGONsetLimit(false);
        }
    }

    public /* synthetic */ void lambda$new$1$AfterSalesPresenter(final boolean onoff, final int switchResult) {
        if (onoff && switchResult == 1 && SUPPORT_SPEED_LIMIT) {
            String toast = setRepairMode(4, null, OTAServiceHelper.OTHER);
            UIUtil.showToast(toast);
        }
        if (switchResult == 1 || (AfterSalesHelper.getAfterSalesManager().getRepairMode() && this.mLastSpeedLimitStatus != OTAServiceHelper.getVcuMode())) {
            if (SUPPORT_SPEED_LIMIT) {
                SystemPropertyUtil.setUploadRepairModeSended(false);
            } else {
                SystemPropertyUtil.setRepairModeSended(false);
            }
            if (AfterSalesHelper.getAfterSalesManager().getRepairMode()) {
                this.mLastSpeedLimitStatus = OTAServiceHelper.getVcuMode();
            }
        }
        uploadRepairModeStatusByRegion();
        ThreadUtils.runOnMainThread(new Runnable() { // from class: com.xiaopeng.cardiagnosis.presenter.-$$Lambda$AfterSalesPresenter$nSy5dNWisEo7Au6Kaz3T3Sy_3Nc
            @Override // java.lang.Runnable
            public final void run() {
                AfterSalesPresenter.this.lambda$new$0$AfterSalesPresenter(switchResult, onoff);
            }
        });
    }

    public /* synthetic */ void lambda$new$0$AfterSalesPresenter(int switchResult, boolean onoff) {
        if (switchResult == 0) {
            XToast.showLong((int) R.string.repair_mode_change_fail);
        } else if (switchResult == 1) {
            this.mMcuModel.setMcuMonitorSwitch(!onoff);
            updateCheckModeDiag();
            if (!onoff) {
                XToast.showLong((int) R.string.checkmode_already_quit);
            }
        } else if (switchResult == 2) {
            Context context = this.mContext;
            Object[] objArr = new Object[1];
            objArr[0] = context.getString(onoff ? R.string.repair_mode_on : R.string.repair_mode_off);
            XToast.showLong(context.getString(R.string.repair_mode_change_keep, objArr));
        }
    }

    public /* synthetic */ void lambda$new$3$AfterSalesPresenter(final int module, final int errorCode, final long time, final String errorMsg) {
        ThreadUtils.execute(new Runnable() { // from class: com.xiaopeng.cardiagnosis.presenter.-$$Lambda$AfterSalesPresenter$JGXGLDgaHKq9ankSHggppFaITuk
            @Override // java.lang.Runnable
            public final void run() {
                AfterSalesPresenter.this.lambda$new$2$AfterSalesPresenter(module, errorCode, time, errorMsg);
            }
        });
    }

    public /* synthetic */ void lambda$new$4$AfterSalesPresenter(int module, int errorCode, long time, String errorMsg, int status) {
        this.mMcuModel.setSocRespDTCInfo(module, errorCode, status);
    }

    public /* synthetic */ void lambda$new$6$AfterSalesPresenter(final boolean onoff, final int switchResult) {
        LogUtils.i(TAG, "AuthModeListener onoff :" + onoff + ", switchResult : " + switchResult);
        if (switchResult == 1) {
            SystemPropertyUtil.setAuthModeSended(false);
        }
        shareAuthMode(onoff);
        ThreadUtils.runOnMainThread(new Runnable() { // from class: com.xiaopeng.cardiagnosis.presenter.-$$Lambda$AfterSalesPresenter$NxUaWR5eK3e5xweg-_blbDBV7Sg
            @Override // java.lang.Runnable
            public final void run() {
                AfterSalesPresenter.lambda$new$5(switchResult, onoff);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public static /* synthetic */ void lambda$new$5(int switchResult, boolean onoff) {
        if (switchResult == 0) {
            XToast.showLong(onoff ? R.string.auth_mode_quit_fail : R.string.auth_mode_enter_fail);
        } else if (switchResult == 1) {
            XToast.showLong(onoff ? R.string.auth_mode_enter_success : R.string.auth_mode_quit_success);
        }
    }

    public /* synthetic */ void lambda$new$7$AfterSalesPresenter(String issueName, String conclusion, String startTime, String endTime, String logicactionTime, String logicactionEntry, String logictreeVer) {
        LogUtils.i(TAG, "uploadLogicAction issueName[%s] conclusion[%s] startTime[%s] endTime[%s] logicactionTime[%s] logicactionEntry[%s] logictreeVer", issueName, conclusion, startTime, endTime, logicactionTime, logicactionEntry, logictreeVer);
        if (this.mMqttNetworkAvailable) {
            uploadLogicAction(issueName, conclusion, startTime, endTime, logicactionTime, logicactionEntry, logictreeVer);
            AfterSalesHelper.getAfterSalesManager().updateLogicActionUploadStatus(true, issueName, conclusion, startTime, endTime, logicactionTime, logicactionEntry, logictreeVer);
            return;
        }
        AfterSalesHelper.getAfterSalesManager().updateLogicActionUploadStatus(false, issueName, conclusion, startTime, endTime, logicactionTime, logicactionEntry, logictreeVer);
    }

    public AfterSalesPresenter(Context context) {
        this.mHandler = null;
        this.mHandlerThread = null;
        this.mLastSpeedLimitStatus = -1;
        this.mContext = context;
        this.mConnectivityManager = (ConnectivityManager) this.mContext.getSystemService("connectivity");
        this.mNaviModel = new NaviModel(context);
        updateMqttNetworkStatus();
        this.mConnectivityManager.registerDefaultNetworkCallback(this.mNetworkCallback);
        AfterSalesHelper.getAfterSalesManager().registerRepairModeListener(this.mRepairModeListener);
        AfterSalesHelper.getAfterSalesManager().addAlertListener(this.mAlertListener);
        AfterSalesHelper.getAfterSalesManager().registerAuthModeListener(this.mAuthModeListener);
        AfterSalesHelper.getAfterSalesManager().addLogicActionListener(this.mLogicActionListener);
        if (SUPPORT_LCD_DTC) {
            AfterSalesHelper.getAfterSalesManager().addDiagnosisStatusListener(this.mDiagnosisStatusListener);
        }
        initCheckModeDialog();
        SystemPropertyUtil.setIGONsetLimit(false);
        this.mTboxModel.registerPropCallback(Collections.singletonList(557846543), this.mEventChange);
        Collection<Integer> icmIds = new ArrayList<>();
        icmIds.add(554702431);
        this.mIcmModel.registerPropCallback(icmIds, this.mEventChange);
        Collection<Integer> mcuIds = new ArrayList<>();
        mcuIds.add(557847561);
        this.mMcuModel.registerPropCallback(mcuIds, this.mEventChange);
        shareAuthMode(AfterSalesHelper.getAfterSalesManager().getAuthMode());
        if (AfterSalesHelper.getAfterSalesManager().getRepairMode()) {
            this.mLastSpeedLimitStatus = OTAServiceHelper.getVcuMode();
        }
        uploadRepairModeStatusByRegion();
        registerReceiver();
        if (NEED_NAPA_INIT_FINISH && AfterSalesHelper.getAfterSalesManager().getRepairMode()) {
            this.mHandlerThread = new HandlerThread("checkNapaFisish", 10);
            this.mHandlerThread.start();
            this.mHandler = new Handler(this.mHandlerThread.getLooper());
            checkNapaFisish();
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void checkTopActivityForCheckModeDiag() {
        String topPackageName = ActivityUtil.getTopPackageName(this.mContext);
        LogUtils.i(TAG, "topPackageName : " + topPackageName);
        if (Constant.PACKAGE_NAME_OOBE.equalsIgnoreCase(topPackageName) || Constant.PACKAGE_NAME_INSTRUMENT.equalsIgnoreCase(topPackageName)) {
            this.mNeedUpdateCheckModeDialog = true;
            return;
        }
        boolean z = NEED_NAPA_INIT_FINISH;
        if (!z || (z && Constant.NAPA_READY.equals(Support.Properties.get(Support.Properties.NAPA_INIT)))) {
            LogUtils.d(TAG, "checkTopActivityForCheckModeDiag updateCheckModeDiag : " + topPackageName);
            updateCheckModeDiag();
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void checkNapaFisish() {
        if (this.mIsNapaFinish) {
            return;
        }
        boolean isNapaFinish = Constant.NAPA_READY.equals(Support.Properties.get(Support.Properties.NAPA_INIT));
        LogUtils.d(TAG, "checkNapaFisish isNapaFinish : " + isNapaFinish);
        if (isNapaFinish) {
            this.mIsNapaFinish = true;
            this.mNeedUpdateCheckModeDialog = false;
            updateCheckModeDiag();
            return;
        }
        Handler handler = this.mHandler;
        if (handler != null) {
            handler.postDelayed(new Runnable() { // from class: com.xiaopeng.cardiagnosis.presenter.-$$Lambda$AfterSalesPresenter$XPVzYMBAqUDVN2swvZVhEVEXaAw
                @Override // java.lang.Runnable
                public final void run() {
                    AfterSalesPresenter.this.checkNapaFisish();
                }
            }, 3000L);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void updateMqttNetworkStatus() {
        this.mMqttNetworkAvailable = ProcessUtil.sendPing(DATA_MQTT_SERVER);
    }

    private void registerReceiver() {
        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction(Constant.ACTION_ACTIVITY_CHANGED);
        this.mContext.registerReceiver(this.mBroadcastReceiver, intentFilter);
    }

    public void updateCheckModeDiag() {
        LogUtils.d(TAG, "updateCheckModeDiag");
        ThreadUtils.runOnMainThread(new Runnable() { // from class: com.xiaopeng.cardiagnosis.presenter.-$$Lambda$AfterSalesPresenter$DkLQ-p8o7hKIBnrGMyFTG0p1FO8
            @Override // java.lang.Runnable
            public final void run() {
                AfterSalesPresenter.this.lambda$updateCheckModeDiag$9$AfterSalesPresenter();
            }
        });
    }

    public /* synthetic */ void lambda$updateCheckModeDiag$9$AfterSalesPresenter() {
        if (this.mCheckModeDialog != null) {
            if (SUPPORT_SPEED_LIMIT) {
                changeButtonStatus(this.mBtCancelSpeedLimit, false, R.string.get_speed_limit_status);
                this.mLdSpeedLimitStatus.setVisibility(0);
                ThreadUtils.execute(new Runnable() { // from class: com.xiaopeng.cardiagnosis.presenter.-$$Lambda$AfterSalesPresenter$i7ggYqf6Cj6hoJATSmZVpoCJYJo
                    @Override // java.lang.Runnable
                    public final void run() {
                        AfterSalesPresenter.this.lambda$updateCheckModeDiag$8$AfterSalesPresenter();
                    }
                });
            }
            if (AfterSalesHelper.getAfterSalesManager().getRepairMode()) {
                if (!this.mCheckModeDialog.isShowing()) {
                    this.mCheckModeDialog.show();
                    return;
                }
                return;
            }
            this.mCheckModeDialog.dismiss();
        }
    }

    public /* synthetic */ void lambda$updateCheckModeDiag$8$AfterSalesPresenter() {
        if (AfterSalesHelper.getAfterSalesManager().getRepairMode()) {
            int vcuMode = OTAServiceHelper.getVcuMode();
            LogUtils.d(TAG, "vcu mode: " + vcuMode + ", speed limit mode: " + AfterSalesHelper.getAfterSalesManager().getSpeedLimitMode());
            if (vcuMode != 3 && vcuMode != -1) {
                changeButtonStatus(this.mBtCancelSpeedLimit, false, R.string.already_cancel_speed_limit);
            } else {
                changeButtonStatus(this.mBtCancelSpeedLimit, true, R.string.cancel_speed_limit);
            }
        }
    }

    private void initCheckModeDialog() {
        this.mCheckModeDialog = new XDialog(this.mContext);
        View view = LayoutInflater.from(this.mContext).inflate(R.layout.dialog_checkmode, this.mCheckModeDialog.getContentView(), false);
        view.findViewById(R.id.quit_checkmode).setOnClickListener(new View.OnClickListener() { // from class: com.xiaopeng.cardiagnosis.presenter.-$$Lambda$AfterSalesPresenter$ykBEpO5ws_w5GXSaLjEQSTmSKug
            @Override // android.view.View.OnClickListener
            public final void onClick(View view2) {
                AfterSalesPresenter.this.lambda$initCheckModeDialog$10$AfterSalesPresenter(view2);
            }
        });
        this.mBtEnterDiagnosis = (XButton) view.findViewById(R.id.enter_ivi_diagnosis);
        this.mBtEnterDiagnosis.setOnClickListener(new View.OnClickListener() { // from class: com.xiaopeng.cardiagnosis.presenter.-$$Lambda$AfterSalesPresenter$wKDlp1tDR4b8jH2paKYPotT1vcs
            @Override // android.view.View.OnClickListener
            public final void onClick(View view2) {
                AfterSalesPresenter.this.lambda$initCheckModeDialog$11$AfterSalesPresenter(view2);
            }
        });
        if (SUPPORT_CAR_DIAGNOSTIC_APP) {
            this.mBtEnterDiagnosis.setText(R.string.enter_car_diagnosis);
        }
        this.mLlSpeedLimit = (XLinearLayout) view.findViewById(R.id.speed_limit);
        if (!SUPPORT_SPEED_LIMIT) {
            this.mLlSpeedLimit.setVisibility(8);
        }
        this.mBtCancelSpeedLimit = (XButton) view.findViewById(R.id.cancel_speed_limit);
        this.mBtCancelSpeedLimit.setOnClickListener(new View.OnClickListener() { // from class: com.xiaopeng.cardiagnosis.presenter.-$$Lambda$AfterSalesPresenter$q4rUcguYt9xA7DHdrXnManlKc-Q
            @Override // android.view.View.OnClickListener
            public final void onClick(View view2) {
                AfterSalesPresenter.this.lambda$initCheckModeDialog$12$AfterSalesPresenter(view2);
            }
        });
        this.mLdSpeedLimitStatus = (XLoading) view.findViewById(R.id.get_speed_limit_status_loading);
        this.mCheckModeDialog.setTitle(R.string.under_checkmode).setCustomView(view, false);
        this.mCheckModeDialog.setCloseVisibility(true);
        this.mCheckModeDialog.setSystemDialog(XDialogSystemType.TYPE_SYSTEM_DIALOG);
    }

    public /* synthetic */ void lambda$initCheckModeDialog$10$AfterSalesPresenter(View v) {
        LogUtils.i(TAG, "try to quit Check Mode");
        if (!SystemPropertyUtil.getIGONsetLimit()) {
            LogUtils.i(TAG, "no in IG_ON set status");
            this.mCheckModeDialog.dismiss();
            execCheckModeFunc(1);
            return;
        }
        UIUtil.showInfoConfirmation(this.mContext, R.string.title_quit_check_mode, R.string.tips_quit_check_mode_set_limit, R.string.confirm);
    }

    public /* synthetic */ void lambda$initCheckModeDialog$11$AfterSalesPresenter(View v) {
        LogUtils.i(TAG, "enter Ivi Diagnosis");
        this.mCheckModeDialog.dismiss();
        execCheckModeFunc(2);
    }

    public /* synthetic */ void lambda$initCheckModeDialog$12$AfterSalesPresenter(View v) {
        LogUtils.i(TAG, "click to cancel speed limit");
        if (!SystemPropertyUtil.getIGONsetLimit()) {
            LogUtils.i(TAG, "no in IG_ON set status");
            this.mCheckModeDialog.dismiss();
            execCheckModeFunc(3);
            return;
        }
        UIUtil.showInfoConfirmation(this.mContext, R.string.title_cancel_speed_limit_mode, R.string.tips_cancel_speed_limit_mode_set_limit, R.string.confirm);
    }

    private void execCheckModeFunc(int type) {
        if (type == 1) {
            showConfirmDialog(R.string.under_checkmode, R.string.whether_repair_done, R.string.confirm_to_quit, R.string.dont_quit, type);
        } else if (type != 2) {
            if (type == 3) {
                showConfirmDialog(R.string.under_checkmode, R.string.whether_cancel_speed_limit, R.string.confirm_to_cancel_speed_limit, R.string.keep_speed_limit, type);
            }
        } else {
            Intent intent = new Intent();
            if (!SUPPORT_CAR_DIAGNOSTIC_APP) {
                intent.setAction(Constant.ACTION_IVI_DIAGNOSIS);
                intent.setPackage("com.xiaopeng.devtools");
                this.mContext.sendBroadcast(intent, Constant.BROADCAST_PERMISSION);
                return;
            }
            intent.setAction(Constant.ACTION_DIAGNOSTIC_AFTERSALE);
            intent.putExtra("from", "repair");
            intent.addFlags(268435456);
            try {
                this.mContext.startActivity(intent);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    private void showConfirmDialog(int titleResid, int messageResid, int confirmResid, int cancelResid, final int type) {
        XDialog dialog = new XDialog(this.mContext);
        dialog.setTitle(titleResid).setMessage(messageResid).setPositiveButton(DataHelp.getString(this.mContext, confirmResid), new XDialogInterface.OnClickListener() { // from class: com.xiaopeng.cardiagnosis.presenter.-$$Lambda$AfterSalesPresenter$arXhDaZ5Pc_fmKXKHQN-qeNMpMw
            @Override // com.xiaopeng.xui.app.XDialogInterface.OnClickListener
            public final void onClick(XDialog xDialog, int i) {
                AfterSalesPresenter.this.lambda$showConfirmDialog$15$AfterSalesPresenter(type, xDialog, i);
            }
        }).setNegativeButton(DataHelp.getString(this.mContext, cancelResid), new XDialogInterface.OnClickListener() { // from class: com.xiaopeng.cardiagnosis.presenter.-$$Lambda$AfterSalesPresenter$fMV1n_FICzIc0ZXO0yGq3DloSro
            @Override // com.xiaopeng.xui.app.XDialogInterface.OnClickListener
            public final void onClick(XDialog xDialog, int i) {
                AfterSalesPresenter.this.lambda$showConfirmDialog$16$AfterSalesPresenter(xDialog, i);
            }
        }).setSystemDialog(XDialogSystemType.TYPE_SYSTEM_DIALOG);
        dialog.show();
    }

    public /* synthetic */ void lambda$showConfirmDialog$15$AfterSalesPresenter(int type, XDialog xDialog, int i) {
        xDialog.dismiss();
        if (type != 1) {
            if (type == 3) {
                ThreadUtils.execute(new Runnable() { // from class: com.xiaopeng.cardiagnosis.presenter.-$$Lambda$AfterSalesPresenter$F0kUDT9ow-Jr2WcNotp1rA0WqeA
                    @Override // java.lang.Runnable
                    public final void run() {
                        AfterSalesPresenter.this.lambda$showConfirmDialog$13$AfterSalesPresenter();
                    }
                });
            }
        } else if (!SUPPORT_SPEED_LIMIT) {
            AfterSalesHelper.getAfterSalesManager().disableRepairMode();
        } else {
            ThreadUtils.execute(new Runnable() { // from class: com.xiaopeng.cardiagnosis.presenter.-$$Lambda$AfterSalesPresenter$QbfQytR8E4tQWVJ-XFoL56HCzbI
                @Override // java.lang.Runnable
                public final void run() {
                    AfterSalesPresenter.this.lambda$showConfirmDialog$14$AfterSalesPresenter();
                }
            });
        }
    }

    public /* synthetic */ void lambda$showConfirmDialog$13$AfterSalesPresenter() {
        String res = setRepairMode(3, null, OTAServiceHelper.OTHER);
        if (this.mContext.getString(R.string.already_cancel_speed_limit).equals(res)) {
            uploadRepairModeStatusByRegion();
        }
        UIUtil.showToast(res);
    }

    public /* synthetic */ void lambda$showConfirmDialog$14$AfterSalesPresenter() {
        String toast;
        RepairModeStatusBean bean = reqTargetRepairModeStatus();
        if (bean != null) {
            toast = bean.targetCheckMode.booleanValue() ? this.mContext.getString(R.string.server_not_allow_quit_checkmode) : setRepairMode(1, null, OTAServiceHelper.OTHER);
        } else {
            LogUtils.e(TAG, "get target repair mode status fail");
            toast = this.mContext.getString(R.string.get_target_checkmode_status_fail);
        }
        UIUtil.showToast(toast);
    }

    public /* synthetic */ void lambda$showConfirmDialog$16$AfterSalesPresenter(XDialog xDialog, int i) {
        xDialog.dismiss();
        updateCheckModeDiag();
    }

    private void changeButtonStatus(final Button button, final boolean status, final int res) {
        ThreadUtils.runOnMainThread(new Runnable() { // from class: com.xiaopeng.cardiagnosis.presenter.-$$Lambda$AfterSalesPresenter$0ircqS1pyQpxo5S1A__IvwnJ8bU
            @Override // java.lang.Runnable
            public final void run() {
                AfterSalesPresenter.this.lambda$changeButtonStatus$17$AfterSalesPresenter(button, res, status);
            }
        });
    }

    public /* synthetic */ void lambda$changeButtonStatus$17$AfterSalesPresenter(Button button, int res, boolean status) {
        button.setText(res);
        button.setEnabled(status);
        if (res != R.string.get_speed_limit_status && button.getId() == R.id.cancel_speed_limit) {
            this.mLdSpeedLimitStatus.setVisibility(8);
        }
    }

    public void destroy() {
        HandlerThread handlerThread;
        this.mContext.unregisterReceiver(this.mBroadcastReceiver);
        AfterSalesHelper.getAfterSalesManager().unregisterRepairModeListener(this.mRepairModeListener);
        AfterSalesHelper.getAfterSalesManager().removeAlertListener(this.mAlertListener);
        AfterSalesHelper.getAfterSalesManager().unregisterAuthModeListener(this.mAuthModeListener);
        AfterSalesHelper.getAfterSalesManager().removeLogicActionListener(this.mLogicActionListener);
        if (SUPPORT_LCD_DTC) {
            AfterSalesHelper.getAfterSalesManager().removeDiagnosisStatusListener(this.mDiagnosisStatusListener);
        }
        this.mTboxModel.onDestroy();
        this.mMcuModel.onDestroy();
        this.mIcmModel.onDestroy();
        if (NEED_NAPA_INIT_FINISH && (handlerThread = this.mHandlerThread) != null) {
            handlerThread.getLooper().quit();
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* renamed from: uploadDiagnosis2Cloud */
    public void lambda$new$2$AfterSalesPresenter(final int module, final int errorCode, final long time, final String errorMsg) {
        LogUtils.i(TAG, "uploadDiagnosis2Cloud module:" + module + ", errorCode:" + errorCode + ", time:" + time + ", errorMsg:" + errorMsg);
        String vin = SystemPropertyUtil.getVIN();
        String cduid = SystemPropertyUtil.getHardwareId();
        if (vin.length() > 0 && cduid.length() > 0) {
            Map<String, String> param = new HashMap<>();
            param.put(Constant.HTTP_KEY_VIN, vin);
            param.put("cduId", cduid);
            param.put(Constant.HTTP_KEY_MODULE, String.valueOf(module));
            param.put(Constant.HTTP_KEY_ERRORCODE, String.valueOf(errorCode));
            param.put(Constant.HTTP_KEY_TRIGGERTIME, String.valueOf(time));
            param.put(Constant.HTTP_KEY_REMARK, errorMsg);
            IHttp http = (IHttp) Module.get(NetworkChannelsEntry.class).get(IHttp.class);
            http.bizHelper().post(URL_DIAGNOSIS_UPLOAD, new Gson().toJson(param)).needAuthorizationInfo().enableSecurityEncoding().build().tag(URL_DIAGNOSIS_UPLOAD).execute(new Callback() { // from class: com.xiaopeng.cardiagnosis.presenter.AfterSalesPresenter.4
                @Override // com.xiaopeng.lib.framework.moduleinterface.netchannelmodule.http.Callback
                public void onSuccess(IResponse iResponse) {
                    LogUtils.v(AfterSalesPresenter.TAG, "uploadDiagnosis2Cloud onSuccess iResponse: " + iResponse.body() + ", module:" + module + ", errorCode:" + errorCode + ", time:" + time + ", errorMsg:" + errorMsg);
                    boolean result = false;
                    try {
                        result = new JSONObject(iResponse.body()).getInt("code") == 200;
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                    AfterSalesHelper.getAfterSalesManager().updateDiagnosisUploadStatus(module, result, errorCode, time, errorMsg);
                }

                @Override // com.xiaopeng.lib.framework.moduleinterface.netchannelmodule.http.Callback
                public void onFailure(IResponse iResponse) {
                    LogUtils.e(AfterSalesPresenter.TAG, "uploadDiagnosis2Cloud onFailure " + iResponse.body());
                    AfterSalesHelper.getAfterSalesManager().updateDiagnosisUploadStatus(module, false, errorCode, time, errorMsg);
                }
            });
            return;
        }
        LogUtils.e(TAG, "VIN = " + vin + ", cduid = " + cduid);
        AfterSalesHelper.getAfterSalesManager().updateDiagnosisUploadStatus(module, false, errorCode, time, errorMsg);
    }

    private void notifyRepairModeStatus(boolean status) {
        LogUtils.i(TAG, "notifyRepairModeStatus: " + status);
        this.mTboxModel.setRepairMode(status);
        this.mBcmModel.setRepairModeStatus(status);
        String vin = SystemPropertyUtil.getVIN();
        if (vin.length() > 0 && !SystemPropertyUtil.getRepairModeSended()) {
            Map<String, String> param = new HashMap<>();
            param.put(Constant.HTTP_KEY_VIN, vin);
            param.put("type", status ? "1" : "2");
            IHttp http = (IHttp) Module.get(NetworkChannelsEntry.class).get(IHttp.class);
            http.cancelTag(URL_REPAIR_MODE);
            http.bizHelper().post(URL_REPAIR_MODE, new Gson().toJson(param)).needAuthorizationInfo().enableSecurityEncoding().build().tag(URL_REPAIR_MODE).execute(new Callback() { // from class: com.xiaopeng.cardiagnosis.presenter.AfterSalesPresenter.5
                @Override // com.xiaopeng.lib.framework.moduleinterface.netchannelmodule.http.Callback
                public void onSuccess(IResponse iResponse) {
                    LogUtils.d(AfterSalesPresenter.TAG, "notifyRepairModeStatus : onSuccess");
                    SystemPropertyUtil.setRepairModeSended(true);
                }

                @Override // com.xiaopeng.lib.framework.moduleinterface.netchannelmodule.http.Callback
                public void onFailure(IResponse iResponse) {
                }
            });
            return;
        }
        LogUtils.e(TAG, "fail to share repair mode VIN = " + vin + ", getRepairModeSended = " + SystemPropertyUtil.getRepairModeSended());
    }

    private void uploadRepairModeStatus(boolean status) {
        LogUtils.i(TAG, "uploadRepairModeStatus: " + status);
        this.mBcmModel.setRepairModeStatus(status);
        this.mTboxModel.setRepairMode(status);
        String vin = SystemPropertyUtil.getVIN();
        String cduId = SystemPropertyUtil.getHardwareId();
        String keyId = AfterSalesHelper.getAfterSalesManager().getRepairModeKeyId();
        if (vin.length() > 0 && cduId.length() > 0 && !TextUtils.isEmpty(keyId) && !SystemPropertyUtil.getUploadRepairModeSended()) {
            Map<String, String> param = new HashMap<>();
            param.put(Constant.HTTP_KEY_VIN, vin);
            param.put("cduId", cduId);
            param.put("key", keyId);
            ParamRepairMode maintainMode = new ParamRepairMode(AfterSalesHelper.getAfterSalesManager().getRepairMode(), AfterSalesHelper.getAfterSalesManager().getRepairModeEnableTime(), AfterSalesHelper.getAfterSalesManager().getRepairModeDisableTime());
            ParamRepairMode speedLimitMode = new ParamRepairMode(AfterSalesHelper.getAfterSalesManager().getSpeedLimitMode(), AfterSalesHelper.getAfterSalesManager().getSpeedLimitEnableTime(), AfterSalesHelper.getAfterSalesManager().getSpeedLimitDisableTime());
            CheckModeStatus checkModeStatus = new CheckModeStatus(vin, cduId, keyId, maintainMode, speedLimitMode);
            IHttp http = (IHttp) Module.get(NetworkChannelsEntry.class).get(IHttp.class);
            http.cancelTag(URL_UPLOAD_REPAIR_MODE_STATUS);
            http.bizHelper().post(URL_UPLOAD_REPAIR_MODE_STATUS, new Gson().toJson(checkModeStatus)).needAuthorizationInfo().enableSecurityEncoding().build().tag(URL_UPLOAD_REPAIR_MODE_STATUS).execute(new Callback() { // from class: com.xiaopeng.cardiagnosis.presenter.AfterSalesPresenter.6
                @Override // com.xiaopeng.lib.framework.moduleinterface.netchannelmodule.http.Callback
                public void onSuccess(IResponse iResponse) {
                    LogUtils.d(AfterSalesPresenter.TAG, "uploadRepairModeStatus onSuccess");
                    SystemPropertyUtil.setUploadRepairModeSended(true);
                }

                @Override // com.xiaopeng.lib.framework.moduleinterface.netchannelmodule.http.Callback
                public void onFailure(IResponse iResponse) {
                }
            });
            return;
        }
        LogUtils.e(TAG, "fail to upload repair mode status, VIN = " + vin + ", CDUID = " + cduId + ", KEYID = " + keyId + ", getUploadRepairModeSended = " + SystemPropertyUtil.getUploadRepairModeSended());
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void shareAuthMode(final boolean onoff) {
        LogUtils.i(TAG, "shareAuthMode" + onoff);
        ThreadUtils.execute(new Runnable() { // from class: com.xiaopeng.cardiagnosis.presenter.-$$Lambda$AfterSalesPresenter$E_iVnsj72wJg6G-YGc3gpNLzkdY
            @Override // java.lang.Runnable
            public final void run() {
                AfterSalesPresenter.this.lambda$shareAuthMode$18$AfterSalesPresenter(onoff);
            }
        });
    }

    public /* synthetic */ void lambda$shareAuthMode$18$AfterSalesPresenter(boolean onoff) {
        String vin = SystemPropertyUtil.getVIN();
        String cduid = SystemPropertyUtil.getHardwareId();
        String iccid = SystemPropertyUtil.getIccid();
        if (vin.length() > 0 && cduid.length() > 0 && iccid.length() > 0 && !SystemPropertyUtil.getAuthModeSended()) {
            long time = System.currentTimeMillis() / 1000;
            Map<String, String> param = new HashMap<>();
            param.put(Constant.HTTP_KEY_VIN, vin);
            param.put("cduId", cduid);
            param.put(Constant.HTTP_KEY_ICCID, iccid);
            param.put(Constant.HTTP_KEY_ONOFF, String.valueOf(onoff));
            param.put("timestamp", String.valueOf(time));
            IHttp http = (IHttp) Module.get(NetworkChannelsEntry.class).get(IHttp.class);
            http.cancelTag(URL_SHARE_AUTHMODE);
            http.bizHelper().post(URL_SHARE_AUTHMODE, new Gson().toJson(param)).needAuthorizationInfo().enableSecurityEncoding().build().tag(URL_SHARE_AUTHMODE).execute(new Callback() { // from class: com.xiaopeng.cardiagnosis.presenter.AfterSalesPresenter.7
                @Override // com.xiaopeng.lib.framework.moduleinterface.netchannelmodule.http.Callback
                public void onSuccess(IResponse iResponse) {
                    LogUtils.d(AfterSalesPresenter.TAG, "shareAuthMode onSuccess");
                    SystemPropertyUtil.setAuthModeSended(true);
                }

                @Override // com.xiaopeng.lib.framework.moduleinterface.netchannelmodule.http.Callback
                public void onFailure(IResponse iResponse) {
                }
            });
            return;
        }
        LogUtils.e(TAG, "fail to share auth mode VIN = " + vin + ", cduid = " + cduid + ", iccid = " + iccid + ", getAuthModeSended = " + SystemPropertyUtil.getAuthModeSended());
    }

    private void uploadLogicAction(final String issueName, final String conclusion, final String startTime, final String endTime, final String logicactionTime, final String logicactionEntry, final String logictreeVer) {
        ThreadUtils.execute(new Runnable() { // from class: com.xiaopeng.cardiagnosis.presenter.-$$Lambda$AfterSalesPresenter$002wq7ItBekGHZpsyHmN9IL1rOw
            @Override // java.lang.Runnable
            public final void run() {
                AfterSalesPresenter.this.lambda$uploadLogicAction$19$AfterSalesPresenter(issueName, conclusion, startTime, endTime, logicactionTime, logicactionEntry, logictreeVer);
            }
        });
    }

    public /* synthetic */ void lambda$uploadLogicAction$19$AfterSalesPresenter(String issueName, String conclusion, String startTime, String endTime, String logicactionTime, String logicactionEntry, String logictreeVer) {
        IMoleEventBuilder builder = this.mDataLog.buildMoleEvent();
        builder.setEvent("logictree_action").setPageId("P10246").setButtonId("B001");
        builder.setProperty("name", issueName).setProperty(Constant.MCUTEST.MCU_SERVER_CONNECTION_RESULT_TAG, conclusion).setProperty("startTime", startTime).setProperty("endTime", endTime).setProperty(Constant.HTTP_KEY_TRIGGERTIME, logicactionTime).setProperty("source", logicactionEntry).setProperty("version", logictreeVer);
        this.mDataLog.sendStatData(builder.build());
    }

    public RepairModeStatusBean reqTargetRepairModeStatus() {
        String vin = SystemPropertyUtil.getVIN();
        if (vin.length() > 0) {
            Map<String, String> param = new HashMap<>();
            param.put(Constant.HTTP_KEY_VIN, vin);
            try {
                IHttp http = (IHttp) Module.get(NetworkChannelsEntry.class).get(IHttp.class);
                IResponse response = http.bizHelper().post(URL_REQ_TARGET_REPAIR_MODE_STATUS, new Gson().toJson(param)).needAuthorizationInfo().enableSecurityEncoding().build().execute();
                ServerBean serverBean = DataHelp.getServerBean(response);
                if (serverBean.getCode() != 200) {
                    return null;
                }
                String data = serverBean.getData();
                if (TextUtils.isEmpty(data)) {
                    return null;
                }
                RepairModeStatusBean bean = (RepairModeStatusBean) new Gson().fromJson(data, (Class<Object>) RepairModeStatusBean.class);
                if (bean.targetCheckMode == null) {
                    return null;
                }
                LogUtils.d(TAG, "target: " + bean.targetCheckMode + " num : " + bean.jobNum);
                return bean;
            } catch (Exception e) {
                e.printStackTrace();
                return null;
            }
        }
        LogUtils.e(TAG, "fail to get repair mode VIN = " + vin);
        return null;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void uploadRepairModeStatusByRegion() {
        if (SUPPORT_SPEED_LIMIT) {
            uploadRepairModeStatus(AfterSalesHelper.getAfterSalesManager().getRepairMode());
        } else {
            notifyRepairModeStatus(AfterSalesHelper.getAfterSalesManager().getRepairMode());
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public String setRepairMode(int target, String keyId, String source) {
        LogUtils.d(TAG, "target: " + target + " keyId: " + keyId);
        StringBuilder sb = new StringBuilder();
        sb.append(", speed limit mode: ");
        sb.append(AfterSalesHelper.getAfterSalesManager().getSpeedLimitMode());
        LogUtils.d(TAG, sb.toString());
        if (target == 1) {
            if (AfterSalesHelper.getAfterSalesManager().getRepairMode()) {
                if (OTAServiceHelper.getVcuMode() == 2) {
                    if (AfterSalesHelper.getAfterSalesManager().getSpeedLimitMode()) {
                        AfterSalesHelper.getAfterSalesManager().recordSpeedLimitOff();
                    }
                    AfterSalesHelper.getAfterSalesManager().disableRepairMode();
                    return null;
                } else if (!this.mVcuModel.isUnderLevelP()) {
                    LogUtils.e(TAG, "vehicle not under p level");
                    Context context = this.mContext;
                    return context.getString(R.string.not_under_p_level, context.getString(R.string.quit));
                } else if (OTAServiceHelper.setVcuModel(2, source)) {
                    LogUtils.d(TAG, "now vcu in normal mode");
                    if (AfterSalesHelper.getAfterSalesManager().getSpeedLimitMode()) {
                        AfterSalesHelper.getAfterSalesManager().recordSpeedLimitOff();
                    }
                    AfterSalesHelper.getAfterSalesManager().disableRepairMode();
                    return null;
                } else {
                    LogUtils.e(TAG, "set vcu normal mode fail");
                    UIUtil.showInfoConfirmation(this.mContext, R.string.title_quit_check_mode, R.string.tips_quit_check_mode, R.string.confirm);
                    Context context2 = this.mContext;
                    return context2.getString(R.string.set_vcu_mode_fail, context2.getString(R.string.quit));
                }
            }
            return null;
        } else if (target != 3) {
            if (target != 4) {
                return null;
            }
            if (AfterSalesHelper.getAfterSalesManager().getRepairMode() && OTAServiceHelper.getVcuMode() == 3) {
                LogUtils.d(TAG, "vcu and cdu already in repair mode");
                return null;
            } else if (!AfterSalesHelper.getAfterSalesManager().getRepairMode()) {
                AfterSalesHelper.getAfterSalesManager().enableRepairModeWithKeyId(keyId);
                return null;
            } else if (!this.mVcuModel.isUnderLevelP()) {
                LogUtils.e(TAG, "vehicle not under p level");
                Context context3 = this.mContext;
                return context3.getString(R.string.not_under_p_level, context3.getString(R.string.enter));
            } else if (OTAServiceHelper.setVcuModel(3, source)) {
                AfterSalesHelper.getAfterSalesManager().recordSpeedLimitOn();
                return null;
            } else {
                LogUtils.e(TAG, "set vcu normal model fail");
                Context context4 = this.mContext;
                return context4.getString(R.string.set_vcu_mode_fail, context4.getString(R.string.enter));
            }
        } else if (AfterSalesHelper.getAfterSalesManager().getRepairMode() && OTAServiceHelper.getVcuMode() == 2) {
            LogUtils.i(TAG, "cancel speed limit success, vcu already in narmal mode");
            if (AfterSalesHelper.getAfterSalesManager().getSpeedLimitMode()) {
                AfterSalesHelper.getAfterSalesManager().recordSpeedLimitOff();
            }
            return this.mContext.getString(R.string.already_cancel_speed_limit);
        } else if (!this.mVcuModel.isUnderLevelP()) {
            LogUtils.e(TAG, "vehicle not under p level, can't cancel speed limit");
            Context context5 = this.mContext;
            return context5.getString(R.string.not_under_p_level, context5.getString(R.string.quit));
        } else if (AfterSalesHelper.getAfterSalesManager().getRepairMode() && OTAServiceHelper.setVcuModel(2, source)) {
            LogUtils.i(TAG, "cancel speed limit success, now vcu in normal mode");
            if (AfterSalesHelper.getAfterSalesManager().getSpeedLimitMode()) {
                AfterSalesHelper.getAfterSalesManager().recordSpeedLimitOff();
            }
            return this.mContext.getString(R.string.already_cancel_speed_limit);
        } else {
            LogUtils.e(TAG, "cancel speed limit fail");
            UIUtil.showInfoConfirmation(this.mContext, R.string.title_cancel_speed_limit_mode, R.string.tips_cancel_speed_limit_mode, R.string.confirm);
            Context context6 = this.mContext;
            return context6.getString(R.string.set_vcu_mode_fail, context6.getString(R.string.quit));
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* loaded from: classes4.dex */
    public class RepairModeStatusBean {
        public String jobNum;
        public Boolean targetCheckMode;

        private RepairModeStatusBean() {
        }
    }
}
