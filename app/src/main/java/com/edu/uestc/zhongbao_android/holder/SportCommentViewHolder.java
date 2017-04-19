package com.edu.uestc.zhongbao_android.holder;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.edu.uestc.zhongbao_android.R;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by zhu on 17/4/11.
 */

public class SportCommentViewHolder {

    @BindView(R.id.iconView)
    ImageView iconView;

    @BindView(R.id.titleView)
    TextView titleView;

    @BindView(R.id.contentView)
    TextView contentView;

    @BindView(R.id.timeView)
    TextView timeView;

    public SportCommentViewHolder(View view) {
        ButterKnife.bind(this, view);
    }
}
