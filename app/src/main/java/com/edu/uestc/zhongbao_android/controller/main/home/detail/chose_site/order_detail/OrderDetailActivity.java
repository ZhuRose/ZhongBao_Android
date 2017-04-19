package com.edu.uestc.zhongbao_android.controller.main.home.detail.chose_site.order_detail;

import android.support.constraint.ConstraintLayout;
import android.util.Log;
import android.view.View;

import com.edu.uestc.zhongbao_android.R;
import com.edu.uestc.zhongbao_android.application.Constant;
import com.edu.uestc.zhongbao_android.controller.base.BaseActivity;
import com.edu.uestc.zhongbao_android.utils.AndroidBug5497Workaround;
import com.edu.uestc.zhongbao_android.utils.SoftKeyboardUtil;

import butterknife.BindView;

/**
 * Created by zhu on 17/4/10.
 */

public class OrderDetailActivity extends BaseActivity {
    @BindView(R.id.bottomView)
    ConstraintLayout bottomView;

    @Override
    protected int loadLayout() {
        return R.layout.activity_detail_order;
    }

    @Override
    protected void initData() {
        super.initData();
        AndroidBug5497Workaround.assistActivity(mContext);
        SoftKeyboardUtil.observeSoftKeyboard(mContext, new SoftKeyboardUtil.OnSoftKeyboardChangeListener() {
            @Override
            public void onSoftKeyBoardChange(int softKeybardHeight, boolean visible) {
                Log.v("info", "keyboard"+softKeybardHeight+visible);
                ConstraintLayout.LayoutParams params =  (ConstraintLayout.LayoutParams)bottomView.getLayoutParams();
                if (!visible) {
                    params.setMargins(0, 0, 0, Constant.getVirtualBarHeight(mContext));
                } else {
                    params.setMargins(0, 0, 0, 0);
                }
                bottomView.requestLayout();

            }
        });
    }

    @Override
    protected void initView() {
        super.initView();
    }
}
