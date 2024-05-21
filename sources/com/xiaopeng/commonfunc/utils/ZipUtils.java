package com.xiaopeng.commonfunc.utils;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;
import java.util.zip.ZipOutputStream;
import net.lingala.zip4j.util.InternalZipConstants;
/* loaded from: classes4.dex */
public class ZipUtils {
    private static final String TAG = ZipUtils.class.getSimpleName();

    /* JADX WARN: Code restructure failed: missing block: B:10:0x0030, code lost:
        r3.write(r5, 0, r6);
     */
    /* JADX WARN: Code restructure failed: missing block: B:11:0x0035, code lost:
        r0.closeEntry();
        r6 = r3.toByteArray();
     */
    /* JADX WARN: Code restructure failed: missing block: B:12:0x003c, code lost:
        $closeResource(null, r0);
     */
    /* JADX WARN: Code restructure failed: missing block: B:13:0x003f, code lost:
        return r6;
     */
    /* JADX WARN: Code restructure failed: missing block: B:7:0x001d, code lost:
        r3 = new java.io.ByteArrayOutputStream(2097152);
        r5 = new byte[4096];
     */
    /* JADX WARN: Code restructure failed: missing block: B:8:0x0029, code lost:
        r6 = r0.read(r5);
     */
    /* JADX WARN: Code restructure failed: missing block: B:9:0x002e, code lost:
        if (r6 <= 0) goto L13;
     */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct add '--show-bad-code' argument
    */
    public static byte[] getEntryFromZip(java.io.File r7, java.lang.String r8) throws java.io.IOException {
        /*
            java.util.zip.ZipInputStream r0 = new java.util.zip.ZipInputStream
            java.io.FileInputStream r1 = new java.io.FileInputStream
            r1.<init>(r7)
            r0.<init>(r1)
            r1 = 0
            r2 = r1
        Lc:
            java.util.zip.ZipEntry r3 = r0.getNextEntry()     // Catch: java.lang.Throwable -> L5e
            r2 = r3
            if (r3 == 0) goto L44
            java.lang.String r3 = r2.getName()     // Catch: java.lang.Throwable -> L5e
            boolean r3 = r3.equalsIgnoreCase(r8)     // Catch: java.lang.Throwable -> L5e
            if (r3 == 0) goto L40
            java.io.ByteArrayOutputStream r3 = new java.io.ByteArrayOutputStream     // Catch: java.lang.Throwable -> L5e
            r4 = 2097152(0x200000, float:2.938736E-39)
            r3.<init>(r4)     // Catch: java.lang.Throwable -> L5e
            r4 = 0
            r5 = 4096(0x1000, float:5.74E-42)
            byte[] r5 = new byte[r5]     // Catch: java.lang.Throwable -> L5e
        L29:
            int r6 = r0.read(r5)     // Catch: java.lang.Throwable -> L5e
            r4 = r6
            if (r6 <= 0) goto L35
            r6 = 0
            r3.write(r5, r6, r4)     // Catch: java.lang.Throwable -> L5e
            goto L29
        L35:
            r0.closeEntry()     // Catch: java.lang.Throwable -> L5e
            byte[] r6 = r3.toByteArray()     // Catch: java.lang.Throwable -> L5e
            $closeResource(r1, r0)
            return r6
        L40:
            r0.closeEntry()     // Catch: java.lang.Throwable -> L5e
            goto Lc
        L44:
            $closeResource(r1, r0)
            java.io.IOException r0 = new java.io.IOException
            java.lang.StringBuilder r1 = new java.lang.StringBuilder
            r1.<init>()
            r1.append(r8)
            java.lang.String r2 = " Not Found"
            r1.append(r2)
            java.lang.String r1 = r1.toString()
            r0.<init>(r1)
            throw r0
        L5e:
            r1 = move-exception
            throw r1     // Catch: java.lang.Throwable -> L60
        L60:
            r2 = move-exception
            $closeResource(r1, r0)
            throw r2
        */
        throw new UnsupportedOperationException("Method not decompiled: com.xiaopeng.commonfunc.utils.ZipUtils.getEntryFromZip(java.io.File, java.lang.String):byte[]");
    }

    private static /* synthetic */ void $closeResource(Throwable x0, AutoCloseable x1) {
        if (x0 == null) {
            x1.close();
            return;
        }
        try {
            x1.close();
        } catch (Throwable th) {
            x0.addSuppressed(th);
        }
    }

    /* JADX WARN: Code restructure failed: missing block: B:20:0x006a, code lost:
        r0.close();
     */
    /* JADX WARN: Code restructure failed: missing block: B:22:0x006e, code lost:
        r3 = move-exception;
     */
    /* JADX WARN: Code restructure failed: missing block: B:23:0x006f, code lost:
        r3.printStackTrace();
     */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct add '--show-bad-code' argument
    */
    public static java.io.File getFileFromZip(java.io.File r7, java.lang.String r8) {
        /*
            r0 = 0
            r1 = 0
            byte[] r2 = getEntryFromZip(r7, r8)     // Catch: java.lang.Throwable -> L73 java.io.IOException -> L75
            java.lang.String r3 = com.xiaopeng.commonfunc.utils.ZipUtils.TAG     // Catch: java.lang.Throwable -> L73 java.io.IOException -> L75
            java.lang.StringBuilder r4 = new java.lang.StringBuilder     // Catch: java.lang.Throwable -> L73 java.io.IOException -> L75
            r4.<init>()     // Catch: java.lang.Throwable -> L73 java.io.IOException -> L75
            java.lang.String r5 = "getFileFromZip:"
            r4.append(r5)     // Catch: java.lang.Throwable -> L73 java.io.IOException -> L75
            r5 = 0
            if (r2 != 0) goto L17
            r6 = r5
            goto L18
        L17:
            int r6 = r2.length     // Catch: java.lang.Throwable -> L73 java.io.IOException -> L75
        L18:
            r4.append(r6)     // Catch: java.lang.Throwable -> L73 java.io.IOException -> L75
            java.lang.String r4 = r4.toString()     // Catch: java.lang.Throwable -> L73 java.io.IOException -> L75
            android.util.Log.d(r3, r4)     // Catch: java.lang.Throwable -> L73 java.io.IOException -> L75
            if (r2 == 0) goto L67
            int r3 = r2.length     // Catch: java.lang.Throwable -> L73 java.io.IOException -> L75
            if (r3 != 0) goto L28
            goto L67
        L28:
            java.lang.String r3 = r7.getAbsolutePath()     // Catch: java.lang.Throwable -> L73 java.io.IOException -> L75
            java.lang.StringBuilder r4 = new java.lang.StringBuilder     // Catch: java.lang.Throwable -> L73 java.io.IOException -> L75
            r4.<init>()     // Catch: java.lang.Throwable -> L73 java.io.IOException -> L75
            java.lang.String r6 = java.io.File.separator     // Catch: java.lang.Throwable -> L73 java.io.IOException -> L75
            int r6 = r3.lastIndexOf(r6)     // Catch: java.lang.Throwable -> L73 java.io.IOException -> L75
            int r6 = r6 + 1
            java.lang.String r5 = r3.substring(r5, r6)     // Catch: java.lang.Throwable -> L73 java.io.IOException -> L75
            r4.append(r5)     // Catch: java.lang.Throwable -> L73 java.io.IOException -> L75
            r4.append(r8)     // Catch: java.lang.Throwable -> L73 java.io.IOException -> L75
            java.lang.String r4 = r4.toString()     // Catch: java.lang.Throwable -> L73 java.io.IOException -> L75
            java.io.FileOutputStream r5 = new java.io.FileOutputStream     // Catch: java.lang.Throwable -> L73 java.io.IOException -> L75
            r5.<init>(r4)     // Catch: java.lang.Throwable -> L73 java.io.IOException -> L75
            r0 = r5
            java.io.DataOutputStream r5 = new java.io.DataOutputStream     // Catch: java.lang.Throwable -> L73 java.io.IOException -> L75
            r5.<init>(r0)     // Catch: java.lang.Throwable -> L73 java.io.IOException -> L75
            r5.write(r2)     // Catch: java.lang.Throwable -> L73 java.io.IOException -> L75
            r5.close()     // Catch: java.lang.Throwable -> L73 java.io.IOException -> L75
            java.io.File r6 = new java.io.File     // Catch: java.lang.Throwable -> L73 java.io.IOException -> L75
            r6.<init>(r4)     // Catch: java.lang.Throwable -> L73 java.io.IOException -> L75
            r0.close()     // Catch: java.lang.Exception -> L62
            goto L66
        L62:
            r1 = move-exception
            r1.printStackTrace()
        L66:
            return r6
        L67:
            if (r0 == 0) goto L72
            r0.close()     // Catch: java.lang.Exception -> L6e
            goto L72
        L6e:
            r3 = move-exception
            r3.printStackTrace()
        L72:
            return r1
        L73:
            r1 = move-exception
            goto L85
        L75:
            r2 = move-exception
            r2.printStackTrace()     // Catch: java.lang.Throwable -> L73
            if (r0 == 0) goto L84
            r0.close()     // Catch: java.lang.Exception -> L7f
        L7e:
            goto L84
        L7f:
            r2 = move-exception
            r2.printStackTrace()
            goto L7e
        L84:
            return r1
        L85:
            if (r0 == 0) goto L8f
            r0.close()     // Catch: java.lang.Exception -> L8b
            goto L8f
        L8b:
            r2 = move-exception
            r2.printStackTrace()
        L8f:
            throw r1
        */
        throw new UnsupportedOperationException("Method not decompiled: com.xiaopeng.commonfunc.utils.ZipUtils.getFileFromZip(java.io.File, java.lang.String):java.io.File");
    }

    public static void zipFiles(File[] files, File zipFile) throws Exception {
        if (files == null || files.length == 0) {
            throw new IllegalArgumentException("files is empty");
        }
        byte[] buffer = new byte[1024];
        try {
            FileOutputStream fos = new FileOutputStream(zipFile);
            ZipOutputStream zos = new ZipOutputStream(fos);
            try {
                for (File file : files) {
                    if (file != null) {
                        zos.putNextEntry(new ZipEntry(file.getName()));
                        FileInputStream fis = new FileInputStream(file);
                        BufferedInputStream bis = new BufferedInputStream(fis);
                        while (true) {
                            try {
                                int len = bis.read(buffer, 0, 1024);
                                if (len == -1) {
                                    break;
                                }
                                zos.write(buffer, 0, len);
                            } finally {
                            }
                        }
                        $closeResource(null, bis);
                        $closeResource(null, fis);
                    }
                }
                $closeResource(null, zos);
                $closeResource(null, fos);
            } finally {
            }
        } catch (Exception e) {
            throw e;
        }
    }

    public static void unzipFile(String inputPath, String outputPath) throws IOException {
        unzipFile(new File(inputPath), new File(outputPath));
    }

    public static void unzipFile(File input, File output) throws IOException {
        if (!checkInputFile(input)) {
            return;
        }
        if (!output.exists() || !output.isDirectory()) {
            output.mkdirs();
        }
        ZipInputStream zis = new ZipInputStream(new FileInputStream(input));
        while (true) {
            try {
                ZipEntry ze = zis.getNextEntry();
                if (ze != null) {
                    String name = ze.getName();
                    if (ze.isDirectory()) {
                        mkdirsIfNotExist(output, name);
                    } else {
                        if (name.contains(InternalZipConstants.ZIP_FILE_SEPARATOR)) {
                            String dirs = name.substring(0, name.lastIndexOf(InternalZipConstants.ZIP_FILE_SEPARATOR));
                            mkdirsIfNotExist(output, dirs);
                        }
                        FileOutputStream outputStream = new FileOutputStream(new File(output, name));
                        byte[] buffer = new byte[4096];
                        while (true) {
                            int read = zis.read(buffer);
                            if (read <= 0) {
                                break;
                            }
                            outputStream.write(buffer, 0, read);
                        }
                        $closeResource(null, outputStream);
                        zis.closeEntry();
                    }
                } else {
                    $closeResource(null, zis);
                    return;
                }
            } finally {
            }
        }
    }

    private static boolean checkInputFile(File input) {
        return input != null && input.isFile() && input.exists();
    }

    private static void mkdirsIfNotExist(File parent, String name) {
        File dir = new File(parent, name);
        if (dir.isDirectory() && dir.exists()) {
            return;
        }
        dir.mkdirs();
    }
}
