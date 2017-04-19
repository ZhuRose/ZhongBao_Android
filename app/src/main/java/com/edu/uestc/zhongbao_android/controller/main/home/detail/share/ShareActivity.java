package com.edu.uestc.zhongbao_android.controller.main.home.detail.share;

import android.animation.Animator;
import android.animation.ObjectAnimator;
import android.support.constraint.ConstraintLayout;
import android.transition.Fade;
import android.view.MotionEvent;
import android.view.View;

import com.edu.uestc.zhongbao_android.R;
import com.edu.uestc.zhongbao_android.application.Constant;
import com.edu.uestc.zhongbao_android.controller.base.BaseActivity;

import butterknife.BindView;
import butterknife.OnClick;
import butterknife.OnTouch;

/**
 * Created by zhu on 17/4/6.
 */

public class ShareActivity extends BaseActivity {

    private boolean animating;

    @OnTouch(R.id.contentLayout)
    public boolean touch(View view, MotionEvent event) {
        if (event.getAction() == MotionEvent.ACTION_DOWN && !animating) {
//            stopAnimation();
            finishAfterTransition();
        }
        return false;
    }

    @OnTouch(R.id.mainView)
    public boolean interputTouch(View view, MotionEvent event) {
        return true;
    }

    @OnClick(R.id.cancleBtn)
    void onClick(View view) {
//        stopAnimation();
        finishAfterTransition();
    }

    @BindView(R.id.mainView)
    ConstraintLayout mainView;

    @Override
    protected int loadLayout() {
        return R.layout.activity_share;
    }

    @Override
    protected void initData() {
        super.initData();
        animating = true;
        getWindow().setEnterTransition(new Fade());
        getWindow().setReenterTransition(new Fade());
    }

    @Override
    protected void onStart() {
        super.onStart();
        animating = false;
//        beginAnimation();
    }

    public void beginAnimation() {
        ObjectAnimator animator = ObjectAnimator.ofFloat(mainView, "translationY",  -192.0f* Constant.getDensity(mContext)).setDuration(300);
        animator.start();
        animating = true;
        animator.addListener(new Animator.AnimatorListener() {
            @Override
            public void onAnimationStart(Animator animation) {

            }

            @Override
            public void onAnimationEnd(Animator animation) {
                animating = false;
            }

            @Override
            public void onAnimationCancel(Animator animation) {

            }

            @Override
            public void onAnimationRepeat(Animator animation) {

            }
        });
    }

    public void stopAnimation() {
        ObjectAnimator animator = ObjectAnimator.ofFloat(mainView, "translationY",  192.0f* Constant.getDensity(mContext)).setDuration(300);
        animator.start();
        animating = true;
        animator.addListener(new Animator.AnimatorListener() {
            @Override
            public void onAnimationStart(Animator animation) {

            }

            @Override
            public void onAnimationEnd(Animator animation) {
                finishAfterTransition();
            }

            @Override
            public void onAnimationCancel(Animator animation) {

            }

            @Override
            public void onAnimationRepeat(Animator animation) {

            }
        });
    }
}
