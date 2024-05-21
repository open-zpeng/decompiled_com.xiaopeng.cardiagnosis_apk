package com.nineoldandroids.view;

import android.animation.Animator;
import android.view.View;
import android.view.animation.Interpolator;
import com.nineoldandroids.animation.Animator;
import java.lang.ref.WeakReference;
/* loaded from: classes4.dex */
class ViewPropertyAnimatorICS extends ViewPropertyAnimator {
    private static final long RETURN_WHEN_NULL = -1;
    private final WeakReference<android.view.ViewPropertyAnimator> mNative;

    /* JADX INFO: Access modifiers changed from: package-private */
    public ViewPropertyAnimatorICS(View view) {
        this.mNative = new WeakReference<>(view.animate());
    }

    @Override // com.nineoldandroids.view.ViewPropertyAnimator
    public ViewPropertyAnimator setDuration(long duration) {
        android.view.ViewPropertyAnimator n = this.mNative.get();
        if (n != null) {
            n.setDuration(duration);
        }
        return this;
    }

    @Override // com.nineoldandroids.view.ViewPropertyAnimator
    public long getDuration() {
        android.view.ViewPropertyAnimator n = this.mNative.get();
        if (n != null) {
            return n.getDuration();
        }
        return -1L;
    }

    @Override // com.nineoldandroids.view.ViewPropertyAnimator
    public ViewPropertyAnimator setStartDelay(long startDelay) {
        android.view.ViewPropertyAnimator n = this.mNative.get();
        if (n != null) {
            n.setStartDelay(startDelay);
        }
        return this;
    }

    @Override // com.nineoldandroids.view.ViewPropertyAnimator
    public long getStartDelay() {
        android.view.ViewPropertyAnimator n = this.mNative.get();
        if (n != null) {
            return n.getStartDelay();
        }
        return -1L;
    }

    @Override // com.nineoldandroids.view.ViewPropertyAnimator
    public ViewPropertyAnimator setInterpolator(Interpolator interpolator) {
        android.view.ViewPropertyAnimator n = this.mNative.get();
        if (n != null) {
            n.setInterpolator(interpolator);
        }
        return this;
    }

    @Override // com.nineoldandroids.view.ViewPropertyAnimator
    public ViewPropertyAnimator setListener(final Animator.AnimatorListener listener) {
        android.view.ViewPropertyAnimator n = this.mNative.get();
        if (n != null) {
            if (listener == null) {
                n.setListener(null);
            } else {
                n.setListener(new Animator.AnimatorListener() { // from class: com.nineoldandroids.view.ViewPropertyAnimatorICS.1
                    @Override // android.animation.Animator.AnimatorListener
                    public void onAnimationStart(android.animation.Animator animation) {
                        listener.onAnimationStart(null);
                    }

                    @Override // android.animation.Animator.AnimatorListener
                    public void onAnimationRepeat(android.animation.Animator animation) {
                        listener.onAnimationRepeat(null);
                    }

                    @Override // android.animation.Animator.AnimatorListener
                    public void onAnimationEnd(android.animation.Animator animation) {
                        listener.onAnimationEnd(null);
                    }

                    @Override // android.animation.Animator.AnimatorListener
                    public void onAnimationCancel(android.animation.Animator animation) {
                        listener.onAnimationCancel(null);
                    }
                });
            }
        }
        return this;
    }

    @Override // com.nineoldandroids.view.ViewPropertyAnimator
    public void start() {
        android.view.ViewPropertyAnimator n = this.mNative.get();
        if (n != null) {
            n.start();
        }
    }

    @Override // com.nineoldandroids.view.ViewPropertyAnimator
    public void cancel() {
        android.view.ViewPropertyAnimator n = this.mNative.get();
        if (n != null) {
            n.cancel();
        }
    }

    @Override // com.nineoldandroids.view.ViewPropertyAnimator
    public ViewPropertyAnimator x(float value) {
        android.view.ViewPropertyAnimator n = this.mNative.get();
        if (n != null) {
            n.x(value);
        }
        return this;
    }

    @Override // com.nineoldandroids.view.ViewPropertyAnimator
    public ViewPropertyAnimator xBy(float value) {
        android.view.ViewPropertyAnimator n = this.mNative.get();
        if (n != null) {
            n.xBy(value);
        }
        return this;
    }

    @Override // com.nineoldandroids.view.ViewPropertyAnimator
    public ViewPropertyAnimator y(float value) {
        android.view.ViewPropertyAnimator n = this.mNative.get();
        if (n != null) {
            n.y(value);
        }
        return this;
    }

    @Override // com.nineoldandroids.view.ViewPropertyAnimator
    public ViewPropertyAnimator yBy(float value) {
        android.view.ViewPropertyAnimator n = this.mNative.get();
        if (n != null) {
            n.yBy(value);
        }
        return this;
    }

    @Override // com.nineoldandroids.view.ViewPropertyAnimator
    public ViewPropertyAnimator rotation(float value) {
        android.view.ViewPropertyAnimator n = this.mNative.get();
        if (n != null) {
            n.rotation(value);
        }
        return this;
    }

    @Override // com.nineoldandroids.view.ViewPropertyAnimator
    public ViewPropertyAnimator rotationBy(float value) {
        android.view.ViewPropertyAnimator n = this.mNative.get();
        if (n != null) {
            n.rotationBy(value);
        }
        return this;
    }

    @Override // com.nineoldandroids.view.ViewPropertyAnimator
    public ViewPropertyAnimator rotationX(float value) {
        android.view.ViewPropertyAnimator n = this.mNative.get();
        if (n != null) {
            n.rotationX(value);
        }
        return this;
    }

    @Override // com.nineoldandroids.view.ViewPropertyAnimator
    public ViewPropertyAnimator rotationXBy(float value) {
        android.view.ViewPropertyAnimator n = this.mNative.get();
        if (n != null) {
            n.rotationXBy(value);
        }
        return this;
    }

    @Override // com.nineoldandroids.view.ViewPropertyAnimator
    public ViewPropertyAnimator rotationY(float value) {
        android.view.ViewPropertyAnimator n = this.mNative.get();
        if (n != null) {
            n.rotationY(value);
        }
        return this;
    }

    @Override // com.nineoldandroids.view.ViewPropertyAnimator
    public ViewPropertyAnimator rotationYBy(float value) {
        android.view.ViewPropertyAnimator n = this.mNative.get();
        if (n != null) {
            n.rotationYBy(value);
        }
        return this;
    }

    @Override // com.nineoldandroids.view.ViewPropertyAnimator
    public ViewPropertyAnimator translationX(float value) {
        android.view.ViewPropertyAnimator n = this.mNative.get();
        if (n != null) {
            n.translationX(value);
        }
        return this;
    }

    @Override // com.nineoldandroids.view.ViewPropertyAnimator
    public ViewPropertyAnimator translationXBy(float value) {
        android.view.ViewPropertyAnimator n = this.mNative.get();
        if (n != null) {
            n.translationXBy(value);
        }
        return this;
    }

    @Override // com.nineoldandroids.view.ViewPropertyAnimator
    public ViewPropertyAnimator translationY(float value) {
        android.view.ViewPropertyAnimator n = this.mNative.get();
        if (n != null) {
            n.translationY(value);
        }
        return this;
    }

    @Override // com.nineoldandroids.view.ViewPropertyAnimator
    public ViewPropertyAnimator translationYBy(float value) {
        android.view.ViewPropertyAnimator n = this.mNative.get();
        if (n != null) {
            n.translationYBy(value);
        }
        return this;
    }

    @Override // com.nineoldandroids.view.ViewPropertyAnimator
    public ViewPropertyAnimator scaleX(float value) {
        android.view.ViewPropertyAnimator n = this.mNative.get();
        if (n != null) {
            n.scaleX(value);
        }
        return this;
    }

    @Override // com.nineoldandroids.view.ViewPropertyAnimator
    public ViewPropertyAnimator scaleXBy(float value) {
        android.view.ViewPropertyAnimator n = this.mNative.get();
        if (n != null) {
            n.scaleXBy(value);
        }
        return this;
    }

    @Override // com.nineoldandroids.view.ViewPropertyAnimator
    public ViewPropertyAnimator scaleY(float value) {
        android.view.ViewPropertyAnimator n = this.mNative.get();
        if (n != null) {
            n.scaleY(value);
        }
        return this;
    }

    @Override // com.nineoldandroids.view.ViewPropertyAnimator
    public ViewPropertyAnimator scaleYBy(float value) {
        android.view.ViewPropertyAnimator n = this.mNative.get();
        if (n != null) {
            n.scaleYBy(value);
        }
        return this;
    }

    @Override // com.nineoldandroids.view.ViewPropertyAnimator
    public ViewPropertyAnimator alpha(float value) {
        android.view.ViewPropertyAnimator n = this.mNative.get();
        if (n != null) {
            n.alpha(value);
        }
        return this;
    }

    @Override // com.nineoldandroids.view.ViewPropertyAnimator
    public ViewPropertyAnimator alphaBy(float value) {
        android.view.ViewPropertyAnimator n = this.mNative.get();
        if (n != null) {
            n.alphaBy(value);
        }
        return this;
    }
}
