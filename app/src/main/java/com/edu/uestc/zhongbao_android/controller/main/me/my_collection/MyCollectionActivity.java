package com.edu.uestc.zhongbao_android.controller.main.me.my_collection;

import android.content.Intent;
import android.support.v4.widget.SwipeRefreshLayout;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;

import com.edu.uestc.zhongbao_android.R;
import com.edu.uestc.zhongbao_android.controller.base.BaseListActivity;
import com.edu.uestc.zhongbao_android.controller.main.home.detail.HomeDetailActivity;
import com.edu.uestc.zhongbao_android.holder.HomeViewHolder;
import com.edu.uestc.zhongbao_android.model.HomeInfoModel;
import com.edu.uestc.zhongbao_android.model.HomeModel;
import com.edu.uestc.zhongbao_android.utils.NetworkUtil;
import com.edu.uestc.zhongbao_android.view.LoadMoreListView;

import java.util.ArrayList;
import java.util.List;


/**
 * Created by zhu on 17/4/9.
 */

public class MyCollectionActivity extends BaseListActivity {

    HomeAdapter adapter;
    List<HomeInfoModel> dataSource;
    int page;
    NetworkUtil networkUtil;

    @Override
    protected void initData() {
        super.initData();
        page = 1;
        dataSource = new ArrayList<HomeInfoModel>();
        networkUtil = new NetworkUtil(mContext) {
            @Override
            public void successNetwork(Object object, String tag) {
                refreshView.setRefreshing(false);
                HomeModel model = (HomeModel) object;
                if (page==1) dataSource.clear();
                if (model.result!=null) {
                    dataSource.addAll(model.result);
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
        navi.setTitle("我喜欢的");
        adapter = new HomeAdapter();
        list.setAdapter(adapter);
        list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(mContext, HomeDetailActivity.class);
                HomeInfoModel model = dataSource.get(position);
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
    }

    @Override
    protected void onResume() {
        super.onResume();
        page=1;
        getResponse();
    }

    public void getResponse() {
        networkUtil.myAttentionNetwork(""+page);
    }

    class HomeAdapter extends BaseAdapter {

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
            HomeViewHolder holder;
            HomeInfoModel model = dataSource.get(position);
            if (convertView == null) {
                convertView = LayoutInflater.from(mContext).inflate(R.layout.cell_home, null);
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
