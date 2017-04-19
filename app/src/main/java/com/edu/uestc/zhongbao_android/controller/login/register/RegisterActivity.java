package com.edu.uestc.zhongbao_android.controller.login.register;

import com.edu.uestc.zhongbao_android.R;
import com.edu.uestc.zhongbao_android.controller.base.BaseActivity;
import com.edu.uestc.zhongbao_android.utils.AndroidBug5497Workaround;
import com.edu.uestc.zhongbao_android.view.CountDownButton;

import butterknife.OnClick;

/**
 * Created by zhu on 17/4/7.
 */

public class RegisterActivity extends BaseActivity {
    @Override
    protected int loadLayout() {
        return R.layout.activity_register;
    }

    @OnClick(R.id.countDownBtn)
    public void countDownButtonClick(CountDownButton sender) {
        sender.beginCountDown();
    }

    @Override
    protected void initData() {
        super.initData();
        AndroidBug5497Workaround.assistActivity(mContext);
    }
}
