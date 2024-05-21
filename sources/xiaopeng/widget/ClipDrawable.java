package xiaopeng.widget;

import android.content.res.Resources;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Path;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.DrawableWrapper;
import android.util.AttributeSet;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import java.io.IOException;
import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;
/* loaded from: classes5.dex */
public class ClipDrawable extends DrawableWrapper {
    private static final int MAX_LEVEL = 10000;
    protected Path mClipPath;
    protected int mRadiusX;
    protected int mRadiusY;
    private Path mTempPath;

    public ClipDrawable(Drawable dr) {
        super(dr);
        init();
    }

    public ClipDrawable() {
        this(null);
    }

    private void init() {
    }

    @Override // android.graphics.drawable.DrawableWrapper, android.graphics.drawable.Drawable
    public void inflate(Resources r, XmlPullParser parser, AttributeSet attrs, Resources.Theme theme) throws XmlPullParserException, IOException {
        TypedArray ta;
        super.inflate(r, parser, attrs, theme);
        if (theme != null) {
            ta = theme.obtainStyledAttributes(attrs, R.styleable.ClipDrawable, 0, 0);
        } else {
            ta = r.obtainAttributes(attrs, R.styleable.ClipDrawable);
        }
        Drawable drawable = ta.getDrawable(R.styleable.ClipDrawable_android_drawable);
        if (drawable != null) {
            setDrawable(drawable);
        } else {
            inflateChildDrawable(r, parser, attrs, theme);
        }
        this.mRadiusX = ta.getDimensionPixelOffset(R.styleable.ClipDrawable_radius_x, 0);
        this.mRadiusY = ta.getDimensionPixelOffset(R.styleable.ClipDrawable_radius_y, 0);
        ta.recycle();
    }

    private void inflateChildDrawable(@NonNull Resources r, @NonNull XmlPullParser parser, @NonNull AttributeSet attrs, @Nullable Resources.Theme theme) throws XmlPullParserException, IOException {
        Drawable dr = null;
        int outerDepth = parser.getDepth();
        while (true) {
            int type = parser.next();
            if (type == 1 || (type == 3 && parser.getDepth() <= outerDepth)) {
                break;
            } else if (type == 2) {
                dr = Drawable.createFromXmlInner(r, parser, attrs, theme);
            }
        }
        if (dr != null) {
            setDrawable(dr);
        }
    }

    @Override // android.graphics.drawable.DrawableWrapper, android.graphics.drawable.Drawable
    protected boolean onLevelChange(int level) {
        super.onLevelChange(level);
        initPath(level, getBounds());
        invalidateSelf();
        return true;
    }

    @Override // android.graphics.drawable.DrawableWrapper, android.graphics.drawable.Drawable
    protected void onBoundsChange(Rect bounds) {
        super.onBoundsChange(bounds);
        initPath(getLevel(), bounds);
    }

    private void initPath(int level, Rect bounds) {
        float w = bounds.width();
        int h = bounds.height();
        this.mClipPath = generateRoundRect(bounds.left, bounds.top, bounds.left + (w - (((10000 - level) * w) / 10000.0f)), bounds.top + h, this.mRadiusX, this.mRadiusY);
    }

    @Override // android.graphics.drawable.DrawableWrapper, android.graphics.drawable.Drawable
    public void draw(Canvas canvas) {
        drawSliderRect(this.mClipPath, canvas);
    }

    protected void drawSliderRect(Path sliderPath, Canvas canvas) {
        Drawable dr = getDrawable();
        canvas.save();
        canvas.clipPath(sliderPath);
        dr.draw(canvas);
        canvas.restore();
    }

    public Path generateRoundRect(float left, float top, float right, float bottom, float rx, float ry) {
        Path path = this.mTempPath;
        if (path == null) {
            this.mTempPath = new Path();
        } else {
            path.reset();
        }
        float[] radii = {0.0f, 0.0f, rx, ry, rx, ry, 0.0f, 0.0f};
        this.mTempPath.addRoundRect(left, top, right, bottom, radii, Path.Direction.CW);
        return this.mTempPath;
    }
}
