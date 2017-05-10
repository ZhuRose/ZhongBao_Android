package com.edu.uestc.zhongbao_android.controller.base;

import android.support.v4.widget.SwipeRefreshLayout;
import android.widget.ListView;

import com.edu.uestc.zhongbao_android.R;
import com.edu.uestc.zhongbao_android.view.BaseNavigationView;
import com.edu.uestc.zhongbao_android.view.LoadMoreListView;

import butterknife.BindView;

/**
 * Created by zhu on 17/4/9.
 */

public class BaseListActivity extends BaseActivity {
    @BindView(R.id.navi)
    protected BaseNavigationView navi;

    @BindView(R.id.refreshView)
    protected SwipeRefreshLayout refreshView;

    @BindView(R.id.list)
    protected LoadMoreListView list;

    @Override
    protected int loadLayout() {
        return R.layout.activity_list;
    }


}
