package com.zs.animationtest.activity

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.zs.animationtest.R
import kotlinx.android.synthetic.main.activity_main.*
import org.jetbrains.anko.dip

/**
 * @Author: zs
 * @Date: 2020/11/23 2:20 PM
 *
 * @Description:
 */
class LottieActivity: AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_lottie)
        showLottie()
    }

    /**
     * lottie
     */
    private fun showLottie(){
        lottie_view?.layoutParams?.width = dip(350)
        lottie_view?.layoutParams?.height = dip(450)
        lottie_view?.imageAssetsFolder = "gifts/hb"
        lottie_view?.setAnimation("gifts/hb.json")
        lottie_view?.playAnimation()
    }

    override fun onDestroy() {
        super.onDestroy()
        if (lottie_view.isAnimating) {
            lottie_view.cancelAnimation()
        }
    }

}