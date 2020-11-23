package com.zs.animationtest

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.zs.animationtest.activity.FrameActivity
import com.zs.animationtest.activity.LeonidActivity
import com.zs.animationtest.activity.LottieActivity
import com.zs.animationtest.activity.SVGAActivity
import kotlinx.android.synthetic.main.activity_main.*
import org.jetbrains.anko.startActivity

/**
 * Lottie SVGA ParticleSystem Animation
 */
class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        tv_lottie?.setOnClickListener {
            startActivity<LottieActivity>()
        }

        tv_svga?.setOnClickListener {
            startActivity<SVGAActivity>()
        }

        tv_frame?.setOnClickListener {
            startActivity<FrameActivity>()
        }

        tv_particle?.setOnClickListener {
            startActivity<LeonidActivity>()
        }
    }

}
