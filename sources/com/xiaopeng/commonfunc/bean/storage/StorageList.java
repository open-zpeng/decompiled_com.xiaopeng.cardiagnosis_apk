package com.xiaopeng.commonfunc.bean.storage;

import com.google.gson.annotations.SerializedName;
import com.xiaopeng.lib.apirouter.ClientConstants;
/* loaded from: classes4.dex */
public class StorageList {
    @SerializedName("bigfilelist")
    private StorageData[] bigfilelist;
    @SerializedName("cachelist")
    private StorageData[] cachelist;
    @SerializedName("loglist")
    private StorageData[] loglist;

    public StorageList(StorageData[] loglist, StorageData[] cachelist, StorageData[] bigfilelist) {
        this.loglist = loglist;
        this.cachelist = cachelist;
        this.bigfilelist = bigfilelist;
    }

    public StorageData[] getLoglist() {
        return this.loglist;
    }

    public StorageData[] getCachelist() {
        return this.cachelist;
    }

    public StorageData[] getBigfilelist() {
        return this.bigfilelist;
    }

    /* loaded from: classes4.dex */
    public class StorageData {
        @SerializedName(ClientConstants.ALIAS.PATH)
        private final String path;
        @SerializedName("suffix")
        private final String suffix;

        public StorageData(String path, String suffix) {
            this.path = path;
            this.suffix = suffix;
        }

        public String getPath() {
            return this.path;
        }

        public String getSuffix() {
            return this.suffix;
        }

        public String toString() {
            return "StorageData{path=" + this.path + ", suffix='" + this.suffix + '}';
        }
    }
}
