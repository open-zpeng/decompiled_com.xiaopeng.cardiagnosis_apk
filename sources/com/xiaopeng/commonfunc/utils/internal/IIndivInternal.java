package com.xiaopeng.commonfunc.utils.internal;

import android.content.Context;
import com.xiaopeng.lib.framework.moduleinterface.netchannelmodule.http.IRequest;
import com.xiaopeng.lib.framework.moduleinterface.netchannelmodule.http.IResponse;
import com.xiaopeng.lib.security.ISecurityModule;
/* loaded from: classes4.dex */
public interface IIndivInternal {
    IRequest buildIndivRequest(Context context, ISecurityModule iSecurityModule) throws Exception;

    IRequest buildIndivSavingRequest(Context context, ISecurityModule iSecurityModule) throws Exception;

    boolean checkIndivSavingResult(IResponse iResponse);

    String getIndivDataFromResponse(IResponse iResponse);

    void notifyIndivFinished(Context context);
}
