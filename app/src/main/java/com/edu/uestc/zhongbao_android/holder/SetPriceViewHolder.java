package com.edu.uestc.zhongbao_android.holder;

import android.graphics.Color;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.edu.uestc.zhongbao_android.R;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by zhu on 17/4/7.
 */

public class SetPriceViewHolder extends RecyclerView.ViewHolder {
    @BindView(R.id.cardView)
    CardView cardView;

    @BindView(R.id.priceView)
    TextView priceView;

    boolean isSelected;
    boolean enable;


    public SetPriceViewHolder(View itemView) {
        super(itemView);
        ButterKnife.bind(this, itemView);
        isSelected = false;
        enable = true;
    }

    public void setViews(String price, boolean enable) {
        this.enable = enable;
        if (enable) {
            cardView.setCardBackgroundColor(itemView.getResources().getColor(R.color.colorUnSelected));
            priceView.setTextColor(itemView.getResources().getColor(R.color.colorText));
            priceView.setText("¥ "+price);
        } else {
            cardView.setCardBackgroundColor(itemView.getResources().getColor(R.color.colorUnable));
            priceView.setTextColor(itemView.getResources().getColor(R.color.colorUnable));
        }
    }

    public void setIsSelected(boolean isSelected) {
        if (!enable) return;
        this.isSelected = isSelected;
        if (!isSelected) {
            cardView.setCardBackgroundColor(itemView.getResources().getColor(R.color.colorUnSelected));
            priceView.setTextColor(itemView.getResources().getColor(R.color.colorText));
        } else {
            cardView.setCardBackgroundColor(itemView.getResources().getColor(R.color.colorSelected));
            priceView.setTextColor(Color.WHITE);
        }
    }

    public boolean getIsSelected() {
        return isSelected;
    }

}
