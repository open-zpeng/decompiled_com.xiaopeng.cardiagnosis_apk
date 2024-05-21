package com.xiaopeng.xui.widget.slider;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.ColorFilter;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.NinePatchDrawable;
import androidx.core.content.ContextCompat;
import com.xiaopeng.xpui.R;
/* loaded from: classes5.dex */
class IndicatorDrawable extends Drawable {
    private static final int TEXT_PADDING = 50;
    NinePatchDrawable bgDay9;
    NinePatchDrawable bgDayDisable9;
    NinePatchDrawable bgNight9;
    NinePatchDrawable bgNightDisable9;
    private Rect bonds;
    private float indicatorCenter;
    private boolean isEnabled;
    private boolean isNight;
    private int slideWidth;
    private int textWidth;
    private static int MAX_INDICATOR_SIZE = 116;
    private static float INDICATOR_TEXT_SIZE = 24.0f;
    private static int MIN_INDICATOR_SIZE = 56;
    private static int INDICATOR_TEXT_VERTICAL = 10;
    private static int TEXT_PADDING_TOP = 42;
    private final Paint textPaint = new Paint(1);
    private String indicatorText = "";

    public IndicatorDrawable(Context context) {
        float f = this.indicatorCenter;
        int i = MIN_INDICATOR_SIZE;
        int i2 = INDICATOR_TEXT_VERTICAL;
        this.bonds = new Rect((int) (f - (i / 2)), i2, (int) (f + (i / 2)), i2 + 50);
        this.textPaint.setTextSize(INDICATOR_TEXT_SIZE);
        this.textPaint.setStyle(Paint.Style.FILL);
        this.textPaint.setTextAlign(Paint.Align.CENTER);
        this.bgDay9 = (NinePatchDrawable) ContextCompat.getDrawable(context, R.drawable.x_slider_tag_day);
        this.bgNight9 = (NinePatchDrawable) ContextCompat.getDrawable(context, R.drawable.x_slider_tag_night);
        this.bgDayDisable9 = (NinePatchDrawable) ContextCompat.getDrawable(context, R.drawable.x_slider_tag_day_disable);
        this.bgNightDisable9 = (NinePatchDrawable) ContextCompat.getDrawable(context, R.drawable.x_slider_tag_night_disable);
    }

    @Override // android.graphics.drawable.Drawable
    public void draw(Canvas canvas) {
        if (this.isNight) {
            if (this.isEnabled) {
                this.bgNight9.setBounds(this.bonds);
                this.bgNight9.draw(canvas);
            } else {
                this.bgNightDisable9.setBounds(this.bonds);
                this.bgNightDisable9.draw(canvas);
            }
        } else if (this.isEnabled) {
            this.bgDay9.setBounds(this.bonds);
            this.bgDay9.draw(canvas);
        } else {
            this.bgDayDisable9.setBounds(this.bonds);
            this.bgDayDisable9.draw(canvas);
        }
        this.textPaint.setColor(this.isEnabled ? -1 : 1560281087);
        canvas.drawText(this.indicatorText, (this.bonds.left + this.bonds.right) / 2, TEXT_PADDING_TOP, this.textPaint);
    }

    public void updateCenter(float center, String text, boolean isNight, int slideWidth) {
        this.isNight = isNight;
        this.indicatorText = text;
        this.indicatorCenter = center;
        this.textWidth = (int) this.textPaint.measureText(text);
        this.slideWidth = slideWidth;
        resetBounds();
        invalidateSelf();
    }

    @Override // android.graphics.drawable.Drawable
    public void setAlpha(int alpha) {
    }

    @Override // android.graphics.drawable.Drawable
    public void setColorFilter(ColorFilter colorFilter) {
    }

    @Override // android.graphics.drawable.Drawable
    public int getOpacity() {
        return 0;
    }

    public void draw(Canvas canvas, boolean isNight, boolean enabled) {
        this.isNight = isNight;
        this.isEnabled = enabled;
        draw(canvas);
    }

    private void resetBounds() {
        int specifyWidth = Math.max(this.textWidth + 50, MIN_INDICATOR_SIZE);
        float f = this.indicatorCenter;
        float offsetStart = f - (specifyWidth / 2);
        int i = this.slideWidth;
        float offsetEnd = (i - f) - (specifyWidth / 2);
        if (offsetStart < 0.0f) {
            Rect rect = this.bonds;
            rect.left = 0;
            rect.right = specifyWidth;
        } else if (offsetEnd < 0.0f) {
            Rect rect2 = this.bonds;
            rect2.left = i - specifyWidth;
            rect2.right = i;
        } else {
            Rect rect3 = this.bonds;
            rect3.left = (int) (f - (specifyWidth / 2));
            rect3.right = (int) (f + (specifyWidth / 2));
        }
    }
}
