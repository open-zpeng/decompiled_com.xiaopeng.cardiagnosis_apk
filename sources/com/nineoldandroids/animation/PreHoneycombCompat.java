package com.nineoldandroids.animation;

import android.view.View;
import com.nineoldandroids.util.FloatProperty;
import com.nineoldandroids.util.IntProperty;
import com.nineoldandroids.util.Property;
import com.nineoldandroids.view.animation.AnimatorProxy;
import com.xiaopeng.libtheme.ThemeManager;
/* loaded from: classes4.dex */
final class PreHoneycombCompat {
    static Property<View, Float> ALPHA = new FloatProperty<View>(ThemeManager.AttributeSet.ALPHA) { // from class: com.nineoldandroids.animation.PreHoneycombCompat.1
        @Override // com.nineoldandroids.util.FloatProperty
        public void setValue(View object, float value) {
            AnimatorProxy.wrap(object).setAlpha(value);
        }

        @Override // com.nineoldandroids.util.Property
        public Float get(View object) {
            return Float.valueOf(AnimatorProxy.wrap(object).getAlpha());
        }
    };
    static Property<View, Float> PIVOT_X = new FloatProperty<View>("pivotX") { // from class: com.nineoldandroids.animation.PreHoneycombCompat.2
        @Override // com.nineoldandroids.util.FloatProperty
        public void setValue(View object, float value) {
            AnimatorProxy.wrap(object).setPivotX(value);
        }

        @Override // com.nineoldandroids.util.Property
        public Float get(View object) {
            return Float.valueOf(AnimatorProxy.wrap(object).getPivotX());
        }
    };
    static Property<View, Float> PIVOT_Y = new FloatProperty<View>("pivotY") { // from class: com.nineoldandroids.animation.PreHoneycombCompat.3
        @Override // com.nineoldandroids.util.FloatProperty
        public void setValue(View object, float value) {
            AnimatorProxy.wrap(object).setPivotY(value);
        }

        @Override // com.nineoldandroids.util.Property
        public Float get(View object) {
            return Float.valueOf(AnimatorProxy.wrap(object).getPivotY());
        }
    };
    static Property<View, Float> TRANSLATION_X = new FloatProperty<View>("translationX") { // from class: com.nineoldandroids.animation.PreHoneycombCompat.4
        @Override // com.nineoldandroids.util.FloatProperty
        public void setValue(View object, float value) {
            AnimatorProxy.wrap(object).setTranslationX(value);
        }

        @Override // com.nineoldandroids.util.Property
        public Float get(View object) {
            return Float.valueOf(AnimatorProxy.wrap(object).getTranslationX());
        }
    };
    static Property<View, Float> TRANSLATION_Y = new FloatProperty<View>("translationY") { // from class: com.nineoldandroids.animation.PreHoneycombCompat.5
        @Override // com.nineoldandroids.util.FloatProperty
        public void setValue(View object, float value) {
            AnimatorProxy.wrap(object).setTranslationY(value);
        }

        @Override // com.nineoldandroids.util.Property
        public Float get(View object) {
            return Float.valueOf(AnimatorProxy.wrap(object).getTranslationY());
        }
    };
    static Property<View, Float> ROTATION = new FloatProperty<View>("rotation") { // from class: com.nineoldandroids.animation.PreHoneycombCompat.6
        @Override // com.nineoldandroids.util.FloatProperty
        public void setValue(View object, float value) {
            AnimatorProxy.wrap(object).setRotation(value);
        }

        @Override // com.nineoldandroids.util.Property
        public Float get(View object) {
            return Float.valueOf(AnimatorProxy.wrap(object).getRotation());
        }
    };
    static Property<View, Float> ROTATION_X = new FloatProperty<View>("rotationX") { // from class: com.nineoldandroids.animation.PreHoneycombCompat.7
        @Override // com.nineoldandroids.util.FloatProperty
        public void setValue(View object, float value) {
            AnimatorProxy.wrap(object).setRotationX(value);
        }

        @Override // com.nineoldandroids.util.Property
        public Float get(View object) {
            return Float.valueOf(AnimatorProxy.wrap(object).getRotationX());
        }
    };
    static Property<View, Float> ROTATION_Y = new FloatProperty<View>("rotationY") { // from class: com.nineoldandroids.animation.PreHoneycombCompat.8
        @Override // com.nineoldandroids.util.FloatProperty
        public void setValue(View object, float value) {
            AnimatorProxy.wrap(object).setRotationY(value);
        }

        @Override // com.nineoldandroids.util.Property
        public Float get(View object) {
            return Float.valueOf(AnimatorProxy.wrap(object).getRotationY());
        }
    };
    static Property<View, Float> SCALE_X = new FloatProperty<View>("scaleX") { // from class: com.nineoldandroids.animation.PreHoneycombCompat.9
        @Override // com.nineoldandroids.util.FloatProperty
        public void setValue(View object, float value) {
            AnimatorProxy.wrap(object).setScaleX(value);
        }

        @Override // com.nineoldandroids.util.Property
        public Float get(View object) {
            return Float.valueOf(AnimatorProxy.wrap(object).getScaleX());
        }
    };
    static Property<View, Float> SCALE_Y = new FloatProperty<View>("scaleY") { // from class: com.nineoldandroids.animation.PreHoneycombCompat.10
        @Override // com.nineoldandroids.util.FloatProperty
        public void setValue(View object, float value) {
            AnimatorProxy.wrap(object).setScaleY(value);
        }

        @Override // com.nineoldandroids.util.Property
        public Float get(View object) {
            return Float.valueOf(AnimatorProxy.wrap(object).getScaleY());
        }
    };
    static Property<View, Integer> SCROLL_X = new IntProperty<View>("scrollX") { // from class: com.nineoldandroids.animation.PreHoneycombCompat.11
        @Override // com.nineoldandroids.util.IntProperty
        public void setValue(View object, int value) {
            AnimatorProxy.wrap(object).setScrollX(value);
        }

        @Override // com.nineoldandroids.util.Property
        public Integer get(View object) {
            return Integer.valueOf(AnimatorProxy.wrap(object).getScrollX());
        }
    };
    static Property<View, Integer> SCROLL_Y = new IntProperty<View>("scrollY") { // from class: com.nineoldandroids.animation.PreHoneycombCompat.12
        @Override // com.nineoldandroids.util.IntProperty
        public void setValue(View object, int value) {
            AnimatorProxy.wrap(object).setScrollY(value);
        }

        @Override // com.nineoldandroids.util.Property
        public Integer get(View object) {
            return Integer.valueOf(AnimatorProxy.wrap(object).getScrollY());
        }
    };
    static Property<View, Float> X = new FloatProperty<View>("x") { // from class: com.nineoldandroids.animation.PreHoneycombCompat.13
        @Override // com.nineoldandroids.util.FloatProperty
        public void setValue(View object, float value) {
            AnimatorProxy.wrap(object).setX(value);
        }

        @Override // com.nineoldandroids.util.Property
        public Float get(View object) {
            return Float.valueOf(AnimatorProxy.wrap(object).getX());
        }
    };
    static Property<View, Float> Y = new FloatProperty<View>("y") { // from class: com.nineoldandroids.animation.PreHoneycombCompat.14
        @Override // com.nineoldandroids.util.FloatProperty
        public void setValue(View object, float value) {
            AnimatorProxy.wrap(object).setY(value);
        }

        @Override // com.nineoldandroids.util.Property
        public Float get(View object) {
            return Float.valueOf(AnimatorProxy.wrap(object).getY());
        }
    };

    private PreHoneycombCompat() {
    }
}
