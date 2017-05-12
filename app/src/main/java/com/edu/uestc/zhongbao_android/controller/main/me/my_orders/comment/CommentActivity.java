package com.edu.uestc.zhongbao_android.controller.main.me.my_orders.comment;

import android.view.View;
import android.widget.EditText;

import com.edu.uestc.zhongbao_android.R;
import com.edu.uestc.zhongbao_android.controller.base.BaseActivity;
import com.edu.uestc.zhongbao_android.controller.base.BaseDialogFragment;
import com.edu.uestc.zhongbao_android.utils.AndroidBug5497Workaround;
import com.edu.uestc.zhongbao_android.utils.NetworkUtil;
import com.edu.uestc.zhongbao_android.view.StarCommentView;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * Created by zhu on 17/4/10.
 */

public class CommentActivity extends BaseActivity {
    String uuid;
    NetworkUtil networkUtil;

    @BindView(R.id.priceStar)
    StarCommentView priceStar;
    @BindView(R.id.environmentStar)
    StarCommentView environmentStar;
    @BindView(R.id.serviceStar)
    StarCommentView serviceStar;
    @BindView(R.id.trafficStar)
    StarCommentView trafficStar;

    @BindView(R.id.contentEdit)
    EditText contentEdit;

    @OnClick(R.id.sendBtn)
    void sendBtnClick(View sender) {
        BaseDialogFragment.showLoading(getSupportFragmentManager());
        networkUtil.commentNetwork(uuid, ""+priceStar.grades, ""+environmentStar.grades, ""+serviceStar.grades, ""+trafficStar.grades, String.valueOf(contentEdit.getText()));
    }

    @Override
    protected int loadLayout() {
        return R.layout.activity_comment;
    }

    @Override
    protected void initData() {
        super.initData();
        uuid = getIntent().getStringExtra("uuid");
        AndroidBug5497Workaround.assistActivity(mContext);
        networkUtil = new NetworkUtil(mContext) {
            @Override
            public void successNetwork(Object object, String tag) {
                BaseDialogFragment.showSuccess(getSupportFragmentManager(), "评论成功");
                CommentActivity.this.finishAfterTransition();
            }

            @Override
            public void failedNetwork(String errorInfo, String tag) {
                BaseDialogFragment.showFailed(getSupportFragmentManager(), "网络错误");
            }
        };
    }

    @Override
    protected void initView() {
        super.initView();
        priceStar.setStarsFromGrades(5.0f);
        environmentStar.setStarsFromGrades(5.0f);
        serviceStar.setStarsFromGrades(5.0f);
        trafficStar.setStarsFromGrades(5.0f);
    }
}
