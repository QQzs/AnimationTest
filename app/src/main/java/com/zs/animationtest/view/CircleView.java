package com.zs.animationtest.view;

import android.animation.ValueAnimator;
import android.content.Context;
import android.util.AttributeSet;
import android.widget.FrameLayout;
import android.widget.ImageView;

import androidx.annotation.Nullable;

import com.zs.animationtest.R;

/**
 * @Author: zs
 * @Date: 2020/11/24 3:44 PM
 * @Description:
 */
public class CircleView extends FrameLayout {

    private ImageView outer;
    private ImageView middle;
    private ImageView inner;

    private ValueAnimator animator;

    public CircleView(Context context) {
        this(context, null);
    }

    public CircleView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public CircleView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initView();
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);

    }

    private void initView() {
        outer = new ImageView(getContext());
        outer.setImageResource(R.drawable.c_bg);
        addView(outer);

        middle = new ImageView(getContext());
        middle.setImageResource(R.drawable.c_bg);
        middle.setScaleX(0.8f);
        middle.setScaleY(0.8f);
        middle.setRotation(30);
        addView(middle);

        inner = new ImageView(getContext());
        inner.setImageResource(R.drawable.c_bg);
        inner.setScaleX(0.6f);
        inner.setScaleY(0.6f);
        inner.setRotation(60);
        addView(inner);

    }

    public void startAnim() {
        animator = ValueAnimator.ofInt(0, 60, 0);
        animator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator valueAnimator) {
                int value = (int) valueAnimator.getAnimatedValue();
                outer.setRotation(value);
                middle.setRotation(30 - value);
                inner.setRotation(60 + value);
            }
        });
        animator.setDuration(1800);
        animator.setRepeatCount(8);
        animator.start();
    }

    public void stopAnim() {
        if (animator != null && animator.isRunning()) {
            animator.cancel();
        }
        animator = null;
    }

    @Override
    protected void onDetachedFromWindow() {
        super.onDetachedFromWindow();
        stopAnim();
    }
}
