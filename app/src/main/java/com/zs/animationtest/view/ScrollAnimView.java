package com.zs.animationtest.view;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.widget.FrameLayout;

import com.zs.animationtest.util.SystemTool;

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
    private long mEnterDuration = 4000;
    /**
     * 出场时间
     */
    private long mOutDuration = 3000;

    /**
     * 滚动正在执行
     */
    private boolean mAnimScrolling;

    private int width;
    private int height;

    private OuterListener outListener;

    public ScrollAnimView(Context context) {
        this(context, null);
    }

    public ScrollAnimView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public ScrollAnimView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initEnterOutAnim();
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        width = w;
        height = h;
    }

    /**
     * 初始化进场和出场动画
     */
    public void initEnterOutAnim() {
        final int screenWidth = SystemTool.getScreenWidth();
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
            mOutObjAnim.setStartDelay(1000);
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

            mOutObjAnim.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
                @Override
                public void onAnimationUpdate(ValueAnimator valueAnimator) {
                    float value = (float) valueAnimator.getAnimatedValue();
                    if (outListener != null) {
                        outListener.backValue(- value / screenWidth);
                    }
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

    public void setOutListener(OuterListener listener) {
        this.outListener = listener;
    }

    public interface OuterListener{
        void backValue(float value);
    }

}
