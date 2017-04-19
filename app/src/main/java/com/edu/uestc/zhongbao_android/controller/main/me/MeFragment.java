package com.edu.uestc.zhongbao_android.controller.main.me;

import android.content.Intent;
import android.view.View;

import com.edu.uestc.zhongbao_android.R;
import com.edu.uestc.zhongbao_android.controller.base.BaseFragment;
import com.edu.uestc.zhongbao_android.controller.main.me.feedback.FeedbackActivity;
import com.edu.uestc.zhongbao_android.controller.main.me.info.InfoActivity;
import com.edu.uestc.zhongbao_android.controller.main.me.my_collection.MyCollectionActivity;
import com.edu.uestc.zhongbao_android.controller.main.me.my_locus.MyLocusActivity;
import com.edu.uestc.zhongbao_android.controller.main.me.my_orders.MyOrdersActivity;
import com.edu.uestc.zhongbao_android.controller.main.me.setting.SettingActivity;
import com.edu.uestc.zhongbao_android.view.ModelView;

import butterknife.OnClick;

/**
 * Created by zhu on 17/3/21.
 */

public class MeFragment extends BaseFragment {
    @OnClick({R.id.modelView1, R.id.modelView2, R.id.modelView3, R.id.modelView4, R.id.modelView5})
    void settingButtonClick(ModelView sender) {
        Intent intent = null;
        switch (sender.getId()) {
            case R.id.modelView1:
                intent = new Intent(mActivity, MyOrdersActivity.class);
                break;
            case R.id.modelView2:
                intent = new Intent(mActivity, MyLocusActivity.class);
                break;
            case R.id.modelView3:
                intent = new Intent(mActivity, MyCollectionActivity.class);
                break;
            case R.id.modelView4:
                intent = new Intent(mActivity, FeedbackActivity.class);
                break;
            default:
                intent = new Intent(mActivity, SettingActivity.class);
                break;
        }
        mActivity.startActivity(intent);
    }

    @OnClick(R.id.headView)
    void headButtonClick(View view) {
        mActivity.startActivity(new Intent(mActivity, InfoActivity.class));
    }

    @Override
    protected int loadLayout() {
        return R.layout.fragment_me;
    }
}
