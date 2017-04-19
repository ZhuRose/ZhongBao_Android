package com.edu.uestc.zhongbao_android.controller.main.me.my_orders;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ListView;

import com.edu.uestc.zhongbao_android.R;
import com.edu.uestc.zhongbao_android.controller.base.BaseFragment;
import com.edu.uestc.zhongbao_android.controller.base.BaseListFragment;
import com.edu.uestc.zhongbao_android.holder.HomeViewHolder;
import com.edu.uestc.zhongbao_android.holder.OrderViewHolder;

import butterknife.BindView;

/**
 * Created by zhu on 17/4/8.
 */

public class MyOrdersFragment extends BaseListFragment {

    public static final int OrderType_Unpayed = 0;
    public static final int OrderType_Unconsumed = 1;
    public static final int OrderType_Unevaluated = 2;

    int orderType;

    OrdersAdapter adapter;

    @Override
    protected void initData(Bundle bundle) {
        super.initData(bundle);
        orderType = bundle.getInt("OrderType", 0);
    }

    @Override
    protected void initView() {
        super.initView();
        adapter = new OrdersAdapter();
        list.setAdapter(adapter);

    }

    class OrdersAdapter extends BaseAdapter {

        @Override
        public int getCount() {
            return 6;
        }

        @Override
        public Object getItem(int position) {
            return position;
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            OrderViewHolder holder;
            if (convertView == null) {
                convertView = LayoutInflater.from(mActivity).inflate(R.layout.cell_order, null);
                holder = new OrderViewHolder(convertView);
                convertView.setTag(holder);
            } else {
                holder = (OrderViewHolder) convertView.getTag();
            }
            holder.setViews("", "标题标题就是标题", "100", orderType, "2017-10-16");
            return convertView;
        }
    }
}
