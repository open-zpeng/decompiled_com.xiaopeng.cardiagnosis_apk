package com.xiaopeng.xui.widget;

import android.content.Context;
import android.content.res.Configuration;
import android.util.AttributeSet;
import com.xiaopeng.libtheme.ThemeViewModel;
import com.xiaopeng.xpui.R;
import com.xiaopeng.xui.view.XViewDelegate;
import xiaopeng.widget.SimpleSlider;
/* loaded from: classes5.dex */
public class XSimpleSlider extends SimpleSlider {
    private XViewDelegate mXViewDelegate;

    public XSimpleSlider(Context context) {
        this(context, null);
    }

    public XSimpleSlider(Context context, AttributeSet attrs) {
        this(context, attrs, R.style.XSimpleSlider);
    }

    public XSimpleSlider(Context context, AttributeSet attrs, int defStyleAttr) {
        this(context, attrs, defStyleAttr, R.style.XSimpleSlider);
    }

    public XSimpleSlider(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        this.mXViewDelegate = XViewDelegate.create(this, attrs, defStyleAttr, defStyleRes);
        this.mXViewDelegate.getThemeViewModel().setCallback(new ThemeViewModel.OnCallback() { // from class: com.xiaopeng.xui.widget.-$$Lambda$1nSZItY1LxLjhgUMuj4enwCbqSg
            @Override // com.xiaopeng.libtheme.ThemeViewModel.OnCallback
            public final void onThemeChanged() {
                XSimpleSlider.this.refreshVisual();
            }
        });
    }

    @Override // android.view.View
    protected void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        XViewDelegate xViewDelegate = this.mXViewDelegate;
        if (xViewDelegate != null) {
            xViewDelegate.onConfigurationChanged(newConfig);
        }
    }

    @Override // android.view.View
    protected void onAttachedToWindow() {
        super.onAttachedToWindow();
        XViewDelegate xViewDelegate = this.mXViewDelegate;
        if (xViewDelegate != null) {
            xViewDelegate.onAttachedToWindow();
        }
    }

    @Override // android.view.View
    protected void onDetachedFromWindow() {
        super.onDetachedFromWindow();
        XViewDelegate xViewDelegate = this.mXViewDelegate;
        if (xViewDelegate != null) {
            xViewDelegate.onDetachedFromWindow();
        }
    }
}
