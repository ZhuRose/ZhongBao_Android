package com.edu.uestc.zhongbao_android.controller.base;

import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;

import com.edu.uestc.zhongbao_android.application.MainApplication;
import com.edu.uestc.zhongbao_android.controller.launch.LaunchActivity;

import butterknife.ButterKnife;

/**
 * Created by zhu on 17/3/12.
 */

public abstract class BaseActivity extends AppCompatActivity {
    protected BaseActivity mContext;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        hideStatusBar();
        setContentView(loadLayout());
        mContext = this;
        ((MainApplication)getApplication()).addActivity(mContext);
        ButterKnife.bind(this);
        initData();
        initView();
    }

    protected abstract int loadLayout();

    protected void initView() {

    }

    protected void initData() {

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        ((MainApplication)getApplication()).finishActivity(mContext);
    }

    protected void hideStatusBar() {
        Window window = getWindow();
        window.requestFeature(Window.FEATURE_NO_TITLE);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {//>5.0
            window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS
                    | WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION);
            window.getDecorView().setSystemUiVisibility(
                    View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                            | View.SYSTEM_UI_FLAG_LAYOUT_STABLE
            );
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.setStatusBarColor(Color.TRANSPARENT);
        } else if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {//>4.4
            window.addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        }
    }
}
