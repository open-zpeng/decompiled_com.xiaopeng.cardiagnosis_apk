package com.xiaopeng.commonfunc.utils;

import android.content.ComponentName;
import android.content.Context;
import android.content.ServiceConnection;
import android.os.IBinder;
import com.xiaopeng.aftersales.manager.AfterSalesManager;
import com.xiaopeng.commonfunc.Constant;
import com.xiaopeng.lib.utils.LogUtils;
/* loaded from: classes4.dex */
public class AfterSalesHelper {
    public static final String REPAIRMODE_ACTION_ACTIVATE_XPU = "activate XPU";
    public static final String REPAIRMODE_ACTION_AUTO_CATCH_LOG = "set auto catch log";
    public static final String REPAIRMODE_ACTION_BACKLIGHT_DIAGNOSIS = "back light diagnosis";
    public static final String REPAIRMODE_ACTION_BTHCI_LOG = "set bt hci log";
    public static final String REPAIRMODE_ACTION_BT_DIAGNOSIS = "bt diagnosis";
    public static final String REPAIRMODE_ACTION_BY_PC_COMMAND = "by PC command";
    public static final String REPAIRMODE_ACTION_CAMERA_DIAGNOSIS = "camera diagnosis";
    public static final String REPAIRMODE_ACTION_CAT = "cat";
    public static final String REPAIRMODE_ACTION_CLEAR_INDIV = "clear indiv data";
    public static final String REPAIRMODE_ACTION_CLEAR_LOG = "clear log";
    public static final String REPAIRMODE_ACTION_COLLECT_CANDATA = "collect candata";
    public static final String REPAIRMODE_ACTION_CONNECT_CERT_WIFI = "connect security wifi";
    public static final String REPAIRMODE_ACTION_CONSOLE = "set console";
    public static final String REPAIRMODE_ACTION_COPY_LOG = "copy log";
    public static final String REPAIRMODE_ACTION_COPY_RECORD_MEDIA = "copy record diagnosis media";
    public static final String REPAIRMODE_ACTION_DF = "df -h";
    public static final String REPAIRMODE_ACTION_DU = "du -sh";
    public static final String REPAIRMODE_ACTION_DUMPSYS = "dumpsys meminfo";
    public static final String REPAIRMODE_ACTION_ETH_DIAGNOSIS = "ethernet diagnosis";
    public static final String REPAIRMODE_ACTION_EXEC_ENCRYPTSH = "execute xiaopeng tool";
    public static final String REPAIRMODE_ACTION_FAIL = "fail";
    public static final String REPAIRMODE_ACTION_FREE_STORAGE = "free storage";
    public static final String REPAIRMODE_ACTION_GEN_NAVI_UUID = "generate navigation activation UUID";
    public static final String REPAIRMODE_ACTION_GETPROP = "getprop";
    public static final String REPAIRMODE_ACTION_GET_CDU_KEY = "get cdu cert";
    public static final String REPAIRMODE_ACTION_GET_INDIV = "get indiv data";
    public static final String REPAIRMODE_ACTION_GET_PSO_KEY = "get pso key";
    public static final String REPAIRMODE_ACTION_GET_TBOX_KEY = "get tbox cert";
    public static final String REPAIRMODE_ACTION_GET_WIFI_KEY = "get wifi cert";
    public static final String REPAIRMODE_ACTION_GPS_COLD_RESET = "GPS cold reset";
    public static final String REPAIRMODE_ACTION_GPS_DIAGNOSIS = "gps diagnosis";
    public static final String REPAIRMODE_ACTION_GPS_HOT_RESET = "GPS hot reset";
    public static final String REPAIRMODE_ACTION_GPS_WARM_RESET = "GPS warm reset";
    public static final String REPAIRMODE_ACTION_IFCONFIG = "ifconfig";
    public static final String REPAIRMODE_ACTION_LCD_DIAGNOSIS = "screen diagnosis";
    public static final String REPAIRMODE_ACTION_LSAL = "ls -al";
    public static final String REPAIRMODE_ACTION_LTE_DIAGNOSIS = "lte diagnosis";
    public static final String REPAIRMODE_ACTION_MIC_DIAGNOSIS = "mic diagnosis";
    public static final String REPAIRMODE_ACTION_MODEM_LOG = "set modem log";
    public static final String REPAIRMODE_ACTION_MOUNT = "mount";
    public static final String REPAIRMODE_ACTION_NAVI_LOG = "set navi log";
    public static final String REPAIRMODE_ACTION_RAMDUMP = "set ramdump";
    public static final String REPAIRMODE_ACTION_REBOOT = "reboot system";
    public static final String REPAIRMODE_ACTION_REPLACE_BLE = "replace BLE";
    public static final String REPAIRMODE_ACTION_REPLACE_CDU = "replace CDU";
    public static final String REPAIRMODE_ACTION_REPLACE_NFC = "replace NFC";
    public static final String REPAIRMODE_ACTION_REPLACE_TBOX = "replace TBOX";
    public static final String REPAIRMODE_ACTION_RESET = "reset system";
    public static final String REPAIRMODE_ACTION_SOUND_DIAGNOSIS = "sound diagnosis";
    public static final String REPAIRMODE_ACTION_SUCCESS = "success";
    public static final String REPAIRMODE_ACTION_TBOX_LOG = "set tbox log";
    public static final String REPAIRMODE_ACTION_TBOX_ROUTING = "set tbox routing";
    public static final String REPAIRMODE_ACTION_TCPDUMP = "set tcpdump";
    public static final String REPAIRMODE_ACTION_TEMP_DIAGNOSIS = "temperature diagnosis";
    public static final String REPAIRMODE_ACTION_TOP = "top";
    public static final String REPAIRMODE_ACTION_TOUCH_DIAGNOSIS = "touch panel diagnosis";
    public static final String REPAIRMODE_ACTION_TRIGGERED = "triggered";
    public static final String REPAIRMODE_ACTION_USB_DIAGNOSIS = "usb diagnosis";
    public static final String REPAIRMODE_ACTION_WIFI_DIAGNOSIS = "wifi diagnosis";
    private static final String TAG = "AfterSalesHelper";
    private static final ServiceConnection mAfterSalesServiceConnection = new ServiceConnection() { // from class: com.xiaopeng.commonfunc.utils.AfterSalesHelper.1
        @Override // android.content.ServiceConnection
        public void onServiceConnected(ComponentName name, IBinder service) {
            LogUtils.i(AfterSalesHelper.TAG, "AfterSales onServiceConnected, name: " + name + ", service: " + service);
        }

        @Override // android.content.ServiceConnection
        public void onServiceDisconnected(ComponentName name) {
            LogUtils.i(AfterSalesHelper.TAG, "AfterSales onServiceDisconnected, name: " + name);
        }
    };
    private static AfterSalesManager sAfterSalesManager;
    private static Context sContext;

    public static AfterSalesManager getAfterSalesManager() {
        return sAfterSalesManager;
    }

    public static void init(Context context) {
        sContext = context;
        sAfterSalesManager = AfterSalesManager.createAfterSalesManager(context, mAfterSalesServiceConnection);
        sAfterSalesManager.connect();
    }

    public static void deinit() {
        AfterSalesManager afterSalesManager = sAfterSalesManager;
        if (afterSalesManager != null) {
            afterSalesManager.disconnect();
        }
    }

    public static void recordRepairModeAction(String action, String result) {
        Context context;
        AfterSalesManager afterSalesManager = sAfterSalesManager;
        if (afterSalesManager != null && afterSalesManager.getRepairMode() && (context = sContext) != null && context.getPackageName() != Constant.PACKAGE_NAME_FACTORYTEST) {
            String temp_action = action.replace("\r\n", "").replace("\n", "").replace("\r", "").replace(Constant.AT_STRING, "").replace("#", "");
            sAfterSalesManager.recordRepairModeAction(temp_action, result);
        }
    }
}
