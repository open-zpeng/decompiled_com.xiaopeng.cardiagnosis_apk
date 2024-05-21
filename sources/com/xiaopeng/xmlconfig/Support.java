package com.xiaopeng.xmlconfig;

import android.os.SystemProperties;
import com.lzy.okgo.cookie.SerializableCookie;
import com.xiaopeng.commonfunc.Constant;
import com.xiaopeng.lib.apirouter.ClientConstants;
/* loaded from: classes5.dex */
public class Support {
    private static final String CLASS_NAME = "Support";

    /* loaded from: classes5.dex */
    public static class Version {
        public static final String TAG = "Version";
        public static final String XML_DOCUMENT = "XML_DOCUMENT";

        public static String getString(String id) {
            return Values.getString(id, "value");
        }
    }

    /* loaded from: classes5.dex */
    public static class Feature {
        public static final String FACTORY_RUN_WHEN_SYSTEM_BOOTUP = "FACTORY_RUN_WHEN_SYSTEM_BOOTUP";
        public static final String MIC_COUNT = "MIC_COUNT";
        public static final String MODEL_NAME = "MODEL_NAME";
        public static final String NEED_NAPA_INIT_FINISH = "NEED_NAPA_INIT_FINISH";
        public static final String PSO_CAR_TYPE = "PSO_CAR_TYPE";
        public static final String PSO_SERVER_CAR_TYPE = "PSO_SERVER_CAR_TYPE";
        public static final String SCP_UPDATE_TBOX = "SCP_UPDATE_TBOX";
        public static final String SEPARATE_UPLOAD_TBOX = "SEPARATE_UPLOAD_TBOX";
        public static final String SUPPORT_AI_DMASP = "SUPPORT_AI_DMASP";
        public static final String SUPPORT_AUTO_CLEAR = "SUPPORT_AUTO_CLEAR";
        public static final String SUPPORT_BLE_KEY = "SUPPORT_BLE_KEY";
        public static final String SUPPORT_CAR_DIAGNOSTIC_APP = "SUPPORT_CAR_DIAGNOSTIC_APP";
        public static final String SUPPORT_COLOR_BLOCK = "SUPPORT_COLOR_BLOCK";
        public static final String SUPPORT_DIAG_MDLOG = "SUPPORT_DIAG_MDLOG";
        public static final String SUPPORT_ETH = "SUPPORT_ETH";
        public static final String SUPPORT_EXT_AMP = "SUPPORT_EXT_AMP";
        public static final String SUPPORT_GPS_IMU_SYSST = "SUPPORT_GPS_IMU_SYSST";
        public static final String SUPPORT_ICM_SCREEN = "SUPPORT_ICM_SCREEN";
        public static final String SUPPORT_ICM_SR_LOG = "SUPPORT_ICM_SR_LOG";
        public static final String SUPPORT_LCD_DTC = "SUPPORT_LCD_DTC";
        public static final String SUPPORT_LCD_SELF_DIAGNOSIS = "SUPPORT_LCD_SELF_DIAGNOSIS";
        public static final String SUPPORT_MULTI_BLE_KEY = "SUPPORT_MULTI_BLE_KEY";
        public static final String SUPPORT_NAVI_ACTIVATE_UUID = "SUPPORT_NAVI_ACTIVATE_UUID";
        public static final String SUPPORT_NFC_KEY = "SUPPORT_NFC_KEY";
        public static final String SUPPORT_PCB_TEMP = "SUPPORT_PCB_TEMP";
        public static final String SUPPORT_PHY_MODE = "SUPPORT_PHY_MODE";
        public static final String SUPPORT_SECOND_SCREEN = "SUPPORT_SECOND_SCREEN";
        public static final String SUPPORT_SPEED_LIMIT = "SUPPORT_SPEED_LIMIT";
        public static final String SUPPORT_TBOX_ETH_CONNECTION = "SUPPORT_TBOX_ETH_CONNECTION";
        public static final String SUPPORT_TMCU = "SUPPORT_TMCU";
        public static final String SUPPORT_TOUCH_KEY = "SUPPORT_TOUCH_KEY";
        public static final String SUPPORT_USB_DVR = "SUPPORT_USB_DVR";
        public static final String SUPPORT_USB_ICM = "SUPPORT_USB_ICM";
        public static final String SUPPORT_XPU = "SUPPORT_XPU";
        public static final String SUPPORT_XPU_REPLACE_ACTIVE = "SUPPORT_XPU_REPLACE_ACTIVE";
        public static final String TAG = "Feature";
        public static final String UPDATE_ICM_BY_OTA = "UPDATE_ICM_BY_OTA";
        public static final String XPU_LVDS = "XPU_LVDS";

        public static boolean getBoolean(String id) {
            return Values.getBoolean(id, "value");
        }

        public static boolean getBoolean(String id, boolean defaultValue) {
            return Values.getBoolean(id, "value", defaultValue);
        }

        public static byte getByte(String id) {
            return Values.getByte(id, "value");
        }

        public static String getString(String id) {
            return Values.getString(id, "value");
        }

        public static int getInt(String id) {
            return Values.getInt(id, "value");
        }

        public static float getFloat(String id) {
            return Values.getFloat(id, "value");
        }

        public static double getDouble(String id) {
            return Values.getDouble(id, "value");
        }
    }

    /* loaded from: classes5.dex */
    public static class Spec {
        public static final String AUDIO_CHANNEL = "AUDIO_CHANNEL";
        public static final String BUILD_SPECIAL_FACTORY = "BUILD_SPECIAL_FACTORY";
        public static final String PROPERTY_PROCESS_CONSOLE = "PROPERTY_PROCESS_CONSOLE";
        public static final String PROPERTY_VALUE_CHN_REGION = "PROPERTY_VALUE_CHN_REGION";
        public static final String PROPERTY_VALUE_ENG = "PROPERTY_VALUE_ENG";
        public static final String PROPERTY_VALUE_FALSE = "PROPERTY_VALUE_FALSE";
        public static final String PROPERTY_VALUE_HCILOG_OFF = "PROPERTY_VALUE_HCILOG_OFF";
        public static final String PROPERTY_VALUE_HCILOG_ON = "PROPERTY_VALUE_HCILOG_ON";
        public static final String PROPERTY_VALUE_MTP_ADB = "PROPERTY_VALUE_MTP_ADB";
        public static final String PROPERTY_VALUE_NONE = "PROPERTY_VALUE_NONE";
        public static final String PROPERTY_VALUE_OFF = "PROPERTY_VALUE_OFF";
        public static final String PROPERTY_VALUE_ON = "PROPERTY_VALUE_ON";
        public static final String PROPERTY_VALUE_RAMDUMP_ON = "PROPERTY_VALUE_RAMDUMP_ON";
        public static final String PROPERTY_VALUE_RUNNING = "PROPERTY_VALUE_RUNNING";
        public static final String PROPERTY_VALUE_STOPPED = "PROPERTY_VALUE_STOPPED";
        public static final String PROPERTY_VALUE_TRUE = "PROPERTY_VALUE_TRUE";
        public static final String PROPERTY_VALUE_USER = "PROPERTY_VALUE_USER";
        public static final String PROPERTY_VALUE_USERDEBUG = "PROPERTY_VALUE_USERDEBUG";
        public static final String SCREEN_HW_VER_CMD = "SCREEN_HW_VER_CMD";
        public static final String SCREEN_SW_VER_CMD = "SCREEN_SW_VER_CMD";
        public static final String SET_VCU_SERVICE = "SET_VCU_SERVICE";
        public static final String TAG = "Spec";
        public static final String TOUCH_TEST_HEIGTH = "TOUCH_TEST_HEIGTH";
        public static final String TOUCH_TEST_WIDTH = "TOUCH_TEST_WIDTH";
        public static final String USB_MODE_HOST = "USB_MODE_HOST";
        public static final String USB_MODE_PERIPHERAL = "USB_MODE_PERIPHERAL";

        public static boolean getBoolean(String id) {
            return Values.getBoolean(id, "value");
        }

        public static byte getByte(String id) {
            return Values.getByte(id, "value");
        }

        public static String getString(String id) {
            return Values.getString(id, "value");
        }

        public static int getInt(String id) {
            return Values.getInt(id, "value");
        }

        public static long getLong(String id) {
            return Values.getLong(id, "value");
        }

        public static float getFloat(String id) {
            return Values.getFloat(id, "value");
        }

        public static double getDouble(String id) {
            return Values.getDouble(id, "value");
        }
    }

    /* loaded from: classes5.dex */
    public static class Properties {
        public static final String AUTHMODE_SENDED = "AUTHMODE_SENDED";
        public static final String BINARY_TYPE = "BINARY_TYPE";
        public static final String BTSNOOP_ENABLE = "BTSNOOP_ENABLE";
        public static final String BTSNOOP_PATH = "BTSNOOP_PATH";
        public static final String BUILD_SPECIAL = "BUILD_SPECIAL";
        public static final String BUILD_TYPE = "BUILD_TYPE";
        public static final String CFCINDEX = "CFCINDEX";
        public static final String CONTROL_RESTART = "CONTROL_RESTART";
        public static final String CONTROL_START = "CONTROL_START";
        public static final String CONTROL_STOP = "CONTROL_STOP";
        public static final String DEBUG_MODE = "DEBUG_MODE";
        public static final String DIAG_MDLOG = "DIAG_MDLOG";
        public static final String FACTORY_MODE = "FACTORY_MODE";
        public static final String GRAB_LOG = "GRAB_LOG";
        public static final String HARDWARE_ID = "HARDWARE_ID";
        public static final String HARDWARE_VERSION = "HARDWARE_VERSION";
        public static final String ICCID = "ICCID";
        public static final String ICM_LOGGING = "ICM_LOGGING";
        public static final String IG_ON_SET_LIMIT = "IG_ON_SET_LIMIT";
        public static final String IG_ON_TIME = "IG_ON_TIME";
        public static final String LOCALE_REGION = "LOCALE_REGION";
        public static final String LOG_LEVEL = "LOG_LEVEL";
        public static final String LTE_APN = "LTE_APN";
        public static final String MCU_VERSION = "MCU_VERSION";
        public static final String MODEM_LOG = "MODEM_LOG";
        public static final String NAPA_INIT = "NAPA_INIT";
        public static final String NAVI_LOG = "NAVI_LOG";
        public static final String NCM_CLUSTER = "NCM_CLUSTER";
        public static final String PARTNUMBER = "PARTNUMBER";
        public static final String PRODUCT_MODEL = "PRODUCT_MODEL";
        public static final String PROPERTIES_DEFAULT_STRING = "Unknown";
        public static final String PROP_CODE_VERSION = "PROP_CODE_VERSION";
        public static final String RAMDUMP = "RAMDUMP";
        public static final String REPAIRMODE_SENDED = "REPAIRMODE_SENDED";
        public static final String REPAIR_MODE = "REPAIR_MODE";
        public static final String SCP_SUPPORTED = "SCP_SUPPORTED";
        public static final String SHOW_DIALOG = "SHOW_DIALOG";
        public static final String SOCEEPROM_CRC = "SOCEEPROM_CRC";
        public static final String SOCEEPROM_MODEL = "SOCEEPROM_MODEL";
        public static final String SOFTWARE_VERSION = "SOFTWARE_VERSION";
        public static final String SVC_CONSOLE = "SVC_CONSOLE";
        public static final String TAG = "Properties";
        public static final String TBOX_VERSION = "TBOX_VERSION";
        public static final String TCPDUMP_ETH = "TCPDUMP_ETH";
        public static final String TCPDUMP_ICM = "TCPDUMP_ICM";
        public static final String TCPDUMP_TBOX = "TCPDUMP_TBOX";
        public static final String TCPDUMP_WLAN = "TCPDUMP_WLAN";
        public static final String TCP_PORT = "TCP_PORT";
        public static final String TMCU_VERSION = "TMCU_VERSION";
        public static final String UDISK_PATH = "UDISK_PATH";
        public static final String UDISK_READONLY = "UDISK_READONLY";
        public static final String UPLOAD_REPAIR_MODE_SENDED = "UPLOAD_REPAIR_MODE_SENDED";
        public static final String USBCONFIG = "USBCONFIG";
        public static final String VID = "VID";
        public static final String VIN = "VIN";
        public static final String XML_MODEL = "XML_MODEL";
        public static final String XPU = "XPU";

        public static String get(String id) {
            String property = Values.getString(id, "key");
            String value = SystemProperties.get(property, PROPERTIES_DEFAULT_STRING);
            XmlUtil.log_d(Support.CLASS_NAME, "Properties.get", "property=" + property + " value=" + value);
            return value;
        }

        public static String get(String id, String defaultValue) {
            String property = Values.getString(id, "key");
            String value = SystemProperties.get(property, defaultValue);
            XmlUtil.log_d(Support.CLASS_NAME, "Properties.get", "property=" + property + " value=" + value);
            return value;
        }

        public static int getInt(String id, int defaultValue) {
            String property = Values.getString(id, "key");
            int value = SystemProperties.getInt(property, defaultValue);
            XmlUtil.log_d(Support.CLASS_NAME, "Properties.getInt", "property=" + property + " value=" + value);
            return value;
        }

        public static boolean getBoolean(String id) {
            String property = Values.getString(id, "key");
            boolean value = SystemProperties.getBoolean(property, false);
            XmlUtil.log_d(Support.CLASS_NAME, "Properties.getBoolean", "property=" + property + " value=" + value);
            return value;
        }

        public static long getLong(String id, long defaultValue) {
            String property = Values.getString(id, "key");
            long value = SystemProperties.getLong(property, defaultValue);
            XmlUtil.log_d(Support.CLASS_NAME, "Properties.getLong", "property=" + property + " value=" + value);
            return value;
        }

        public static String getProp(String key) {
            String value = SystemProperties.get(key, PROPERTIES_DEFAULT_STRING);
            XmlUtil.log_d(Support.CLASS_NAME, "Properties.get", "property=" + key + " value=" + value);
            return value;
        }

        public static void set(String id, String value) {
            String property = Values.getString(id, "key");
            XmlUtil.log_i(Support.CLASS_NAME, "Properties.set", "property=" + property + ", value=" + value);
            SystemProperties.set(property, value);
        }
    }

    /* loaded from: classes5.dex */
    public static class Case {
        public static final String ADCAN = "ADCAN";
        public static final String ANDROID_LOG = "ANDROID_LOG";
        public static final String AVM_LOG = "AVM_LOG";
        public static final String BCAN = "BCAN";
        public static final String BLE_POSITION_NAMES = "BLE_POSITION_NAMES";
        public static final String CANDATA_COLLECTOR = "CANDATA_COLLECTOR";
        public static final String CCAN = "CCAN";
        public static final String CDU_AES_KEY = "CDU_AES_KEY";
        public static final String CDU_CAR_FEATURE_CODE = "CDU_CAR_FEATURE_CODE";
        public static final String CDU_CAR_FEATURE_CODE_AFTERSALES = "CDU_CAR_FEATURE_CODE_AFTERSALES";
        public static final String CDU_FAULT_CODE = "CDU_FAULT_CODE";
        public static final String CERT = "CERT";
        public static final String CFC_CODE = "CFC_CODE";
        public static final String CURRENT_RUNNING_APP_ZONE = "CURRENT_RUNNING_APP_ZONE";
        public static final String DCAN = "DCAN";
        public static final String DCM_USB = "DCM_USB";
        public static final String DIAGNOSIS_RECORD = "DIAGNOSIS_RECORD";
        public static final String DID_INFO = "DID_INFO";
        public static final String DOLBY_SECRET_KEY = "DOLBY_SECRET_KEY";
        public static final String DVR_USB = "DVR_USB";
        public static final String ECAN = "ECAN";
        public static final String ESK_CODE_WRITTEN_STATUS = "ESK_CODE_WRITTEN_STATUS";
        public static final String FACE_USB = "FACE_USB";
        public static final String FACTORY_BATTERY = "FACTORY_BATTERY";
        public static final String FACTORY_BLUETOOTH = "FACTORY_BLUETOOTH";
        public static final String FACTORY_BRIGHTNESS = "FACTORY_BRIGHTNESS";
        public static final String FACTORY_CAMERA = "FACTORY_CAMERA";
        public static final String FACTORY_DAB = "FACTORY_DAB";
        public static final String FACTORY_ETH = "FACTORY_ETH";
        public static final String FACTORY_FEEDBACK = "FACTORY_FEEDBACK";
        public static final String FACTORY_GPS = "FACTORY_GPS";
        public static final String FACTORY_KEY = "FACTORY_KEY";
        public static final String FACTORY_LTE = "FACTORY_LTE";
        public static final String FACTORY_RADIO = "FACTORY_RADIO";
        public static final String FACTORY_RECORD = "FACTORY_RECORD";
        public static final String FACTORY_RGB = "FACTORY_RGB";
        public static final String FACTORY_SOUND = "FACTORY_SOUND";
        public static final String FACTORY_TEMPERATURE = "FACTORY_TEMPERATURE";
        public static final String FACTORY_TOUCH = "FACTORY_TOUCH";
        public static final String FACTORY_USB = "FACTORY_USB";
        public static final String FACTORY_USB_BLUETOOTH = "FACTORY_USB_BLUETOOTH";
        public static final String FACTORY_WLAN = "FACTORY_WLAN";
        public static final String HARDWARE_TEST = "HARDWARE_TEST";
        public static final String HOST_LCMS = "HOST_LCMS";
        public static final String HOST_RCMS = "HOST_RCMS";
        public static final String HW_INFO = "HW_INFO";
        public static final String HW_SW_INFO = "HW_SW_INFO";
        public static final String ICAN = "ICAN";
        public static final String ICM_HW_VER_INFO = "ICM_HW_VER_INFO";
        public static final String ICM_MCU_VER_INFO = "ICM_MCU_VER_INFO";
        public static final String ICM_SCREEN_DIAGNOSIS = "ICM_SCREEN_DIAGNOSIS";
        public static final String ICM_SUPPORT_SSH = "ICM_SUPPORT_SSH";
        public static final String IMEI = "IMEI";
        public static final String INDIV = "INDIV";
        public static final String IVI_HW_VER_INFO = "IVI_HW_VER_INFO";
        public static final String IVI_MCU_VER_INFO = "IVI_MCU_VER_INFO";
        public static final String IVI_SCREEN_DIAGNOSIS = "IVI_SCREEN_DIAGNOSIS";
        public static final String LCMS_LOG = "LCMS_LOG";
        public static final String LOG_UPLOAD = "LOG_UPLOAD";
        public static final String MCU_SAVE_CONFIG = "MCU_SAVE_CONFIG";
        public static final String MEDIA_USB = "MEDIA_USB";
        public static final String MODEM_LOG = "MODEM_LOG";
        public static final String MODULE_DIAGNOSIS = "MODULE_DIAGNOSIS";
        public static final String NAVI = "NAVI";
        public static final String NAVIGATION_LOG = "NAVIGATION_LOG";
        public static final String NEED_PARSE_CANDATA = "NEED_PARSE_CANDATA";
        public static final String ONE_CLICK_DIAGNOSIS = "ONE_CLICK_DIAGNOSIS";
        public static final String PASSENGER_HW_VER_INFO = "PASSENGER_HW_VER_INFO";
        public static final String PASSENGER_MCU_VER_INFO = "PASSENGER_MCU_VER_INFO";
        public static final String PASSENGER_SCREEN_DIAGNOSIS = "PASSENGER_SCREEN_DIAGNOSIS";
        public static final String RCMS_LOG = "RCMS_LOG";
        public static final String REALTIME_STATUS = "REALTIME_STATUS";
        public static final String REWORK = "REWORK";
        public static final String SCREEN_CALIBRATION = "SCREEN_CALIBRATION";
        public static final String SECURE_BOOT = "SECURE_BOOT";
        public static final String SREEN_SELTEST = "SREEN_SELTEST";
        public static final String SUPPORT_MCU_CAN = "SUPPORT_MCU_CAN";
        public static final String TAG = "Case";
        public static final String TBOX_AES_KEY = "TBOX_AES_KEY";
        public static final String TBOX_CAN_DATA_SERVER_IP = "TBOX_CAN_DATA_SERVER_IP";
        public static final String TBOX_CAN_DATA_SERVER_PORT = "TBOX_CAN_DATA_SERVER_PORT";
        public static final String TBOX_LOG = "TBOX_LOG";
        public static final String TBOX_SUPPORT_SSH = "TBOX_SUPPORT_SSH";
        public static final String TEST_AVAS = "TEST_AVAS";
        public static final String TEST_BATTERY = "TEST_BATTERY";
        public static final String TEST_BLUETOOTH = "TEST_BLUETOOTH";
        public static final String TEST_BRIGHTNESS = "TEST_BRIGHTNESS";
        public static final String TEST_CAMERA = "TEST_CAMERA";
        public static final String TEST_CAN = "TEST_CAN";
        public static final String TEST_EEPROM = "TEST_EEPROM";
        public static final String TEST_FAN = "TEST_FAN";
        public static final String TEST_FEEDBACK = "TEST_FEEDBACK";
        public static final String TEST_GPS = "TEST_GPS";
        public static final String TEST_KEY = "TEST_KEY";
        public static final String TEST_LTE = "TEST_LTE";
        public static final String TEST_PHY = "TEST_PHY";
        public static final String TEST_PSU = "TEST_PSU";
        public static final String TEST_QUIET = "TEST_QUIET";
        public static final String TEST_RADIO = "TEST_RADIO";
        public static final String TEST_RECORD = "TEST_RECORD";
        public static final String TEST_RGB = "TEST_RGB";
        public static final String TEST_SD = "TEST_SD";
        public static final String TEST_SENSOR = "TEST_SENSOR";
        public static final String TEST_SOUND = "TEST_SOUND";
        public static final String TEST_SPDIF = "TEST_SPDIF";
        public static final String TEST_TEMPERATURE = "TEST_TEMPERATURE";
        public static final String TEST_TOUCH = "TEST_TOUCH";
        public static final String TEST_USB = "TEST_USB";
        public static final String TEST_WLAN = "TEST_WLAN";
        public static final String UPDATE_ANDROID_SOC = "UPDATE_ANDROID_SOC";
        public static final String UPDATE_ICM = "UPDATE_ICM";
        public static final String UPDATE_ICM_MCU = "UPDATE_ICM_MCU";
        public static final String UPDATE_IVI_SCREEN = "UPDATE_IVI_SCREEN";
        public static final String UPDATE_MCU = "UPDATE_MCU";
        public static final String UPDATE_NEW_DEVICES_ID = "UPDATE_NEW_DEVICES_ID";
        public static final String UPDATE_PASSENGER_SCREEN = "UPDATE_PASSENGER_SCREEN";
        public static final String UPDATE_TBOX = "UPDATE_TBOX";
        public static final String UPDATE_USB_BLUETOOTH = "UPDATE_USB_BLUETOOTH";
        public static final String UPDATE_XPU = "UPDATE_XPU";
        public static final String USB_BLUETOOTH_VER_INFO = "USB_BLUETOOTH_VER_INFO";
        public static final String VERSION_INFO = "VERSION_INFO";

        public static boolean getEnabled(String id) {
            return Values.getBoolean(id, "enable");
        }

        public static String getString(String id) {
            return Values.getString(id, "value");
        }

        public static int getInt(String id) {
            return Values.getInt(id, "value");
        }

        public static double getDouble(String id) {
            return Values.getDouble(id, "value");
        }

        public static int getAddress(String id) {
            try {
                int address = Integer.parseInt(Values.getString(id, Constant.KEY_ADDRESS).substring(2), 16);
                return address;
            } catch (NumberFormatException e) {
                e.printStackTrace();
                return 0;
            }
        }

        public static int getDid(String id) {
            try {
                int did = Integer.parseInt(Values.getString(id, "did").substring(2), 16);
                return did;
            } catch (NumberFormatException e) {
                e.printStackTrace();
                return 0;
            }
        }

        public static int getLength(String id) {
            return Values.getInt(id, "length");
        }

        public static String getExtra(String id) {
            return Values.getString(id, "extra");
        }

        public static String getPath(String id) {
            return Values.getString(id, ClientConstants.ALIAS.PATH);
        }

        public static String getProp(String id) {
            return Values.getString(id, "prop");
        }
    }

    /* loaded from: classes5.dex */
    public static class Path {
        public static final String AMP_THERM = "AMP_THERM";
        public static final String CAMERA_CHANNEL = "CAMERA_CHANNEL";
        public static final String CPU_INTERNAL_THERM = "CPU_INTERNAL_THERM";
        public static final String DDR_THERM = "DDR_THERM";
        public static final String ETC_PWM = "ETC_PWM";
        public static final String FAN_STATUS = "FAN_STATUS";
        public static final String FM_RADIO_RSSI = "FM_RADIO_RSSI";
        public static final String FPS_SELECTION_TOP_CAM = "FPS_SELECTION_TOP_CAM";
        public static final String GPU_THERM = "GPU_THERM";
        public static final String ICM_MCU_UPDATE = "ICM_MCU_UPDATE";
        public static final String ICM_MCU_VERSION = "ICM_MCU_VERSION";
        public static final String ICM_SCREEN_DATA = "ICM_SCREEN_DATA";
        public static final String INAP562T_REMOTE_ATTR = "INAP562T_REMOTE_ATTR";
        public static final String IVI_MCU_UPDATE = "IVI_MCU_UPDATE";
        public static final String IVI_SCREEN_CALIBRATION = "IVI_SCREEN_CALIBRATION";
        public static final String IVI_SCREEN_DATA = "IVI_SCREEN_DATA";
        public static final String IVI_SCREEN_VERSION = "IVI_SCREEN_VERSION";
        public static final String LCD_CLK = "LCD_CLK";
        public static final String LED_CTRL = "LED_CTRL";
        public static final String LOGICTREE_UPGRADE_FOLDER = "LOGICTREE_UPGRADE_FOLDER";
        public static final String MEM_INFO = "MEM_INFO";
        public static final String NAVI_MAP_CONFIG = "NAVI_MAP_CONFIG";
        public static final String NAVI_MAP_VERSION = "NAVI_MAP_VERSION";
        public static final String NAVI_UUID = "NAVI_UUID";
        public static final String OLD_NAVI_MAP_VERSION = "OLD_NAVI_MAP_VERSION";
        public static final String PASSENGER_MCU_UPDATE = "PASSENGER_MCU_UPDATE";
        public static final String PASSENGER_MCU_VERSION = "PASSENGER_MCU_VERSION";
        public static final String PASSENGER_SCREEN_DATA = "PASSENGER_SCREEN_DATA";
        public static final String PATH_AUDIO_EXTERNAL_PA = "PATH_AUDIO_EXTERNAL_PA";
        public static final String PATH_CHANGE_LCD_DIS_MODE = "PATH_CHANGE_LCD_DIS_MODE";
        public static final String PATH_DIAGNOSIS_DB = "PATH_DIAGNOSIS_DB";
        public static final String PATH_DSP_VOLUME = "PATH_DSP_VOLUME";
        public static final String PATH_PCBA_SERIALNO = "PATH_PCBA_SERIALNO";
        public static final String PATH_SCREEN_DIAGNOSIS = "PATH_SCREEN_DIAGNOSIS";
        public static final String PHY_LINK = "PHY_LINK";
        public static final String PHY_MODE = "PHY_MODE";
        public static final String PHY_SQI = "PHY_SQI";
        public static final String PMIC_2_THERM = "PMIC_2_THERM";
        public static final String PMIC_THERM = "PMIC_THERM";
        public static final String PSO_KEY = "PSO_KEY";
        public static final String ROM_DISK_MODEL = "ROM_DISK_MODEL";
        public static final String ROM_DISK_SIZE = "ROM_DISK_SIZE";
        public static final String SCREEN_DATA = "SCREEN_DATA";
        public static final String SOC_THERM = "SOC_THERM";
        public static final String SWITCH_DEBUG_FILE = "SWITCH_DEBUG_FILE";
        public static final String TAG = "Path";
        public static final String UFS_THERM = "UFS_THERM";
        public static final String USB_DEVICES = "USB_DEVICES";
        public static final String WIFI_CLIENT_CERT = "WIFI_CLIENT_CERT";
        public static final String WIFI_ROOT_BKS_CERT = "WIFI_ROOT_BKS_CERT";
        public static final String WIFI_ROOT_CERT = "WIFI_ROOT_CERT";

        public static String getFilePath(String id) {
            return Values.getString(id, "value");
        }

        public static String getKey(String id) {
            return Values.getString(id, "key");
        }
    }

    /* loaded from: classes5.dex */
    public static class Url {
        public static final String ACTIVATE_XPU = "ACTIVATE_XPU";
        public static final String CAR_CODE = "CAR_CODE";
        public static final String CDU_CERT_CHECK = "CDU_CERT_CHECK";
        public static final String CELLULAR_DATA_INFO = "CELLULAR_DATA_INFO";
        public static final String CHECK_CDU_KEY = "CHECK_CDU_KEY";
        public static final String CHECK_TBOX_KEY = "CHECK_TBOX_KEY";
        public static final String CHECK_V18_CDU_KEY = "CHECK_V18_CDU_KEY";
        public static final String CHECK_WIFI_KEY = "CHECK_WIFI_KEY";
        public static final String CHECK_XPU_ACTIVATE_STATUS = "CHECK_XPU_ACTIVATE_STATUS";
        public static final String CHECK_XPU_FACTORY_ACTIVATE_STATUS = "CHECK_XPU_FACTORY_ACTIVATE_STATUS";
        public static final String GEN_UUID = "GEN_UUID";
        public static final String INDIV_SERVICE = "INDIV_SERVICE";
        public static final String INDIV_TEST_DECRYPTION = "INDIV_TEST_DECRYPTION";
        public static final String INDIV_TEST_ENCRYPTION = "INDIV_TEST_ENCRYPTION";
        public static final String REPLACE_XPU = "REPLACE_XPU";
        public static final String REQUEST_CDU_KEY = "REQUEST_CDU_KEY";
        public static final String REQUEST_PSU_KEY = "REQUEST_PSU_KEY";
        public static final String REQUEST_REPAIR_PSU = "REQUEST_REPAIR_PSU";
        public static final String REQUEST_TARGET_CHECK_MODE = "REQUEST_TARGET_CHECK_MODE";
        public static final String REQUEST_TBOX_KEY = "REQUEST_TBOX_KEY";
        public static final String REQUEST_TBOX_KEY_V3 = "REQUEST_TBOX_KEY_V3";
        public static final String REQUEST_TO_ENTER_AUTH_MODE = "REQUEST_TO_ENTER_AUTH_MODE";
        public static final String REQUEST_WIFI_KEY = "REQUEST_WIFI_KEY";
        public static final String TAG = "Url";
        public static final String UPDATE_BLEKEY_MACADDR = "UPDATE_BLEKEY_MACADDR";
        public static final String UPDATE_CDUID = "UPDATE_CDUID";
        public static final String UPDATE_ICCID = "UPDATE_ICCID";
        public static final String UPDATE_NFC_SEID = "UPDATE_NFC_SEID";
        public static final String UPLOAD_AUTH_MODE = "UPLOAD_AUTH_MODE";
        public static final String UPLOAD_BLEKEYS_MACADDR = "UPLOAD_BLEKEYS_MACADDR";
        public static final String UPLOAD_ERROR_CODE = "UPLOAD_ERROR_CODE";
        public static final String UPLOAD_HW_INFO = "UPLOAD_HW_INFO";
        public static final String UPLOAD_REPAIR_MODE = "UPLOAD_REPAIR_MODE";
        public static final String UPLOAD_REPAIR_MODE_STATUS = "UPLOAD_REPAIR_MODE_STATUS";

        public static String getUrl(String id) {
            return Values.getString(id, "value");
        }

        public static String getHost(String id) {
            return Values.getString(id, SerializableCookie.HOST);
        }
    }

    /* loaded from: classes5.dex */
    private static class Values {
        private Values() {
        }

        /* JADX INFO: Access modifiers changed from: private */
        public static boolean getBoolean(String id, String resultField) {
            return getBoolean(id, resultField, false);
        }

        private static boolean getBoolean(String id, String resultField, int logLevel) {
            return getBoolean(id, resultField, false, logLevel);
        }

        /* JADX INFO: Access modifiers changed from: private */
        public static boolean getBoolean(String id, String resultField, boolean defaultValue) {
            boolean value = defaultValue;
            try {
                value = Boolean.parseBoolean(XMLDataStorage.instance().getAttributeValueById(id, resultField));
                XmlUtil.log_d(Support.CLASS_NAME, "Values.getBoolean", "id=" + id + ", value=" + value);
                return value;
            } catch (Exception e) {
                XmlUtil.log_e(e);
                return value;
            }
        }

        private static boolean getBoolean(String id, String resultField, boolean defaultValue, int logLevel) {
            boolean value = defaultValue;
            try {
                value = Boolean.parseBoolean(XMLDataStorage.instance().getAttributeValueById(id, resultField));
                XmlUtil.log_d(Support.CLASS_NAME, "Values.getBoolean", "id=" + id + ", value=" + value);
                return value;
            } catch (Exception e) {
                XmlUtil.log_e(e);
                return value;
            }
        }

        /* JADX INFO: Access modifiers changed from: private */
        public static byte getByte(String id, String resultField) {
            byte value = 0;
            try {
                value = Byte.parseByte(XMLDataStorage.instance().getAttributeValueById(id, resultField));
                XmlUtil.log_d(Support.CLASS_NAME, "Values.getByte", "id=" + id + ", value=" + ((int) value));
                return value;
            } catch (Exception e) {
                XmlUtil.log_e(e);
                return value;
            }
        }

        private static String getString(String id, String resultField, boolean isLog) {
            String value = Properties.PROPERTIES_DEFAULT_STRING;
            try {
                value = XMLDataStorage.instance().getAttributeValueById(id, resultField);
                if (isLog) {
                    XmlUtil.log_d(Support.CLASS_NAME, "Values.getString", "id=" + id + ", " + resultField + Constant.EQUALS_STRING + value);
                }
            } catch (Exception e) {
                XmlUtil.log_e(e);
            }
            if (value == null) {
                return Properties.PROPERTIES_DEFAULT_STRING;
            }
            return value;
        }

        /* JADX INFO: Access modifiers changed from: private */
        public static String getString(String id, String resultField) {
            return getString(id, resultField, true);
        }

        private static String getString(String id, String resultField, int logLevel) {
            String value = Properties.PROPERTIES_DEFAULT_STRING;
            try {
                value = XMLDataStorage.instance().getAttributeValueById(id, resultField);
                XmlUtil.log_d(Support.CLASS_NAME, "Values.getString", "id=" + id + ", value=" + value);
            } catch (Exception e) {
                XmlUtil.log_e(e);
            }
            if (value == null) {
                return Properties.PROPERTIES_DEFAULT_STRING;
            }
            return value;
        }

        /* JADX INFO: Access modifiers changed from: private */
        public static int getInt(String id, String resultField) {
            int value = 0;
            try {
                value = Integer.parseInt(XMLDataStorage.instance().getAttributeValueById(id, resultField));
                XmlUtil.log_d(Support.CLASS_NAME, "Values.getInt", "id=" + id + ", value=" + value);
                return value;
            } catch (Exception e) {
                XmlUtil.log_e(e);
                return value;
            }
        }

        /* JADX INFO: Access modifiers changed from: private */
        public static long getLong(String id, String resultField) {
            long value = 0;
            try {
                value = Long.parseLong(XMLDataStorage.instance().getAttributeValueById(id, resultField));
                XmlUtil.log_d(Support.CLASS_NAME, "Values.getFloat", "id=" + id + ", value=" + value);
                return value;
            } catch (Exception e) {
                XmlUtil.log_e(e);
                return value;
            }
        }

        /* JADX INFO: Access modifiers changed from: private */
        public static float getFloat(String id, String resultField) {
            float value = 0.0f;
            try {
                value = Float.parseFloat(XMLDataStorage.instance().getAttributeValueById(id, resultField));
                XmlUtil.log_d(Support.CLASS_NAME, "Values.getFloat", "id=" + id + ", value=" + value);
                return value;
            } catch (Exception e) {
                XmlUtil.log_e(e);
                return value;
            }
        }

        /* JADX INFO: Access modifiers changed from: private */
        public static double getDouble(String id, String resultField) {
            double value = 0.0d;
            try {
                value = Double.parseDouble(XMLDataStorage.instance().getAttributeValueById(id, resultField));
                XmlUtil.log_d(Support.CLASS_NAME, "Values.getDouble", "id=" + id + ", value=" + value);
                return value;
            } catch (Exception e) {
                XmlUtil.log_e(e);
                return value;
            }
        }
    }
}
