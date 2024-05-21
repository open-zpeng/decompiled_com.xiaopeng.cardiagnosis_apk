package com.xiaopeng.commonfunc.model.security;

import android.content.Context;
import android.os.FileUtils;
import android.text.TextUtils;
import android.util.Base64;
import com.lzy.okgo.interceptor.HttpLoggingInterceptor;
import com.xiaopeng.lib.http.ICallback;
import com.xiaopeng.lib.http.tls.HostNameVerifier;
import com.xiaopeng.lib.http.tls.SSLHelper;
import com.xiaopeng.lib.security.xmartv1.XmartV1Constants;
import com.xiaopeng.lib.utils.LogUtils;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.security.KeyManagementException;
import java.security.KeyStore;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.security.UnrecoverableKeyException;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;
import java.util.logging.Level;
import javax.net.ssl.KeyManager;
import javax.net.ssl.KeyManagerFactory;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSocketFactory;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.ConnectionPool;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
/* loaded from: classes4.dex */
public class CertVerifyModel {
    private static final String KEY_STORE_TYPE_BKS = "bks";
    private static final String KEY_STORE_TYPE_P12 = "PKCS12";
    private static final String ROOT_CERT_KEY_PASSWORD = "chengzi";
    private static final String TAG = "CertVerifyModel";
    public static final String WIFI_CLIENT_CERT_KEY_PASSWORD = "jCmX14gy4XLe";
    private final boolean mCaBaseEncode;
    private final boolean mClientBaseEncode;
    private final String mClientCertPath;
    private final String mClientPassword;
    private Context mContext;
    private final String mRootCertPath;
    private final String mVerifyUrl;

    public CertVerifyModel(Context context, String clientCertPath, String rootCertPath, String url, String clientPassword) {
        this.mContext = context;
        this.mClientCertPath = clientCertPath;
        this.mRootCertPath = rootCertPath;
        this.mVerifyUrl = url;
        this.mClientPassword = clientPassword;
        this.mClientBaseEncode = true;
        this.mCaBaseEncode = true;
    }

    public CertVerifyModel(Context context, String clientCertPath, String rootCertPath, String url, String clientPassword, boolean clientBaseEncode, boolean caBaseEncode) {
        this.mContext = context;
        this.mClientCertPath = clientCertPath;
        this.mRootCertPath = rootCertPath;
        this.mVerifyUrl = url;
        this.mClientPassword = clientPassword;
        this.mClientBaseEncode = clientBaseEncode;
        this.mCaBaseEncode = caBaseEncode;
    }

    private synchronized KeyManager[] getKeyManagers(Context context) {
        InputStream ksIn;
        try {
            try {
                try {
                    try {
                        KeyStore keyStore = KeyStore.getInstance(KEY_STORE_TYPE_P12);
                        if (this.mClientBaseEncode) {
                            LogUtils.i(TAG, "get client cert content from file mClientCertPath:" + this.mClientCertPath);
                            String certContent = FileUtils.readTextFile(new File(this.mClientCertPath), 0, null);
                            ksIn = TextUtils.isEmpty(certContent) ? null : new ByteArrayInputStream(Base64.decode(certContent, 0));
                        } else {
                            ksIn = new FileInputStream(new File(this.mClientCertPath));
                        }
                        try {
                            if (ksIn != null) {
                                try {
                                    try {
                                        keyStore.load(ksIn, this.mClientPassword.toCharArray());
                                        try {
                                            ksIn.close();
                                        } catch (IOException e) {
                                            e = e;
                                            e.printStackTrace();
                                            KeyManagerFactory kmf = KeyManagerFactory.getInstance("X509");
                                            kmf.init(keyStore, this.mClientPassword.toCharArray());
                                            return kmf.getKeyManagers();
                                        }
                                    } catch (NoSuchAlgorithmException e2) {
                                        e2.printStackTrace();
                                        try {
                                            ksIn.close();
                                        } catch (IOException e3) {
                                            e = e3;
                                            e.printStackTrace();
                                            KeyManagerFactory kmf2 = KeyManagerFactory.getInstance("X509");
                                            kmf2.init(keyStore, this.mClientPassword.toCharArray());
                                            return kmf2.getKeyManagers();
                                        }
                                    }
                                } catch (IOException e4) {
                                    e4.printStackTrace();
                                    try {
                                        ksIn.close();
                                    } catch (IOException e5) {
                                        e = e5;
                                        e.printStackTrace();
                                        KeyManagerFactory kmf22 = KeyManagerFactory.getInstance("X509");
                                        kmf22.init(keyStore, this.mClientPassword.toCharArray());
                                        return kmf22.getKeyManagers();
                                    }
                                } catch (CertificateException e6) {
                                    e6.printStackTrace();
                                    try {
                                        ksIn.close();
                                    } catch (IOException e7) {
                                        e = e7;
                                        e.printStackTrace();
                                        KeyManagerFactory kmf222 = KeyManagerFactory.getInstance("X509");
                                        kmf222.init(keyStore, this.mClientPassword.toCharArray());
                                        return kmf222.getKeyManagers();
                                    }
                                }
                            }
                            KeyManagerFactory kmf2222 = KeyManagerFactory.getInstance("X509");
                            kmf2222.init(keyStore, this.mClientPassword.toCharArray());
                            return kmf2222.getKeyManagers();
                        } catch (Throwable th) {
                            try {
                                ksIn.close();
                            } catch (IOException e8) {
                                e8.printStackTrace();
                            }
                            throw th;
                        }
                    } catch (IOException e9) {
                        e9.printStackTrace();
                        return null;
                    }
                } catch (NoSuchAlgorithmException e10) {
                    e10.printStackTrace();
                    return null;
                }
            } catch (KeyStoreException e11) {
                e11.printStackTrace();
                return null;
            }
        } catch (UnrecoverableKeyException e12) {
            e12.printStackTrace();
            return null;
        }
    }

    private SSLSocketFactory getTLS2SocketFactory(Context context) {
        try {
            SSLContext sslContext = SSLContext.getInstance(XmartV1Constants.TLS_REVISION_1_2);
            CompositeX509TrustManager trustManager = new CompositeX509TrustManager(this.mRootCertPath);
            if (!trustManager.isHasValidCert()) {
                LogUtils.e(TAG, "getTLS2SocketFactory trustManager has no valid cert");
            }
            TrustManager[] ts = {trustManager};
            sslContext.init(getKeyManagers(context), ts, new SecureRandom());
            return new SSLHelper.TLS2SocketFactory(sslContext.getSocketFactory());
        } catch (KeyManagementException e) {
            e.printStackTrace();
            return null;
        } catch (NoSuchAlgorithmException e2) {
            e2.printStackTrace();
            return null;
        }
    }

    public X509TrustManager getX509TrustManager() {
        try {
            CompositeX509TrustManager trustManager = new CompositeX509TrustManager(this.mRootCertPath);
            if (!trustManager.isHasValidCert()) {
                LogUtils.e(TAG, "getX509TrustManager trustManager has no valid cert");
            }
            return trustManager;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    private OkHttpClient buildOkHttpClient(Context context) {
        HttpLoggingInterceptor loggingInterceptor = new HttpLoggingInterceptor(TAG);
        loggingInterceptor.setPrintLevel(HttpLoggingInterceptor.Level.BODY);
        loggingInterceptor.setColorLevel(Level.INFO);
        return new OkHttpClient.Builder().sslSocketFactory(getTLS2SocketFactory(context), getX509TrustManager()).connectionSpecs(SSLHelper.getConnectionSpecs()).connectionPool(new ConnectionPool()).addInterceptor(loggingInterceptor).readTimeout(30L, TimeUnit.SECONDS).hostnameVerifier(HostNameVerifier.INSTANCE).build();
    }

    public boolean verifyCert() {
        try {
            Request request = new Request.Builder().url(this.mVerifyUrl).get().build();
            Response response = buildOkHttpClient(this.mContext).newCall(request).execute();
            if (response.code() != 200) {
                return false;
            }
            return true;
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
    }

    public void verifyCert(final ICallback<String, String> callback) {
        Request request = new Request.Builder().url(this.mVerifyUrl).get().build();
        buildOkHttpClient(this.mContext).newCall(request).enqueue(new Callback() { // from class: com.xiaopeng.commonfunc.model.security.CertVerifyModel.1
            @Override // okhttp3.Callback
            public void onFailure(Call call, IOException e) {
                LogUtils.e(CertVerifyModel.TAG, "onFailure:" + e);
                ICallback iCallback = callback;
                if (iCallback != null) {
                    iCallback.onError(null);
                }
            }

            @Override // okhttp3.Callback
            public void onResponse(Call call, Response response) throws IOException {
                LogUtils.i(CertVerifyModel.TAG, "onResponse:" + response.code());
                ICallback iCallback = callback;
                if (iCallback != null) {
                    iCallback.onSuccess(String.valueOf(response.code()));
                }
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* loaded from: classes4.dex */
    public class CompositeX509TrustManager implements X509TrustManager {
        private boolean hasValidCert;
        private final List<X509TrustManager> trustManagers = new ArrayList();
        private final List<X509Certificate> certificates = new ArrayList();

        /* JADX WARN: Removed duplicated region for block: B:48:0x00b4 A[Catch: Exception -> 0x00f2, TryCatch #9 {Exception -> 0x00f2, blocks: (B:3:0x0016, B:5:0x003d, B:7:0x0051, B:13:0x0076, B:24:0x0087, B:45:0x00aa, B:46:0x00ae, B:48:0x00b4, B:50:0x00c2, B:52:0x00ce, B:54:0x00dd, B:56:0x00e0, B:58:0x00e6, B:21:0x0082, B:28:0x008f, B:34:0x0099, B:9:0x005c, B:12:0x006d, B:20:0x007f, B:27:0x008c, B:33:0x0096), top: B:72:0x0016, inners: #4 }] */
        /* JADX WARN: Removed duplicated region for block: B:54:0x00dd A[Catch: Exception -> 0x00f2, TryCatch #9 {Exception -> 0x00f2, blocks: (B:3:0x0016, B:5:0x003d, B:7:0x0051, B:13:0x0076, B:24:0x0087, B:45:0x00aa, B:46:0x00ae, B:48:0x00b4, B:50:0x00c2, B:52:0x00ce, B:54:0x00dd, B:56:0x00e0, B:58:0x00e6, B:21:0x0082, B:28:0x008f, B:34:0x0099, B:9:0x005c, B:12:0x006d, B:20:0x007f, B:27:0x008c, B:33:0x0096), top: B:72:0x0016, inners: #4 }] */
        /* JADX WARN: Removed duplicated region for block: B:82:? A[RETURN, SYNTHETIC] */
        /*
            Code decompiled incorrectly, please refer to instructions dump.
            To view partially-correct add '--show-bad-code' argument
        */
        public CompositeX509TrustManager(java.lang.String r11) {
            /*
                Method dump skipped, instructions count: 247
                To view this dump add '--comments-level debug' option
            */
            throw new UnsupportedOperationException("Method not decompiled: com.xiaopeng.commonfunc.model.security.CertVerifyModel.CompositeX509TrustManager.<init>(com.xiaopeng.commonfunc.model.security.CertVerifyModel, java.lang.String):void");
        }

        @Override // javax.net.ssl.X509TrustManager
        public void checkClientTrusted(X509Certificate[] chain, String authType) throws CertificateException {
            for (X509TrustManager trustManager : this.trustManagers) {
                try {
                    trustManager.checkClientTrusted(chain, authType);
                    return;
                } catch (CertificateException e) {
                }
            }
            throw new CertificateException("None of the TrustManagers trust this client certificate chain");
        }

        @Override // javax.net.ssl.X509TrustManager
        public void checkServerTrusted(X509Certificate[] chain, String authType) throws CertificateException {
            for (X509TrustManager trustManager : this.trustManagers) {
                try {
                    trustManager.checkServerTrusted(chain, authType);
                    return;
                } catch (CertificateException e) {
                }
            }
            throw new CertificateException("None of the TrustManagers trust this server certificate chain");
        }

        @Override // javax.net.ssl.X509TrustManager
        public X509Certificate[] getAcceptedIssuers() {
            return (X509Certificate[]) this.certificates.toArray(new X509Certificate[0]);
        }

        public boolean isHasValidCert() {
            return this.hasValidCert;
        }
    }
}
