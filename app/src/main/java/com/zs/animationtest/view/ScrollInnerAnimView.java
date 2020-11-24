package com.zs.animationtest.view;

import android.app.Activity;
import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;

/**
 * @Author: zs
 * @Date: 2020/11/23 12:01 PM
 * @Description:
 */
public class ScrollInnerAnimView extends LinearLayout {

    int width;
    int height;

    public ScrollInnerAnimView(Context context) {
        this(context, null);
    }

    public ScrollInnerAnimView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public ScrollInnerAnimView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        width = w;
        height = h;
    }

    public ImageView addFireView(int x, int y) {
        LayoutParams params = new LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        ImageView view = new ImageView(getContext());
        view.setX(x);
        view.setY(y);
        addView(view, params);
        return view;
    }


}
