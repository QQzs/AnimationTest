package com.zs.animationtest.view.particle;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;

import java.util.Random;

/**
 * @Author: zs
 * @Date: 2020/11/24 7:55 PM
 * @Description:
 */
public abstract class IParticleBean {

    /**
     * 产生随机数
     */
    public Random random = new Random();

    /**
     * 绘制下一帧
     *
     * @param canvas
     * @param paint
     */
    public abstract void drawNextFrame(Canvas canvas, Paint paint);

    /**
     * 释放资源
     */
    public abstract void destroy();

}
