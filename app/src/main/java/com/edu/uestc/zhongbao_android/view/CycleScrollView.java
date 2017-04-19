package com.edu.uestc.zhongbao_android.view;

import android.content.Context;
import android.os.Handler;
import android.support.constraint.ConstraintLayout;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.edu.uestc.zhongbao_android.R;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by zhu on 17/3/12.
 */

public class CycleScrollView extends ConstraintLayout {
    static String tag = "CycleScrollView";

    ViewPager pager;
    CycleScrollViewIndicators indicators;
    Handler mhandler;
    List<ImageView> viewList;
    CycleScrollViewPagerAdapter pagerAdapter;
    CycleScrollViewPagerListener pagerListener;
    CycleScrollViewDataSource dataSource;
    CycleScrollViewDelegate delegate;
    int currentPage = 0;
    int totalPages = 1;
    int animationDuration = 0;
    boolean haDone = false;

    public CycleScrollView(Context context) {
        super(context, null);
    }

    public CycleScrollView(Context context, AttributeSet attrs) {
        super(context, attrs);
        Log.v(tag,"loading...");
        LayoutInflater.from(context).inflate(R.layout.view_scroll_cycle, this);
        pager = (ViewPager)findViewById(R.id.view_scroll_cycle_pager);
        viewList = new ArrayList<ImageView>();
        pagerAdapter = new CycleScrollViewPagerAdapter();
        pager.setAdapter(pagerAdapter);
        pagerListener = new CycleScrollViewPagerListener();
        pager.addOnPageChangeListener(pagerListener);
        indicators = (CycleScrollViewIndicators) findViewById(R.id.view_scroll_cycle_indicators);
        mhandler = new Handler();
    }

    @Override
    protected void onDetachedFromWindow() {
        super.onDetachedFromWindow();
        Log.v("tag","detach");
        mhandler.removeCallbacks(runnable);
    }

    private void setupPager(Context context) {
        viewList.clear();
        int count = (totalPages>1)? 3: totalPages;
        for (int i=0; i<count; i++) {
            ImageView view = new ImageView(context);
            view.setOnClickListener(new OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (delegate != null) delegate.didSelectItemAt(currentPage);
                }
            });
            view.setScaleType(ImageView.ScaleType.FIT_XY);
            viewList.add(view);
        }
//        pagerAdapter.notifyDataSetChanged();
        pager.setAdapter(pagerAdapter);
    }

    public void setAnimationDuration(int animationDuration) {
        this.animationDuration = animationDuration;
        if (animationDuration>0) mhandler.postDelayed(runnable, animationDuration*1000);
        else mhandler.removeCallbacks(runnable);

    }

    private void setupIndicators() {
        indicators.setTotalPages(this.totalPages, this.currentPage);
        if (this.totalPages >1) {
            indicators.setVisibility(VISIBLE);
        } else  {
            indicators.setVisibility(GONE);
        }
    }

    private Runnable runnable = new Runnable() {
        @Override
        public void run() {
            if (totalPages>1) pager.setCurrentItem(2, true);
        }
    };

    public void setDataSource(CycleScrollViewDataSource dataSource) {
        this.dataSource = dataSource;
        reload();
    }

    public void setDelegate(final CycleScrollViewDelegate delegate) {
        this.delegate = delegate;
    }

    public void reload() {
        haDone = false;
        totalPages = this.dataSource.numberOfPages();
        currentPage = 0;
        setupPager(getContext());
        if (totalPages>1) pager.setCurrentItem(1, false);
        setupIndicators();
    }

    public int currentPageValue(int value) {
        int page = this.currentPage;
        if (value>0) {
            page ++;
            if (page >= this.totalPages) page -= this.totalPages;
        } else if (value<0) {
            page --;
            if (page < 0) page += this.totalPages;
        }
        return page;
    }

    class CycleScrollViewPagerAdapter extends PagerAdapter {

        public CycleScrollViewPagerAdapter() {

        }

        @Override
        public int getCount() {//必须实现
            return viewList.size();
        }

        @Override
        public boolean isViewFromObject(View view, Object object) {//必须实现
            return view == object;
        }

        @Override
        public Object instantiateItem(ViewGroup container, int position) {//必须实现，实例化
            View view = viewList.get(position);
            dataSource.pageAt(currentPageValue(position-1), (ImageView) view);
            container.addView(view);
            return viewList.get(position);
        }

        @Override
        public void destroyItem(ViewGroup container, int position, Object object) {//必须实现，销毁
            container.removeView(viewList.get(position));
        }
    }

    class CycleScrollViewPagerListener implements ViewPager.OnPageChangeListener {

        @Override
        public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
            if (totalPages>1) {
                if ((position + positionOffset)==2.0 && haDone){
                    currentPage = currentPageValue(1);
                    indicators.setCurrentPage(currentPage);
                    changeView();
                    pager.setCurrentItem(1,false);
                    Log.v("tag", "cur"+currentPage);

                } else if ((position + positionOffset)==0.0 && haDone) {
                    currentPage = currentPageValue(-1);
                    indicators.setCurrentPage(currentPage);
                    changeView();
                    pager.setCurrentItem(1,false);
                    Log.v("tag", "pre"+currentPage);
                } else {
                    haDone = true;
                }
            }
        }

        @Override
        public void onPageSelected(int position) {
        }

        @Override
        public void onPageScrollStateChanged(int state) {
            switch (state) {
                case 1: //press
                    mhandler.removeCallbacks(runnable);
                    break;
                case 2: //up
                    mhandler.postDelayed(runnable, animationDuration*1000);
                    break;
                default: //end
                    break;
            }
        }

        public void changeView() {
            for(int i=0; i<viewList.size(); i++) {
                ImageView view = viewList.get(i);
                dataSource.pageAt(currentPageValue(i-1), view);
            }
        }
    }

}
