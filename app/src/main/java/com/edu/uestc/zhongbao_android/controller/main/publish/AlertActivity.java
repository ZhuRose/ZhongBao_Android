package com.edu.uestc.zhongbao_android.controller.main.publish;

import android.transition.Fade;
import android.view.View;

import com.edu.uestc.zhongbao_android.R;
import com.edu.uestc.zhongbao_android.controller.base.BaseActivity;

import butterknife.BindView;
import de.hdodenhof.circleimageview.CircleImageView;

/**
 * Created by zhu on 17/3/30.
 */

public class AlertActivity extends BaseActivity {

    @BindView(R.id.imageView)
    CircleImageView imageView;

    @Override
    protected int loadLayout() {
        return R.layout.fragment_alert;
    }

    @Override
    protected void initData() {
        super.initData();
        getWindow().setEnterTransition(new Fade());
        getWindow().setReenterTransition(new Fade());

    }

    @Override
    protected void initView() {
        super.initView();
        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finishAfterTransition();
            }
        });
    }
}
