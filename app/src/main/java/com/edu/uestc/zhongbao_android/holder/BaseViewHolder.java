package com.edu.uestc.zhongbao_android.holder;

import android.view.View;
import android.widget.TextView;

import com.edu.uestc.zhongbao_android.R;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by zhu on 17/4/2.
 */

public class BaseViewHolder {
    @BindView(R.id.titleView)
    TextView titleView;

    public BaseViewHolder(View view) {
        ButterKnife.bind(this, view);
    }

    public void setTitle(String title, int color) {
        titleView.setText(title);
        titleView.setTextColor(color);
    }

    public void setTitle(String title) {
        titleView.setText(title);
    }
}
