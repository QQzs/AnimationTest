package com.zs.animationtest

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.opensource.svgaplayer.SVGADrawable
import com.opensource.svgaplayer.SVGAParser
import com.opensource.svgaplayer.SVGAVideoEntity
import com.plattysoft.leonids.ParticleSystem
import com.zs.animationtest.util.AnimationUtil
import com.zs.animationtest.util.SystemTool
import kotlinx.android.synthetic.main.activity_main.*
import org.jetbrains.anko.dip

/**
 * Lottie SVGA ParticleSystem Animation
 */
class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        tv_lottie?.setOnClickListener {
            showLottie()
        }

        tv_svga?.setOnClickListener {
            showSVGA()
        }

        tv_frame?.setOnClickListener {
            frame_view?.resIds = AnimationUtil.GIFT_YHY
            frame_view?.startAnim()
        }

        tv_particle?.setOnClickListener {
            showParticleSystem()
        }
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

    /**
     * SVGA
     */
    private fun showSVGA(){
        svg_view?.loops = 1
        var parser = SVGAParser(this)
        parser.parse("gifts/qp.svga" , object: SVGAParser.ParseCompletion{
            override fun onComplete(videoItem: SVGAVideoEntity) {
                svg_view?.setImageDrawable(SVGADrawable(videoItem))
                svg_view?.startAnimation()
            }

            override fun onError() {

            }

        })
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
