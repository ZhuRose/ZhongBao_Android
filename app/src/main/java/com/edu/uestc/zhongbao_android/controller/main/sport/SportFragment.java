package com.edu.uestc.zhongbao_android.controller.main.sport;

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
import com.edu.uestc.zhongbao_android.controller.main.sport.detail.SportDetailActivity;
import com.edu.uestc.zhongbao_android.holder.MessageViewHolder;
import com.edu.uestc.zhongbao_android.holder.SportViewHolder;
import com.edu.uestc.zhongbao_android.model.MessageInfoModel;
import com.edu.uestc.zhongbao_android.model.MessageModel;
import com.edu.uestc.zhongbao_android.model.SportsCityDetailModel;
import com.edu.uestc.zhongbao_android.model.SportsCityModel;
import com.edu.uestc.zhongbao_android.utils.NetworkUtil;
import com.edu.uestc.zhongbao_android.view.LoadMoreListView;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

/**
 * Created by zhu on 17/3/21.
 */

public class SportFragment extends BaseFragment {

    @BindView(R.id.refreshView)
    SwipeRefreshLayout refreshView;

    @BindView(R.id.list)
    LoadMoreListView listView;

    SportAdapter adapter;

    NetworkUtil networkUtil;
    List<SportsCityDetailModel> dataSource;
    int page;

    @Override
    protected int loadLayout() {
        return R.layout.fragment_sports;
    }

    @Override
    protected void initData(Bundle bundle) {
        super.initData(bundle);
        page = 1;
        dataSource = new ArrayList<SportsCityDetailModel>();
        networkUtil = new NetworkUtil(mActivity) {
            @Override
            public void successNetwork(Object object, String tag) {
                refreshView.setRefreshing(false);
                SportsCityModel model = (SportsCityModel)object;
                if (page==1) dataSource.clear();
                if (model.info!=null) {
                    dataSource.addAll(model.info);
                    adapter.notifyDataSetChanged();
                    listView.onLoadMoreComplete(3);
                } else {
                    page--;
                    listView.onLoadMoreComplete(2);
                }

            }

            @Override
            public void failedNetwork(String errorInfo, String tag) {
                refreshView.setRefreshing(false);
                listView.onLoadMoreComplete(1);
            }
        };
    }

    @Override
    protected void initView() {
        super.initView();

        adapter = new SportAdapter();
        listView.setAdapter(adapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                if (position>=dataSource.size()) return;
                Intent intent = new Intent(mActivity, SportDetailActivity.class);
                SportsCityDetailModel model = dataSource.get(position);
                intent.putExtra("model", model);
                startActivity(intent);
            }
        });

        listView.setmOnLoadMoreListener(new LoadMoreListView.OnLoadMoreListener() {
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
        networkUtil.sportsCityNetwork(""+page);
    }

    class SportAdapter extends BaseAdapter {
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
            SportViewHolder holder;
            SportsCityDetailModel model = dataSource.get(position);
            if (convertView == null) {
                convertView = LayoutInflater.from(mActivity).inflate(R.layout.cell_sport, null);
                holder = new SportViewHolder(convertView);
                convertView.setTag(holder);
            } else {
                holder = (SportViewHolder) convertView.getTag();
            }
            holder.setViews(model.headpic, model.nickname, model.content, model.create_time, model.number, model.site, model.pictures);
            convertView.requestLayout();
            return convertView;
        }
    }
}
