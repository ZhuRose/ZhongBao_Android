package com.edu.uestc.zhongbao_android.holder;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.edu.uestc.zhongbao_android.R;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by zhu on 17/3/28.
 */

public class GridViewHolder {

    @BindView(R.id.titleView)
    TextView titleView;

    @BindView(R.id.iconView)
    ImageView iconView;


    public GridViewHolder(View view) {
        ButterKnife.bind(this,view);
    }

    public void setViews(int icon, String title) {
        iconView.setImageResource(icon);
        titleView.setText(title);
    }
}
