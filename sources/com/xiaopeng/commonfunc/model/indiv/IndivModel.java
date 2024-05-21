package com.xiaopeng.commonfunc.model.indiv;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.NonNull;
import android.text.TextUtils;
import com.google.gson.Gson;
import com.xiaopeng.commonfunc.Constant;
import com.xiaopeng.commonfunc.callback.SecurityCallback;
import com.xiaopeng.commonfunc.utils.DataHelp;
import com.xiaopeng.commonfunc.utils.IndivHelper;
import com.xiaopeng.lib.framework.module.Module;
import com.xiaopeng.lib.framework.moduleinterface.netchannelmodule.http.IHttp;
import com.xiaopeng.lib.framework.moduleinterface.netchannelmodule.http.IResponse;
import com.xiaopeng.lib.framework.netchannelmodule.NetworkChannelsEntry;
import com.xiaopeng.lib.http.server.ServerBean;
import com.xiaopeng.lib.security.xmartv1.RandomKeySecurity;
import com.xiaopeng.lib.security.xmartv1.XmartV1Constants;
import com.xiaopeng.lib.utils.LogUtils;
import com.xiaopeng.lib.utils.ThreadUtils;
import com.xiaopeng.lib.utils.config.CommonConfig;
import com.xiaopeng.xmlconfig.Support;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
/* loaded from: classes4.dex */
public class IndivModel {
    public static final int INDIV_CHECKING = 1;
    public static final int INDIV_CHECK_DEFAULT = -1;
    public static final int INDIV_CHECK_FAIL = 0;
    public static final int INDIV_CHECK_PASS = 2;
    private static final String TAG = "IndivModel";
    private static final String TESTING_TEXT = "工厂检查indiv加解密状态";
    private static final int TIME_OUT = 1;
    private static final int WAITING_TIMES = 30000;
    private Context mContext;
    private final Handler mHandler = new Handler(ThreadUtils.getLooper(1)) { // from class: com.xiaopeng.commonfunc.model.indiv.IndivModel.1
        @Override // android.os.Handler
        public void handleMessage(@NonNull Message msg) {
            super.handleMessage(msg);
            if (msg.what == 1) {
                LogUtils.i(IndivModel.TAG, "handleMessage TIME_OUT");
                if (IndivModel.this.mSecurityCallback != null) {
                    IndivModel.this.mSecurityCallback.onReceiveResult(6, 2);
                }
            }
        }
    };
    private final BroadcastReceiver mReceiver = new BroadcastReceiver() { // from class: com.xiaopeng.commonfunc.model.indiv.IndivModel.2
        @Override // android.content.BroadcastReceiver
        public void onReceive(Context context, Intent intent) {
            String action = intent.getAction();
            if ("com.xiaopeng.action.SECURE_STORE_RELOAD".equals(action)) {
                LogUtils.i(IndivModel.TAG, "onReceive ACTION_BROADCAST_SECURE_STORE_RELOAD");
                if (IndivModel.this.mHandler.hasMessages(1)) {
                    IndivModel.this.mHandler.removeMessages(1);
                    if (IndivModel.this.mSecurityCallback != null) {
                        IndivModel.this.mSecurityCallback.onReceiveResult(6, 1);
                    }
                }
                IndivHelper.initMain(IndivModel.this.mContext);
            }
        }
    };
    private SecurityCallback mSecurityCallback;
    private static final String URL_INDIV_TEST_ENCRYPTION = CommonConfig.HTTP_HOST + Support.Url.getUrl(Support.Url.INDIV_TEST_ENCRYPTION);
    private static final String URL_INDIV_TEST_DECRYPTION = CommonConfig.HTTP_HOST + Support.Url.getUrl(Support.Url.INDIV_TEST_DECRYPTION);

    public IndivModel(Context context, SecurityCallback callback) {
        this.mSecurityCallback = callback;
        this.mContext = context;
    }

    public void init() {
        IntentFilter filter = new IntentFilter();
        filter.addAction("com.xiaopeng.action.SECURE_STORE_RELOAD");
        this.mContext.registerReceiver(this.mReceiver, filter);
    }

    public void deinit() {
        this.mContext.unregisterReceiver(this.mReceiver);
    }

    public boolean isTeeInitSuccess() {
        return IndivHelper.isInitSuccess();
    }

    public void clearLocalIndividualData() {
        IndivHelper.clearLocalIndividualData();
    }

    public void asyncInitIndivService() {
        this.mHandler.sendEmptyMessageDelayed(1, 30000L);
        IndivHelper.asyncInitIndivService();
    }

    public void checkIndiv() {
        ThreadUtils.postBackground(new Runnable() { // from class: com.xiaopeng.commonfunc.model.indiv.-$$Lambda$IndivModel$1Jo0twy0myj_VBDkiTWzjgjL3zw
            @Override // java.lang.Runnable
            public final void run() {
                IndivModel.this.lambda$checkIndiv$0$IndivModel();
            }
        });
    }

    public /* synthetic */ void lambda$checkIndiv$0$IndivModel() {
        boolean result = testEncryption() && testDecryption();
        SecurityCallback securityCallback = this.mSecurityCallback;
        if (securityCallback != null) {
            securityCallback.onReceiveResult(0, result ? 1 : 2);
        }
    }

    private boolean testEncryption() {
        EncryptCheckBean bean = new EncryptCheckBean();
        bean.rawData = TESTING_TEXT;
        bean.teeDataList = new ArrayList<>();
        for (int i = 0; i < XmartV1Constants.LOCAL_KEYS_NUM; i++) {
            bean.teeDataList.add(RandomKeySecurity.getInstance().encodeWithIndex(TESTING_TEXT, i));
        }
        String requestJson = new Gson().toJson(bean);
        LogUtils.i(TAG, "Encrypt check string:" + requestJson);
        try {
            IHttp http = (IHttp) Module.get(NetworkChannelsEntry.class).get(IHttp.class);
            IResponse response = http.bizHelper().post(URL_INDIV_TEST_ENCRYPTION, requestJson).build().execute();
            ServerBean serverBean = DataHelp.getServerBean(response);
            boolean result = serverBean.getCode() == 200;
            return result;
        } catch (Exception ex) {
            ex.printStackTrace();
            return false;
        }
    }

    private boolean testDecryption() {
        EncryptCheckBean bean;
        Map<String, String> param = new HashMap<>(1);
        param.put(Constant.KEY_RAW_DATA, TESTING_TEXT);
        try {
            IHttp http = (IHttp) Module.get(NetworkChannelsEntry.class).get(IHttp.class);
            IResponse response = http.bizHelper().post(URL_INDIV_TEST_DECRYPTION, new Gson().toJson(param)).build().execute();
            ServerBean serverBean = DataHelp.getServerBean(response);
            if (serverBean.getCode() != 200) {
                return false;
            }
            String data = serverBean.getData();
            if (!TextUtils.isEmpty(data) && (bean = (EncryptCheckBean) new Gson().fromJson(data, (Class<Object>) EncryptCheckBean.class)) != null && bean.teeDataList.size() == XmartV1Constants.LOCAL_KEYS_NUM) {
                for (int i = 0; i < bean.teeDataList.size(); i++) {
                    if (!TESTING_TEXT.equals(RandomKeySecurity.getInstance().decode(bean.teeDataList.get(i)))) {
                        LogUtils.i(TAG, "Decryption check failed with key: #" + i);
                        return false;
                    }
                }
                return true;
            }
            return false;
        } catch (Exception ex) {
            ex.printStackTrace();
            return false;
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* loaded from: classes4.dex */
    public class EncryptCheckBean {
        public String rawData;
        public ArrayList<String> teeDataList;

        private EncryptCheckBean() {
        }
    }
}
