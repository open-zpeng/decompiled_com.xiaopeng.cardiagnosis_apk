package com.xiaopeng.commonfunc.model.security;

import android.content.Context;
import android.text.TextUtils;
import android.util.Base64;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.xiaopeng.commonfunc.Constant;
import com.xiaopeng.commonfunc.bean.car.TboxBean;
import com.xiaopeng.commonfunc.bean.event.SSLKeyEvent;
import com.xiaopeng.commonfunc.bean.http.CertKey;
import com.xiaopeng.commonfunc.bean.http.TboxCertKey;
import com.xiaopeng.commonfunc.bean.http.TboxVerifyKey;
import com.xiaopeng.commonfunc.model.car.CarEventChangedListener;
import com.xiaopeng.commonfunc.model.car.TboxModel;
import com.xiaopeng.commonfunc.model.security.TboxKeyModel;
import com.xiaopeng.commonfunc.utils.DataHelp;
import com.xiaopeng.commonfunc.utils.FileUtil;
import com.xiaopeng.commonfunc.utils.IndivHelper;
import com.xiaopeng.commonfunc.utils.ProcessUtil;
import com.xiaopeng.commonfunc.utils.ScpUtil;
import com.xiaopeng.commonfunc.utils.SystemPropertyUtil;
import com.xiaopeng.commonfunc.utils.TftpSender;
import com.xiaopeng.lib.framework.module.Module;
import com.xiaopeng.lib.framework.moduleinterface.netchannelmodule.http.Callback;
import com.xiaopeng.lib.framework.moduleinterface.netchannelmodule.http.IHttp;
import com.xiaopeng.lib.framework.moduleinterface.netchannelmodule.http.IRequest;
import com.xiaopeng.lib.framework.moduleinterface.netchannelmodule.http.IResponse;
import com.xiaopeng.lib.framework.netchannelmodule.NetworkChannelsEntry;
import com.xiaopeng.lib.http.ICallback;
import com.xiaopeng.lib.http.server.ServerBean;
import com.xiaopeng.lib.utils.LogUtils;
import com.xiaopeng.lib.utils.MD5Utils;
import com.xiaopeng.lib.utils.ThreadUtils;
import com.xiaopeng.lib.utils.info.BuildInfoUtils;
import com.xiaopeng.xmlconfig.Support;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import org.greenrobot.eventbus.EventBus;
/* loaded from: classes4.dex */
public class TboxKeyModel {
    private static final String ACTION_4G = "4G";
    private static final String ACTION_4G_COMMON = "4G_COMMON";
    private static final String ETH_TBOX_PATH_TEMP_KEY = "/mnt/sdcard/tftpboot/tbox.key";
    public static final int EXECUTE_SUCCESS = 0;
    private static final String PATH_CACHE_FACTORY = "/cache/factory/";
    private static final String PATH_TEMP_KEY = "/cache/factory/tbox.key";
    public static final int REQUEST_GET_CERT = 3;
    public static final int REQUEST_GET_ICCID = 1;
    public static final int REQUEST_INSTALL_CERT = 2;
    private static final String TAG = "TboxKeyModel";
    private static final String TBOX_CA_CERT_PATH = "/res/dfer27nt.png";
    private static final String TBOX_CERT_PKCS12_PASS = "T3g4daAxt6re";
    private static final String TBOX_PATH_TEMP_KEY = "/mnt/sdcard2/tbox.key";
    private Context mContext;
    private static final boolean SUPPORT_TBOX_ETH_CONNECTION = Support.Feature.getBoolean(Support.Feature.SUPPORT_TBOX_ETH_CONNECTION);
    private static final String URL_REQUEST_TBOX_KEY = Support.Url.getUrl(Support.Url.REQUEST_TBOX_KEY);
    private static final String URL_REQUEST_TBOX_KEY_V3 = Support.Url.getUrl(Support.Url.REQUEST_TBOX_KEY_V3);
    private TboxModel mTboxModel = new TboxModel(TAG);
    private TboxBean mTboxBean = new TboxBean(0, 0, "", "");
    private TboxCertKey mTboxCertKey = new TboxCertKey(null, null);

    public TboxKeyModel(Context context) {
        this.mContext = context;
    }

    public void registerPropCallback(Collection<Integer> ids, CarEventChangedListener listener) {
        this.mTboxModel.registerPropCallbackWithCache(ids, listener);
    }

    public void startTboxCertInstall() {
        this.mTboxModel.startTboxCertInstall();
    }

    public void startTboxCertVerify() {
        this.mTboxModel.startTboxCertVerify();
    }

    public void getIccid() {
        this.mTboxBean.setRequest(1);
        this.mTboxModel.sendFactoryPreCert(new Gson().toJson(this.mTboxBean));
    }

    public void reqTboxKey(Context context, String type) {
        reqTboxKey(context, "4G", type);
    }

    private void reqTboxKey(Context context, String action, String type) {
        Map<String, String> param = new HashMap<>();
        param.put("app_id", "xmart:appid:002");
        param.put(Constant.HARDWAREID, SystemPropertyUtil.getIccid());
        long time = System.currentTimeMillis();
        param.put("timestamp", String.valueOf(time));
        param.put(Constant.ACTION, action);
        String[] res = parseType(type);
        String url = chooseUrl(res);
        if (url.equals(URL_REQUEST_TBOX_KEY_V3)) {
            param.put(Constant.VEHICLE_MODEL, res[0]);
            LogUtils.d(TAG, "vehicle model: " + res[0]);
        }
        String sign = IndivHelper.sign(context, param, time);
        param.put("sign", sign);
        IHttp http = (IHttp) Module.get(NetworkChannelsEntry.class).get(IHttp.class);
        IRequest uploadJson = http.post(url).uploadJson(new Gson().toJson(param));
        uploadJson.headers("Client", Constant.HTTP_CAR_XMART + BuildInfoUtils.getSystemVersion()).execute(new AnonymousClass1(sign, action, type));
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    /* renamed from: com.xiaopeng.commonfunc.model.security.TboxKeyModel$1  reason: invalid class name */
    /* loaded from: classes4.dex */
    public class AnonymousClass1 implements Callback {
        final /* synthetic */ String val$action;
        final /* synthetic */ String val$sign;
        final /* synthetic */ String val$type;

        AnonymousClass1(String str, String str2, String str3) {
            this.val$sign = str;
            this.val$action = str2;
            this.val$type = str3;
        }

        public /* synthetic */ void lambda$onSuccess$0$TboxKeyModel$1(IResponse iResponse, String sign, String action, String type) {
            TboxKeyModel.this.dealResponse(iResponse, sign, action, type);
        }

        @Override // com.xiaopeng.lib.framework.moduleinterface.netchannelmodule.http.Callback
        public void onSuccess(final IResponse iResponse) {
            final String str = this.val$sign;
            final String str2 = this.val$action;
            final String str3 = this.val$type;
            ThreadUtils.execute(new Runnable() { // from class: com.xiaopeng.commonfunc.model.security.-$$Lambda$TboxKeyModel$1$beUACI1VtjvDqKQYZ42-ANa2P0A
                @Override // java.lang.Runnable
                public final void run() {
                    TboxKeyModel.AnonymousClass1.this.lambda$onSuccess$0$TboxKeyModel$1(iResponse, str, str2, str3);
                }
            });
        }

        @Override // com.xiaopeng.lib.framework.moduleinterface.netchannelmodule.http.Callback
        public void onFailure(IResponse iResponse) {
            EventBus.getDefault().post(new SSLKeyEvent(12));
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void dealResponse(IResponse iResponse, String sign, String action, String type) {
        ServerBean bean = DataHelp.getServerBean(iResponse);
        if (bean != null && bean.getCode() == 200) {
            String content = new String(DataHelp.decrypt(Base64.decode(bean.getData(), 0), DataHelp.getKey(sign, DataHelp.SEC_SALT).getBytes()));
            LogUtils.i(TAG, content);
            char c = 65535;
            try {
                int hashCode = action.hashCode();
                if (hashCode != 1683) {
                    if (hashCode == 94116119 && action.equals(ACTION_4G_COMMON)) {
                        c = 1;
                    }
                } else if (action.equals("4G")) {
                    c = 0;
                }
                if (c == 0) {
                    CertKey[] certKeys = (CertKey[]) new Gson().fromJson(content, new TypeToken<CertKey[]>() { // from class: com.xiaopeng.commonfunc.model.security.TboxKeyModel.2
                    }.getType());
                    this.mTboxCertKey.setCertKeys(certKeys);
                    reqTboxKey(this.mContext, ACTION_4G_COMMON, type);
                    return;
                } else if (c == 1) {
                    CertKey certKey = (CertKey) new Gson().fromJson(content, (Class<Object>) CertKey.class);
                    certKey.setIndex(0);
                    this.mTboxCertKey.setBLeSecKey(certKey);
                    FileUtil.writeNCreateFile(PATH_TEMP_KEY, new Gson().toJson(this.mTboxCertKey));
                    requestInstallTboxKey();
                    return;
                } else {
                    return;
                }
            } catch (Exception e) {
                LogUtils.e(TAG, "save cert fail : " + e.getMessage());
                EventBus.getDefault().post(new SSLKeyEvent(14));
                return;
            }
        }
        EventBus.getDefault().post(new SSLKeyEvent(13));
    }

    private void requestInstallTboxKey() throws FileNotFoundException {
        String cmd;
        this.mTboxBean.setRequest(2);
        this.mTboxBean.setStringValue2(MD5Utils.getFileMd5(new File(PATH_TEMP_KEY)));
        if (TftpSender.TBOX_SUPPORT_SSH) {
            if (SUPPORT_TBOX_ETH_CONNECTION) {
                this.mTboxBean.setStringValue1(ETH_TBOX_PATH_TEMP_KEY);
                cmd = ScpUtil.genScpToServerCmd(ScpUtil.SSH_ROOT_USER, ScpUtil.HOST_ETH_TBOX, PATH_TEMP_KEY, ETH_TBOX_PATH_TEMP_KEY, ScpUtil.FILE_SCP_PUBKEY);
            } else {
                this.mTboxBean.setStringValue1(TBOX_PATH_TEMP_KEY);
                cmd = ScpUtil.genScpToServerCmd(ScpUtil.SSH_ROOT_USER, ScpUtil.HOST_TBOX, PATH_TEMP_KEY, TBOX_PATH_TEMP_KEY, ScpUtil.FILE_SCP_PUBKEY);
            }
            ProcessUtil.execCommand(cmd);
        } else {
            this.mTboxBean.setStringValue1(ETH_TBOX_PATH_TEMP_KEY);
            try {
                TftpSender.sendFile(PATH_TEMP_KEY, ETH_TBOX_PATH_TEMP_KEY, TftpSender.HOST_TBOX);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        this.mTboxModel.sendFactoryPreCert(new Gson().toJson(this.mTboxBean));
        FileUtil.deleteFile(PATH_TEMP_KEY);
    }

    public void requestGetTboxKey() {
        this.mTboxBean.setRequest(3);
        this.mTboxModel.sendFactoryPreCert(new Gson().toJson(this.mTboxBean));
    }

    public boolean copyTboxKeyToLocal(String tboxCertPath, String md5) {
        String cmd;
        try {
            FileUtil.mkDir(PATH_CACHE_FACTORY);
            FileUtil.deleteFile(PATH_TEMP_KEY);
            if (!TftpSender.TBOX_SUPPORT_SSH) {
                TftpSender.receiveFile(tboxCertPath, PATH_TEMP_KEY, TftpSender.HOST_TBOX);
            } else {
                if (SUPPORT_TBOX_ETH_CONNECTION) {
                    cmd = ScpUtil.genScpFromServerCmd(ScpUtil.SSH_ROOT_USER, ScpUtil.HOST_ETH_TBOX, tboxCertPath, PATH_TEMP_KEY, ScpUtil.FILE_SCP_PUBKEY);
                } else {
                    String cmd2 = ScpUtil.HOST_TBOX;
                    cmd = ScpUtil.genScpFromServerCmd(ScpUtil.SSH_ROOT_USER, cmd2, tboxCertPath, PATH_TEMP_KEY, ScpUtil.FILE_SCP_PUBKEY);
                }
                ProcessUtil.execCommand(cmd);
            }
            String fileMd5 = MD5Utils.getFileMd5(new File(PATH_TEMP_KEY));
            LogUtils.i(TAG, "fileMd5 : " + fileMd5 + ", md5 : " + md5);
            return md5.equalsIgnoreCase(fileMd5);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            return false;
        }
    }

    public void verifyTboxCert(ICallback<String, String> callback) {
        boolean result = false;
        TboxVerifyKey tboxVerifyKey = (TboxVerifyKey) new Gson().fromJson(FileUtil.readAll(PATH_TEMP_KEY), (Class<Object>) TboxVerifyKey.class);
        CertKey[] certKeys = tboxVerifyKey.getCertKeys();
        int length = certKeys.length;
        int i = 0;
        while (true) {
            if (i >= length) {
                break;
            }
            CertKey certKey = certKeys[i];
            FileUtil.writeNCreateFile("/cache/factory/CERT" + certKey.getIndex(), certKey.getCert());
            FileUtil.writeNCreateFile("/cache/factory/KEY" + certKey.getIndex(), certKey.getKey());
            ProcessUtil.changePemToPkcs12("/cache/factory/KEY" + certKey.getIndex(), "/cache/factory/CERT" + certKey.getIndex(), TBOX_CERT_PKCS12_PASS, "/cache/factory/PKCS12_" + certKey.getIndex());
            CertVerifyModel certVerifyModel = new CertVerifyModel(this.mContext, "/cache/factory/PKCS12_" + certKey.getIndex(), TBOX_CA_CERT_PATH, Support.Url.getUrl(Support.Url.CHECK_TBOX_KEY), TBOX_CERT_PKCS12_PASS, false, false);
            if (!certVerifyModel.verifyCert()) {
                LogUtils.e(TAG, "verifyCert FAIL when cert " + certKey.getIndex());
                result = false;
                break;
            }
            result = true;
            i++;
        }
        if (callback != null) {
            if (result) {
                callback.onSuccess(null);
            } else {
                callback.onError(null);
            }
        }
        FileUtil.deleteFolderFile(PATH_CACHE_FACTORY);
    }

    private String chooseUrl(String[] res) {
        if (res != null) {
            if (!res[1].equalsIgnoreCase("v1")) {
                return null;
            }
            String url = URL_REQUEST_TBOX_KEY_V3;
            return url;
        }
        String url2 = URL_REQUEST_TBOX_KEY;
        return url2;
    }

    private String[] parseType(String type) {
        if (!TextUtils.isEmpty(type)) {
            String[] res = type.split(Constant.HYPHEN);
            if (res.length == 2) {
                String[] temp = res[0].split(Constant.DATA_SEPARATOR_STRING);
                if (temp.length == 1 || temp.length == 2) {
                    res[0] = temp[0];
                    return res;
                }
                return null;
            }
            return null;
        }
        return null;
    }

    public void onDestroy() {
        this.mTboxModel.onDestroy();
    }
}
