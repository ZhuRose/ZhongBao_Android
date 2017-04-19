package com.edu.uestc.zhongbao_android.controller.main.home.session_list;

import android.support.v4.app.FragmentTransaction;

import com.edu.uestc.zhongbao_android.R;
import com.edu.uestc.zhongbao_android.controller.base.BaseActivity;
import com.edu.uestc.zhongbao_android.view.DropView;
import com.edu.uestc.zhongbao_android.view.DropViewDataSource;

import butterknife.BindView;

/**
 * Created by zhu on 17/4/6.
 */

public class SessionListActivity extends BaseActivity {

    @BindView(R.id.dropView)
    DropView dropView;

    @Override
    protected int loadLayout() {
        return R.layout.activity_list_session;
    }

    @Override
    protected void initView() {
        super.initView();
        final String[] titles = {"全部区域", "所有运动", "智能排序"};
        dropView.setDataSource(new DropViewDataSource() {
            @Override
            public int numberOfMeuns() {
                return 3;
            }

            @Override
            public String titleForMenu(int menuNum) {
                return titles[menuNum];
            }

            @Override
            public String[] dataSorceIn(int menuNum) {
                String[] strings;
                switch (menuNum) {
                    case 0:
                        strings = new String[]{"全部区域", "高新西区", "高新区", "成华区", "青羊区", "郫县", "金牛区", "锦江区", "武侯区", "天府新区"};
                        break;
                    case 1:
                        strings = new String[]{"所有运动", "羽毛球", "乒乓球", "台球", "篮球", "健身", "足球", "舞蹈", "游泳"};
                        break;
                    default:
                        strings = new String[]{"智能排序", "离我最近", "价格最低", "人气最高", "评价最高"};
                        break;
                }
                return strings;
            }
        });

        initFragment();
    }

    protected void initFragment() {
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.frame_content, new SessionListFragment());
        transaction.commit();
    }

}
