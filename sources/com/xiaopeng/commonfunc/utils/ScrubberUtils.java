package com.xiaopeng.commonfunc.utils;

import android.content.Context;
import android.os.Build;
import android.telephony.TelephonyManager;
import com.xiaopeng.commonfunc.Constant;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.regex.Pattern;
/* loaded from: classes4.dex */
public class ScrubberUtils {
    private static final boolean DEBUG = false;
    public static final String IGNORE_CACHE_DALVIK_CACHE = "/private/dalvik-cache";
    public static final String IGNORE_DATA_DALVIK_CACHE = "/data/dalvik-cache";
    public static final String IGNORE_DATA_RESOURCE_CACHE = "/data/resource-cache";
    private static final String TAG = ScrubberUtils.class.getSimpleName();
    private static final Pattern EMAIL_PATTERN = Pattern.compile("[a-zA-Z0-9_]+(?:\\.[A-Za-z0-9!#$%&'*+/=?^_`{|}~-]+)*(@|%40)(?!([a-zA-Z0-9]*\\.[a-zA-Z0-9]*\\.[a-zA-Z0-9]*\\.))(?:[A-Za-z0-9](?:[a-zA-Z0-9-]*[A-Za-z0-9])?\\.)+[a-zA-Z](?:[a-zA-Z0-9-]*[a-zA-Z0-9])?");
    private static final Pattern PHONE_NUMBER_PATTERN = Pattern.compile("^(?:(?:\\+?1\\s*(?:[.-]\\s*)?)?(?:\\(\\s*([2-9]1[02-9]|[2-9][02-8]1|[2-9][02-8][02-9])\\s*\\)|([2-9]1[02-9]|[2-9][02-8]1|[2-9][02-8][02-9]))\\s*(?:[.-]\\s*)?)?([2-9]1[02-9]|[2-9][02-9]1|[2-9][02-9]{2})\\s*(?:[.-]\\s*)?([0-9]{4})(?:\\s*(?:#|x\\.?|ext\\.?|extension)\\s*(\\d+))?$");
    private static final Pattern WEB_URL_PATTERN = Pattern.compile("\\b(https?|ftp|file)://[-a-zA-Z0-9+&@#/%?=~_|!:,.;]*[-a-zA-Z0-9+&@#/%=~_|]");
    private static final Pattern IPADDRESS_PATTERN = Pattern.compile("^([01]?\\d\\d?|2[0-4]\\d|25[0-5])\\.([01]?\\d\\d?|2[0-4]\\d|25[0-5])\\.([01]?\\d\\d?|2[0-4]\\d|25[0-5])\\.([01]?\\d\\d?|2[0-4]\\d|25[0-5])$");
    private static final Pattern PHONE_INFO_PATTERN = Pattern.compile("(msisdn=|mMsisdn=|iccid=|iccid: |mImsi=)[a-zA-Z0-9]*", 2);
    private static final Pattern USER_INFO_PATTERN = Pattern.compile("(UserInfo\\{\\d:)[a-zA-Z0-9\\s]*", 2);
    private static final Pattern ACCOUNT_INFO_PATTERN = Pattern.compile("(Account \\{name=)[a-zA-Z0-9]*", 2);

    public static String scrubLine(String line, Pattern extraPattern) {
        if (line.contains(IGNORE_DATA_RESOURCE_CACHE) || line.contains(IGNORE_DATA_DALVIK_CACHE) || line.contains(IGNORE_CACHE_DALVIK_CACHE)) {
            return line;
        }
        String line2 = ACCOUNT_INFO_PATTERN.matcher(USER_INFO_PATTERN.matcher(PHONE_INFO_PATTERN.matcher(WEB_URL_PATTERN.matcher(PHONE_NUMBER_PATTERN.matcher(EMAIL_PATTERN.matcher(IPADDRESS_PATTERN.matcher(line).replaceAll("<IP address omitted>")).replaceAll("<email omitted>")).replaceAll("<phone number omitted>")).replaceAll("<web url omitted>")).replaceAll("<omitted>")).replaceAll("<omitted>")).replaceAll("<omitted>");
        if (extraPattern != null) {
            return extraPattern.matcher(line2).replaceAll("<private info omitted>");
        }
        return line2;
    }

    public static void scrubFile(Context context, File input, File output) throws IOException {
        System.currentTimeMillis();
        ArrayList<String> extraFilters = new ArrayList<>();
        TelephonyManager tm = (TelephonyManager) context.getSystemService("phone");
        if (tm != null && tm.getDeviceId() != null) {
            extraFilters.add(tm.getDeviceId());
        }
        extraFilters.add(getSerialNumber());
        String extraRegex = "";
        Iterator<String> it = extraFilters.iterator();
        while (it.hasNext()) {
            String regex = it.next();
            extraRegex = extraRegex + regex + Constant.VERTIAL_BAR_STRING;
        }
        Pattern privateInfoPattern = Pattern.compile(extraRegex.substring(0, extraRegex.length() - 1));
        BufferedWriter bw = null;
        BufferedReader br = null;
        try {
            br = new BufferedReader(new FileReader(input));
            bw = new BufferedWriter(new FileWriter(output));
            while (true) {
                String line = br.readLine();
                if (line == null) {
                    break;
                }
                String scrubbedLine = scrubLine(line, privateInfoPattern);
                scrubbedLine.equals(line);
                bw.write(scrubbedLine);
                bw.newLine();
            }
            bw.flush();
            try {
                br.close();
            } catch (IOException e) {
            }
            try {
                bw.close();
            } catch (IOException e2) {
            }
            System.currentTimeMillis();
        } catch (Throwable th) {
            if (br != null) {
                try {
                    br.close();
                } catch (IOException e3) {
                }
            }
            if (bw != null) {
                try {
                    bw.close();
                } catch (IOException e4) {
                }
            }
            System.currentTimeMillis();
            throw th;
        }
    }

    private static String getSerialNumber() {
        return Build.SERIAL;
    }
}
