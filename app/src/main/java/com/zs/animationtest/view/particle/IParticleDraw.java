package com.zs.animationtest.view.particle;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.os.Handler;
import android.os.HandlerThread;

import java.util.ArrayList;
import java.util.Random;

/**
 * Created by klaus on 2018/8/2.
 */
public abstract class IParticleDraw<T extends IParticleBean> {

    protected int mViewHeight;
    protected int mViewWidth;
    protected int maxNum;
    protected int delay;
    protected ArrayList<T> effectBeanList;
    protected Handler mEffectHandler;

    protected Random mRandom = new Random();

    protected Context mContext;

    protected final HandlerThread mHandlerThread;

    protected Bitmap originBitmap;
    protected ArrayList<Bitmap> mBitmapsList;

    public IParticleDraw(Context context, int resourceId, int maxNum, int delay) {
        mContext = context;
        this.maxNum = maxNum;
        this.delay = delay;
        if (resourceId != 0) {
            originBitmap = BitmapFactory.decodeResource(mContext.getResources(), resourceId, new BitmapFactory.Options());
        }
        mHandlerThread = new HandlerThread(getClass().getSimpleName());
        mHandlerThread.start();
        effectBeanList = new ArrayList<>();
        mEffectHandler = getEffectHandler();
        init();
    }

    private void init() {
        if (mBitmapsList == null) {
            mBitmapsList = new ArrayList<>();
        }
        mBitmapsList.clear();
        initEffectBitmaps();
        if (originBitmap != null) {
            originBitmap.recycle();
        }
    }

    /**
     * 开始创建粒子
     * @param width
     * @param height
     */
    public void createParticle(int width , int height) {
        this.mViewWidth = width;
        this.mViewHeight = height;
        if (mEffectHandler != null) {
            mEffectHandler.sendEmptyMessage(getMessage());
        }
    }

    /**
     * 初始化每个特效bitmap，添加进mBitmaps
     */
    public abstract void initEffectBitmaps();

    /**
     * 获取Handler message
     * @return
     */
    public abstract int getMessage();

    /**
     * 获取Handler
     * @return
     */
    public abstract Handler getEffectHandler();

    public abstract T getParticle();

    /**
     * 初始化每个特效bean，添加进mBitmapsList
     */
    public void addParticle(){
        if (effectBeanList != null && effectBeanList.size() < maxNum) {
            effectBeanList.add(getParticle());
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

    public ArrayList<T> getEffectBeanList() {
        return effectBeanList;
    }

    public void setEffectBeanList(ArrayList<T> effectBeanList) {
        this.effectBeanList = effectBeanList;
    }

    public void draw(Canvas canvas, Paint paint) {
        for (int i = 0; i < effectBeanList.size(); i++) {
            effectBeanList.get(i).drawNextFrame(canvas, paint);
        }
    }

    public synchronized void destroy() {
        if (mEffectHandler != null) {
            mEffectHandler.removeCallbacksAndMessages(null);
            mEffectHandler = null;
            mHandlerThread.quit();
        }
        if (effectBeanList != null && effectBeanList.size() > 0) {
            for (T bean : effectBeanList) {
                bean.destroy();
            }
        }
        if (mBitmapsList != null && mBitmapsList.size() > 0) {
            for (Bitmap bitmap : mBitmapsList) {
                if (bitmap != null && !bitmap.isRecycled()){
                    bitmap.recycle();
                }
            }
        }
    }
}
