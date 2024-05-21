package com.xiaopeng.commonfunc.utils;

import com.google.gson.Gson;
import com.xiaopeng.lib.framework.module.Module;
import com.xiaopeng.lib.framework.moduleinterface.netchannelmodule.http.Callback;
import com.xiaopeng.lib.framework.moduleinterface.netchannelmodule.http.IHttp;
import com.xiaopeng.lib.framework.netchannelmodule.NetworkChannelsEntry;
import com.xiaopeng.lib.utils.ThreadUtils;
import com.xiaopeng.lib.utils.config.CommonConfig;
import com.xiaopeng.xmlconfig.Support;
import java.util.HashMap;
import java.util.Map;
/* loaded from: classes4.dex */
public class HttpUtil {
    private static final String TAG = "HttpUtil";
    public static final String URL_GEN_UUID = CommonConfig.HTTP_HOST + Support.Url.getUrl(Support.Url.GEN_UUID);
    private static final String URL_UPLOAD_HW_INFO = CommonConfig.HTTP_HOST + Support.Url.getUrl(Support.Url.UPLOAD_HW_INFO);

    public static void genUuid(final Callback callback) {
        ThreadUtils.execute(new Runnable() { // from class: com.xiaopeng.commonfunc.utils.-$$Lambda$HttpUtil$smqW5sQ9kgHg01ZPt143s5WeF5A
            @Override // java.lang.Runnable
            public final void run() {
                HttpUtil.lambda$genUuid$0(Callback.this);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public static /* synthetic */ void lambda$genUuid$0(Callback callback) {
        Map<String, String> param = new HashMap<>();
        IHttp http = (IHttp) Module.get(NetworkChannelsEntry.class).get(IHttp.class);
        http.cancelTag(URL_GEN_UUID);
        http.bizHelper().post(URL_GEN_UUID, new Gson().toJson(param)).build().tag(URL_GEN_UUID).execute(callback);
    }

    public static void uploadHwInfo(final Map<String, String> param, final Callback callback) {
        ThreadUtils.execute(new Runnable() { // from class: com.xiaopeng.commonfunc.utils.-$$Lambda$HttpUtil$rA87NcIN_K6sqlJNrTWVEqvxWFs
            @Override // java.lang.Runnable
            public final void run() {
                HttpUtil.lambda$uploadHwInfo$1(param, callback);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public static /* synthetic */ void lambda$uploadHwInfo$1(Map param, Callback callback) {
        IHttp http = (IHttp) Module.get(NetworkChannelsEntry.class).get(IHttp.class);
        http.cancelTag(URL_UPLOAD_HW_INFO);
        http.bizHelper().post(URL_UPLOAD_HW_INFO, new Gson().toJson(param)).needAuthorizationInfo().build().tag(URL_UPLOAD_HW_INFO).execute(callback);
    }

    /* JADX WARN: Removed duplicated region for block: B:20:0x003a  */
    /* JADX WARN: Removed duplicated region for block: B:25:0x0047 A[Catch: Exception -> 0x0051, TryCatch #0 {Exception -> 0x0051, blocks: (B:3:0x0002, B:23:0x003f, B:24:0x0043, B:25:0x0047, B:27:0x004d, B:10:0x001b, B:13:0x0025, B:16:0x002f), top: B:33:0x0002 }] */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct add '--show-bad-code' argument
    */
    public static java.lang.String getHost(java.lang.String r7) {
        /*
            java.lang.String r0 = com.xiaopeng.lib.utils.config.CommonConfig.HTTP_HOST
            java.lang.String r1 = com.xiaopeng.xmlconfig.Support.Url.getHost(r7)     // Catch: java.lang.Exception -> L51
            r2 = -1
            int r3 = r1.hashCode()     // Catch: java.lang.Exception -> L51
            r4 = -79017120(0xfffffffffb4a4b60, float:-1.05037275E36)
            r5 = 2
            r6 = 1
            if (r3 == r4) goto L2f
            r4 = 3179(0xc6b, float:4.455E-42)
            if (r3 == r4) goto L25
            r4 = 3248(0xcb0, float:4.551E-42)
            if (r3 == r4) goto L1b
        L1a:
            goto L38
        L1b:
            java.lang.String r3 = "eu"
            boolean r3 = r1.equals(r3)     // Catch: java.lang.Exception -> L51
            if (r3 == 0) goto L1a
            r2 = r5
            goto L38
        L25:
            java.lang.String r3 = "cn"
            boolean r3 = r1.equals(r3)     // Catch: java.lang.Exception -> L51
            if (r3 == 0) goto L1a
            r2 = r6
            goto L38
        L2f:
            java.lang.String r3 = "optional"
            boolean r3 = r1.equals(r3)     // Catch: java.lang.Exception -> L51
            if (r3 == 0) goto L1a
            r2 = 0
        L38:
            if (r2 == 0) goto L47
            if (r2 == r6) goto L43
            if (r2 == r5) goto L3f
            goto L50
        L3f:
            java.lang.String r2 = com.xiaopeng.lib.utils.config.CommonConfig.HTTP_HOST_EU     // Catch: java.lang.Exception -> L51
            r0 = r2
            goto L50
        L43:
            java.lang.String r2 = com.xiaopeng.lib.utils.config.CommonConfig.HTTP_HOST     // Catch: java.lang.Exception -> L51
            r0 = r2
            goto L50
        L47:
            boolean r2 = android.car.Car.isExportVersion()     // Catch: java.lang.Exception -> L51
            if (r2 == 0) goto L50
            java.lang.String r2 = com.xiaopeng.lib.utils.config.CommonConfig.HTTP_HOST_EU     // Catch: java.lang.Exception -> L51
            r0 = r2
        L50:
            goto L55
        L51:
            r1 = move-exception
            r1.printStackTrace()
        L55:
            return r0
        */
        throw new UnsupportedOperationException("Method not decompiled: com.xiaopeng.commonfunc.utils.HttpUtil.getHost(java.lang.String):java.lang.String");
    }
}
