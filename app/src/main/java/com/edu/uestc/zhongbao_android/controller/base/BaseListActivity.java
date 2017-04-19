package com.edu.uestc.zhongbao_android.controller.base;

import android.widget.ListView;

import com.edu.uestc.zhongbao_android.R;
import com.edu.uestc.zhongbao_android.view.BaseNavigationView;

import butterknife.BindView;

/**
 * Created by zhu on 17/4/9.
 */

public class BaseListActivity extends BaseActivity {
    @BindView(R.id.navi)
    protected BaseNavigationView navi;

    @BindView(R.id.list)
    protected ListView list;

    @Override
    protected int loadLayout() {
        return R.layout.activity_list;
    }
}
