package com.xiaopeng.xui.widget.slider;

import android.content.Context;
import android.content.res.Configuration;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.LinearGradient;
import android.graphics.Paint;
import android.graphics.Shader;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.ColorDrawable;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewConfiguration;
import android.view.ViewGroup;
import android.view.ViewParent;
import androidx.annotation.ColorInt;
import androidx.core.content.ContextCompat;
import com.alibaba.mtl.appmonitor.AppMonitorDelegate;
import com.xiaopeng.vui.commons.IVuiElementBuilder;
import com.xiaopeng.vui.commons.IVuiElementListener;
import com.xiaopeng.vui.commons.model.VuiElement;
import com.xiaopeng.vui.commons.model.VuiEvent;
import com.xiaopeng.xpui.R;
import com.xiaopeng.xui.theme.XThemeManager;
import com.xiaopeng.xui.vui.floatinglayer.VuiFloatingLayerManager;
import com.xiaopeng.xui.widget.XViewGroup;
import java.text.DecimalFormat;
import org.json.JSONException;
import org.json.JSONObject;
/* loaded from: classes5.dex */
public class XSlider extends XViewGroup implements IVuiElementListener {
    private static final int BG_ITEM_MARGIN = 18;
    public static final int BG_ITEM_SIZE = 30;
    private static final int CHILDREN_LAYOUT_HEIGHT = 40;
    private static final int CHILDREN_LAYOUT_WIDTH = 20;
    private static final int INDICATOR_BALL_RADIUS = 9;
    private static final int INDICATOR_HOLD_HORIZONTAL = 0;
    private static final int INDICATOR_HOLD_VERTICAL = 40;
    private static final int INDICATOR_MARGIN = 16;
    private static final int INDICATOR_OUTER = 7;
    private float accuracy;
    private LinearGradient barGradient;
    @ColorInt
    int bgBallColor;
    @ColorInt
    int bgDayColor;
    private LinearGradient bgGradient;
    private final Paint bgGradientPaint;
    private float bgHeight;
    private float bgItemGap;
    @ColorInt
    int bgLineColorSelect;
    private Paint bgLinePaint;
    @ColorInt
    int bgNightColor;
    private Paint bgPaint;
    private float bgVertical;
    private Paint bollPaint;
    private float currentUpdateIndex;
    @ColorInt
    private int customBackground;
    private int decimal;
    private DecimalFormat decimalFormat;
    private float desireHeight;
    private float desireWidth;
    private int disableAlpha;
    private int enableAlpha;
    private int endColor;
    private int endIndex;
    private final Paint gradientPaint;
    private boolean hidePop;
    IndicatorDrawable indicatorDrawable;
    private float indicatorValue;
    private float indicatorX;
    private int initIndex;
    private boolean isNight;
    private int leftColor;
    @ColorInt
    int lineColorSelect;
    private Paint lineSelectPaint;
    private boolean mIsDragging;
    private float mScaledTouchSlop;
    private int mStep;
    private float mTouchDownX;
    private String prefixUnit;
    private ProgressChangeListener progressChangeListener;
    private int rightColor;
    private float roundRadius;
    private SliderProgressListener sliderProgressListener;
    private int startIndex;
    private BitmapDrawable thumb;
    private int topColor;
    private String unit;
    private int upperLimit;
    private int workableTotalWidth;

    /* loaded from: classes5.dex */
    public interface ProgressChangeListener {
        void onProgressChanged(XSlider xSlider, float f, String str, boolean z);
    }

    /* loaded from: classes5.dex */
    public interface SliderProgressListener {
        void onProgressChanged(XSlider xSlider, float f, String str);

        void onStartTrackingTouch(XSlider xSlider);

        void onStopTrackingTouch(XSlider xSlider);
    }

    public XSlider(Context context) {
        super(context);
        this.gradientPaint = new Paint(1);
        this.bgGradientPaint = new Paint(1);
        this.enableAlpha = 92;
        this.bgLineColorSelect = -15945223;
        this.bgNightColor = 1543503872;
        this.bgDayColor = 1560281087;
        this.bgBallColor = -12871169;
        this.lineColorSelect = -1;
        this.customBackground = 0;
        this.desireHeight = 100.0f;
        this.desireWidth = 644.0f;
        this.bgHeight = 40.0f;
        this.roundRadius = 16.0f;
        this.disableAlpha = 40;
        this.initIndex = -1;
        this.upperLimit = Integer.MIN_VALUE;
        this.bgVertical = 16.0f;
        this.accuracy = 1.0f;
        this.endColor = 1555808977;
        this.topColor = 1555808977;
        this.rightColor = -12871169;
        this.leftColor = -12860929;
        this.mStep = 1;
        this.hidePop = false;
    }

    public XSlider(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.gradientPaint = new Paint(1);
        this.bgGradientPaint = new Paint(1);
        this.enableAlpha = 92;
        this.bgLineColorSelect = -15945223;
        this.bgNightColor = 1543503872;
        this.bgDayColor = 1560281087;
        this.bgBallColor = -12871169;
        this.lineColorSelect = -1;
        this.customBackground = 0;
        this.desireHeight = 100.0f;
        this.desireWidth = 644.0f;
        this.bgHeight = 40.0f;
        this.roundRadius = 16.0f;
        this.disableAlpha = 40;
        this.initIndex = -1;
        this.upperLimit = Integer.MIN_VALUE;
        this.bgVertical = 16.0f;
        this.accuracy = 1.0f;
        this.endColor = 1555808977;
        this.topColor = 1555808977;
        this.rightColor = -12871169;
        this.leftColor = -12860929;
        this.mStep = 1;
        this.hidePop = false;
        initView(context, attrs);
        initPaint();
        if (!isInEditMode()) {
            this.isNight = XThemeManager.isNight(getContext());
        }
    }

    public XSlider(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        this.gradientPaint = new Paint(1);
        this.bgGradientPaint = new Paint(1);
        this.enableAlpha = 92;
        this.bgLineColorSelect = -15945223;
        this.bgNightColor = 1543503872;
        this.bgDayColor = 1560281087;
        this.bgBallColor = -12871169;
        this.lineColorSelect = -1;
        this.customBackground = 0;
        this.desireHeight = 100.0f;
        this.desireWidth = 644.0f;
        this.bgHeight = 40.0f;
        this.roundRadius = 16.0f;
        this.disableAlpha = 40;
        this.initIndex = -1;
        this.upperLimit = Integer.MIN_VALUE;
        this.bgVertical = 16.0f;
        this.accuracy = 1.0f;
        this.endColor = 1555808977;
        this.topColor = 1555808977;
        this.rightColor = -12871169;
        this.leftColor = -12860929;
        this.mStep = 1;
        this.hidePop = false;
    }

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // com.xiaopeng.xui.widget.XViewGroup, android.view.View
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        if (!isInEditMode()) {
            this.isNight = XThemeManager.isNight(getContext());
        }
        postDelayed(new Runnable() { // from class: com.xiaopeng.xui.widget.slider.XSlider.1
            @Override // java.lang.Runnable
            public void run() {
                XSlider.this.invalidate();
            }
        }, 1000L);
    }

    private void initPaint() {
        if (!this.hidePop) {
            this.indicatorDrawable = new IndicatorDrawable(getContext());
        }
        this.bgPaint = new Paint(1);
        this.bgPaint.setStyle(Paint.Style.FILL);
        this.bgPaint.setColor(this.bgNightColor);
        this.bgLinePaint = new Paint(1);
        this.bgLinePaint.setStyle(Paint.Style.FILL);
        this.bgLinePaint.setStrokeCap(Paint.Cap.ROUND);
        this.bgLinePaint.setColor(this.bgLineColorSelect);
        this.bgLinePaint.setStrokeWidth(16.0f);
        this.bollPaint = new Paint(1);
        this.bollPaint.setStyle(Paint.Style.FILL);
        this.bollPaint.setColor(-1);
        this.lineSelectPaint = new Paint(1);
        this.lineSelectPaint.setStyle(Paint.Style.FILL);
        this.lineSelectPaint.setStrokeCap(Paint.Cap.ROUND);
        this.lineSelectPaint.setStrokeWidth(12.0f);
        this.lineSelectPaint.setColor(this.lineColorSelect);
        setEnabled(true);
        this.thumb = (BitmapDrawable) ContextCompat.getDrawable(getContext(), R.drawable.x_slider_slideblock_night);
    }

    private void initView(Context context, AttributeSet attrs) {
        if (attrs != null) {
            TypedArray attributes = context.obtainStyledAttributes(attrs, R.styleable.XSlider);
            this.unit = attributes.getString(R.styleable.XSlider_slider_unit);
            this.startIndex = attributes.getInteger(R.styleable.XSlider_slider_start_index, 0);
            this.mStep = attributes.getInteger(R.styleable.XSlider_slider_step, 1);
            this.initIndex = attributes.getInteger(R.styleable.XSlider_slider_init_index, -1);
            this.endIndex = attributes.getInteger(R.styleable.XSlider_slider_end_index, 100);
            this.upperLimit = attributes.getInteger(R.styleable.XSlider_slider_upper_limit, Integer.MIN_VALUE);
            this.decimal = attributes.getInteger(R.styleable.XSlider_slider_index_decimal, 0);
            this.prefixUnit = attributes.getString(R.styleable.XSlider_slider_unit_prefix);
            this.bgNightColor = attributes.getColor(R.styleable.XSlider_slider_bg_color, this.bgNightColor);
            this.bgLineColorSelect = attributes.getColor(R.styleable.XSlider_slider_bg_line_color, this.bgLineColorSelect);
            this.customBackground = attributes.getColor(R.styleable.XSlider_slider_background, 0);
            this.accuracy = attributes.getFloat(R.styleable.XSlider_slider_accuracy, 0.0f);
            this.hidePop = attributes.getBoolean(R.styleable.XSlider_slider_hide_pop, false);
            if (this.initIndex == -1) {
                int i = this.startIndex;
                int i2 = this.endIndex;
                if (i > i2) {
                    i = i2;
                }
                this.initIndex = i;
            }
            int i3 = this.initIndex;
            int i4 = this.startIndex;
            this.indicatorValue = i3 - i4;
            if (this.endIndex == i4) {
                throw new RuntimeException("startIndex = endIndex!!! please check the xml");
            }
            int i5 = this.decimal;
            this.decimalFormat = i5 == 0 ? null : i5 == 1 ? new DecimalFormat("0.0") : new DecimalFormat("0.00");
            if (this.accuracy == 0.0f) {
                int i6 = this.decimal;
                this.accuracy = i6 == 0 ? 1.0f : i6 == 1 ? 0.1f : 0.01f;
            }
            attributes.recycle();
        }
        setMinimumWidth(80);
        setBackground(new ColorDrawable(this.customBackground));
        this.mScaledTouchSlop = ViewConfiguration.get(context).getScaledTouchSlop();
    }

    @Override // android.view.View
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        int width;
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        if (View.MeasureSpec.getMode(widthMeasureSpec) == Integer.MIN_VALUE) {
            width = (int) this.desireWidth;
        } else {
            width = getMeasuredWidth();
        }
        setMeasuredDimension(width, (int) this.desireHeight);
    }

    @Override // android.view.View
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        if (this.isNight) {
            this.bgPaint.setColor(this.bgNightColor);
            float f = this.roundRadius;
            canvas.drawRoundRect(0.0f, (getHeightExIndicator() / 2.0f) - this.bgVertical, getWidthExIndicator(), (getHeightExIndicator() / 2.0f) + this.bgVertical, f, f, this.bgPaint);
            return;
        }
        if (isEnabled()) {
            this.bgGradientPaint.setShader(this.bgGradient);
            float f2 = this.roundRadius;
            canvas.drawRoundRect(0.0f, (getHeightExIndicator() / 2.0f) - this.bgVertical, getWidthExIndicator(), (getHeightExIndicator() / 2.0f) + this.bgVertical, f2, f2, this.bgGradientPaint);
        } else {
            this.bgPaint.setColor(this.bgDayColor);
            float f3 = this.roundRadius;
            canvas.drawRoundRect(0.0f, (getHeightExIndicator() / 2.0f) - this.bgVertical, getWidthExIndicator(), (getHeightExIndicator() / 2.0f) + this.bgVertical, f3, f3, this.bgPaint);
        }
        if (isEnabled()) {
            this.gradientPaint.setShader(this.barGradient);
            float f4 = this.roundRadius;
            canvas.drawRoundRect(0.0f, (getHeightExIndicator() / 2.0f) - this.bgVertical, filterValidValue() + 9.0f + 7.0f, (getHeightExIndicator() / 2.0f) + this.bgVertical, f4, f4, this.gradientPaint);
            return;
        }
        this.bgPaint.setColor(this.bgBallColor);
        float f5 = this.roundRadius;
        canvas.drawRoundRect(0.0f, (getHeightExIndicator() / 2.0f) - this.bgVertical, filterValidValue() + 9.0f + 7.0f, (getHeightExIndicator() / 2.0f) + this.bgVertical, f5, f5, this.bgPaint);
    }

    @Override // android.view.View
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        setPadding(0, 0, 0, 0);
        this.workableTotalWidth = w - 32;
        this.bgItemGap = this.workableTotalWidth / 29.0f;
        int i = this.initIndex;
        int i2 = this.startIndex;
        this.indicatorX = (Math.abs((i - i2) / (this.endIndex - i2)) * this.workableTotalWidth) + 16.0f;
        int i3 = 0;
        while (true) {
            boolean z = true;
            if (i3 >= 30) {
                break;
            }
            Context context = getContext();
            if (this.indicatorX <= (this.bgItemGap * i3) + 16.0f) {
                z = false;
            }
            SlideLineView slideLineView = new SlideLineView(context, z);
            addView(slideLineView);
            i3++;
        }
        this.bgGradient = new LinearGradient(0.0f, (getHeightExIndicator() / 2.0f) - this.bgVertical, 0.0f, (getHeightExIndicator() / 2.0f) + this.bgVertical, new int[]{this.topColor, this.endColor}, (float[]) null, Shader.TileMode.REPEAT);
        this.barGradient = new LinearGradient(16.0f, 0.0f, this.workableTotalWidth, 0.0f, new int[]{this.leftColor, this.rightColor}, (float[]) null, Shader.TileMode.CLAMP);
        if (!this.hidePop) {
            this.indicatorDrawable.updateCenter(filterValidValue(), getPopString(), this.isNight, getWidth());
        }
        invalidate();
    }

    @Override // android.view.ViewGroup, android.view.View
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        float itemGap = (getWidth() - 36) / 29.0f;
        for (int i = 0; i < getChildCount(); i++) {
            View childAt = getChildAt(i);
            int left = (int) (((i * itemGap) + 18.0f) - 10.0f);
            int top = (((int) getHeightExIndicator()) / 2) - 20;
            int right = (int) ((i * itemGap) + 18.0f + 10.0f);
            int bottom = (((int) getHeightExIndicator()) / 2) + 20;
            childAt.layout(left, top, right, bottom);
        }
    }

    @Override // android.view.View
    public boolean onTouchEvent(MotionEvent event) {
        if (isEnabled()) {
            int action = event.getAction();
            if (action != 0) {
                if (action == 1) {
                    if (this.mIsDragging) {
                        this.mIsDragging = false;
                    } else {
                        SliderProgressListener sliderProgressListener = this.sliderProgressListener;
                        if (sliderProgressListener != null) {
                            sliderProgressListener.onStartTrackingTouch(this);
                        }
                    }
                    this.indicatorX = event.getX();
                    stickIndicator();
                    notifyChildren(true, true);
                    getParent().requestDisallowInterceptTouchEvent(false);
                    SliderProgressListener sliderProgressListener2 = this.sliderProgressListener;
                    if (sliderProgressListener2 != null) {
                        sliderProgressListener2.onStopTrackingTouch(this);
                    }
                    invalidateAll();
                } else if (action != 2) {
                    if (action == 3) {
                        if (this.mIsDragging) {
                            this.mIsDragging = false;
                        }
                        invalidateAll();
                    }
                } else if (this.mIsDragging) {
                    this.indicatorX = event.getX();
                    notifyChildren(true, false);
                    invalidateAll();
                } else {
                    float x = event.getX();
                    if (Math.abs(x - this.mTouchDownX) > this.mScaledTouchSlop) {
                        this.mIsDragging = true;
                        SliderProgressListener sliderProgressListener3 = this.sliderProgressListener;
                        if (sliderProgressListener3 != null) {
                            sliderProgressListener3.onStartTrackingTouch(this);
                        }
                        this.indicatorX = event.getX();
                        getParent().requestDisallowInterceptTouchEvent(true);
                        notifyChildren(true, false);
                        invalidateAll();
                    }
                }
            } else if (isInScrollContainer()) {
                this.mTouchDownX = event.getX();
            } else {
                this.mIsDragging = true;
                getParent().requestDisallowInterceptTouchEvent(true);
                SliderProgressListener sliderProgressListener4 = this.sliderProgressListener;
                if (sliderProgressListener4 != null) {
                    sliderProgressListener4.onStartTrackingTouch(this);
                }
                this.indicatorX = event.getX();
                notifyChildren(true, false);
                invalidateAll();
            }
            return true;
        }
        return true;
    }

    private void invalidateAll() {
        invalidate();
        if (!this.hidePop) {
            this.indicatorDrawable.updateCenter(filterValidValue(), getPopString(), this.isNight, getWidth());
        }
    }

    private boolean isInScrollContainer() {
        for (ViewParent p = getParent(); p instanceof ViewGroup; p = p.getParent()) {
            if (((ViewGroup) p).shouldDelayChildPressedState()) {
                return true;
            }
        }
        return false;
    }

    private void stickIndicator() {
        if (this.mStep == 1) {
            return;
        }
        float natureGap = this.workableTotalWidth / (this.endIndex - this.startIndex);
        int number = (int) (((this.indicatorX - 16.0f) / natureGap) + 0.5d);
        this.indicatorX = (number * natureGap) + 16.0f;
    }

    private void notifyChildren(boolean isNeedUpdate, boolean isForce) {
        float xLocation = filterValidValue();
        int count = getChildCount();
        for (int i = 0; i < count; i++) {
            SlideLineView child = (SlideLineView) getChildAt(i);
            if (child.getX() + (child.getWidth() / 2) <= filterValidValue()) {
                if (!child.isSelect()) {
                    child.setSelect(true);
                }
            } else {
                child.setSelect(false);
            }
        }
        if (isNeedUpdate) {
            float f = (xLocation - 16.0f) / this.workableTotalWidth;
            int i2 = this.endIndex;
            int i3 = this.startIndex;
            this.indicatorValue = f * (i2 - i3);
            if (this.sliderProgressListener != null) {
                if (!isForce) {
                    float f2 = this.indicatorValue;
                    float f3 = this.currentUpdateIndex;
                    float f4 = this.accuracy;
                    if (i3 + f2 < f3 + f4 && f2 + i3 > f3 - f4 && f3 != 0.0f) {
                        return;
                    }
                }
                this.sliderProgressListener.onProgressChanged(this, this.indicatorValue + this.startIndex, this.unit);
                float f5 = (xLocation - 16.0f) / this.workableTotalWidth;
                int i4 = this.endIndex;
                int i5 = this.startIndex;
                this.indicatorValue = f5 * (i4 - i5);
                this.currentUpdateIndex = ((int) this.indicatorValue) + i5;
                updateVui(this);
            }
        }
    }

    public float getIndicatorValue() {
        return (this.indicatorValue + this.startIndex) * this.mStep;
    }

    public void setAccuracy(float accuracy) {
        this.accuracy = accuracy;
    }

    @Override // android.view.ViewGroup, android.view.View
    protected void dispatchDraw(Canvas canvas) {
        super.dispatchDraw(canvas);
        float barCenterX = filterValidValue();
        if (barCenterX == 0.0f) {
            return;
        }
        float barCenterY = getHeightExIndicator() / 2.0f;
        if (!this.hidePop) {
            this.indicatorDrawable.draw(canvas, this.isNight, isEnabled());
        }
        if (!isEnabled()) {
            return;
        }
        if (this.isNight) {
            canvas.drawBitmap(this.thumb.getBitmap(), barCenterX - (this.thumb.getBitmap().getWidth() / 2), barCenterY - (this.thumb.getBitmap().getHeight() / 2), this.bgLinePaint);
        } else {
            canvas.drawCircle(barCenterX, barCenterY, 9.0f, this.bollPaint);
        }
    }

    public float getHeightExIndicator() {
        return getHeight() + 40;
    }

    private float getWidthExIndicator() {
        return getWidth() + 0;
    }

    public float getIndicatorLocationX() {
        return this.indicatorX;
    }

    private float filterValidValue() {
        if (this.indicatorX < 16.0f) {
            return 16.0f;
        }
        float maxValue = (getWidth() - 16) - upperLimitDistance();
        float f = this.indicatorX;
        if (f > maxValue) {
            return maxValue;
        }
        return f;
    }

    private float upperLimitDistance() {
        int i;
        int i2;
        int i3 = this.upperLimit;
        if (i3 != Integer.MIN_VALUE && (i = this.startIndex) < (i2 = this.endIndex) && i <= i3 && i3 <= i2) {
            return ((i2 - i3) * this.workableTotalWidth) / (i2 - i);
        }
        return 0.0f;
    }

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // com.xiaopeng.xui.widget.XViewGroup, android.view.ViewGroup, android.view.View
    public void onDetachedFromWindow() {
        super.onDetachedFromWindow();
    }

    protected String getPopString() {
        if (this.unit == null) {
            this.unit = "";
        }
        if (this.prefixUnit == null) {
            this.prefixUnit = "";
        }
        if (this.decimalFormat == null) {
            if (this.mStep == 1) {
                return this.prefixUnit + (this.startIndex + ((int) this.indicatorValue)) + this.unit;
            }
            return this.prefixUnit + ((this.startIndex + ((int) (this.indicatorValue + 0.5d))) * this.mStep) + this.unit;
        }
        return this.prefixUnit + this.decimalFormat.format((this.startIndex + this.indicatorValue) * this.mStep) + this.unit;
    }

    public void setSliderProgressListener(SliderProgressListener sliderProgressListener) {
        this.sliderProgressListener = sliderProgressListener;
    }

    public void setProgressChangeListener(ProgressChangeListener progressChangeListener) {
        this.progressChangeListener = progressChangeListener;
    }

    public void setCurrentIndex(int currentIndex) {
        setCurrentIndex(currentIndex, false);
    }

    public void setCurrentIndex(final int currentIndex, final boolean fromUser) {
        post(new Runnable() { // from class: com.xiaopeng.xui.widget.slider.-$$Lambda$XSlider$6nRMnQGFhZSAnHXSTyWZZ5qLEF4
            @Override // java.lang.Runnable
            public final void run() {
                XSlider.this.lambda$setCurrentIndex$0$XSlider(currentIndex, fromUser);
            }
        });
    }

    public /* synthetic */ void lambda$setCurrentIndex$0$XSlider(int currentIndex, boolean fromUser) {
        ProgressChangeListener progressChangeListener;
        int i = this.startIndex;
        this.indicatorX = (((currentIndex - i) / (this.endIndex - i)) * this.workableTotalWidth) + 16.0f;
        this.indicatorValue = currentIndex - i;
        invalidate();
        notifyChildren(false, false);
        if (!this.hidePop) {
            this.indicatorDrawable.updateCenter(filterValidValue(), getPopString(), this.isNight, getWidth());
        }
        if (fromUser && (progressChangeListener = this.progressChangeListener) != null) {
            progressChangeListener.onProgressChanged(this, this.indicatorValue + this.startIndex, this.unit, true);
        }
        if (getVuiValue() != null && ((Float) getVuiValue()).floatValue() == getIndicatorValue()) {
            return;
        }
        updateVui(this);
    }

    @Override // android.view.View
    public void setEnabled(boolean enable) {
        if (!enable) {
            this.mIsDragging = false;
        }
        super.setEnabled(enable);
        for (int i = 0; i < getChildCount(); i++) {
            getChildAt(i).setEnabled(enable);
        }
        setAlphaByEnable(enable);
        invalidate();
    }

    public void setStartIndex(int startIndex) {
        if (startIndex == this.endIndex) {
            throw new RuntimeException("startIndex = endIndex!!!");
        }
        this.startIndex = startIndex;
        post(new Runnable() { // from class: com.xiaopeng.xui.widget.slider.-$$Lambda$XSlider$Xz60DHZ7J60ywvILqd--vaYYV3k
            @Override // java.lang.Runnable
            public final void run() {
                XSlider.this.lambda$setStartIndex$1$XSlider();
            }
        });
    }

    public /* synthetic */ void lambda$setStartIndex$1$XSlider() {
        if (!this.hidePop) {
            this.indicatorDrawable.updateCenter(filterValidValue(), getPopString(), this.isNight, getWidth());
        }
        invalidate();
    }

    public void setEndIndex(int endIndex) {
        if (this.startIndex == endIndex) {
            throw new RuntimeException("startIndex = endIndex!!!");
        }
        this.endIndex = endIndex;
        post(new Runnable() { // from class: com.xiaopeng.xui.widget.slider.-$$Lambda$XSlider$kB2KeMUVNgZs-Tjtgxuk4WuttL4
            @Override // java.lang.Runnable
            public final void run() {
                XSlider.this.lambda$setEndIndex$2$XSlider();
            }
        });
    }

    public /* synthetic */ void lambda$setEndIndex$2$XSlider() {
        invalidate();
    }

    public void setInitIndex(int initIndex) {
        int i = this.endIndex;
        if (initIndex > i) {
            this.initIndex = i;
            return;
        }
        int i2 = this.startIndex;
        if (initIndex < i2) {
            this.initIndex = i2;
            return;
        }
        this.initIndex = initIndex;
        this.indicatorValue = initIndex - i2;
        invalidate();
    }

    private void setAlphaByEnable(boolean enable) {
        this.bgNightColor = resetAlpha(this.bgNightColor, enable ? this.enableAlpha : this.disableAlpha);
        this.bgDayColor = resetAlpha(this.bgDayColor, enable ? this.enableAlpha : this.disableAlpha);
        this.bgBallColor = resetAlpha(this.bgBallColor, enable ? 255 : this.disableAlpha);
    }

    private int resetAlpha(@ColorInt int color, int alpha) {
        return (color & 16777215) | (alpha << 24);
    }

    @Override // com.xiaopeng.vui.commons.IVuiElementListener
    public VuiElement onBuildVuiElement(String s, IVuiElementBuilder iVuiElementBuilder) {
        try {
            setVuiValue(Float.valueOf(getIndicatorValue()));
            if (getVuiProps() != null && getVuiProps().has("customSetProps")) {
                boolean customSet = getVuiProps().getBoolean("customSetProps");
                if (customSet) {
                    return null;
                }
            }
            JSONObject jsonObject = getVuiProps();
            if (jsonObject == null) {
                jsonObject = new JSONObject();
            }
            jsonObject.put(AppMonitorDelegate.MIN_VALUE, this.startIndex);
            jsonObject.put(AppMonitorDelegate.MAX_VALUE, this.endIndex);
            jsonObject.put("interval", (int) Math.ceil((this.endIndex - this.startIndex) / 10.0d));
            setVuiProps(jsonObject);
        } catch (JSONException e) {
        }
        return null;
    }

    @Override // com.xiaopeng.vui.commons.IVuiElementListener
    public boolean onVuiElementEvent(final View view, VuiEvent vuiEvent) {
        Double value;
        int index;
        logD("slider onVuiElementEvent");
        if (view == null || (value = (Double) vuiEvent.getEventValue(vuiEvent)) == null) {
            return false;
        }
        if (this.mStep == 1) {
            index = (int) Math.ceil(value.doubleValue());
        } else {
            index = (int) Math.round(value.doubleValue() / this.mStep);
        }
        if (index < this.startIndex || index > this.endIndex) {
            return true;
        }
        setCurrentIndex(index, true);
        post(new Runnable() { // from class: com.xiaopeng.xui.widget.slider.-$$Lambda$XSlider$8q6TCB-9AxkZNaxjbRsq1Ki1ixY
            @Override // java.lang.Runnable
            public final void run() {
                XSlider.this.lambda$onVuiElementEvent$3$XSlider(view);
            }
        });
        return true;
    }

    public /* synthetic */ void lambda$onVuiElementEvent$3$XSlider(View view) {
        int offsetY = (int) ((getHeightExIndicator() / 2.0f) - (getHeight() / 2));
        int offsetX = ((int) getIndicatorLocationX()) - (getWidth() / 2);
        VuiFloatingLayerManager.show(view, offsetX, offsetY);
    }
}
