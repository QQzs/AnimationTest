package com.zs.animationtest.view;

import android.content.Context;
import android.os.Handler;
import android.os.Message;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;

import androidx.annotation.NonNull;

import java.lang.ref.WeakReference;

/**
 * 帧动画
 *
 * @author fpc
 * @date 2018-9-28
 */
public class FrameAnimView extends androidx.appcompat.widget.AppCompatImageView {

    private static final int UPDATE = 1;
    private static final int STOP = 2;
    private static final int STOP_AT_LAST = 3;

    public FrameAnimListener animListener;

    public FrameAnimView(Context context) {
        this(context, null);
    }

    public FrameAnimView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public FrameAnimView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        handler = new InterActHandler(this);
    }

    private InterActHandler handler;
    private int[] mRids;
    private int mCurFrameIndex = 0;
    /**
     * 是否执行一次
     */
    private boolean animOne = false;
    /**
     * 下一帧的时间
     */
    private int[] delayArr;
    private int defaultDelay = 41;

    private boolean stop = false;
    public boolean start = false;
    /**
     * control auto play anim
     * */
    public boolean autoPlay = true;
    /**
     * 执行的次数
     */
    private int animCount = 1;

    public void setResIds(int[] rids, int[] delays) {
        mRids = rids;
        if (mRids.length == delays.length) {
            delayArr = delays;
        }
    }

    public int[] getResIds() {
        return mRids;
    }

    public void setResIds(int[] rids) {
        mRids = rids;
    }

    public void setResIds(int[] rids, int delay) {
        mRids = rids;
        defaultDelay = delay;
    }

    public void setAnimOne(boolean animOne) {
        this.animOne = animOne;
    }

    public void setShotCount(int count) {
        this.animCount = count;
    }

    public int getAnimCount() {
        return animCount;
    }

    public void startAnim() {
        if (mRids == null) {
            throw new RuntimeException("rids must not null");
        }
        if (!start) {
            start = true;
            stop = false;
            mCurFrameIndex = 0;
            setVisibility(View.VISIBLE);
            handler.sendEmptyMessage(UPDATE);
        }
    }


    public void stopAnim() {
        handler.removeCallbacksAndMessages(null);
        stop();
    }

    /**
     * 动画停止在当前图片
     */
    public void stopAnimAtCurrent() {
        handler.removeCallbacksAndMessages(null);
        stop();
    }

    /**
     * 动画结束停在最有一张图
     */
    private void stopAtLast() {
        handler.removeCallbacksAndMessages(null);
        setImageResource(mRids[mRids.length - 1]);
        start = false;
    }

    private void stop() {
        handler.removeCallbacksAndMessages(null);
        start = false;
        stop = false;
    }

    private static class InterActHandler extends Handler {
        WeakReference<FrameAnimView> reference;

        public InterActHandler(FrameAnimView v) {
            reference = new WeakReference<FrameAnimView>(v);
        }

        @Override
        public void dispatchMessage(Message msg) {
            switch (msg.what) {
                case UPDATE: //更新
                    if (reference != null && reference.get() != null){
                        reference.get().updateFrame();
                    }
                    break;
                case STOP:
                    if (reference != null && reference.get() != null){
                        reference.get().stop();
                    }
                    break;
                case STOP_AT_LAST:
                    if (reference != null && reference.get() != null) {
                        reference.get().stopAtLast();
                    }
                    break;
                default:
                    break;
            }
        }
    }

    /**
     * 更新图片
     */
    private void updateFrame() {
        if (getVisibility() != View.VISIBLE
                || stop) {
            handler.sendEmptyMessage(STOP);
            return;
        }
        if (mCurFrameIndex < mRids.length) {
            setImageResource(mRids[mCurFrameIndex]);
            int delay = getDelay();
            mCurFrameIndex++;
            if (mCurFrameIndex == mRids.length) {
                mCurFrameIndex = 0;
                if (animOne) {
                    //到此结束了 停留在最后一帧
                    if (animCount <= 1) {
                        handler.sendEmptyMessage(STOP_AT_LAST);
                        if (animListener != null) {
                            animListener.onAnimationComplete();
                        }
                    } else {
                        handler.sendEmptyMessageDelayed(UPDATE, delay);
                        animCount--;
                    }
                } else {
                    handler.sendEmptyMessageDelayed(UPDATE, delay);
                }
            } else {
                handler.sendEmptyMessageDelayed(UPDATE, delay);
            }
        }
    }

    private int getDelay() {
        if (delayArr != null) {
            if (mCurFrameIndex < delayArr.length) {
                return delayArr[mCurFrameIndex];
            } else {
                return defaultDelay;
            }
        } else {
            return defaultDelay;
        }
    }

    @Override
    public void setVisibility(int visibility) {
        if (visibility == GONE && isShown()) {
            stopAnim();
            Log.d("FrameAnimVIew", "我要停止了");
        }
        super.setVisibility(visibility);
    }

    @Override
    protected void onVisibilityChanged(@NonNull View changedView, int visibility) {
        if (visibility == View.GONE) {
            stopAnimAtCurrent();
        } else if (mRids != null && getVisibility() == View.VISIBLE && !animOne && autoPlay) {
            startAnim();
        }
        super.onVisibilityChanged(changedView, visibility);
    }

    public void setOnFrameAnimListener(FrameAnimListener listener) {
        this.animListener = listener;
    }

    public interface FrameAnimListener {
        void onAnimationComplete();
    }

}
