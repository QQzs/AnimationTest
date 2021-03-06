package com.zs.animationtest.view.particle.driftstar;

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
public class DriftStarDraw extends IParticleDraw<DriftStarBean> {

    private final int ADD_EFFECT_DRIFT_STAR = 2002;

    private class EffectHandler extends Handler {

        public EffectHandler(Looper looper) {
            super(looper);
        }

        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case ADD_EFFECT_DRIFT_STAR:
                    addParticle();
                    break;
            }
        }
    }

    public DriftStarDraw(Context context, int resourceId, int maxNum) {
        this(context, resourceId, maxNum, 0);
    }

    public DriftStarDraw(Context context, int resourceId, int maxNum, int delay) {
        super(context, resourceId, maxNum, delay);
    }

    @Override
    public void initEffectBitmaps() {
//        mBitmapsList.add(ParticleView.getScaleBitmap(originBitmap, 1f, 0f));
        mBitmapsList.add(ParticleView.getScaleBitmap(originBitmap, 0.3f, 0f));
        mBitmapsList.add(ParticleView.getScaleBitmap(originBitmap, 0.4f, 0f));
        mBitmapsList.add(ParticleView.getScaleBitmap(originBitmap, 0.5f, 0f));
    }

    @Override
    public int getMessage() {
        return ADD_EFFECT_DRIFT_STAR;
    }

    @Override
    public Handler getEffectHandler() {
        return new EffectHandler(mHandlerThread.getLooper());
    }

    @Override
    public DriftStarBean getParticle() {
        return new DriftStarBean(mViewWidth, mViewHeight, mBitmapsList);
    }

}
