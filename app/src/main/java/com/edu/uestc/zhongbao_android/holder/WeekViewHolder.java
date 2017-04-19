package com.edu.uestc.zhongbao_android.holder;

import android.graphics.Color;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.edu.uestc.zhongbao_android.R;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by zhu on 17/4/7.
 */

public class WeekViewHolder extends RecyclerView.ViewHolder {

    @BindView(R.id.weekView)
    TextView weekView;

    @BindView(R.id.dateView)
    TextView dateView;

    @BindView(R.id.weekLine)
    View weekLine;

    protected boolean isSelected;

    public WeekViewHolder(View itemView) {
        super(itemView);
        ButterKnife.bind(this, itemView);
        isSelected = false;
    }

    public void setViews(String week, String date) {
        weekView.setText(week);
        dateView.setText(date);
    }

    public void setIsSelected(boolean isSelected) {
        this.isSelected = isSelected;
        if (isSelected) {
            weekView.setTextColor(itemView.getResources().getColor(R.color.colorSelected));
            dateView.setTextColor(itemView.getResources().getColor(R.color.colorSelected));
            weekLine.setBackgroundColor(itemView.getResources().getColor(R.color.colorSelected));
        } else {
            weekView.setTextColor(Color.BLACK);
            dateView.setTextColor(itemView.getResources().getColor(R.color.colorText));
            weekLine.setBackgroundColor(itemView.getResources().getColor(R.color.colorUnable));
        }
    }


    public boolean getIsSelected() {
        return isSelected;
    }
}
