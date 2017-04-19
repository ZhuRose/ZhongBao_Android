package com.edu.uestc.zhongbao_android.holder;

import android.content.Intent;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.edu.uestc.zhongbao_android.R;
import com.edu.uestc.zhongbao_android.controller.main.home.detail.chose_site.order_detail.OrderDetailActivity;
import com.edu.uestc.zhongbao_android.controller.main.me.my_orders.comment.CommentActivity;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by zhu on 17/4/8.
 */

public class OrderViewHolder {

    int status;
    View itemView;

    @BindView(R.id.iconView)
    ImageView iconView;

    @BindView(R.id.titleView)
    TextView titleView;

    @BindView(R.id.priceView)
    TextView priceView;

    @BindView(R.id.statusView)
    TextView statusView;

    @BindView(R.id.timeView)
    TextView timeView;

    @BindView(R.id.actionBtn)
    Button actionButton;

    @OnClick(R.id.actionBtn)
    void onClick(View view) {
        switch (status) {
            case 0:
                itemView.getContext().startActivity(new Intent(itemView.getContext(), OrderDetailActivity.class));
                break;
            case 1:
                itemView.getContext().startActivity(new Intent(itemView.getContext(), OrderDetailActivity.class));
                break;
            default:
                itemView.getContext().startActivity(new Intent(itemView.getContext(), CommentActivity.class));
                break;
        }
    }

    public OrderViewHolder(View view) {
        itemView = view;
        ButterKnife.bind(this, view);
    }

    public void setViews(String iconStr, String title, String price, int status, String time) {
        titleView.setText(title);
        priceView.setText("¥ "+price);
        timeView.setText(time);
        this.status = status;
        String statusStr = "";
        String actionStr = "";
        switch (status) {
            case 0:
                statusStr = "待付款";
                actionStr = "立即付款";
                break;
            case 1:
                statusStr = "待消费";
                actionStr = "订单详情";
                break;
            default:
                statusStr = "待评价";
                actionStr = "立即评价";
                break;
        }
        statusView.setText(statusStr);
        actionButton.setText(actionStr);
    }
}
