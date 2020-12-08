package com.zs.animationtest.view.particle.star;

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
public class StarDraw extends IParticleDraw<StarBean> {

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
                    addParticle();
                    break;
            }
        }
    }

    public StarDraw(Context context, int resourceId, int maxNum) {
        this(context, resourceId, maxNum, 0);
    }

    public StarDraw(Context context, int resourceId, int maxNum, int delay) {
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
    public StarBean getParticle() {
        return new StarBean(mViewWidth, mViewHeight, mBitmapsList);
    }

}
