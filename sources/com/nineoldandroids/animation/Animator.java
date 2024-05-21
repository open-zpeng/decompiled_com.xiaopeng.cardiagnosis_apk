package com.nineoldandroids.animation;

import android.view.animation.Interpolator;
import java.util.ArrayList;
/* loaded from: classes4.dex */
public abstract class Animator implements Cloneable {
    ArrayList<AnimatorListener> mListeners = null;

    /* loaded from: classes4.dex */
    public interface AnimatorListener {
        void onAnimationCancel(Animator animator);

        void onAnimationEnd(Animator animator);

        void onAnimationRepeat(Animator animator);

        void onAnimationStart(Animator animator);
    }

    public abstract long getDuration();

    public abstract long getStartDelay();

    public abstract boolean isRunning();

    public abstract Animator setDuration(long j);

    public abstract void setInterpolator(Interpolator interpolator);

    public abstract void setStartDelay(long j);

    public void start() {
    }

    public void cancel() {
    }

    public void end() {
    }

    public boolean isStarted() {
        return isRunning();
    }

    public void addListener(AnimatorListener listener) {
        if (this.mListeners == null) {
            this.mListeners = new ArrayList<>();
        }
        this.mListeners.add(listener);
    }

    public void removeListener(AnimatorListener listener) {
        ArrayList<AnimatorListener> arrayList = this.mListeners;
        if (arrayList == null) {
            return;
        }
        arrayList.remove(listener);
        if (this.mListeners.size() == 0) {
            this.mListeners = null;
        }
    }

    public ArrayList<AnimatorListener> getListeners() {
        return this.mListeners;
    }

    public void removeAllListeners() {
        ArrayList<AnimatorListener> arrayList = this.mListeners;
        if (arrayList != null) {
            arrayList.clear();
            this.mListeners = null;
        }
    }

    @Override // 
    /* renamed from: clone */
    public Animator mo100clone() {
        try {
            Animator anim = (Animator) super.clone();
            if (this.mListeners != null) {
                ArrayList<AnimatorListener> oldListeners = this.mListeners;
                anim.mListeners = new ArrayList<>();
                int numListeners = oldListeners.size();
                for (int i = 0; i < numListeners; i++) {
                    anim.mListeners.add(oldListeners.get(i));
                }
            }
            return anim;
        } catch (CloneNotSupportedException e) {
            throw new AssertionError();
        }
    }

    public void setupStartValues() {
    }

    public void setupEndValues() {
    }

    public void setTarget(Object target) {
    }
}
