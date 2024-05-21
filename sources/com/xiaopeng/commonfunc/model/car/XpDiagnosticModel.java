package com.xiaopeng.commonfunc.model.car;

import android.car.diagnostic.XpDiagnosticManager;
import com.xiaopeng.lib.utils.LogUtils;
/* loaded from: classes4.dex */
public class XpDiagnosticModel extends CommonCarModel<XpDiagnosticManager> {
    private static final String TAG = "XpDiagnosticModel";

    public XpDiagnosticModel(String name) {
        super(name);
    }

    @Override // com.xiaopeng.commonfunc.model.car.CommonCarModel
    protected String getServiceName() {
        return "xp_diagnostic";
    }

    public boolean sendMcuDiagCmdAck(byte[] msg) {
        boolean res = false;
        try {
            XpDiagnosticManager xpDiagnosticManager = getCarManager();
            if (xpDiagnosticManager != null) {
                xpDiagnosticManager.sendMcuDiagCmdAck(msg);
                res = true;
            } else {
                LogUtils.e(TAG, "sendMcuDiagCmdAck fail xpDiagnosticManager == null");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return res;
    }
}
