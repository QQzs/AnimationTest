package com.zs.animationtest;

import android.app.Application;

import com.zs.animationtest.util.ContextUtils;

/**
 * @Author: zs
 * @Date: 2020-05-26 16:43
 * @Description:
 */
public class MyApplicaton extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        ContextUtils.init(this);
    }
}
