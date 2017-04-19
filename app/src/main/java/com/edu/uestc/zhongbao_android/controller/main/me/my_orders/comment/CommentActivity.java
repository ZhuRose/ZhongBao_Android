package com.edu.uestc.zhongbao_android.controller.main.me.my_orders.comment;

import com.edu.uestc.zhongbao_android.R;
import com.edu.uestc.zhongbao_android.controller.base.BaseActivity;
import com.edu.uestc.zhongbao_android.utils.AndroidBug5497Workaround;

/**
 * Created by zhu on 17/4/10.
 */

public class CommentActivity extends BaseActivity {
    @Override
    protected int loadLayout() {
        return R.layout.activity_comment;
    }

    @Override
    protected void initData() {
        super.initData();
        AndroidBug5497Workaround.assistActivity(mContext);
    }
}
