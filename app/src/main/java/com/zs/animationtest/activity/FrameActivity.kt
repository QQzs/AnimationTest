package com.zs.animationtest.activity

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.zs.animationtest.R
import com.zs.animationtest.util.AnimationUtil
import kotlinx.android.synthetic.main.activity_main.*

/**
 * @Author: zs
 * @Date: 2020/11/23 2:20 PM
 *
 * @Description:
 */
class FrameActivity: AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_frame)
        frame_view?.resIds = AnimationUtil.GIFT_YHY
        frame_view?.startAnim()
    }

}