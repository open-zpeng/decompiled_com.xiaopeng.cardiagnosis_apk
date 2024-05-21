package com.xiaopeng.commonfunc.utils;

import com.xiaopeng.commonfunc.bean.factorytest.TestResultItem;
import com.xiaopeng.lib.utils.LogUtils;
import java.io.IOException;
import java.io.RandomAccessFile;
/* loaded from: classes4.dex */
public class TestResultUtil {
    private static final String AGING_TEST_INIT_VALUE = "0,0 ";
    private static final int AGING_TEST_RECORD_CNT = 3;
    public static final int AGING_TEST_RECORD_SIZE = 4;
    public static final String PATH_AGING_TEST_RECORD = "/mnt/vendor/private/factory/aging_test_record";
    public static final String PATH_TEST_RESULT = "/mnt/vendor/private/factory/test_result";
    private static final String TAG = "TestResultUtil";
    private static final int TEST_RESULT_CNT = 500;

    public static boolean createTestResultFile(byte initValue) {
        StringBuilder sb = new StringBuilder();
        for (int i = 1; i <= 500; i++) {
            sb.append(new TestResultItem(i, initValue).toString());
        }
        return FileUtil.writeNCreateFile(PATH_TEST_RESULT, sb.toString());
    }

    public static boolean writeTestResult(int index, byte value) {
        RandomAccessFile out = null;
        try {
            try {
                out = new RandomAccessFile(PATH_TEST_RESULT, "rw");
                out.seek(((index - 1) * 4) + 3);
                out.writeByte(value);
                LogUtils.d(TAG, "writeTestResult path: /mnt/vendor/private/factory/test_result, index: " + index + " value: " + ((char) value));
                try {
                    out.getFD().sync();
                    out.close();
                    return true;
                } catch (IOException e) {
                    return false;
                }
            } catch (Exception e2) {
                LogUtils.d(TAG, "writeTestResult Exception" + e2.toString());
                if (out != null) {
                    try {
                        out.getFD().sync();
                        out.close();
                    } catch (IOException e3) {
                        return false;
                    }
                }
                return false;
            }
        } catch (Throwable e4) {
            if (out != null) {
                try {
                    out.getFD().sync();
                    out.close();
                } catch (IOException e5) {
                    return false;
                }
            }
            throw e4;
        }
    }

    public static byte readTestResult(int index) {
        StringBuilder sb;
        byte value = 0;
        RandomAccessFile reader = null;
        try {
            try {
                reader = new RandomAccessFile(PATH_TEST_RESULT, "r");
                reader.seek(((index - 1) * 4) + 3);
                value = reader.readByte();
                LogUtils.d(TAG, "readTestResult path: /mnt/vendor/private/factory/test_result, value: " + ((int) value));
                try {
                    reader.close();
                } catch (IOException e) {
                    e = e;
                    sb = new StringBuilder();
                    sb.append("readTestResult IOException: ");
                    sb.append(e.toString());
                    LogUtils.d(TAG, sb.toString());
                    return value;
                }
            } catch (Throwable th) {
                if (reader != null) {
                    try {
                        reader.close();
                    } catch (IOException e2) {
                        LogUtils.d(TAG, "readTestResult IOException: " + e2.toString());
                    }
                }
                throw th;
            }
        } catch (IOException e3) {
            LogUtils.d(TAG, "readTestResult IOException: " + e3.toString());
            if (reader != null) {
                try {
                    reader.close();
                } catch (IOException e4) {
                    e = e4;
                    sb = new StringBuilder();
                    sb.append("readTestResult IOException: ");
                    sb.append(e.toString());
                    LogUtils.d(TAG, sb.toString());
                    return value;
                }
            }
        }
        return value;
    }

    public static byte toByte(String str) {
        if ("P".equalsIgnoreCase(str)) {
            return TestResultItem.RESULT_PASS;
        }
        if ("F".equalsIgnoreCase(str)) {
            return TestResultItem.RESULT_FAIL;
        }
        if ("E".equalsIgnoreCase(str)) {
            return TestResultItem.RESULT_ENTER;
        }
        if (!"N".equalsIgnoreCase(str)) {
            return (byte) 0;
        }
        return TestResultItem.RESULT_NOTEST;
    }

    public static boolean createAgingTestResultFile() {
        StringBuilder sb = new StringBuilder();
        for (int i = 1; i <= 3; i++) {
            sb.append(AGING_TEST_INIT_VALUE);
        }
        return FileUtil.writeNCreateFile(PATH_AGING_TEST_RECORD, sb.toString());
    }

    public static boolean increaseAgingTestRecord(int index) {
        RandomAccessFile out = null;
        try {
            try {
                out = new RandomAccessFile(PATH_AGING_TEST_RECORD, "rw");
                out.seek(index);
                int value = out.read();
                out.seek(index);
                out.write(value + 1);
                LogUtils.d(TAG, "IncreaseAgingTestRecord path: /mnt/vendor/private/factory/aging_test_record, index: " + index + " value ascii: " + value);
                try {
                    out.close();
                    return true;
                } catch (IOException e) {
                    return false;
                }
            } catch (Throwable e2) {
                if (out != null) {
                    try {
                        out.close();
                    } catch (IOException e3) {
                        return false;
                    }
                }
                throw e2;
            }
        } catch (Exception e4) {
            LogUtils.d(TAG, "IncreaseAgingTestRecord Exception" + e4.toString());
            if (out != null) {
                try {
                    out.close();
                } catch (IOException e5) {
                    return false;
                }
            }
            return false;
        }
    }

    public static String readAgingTestRecord() {
        return FileUtil.read(PATH_AGING_TEST_RECORD);
    }

    public static int readAgingTestRecord(int index) {
        StringBuilder sb;
        int value = 0;
        RandomAccessFile reader = null;
        try {
            try {
                reader = new RandomAccessFile(PATH_AGING_TEST_RECORD, "r");
                reader.seek(index);
                value = reader.read() - 48;
                LogUtils.d(TAG, "readAgingTestRecord path: /mnt/vendor/private/factory/aging_test_record, index: " + index + ", value: " + value);
            } catch (IOException e) {
                LogUtils.d(TAG, "readAgingTestRecord IOException: " + e.toString());
                if (reader != null) {
                    try {
                        reader.close();
                    } catch (IOException e2) {
                        e = e2;
                        sb = new StringBuilder();
                        sb.append("readAgingTestRecord IOException: ");
                        sb.append(e.toString());
                        LogUtils.d(TAG, sb.toString());
                        return value;
                    }
                }
            }
            try {
                reader.close();
            } catch (IOException e3) {
                e = e3;
                sb = new StringBuilder();
                sb.append("readAgingTestRecord IOException: ");
                sb.append(e.toString());
                LogUtils.d(TAG, sb.toString());
                return value;
            }
            return value;
        } catch (Throwable th) {
            if (reader != null) {
                try {
                    reader.close();
                } catch (IOException e4) {
                    LogUtils.d(TAG, "readAgingTestRecord IOException: " + e4.toString());
                }
            }
            throw th;
        }
    }
}
