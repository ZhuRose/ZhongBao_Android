package com.edu.uestc.zhongbao_android.controller.base;

import android.support.v4.widget.SwipeRefreshLayout;
import android.widget.ListView;

import com.edu.uestc.zhongbao_android.R;
import com.edu.uestc.zhongbao_android.view.LoadMoreListView;

import butterknife.BindView;

/**
 * Created by zhu on 17/4/9.
 */

public class BaseListFragment extends BaseFragment {

    @BindView(R.id.refreshView)
    protected SwipeRefreshLayout refreshView;

    @BindView(R.id.list)
    protected LoadMoreListView list;

    @Override
    protected int loadLayout() {
        return R.layout.fragment_list;
    }
}
