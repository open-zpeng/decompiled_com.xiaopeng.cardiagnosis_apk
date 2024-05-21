package com.nineoldandroids.view;

import android.view.View;
import android.view.animation.Interpolator;
import com.nineoldandroids.animation.Animator;
import com.nineoldandroids.animation.ValueAnimator;
import com.nineoldandroids.view.animation.AnimatorProxy;
import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Set;
/* JADX INFO: Access modifiers changed from: package-private */
/* loaded from: classes4.dex */
public class ViewPropertyAnimatorPreHC extends ViewPropertyAnimator {
    private static final int ALPHA = 512;
    private static final int NONE = 0;
    private static final int ROTATION = 16;
    private static final int ROTATION_X = 32;
    private static final int ROTATION_Y = 64;
    private static final int SCALE_X = 4;
    private static final int SCALE_Y = 8;
    private static final int TRANSFORM_MASK = 511;
    private static final int TRANSLATION_X = 1;
    private static final int TRANSLATION_Y = 2;
    private static final int X = 128;
    private static final int Y = 256;
    private long mDuration;
    private Interpolator mInterpolator;
    private final AnimatorProxy mProxy;
    private final WeakReference<View> mView;
    private boolean mDurationSet = false;
    private long mStartDelay = 0;
    private boolean mStartDelaySet = false;
    private boolean mInterpolatorSet = false;
    private Animator.AnimatorListener mListener = null;
    private AnimatorEventListener mAnimatorEventListener = new AnimatorEventListener();
    ArrayList<NameValuesHolder> mPendingAnimations = new ArrayList<>();
    private Runnable mAnimationStarter = new Runnable() { // from class: com.nineoldandroids.view.ViewPropertyAnimatorPreHC.1
        @Override // java.lang.Runnable
        public void run() {
            ViewPropertyAnimatorPreHC.this.startAnimation();
        }
    };
    private HashMap<Animator, PropertyBundle> mAnimatorMap = new HashMap<>();

    /* JADX INFO: Access modifiers changed from: private */
    /* loaded from: classes4.dex */
    public static class PropertyBundle {
        ArrayList<NameValuesHolder> mNameValuesHolder;
        int mPropertyMask;

        PropertyBundle(int propertyMask, ArrayList<NameValuesHolder> nameValuesHolder) {
            this.mPropertyMask = propertyMask;
            this.mNameValuesHolder = nameValuesHolder;
        }

        boolean cancel(int propertyConstant) {
            ArrayList<NameValuesHolder> arrayList;
            if ((this.mPropertyMask & propertyConstant) != 0 && (arrayList = this.mNameValuesHolder) != null) {
                int count = arrayList.size();
                for (int i = 0; i < count; i++) {
                    NameValuesHolder nameValuesHolder = this.mNameValuesHolder.get(i);
                    if (nameValuesHolder.mNameConstant == propertyConstant) {
                        this.mNameValuesHolder.remove(i);
                        this.mPropertyMask &= ~propertyConstant;
                        return true;
                    }
                }
                return false;
            }
            return false;
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* loaded from: classes4.dex */
    public static class NameValuesHolder {
        float mDeltaValue;
        float mFromValue;
        int mNameConstant;

        NameValuesHolder(int nameConstant, float fromValue, float deltaValue) {
            this.mNameConstant = nameConstant;
            this.mFromValue = fromValue;
            this.mDeltaValue = deltaValue;
        }
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public ViewPropertyAnimatorPreHC(View view) {
        this.mView = new WeakReference<>(view);
        this.mProxy = AnimatorProxy.wrap(view);
    }

    @Override // com.nineoldandroids.view.ViewPropertyAnimator
    public ViewPropertyAnimator setDuration(long duration) {
        if (duration < 0) {
            throw new IllegalArgumentException("Animators cannot have negative duration: " + duration);
        }
        this.mDurationSet = true;
        this.mDuration = duration;
        return this;
    }

    @Override // com.nineoldandroids.view.ViewPropertyAnimator
    public long getDuration() {
        if (this.mDurationSet) {
            return this.mDuration;
        }
        return new ValueAnimator().getDuration();
    }

    @Override // com.nineoldandroids.view.ViewPropertyAnimator
    public long getStartDelay() {
        if (this.mStartDelaySet) {
            return this.mStartDelay;
        }
        return 0L;
    }

    @Override // com.nineoldandroids.view.ViewPropertyAnimator
    public ViewPropertyAnimator setStartDelay(long startDelay) {
        if (startDelay < 0) {
            throw new IllegalArgumentException("Animators cannot have negative duration: " + startDelay);
        }
        this.mStartDelaySet = true;
        this.mStartDelay = startDelay;
        return this;
    }

    @Override // com.nineoldandroids.view.ViewPropertyAnimator
    public ViewPropertyAnimator setInterpolator(Interpolator interpolator) {
        this.mInterpolatorSet = true;
        this.mInterpolator = interpolator;
        return this;
    }

    @Override // com.nineoldandroids.view.ViewPropertyAnimator
    public ViewPropertyAnimator setListener(Animator.AnimatorListener listener) {
        this.mListener = listener;
        return this;
    }

    @Override // com.nineoldandroids.view.ViewPropertyAnimator
    public void start() {
        startAnimation();
    }

    @Override // com.nineoldandroids.view.ViewPropertyAnimator
    public void cancel() {
        if (this.mAnimatorMap.size() > 0) {
            HashMap<Animator, PropertyBundle> mAnimatorMapCopy = (HashMap) this.mAnimatorMap.clone();
            Set<Animator> animatorSet = mAnimatorMapCopy.keySet();
            for (Animator runningAnim : animatorSet) {
                runningAnim.cancel();
            }
        }
        this.mPendingAnimations.clear();
        View v = this.mView.get();
        if (v != null) {
            v.removeCallbacks(this.mAnimationStarter);
        }
    }

    @Override // com.nineoldandroids.view.ViewPropertyAnimator
    public ViewPropertyAnimator x(float value) {
        animateProperty(128, value);
        return this;
    }

    @Override // com.nineoldandroids.view.ViewPropertyAnimator
    public ViewPropertyAnimator xBy(float value) {
        animatePropertyBy(128, value);
        return this;
    }

    @Override // com.nineoldandroids.view.ViewPropertyAnimator
    public ViewPropertyAnimator y(float value) {
        animateProperty(256, value);
        return this;
    }

    @Override // com.nineoldandroids.view.ViewPropertyAnimator
    public ViewPropertyAnimator yBy(float value) {
        animatePropertyBy(256, value);
        return this;
    }

    @Override // com.nineoldandroids.view.ViewPropertyAnimator
    public ViewPropertyAnimator rotation(float value) {
        animateProperty(16, value);
        return this;
    }

    @Override // com.nineoldandroids.view.ViewPropertyAnimator
    public ViewPropertyAnimator rotationBy(float value) {
        animatePropertyBy(16, value);
        return this;
    }

    @Override // com.nineoldandroids.view.ViewPropertyAnimator
    public ViewPropertyAnimator rotationX(float value) {
        animateProperty(32, value);
        return this;
    }

    @Override // com.nineoldandroids.view.ViewPropertyAnimator
    public ViewPropertyAnimator rotationXBy(float value) {
        animatePropertyBy(32, value);
        return this;
    }

    @Override // com.nineoldandroids.view.ViewPropertyAnimator
    public ViewPropertyAnimator rotationY(float value) {
        animateProperty(64, value);
        return this;
    }

    @Override // com.nineoldandroids.view.ViewPropertyAnimator
    public ViewPropertyAnimator rotationYBy(float value) {
        animatePropertyBy(64, value);
        return this;
    }

    @Override // com.nineoldandroids.view.ViewPropertyAnimator
    public ViewPropertyAnimator translationX(float value) {
        animateProperty(1, value);
        return this;
    }

    @Override // com.nineoldandroids.view.ViewPropertyAnimator
    public ViewPropertyAnimator translationXBy(float value) {
        animatePropertyBy(1, value);
        return this;
    }

    @Override // com.nineoldandroids.view.ViewPropertyAnimator
    public ViewPropertyAnimator translationY(float value) {
        animateProperty(2, value);
        return this;
    }

    @Override // com.nineoldandroids.view.ViewPropertyAnimator
    public ViewPropertyAnimator translationYBy(float value) {
        animatePropertyBy(2, value);
        return this;
    }

    @Override // com.nineoldandroids.view.ViewPropertyAnimator
    public ViewPropertyAnimator scaleX(float value) {
        animateProperty(4, value);
        return this;
    }

    @Override // com.nineoldandroids.view.ViewPropertyAnimator
    public ViewPropertyAnimator scaleXBy(float value) {
        animatePropertyBy(4, value);
        return this;
    }

    @Override // com.nineoldandroids.view.ViewPropertyAnimator
    public ViewPropertyAnimator scaleY(float value) {
        animateProperty(8, value);
        return this;
    }

    @Override // com.nineoldandroids.view.ViewPropertyAnimator
    public ViewPropertyAnimator scaleYBy(float value) {
        animatePropertyBy(8, value);
        return this;
    }

    @Override // com.nineoldandroids.view.ViewPropertyAnimator
    public ViewPropertyAnimator alpha(float value) {
        animateProperty(512, value);
        return this;
    }

    @Override // com.nineoldandroids.view.ViewPropertyAnimator
    public ViewPropertyAnimator alphaBy(float value) {
        animatePropertyBy(512, value);
        return this;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void startAnimation() {
        ValueAnimator animator = ValueAnimator.ofFloat(1.0f);
        ArrayList<NameValuesHolder> nameValueList = (ArrayList) this.mPendingAnimations.clone();
        this.mPendingAnimations.clear();
        int propertyMask = 0;
        int propertyCount = nameValueList.size();
        for (int i = 0; i < propertyCount; i++) {
            NameValuesHolder nameValuesHolder = nameValueList.get(i);
            propertyMask |= nameValuesHolder.mNameConstant;
        }
        this.mAnimatorMap.put(animator, new PropertyBundle(propertyMask, nameValueList));
        animator.addUpdateListener(this.mAnimatorEventListener);
        animator.addListener(this.mAnimatorEventListener);
        if (this.mStartDelaySet) {
            animator.setStartDelay(this.mStartDelay);
        }
        if (this.mDurationSet) {
            animator.setDuration(this.mDuration);
        }
        if (this.mInterpolatorSet) {
            animator.setInterpolator(this.mInterpolator);
        }
        animator.start();
    }

    private void animateProperty(int constantName, float toValue) {
        float fromValue = getValue(constantName);
        float deltaValue = toValue - fromValue;
        animatePropertyBy(constantName, fromValue, deltaValue);
    }

    private void animatePropertyBy(int constantName, float byValue) {
        float fromValue = getValue(constantName);
        animatePropertyBy(constantName, fromValue, byValue);
    }

    private void animatePropertyBy(int constantName, float startValue, float byValue) {
        if (this.mAnimatorMap.size() > 0) {
            Animator animatorToCancel = null;
            Set<Animator> animatorSet = this.mAnimatorMap.keySet();
            Iterator i$ = animatorSet.iterator();
            while (true) {
                if (!i$.hasNext()) {
                    break;
                }
                Animator runningAnim = i$.next();
                PropertyBundle bundle = this.mAnimatorMap.get(runningAnim);
                if (bundle.cancel(constantName) && bundle.mPropertyMask == 0) {
                    animatorToCancel = runningAnim;
                    break;
                }
            }
            if (animatorToCancel != null) {
                animatorToCancel.cancel();
            }
        }
        NameValuesHolder nameValuePair = new NameValuesHolder(constantName, startValue, byValue);
        this.mPendingAnimations.add(nameValuePair);
        View v = this.mView.get();
        if (v != null) {
            v.removeCallbacks(this.mAnimationStarter);
            v.post(this.mAnimationStarter);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void setValue(int propertyConstant, float value) {
        if (propertyConstant == 1) {
            this.mProxy.setTranslationX(value);
        } else if (propertyConstant == 2) {
            this.mProxy.setTranslationY(value);
        } else if (propertyConstant == 4) {
            this.mProxy.setScaleX(value);
        } else if (propertyConstant == 8) {
            this.mProxy.setScaleY(value);
        } else if (propertyConstant == 16) {
            this.mProxy.setRotation(value);
        } else if (propertyConstant == 32) {
            this.mProxy.setRotationX(value);
        } else if (propertyConstant == 64) {
            this.mProxy.setRotationY(value);
        } else if (propertyConstant == 128) {
            this.mProxy.setX(value);
        } else if (propertyConstant == 256) {
            this.mProxy.setY(value);
        } else if (propertyConstant == 512) {
            this.mProxy.setAlpha(value);
        }
    }

    private float getValue(int propertyConstant) {
        if (propertyConstant != 1) {
            if (propertyConstant != 2) {
                if (propertyConstant != 4) {
                    if (propertyConstant != 8) {
                        if (propertyConstant != 16) {
                            if (propertyConstant != 32) {
                                if (propertyConstant != 64) {
                                    if (propertyConstant != 128) {
                                        if (propertyConstant != 256) {
                                            if (propertyConstant == 512) {
                                                return this.mProxy.getAlpha();
                                            }
                                            return 0.0f;
                                        }
                                        return this.mProxy.getY();
                                    }
                                    return this.mProxy.getX();
                                }
                                return this.mProxy.getRotationY();
                            }
                            return this.mProxy.getRotationX();
                        }
                        return this.mProxy.getRotation();
                    }
                    return this.mProxy.getScaleY();
                }
                return this.mProxy.getScaleX();
            }
            return this.mProxy.getTranslationY();
        }
        return this.mProxy.getTranslationX();
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* loaded from: classes4.dex */
    public class AnimatorEventListener implements Animator.AnimatorListener, ValueAnimator.AnimatorUpdateListener {
        private AnimatorEventListener() {
        }

        @Override // com.nineoldandroids.animation.Animator.AnimatorListener
        public void onAnimationStart(Animator animation) {
            if (ViewPropertyAnimatorPreHC.this.mListener != null) {
                ViewPropertyAnimatorPreHC.this.mListener.onAnimationStart(animation);
            }
        }

        @Override // com.nineoldandroids.animation.Animator.AnimatorListener
        public void onAnimationCancel(Animator animation) {
            if (ViewPropertyAnimatorPreHC.this.mListener != null) {
                ViewPropertyAnimatorPreHC.this.mListener.onAnimationCancel(animation);
            }
        }

        @Override // com.nineoldandroids.animation.Animator.AnimatorListener
        public void onAnimationRepeat(Animator animation) {
            if (ViewPropertyAnimatorPreHC.this.mListener != null) {
                ViewPropertyAnimatorPreHC.this.mListener.onAnimationRepeat(animation);
            }
        }

        @Override // com.nineoldandroids.animation.Animator.AnimatorListener
        public void onAnimationEnd(Animator animation) {
            if (ViewPropertyAnimatorPreHC.this.mListener != null) {
                ViewPropertyAnimatorPreHC.this.mListener.onAnimationEnd(animation);
            }
            ViewPropertyAnimatorPreHC.this.mAnimatorMap.remove(animation);
            if (ViewPropertyAnimatorPreHC.this.mAnimatorMap.isEmpty()) {
                ViewPropertyAnimatorPreHC.this.mListener = null;
            }
        }

        @Override // com.nineoldandroids.animation.ValueAnimator.AnimatorUpdateListener
        public void onAnimationUpdate(ValueAnimator animation) {
            View v;
            float fraction = animation.getAnimatedFraction();
            PropertyBundle propertyBundle = (PropertyBundle) ViewPropertyAnimatorPreHC.this.mAnimatorMap.get(animation);
            int propertyMask = propertyBundle.mPropertyMask;
            if ((propertyMask & 511) != 0 && (v = (View) ViewPropertyAnimatorPreHC.this.mView.get()) != null) {
                v.invalidate();
            }
            ArrayList<NameValuesHolder> valueList = propertyBundle.mNameValuesHolder;
            if (valueList != null) {
                int count = valueList.size();
                for (int i = 0; i < count; i++) {
                    NameValuesHolder values = valueList.get(i);
                    float value = values.mFromValue + (values.mDeltaValue * fraction);
                    ViewPropertyAnimatorPreHC.this.setValue(values.mNameConstant, value);
                }
            }
            View v2 = (View) ViewPropertyAnimatorPreHC.this.mView.get();
            if (v2 != null) {
                v2.invalidate();
            }
        }
    }
}
