package com.edu.uestc.zhongbao_android.controller.main.sport.detail;

import android.support.constraint.ConstraintLayout;
import android.support.v4.widget.SwipeRefreshLayout;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ListView;

import com.edu.uestc.zhongbao_android.R;
import com.edu.uestc.zhongbao_android.application.Constant;
import com.edu.uestc.zhongbao_android.controller.base.BaseActivity;
import com.edu.uestc.zhongbao_android.controller.main.home.detail.all_comments.AllCommentsActivity;
import com.edu.uestc.zhongbao_android.holder.CommentViewHolder;
import com.edu.uestc.zhongbao_android.holder.SportCommentViewHolder;
import com.edu.uestc.zhongbao_android.holder.SportViewHolder;
import com.edu.uestc.zhongbao_android.model.SportsCityCommentsDetailModel;
import com.edu.uestc.zhongbao_android.model.SportsCityCommentsModel;
import com.edu.uestc.zhongbao_android.model.SportsCityDetailModel;
import com.edu.uestc.zhongbao_android.model.SportsCityModel;
import com.edu.uestc.zhongbao_android.utils.AndroidBug5497Workaround;
import com.edu.uestc.zhongbao_android.utils.NetworkUtil;
import com.edu.uestc.zhongbao_android.utils.SoftKeyboardUtil;
import com.edu.uestc.zhongbao_android.view.InputView;
import com.edu.uestc.zhongbao_android.view.InputViewDelegate;
import com.edu.uestc.zhongbao_android.view.LoadMoreListView;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

/**
 * Created by zhu on 17/4/11.
 */

public class SportDetailActivity extends BaseActivity {
    String uuid;
    SportsCityDetailModel detailModel;

    View header;
    SportCommentAdapter adapter;

    View sportCell;
    SportViewHolder sportViewHolder;

    @BindView(R.id.refreshView)
    SwipeRefreshLayout refreshView;

    @BindView(R.id.list)
    LoadMoreListView listView;

    NetworkUtil networkUtil;
    List<SportsCityCommentsDetailModel> dataSource;
    int page;

    @BindView(R.id.inputView)
    InputView inputView;

    @Override
    protected int loadLayout() {
        return R.layout.activity_detail_sport;
    }

    @Override
    protected void initView() {
        super.initView();

        header = LayoutInflater.from(mContext).inflate(R.layout.header_list_detail_sport, null);
        sportCell = header.findViewById(R.id.sportCell);
        sportViewHolder = new SportViewHolder(sportCell);
        sportViewHolder.setViews(detailModel.headpic, detailModel.nickname, detailModel.content, detailModel.create_time, detailModel.number, detailModel.site, detailModel.pictures);

        ListView.LayoutParams layoutParams = new ListView.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        header.setLayoutParams(layoutParams);
        listView.addHeaderView(header);

        adapter = new SportCommentAdapter();
        listView.setAdapter(adapter);

        inputView.setEditText("", "请输入评论");
        inputView.setDelegate(new InputViewDelegate() {
            @Override
            public void getInputContent(String content, String tag) {
                inputView.hideKeyBoard();
                inputView.setEditText("", "请输入评论");
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

    @Override
    protected void initData() {
        super.initData();
        detailModel = getIntent().getParcelableExtra("model");
        uuid = detailModel.uuid;

        AndroidBug5497Workaround.assistActivity(mContext);
        SoftKeyboardUtil.observeSoftKeyboard(mContext, new SoftKeyboardUtil.OnSoftKeyboardChangeListener() {
            @Override
            public void onSoftKeyBoardChange(int softKeybardHeight, boolean visible) {
                Log.v("info", "keyboard"+softKeybardHeight+visible);
                ConstraintLayout.LayoutParams params =  (ConstraintLayout.LayoutParams)inputView.getLayoutParams();
                if (!visible) {
                    params.setMargins(0, 0, 0, Constant.getVirtualBarHeight(mContext));
                } else {
                    params.setMargins(0, 0, 0, 0);
                }
                inputView.requestLayout();

            }
        });

        dataSource = new ArrayList<SportsCityCommentsDetailModel>();
        networkUtil = new NetworkUtil(mContext) {
            @Override
            public void successNetwork(Object object, String tag) {
                refreshView.setRefreshing(false);
                SportsCityCommentsModel model = (SportsCityCommentsModel)object;
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

    public void getResponse() {
        networkUtil.sportsCityCommentsNetwork(uuid, ""+page);
    }

    class SportCommentAdapter extends BaseAdapter {

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
            SportCommentViewHolder viewHolder;
            SportsCityCommentsDetailModel model = dataSource.get(position);
            if (convertView == null) {
                convertView = LayoutInflater.from(mContext).inflate(R.layout.cell_comment_sport, parent, false);
                viewHolder = new SportCommentViewHolder(convertView);
                convertView.setTag(viewHolder);
            } else {
                viewHolder = (SportCommentViewHolder)convertView.getTag();
            }
            viewHolder.setViews(model.headpic, model.nickname, model.content, model.create_time);
            return convertView;
        }
    }
}
