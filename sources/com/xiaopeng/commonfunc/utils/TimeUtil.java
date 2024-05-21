package com.xiaopeng.commonfunc.utils;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
/* loaded from: classes4.dex */
public class TimeUtil {
    public static final String DATE_FORMAT_DAY = "dd";
    public static final String DATE_FORMAT_ENGLISH_FULL_TIME = "EEE MMM dd HH:mm:ss.SSS aa yyyy";
    public static final String DATE_FORMAT_HOUR = "HH";
    public static final String DATE_FORMAT_MIN = "mm";
    public static final String DATE_FORMAT_MONTH = "MM";
    public static final String DATE_FORMAT_YEAR = "yyyy";
    public static final String DATE_FORMAT_YYYYMMDDHHMM = "yyyy-MM-dd HH:mm";
    public static final String DATE_FORMAT_YYYYMMDDHHMMSS = "yyyyMMddHHmmss";
    public static final String DATE_FORMAT_YYYYMMDDHHMMSSMMM = "YYYY-MM-DD hh:mm:ss.mmm";
    public static final String DATE_FORMAT_YYYYMMDDHHMMSS_2 = "yyyy-MM-dd HH.mm.ss";
    public static final String DATE_FORMAT_YYYYMMDDHHMMSS_WITH_SEPARATOR = "yyyy-MM-dd HH:mm:ss";

    public static String getDate() {
        SimpleDateFormat format = new SimpleDateFormat("yyyyMMddHHmmss");
        Date date = new Date();
        String time = format.format(date);
        return time;
    }

    public static String getDate(String dateformat) {
        SimpleDateFormat format = new SimpleDateFormat(dateformat);
        Date date = new Date();
        String time = format.format(date);
        return time;
    }

    public static String getEnglishDate(String dateformat) {
        SimpleDateFormat format = new SimpleDateFormat(dateformat, Locale.ENGLISH);
        Date date = new Date();
        String time = format.format(date);
        return time;
    }

    public static double getTimeStampSeconds() {
        return (System.nanoTime() / 1000) / 1000000.0d;
    }

    public static String timeStamp2Date(long seconds, String format) {
        if (format == null || format.isEmpty()) {
            format = DATE_FORMAT_YYYYMMDDHHMMSS_WITH_SEPARATOR;
        }
        SimpleDateFormat sdf = new SimpleDateFormat(format);
        return sdf.format(new Date(1000 * seconds));
    }

    public static long date2TimeStamp(String date_str, String format) {
        try {
            SimpleDateFormat sdf = new SimpleDateFormat(format);
            return sdf.parse(date_str).getTime() / 1000;
        } catch (Exception e) {
            e.printStackTrace();
            return 0L;
        }
    }

    public static long date2TimeStampMills(String date_str, String format) {
        try {
            SimpleDateFormat sdf = new SimpleDateFormat(format);
            return sdf.parse(date_str).getTime();
        } catch (Exception e) {
            e.printStackTrace();
            return 0L;
        }
    }
}
