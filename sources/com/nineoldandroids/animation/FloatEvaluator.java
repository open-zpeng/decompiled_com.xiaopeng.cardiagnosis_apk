package com.nineoldandroids.animation;
/* loaded from: classes4.dex */
public class FloatEvaluator implements TypeEvaluator<Number> {
    @Override // com.nineoldandroids.animation.TypeEvaluator
    public Float evaluate(float fraction, Number startValue, Number endValue) {
        float startFloat = startValue.floatValue();
        return Float.valueOf(((endValue.floatValue() - startFloat) * fraction) + startFloat);
    }
}
