package com.zs.animationtest.activity

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.plattysoft.leonids.ParticleSystem
import com.zs.animationtest.R
import com.zs.animationtest.util.AnimationUtil
import com.zs.animationtest.util.SystemTool

/**
 * @Author: zs
 * @Date: 2020/11/23 2:20 PM
 *
 * @Description:
 */
class LeonidActivity: AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_leonid)
    }

    /**
     * 粒子运动
     */
    private fun showParticleSystem(){
        AnimationUtil.GIFT_GBQQ_LZ.forEach {
            var ps = ParticleSystem(this, 3, it, 10000L)
            ps.setSpeedModuleAndAngleRange(0.1f, 0.15f, 240, 300)
            ps.setRotationSpeedRange(0f, 1f)
            ps.setScaleRange(0.8f, 1.2f)
            ps.setFadeOut(200)
            ps.setAcceleration(0f, 270)
            ps.emit(SystemTool.getScreenWidth() / 2, SystemTool.getScreenHeight() + 600, 1, 10000)
        }
    }

}