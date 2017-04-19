package com.edu.uestc.zhongbao_android.holder;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.edu.uestc.zhongbao_android.R;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by zhu on 17/4/7.
 */

public class SetNumViewHolder extends RecyclerView.ViewHolder {

    @BindView(R.id.numView)
    TextView numView;

    public SetNumViewHolder(View itemView) {
        super(itemView);
        ButterKnife.bind(this, itemView);
    }

    public void setNum(String num) {
        numView.setText(num + "号场");
    }

    public void setNum(int num) {
        numView.setText("" + num + "号场");
    }
}
