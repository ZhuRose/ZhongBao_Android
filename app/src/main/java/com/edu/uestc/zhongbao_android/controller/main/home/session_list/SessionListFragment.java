package com.edu.uestc.zhongbao_android.controller.main.home.session_list;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ListView;

import com.edu.uestc.zhongbao_android.R;
import com.edu.uestc.zhongbao_android.controller.base.BaseFragment;
import com.edu.uestc.zhongbao_android.controller.base.BaseListFragment;
import com.edu.uestc.zhongbao_android.controller.main.home.detail.HomeDetailActivity;
import com.edu.uestc.zhongbao_android.holder.HomeViewHolder;
import com.edu.uestc.zhongbao_android.model.HomeInfoModel;
import com.edu.uestc.zhongbao_android.model.HomeModel;
import com.edu.uestc.zhongbao_android.model.MessageModel;
import com.edu.uestc.zhongbao_android.utils.NetworkUtil;
import com.edu.uestc.zhongbao_android.view.LoadMoreListView;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

/**
 * Created by zhu on 17/4/6.
 */

public class SessionListFragment extends BaseListFragment {

    SessionListAdapter adapter;
    List<HomeInfoModel> dataSouce;
    NetworkUtil networkUtil;
    int page;
    String region = "";
    String sport = "";
    String sort = "0";

    @Override
    protected void initData(Bundle bundle) {
        super.initData(bundle);
        page = 1;
        dataSouce = new ArrayList<HomeInfoModel>();
        networkUtil = new NetworkUtil(mActivity) {
            @Override
            public void successNetwork(Object object, String tag) {
                refreshView.setRefreshing(false);
                HomeModel model = (HomeModel) object;
                if (page==1) dataSouce.clear();
                if (model.result!=null) {
                    dataSouce.addAll(model.result);
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
        adapter = new SessionListAdapter();
//        list.setDividerHeight((int)getResources().getDisplayMetrics().density);
        list.setAdapter(adapter);
        list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(mActivity, HomeDetailActivity.class);
                HomeInfoModel model = dataSouce.get(position);
                intent.putExtra("uuid", model.uuid);
                intent.putExtra("pic", model.picture);
                startActivity(intent);
            }
        });
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

    public void setCondition(String region, String sport, String sort) {
        this.region = region;
        this.sport = sport;
        this.sort = sort;
        if (dataSouce != null) {
            page = 1;
            dataSouce.clear();
        }
    }

    public void getResponse() {
        networkUtil.searchWithConditionNetwork(""+page, region, sport, sort);
    }

    class SessionListAdapter extends BaseAdapter {

        @Override
        public int getCount() {
            return dataSouce.size();
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
            HomeViewHolder holder;
            HomeInfoModel model = dataSouce.get(position);
            if (convertView == null) {
                convertView = LayoutInflater.from(mActivity).inflate(R.layout.cell_home, null);
                holder = new HomeViewHolder(convertView);
                convertView.setTag(holder);
            } else {
                holder = (HomeViewHolder) convertView.getTag();
            }
            holder.setViews(model.picture, model.name, Float.valueOf(model.score), model.sport+" "+model.distance, model.price);
            return convertView;
        }
    }
}
