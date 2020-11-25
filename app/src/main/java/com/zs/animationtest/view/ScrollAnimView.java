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

    private LayoutParams params;

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
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        width = w;
        height = h;
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

}
