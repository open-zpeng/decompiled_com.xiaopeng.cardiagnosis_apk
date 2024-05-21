package com.xiaopeng.commonfunc.bean.car;
/* loaded from: classes4.dex */
public class IPNPort {
    private String ip;
    private String port;

    public IPNPort() {
    }

    public IPNPort(String ip, String port) {
        this.ip = ip;
        this.port = port;
    }

    public String getIp() {
        return this.ip;
    }

    public String getPort() {
        return this.port;
    }

    public String toString() {
        return "IPNPort{ip='" + this.ip + "', port='" + this.port + "'}";
    }
}
