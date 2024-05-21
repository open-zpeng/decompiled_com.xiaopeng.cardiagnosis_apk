package com.nineoldandroids.animation;
/* loaded from: classes4.dex */
public class TimeAnimator extends ValueAnimator {
    private TimeListener mListener;
    private long mPreviousTime = -1;

    /* loaded from: classes4.dex */
    public interface TimeListener {
        void onTimeUpdate(TimeAnimator timeAnimator, long j, long j2);
    }

    @Override // com.nineoldandroids.animation.ValueAnimator
    boolean animationFrame(long currentTime) {
        if (this.mPlayingState == 0) {
            this.mPlayingState = 1;
            if (this.mSeekTime < 0) {
                this.mStartTime = currentTime;
            } else {
                this.mStartTime = currentTime - this.mSeekTime;
                this.mSeekTime = -1L;
            }
        }
        if (this.mListener != null) {
            long totalTime = currentTime - this.mStartTime;
            long j = this.mPreviousTime;
            long deltaTime = j >= 0 ? currentTime - j : 0L;
            this.mPreviousTime = currentTime;
            this.mListener.onTimeUpdate(this, totalTime, deltaTime);
            return false;
        }
        return false;
    }

    public void setTimeListener(TimeListener listener) {
        this.mListener = listener;
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    @Override // com.nineoldandroids.animation.ValueAnimator
    public void animateValue(float fraction) {
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    @Override // com.nineoldandroids.animation.ValueAnimator
    public void initAnimation() {
    }
}
