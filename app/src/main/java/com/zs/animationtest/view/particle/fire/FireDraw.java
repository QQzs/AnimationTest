package com.zs.animationtest.view.particle.fire;

import android.content.Context;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;

import com.zs.animationtest.view.particle.IParticleDraw;
import com.zs.animationtest.view.particle.ParticleView;

/**
 * @Author: zs
 * @Date: 2020/11/24 8:05 PM
 * @Description:
 */
public class FireDraw extends IParticleDraw<FireBean> {

    private final int ADD_EFFECT_STAR = 1000;

    private class EffectHandler extends Handler {

        public EffectHandler(Looper looper) {
            super(looper);
        }

        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case ADD_EFFECT_STAR:
                    addEffectBean();
                    break;
            }
        }
    }

    public FireDraw(Context context, int resourceId, int maxNum) {
        this(context, resourceId, maxNum, 0);
    }

    public FireDraw(Context context, int resourceId, int maxNum, int delay) {
        super(context, resourceId, maxNum, delay);
    }

    @Override
    public int getMessage() {
        return ADD_EFFECT_STAR;
    }

    @Override
    public Handler getEffectHandler() {
        return new EffectHandler(mHandlerThread.getLooper());
    }

    @Override
    public void initEffectBitmaps() {
        mBitmapsList.add(ParticleView.getScaleBitmap(originBitmap, 0.5f, 0f));
        mBitmapsList.add(ParticleView.getScaleBitmap(originBitmap, 0.6f, 0f));
        mBitmapsList.add(ParticleView.getScaleBitmap(originBitmap, 0.7f, 0f));
    }

    @Override
    public void addEffectBean() {
        if (effectBeanList != null && effectBeanList.size() < maxNum) {
            effectBeanList.add(new FireBean(mViewWidth, mViewHeight, mBitmapsList));
            if (mEffectHandler != null) {
                if (delay > 0) {
                    mEffectHandler.sendEmptyMessageDelayed(getMessage(), mRandom.nextInt(delay));
                } else {
                    mEffectHandler.sendEmptyMessage(getMessage());
                }
            }
        } else {
            if (mEffectHandler != null) {
                mEffectHandler.removeCallbacksAndMessages(null);
            }
            mEffectHandler = null;
            mHandlerThread.quit();
        }
    }
}
