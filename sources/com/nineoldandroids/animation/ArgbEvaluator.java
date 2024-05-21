package com.nineoldandroids.animation;
/* loaded from: classes4.dex */
public class ArgbEvaluator implements TypeEvaluator {
    @Override // com.nineoldandroids.animation.TypeEvaluator
    public Object evaluate(float fraction, Object startValue, Object endValue) {
        int startInt = ((Integer) startValue).intValue();
        int startA = startInt >> 24;
        int startR = (startInt >> 16) & 255;
        int startG = (startInt >> 8) & 255;
        int startB = startInt & 255;
        int endInt = ((Integer) endValue).intValue();
        int endA = endInt >> 24;
        int endR = (endInt >> 16) & 255;
        int endG = (endInt >> 8) & 255;
        int endB = endInt & 255;
        return Integer.valueOf(((((int) ((endA - startA) * fraction)) + startA) << 24) | ((((int) ((endR - startR) * fraction)) + startR) << 16) | ((((int) ((endG - startG) * fraction)) + startG) << 8) | (((int) ((endB - startB) * fraction)) + startB));
    }
}
