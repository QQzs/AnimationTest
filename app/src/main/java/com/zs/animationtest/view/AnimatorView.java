package com.zs.animationtest.view;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.AttributeSet;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;

import java.util.Random;

/**
 * @Author: zs
 * @Date: 2020/11/25 7:51 PM
 * @Description:
 */
class AnimatorView extends FrameLayout {

    private int width;
    private int height;
    private int randomX = 0;
    private int randomY = 0;

    private LayoutParams params;

    private Random mRandom;

    public AnimatorView(Context context) {
        this(context, null);
    }

    public AnimatorView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public AnimatorView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        params = new LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        mRandom = new Random();
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        width = w;
        height = h;

        randomX = width - height;
        randomY = height;
    }

    public void addAnimView(int drawable, int count) {
        Bitmap imageBitmap = BitmapFactory.decodeResource(getContext().getResources(), drawable);
        int imageHeight = imageBitmap.getHeight();
        for (int i = 0; i < count; i++) {
            ImageView view = new ImageView(getContext());
            view.setScaleX(0f);
            view.setScaleY(0f);
            int repeatCount = 10;
            int delay = mRandom.nextInt(1000);
            int x = mRandom.nextInt(randomX)  + height / 2;
            int y = mRandom.nextInt(randomY) - imageHeight / 2;

            view.setX(x);
            view.setY(y);
//            view.setImageResource(drawable);
            view.setImageBitmap(imageBitmap);
            addView(view, params);

            ObjectAnimator alpha = ObjectAnimator.ofFloat(view, "alpha", 0f, 1f, 0f);
            alpha.setRepeatCount(repeatCount);
            ObjectAnimator scaleX = ObjectAnimator.ofFloat(view, "scaleX", 0f, 0.5f, 0f);
            scaleX.setRepeatCount(repeatCount);
            ObjectAnimator scaleY = ObjectAnimator.ofFloat(view, "scaleY", 0f, 0.5f, 0f);
            scaleY.setRepeatCount(repeatCount);
            ObjectAnimator rotation = ObjectAnimator.ofFloat(view, "rotation", 0f, 360f);
            rotation.setRepeatCount(repeatCount);
            AnimatorSet animSet = new AnimatorSet();
            animSet.playTogether(alpha, scaleX, scaleY, rotation);
            animSet.setDuration(2000);
            animSet.setStartDelay(delay);
            animSet.start();
        }
    }

    public void addRotationView(int drawable, int count) {
        Bitmap imageBitmap = BitmapFactory.decodeResource(getContext().getResources(), drawable);
        int imageHeight = imageBitmap.getHeight();
        for (int i = 0; i < count; i++) {
            final ImageView view = new ImageView(getContext());
            view.setScaleX(0.2f);
            view.setScaleY(0.2f);
            int repeatCount = 10;
            int delay = mRandom.nextInt(1000);
            int x = mRandom.nextInt(randomX) + height / 2;
            int y = mRandom.nextInt(randomY) - imageHeight / 5;

            view.setX(x);
            view.setY(y);
//            view.setImageResource(drawable);
            view.setImageBitmap(imageBitmap);
            addView(view, params);

            ObjectAnimator rotation = ObjectAnimator.ofFloat(view, "rotation", 0f, 360f);
            rotation.setRepeatCount(repeatCount);
            rotation.setDuration(2000);
            rotation.setStartDelay(delay);
            rotation.start();
            rotation.addListener(new AnimatorListenerAdapter() {
                @Override
                public void onAnimationEnd(Animator animation) {
                    super.onAnimationEnd(animation);
                    view.setAlpha(0f);
                }
            });
        }
    }
}
