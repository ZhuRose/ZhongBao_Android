package com.edu.uestc.zhongbao_android.controller.launch;

import android.content.Intent;
import android.os.Handler;
import android.support.v4.app.ActivityOptionsCompat;
import android.transition.Fade;
import android.widget.FrameLayout;

import com.edu.uestc.zhongbao_android.R;
import com.edu.uestc.zhongbao_android.controller.base.BaseActivity;
import com.edu.uestc.zhongbao_android.controller.main.MainActivity;

import butterknife.BindView;

/**
 * Created by zhu on 17/3/12.
 */

public class LaunchActivity extends BaseActivity {
    @BindView(R.id.frameViews)
    FrameLayout frameViews;


    @Override
    protected int loadLayout() {
        return R.layout.activity_launch;
    }

    @Override
    protected void initData() {
        super.initData();
        getWindow().setExitTransition(new Fade());
        Handler handler = new Handler();
        //当计时结束时，跳转至主界面
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                startActivity(new Intent(mContext, MainActivity.class),
                        ActivityOptionsCompat.makeSceneTransitionAnimation(mContext).toBundle());
                finishAfterTransition();
            }
        }, 2000);
    }
}
