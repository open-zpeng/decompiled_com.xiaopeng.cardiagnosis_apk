package com.xiaopeng.commonfunc.utils;

import android.content.Context;
import android.net.InterfaceConfiguration;
import android.net.LinkAddress;
import android.os.IBinder;
import android.os.INetworkManagementService;
import android.os.ServiceManager;
import android.text.TextUtils;
import com.xiaopeng.commonfunc.Constant;
import com.xiaopeng.lib.framework.moduleinterface.netchannelmodule.http.IResponse;
import com.xiaopeng.lib.http.server.ServerBean;
import com.xiaopeng.lib.utils.FileUtils;
import com.xiaopeng.lib.utils.LogUtils;
import java.net.InetAddress;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.CharBuffer;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.text.DecimalFormat;
import java.util.BitSet;
import java.util.List;
import java.util.Random;
import java.util.UUID;
import javax.crypto.Cipher;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;
import net.lingala.zip4j.crypto.PBKDF2.BinTools;
import org.json.JSONException;
import org.json.JSONObject;
/* loaded from: classes4.dex */
public class DataHelp {
    public static final Charset ASCII = StandardCharsets.US_ASCII;
    public static final int[] DLC = {0, 1, 2, 3, 4, 5, 6, 7, 8, 12, 16, 20, 24, 32, 48, 64};
    private static final String SEC_IV = "5s9F1pGR@VJZ8F3I";
    public static final String SEC_SALT = "oBPMlkqO4eBRBa@N*zIGdRokeikjKbIg";

    public static int getDLC(int index) {
        int[] iArr = DLC;
        if (index >= iArr.length) {
            return -1;
        }
        int res = iArr[index];
        return res;
    }

    public static byte intToByte(int x) {
        return (byte) x;
    }

    public static byte[] intToByte1(int x) {
        return new byte[]{(byte) x};
    }

    public static byte[] intToBytesBig(int value) {
        byte[] src = {(byte) ((value >> 24) & 255), (byte) ((value >> 16) & 255), (byte) ((value >> 8) & 255), (byte) (value & 255)};
        return src;
    }

    public static int byteToInt(byte b) {
        return b & 255;
    }

    public static byte[] intToBytes2(int value) {
        byte[] src = {(byte) ((value >> 8) & 255), (byte) (value & 255)};
        return src;
    }

    public static int bytes2ToInt(byte[] src) {
        int value = ((src[0] & 255) << 8) | (src[1] & 255);
        return value;
    }

    public static int byte2ToInt(byte[] bytes) {
        short s = ByteBuffer.wrap(bytes).order(ByteOrder.LITTLE_ENDIAN).getShort();
        return s;
    }

    public static int bigbByte2ToInt(byte[] bytes) {
        short s = ByteBuffer.wrap(bytes).order(ByteOrder.BIG_ENDIAN).getShort();
        return s;
    }

    public static int bigBytes4Toint(byte[] bytes) {
        ByteBuffer buffer = ByteBuffer.wrap(bytes);
        buffer.order(ByteOrder.BIG_ENDIAN);
        return buffer.getInt();
    }

    public static int unsignedBytes2ToInt(byte[] src) {
        int value = (src[0] & 255) | ((src[1] & 255) << 8);
        return value;
    }

    public static byte[] strToByteArray(String str) {
        if (str == null) {
            return null;
        }
        byte[] res = str.getBytes();
        return res;
    }

    public static byte[] double2Bytes(double d) {
        long value = Double.doubleToRawLongBits(d);
        byte[] byteRet = new byte[8];
        for (int i = 0; i < 8; i++) {
            byteRet[i] = (byte) ((value >> (i * 8)) & 255);
        }
        return byteRet;
    }

    public static double bytes2Double(byte[] arr) {
        long value = 0;
        for (int i = 0; i < 8; i++) {
            value |= (arr[i] & 255) << (i * 8);
        }
        return Double.longBitsToDouble(value);
    }

    public static byte[] longToBytes(long x) {
        ByteBuffer buffer = ByteBuffer.allocate(8);
        buffer.putLong(x);
        return buffer.array();
    }

    public static long bytesToLong(byte[] bytes) {
        ByteBuffer buffer = ByteBuffer.allocate(8);
        buffer.put(bytes);
        buffer.flip();
        return buffer.getLong();
    }

    public static int hex2decimal(String hex) {
        try {
            int value = Integer.parseInt(hex, 16);
            return value;
        } catch (Exception e) {
            e.printStackTrace();
            return -1;
        }
    }

    public static String byteArrayToStr(byte[] bytes) {
        if (bytes == null) {
            return "";
        }
        String res = new String(bytes);
        return res;
    }

    public static byte[] byteMerger(byte[]... values) {
        int length_byte = 0;
        for (byte[] bArr : values) {
            length_byte += bArr.length;
        }
        byte[] all_byte = new byte[length_byte];
        int countLength = 0;
        for (byte[] b : values) {
            System.arraycopy(b, 0, all_byte, countLength, b.length);
            countLength += b.length;
        }
        return all_byte;
    }

    public static byte[] byteMerge(byte[] dest, int startPos, byte[] src, int length) {
        System.arraycopy(src, 0, dest, startPos, length);
        return dest;
    }

    public static byte[] byteSub(byte[] src, int start, int length) {
        byte[] res = new byte[length];
        System.arraycopy(src, start, res, 0, length);
        return res;
    }

    public static String byteArrayToHexStr(byte[] buf) {
        if (buf == null) {
            return "";
        }
        StringBuffer result = new StringBuffer(buf.length * 3);
        for (int i = 0; i < buf.length; i++) {
            result.append(BinTools.hex.charAt((buf[i] >> 4) & 15));
            result.append(BinTools.hex.charAt(buf[i] & 15));
            result.append(Constant.SPACE_STRING);
        }
        int i2 = result.length();
        result.deleteCharAt(i2 - 1);
        return result.toString();
    }

    public static String byteArrayToHexStr(byte[] buf, String separator) {
        if (buf == null) {
            return "";
        }
        StringBuffer result = new StringBuffer(buf.length * 2);
        for (int i = 0; i < buf.length; i++) {
            result.append(BinTools.hex.charAt((buf[i] >> 4) & 15));
            result.append(BinTools.hex.charAt(buf[i] & 15));
            if (i < buf.length - 1) {
                result.append(separator);
            }
        }
        return result.toString();
    }

    public static String byteArrayToHexStr(byte[] buf, boolean withspace) {
        if (buf == null) {
            return "";
        }
        StringBuffer result = new StringBuffer(buf.length * 2);
        for (int i = 0; i < buf.length; i++) {
            result.append(BinTools.hex.charAt((buf[i] >> 4) & 15));
            result.append(BinTools.hex.charAt(buf[i] & 15));
            if (withspace) {
                result.append(Constant.SPACE_STRING);
            }
        }
        return result.toString();
    }

    public static boolean containString(String[] strings, String string) {
        if (TextUtils.isEmpty(string)) {
            return false;
        }
        for (String str : strings) {
            if (string.equalsIgnoreCase(str)) {
                return true;
            }
        }
        return false;
    }

    public static byte[] hexStringToByteArray(String s) {
        int len = s.length();
        byte[] data = new byte[len / 2];
        for (int i = 0; i < len; i += 2) {
            data[i / 2] = (byte) ((Character.digit(s.charAt(i), 16) << 4) + Character.digit(s.charAt(i + 1), 16));
        }
        return data;
    }

    public static byte[] convert64Byte(byte[] buf) {
        byte[] bytes64 = new byte[64];
        int index = 0;
        while (index < buf.length && index < 64) {
            bytes64[index] = buf[index];
            index++;
        }
        while (index < 64) {
            bytes64[index] = 0;
            index++;
        }
        return bytes64;
    }

    public static boolean checkBytes(byte[] argu, byte[] argv) {
        for (int i = 0; i < argv.length; i++) {
            if (argu[i] != argv[i]) {
                return false;
            }
        }
        return true;
    }

    public static String listToString(List<String> list, String delimiter) {
        if (list.size() <= 0) {
            return "";
        }
        String res = String.join(delimiter, list);
        return res;
    }

    public static String getKey(String str1, String str2) {
        return com.xiaopeng.lib.utils.MD5Utils.getMD5(str1 + str2);
    }

    public static byte[] decrypt(byte[] data, byte[] key) {
        try {
            SecretKeySpec skeySpec = new SecretKeySpec(key, "AES");
            Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
            cipher.init(2, skeySpec, new IvParameterSpec(SEC_IV.getBytes()));
            return cipher.doFinal(data);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public static ServerBean getServerBean(IResponse response) {
        String body = response.body();
        LogUtils.i("CommonUtils", "response data. response: " + body);
        try {
            JSONObject jsonObject = new JSONObject(body);
            ServerBean bean = new ServerBean();
            bean.setCode(jsonObject.getInt("code"));
            try {
                bean.setData(jsonObject.getString("data"));
            } catch (Exception e) {
                e.printStackTrace();
            }
            try {
                bean.setMsg(jsonObject.getString("msg"));
                return bean;
            } catch (Exception var4) {
                var4.printStackTrace();
                return bean;
            }
        } catch (Exception var5) {
            var5.printStackTrace();
            return null;
        }
    }

    public static boolean getHttpResult(IResponse iResponse) {
        try {
            boolean result = new JSONObject(iResponse.body()).getInt("code") == 200;
            return result;
        } catch (JSONException e) {
            e.printStackTrace();
            return false;
        }
    }

    public static String getRandomString(int length) {
        int strlength = "defkSTUB345opqCDElxyzA26cQRF8IYZ0mJabKLMuPvwNOGHVWXnrst179".length();
        Random random = new Random();
        StringBuffer sb = new StringBuffer();
        for (int i = 0; i < length; i++) {
            int number = random.nextInt(strlength);
            sb.append("defkSTUB345opqCDElxyzA26cQRF8IYZ0mJabKLMuPvwNOGHVWXnrst179".charAt(number));
        }
        return sb.toString();
    }

    public static String getUUID() {
        String uuid = UUID.randomUUID().toString();
        char[] cs = new char[32];
        int j = uuid.length() / 2;
        int j2 = 1;
        while (true) {
            int i = j - 1;
            if (j > 0) {
                char c = uuid.charAt(i);
                if (c == '-') {
                    j = i;
                } else {
                    cs[j2] = c;
                    j2++;
                    j = i;
                }
            } else {
                String uid = String.valueOf(cs);
                return uid;
            }
        }
    }

    public static String formatFileSize(long fileSize) {
        DecimalFormat df = new DecimalFormat("#.00");
        if (fileSize == 0) {
            return "0B";
        }
        if (fileSize < FileUtils.SIZE_1KB) {
            String fileSizeString = df.format(fileSize) + "B";
            return fileSizeString;
        } else if (fileSize < FileUtils.SIZE_1MB) {
            String fileSizeString2 = df.format(fileSize / 1024.0d) + "KB";
            return fileSizeString2;
        } else if (fileSize < FileUtils.SIZE_1GB) {
            String fileSizeString3 = df.format(fileSize / 1048576.0d) + Constant.MEGABYTE;
            return fileSizeString3;
        } else {
            String fileSizeString4 = df.format(fileSize / 1.073741824E9d) + "GB";
            return fileSizeString4;
        }
    }

    public static String getString(Context context, int resid) {
        return context.getString(resid);
    }

    public static String getString(Context context, int resId, Object... formatArgs) {
        return context.getString(resId, formatArgs);
    }

    public static boolean isStrArraysContainStr(String[] strings, String string) {
        if (TextUtils.isEmpty(string)) {
            return false;
        }
        for (String str : strings) {
            if (string.equals(str)) {
                return true;
            }
        }
        return false;
    }

    /* JADX WARN: Code restructure failed: missing block: B:10:0x0066, code lost:
        if (r4 == null) goto L15;
     */
    /* JADX WARN: Code restructure failed: missing block: B:11:0x0068, code lost:
        com.xiaopeng.lib.utils.LogUtils.d("getTetherIpAddress", "address " + r4);
     */
    /* JADX WARN: Code restructure failed: missing block: B:7:0x0020, code lost:
        r6 = r2.getInterfaceConfig(r8);
     */
    /* JADX WARN: Code restructure failed: missing block: B:8:0x0025, code lost:
        if (r6 == null) goto L15;
     */
    /* JADX WARN: Code restructure failed: missing block: B:9:0x0027, code lost:
        r6 = r6.getLinkAddress();
        com.xiaopeng.lib.utils.LogUtils.d("getTetherIpAddress", "linkAddr" + r6.toString());
        r7 = r6.getAddress();
        com.xiaopeng.lib.utils.LogUtils.d("getTetherIpAddress", "inetaddr" + r7.toString());
        r4 = r7.getHostAddress();
     */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct add '--show-bad-code' argument
    */
    public static java.lang.String getTetherIpAddress() {
        /*
            java.lang.String r0 = "getTetherIpAddress"
            java.lang.String r1 = "network_management"
            android.os.IBinder r1 = android.os.ServiceManager.getService(r1)
            android.os.INetworkManagementService r2 = android.os.INetworkManagementService.Stub.asInterface(r1)
            r3 = 0
            r4 = 0
            java.lang.String[] r5 = r2.listTetheredInterfaces()     // Catch: java.lang.Exception -> L82
            int r6 = r5.length     // Catch: java.lang.Exception -> L82
            r7 = 0
        L14:
            if (r7 >= r6) goto L80
            r8 = r5[r7]     // Catch: java.lang.Exception -> L82
            java.lang.String r9 = "wlan"
            boolean r9 = r8.contains(r9)     // Catch: java.lang.Exception -> L82
            if (r9 == 0) goto L7d
            android.net.InterfaceConfiguration r6 = r2.getInterfaceConfig(r8)     // Catch: java.lang.Exception -> L82
            r3 = r6
            if (r3 == 0) goto L80
            android.net.LinkAddress r6 = r3.getLinkAddress()     // Catch: java.lang.Exception -> L82
            java.lang.StringBuilder r7 = new java.lang.StringBuilder     // Catch: java.lang.Exception -> L82
            r7.<init>()     // Catch: java.lang.Exception -> L82
            java.lang.String r9 = "linkAddr"
            r7.append(r9)     // Catch: java.lang.Exception -> L82
            java.lang.String r9 = r6.toString()     // Catch: java.lang.Exception -> L82
            r7.append(r9)     // Catch: java.lang.Exception -> L82
            java.lang.String r7 = r7.toString()     // Catch: java.lang.Exception -> L82
            com.xiaopeng.lib.utils.LogUtils.d(r0, r7)     // Catch: java.lang.Exception -> L82
            java.net.InetAddress r7 = r6.getAddress()     // Catch: java.lang.Exception -> L82
            java.lang.StringBuilder r9 = new java.lang.StringBuilder     // Catch: java.lang.Exception -> L82
            r9.<init>()     // Catch: java.lang.Exception -> L82
            java.lang.String r10 = "inetaddr"
            r9.append(r10)     // Catch: java.lang.Exception -> L82
            java.lang.String r10 = r7.toString()     // Catch: java.lang.Exception -> L82
            r9.append(r10)     // Catch: java.lang.Exception -> L82
            java.lang.String r9 = r9.toString()     // Catch: java.lang.Exception -> L82
            com.xiaopeng.lib.utils.LogUtils.d(r0, r9)     // Catch: java.lang.Exception -> L82
            java.lang.String r9 = r7.getHostAddress()     // Catch: java.lang.Exception -> L82
            r4 = r9
            if (r4 == 0) goto L7c
            java.lang.StringBuilder r9 = new java.lang.StringBuilder     // Catch: java.lang.Exception -> L82
            r9.<init>()     // Catch: java.lang.Exception -> L82
            java.lang.String r10 = "address "
            r9.append(r10)     // Catch: java.lang.Exception -> L82
            r9.append(r4)     // Catch: java.lang.Exception -> L82
            java.lang.String r9 = r9.toString()     // Catch: java.lang.Exception -> L82
            com.xiaopeng.lib.utils.LogUtils.d(r0, r9)     // Catch: java.lang.Exception -> L82
        L7c:
            goto L80
        L7d:
            int r7 = r7 + 1
            goto L14
        L80:
            return r4
        L82:
            r5 = move-exception
            java.lang.StringBuilder r6 = new java.lang.StringBuilder
            r6.<init>()
            java.lang.String r7 = "Error configuring interface :"
            r6.append(r7)
            r6.append(r5)
            java.lang.String r6 = r6.toString()
            com.xiaopeng.lib.utils.LogUtils.e(r0, r6)
            r0 = 0
            return r0
        */
        throw new UnsupportedOperationException("Method not decompiled: com.xiaopeng.commonfunc.utils.DataHelp.getTetherIpAddress():java.lang.String");
    }

    public static String getIpAddress(String type) {
        IBinder b = ServiceManager.getService("network_management");
        INetworkManagementService service = INetworkManagementService.Stub.asInterface(b);
        String address = null;
        try {
            InterfaceConfiguration ifcg = service.getInterfaceConfig(type);
            if (ifcg != null) {
                LinkAddress linkAddr = ifcg.getLinkAddress();
                LogUtils.d("getIpAddress", "linkAddr" + linkAddr.toString());
                InetAddress inetaddr = linkAddr.getAddress();
                LogUtils.d("getIpAddress", "inetaddr" + inetaddr.toString());
                address = inetaddr.getHostAddress();
                if (address != null) {
                    LogUtils.d("getIpAddress", "address " + address);
                }
            }
            return address;
        } catch (Exception e) {
            LogUtils.e("getIpAddress", "Error configuring interface :" + e);
            return null;
        }
    }

    private static String decode(Charset charset, byte[] b) {
        if (b == null) {
            return null;
        }
        CharBuffer cb = charset.decode(ByteBuffer.wrap(b));
        return new String(cb.array(), 0, cb.length());
    }

    public static String getAscii(BitSet bitSet, int startbit, int endbit) {
        return decode(ASCII, getBytes(bitSet, startbit, endbit));
    }

    private static byte[] getBytes(BitSet bitSet, int startbit, int endbit) {
        byte[] res = new byte[((endbit - startbit) + 7) / 8];
        for (int i = startbit; i < endbit; i += 8) {
            res[(i - startbit) / 8] = (byte) getInt(bitSet, i, i + 8);
        }
        return res;
    }

    public static int getInt(BitSet bitSet, int startbit, int endbit) {
        int res = 0;
        for (int i = startbit; i < endbit; i++) {
            if (bitSet.get(i)) {
                res = (int) (res + Math.pow(2.0d, i - startbit));
            }
        }
        return res;
    }

    public static long getLong(BitSet bitSet, int startbit, int endbit) {
        long res = 0;
        for (int i = startbit; i < endbit; i++) {
            if (bitSet.get(i)) {
                res = (long) (res + Math.pow(2.0d, i - startbit));
            }
        }
        return res;
    }

    public static String getHex(BitSet bitSet, int startbit, int endbit) {
        return byteArrayToHexStr(getBytes(bitSet, startbit, endbit), false);
    }
}
