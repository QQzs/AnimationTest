package com.zs.animationtest;

import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.plattysoft.leonids.ParticleSystem;
import com.zs.animationtest.view.CircleView;
import com.zs.animationtest.view.ScrollAnimView;
import com.zs.animationtest.view.ScrollInnerAnimView;
import com.zs.animationtest.view.particle.driftstar.DriftStarDraw;
import com.zs.animationtest.view.particle.IParticleDraw;
import com.zs.animationtest.view.particle.ParticleView;
import com.zs.animationtest.view.particle.pointstar.PointStarDraw;
import com.zs.animationtest.view.particle.star.StarDraw;

import java.util.ArrayList;
import java.util.List;

/**
 * @Author: zs
 * @Date: 2020/11/23 3:34 PM
 * @Description:
 */
public class AnimActivity extends AppCompatActivity implements View.OnClickListener {

    private ScrollAnimView scroll_anim_view;
    private ScrollInnerAnimView scroll_inner_anim_view;
    private TextView all_anim, tv_anim1, tv_anim2, tv_anim3, tv_anim4, tv_anim5;
    private View view_avatar;
    private CircleView circle_view;
    private ParticleView particle_view;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_leonids);
        view_avatar = findViewById(R.id.view_avatar);
        scroll_anim_view = findViewById(R.id.scroll_anim_view);
        scroll_inner_anim_view = findViewById(R.id.scroll_inner_anim_view);
        all_anim = findViewById(R.id.all_anim);
        tv_anim1 = findViewById(R.id.tv_anim1);
        tv_anim2 = findViewById(R.id.tv_anim2);
        tv_anim3 = findViewById(R.id.tv_anim3);
        tv_anim4 = findViewById(R.id.tv_anim4);
        tv_anim5 = findViewById(R.id.tv_anim5);
        circle_view = findViewById(R.id.circle_view);
        particle_view = findViewById(R.id.particle_view);

        all_anim.setOnClickListener(this);
        tv_anim1.setOnClickListener(this);
        tv_anim2.setOnClickListener(this);
        tv_anim3.setOnClickListener(this);
        tv_anim4.setOnClickListener(this);
        tv_anim5.setOnClickListener(this);

    }

    @Override
    public void onClick(View view) {
        int id = view.getId();
        if (id == R.id.all_anim) {
            allAnim();
            startScroll();
        } else if (id == R.id.tv_anim1) {
            startScroll();
        } else if (id == R.id.tv_anim2) {
            test2();
            startScroll();
        } else if (id == R.id.tv_anim3) {
            test3();
            startScroll();
        } else if (id == R.id.tv_anim4) {
            test4();
            startScroll();
        } else if (id == R.id.tv_anim5) {
            test5();
            startScroll();
        }
    }

    private void test4() {
        scroll_inner_anim_view.startAnim(10);
    }

    private void test5() {

    }

    private void startScroll() {
        scroll_anim_view.startScroll();
        scroll_anim_view.setOutListener(new ScrollAnimView.OuterListener() {
            @Override
            public void backValue(float value) {
                scroll_inner_anim_view.setScrollStatus(value);
            }

            @Override
            public void scrollEnd() {
                circle_view.stopAnim();
                particle_view.stopAnim();
                scroll_inner_anim_view.stopAnim();
            }
        });
    }

    private void test3() {
        List<IParticleDraw> list = new ArrayList<>();
        list.add(new StarDraw(this, R.drawable.star_white, 15));
        list.add(new PointStarDraw(this, R.drawable.star, 10));
        list.add(new DriftStarDraw(this, R.drawable.star_white, 20));
        list.add(new DriftStarDraw(this, R.drawable.star_pink, 20));
        particle_view.startAnim(list);
    }

    private void test2() {
        circle_view.startAnim();
    }

    private void test1() {
        new ParticleSystem(this, 15, R.drawable.star_pink, 2000)
                // 加速度 和 加速度方向
                .setAcceleration(0.0001f, 0)
                // 自身旋转速度
                .setRotationSpeed(90)
                .setScaleRange(1.2f, 1.5f)
                // X方向速度值  Y方向速度值
                .setSpeedByComponentsRange(0.05f, 0.08f, 0f, 0f)
                // 淡出持续时间(毫秒)
                .setFadeOut(500)
                // 1:粒子发射的视图 2: 发射位置 3: 每秒发射的粒子数
                .emitWithGravity(view_avatar, Gravity.RIGHT, 6);

        new ParticleSystem(this, 15, R.drawable.star, 2000)
                // 加速度 和 加速度方向
                .setAcceleration(0.0001f, 0)
                // 自身旋转速度
                .setRotationSpeed(90)
                .setScaleRange(0.3f, 0.5f)
                // X方向速度值  Y方向速度值
                .setSpeedByComponentsRange(0.05f, 0.08f, 0f, 0f)
                // 淡出持续时间(毫秒)
                .setFadeOut(500)
                // 1:粒子发射的视图 2: 发射位置 3: 每秒发射的粒子数
                .emitWithGravity(view_avatar, Gravity.RIGHT, 4);

    }

    private void allAnim() {
        test2();
        test3();
        test4();
        test5();
    }

}
