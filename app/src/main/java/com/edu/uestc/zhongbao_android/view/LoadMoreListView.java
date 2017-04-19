package com.edu.uestc.zhongbao_android.view;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.AbsListView;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.edu.uestc.zhongbao_android.R;

import java.util.List;

/**
 * Created by zhu on 17/4/12.
 */

public class LoadMoreListView extends ListView implements AbsListView.OnScrollListener {

    Boolean mIsLoadingMore = false;
    Boolean mLastItemVisible;
    OnScrollListener mOnScrollListener;
    public OnLoadMoreListener mOnLoadMoreListener;
    RelativeLayout mFooterView;
    ProgressBar mProgressBar;
    TextView mTextView;

    public void setmOnScrollListener(OnScrollListener mOnScrollListener) {
        this.mOnScrollListener = mOnScrollListener;
    }


    public LoadMoreListView(Context context) {
        super(context);
        init();
    }

    public LoadMoreListView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public LoadMoreListView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    public LoadMoreListView(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        init();
    }

    private void init() {

        mFooterView = (RelativeLayout) LayoutInflater.from(getContext()).inflate(R.layout.layout_load_more, null, false);
        mProgressBar = (ProgressBar) mFooterView.findViewById(R.id.progress_bar);
        mTextView = (TextView) mFooterView.findViewById(R.id.text);

        removeFooterView(mFooterView);
        setFooterDividersEnabled(false);
        addFooterView(mFooterView);
        super.setOnScrollListener(this);
    }

    @Override
    public void setAdapter(ListAdapter adapter) {
        super.setAdapter(adapter);
    }

    @Override
    public void onScrollStateChanged(AbsListView view, int scrollState) {

        if (!mIsLoadingMore && scrollState == OnScrollListener.SCROLL_STATE_IDLE && mLastItemVisible) {
            mIsLoadingMore = true;
            onLoadMore(); //加载一次数据  接口回调
        }
        if( mOnScrollListener != null ) {

            mOnScrollListener.onScrollStateChanged(view,scrollState);
        }
    }

    public void setmOnLoadMoreListener(OnLoadMoreListener mOnLoadMoreListener) {
        this.mOnLoadMoreListener = mOnLoadMoreListener;
    }

    private void onLoadMore() {
        if(mIsLoadingMore != null) {
//            this.addFooterView(mFooterView);
//            mFooterView.setVisibility(VISIBLE);
            setLoadMoreViewText("正在载入");
            mOnLoadMoreListener.onLoadMore();
        }

    }

    /**
     * Set whether the Last Item is Visible. lastVisibleItemIndex is a
     * zero-based index, so we minus one totalItemCount to check
     */
    @Override
    public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {

        if (null != mOnLoadMoreListener) {
            mLastItemVisible = (totalItemCount > 0) && (firstVisibleItem + visibleItemCount >= totalItemCount - 1);
        }

        // Finally call OnScrollListener if we have one
        if (mOnScrollListener != null) {

            mOnScrollListener.onScroll(view, firstVisibleItem, visibleItemCount, totalItemCount);
        }

    }

    public void updateLoadMoreViewText (List data) {

        if(getAdapter().getCount() == 0 || data.isEmpty()) {

            setLoadMoreViewTextNoData();
        } else if (data.size() < 10) {
            //每一页要加载的item的数量，当最后一次加载的数据小于这个值后，设置以加载全部。
            setLoadMoreViewTextNoMoreData();

        }

        mIsLoadingMore = false;
    }


    public void onLoadMoreComplete(){
//        this.removeFooterView(mFooterView);
//        mFooterView.setVisibility(GONE);
        mIsLoadingMore = false;
    }

    public void setLoadMoreViewTextError() {
        mTextView.setText("网络异常，加载重试");
        mProgressBar.setVisibility(GONE);
        mIsLoadingMore = false;
    }

    public void setLoadMoreViewTextNoData() {
        mTextView.setText("暂无数据");
        mProgressBar.setVisibility(GONE);
        mIsLoadingMore = false;
    }

    public void setLoadMoreViewTextNoMoreData() {
        mTextView.setText("已加载全部");
        mProgressBar.setVisibility(GONE);
        mIsLoadingMore = false;
    }

    public void setLoadMoreViewText(String text) {
        mTextView.setText(text);
        mProgressBar.setVisibility(VISIBLE);
    }

    public void setLoadMoreViewText(int resId) {
        mTextView.setText(resId);
        mProgressBar.setVisibility(VISIBLE);
    }

    public interface OnLoadMoreListener {
        void onLoadMore();
    }

}
