package com.zs.animationtest.view;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;

import com.zs.animationtest.util.SystemTool;

import java.util.Random;

/**
 * @Author: zs
 * @Date: 2020/11/23 12:01 PM
 * @Description:
 */
public class ScrollAnimView extends FrameLayout {

    /**
     * 进场动画
     */
    private ObjectAnimator mEnterObjAnim;

    /**
     * 出场动画
     */
    private ObjectAnimator mOutObjAnim;

    /**
     * 进场时间
     */
    private long mEnterDuration = 2000;
    /**
     * 出场时间
     */
    private long mOutDuration = 2000;

    /**
     * 滚动正在执行
     */
    private boolean mAnimScrolling;

    int width;
    int height;

    int randomX = 0;
    int randomY = 0;
    LayoutParams params;

    private Random mRandom;

    public ScrollAnimView(Context context) {
        this(context, null);
    }

    public ScrollAnimView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public ScrollAnimView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initEnterOutAnim();
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

        Log.d("zhang","width = " + width + " height = " + height);

    }

    /**
     * 初始化进场和出场动画
     */
    public void initEnterOutAnim() {
        int screenWidth = SystemTool.getScreenWidth();
        if (mEnterObjAnim == null) {
            mEnterObjAnim = ObjectAnimator.ofFloat(this, "translationX", screenWidth, 0f);
            mEnterObjAnim.setDuration(mEnterDuration);
            mEnterObjAnim.setInterpolator(null);
            mEnterObjAnim.addListener(new AnimatorListenerAdapter() {

                @Override
                public void onAnimationStart(Animator animation) {
                    super.onAnimationStart(animation);
                    mAnimScrolling = true;
                }

                @Override
                public void onAnimationEnd(Animator animation) {
                    super.onAnimationEnd(animation);
                    mOutObjAnim.start();
                }
            });
        }

        if (mOutObjAnim == null) {
            mOutObjAnim = ObjectAnimator.ofFloat(this, "translationX", 0f, -screenWidth);
            mOutObjAnim.setDuration(mOutDuration);
            mOutObjAnim.setInterpolator(null);
            mOutObjAnim.addListener(new AnimatorListenerAdapter() {
                @Override
                public void onAnimationEnd(Animator animation) {
                    super.onAnimationEnd(animation);
                    // 动画结束
                    mAnimScrolling = false;
                    setVisibility(View.INVISIBLE);

                }
            });
        }
    }

    public void startScroll() {
        if (mEnterObjAnim == null || mOutObjAnim == null) {
            initEnterOutAnim();
        }
        if (mAnimScrolling) {
            return;
        }
        setVisibility(View.VISIBLE);
        mEnterObjAnim.start();
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
