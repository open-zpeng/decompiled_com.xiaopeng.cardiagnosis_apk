package com.xiaopeng.logictree.handler;

import android.app.Application;
import android.net.InterfaceConfiguration;
import android.net.LinkAddress;
import android.os.INetworkManagementService;
import android.os.RemoteException;
import android.os.ServiceManager;
import com.xiaopeng.commonfunc.model.factorytest.hardwaretest.PhyModel;
import com.xiaopeng.commonfunc.utils.ProcessUtil;
import com.xiaopeng.lib.utils.LogUtils;
import com.xiaopeng.lib.utils.info.BuildInfoUtils;
import com.xiaopeng.logictree.IssueInfo;
import com.xiaopeng.logictree.LogicTreeHelper;
import java.net.InetAddress;
/* loaded from: classes5.dex */
public class NetworkInfo extends LogicActionHandler {
    private final INetworkManagementService mNetworkManagementService;

    public NetworkInfo(Application application) {
        super(application);
        this.CLASS_NAME = "NetworkInfo";
        this.mNetworkManagementService = INetworkManagementService.Stub.asInterface(ServiceManager.getService("network_management"));
    }

    @Override // com.xiaopeng.logictree.handler.LogicActionHandler
    public synchronized String handleCommand(IssueInfo issueInfo) {
        super.handleCommand(issueInfo);
        if (checkArgu(this.argus, new String[]{"1"})) {
            LogUtils.i(this.CLASS_NAME, "check whether iface exist ? ");
            boolean exist = false;
            try {
                String[] ifaces = this.mNetworkManagementService.listInterfaces();
                int length = ifaces.length;
                int i = 0;
                while (true) {
                    if (i >= length) {
                        break;
                    }
                    String iface = ifaces[i];
                    if (!iface.equalsIgnoreCase(this.argus[1])) {
                        i++;
                    } else {
                        exist = true;
                        break;
                    }
                }
            } catch (RemoteException e) {
                e.printStackTrace();
            }
            if (exist) {
                LogicTreeHelper.responseOK();
            } else {
                LogicTreeHelper.responseNG();
            }
        } else if (checkArgu(this.argus, new String[]{"2"})) {
            LogUtils.i(this.CLASS_NAME, "check whether iface up ? ");
            InterfaceConfiguration ifcg = getInterfaceConfiguration(this.argus[1]);
            if (ifcg != null && ifcg.isUp()) {
                LogicTreeHelper.responseOK();
            } else {
                LogicTreeHelper.responseNG();
            }
        } else if (checkArgu(this.argus, new String[]{"3"})) {
            LogUtils.i(this.CLASS_NAME, "check whether ip address exist ? ");
            InterfaceConfiguration ifcg2 = getInterfaceConfiguration(this.argus[1]);
            String address = null;
            if (ifcg2 != null) {
                LinkAddress linkAddr = ifcg2.getLinkAddress();
                LogUtils.i("getIpAddress", "linkAddr" + linkAddr.toString());
                InetAddress inetaddr = linkAddr.getAddress();
                LogUtils.i("getIpAddress", "inetaddr" + inetaddr.toString());
                address = inetaddr.getHostAddress();
                if (address != null) {
                    LogUtils.i("getIpAddress", "address " + address);
                }
            }
            if (address != null) {
                LogicTreeHelper.responseOK();
            } else {
                LogicTreeHelper.responseNG();
            }
        } else if (checkArgu(this.argus, new String[]{BuildInfoUtils.BID_LAN})) {
            LogUtils.i(this.CLASS_NAME, "check whether phy link up ? ");
            String stat = ProcessUtil.getPhyLinkStat();
            if (PhyModel.PHY_LINK_UP_STRING.equalsIgnoreCase(stat)) {
                LogicTreeHelper.responseOK();
            } else {
                ProcessUtil.getPhySqi();
                LogicTreeHelper.responseNG();
            }
        }
        return null;
    }

    private InterfaceConfiguration getInterfaceConfiguration(String type) {
        try {
            InterfaceConfiguration ifcg = this.mNetworkManagementService.getInterfaceConfig(type);
            return ifcg;
        } catch (RemoteException e) {
            e.printStackTrace();
            return null;
        }
    }

    @Override // com.xiaopeng.logictree.handler.LogicActionHandler
    public void destroy() {
        super.destroy();
    }
}
