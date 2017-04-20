package com.edu.uestc.zhongbao_android.controller.main.me;

import android.content.Intent;
import android.text.TextWatcher;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.edu.uestc.zhongbao_android.R;
import com.edu.uestc.zhongbao_android.application.Constant;
import com.edu.uestc.zhongbao_android.controller.base.BaseFragment;
import com.edu.uestc.zhongbao_android.controller.main.me.feedback.FeedbackActivity;
import com.edu.uestc.zhongbao_android.controller.main.me.info.InfoActivity;
import com.edu.uestc.zhongbao_android.controller.main.me.my_collection.MyCollectionActivity;
import com.edu.uestc.zhongbao_android.controller.main.me.my_locus.MyLocusActivity;
import com.edu.uestc.zhongbao_android.controller.main.me.my_orders.MyOrdersActivity;
import com.edu.uestc.zhongbao_android.controller.main.me.setting.SettingActivity;
import com.edu.uestc.zhongbao_android.utils.Base64Util;
import com.edu.uestc.zhongbao_android.utils.ImageLoadManager;
import com.edu.uestc.zhongbao_android.utils.UserManager;
import com.edu.uestc.zhongbao_android.view.ModelView;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * Created by zhu on 17/3/21.
 */

public class MeFragment extends BaseFragment {
    @BindView(R.id.iconView)
    ImageView iconView;

    @BindView(R.id.nameView)
    TextView nameView;

    @BindView(R.id.sexView)
    TextView sexView;

    @BindView(R.id.ageView)
    TextView ageView;

    @OnClick({R.id.modelView1, R.id.modelView2, R.id.modelView3, R.id.modelView4, R.id.modelView5})
    void settingButtonClick(ModelView sender) {
        Intent intent = null;
        if (!UserManager.shareManager(mActivity).getHasLogin()) {
            intent = new Intent();
            intent.setAction("login");
            mActivity.sendBroadcast(intent);
            return;
        }

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
        if (!UserManager.shareManager(mActivity).getHasLogin()) {
            Intent intent = new Intent();
            intent.setAction("login");
            mActivity.sendBroadcast(intent);
            return;
        }
        mActivity.startActivity(new Intent(mActivity, InfoActivity.class));
    }

    @Override
    protected int loadLayout() {
        return R.layout.fragment_me;
    }

    @Override
    public void onResume() {
        super.onResume();
        setupHeadView();
    }

    public void setupHeadView() {
        if (UserManager.shareManager(mActivity).getHasLogin()) {
            if (UserManager.shareManager(mActivity).getHeadpicBase64() != "") {
                iconView.setImageBitmap(Base64Util.base64ToBitmap(UserManager.shareManager(mActivity).getHeadpicBase64()));
            } else {
                ImageLoadManager.shareManager().displayImage(Constant.getMainImageUrl()+UserManager.shareManager(mActivity).getHeadpic(), iconView);
            }
            nameView.setText(UserManager.shareManager(mActivity).getNickname());
            sexView.setText("性别: "+(UserManager.shareManager(mActivity).getGender().equals("0")?"男":"女"));
            ageView.setText("生日: "+UserManager.shareManager(mActivity).getBirthday());
        } else {
            nameView.setText("请点击登录");
            sexView.setText("");
            ageView.setText("");
        }
    }
}
