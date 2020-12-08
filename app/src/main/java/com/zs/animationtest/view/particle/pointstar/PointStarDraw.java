package com.zs.animationtest.view.particle.pointstar;

import android.content.Context;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.util.Log;

import com.zs.animationtest.view.particle.IParticleDraw;
import com.zs.animationtest.view.particle.ParticleView;

/**
 * @Author: zs
 * @Date: 2020/11/24 8:05 PM
 * @Description:
 */
public class PointStarDraw extends IParticleDraw<PointStarBean> {

    private final int ADD_EFFECT_POINT_STAR = 2001;

    private class EffectHandler extends Handler {

        public EffectHandler(Looper looper) {
            super(looper);
        }

        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case ADD_EFFECT_POINT_STAR:
                    addParticle();
                    break;
            }
        }
    }

    public PointStarDraw(Context context, int resourceId, int maxNum) {
        this(context, resourceId, maxNum, 0);
    }

    public PointStarDraw(Context context, int resourceId, int maxNum, int delay) {
        super(context, resourceId, maxNum, delay);
    }

    @Override
    public int getMessage() {
        return ADD_EFFECT_POINT_STAR;
    }

    @Override
    public Handler getEffectHandler() {
        return new EffectHandler(mHandlerThread.getLooper());
    }

    @Override
    public void initEffectBitmaps() {
        mBitmapsList.add(ParticleView.getScaleBitmap(originBitmap, 0.1f, 0f));
        mBitmapsList.add(ParticleView.getScaleBitmap(originBitmap, 0.2f, 0f));
        mBitmapsList.add(ParticleView.getScaleBitmap(originBitmap, 0.3f, 0f));
    }

    @Override
    public PointStarBean getParticle() {
        return new PointStarBean(mViewWidth, mViewHeight, mBitmapsList);
    }

}
