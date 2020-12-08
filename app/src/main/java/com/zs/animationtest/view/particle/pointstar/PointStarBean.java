package com.zs.animationtest.view.particle.pointstar;

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
class PointStarBean extends IParticleBean {

    /**
     * 当前坐标
     */
    public Point mCurrentPoint;

    /**
     * 旋转
     */
    private int rotation;
    /**
     * 旋转变化值
     */
    private int rotationSpace;

    public PointStarBean(int viewWidth, int viewHeight, ArrayList<Bitmap> bitmapsList) {
        super(viewWidth, viewHeight, bitmapsList);
    }

    private void reset() {
        int size = mBitmapsList.size();
        if (size <= 0){
            return;
        }
        mBitmap = mBitmapsList.get(random.nextInt(size));
        rotation = random.nextInt(360);
        rotationSpace = random.nextInt(6) + 1;
        if (mBitmap != null) {
            int height = mBitmap.getHeight();
            mCurrentPoint = new Point(random.nextInt(mViewWidth - mBitmap.getWidth()),
                    random.nextInt(mViewHeight - height));
        }
    }

    @Override
    public void drawNextFrame(Canvas canvas, Paint paint) {
        if (mBitmap != null) {
            // 旋转角度变化
            rotation += rotationSpace;
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
