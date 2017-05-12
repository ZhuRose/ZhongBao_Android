package com.edu.uestc.zhongbao_android.controller.main.me.my_orders;

import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.util.Log;
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
import com.edu.uestc.zhongbao_android.model.OrderListModel;
import com.edu.uestc.zhongbao_android.model.OrderModel;
import com.edu.uestc.zhongbao_android.model.SportTimeModel;
import com.edu.uestc.zhongbao_android.utils.NetworkUtil;
import com.edu.uestc.zhongbao_android.view.LoadMoreListView;

import java.util.ArrayList;
import java.util.List;

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
    List<OrderModel> dataSource;
    int page;
    NetworkUtil networkUtil;

    @Override
    protected void initData(Bundle bundle) {
        super.initData(bundle);
        orderType = bundle.getInt("OrderType", 0);
        page = 1;
        dataSource = new ArrayList<OrderModel>();
        networkUtil = new NetworkUtil(mActivity) {
            @Override
            public void successNetwork(Object object, String tag) {
                refreshView.setRefreshing(false);
                OrderListModel model = (OrderListModel) object;
                if (page==1) dataSource.clear();
                if (model.orders!=null) {
                    dataSource.addAll(model.orders);
                    adapter.notifyDataSetChanged();
                    list.onLoadMoreComplete(3);
                } else {
                    page--;
                    list.onLoadMoreComplete(2);
                }
            }

            @Override
            public void failedNetwork(String errorInfo, String tag) {
                refreshView.setRefreshing(false);
                list.onLoadMoreComplete(1);
            }
        };
    }

    @Override
    protected void initView() {
        super.initView();
        adapter = new OrdersAdapter();
        list.setAdapter(adapter);
        list.setmOnLoadMoreListener(new LoadMoreListView.OnLoadMoreListener() {
            @Override
            public void onLoadMore() {
                page++;
                getResponse();
            }
        });
        // 设置下拉进度的主题颜色
        refreshView.setColorSchemeResources(R.color.colorTheme);
        // 下拉时触发SwipeRefreshLayout的下拉动画，动画完毕之后就会回调这个方法
        refreshView.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                page=1;
                getResponse();
            }
        });
        getResponse();
    }

    public void getResponse() {
        networkUtil.myOrdersNetwork(""+orderType, ""+page);
    }

    class OrdersAdapter extends BaseAdapter {

        @Override
        public int getCount() {
            return dataSource.size();
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
            OrderModel model = dataSource.get(position);
            if (convertView == null) {
                convertView = LayoutInflater.from(mActivity).inflate(R.layout.cell_order, null);
                holder = new OrderViewHolder(convertView);
                convertView.setTag(holder);
            } else {
                holder = (OrderViewHolder) convertView.getTag();
            }
            holder.setViews(model.picture, model.name, model.price, Integer.parseInt(model.state), model.time, model.uuid);
            return convertView;
        }
    }
}
