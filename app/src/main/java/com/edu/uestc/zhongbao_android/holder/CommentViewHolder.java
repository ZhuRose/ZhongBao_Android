package com.edu.uestc.zhongbao_android.holder;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.edu.uestc.zhongbao_android.R;
import com.edu.uestc.zhongbao_android.view.StarView;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by zhu on 17/4/8.
 */

public class CommentViewHolder {

    @BindView(R.id.iconView)
    ImageView iconView;

    @BindView(R.id.titleView)
    TextView titleView;

    @BindView(R.id.starView)
    StarView starView;

    @BindView(R.id.contentView)
    TextView contentView;

    @BindView(R.id.timeView)
    TextView timeView;

    public CommentViewHolder(View view) {
        ButterKnife.bind(this, view);
    }

    public void setViews(String icon, String title, float score, String content, String time) {
        titleView.setText(title);
        starView.setScore(score);
        contentView.setText(content);
        timeView.setText(time);
    }
}
