package com.zs.animationtest.activity

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.opensource.svgaplayer.SVGADrawable
import com.opensource.svgaplayer.SVGAParser
import com.opensource.svgaplayer.SVGAVideoEntity
import com.zs.animationtest.R
import kotlinx.android.synthetic.main.activity_main.*

/**
 * @Author: zs
 * @Date: 2020/11/23 2:20 PM
 *
 * @Description:
 */
class SVGAActivity: AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_svga)
        showSVGA()
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

    override fun onDestroy() {
        super.onDestroy()
        if (svg_view.isAnimating) {
            svg_view.stopAnimation()
        }
    }

}