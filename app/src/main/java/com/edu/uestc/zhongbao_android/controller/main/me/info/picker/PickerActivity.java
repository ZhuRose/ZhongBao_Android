package com.edu.uestc.zhongbao_android.controller.main.me.info.picker;

import android.animation.Animator;
import android.animation.ObjectAnimator;
import android.os.Bundle;
import android.os.Message;
import android.support.constraint.ConstraintLayout;
import android.transition.Fade;
import android.view.MotionEvent;
import android.view.View;
import android.widget.TextView;

import com.edu.uestc.zhongbao_android.R;
import com.edu.uestc.zhongbao_android.application.Constant;
import com.edu.uestc.zhongbao_android.application.MainApplication;
import com.edu.uestc.zhongbao_android.controller.base.BaseActivity;
import com.edu.uestc.zhongbao_android.utils.PickerDateUtil;
import com.edu.uestc.zhongbao_android.view.PickerView;
import com.edu.uestc.zhongbao_android.view.PickerViewDataSource;

import butterknife.BindView;
import butterknife.OnClick;
import butterknife.OnTouch;

/**
 * Created by zhu on 17/4/2.
 */

public class PickerActivity extends BaseActivity {
    private boolean animating;
    private int type;
    public static final int Type_Birthday = 0;
    public static final int Type_Sex = 1;

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

    @BindView(R.id.mainView)
    ConstraintLayout mainView;

    @BindView(R.id.titleView)
    TextView titleView;

    @BindView(R.id.pickerView)
    PickerView pickerView;

    @OnClick({R.id.cancleBtn, R.id.confirmBtn})
    void onClick(View view) {
        if (view.getId() == R.id.confirmBtn) {
            MainApplication application = (MainApplication)getApplication();
            Message message = new Message();
            message.what = 100+type;
            Bundle data = new Bundle();
            data.putStringArray("results", pickerView.getSelectedStrings());
            message.setData(data);
            application.getHandler().sendMessage(message);
        }
//        stopAnimation();
        finishAfterTransition();
    }

    @Override
    protected int loadLayout() {
        return R.layout.activity_picker;
    }

    @Override
    protected void initView() {
        super.initView();
        type = getIntent().getIntExtra("type", Type_Birthday);
        switch (type) {
            case Type_Birthday:
                PickerDateUtil util = new PickerDateUtil(mContext);
                titleView.setText("请选择生日");
                pickerView.setDataSource(util);
                pickerView.setDelegate(util);
                break;
            default:
                titleView.setText("请选择性别");
                ConstraintLayout.LayoutParams layoutParams = (ConstraintLayout.LayoutParams)pickerView.getLayoutParams();
                layoutParams.setMarginStart(Constant.getScreenWidth(mContext)/3);
                layoutParams.setMarginEnd(Constant.getScreenWidth(mContext)/3);
                pickerView.setDataSource(new PickerViewDataSource() {
                    @Override
                    public int numberOfColumns() {
                        return 1;
                    }

                    @Override
                    public String[] dataSorceIn(int column) {
                        return new String[]{"男", "女"};
                    }

                    @Override
                    public int numberOfVisibleCell() {
                        return 3;
                    }

                    @Override
                    public int cellHeight() {
                        return (int)(60* Constant.getDensity(mContext));
                    }
                });
                break;
        }
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
        ObjectAnimator animator = ObjectAnimator.ofFloat(mainView, "translationY",  -282.0f* Constant.getDensity(mContext)).setDuration(300);
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
        ObjectAnimator animator = ObjectAnimator.ofFloat(mainView, "translationY",  282.0f* Constant.getDensity(mContext)).setDuration(300);
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
