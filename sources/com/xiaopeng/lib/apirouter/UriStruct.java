package com.xiaopeng.lib.apirouter;

import android.support.annotation.NonNull;
import com.xiaopeng.commonfunc.Constant;
/* loaded from: classes4.dex */
public class UriStruct {
    public String applicationId;
    public String processTag;
    public String serviceName;

    @NonNull
    public String toString() {
        return this.applicationId + Constant.DOT_STRING + this.serviceName;
    }
}
