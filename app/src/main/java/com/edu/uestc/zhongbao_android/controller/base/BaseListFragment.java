package com.edu.uestc.zhongbao_android.controller.base;

import android.widget.ListView;

import com.edu.uestc.zhongbao_android.R;

import butterknife.BindView;

/**
 * Created by zhu on 17/4/9.
 */

public class BaseListFragment extends BaseFragment {

    @BindView(R.id.list)
    protected ListView list;

    @Override
    protected int loadLayout() {
        return R.layout.fragment_list;
    }
}
