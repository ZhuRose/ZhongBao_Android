package com.edu.uestc.zhongbao_android.controller.main.message;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
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
    PtrClassicFrameLayout refreshView;

    @BindView(R.id.list)
    LoadMoreListView listView;

    MessageAdapter adapter;

    NetworkUtil networkUtil;
    List<MessageInfoModel> dataSource;
    int page;

    Handler mHandler;

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
                refreshView.refreshComplete();
                listView.onLoadMoreComplete();
                MessageModel model = (MessageModel)object;
                if (model.news!=null) {
                    dataSource.addAll(model.news);
                    adapter.notifyDataSetChanged();
                } else {
                    page--;
                }

            }

            @Override
            public void failedNetwork(String errorInfo, String tag) {
                refreshView.refreshComplete();
                listView.onLoadMoreComplete();
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
                MessageInfoModel model = dataSource.get(position);
                Intent intent = new Intent(mActivity, WebActivity.class);
                intent.putExtra("url", model.uuid);
                startActivity(intent);
            }
        });
        mHandler = new Handler();
        listView.setmOnLoadMoreListener(new LoadMoreListView.OnLoadMoreListener() {
            @Override
            public void onLoadMore() {
                mHandler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        listView.setLoadMoreViewTextNoMoreData();
                    }
                }, 3000);
            }
        });
        refreshView.setKeepHeaderWhenRefresh(true);
        refreshView.setPtrHandler(new PtrDefaultHandler() {
            @Override
            public void onRefreshBegin(PtrFrameLayout frame) {
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
