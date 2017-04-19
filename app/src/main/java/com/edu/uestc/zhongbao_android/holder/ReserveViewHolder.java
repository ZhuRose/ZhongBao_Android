package com.edu.uestc.zhongbao_android.holder;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.edu.uestc.zhongbao_android.R;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by zhu on 17/4/6.
 */

public class ReserveViewHolder extends RecyclerView.ViewHolder{

    @BindView(R.id.weekView)
    TextView weekView;

    @BindView(R.id.dateView)
    TextView dateView;

    @BindView(R.id.reserveBtn)
    Button reserveBtn;

    public ReserveViewHolder(View itemView) {
        super(itemView);
        ButterKnife.bind(this, itemView);
    }

    public void setViews(String week, String date, View.OnClickListener listener) {
        weekView.setText(week);
        dateView.setText(date);
        reserveBtn.setOnClickListener(listener);
    }
}
