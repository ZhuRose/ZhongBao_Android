package com.edu.uestc.zhongbao_android.controller.main.home.search;

import android.content.Context;
import android.content.Intent;
import android.support.v4.widget.SwipeRefreshLayout;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

import com.edu.uestc.zhongbao_android.R;
import com.edu.uestc.zhongbao_android.application.Constant;
import com.edu.uestc.zhongbao_android.controller.base.BaseActivity;
import com.edu.uestc.zhongbao_android.controller.main.home.detail.HomeDetailActivity;
import com.edu.uestc.zhongbao_android.holder.BaseViewHolder;
import com.edu.uestc.zhongbao_android.holder.HomeViewHolder;
import com.edu.uestc.zhongbao_android.model.HomeInfoModel;
import com.edu.uestc.zhongbao_android.model.HomeModel;
import com.edu.uestc.zhongbao_android.utils.NetworkUtil;
import com.edu.uestc.zhongbao_android.utils.UserManager;
import com.edu.uestc.zhongbao_android.view.LoadMoreListView;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import butterknife.BindView;
import butterknife.OnClick;
import butterknife.OnTouch;

/**
 * Created by zhu on 17/5/12.
 */

public class SearchActivity extends BaseActivity {

    HomeAdapter adapter;
    List<HomeInfoModel> dataSource;
    RecordAdapter recordAdapter;
    List<String> searchRecord;
    int page;
    NetworkUtil networkUtil;

    @BindView(R.id.searchEdit)
    EditText searchEdit;

    @BindView(R.id.refreshView)
    SwipeRefreshLayout refreshView;

    @BindView(R.id.resultList)
    LoadMoreListView resultList;

    @BindView(R.id.recordList)
    ListView recordList;

    View footer;

    @OnTouch(R.id.contentLayout)
    public boolean touch(View view, MotionEvent event) {
        if (event.getAction() == MotionEvent.ACTION_DOWN) {
            searchEditLoseFocus();
        }
        return true;
    }

    @OnClick(R.id.searchEdit)
    void searchEditClick(View sender) {
        searchEditGetFocus();
    }

    @OnClick(R.id.searchBtn)
    void searchBtnClick(View sender) {
        searchEditLoseFocus();
        if (String.valueOf(searchEdit.getText()).trim().isEmpty()) return;
        if (!searchRecord.contains(String.valueOf(searchEdit.getText()).trim())) {
            searchRecord.add(String.valueOf(searchEdit.getText()).trim());
            UserManager.shareManager(mContext).setSearchRecord(searchRecord);
            recordAdapter.notifyDataSetChanged();
            if (searchRecord.size() == 1) recordList.addFooterView(footer);
        }
        page = 1;
        getResponse();
    }

    protected void searchEditGetFocus() {
        searchEdit.setFocusable(true);
        searchEdit.setFocusableInTouchMode(true);
        searchEdit.requestFocus();
        searchEdit.requestFocusFromTouch();
        InputMethodManager  inputManager =
                (InputMethodManager)mContext.getSystemService(mContext.INPUT_METHOD_SERVICE);
        inputManager.showSoftInput(searchEdit, 0);

        recordList.setVisibility(View.VISIBLE);
        refreshView.setVisibility(View.INVISIBLE);
    }

    protected void searchEditLoseFocus() {
        searchEdit.setFocusable(false);
        searchEdit.setFocusableInTouchMode(false);
        searchEdit.requestFocus();
        searchEdit.requestFocusFromTouch();
        InputMethodManager imm = (InputMethodManager) mContext.getSystemService(mContext.INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(searchEdit.getWindowToken(), 0);

        recordList.setVisibility(View.INVISIBLE);
        refreshView.setVisibility(View.VISIBLE);
    }

    @Override
    protected int loadLayout() {
        return R.layout.activity_search;
    }

    @Override
    protected void initData() {
        super.initData();
        page = 1;
        searchRecord = UserManager.shareManager(mContext).getSearchRecord();
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
                    resultList.onLoadMoreComplete(3);
                } else {
                    page--;
                    resultList.onLoadMoreComplete(2);
                }
            }

            @Override
            public void failedNetwork(String errorInfo, String tag) {
                refreshView.setRefreshing(false);
                resultList.onLoadMoreComplete(1);
            }
        };
    }

    @Override
    protected void initView() {
        super.initView();
        adapter = new HomeAdapter();
        resultList.setAdapter(adapter);
        resultList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(mContext, HomeDetailActivity.class);
                HomeInfoModel model = dataSource.get(position);
                intent.putExtra("uuid", model.uuid);
                intent.putExtra("pic", model.picture);
                startActivity(intent);
            }
        });
        resultList.setmOnLoadMoreListener(new LoadMoreListView.OnLoadMoreListener() {
            @Override
            public void onLoadMore() {
                page++;
                getResponse();
            }
        });
        resultList.onLoadMoreComplete(1);
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
        refreshView.setVisibility(View.INVISIBLE);

        footer = LayoutInflater.from(mContext).inflate(R.layout.footer_record_clear, null);
        float density = getResources().getDisplayMetrics().density;
        ListView.LayoutParams layoutParams = new ListView.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, (int) (56 * density));
        footer.setLayoutParams(layoutParams);
        recordList.addFooterView(footer);
        recordAdapter = new RecordAdapter();
        recordList.setAdapter(recordAdapter);
        if (searchRecord.size() == 0) recordList.removeFooterView(footer);
        recordList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                if (position == searchRecord.size()) {//清除按钮
                    searchRecord.clear();
                    UserManager.shareManager(mContext).clearSearchRecord();
                    recordAdapter.notifyDataSetChanged();
                    recordList.removeFooterView(footer);
                } else {
                    searchEdit.setText(searchRecord.get(position));
                    searchBtnClick(null);
                }
            }
        });

        searchEdit.setOnEditorActionListener(new TextView.OnEditorActionListener() {

            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                /*判断是否是“GO”键*/
                if(actionId == EditorInfo.IME_ACTION_SEARCH){
                    /*隐藏软键盘*/
                    searchBtnClick(null);

                    return true;
                }
                return false;
            }
        });
    }

    public void getResponse() {
        if (String.valueOf(searchEdit.getText()).trim().isEmpty()) {
            resultList.onLoadMoreComplete(1);
            return;
        }
        networkUtil.searchNetwork(""+page, String.valueOf(searchEdit.getText()).trim());
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

    class RecordAdapter extends BaseAdapter {

        @Override
        public int getCount() {
            return searchRecord.size();
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
            BaseViewHolder viewHolder;
            String name = searchRecord.get(position);
            if (convertView == null) {
                convertView = LayoutInflater.from(mContext).inflate(R.layout.cell_base, null);
                ListView.LayoutParams params = new ListView.LayoutParams(ListView.LayoutParams.MATCH_PARENT,(int)(56* Constant.getDensity(mContext)));
                convertView.setLayoutParams(params);
                viewHolder = new BaseViewHolder(convertView);
                convertView.setTag(viewHolder);
            } else {
                viewHolder = (BaseViewHolder)convertView.getTag();
            }
            viewHolder.setTitle(name);
            return convertView;
        }
    }
}
