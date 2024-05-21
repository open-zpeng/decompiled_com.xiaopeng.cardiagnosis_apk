package com.xiaopeng.commonfunc.utils;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;
import com.google.zxing.BarcodeFormat;
import com.google.zxing.EncodeHintType;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.qrcode.QRCodeWriter;
import com.google.zxing.qrcode.decoder.ErrorCorrectionLevel;
import java.util.HashMap;
import java.util.Map;
/* loaded from: classes4.dex */
public class QRCodeUtil {
    public static Bitmap createQRImage(String content, int widthPix, int heightPix, Bitmap logoBm) {
        if (content != null) {
            try {
                if (!"".equals(content)) {
                    Map<EncodeHintType, Object> hints = new HashMap<>();
                    hints.put(EncodeHintType.CHARACTER_SET, "utf-8");
                    hints.put(EncodeHintType.ERROR_CORRECTION, ErrorCorrectionLevel.H);
                    BitMatrix bitMatrix = new QRCodeWriter().encode(content, BarcodeFormat.QR_CODE, widthPix, heightPix, hints);
                    int[] pixels = new int[widthPix * heightPix];
                    for (int y = 0; y < heightPix; y++) {
                        for (int x = 0; x < widthPix; x++) {
                            if (bitMatrix.get(x, y)) {
                                pixels[(y * widthPix) + x] = -16777216;
                            } else {
                                pixels[(y * widthPix) + x] = -1;
                            }
                        }
                    }
                    Bitmap bitmap = Bitmap.createBitmap(widthPix, heightPix, Bitmap.Config.ARGB_8888);
                    try {
                        bitmap.setPixels(pixels, 0, widthPix, 0, 0, widthPix, heightPix);
                        if (logoBm != null) {
                            return addLogo(bitmap, logoBm);
                        }
                        return bitmap;
                    } catch (Exception e) {
                        e = e;
                        e.printStackTrace();
                        return null;
                    }
                }
            } catch (Exception e2) {
                e = e2;
            }
        }
        return null;
    }

    private static Bitmap addLogo(Bitmap src, Bitmap logo) {
        if (src == null) {
            return null;
        }
        if (logo == null) {
            return src;
        }
        int srcWidth = src.getWidth();
        int srcHeight = src.getHeight();
        int logoWidth = logo.getWidth();
        int logoHeight = logo.getHeight();
        if (srcWidth == 0 || srcHeight == 0) {
            return null;
        }
        if (logoWidth == 0 || logoHeight == 0) {
            return src;
        }
        float scaleFactor = ((srcWidth * 1.0f) / 5.0f) / logoWidth;
        Bitmap bitmap = Bitmap.createBitmap(srcWidth, srcHeight, Bitmap.Config.ARGB_8888);
        try {
            Canvas canvas = new Canvas(bitmap);
            canvas.drawBitmap(src, 0.0f, 0.0f, (Paint) null);
            canvas.scale(scaleFactor, scaleFactor, srcWidth / 2, srcHeight / 2);
            canvas.drawBitmap(logo, (srcWidth - logoWidth) / 2, (srcHeight - logoHeight) / 2, (Paint) null);
            canvas.save(31);
            canvas.restore();
            return bitmap;
        } catch (Exception e) {
            e.getStackTrace();
            return null;
        }
    }
}
