package com.zs.animationtest.view.particle.driftstar;

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
class DriftStarBean extends IParticleBean {

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

    private int pointX;
    private int pointXSpace;

    public DriftStarBean(int viewWidth, int viewHeight, ArrayList<Bitmap> bitmapsList) {
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
        pointX = 0;
        alpha = random.nextInt(56) + 200;
        alphaSpace = random.nextInt(40) + 5;
        mPaint.setAlpha(alpha);
        mBitmap = mBitmapsList.get(random.nextInt(size));

        rotation = random.nextInt(360);
        rotationSpace = random.nextInt(6) + 1;
        pointXSpace = random.nextInt(40) + 5;

        if (mBitmap != null) {
            int height = mBitmap.getHeight();
            mCurrentPoint = new Point(0, random.nextInt(mViewHeight - height));
        }
    }

    @Override
    public void drawNextFrame(Canvas canvas, Paint paint) {
        if (mBitmap != null) {
            pointX += pointXSpace;
            // 粒子移动出view后开始重置状态
            if (pointX + (mBitmap.getWidth() >> 1) > mViewWidth) {
                reset();
                return;
            }
            mCurrentPoint.x = pointX;
            // 移动到0.6的宽度后开始渐隐
            if (1f * pointX / mViewWidth > 0.6f) {
                alpha -= alphaSpace;
                if (alpha < 0) {
                    alpha = 0;
                }
                mPaint.setAlpha(alpha);
            }
            rotation += rotationSpace;
            mCurrentPoint.x = pointX;
            canvas.save();
            canvas.rotate(rotation, mCurrentPoint.x + (mBitmap.getWidth() >> 1),
                    mCurrentPoint.y + (mBitmap.getHeight() >> 1));
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
