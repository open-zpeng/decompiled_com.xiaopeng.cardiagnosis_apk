package com.xiaopeng.commonfunc.utils.internal;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.util.Base64;
import android.util.Log;
import com.google.gson.Gson;
import com.xiaopeng.commonfunc.utils.DataHelp;
import com.xiaopeng.lib.framework.module.Module;
import com.xiaopeng.lib.framework.moduleinterface.netchannelmodule.http.IHttp;
import com.xiaopeng.lib.framework.moduleinterface.netchannelmodule.http.IRequest;
import com.xiaopeng.lib.framework.moduleinterface.netchannelmodule.http.IResponse;
import com.xiaopeng.lib.framework.netchannelmodule.NetworkChannelsEntry;
import com.xiaopeng.lib.http.server.ServerBean;
import com.xiaopeng.lib.security.ISecurityModule;
import com.xiaopeng.lib.security.SecurityCommon;
import com.xiaopeng.lib.security.xmartv1.XmartV1Constants;
import com.xiaopeng.lib.utils.LogUtils;
import com.xiaopeng.lib.utils.config.CommonConfig;
import com.xiaopeng.lib.utils.info.BuildInfoUtils;
import com.xiaopeng.xmlconfig.Support;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.HashMap;
import java.util.Map;
import javax.crypto.Cipher;
import javax.crypto.spec.SecretKeySpec;
import org.json.JSONObject;
/* loaded from: classes4.dex */
public class XmartV1IndivHelper implements IIndivInternal {
    private static final String TAG = "XmartV1IndivHelper";
    private static final String URL_INDIV_SERVICE = CommonConfig.HTTP_HOST + Support.Url.getUrl(Support.Url.INDIV_SERVICE);

    public static XmartV1IndivHelper getInstance() {
        return Holder.INSTANCE;
    }

    @Nullable
    public static byte[] getMd5(String data) {
        LogUtils.d(TAG, "Device ID:" + data);
        byte[] bytes = null;
        try {
            MessageDigest digest = MessageDigest.getInstance("MD5");
            bytes = digest.digest(data.getBytes(StandardCharsets.UTF_8));
        } catch (NoSuchAlgorithmException e) {
        }
        LogUtils.d(TAG, "Key:" + SecurityCommon.parseByte2HexStr(bytes));
        return bytes;
    }

    private static String decrypt(byte[] content, byte[] password) {
        try {
            SecretKeySpec key = new SecretKeySpec(password, "AES");
            Cipher cipher = Cipher.getInstance("AES/ECB/PKCS5Padding");
            cipher.init(2, key);
            byte[] result = cipher.doFinal(content);
            return new String(result, StandardCharsets.UTF_8);
        } catch (Exception ex) {
            ex.printStackTrace();
            return null;
        }
    }

    @Override // com.xiaopeng.commonfunc.utils.internal.IIndivInternal
    public IRequest buildIndivRequest(Context context, ISecurityModule security) throws Exception {
        LogUtils.d(TAG, "Build the individual request ......");
        Map<String, Integer> param = new HashMap<>();
        param.put("version", Integer.valueOf(XmartV1Constants.ALGORITHM_DEFAULT_REVISION));
        IHttp http = (IHttp) Module.get(NetworkChannelsEntry.class).get(IHttp.class);
        return http.bizHelper().post(URL_INDIV_SERVICE, new Gson().toJson(param)).build();
    }

    @Override // com.xiaopeng.commonfunc.utils.internal.IIndivInternal
    public String getIndivDataFromResponse(IResponse response) {
        String result = null;
        ServerBean bean = DataHelp.getServerBean(response);
        if (bean != null) {
            try {
                if (200 == bean.getCode()) {
                    String data = bean.getData();
                    if (!TextUtils.isEmpty(data)) {
                        Log.d(TAG, "Get individual info success");
                        JSONObject jsonObject = new JSONObject(data);
                        result = jsonObject.getString("secreteKey");
                        byte[] bytes = Base64.decode(result, 0);
                        return decrypt(bytes, getMd5(BuildInfoUtils.getHardwareId()));
                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
                Log.d(TAG, "Get individual info failed");
            }
        }
        return result;
    }

    @Override // com.xiaopeng.commonfunc.utils.internal.IIndivInternal
    public IRequest buildIndivSavingRequest(Context context, ISecurityModule security) {
        return null;
    }

    @Override // com.xiaopeng.commonfunc.utils.internal.IIndivInternal
    public boolean checkIndivSavingResult(IResponse response) {
        return true;
    }

    @Override // com.xiaopeng.commonfunc.utils.internal.IIndivInternal
    public void notifyIndivFinished(Context context) {
        LogUtils.d(TAG, "Individual actions were finished!");
        try {
            Intent intent = new Intent();
            intent.setAction("com.xiaopeng.action.SECURE_STORE_RELOAD");
            if (context != null) {
                context.sendBroadcast(intent);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /* loaded from: classes4.dex */
    private static final class Holder {
        private static final XmartV1IndivHelper INSTANCE = new XmartV1IndivHelper();

        private Holder() {
        }
    }
}
