package com.zs.animationtest.view.particle;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.PixelFormat;
import android.graphics.PorterDuff;
import android.util.AttributeSet;
import android.view.Surface;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

import java.util.ArrayList;
import java.util.List;

/**
 * @Author: zs
 * @Date: 2020/11/24 7:56 PM
 * @Description:
 */
public class ParticleView extends SurfaceView implements SurfaceHolder.Callback {

    public static final String TAG = ParticleView.class.getSimpleName();

    private int mViewHeight;

    private int mViewWidth;

    private SurfaceHolder mSurfaceHolder;

    public static int frameRate = 50;

    private List<IParticleDraw> effectList;

    //绘制线程
    private DrawThread mDrawThread;

    public ParticleView(Context context) {
        this(context, null);
    }

    public ParticleView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public ParticleView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private void init() {
        effectList = new ArrayList<>();
        mSurfaceHolder = getHolder();
        mSurfaceHolder.addCallback(this);
        setZOrderOnTop(true);
        mSurfaceHolder.setFormat(PixelFormat.TRANSPARENT);
    }

    @Override
    public void surfaceCreated(SurfaceHolder surfaceHolder) {

    }

    @Override
    public void surfaceChanged(SurfaceHolder surfaceHolder, int i, int i1, int i2) {

    }

    @Override
    public void surfaceDestroyed(SurfaceHolder surfaceHolder) {

    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        mViewWidth = w;
        mViewHeight = h;
    }

    public void startAnim(List<IParticleDraw> list) {
        if (list == null || list.size() == 0) {
            return;
        }
        this.effectList = list;
        for (int i = 0; i < effectList.size(); i++) {
            // 创建粒子
            effectList.get(i).createParticle(mViewWidth, mViewHeight);
        }
        mDrawThread = new DrawThread(effectList);
        mDrawThread.start();
    }

    /**
     * 无限绘制的线程
     */
    private class DrawThread extends Thread {

        private Paint localPaint = new Paint();
        private List<IParticleDraw> effectList;
        private boolean showEffect = true;

        public DrawThread(List<IParticleDraw> list) {
            effectList = list;
            localPaint.setAntiAlias(true);
        }

        public void stopDraw() {
            showEffect = false;
        }

        @Override
        public void run() {
            Canvas canvas = null;
            //无限循环绘制
            while (showEffect) {
                try {
                    Thread.sleep(1000 / frameRate);
                    canvas = mSurfaceHolder.lockCanvas();
                    if (canvas != null && effectList != null && effectList.size() > 0) {
                        canvas.drawColor(Color.TRANSPARENT, PorterDuff.Mode.CLEAR);
                        for (int i = 0; i < effectList.size(); i++) {
                            effectList.get(i).draw(canvas, localPaint);
                        }
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                } finally {
                    if (mSurfaceHolder != null) {
                        Surface surface = mSurfaceHolder.getSurface();
                        if (canvas != null && mSurfaceHolder != null && surface != null && surface.isValid()) {
                            try {
                                mSurfaceHolder.unlockCanvasAndPost(canvas);
                            } catch (Exception e) {
                                e.printStackTrace();
                            }
                        }
                    }
                }
            }
            // 画一帧透明的涂层
            if (mSurfaceHolder != null) {
                canvas = mSurfaceHolder.lockCanvas();
                if (canvas != null) {
                    canvas.drawColor(Color.TRANSPARENT, PorterDuff.Mode.CLEAR);
                    mSurfaceHolder.unlockCanvasAndPost(canvas);
                }
            }
            // 不播放动画的时候马上销毁EffectDraw类
            if (effectList != null && effectList.size() > 0) {
                for (int i = 0; i < effectList.size(); i++) {
                    effectList.get(i).destroy();
                }
            }
        }

        public boolean isDrawing() {
            return showEffect;
        }
    }

    public static Bitmap getScaleBitmap(Bitmap bitmap, float scale, float rotate) {
        try {
            Matrix matrix = new Matrix();
            matrix.postScale(scale, scale);
            matrix.postRotate(rotate);
            int width = bitmap.getWidth();
            int height = bitmap.getHeight();
            width = width == 0 ? width + 1 : width;
            height = height == 0 ? height + 1 : height;
            Bitmap resizeBmp = Bitmap.createBitmap(bitmap, 0, 0, width, height, matrix, false);
            return resizeBmp;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public void stopAnim() {
        if (effectList != null) {
            effectList.clear();
        }
        if (mDrawThread != null && mDrawThread.isAlive()) {
            mDrawThread.stopDraw();
        }
    }

    @Override
    protected void onDetachedFromWindow() {
        super.onDetachedFromWindow();
        stopAnim();
    }
}
