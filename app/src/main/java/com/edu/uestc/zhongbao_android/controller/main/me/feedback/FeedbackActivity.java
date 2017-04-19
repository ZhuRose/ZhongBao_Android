package com.edu.uestc.zhongbao_android.controller.main.me.feedback;

import android.view.View;
import android.widget.Toast;

import com.edu.uestc.zhongbao_android.R;
import com.edu.uestc.zhongbao_android.controller.base.BaseActivity;
import com.edu.uestc.zhongbao_android.controller.base.BaseDialogFragment;
import com.edu.uestc.zhongbao_android.controller.main.publish.AlertFragment;
import com.edu.uestc.zhongbao_android.utils.AndroidBug5497Workaround;
import com.edu.uestc.zhongbao_android.utils.CustomToast;

import butterknife.OnClick;

/**
 * Created by zhu on 17/4/9.
 */

public class FeedbackActivity extends BaseActivity {

    @OnClick(R.id.sendBtn)
    void sendButtonOnClick(View sender) {
        BaseDialogFragment.showFailed(getSupportFragmentManager(), "失败了吗");
    }

    @Override
    protected int loadLayout() {
        return R.layout.activity_feedback;
    }

    @Override
    protected void initData() {
        super.initData();
        AndroidBug5497Workaround.assistActivity(mContext);
    }
}
