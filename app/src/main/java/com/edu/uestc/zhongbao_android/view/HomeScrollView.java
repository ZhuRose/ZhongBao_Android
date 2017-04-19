package com.edu.uestc.zhongbao_android.view;

import android.content.Context;
import android.support.constraint.ConstraintLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;

import com.edu.uestc.zhongbao_android.R;
import com.edu.uestc.zhongbao_android.controller.main.MainActivity;
import com.edu.uestc.zhongbao_android.controller.main.home.HomePagerFragment;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by zhu on 17/3/26.
 */

public class HomeScrollView extends ConstraintLayout {
    @BindView(R.id.pager)
    ViewPager pager;

    @BindView(R.id.indicators)
    PageController indicators;

    int currentPage = 0;

    MainActivity mActivity;

    HomePagerFragment[] fragments = {new HomePagerFragment(), new HomePagerFragment(), new HomePagerFragment()};

    public HomeScrollView(Context context) {
        super(context, null);
    }

    public HomeScrollView(Context context, AttributeSet attrs) {
        super(context, attrs);
        mActivity = (MainActivity) context;
        View view = LayoutInflater.from(context).inflate(R.layout.view_scroll_home, this);
        ButterKnife.bind(this, view);

        indicators.setTotalPages(1, currentPage);
        indicators.setVisibility(GONE);
        pager.setAdapter(new HomePagerAdapter(mActivity.getSupportFragmentManager()));
        pager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                indicators.setCurrentPage(position);
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });

    }

    class HomePagerAdapter extends FragmentPagerAdapter {

        public HomePagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            return fragments[position];
        }

        @Override
        public int getCount() {
            return 1;
        }
    }
}
