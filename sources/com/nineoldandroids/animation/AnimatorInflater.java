package com.nineoldandroids.animation;

import android.content.Context;
import android.content.res.Resources;
import android.content.res.TypedArray;
import android.content.res.XmlResourceParser;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.util.Xml;
import android.view.animation.AnimationUtils;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;
/* loaded from: classes4.dex */
public class AnimatorInflater {
    private static final int AnimatorSet_ordering = 0;
    private static final int Animator_duration = 1;
    private static final int Animator_interpolator = 0;
    private static final int Animator_repeatCount = 3;
    private static final int Animator_repeatMode = 4;
    private static final int Animator_startOffset = 2;
    private static final int Animator_valueFrom = 5;
    private static final int Animator_valueTo = 6;
    private static final int Animator_valueType = 7;
    private static final int PropertyAnimator_propertyName = 0;
    private static final int TOGETHER = 0;
    private static final int VALUE_TYPE_FLOAT = 0;
    private static final int[] AnimatorSet = {16843490};
    private static final int[] PropertyAnimator = {16843489};
    private static final int[] Animator = {16843073, 16843160, 16843198, 16843199, 16843200, 16843486, 16843487, 16843488};

    public static Animator loadAnimator(Context context, int id) throws Resources.NotFoundException {
        XmlResourceParser parser = null;
        try {
            try {
                parser = context.getResources().getAnimation(id);
                return createAnimatorFromXml(context, parser);
            } catch (IOException ex) {
                Resources.NotFoundException rnf = new Resources.NotFoundException("Can't load animation resource ID #0x" + Integer.toHexString(id));
                rnf.initCause(ex);
                throw rnf;
            } catch (XmlPullParserException ex2) {
                Resources.NotFoundException rnf2 = new Resources.NotFoundException("Can't load animation resource ID #0x" + Integer.toHexString(id));
                rnf2.initCause(ex2);
                throw rnf2;
            }
        } finally {
            if (parser != null) {
                parser.close();
            }
        }
    }

    private static Animator createAnimatorFromXml(Context c, XmlPullParser parser) throws XmlPullParserException, IOException {
        return createAnimatorFromXml(c, parser, Xml.asAttributeSet(parser), null, 0);
    }

    private static Animator createAnimatorFromXml(Context c, XmlPullParser parser, AttributeSet attrs, AnimatorSet parent, int sequenceOrdering) throws XmlPullParserException, IOException {
        Animator anim = null;
        ArrayList<Animator> childAnims = null;
        int depth = parser.getDepth();
        while (true) {
            int type = parser.next();
            if ((type != 3 || parser.getDepth() > depth) && type != 1) {
                if (type == 2) {
                    String name = parser.getName();
                    if (name.equals("objectAnimator")) {
                        anim = loadObjectAnimator(c, attrs);
                    } else if (name.equals("animator")) {
                        anim = loadAnimator(c, attrs, null);
                    } else if (name.equals("set")) {
                        anim = new AnimatorSet();
                        TypedArray a = c.obtainStyledAttributes(attrs, AnimatorSet);
                        TypedValue orderingValue = new TypedValue();
                        a.getValue(0, orderingValue);
                        int ordering = orderingValue.type == 16 ? orderingValue.data : 0;
                        createAnimatorFromXml(c, parser, attrs, (AnimatorSet) anim, ordering);
                        a.recycle();
                    } else {
                        throw new RuntimeException("Unknown animator name: " + parser.getName());
                    }
                    if (parent != null) {
                        if (childAnims == null) {
                            childAnims = new ArrayList<>();
                        }
                        childAnims.add(anim);
                    }
                }
            }
        }
        if (parent != null && childAnims != null) {
            Animator[] animsArray = new Animator[childAnims.size()];
            int index = 0;
            Iterator i$ = childAnims.iterator();
            while (i$.hasNext()) {
                animsArray[index] = i$.next();
                index++;
            }
            if (sequenceOrdering == 0) {
                parent.playTogether(animsArray);
            } else {
                parent.playSequentially(animsArray);
            }
        }
        return anim;
    }

    private static ObjectAnimator loadObjectAnimator(Context context, AttributeSet attrs) throws Resources.NotFoundException {
        ObjectAnimator anim = new ObjectAnimator();
        loadAnimator(context, attrs, anim);
        TypedArray a = context.obtainStyledAttributes(attrs, PropertyAnimator);
        String propertyName = a.getString(0);
        anim.setPropertyName(propertyName);
        a.recycle();
        return anim;
    }

    private static ValueAnimator loadAnimator(Context context, AttributeSet attrs, ValueAnimator anim) throws Resources.NotFoundException {
        ValueAnimator anim2;
        char c;
        int valueTo;
        int valueFrom;
        char c2;
        int valueTo2;
        TypedArray a = context.obtainStyledAttributes(attrs, Animator);
        long duration = a.getInt(1, 0);
        long startDelay = a.getInt(2, 0);
        int valueType = a.getInt(7, 0);
        if (anim != null) {
            anim2 = anim;
        } else {
            anim2 = new ValueAnimator();
        }
        boolean getFloats = valueType == 0;
        TypedValue tvFrom = a.peekValue(5);
        boolean hasFrom = tvFrom != null;
        int fromType = hasFrom ? tvFrom.type : 0;
        TypedValue tvTo = a.peekValue(6);
        boolean hasTo = tvTo != null;
        int toType = hasTo ? tvTo.type : 0;
        if ((hasFrom && fromType >= 28 && fromType <= 31) || (hasTo && toType >= 28 && toType <= 31)) {
            getFloats = false;
            anim2.setEvaluator(new ArgbEvaluator());
        }
        if (getFloats) {
            if (!hasFrom) {
                float valueTo3 = toType == 5 ? a.getDimension(6, 0.0f) : a.getFloat(6, 0.0f);
                anim2.setFloatValues(valueTo3);
            } else {
                float valueFrom2 = fromType == 5 ? a.getDimension(5, 0.0f) : a.getFloat(5, 0.0f);
                if (hasTo) {
                    float valueTo4 = toType == 5 ? a.getDimension(6, 0.0f) : a.getFloat(6, 0.0f);
                    anim2.setFloatValues(valueFrom2, valueTo4);
                } else {
                    anim2.setFloatValues(valueFrom2);
                }
            }
        } else if (hasFrom) {
            if (fromType == 5) {
                valueFrom = (int) a.getDimension(5, 0.0f);
            } else {
                valueFrom = (fromType < 28 || fromType > 31) ? a.getInt(5, 0) : a.getColor(5, 0);
            }
            if (hasTo) {
                if (toType == 5) {
                    valueTo2 = (int) a.getDimension(6, 0.0f);
                    c2 = 0;
                } else if (toType < 28 || toType > 31) {
                    c2 = 0;
                    valueTo2 = a.getInt(6, 0);
                } else {
                    c2 = 0;
                    valueTo2 = a.getColor(6, 0);
                }
                int[] iArr = new int[2];
                iArr[c2] = valueFrom;
                iArr[1] = valueTo2;
                anim2.setIntValues(iArr);
            } else {
                anim2.setIntValues(valueFrom);
            }
        } else if (hasTo) {
            if (toType == 5) {
                valueTo = (int) a.getDimension(6, 0.0f);
                c = 0;
            } else if (toType < 28 || toType > 31) {
                c = 0;
                valueTo = a.getInt(6, 0);
            } else {
                c = 0;
                valueTo = a.getColor(6, 0);
            }
            int[] iArr2 = new int[1];
            iArr2[c] = valueTo;
            anim2.setIntValues(iArr2);
        }
        anim2.setDuration(duration);
        anim2.setStartDelay(startDelay);
        if (a.hasValue(3)) {
            anim2.setRepeatCount(a.getInt(3, 0));
        }
        if (a.hasValue(4)) {
            anim2.setRepeatMode(a.getInt(4, 1));
        }
        int resID = a.getResourceId(0, 0);
        if (resID > 0) {
            anim2.setInterpolator(AnimationUtils.loadInterpolator(context, resID));
        }
        a.recycle();
        return anim2;
    }
}
