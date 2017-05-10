package com.edu.uestc.zhongbao_android.controller.main.me.feedback;

import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.edu.uestc.zhongbao_android.R;
import com.edu.uestc.zhongbao_android.controller.base.BaseActivity;
import com.edu.uestc.zhongbao_android.controller.base.BaseDialogFragment;
import com.edu.uestc.zhongbao_android.controller.main.publish.AlertFragment;
import com.edu.uestc.zhongbao_android.utils.AndroidBug5497Workaround;
import com.edu.uestc.zhongbao_android.utils.CustomToast;
import com.edu.uestc.zhongbao_android.utils.NetworkUtil;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * Created by zhu on 17/4/9.
 */

public class FeedbackActivity extends BaseActivity {

    @BindView(R.id.contentEdit)
    EditText contentEdit;

    @BindView(R.id.phoneEdit)
    EditText phoneEdit;

    NetworkUtil networkUtil;

    @OnClick(R.id.sendBtn)
    void sendButtonOnClick(View sender) {
        BaseDialogFragment.showLoading(getSupportFragmentManager());
        networkUtil.feedbackNetwork(String.valueOf(contentEdit.getText()),String.valueOf(phoneEdit.getText()));
    }



    @Override
    protected int loadLayout() {
        return R.layout.activity_feedback;
    }

    @Override
    protected void initData() {
        super.initData();
        AndroidBug5497Workaround.assistActivity(mContext);
        networkUtil = new NetworkUtil(mContext) {
            @Override
            public void successNetwork(Object object, String tag) {
                BaseDialogFragment.showSuccess(getSupportFragmentManager(), "反馈成功");
                FeedbackActivity.this.finishAfterTransition();
            }

            @Override
            public void failedNetwork(String errorInfo, String tag) {
                BaseDialogFragment.showFailed(getSupportFragmentManager(), errorInfo);
            }
        };
    }
}
