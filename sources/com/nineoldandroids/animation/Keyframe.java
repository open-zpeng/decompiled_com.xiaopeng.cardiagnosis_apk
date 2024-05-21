package com.nineoldandroids.animation;

import android.view.animation.Interpolator;
/* loaded from: classes4.dex */
public abstract class Keyframe implements Cloneable {
    float mFraction;
    Class mValueType;
    private Interpolator mInterpolator = null;
    boolean mHasValue = false;

    @Override // 
    /* renamed from: clone */
    public abstract Keyframe mo103clone();

    public abstract Object getValue();

    public abstract void setValue(Object obj);

    public static Keyframe ofInt(float fraction, int value) {
        return new IntKeyframe(fraction, value);
    }

    public static Keyframe ofInt(float fraction) {
        return new IntKeyframe(fraction);
    }

    public static Keyframe ofFloat(float fraction, float value) {
        return new FloatKeyframe(fraction, value);
    }

    public static Keyframe ofFloat(float fraction) {
        return new FloatKeyframe(fraction);
    }

    public static Keyframe ofObject(float fraction, Object value) {
        return new ObjectKeyframe(fraction, value);
    }

    public static Keyframe ofObject(float fraction) {
        return new ObjectKeyframe(fraction, null);
    }

    public boolean hasValue() {
        return this.mHasValue;
    }

    public float getFraction() {
        return this.mFraction;
    }

    public void setFraction(float fraction) {
        this.mFraction = fraction;
    }

    public Interpolator getInterpolator() {
        return this.mInterpolator;
    }

    public void setInterpolator(Interpolator interpolator) {
        this.mInterpolator = interpolator;
    }

    public Class getType() {
        return this.mValueType;
    }

    /* loaded from: classes4.dex */
    static class ObjectKeyframe extends Keyframe {
        Object mValue;

        ObjectKeyframe(float fraction, Object value) {
            this.mFraction = fraction;
            this.mValue = value;
            this.mHasValue = value != null;
            this.mValueType = this.mHasValue ? value.getClass() : Object.class;
        }

        @Override // com.nineoldandroids.animation.Keyframe
        public Object getValue() {
            return this.mValue;
        }

        @Override // com.nineoldandroids.animation.Keyframe
        public void setValue(Object value) {
            this.mValue = value;
            this.mHasValue = value != null;
        }

        @Override // com.nineoldandroids.animation.Keyframe
        /* renamed from: clone */
        public ObjectKeyframe mo103clone() {
            ObjectKeyframe kfClone = new ObjectKeyframe(getFraction(), this.mValue);
            kfClone.setInterpolator(getInterpolator());
            return kfClone;
        }
    }

    /* loaded from: classes4.dex */
    static class IntKeyframe extends Keyframe {
        int mValue;

        IntKeyframe(float fraction, int value) {
            this.mFraction = fraction;
            this.mValue = value;
            this.mValueType = Integer.TYPE;
            this.mHasValue = true;
        }

        IntKeyframe(float fraction) {
            this.mFraction = fraction;
            this.mValueType = Integer.TYPE;
        }

        public int getIntValue() {
            return this.mValue;
        }

        @Override // com.nineoldandroids.animation.Keyframe
        public Object getValue() {
            return Integer.valueOf(this.mValue);
        }

        @Override // com.nineoldandroids.animation.Keyframe
        public void setValue(Object value) {
            if (value != null && value.getClass() == Integer.class) {
                this.mValue = ((Integer) value).intValue();
                this.mHasValue = true;
            }
        }

        @Override // com.nineoldandroids.animation.Keyframe
        /* renamed from: clone */
        public IntKeyframe mo103clone() {
            IntKeyframe kfClone = new IntKeyframe(getFraction(), this.mValue);
            kfClone.setInterpolator(getInterpolator());
            return kfClone;
        }
    }

    /* loaded from: classes4.dex */
    static class FloatKeyframe extends Keyframe {
        float mValue;

        FloatKeyframe(float fraction, float value) {
            this.mFraction = fraction;
            this.mValue = value;
            this.mValueType = Float.TYPE;
            this.mHasValue = true;
        }

        FloatKeyframe(float fraction) {
            this.mFraction = fraction;
            this.mValueType = Float.TYPE;
        }

        public float getFloatValue() {
            return this.mValue;
        }

        @Override // com.nineoldandroids.animation.Keyframe
        public Object getValue() {
            return Float.valueOf(this.mValue);
        }

        @Override // com.nineoldandroids.animation.Keyframe
        public void setValue(Object value) {
            if (value != null && value.getClass() == Float.class) {
                this.mValue = ((Float) value).floatValue();
                this.mHasValue = true;
            }
        }

        @Override // com.nineoldandroids.animation.Keyframe
        /* renamed from: clone */
        public FloatKeyframe mo103clone() {
            FloatKeyframe kfClone = new FloatKeyframe(getFraction(), this.mValue);
            kfClone.setInterpolator(getInterpolator());
            return kfClone;
        }
    }
}
