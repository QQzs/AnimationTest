package com.zs.animationtest.util;

import android.content.Context;

/**
 * @Author: zs
 * @Date: 2020-05-26 16:43
 * @Description:
 */
public class ContextUtils {

    public static Context sApplicationContext;

    public static void init(Context context) {
        sApplicationContext = context;
    }

}
