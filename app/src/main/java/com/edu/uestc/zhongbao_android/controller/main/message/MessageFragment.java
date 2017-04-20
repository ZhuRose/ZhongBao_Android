package com.edu.uestc.zhongbao_android.controller.main.message;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.widget.SwipeRefreshLayout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ListView;

import com.edu.uestc.zhongbao_android.R;
import com.edu.uestc.zhongbao_android.controller.base.BaseFragment;
import com.edu.uestc.zhongbao_android.controller.main.message.detail.WebActivity;
import com.edu.uestc.zhongbao_android.holder.MessageViewHolder;
import com.edu.uestc.zhongbao_android.model.MessageInfoModel;
import com.edu.uestc.zhongbao_android.model.MessageModel;
import com.edu.uestc.zhongbao_android.utils.NetworkUtil;
import com.edu.uestc.zhongbao_android.view.LoadMoreListView;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import in.srain.cube.views.ptr.PtrClassicFrameLayout;
import in.srain.cube.views.ptr.PtrDefaultHandler;
import in.srain.cube.views.ptr.PtrFrameLayout;
import in.srain.cube.views.ptr.PtrHandler;

/**
 * Created by zhu on 17/3/21.
 */

public class MessageFragment extends BaseFragment {

    @BindView(R.id.refreshView)
    SwipeRefreshLayout refreshView;

    @BindView(R.id.list)
    LoadMoreListView listView;

    MessageAdapter adapter;

    NetworkUtil networkUtil;
    List<MessageInfoModel> dataSource;
    int page;


    @Override
    protected int loadLayout() {
        return R.layout.fragment_message;
    }

    @Override
    protected void initData(Bundle bundle) {
        super.initData(bundle);
        page = 1;
        dataSource = new ArrayList<MessageInfoModel>();
        networkUtil = new NetworkUtil(mActivity) {
            @Override
            public void successNetwork(Object object, String tag) {
                refreshView.setRefreshing(false);
                MessageModel model = (MessageModel)object;
                if (page==1) dataSource.clear();
                if (model.news!=null) {
                    dataSource.addAll(model.news);
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
        adapter = new MessageAdapter();
        listView.setAdapter(adapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                if (position>=dataSource.size()) return;
                MessageInfoModel model = dataSource.get(position);
                Intent intent = new Intent(mActivity, WebActivity.class);
                intent.putExtra("url", model.uuid);
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
        networkUtil.messageNetwork(""+page);
    }

    class MessageAdapter extends BaseAdapter {
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
                MessageViewHolder holder;
                MessageInfoModel model = dataSource.get(position);
                if (convertView == null) {
                    convertView = LayoutInflater.from(mActivity).inflate(R.layout.cell_message, null);
                    holder = new MessageViewHolder(convertView);
                    convertView.setTag(holder);
                } else {
                    holder = (MessageViewHolder)convertView.getTag();
                }
                holder.setViews(model.picture, model.title, model.date);
                return convertView;
            }
    }
}
