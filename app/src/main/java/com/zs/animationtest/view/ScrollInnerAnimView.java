package com.zs.animationtest.view;

import android.app.Activity;
import android.content.Context;
import android.os.Handler;
import android.util.AttributeSet;
import android.view.ViewGroup;
import android.view.animation.DecelerateInterpolator;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.plattysoft.leonids.ParticleSystem;
import com.zs.animationtest.R;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * @Author: zs
 * @Date: 2020/11/23 12:01 PM
 * @Description:
 */
public class ScrollInnerAnimView extends LinearLayout {

    private int width;
    private int height;
    private float scrollValue;
    private Random random;
    private LayoutParams params;
    private List<ImageView> fireViews;

    private Handler handler;

    public ScrollInnerAnimView(Context context) {
        this(context, null);
    }

    public ScrollInnerAnimView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public ScrollInnerAnimView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        random = new Random();
        params = new LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        handler = new Handler();
        fireViews = new ArrayList<>();
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        width = w;
        height = h;
    }

    private void addFireView(int maxCount) {
        for (int i = 0; i < maxCount; i++) {
            ImageView view = new ImageView(getContext());
            view.setX(random.nextInt(width - 50));
            view.setY(random.nextInt(height));
            addView(view, params);
            fireViews.add(view);
        }
    }

    private void startFire() {
        int size = fireViews.size();
        if (size > 0) {
            for (int i = 0; i < size; i++) {
                ImageView view = fireViews.get(i);
                view.setX(random.nextInt(width - 50));
                view.setY(random.nextInt(height));
                startFireAnim(view);
            }
        }
    }

    public void startAnim(int maxCount) {
        scrollValue = 0f;
        fireViews.clear();
        addFireView(maxCount);
        addFireAnim();
    }

    private void addFireAnim() {
        if (handler != null) {
            handler.postDelayed(runnable , 500);
        }
    }

    public void setScrollStatus(float value) {
        scrollValue = value;
        if (value > 0.5) {
            if (handler != null) {
                handler.removeCallbacksAndMessages(null);
            }
        }
    }

    private Runnable runnable = new Runnable() {
        @Override
        public void run() {
            startFire();
            addFireAnim();
        }
    };

    private void startFireAnim(ImageView imageView) {
        Context context = getContext();
        if (context instanceof Activity) {
            Activity activity = (Activity) context;
            if (activity.isFinishing()) {
                return;
            }
            ParticleSystem particleSystem = new ParticleSystem(activity, 80, R.drawable.star_pink, 500);
            // 设置大小范围
            particleSystem.setScaleRange(0.5f, 0.5f);
            // 设置速度范围和发射角度范围
            particleSystem.setSpeedModuleAndAngleRange(0.04f, 0.06f, 0, 360);
            particleSystem.setFadeOut(300, new DecelerateInterpolator());
            particleSystem.emit(imageView, 150, 500);


            ParticleSystem particleSystem2 = new ParticleSystem(activity, 70, R.drawable.star_pink, 500);
            // 设置大小范围
            particleSystem2.setScaleRange(0.5f, 0.5f);
            // 设置速度范围和发射角度范围
            particleSystem2.setSpeedModuleAndAngleRange(0.05f, 0.05f, 0, 360);
            particleSystem2.setFadeOut(300, new DecelerateInterpolator());
            particleSystem2.oneShot(imageView, 40);
        }
    }

    public void stopAnim() {
        handler.removeCallbacksAndMessages(null);
        fireViews.clear();
    }

    @Override
    protected void onDetachedFromWindow() {
        super.onDetachedFromWindow();
        stopAnim();
    }
}
