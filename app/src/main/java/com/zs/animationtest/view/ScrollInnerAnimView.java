package com.zs.animationtest.view;

import android.app.Activity;
import android.content.Context;
import android.os.Handler;
import android.os.Message;
import android.util.AttributeSet;
import android.util.Log;
import android.view.ViewGroup;
import android.view.animation.DecelerateInterpolator;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;

import androidx.annotation.NonNull;

import com.plattysoft.leonids.ParticleSystem;
import com.zs.animationtest.R;

import java.util.Random;

/**
 * @Author: zs
 * @Date: 2020/11/23 12:01 PM
 * @Description:
 */
public class ScrollInnerAnimView extends LinearLayout {

    int width;
    int height;
    private Random mRandom;
    private LayoutParams params;

    private Handler handler = new Handler(){
        @Override
        public void handleMessage(@NonNull Message msg) {
            super.handleMessage(msg);
            if (msg.what == 1) {
                initFire();
                handler.sendEmptyMessageDelayed(1, 100);
            }
        }
    };

    public ScrollInnerAnimView(Context context) {
        this(context, null);
    }

    public ScrollInnerAnimView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public ScrollInnerAnimView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        mRandom = new Random();
        params = new LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        width = w;
        height = h;
    }

    public ImageView addFireView(int x, int y) {
        LayoutParams params = new LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        ImageView view = new ImageView(getContext());
        view.setX(x);
        view.setY(y);
        addView(view, params);
        return view;
    }

    public void initFire() {
        ImageView view = new ImageView(getContext());
        view.setX(mRandom.nextInt(width - 50));
        view.setY(mRandom.nextInt(height - 50));
        addView(view, params);
        startFireAnim(view);
    }

    public void startFire() {
        handler.sendEmptyMessageDelayed(1, 100);
    }

    private void startFireAnim(ImageView imageView) {
        Context context = getContext();
        if (context instanceof Activity) {
            Activity activity = (Activity) context;
            if (activity.isFinishing()) {
                return;
            }
            ParticleSystem particleSystem = new ParticleSystem(activity, 150, R.drawable.star_white, 400);
            // 设置大小范围
            particleSystem.setScaleRange(0.5f, 0.5f);
            // 设置速度范围和发射角度范围
            particleSystem.setSpeedModuleAndAngleRange(0.03f, 0.06f, 0, 360);
            particleSystem.setFadeOut(200, new DecelerateInterpolator());
            particleSystem.emit(imageView, 150, 400);


            ParticleSystem particleSystem2 = new ParticleSystem(activity, 70, R.drawable.star_white, 500);
            // 设置大小范围
            particleSystem2.setScaleRange(0.5f, 0.5f);
            // 设置速度范围和发射角度范围
            particleSystem2.setSpeedModuleAndAngleRange(0.05f, 0.05f, 0, 360);
            particleSystem2.setFadeOut(200, new DecelerateInterpolator());
            particleSystem2.oneShot(imageView, 60);
        }
    }

    @Override
    protected void onDetachedFromWindow() {
        super.onDetachedFromWindow();
        Log.d("zhang","ScrollInnerAnimView   onDetachedFromWindow");
        handler.removeCallbacksAndMessages(null);
    }
}
