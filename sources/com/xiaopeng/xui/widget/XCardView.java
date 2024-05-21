package com.xiaopeng.xui.widget;

import android.content.Context;
import android.content.res.Configuration;
import android.util.AttributeSet;
import android.view.View;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.cardview.widget.CardView;
import com.xiaopeng.vui.commons.VuiUpdateType;
import com.xiaopeng.xui.view.XViewDelegate;
import com.xiaopeng.xui.vui.VuiView;
/* loaded from: classes5.dex */
public class XCardView extends CardView implements VuiView {
    protected XViewDelegate mXViewDelegate;

    public XCardView(@NonNull Context context) {
        super(context);
    }

    /* JADX WARN: Multi-variable type inference failed */
    public XCardView(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        this.mXViewDelegate = XViewDelegate.create(this, attrs);
        initVui(this, attrs);
    }

    /* JADX WARN: Multi-variable type inference failed */
    public XCardView(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        this.mXViewDelegate = XViewDelegate.create(this, attrs, defStyleAttr, 0);
        initVui(this, attrs);
    }

    protected void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        XViewDelegate xViewDelegate = this.mXViewDelegate;
        if (xViewDelegate != null) {
            xViewDelegate.onConfigurationChanged(newConfig);
        }
    }

    protected void onAttachedToWindow() {
        super.onAttachedToWindow();
        XViewDelegate xViewDelegate = this.mXViewDelegate;
        if (xViewDelegate != null) {
            xViewDelegate.onAttachedToWindow();
        }
    }

    protected void onDetachedFromWindow() {
        super.onDetachedFromWindow();
        XViewDelegate xViewDelegate = this.mXViewDelegate;
        if (xViewDelegate != null) {
            xViewDelegate.onDetachedFromWindow();
        }
    }

    /* JADX WARN: Multi-variable type inference failed */
    public void setVisibility(int visibility) {
        super.setVisibility(visibility);
        setVuiVisibility(this, visibility);
    }

    /* JADX WARN: Multi-variable type inference failed */
    public void onViewAdded(View child) {
        super.onViewAdded(child);
        updateVui(this, VuiUpdateType.UPDATE_VIEW);
    }

    /* JADX WARN: Multi-variable type inference failed */
    public void onViewRemoved(View child) {
        super.onViewRemoved(child);
        updateVui(this, VuiUpdateType.UPDATE_VIEW);
    }

    protected void finalize() throws Throwable {
        super.finalize();
        releaseVui();
    }
}
