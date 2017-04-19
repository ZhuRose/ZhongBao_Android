package com.edu.uestc.zhongbao_android.controller.main.me.setting;

import android.content.Intent;
import android.widget.Button;

import com.edu.uestc.zhongbao_android.R;
import com.edu.uestc.zhongbao_android.controller.base.BaseActivity;
import com.edu.uestc.zhongbao_android.controller.base.BaseSwipeActivity;
import com.edu.uestc.zhongbao_android.utils.UserManager;

import butterknife.OnClick;

/**
 * Created by zhu on 17/3/30.
 */

public class SettingActivity extends BaseActivity {
    @OnClick(R.id.logoutBtn)
    void logoutButtonClick(Button sender) {
        UserManager.shareManager(mContext).setHasLogin(false);
        finishAfterTransition();
        Intent intent = new Intent();
        intent.setAction("login");
        sendBroadcast(intent);
    }

    @Override
    protected int loadLayout() {
        return R.layout.activity_setting;
    }
}
