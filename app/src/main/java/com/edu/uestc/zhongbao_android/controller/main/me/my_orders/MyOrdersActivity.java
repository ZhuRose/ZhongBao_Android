package com.edu.uestc.zhongbao_android.controller.main.me.my_orders;

import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;

import com.edu.uestc.zhongbao_android.R;
import com.edu.uestc.zhongbao_android.controller.base.BaseActivity;
import com.edu.uestc.zhongbao_android.controller.main.home.HomePagerFragment;
import com.edu.uestc.zhongbao_android.controller.main.home.session_list.SessionListFragment;

import butterknife.BindView;

/**
 * Created by zhu on 17/4/8.
 */

public class MyOrdersActivity extends BaseActivity {

    @BindView(R.id.tab)
    TabLayout tab;

    @BindView(R.id.pager)
    ViewPager pager;

    MyOrdersPagerAdapter adapter;

    MyOrdersFragment[] fragments = {new MyOrdersFragment(), new MyOrdersFragment(), new MyOrdersFragment()};
    String[] titles = {"待付款", "待消费", "待评价"};

    @Override
    protected int loadLayout() {
        return R.layout.activity_orders_my;
    }

    @Override
    protected void initView() {
        super.initView();
        adapter = new MyOrdersPagerAdapter(getSupportFragmentManager());
        pager.setAdapter(adapter);
        tab.setupWithViewPager(pager);

    }

    class MyOrdersPagerAdapter extends FragmentPagerAdapter {

        public MyOrdersPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            MyOrdersFragment fragment = fragments[position];
            Bundle args = new Bundle();
            args.putInt("OrderType", position);
            fragment.setArguments(args);
            return fragments[position];
        }

        @Override
        public int getCount() {
            return 3;
        }

        //重写这个方法，将设置每个Tab的标题
        @Override
        public CharSequence getPageTitle(int position) {
            return titles[position];
        }
    }
}
