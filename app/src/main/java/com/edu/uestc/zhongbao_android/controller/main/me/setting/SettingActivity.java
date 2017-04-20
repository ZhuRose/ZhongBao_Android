package com.edu.uestc.zhongbao_android.controller.main.me.setting;

import android.content.Intent;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.edu.uestc.zhongbao_android.R;
import com.edu.uestc.zhongbao_android.controller.base.BaseActivity;
import com.edu.uestc.zhongbao_android.controller.base.BaseSwipeActivity;
import com.edu.uestc.zhongbao_android.utils.CleanMessageUtil;
import com.edu.uestc.zhongbao_android.utils.ImageLoadManager;
import com.edu.uestc.zhongbao_android.utils.UserManager;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * Created by zhu on 17/3/30.
 */

public class SettingActivity extends BaseActivity {
    @BindView(R.id.clearCacheView)
    TextView cacheView;

    @OnClick(R.id.logoutBtn)
    void logoutButtonClick(Button sender) {
        UserManager.shareManager(mContext).setHasLogin(false);
        finishAfterTransition();
        Intent intent = new Intent();
        intent.setAction("login");
        sendBroadcast(intent);
    }

    @OnClick({R.id.infoView1, R.id.infoView2, R.id.infoView3, R.id.infoView4})
    void infoViewOnClick(final View view) {
        switch (view.getId()) {
            case R.id.infoView3:
                view.post(new Runnable() {
                    @Override
                    public void run() {
                        ImageLoadManager.shareManager().clearCache();
                        view.post(new Runnable() {
                            @Override
                            public void run() {
                                cacheView.setText("0.00 KB");
                            }
                        });
                    }
                });

                break;
            default:
                break;
        }
    }


    @Override
    protected int loadLayout() {
        return R.layout.activity_setting;
    }

    @Override
    protected void initView() {
        super.initView();
        try {
            cacheView.setText(ImageLoadManager.shareManager().getCacheSize());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
