package com.edu.uestc.zhongbao_android.holder;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.edu.uestc.zhongbao_android.R;
import com.edu.uestc.zhongbao_android.application.Constant;
import com.edu.uestc.zhongbao_android.utils.ImageLoadManager;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by zhu on 17/3/28.
 */

public class MessageViewHolder {
    @BindView(R.id.iconView)
    ImageView iconView;

    @BindView(R.id.titleView)
    TextView titleView;

    @BindView(R.id.timeView)
    TextView timeView;

    public MessageViewHolder(View view){
        ButterKnife.bind(this, view);
    }

    public void setViews(String iconStr, String title, String time) {
        ImageLoadManager.shareManager().displayImage(Constant.getMainImageUrl()+iconStr, iconView);
        titleView.setText(title);
        timeView.setText(time);
    }
}
