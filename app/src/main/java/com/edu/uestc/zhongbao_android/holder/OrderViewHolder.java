package com.edu.uestc.zhongbao_android.holder;

import android.content.Intent;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.edu.uestc.zhongbao_android.R;
import com.edu.uestc.zhongbao_android.application.Constant;
import com.edu.uestc.zhongbao_android.controller.main.home.detail.chose_site.order_detail.OrderDetailActivity;
import com.edu.uestc.zhongbao_android.controller.main.me.my_orders.comment.CommentActivity;
import com.edu.uestc.zhongbao_android.model.SportTimeModel;
import com.edu.uestc.zhongbao_android.utils.ImageLoadManager;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by zhu on 17/4/8.
 */

public class OrderViewHolder {

    int status;
    View itemView;
    String uuid;

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
        Intent intent = null;
        switch (status) {
            case 0:
                intent = new Intent(itemView.getContext(), OrderDetailActivity.class);
                break;
            case 1:
                intent = new Intent(itemView.getContext(), OrderDetailActivity.class);
                break;
            case 2:
                intent = new Intent(itemView.getContext(), CommentActivity.class);
                break;
            default:
                break;
        }
        intent.putExtra("uuid", uuid);
        intent.putExtra("status", status);
        itemView.getContext().startActivity(intent);
    }

    public OrderViewHolder(View view) {
        itemView = view;
        ButterKnife.bind(this, view);
    }

    public void setViews(String iconStr, String title, String price, int status, String time, String uuid) {
        ImageLoadManager.shareManager().displayImage(Constant.getMainImageUrl()+iconStr, iconView);
        titleView.setText(title);
        priceView.setText("¥ "+price);
        timeView.setText(time);
        this.status = status;
        this.uuid = uuid;
        String statusStr = "";
        String actionStr = "";
        actionButton.setVisibility(View.VISIBLE);
        switch (status) {
            case 0:
                statusStr = "待付款";
                actionStr = "立即付款";
                break;
            case 1:
                statusStr = "待消费";
                actionStr = "订单详情";
                break;
            case  2:
                statusStr = "待评价";
                actionStr = "立即评价";
                break;
            default:
                statusStr = "退款中";
                actionStr = "";
                actionButton.setVisibility(View.GONE);
                break;
        }
        statusView.setText(statusStr);
        actionButton.setText(actionStr);
    }
}
