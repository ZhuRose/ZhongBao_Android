package com.edu.uestc.zhongbao_android.holder;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.edu.uestc.zhongbao_android.R;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by zhu on 17/4/9.
 */

public class LocusViewHolder {

    @BindView(R.id.iconView)
    ImageView iconView;

    @BindView(R.id.timeView)
    TextView timeView;

    @BindView(R.id.sportView)
    TextView sportView;

    @BindView(R.id.sessionView)
    TextView sessionView;

    public LocusViewHolder(View view) {
        ButterKnife.bind(this, view);
    }

    public void setViews(String time, String sport, String session) {
        timeView.setText(time);
        sportView.setText(sport);
        sessionView.setText(session);
    }
}
