package com.zs.animationtest.view.particle.star;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Point;

import com.zs.animationtest.view.particle.IParticleBean;

import java.util.ArrayList;

/**
 * @Author: zs
 * @Date: 2020/11/24 8:04 PM
 * @Description:
 */
class StarBean extends IParticleBean {

    /**
     * 当前坐标
     */
    public Point mCurrentPoint;

    /**
     * 透明度
     */
    private int alpha;

    /**
     * 透明度变化值
     */
    private int alphaSpace;

    /**
     * 旋转
     */
    private int rotation;
    /**
     * 旋转变化值
     */
    private int rotationSpace;

    /**
     * 大小
     */
    private float scale;

    public StarBean(int viewWidth, int viewHeight, ArrayList<Bitmap> bitmapsList) {
        this.mViewWidth = viewWidth;
        this.mViewHeight = viewHeight;
        this.mBitmapsList = bitmapsList;
        mPaint = new Paint();
        mPaint.setAntiAlias(true);
    }

    private void reset() {
        int size = mBitmapsList.size();
        if (size <= 0){
            return;
        }
        alpha = random.nextInt(56) + 200;
        alphaSpace = random.nextInt(10) + 5;
        mPaint.setAlpha(alpha);
        mBitmap = mBitmapsList.get(random.nextInt(size));

        rotation = random.nextInt(360);
        rotationSpace = random.nextInt(4) + 4;
        scale = 1f;
        if (mBitmap != null) {
            int height = mBitmap.getHeight();
            mCurrentPoint = new Point(random.nextInt(mViewWidth - mBitmap.getWidth()), random.nextInt(mViewHeight - height));
        }
    }

    @Override
    public void drawNextFrame(Canvas canvas, Paint paint) {
        if (mBitmap != null) {
            // 透明度变化
            alpha -= alphaSpace;
            if (alpha < 0) {
                alpha = 0;
                alphaSpace = -alphaSpace;
            } else if (alpha > 255) {
                alpha = 255;
                alphaSpace = -alphaSpace;
            }
            // 大小根据透明度变化
            scale = 1.0f * alpha / 255;
            mPaint.setAlpha(alpha);

            // 旋转变化
            rotation += rotationSpace;

            float px = mCurrentPoint.x + (mBitmap.getWidth() >> 1);
            float py = mCurrentPoint.y + (mBitmap.getHeight() >> 1);

            canvas.save();
            canvas.rotate(rotation, px, py);
            canvas.scale(scale, scale, px, py);
            canvas.drawBitmap(mBitmap, mCurrentPoint.x, mCurrentPoint.y, mPaint);
            canvas.restore();
        } else {
            reset();
        }
    }

    @Override
    public void destroy() {

    }
}
