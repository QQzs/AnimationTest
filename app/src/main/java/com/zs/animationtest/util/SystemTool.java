package com.zs.animationtest.util;

import android.content.Context;
import android.util.DisplayMetrics;
import android.view.WindowManager;

/**
 * @Author: zs
 * @Date: 2020-05-26 16:42
 * @Description:
 */
public class SystemTool {

    public static int getScreenWidth() {
        WindowManager wm = (WindowManager) ContextUtils.sApplicationContext.getSystemService(
                Context.WINDOW_SERVICE);

        int width = wm.getDefaultDisplay().getWidth();
        return width;
    }

    public static int getScreenHeight() {
        WindowManager windowManager =
                (WindowManager) ContextUtils.sApplicationContext.getSystemService(Context.WINDOW_SERVICE);
        DisplayMetrics metric = new DisplayMetrics();
        windowManager.getDefaultDisplay().getMetrics(metric);
        return metric.heightPixels;
    }
}
