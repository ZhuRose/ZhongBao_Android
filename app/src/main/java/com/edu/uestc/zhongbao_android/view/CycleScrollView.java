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
    PageController indicators;
    Handler mhandler;
    List<ImageView> viewList;
    CycleScrollViewPagerAdapter pagerAdapter;
    CycleScrollViewSingleAdapter singleAdapter;
    CycleScrollViewPagerListener pagerListener;
    CycleScrollViewDataSource dataSource;
    CycleScrollViewDelegate delegate;
    int currentPage = 0;
    int totalPages = 0;
    int animationDuration = 0;

    public CycleScrollView(Context context) {
        super(context, null);
    }

    public CycleScrollView(Context context, AttributeSet attrs) {
        super(context, attrs);
        Log.v(tag,"loading...");
        LayoutInflater.from(context).inflate(R.layout.view_scroll_cycle, this);
        pager = (ViewPager)findViewById(R.id.view_scroll_cycle_pager);

        viewList = new ArrayList<ImageView>();
        for (int i=0; i<3; i++) {
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

        pagerAdapter = new CycleScrollViewPagerAdapter();
        singleAdapter = new CycleScrollViewSingleAdapter();
        pagerListener = new CycleScrollViewPagerListener();
        indicators = (PageController) findViewById(R.id.view_scroll_cycle_indicators);
        pager.setVisibility(GONE);
        mhandler = new Handler();
    }

    @Override
    protected void onDetachedFromWindow() {
        super.onDetachedFromWindow();
        Log.v("tag","detach");
        mhandler.removeCallbacks(runnable);
    }

    public void setAnimationDuration(int animationDuration) {
        this.animationDuration = animationDuration;
        if (animationDuration>0) mhandler.postDelayed(runnable, animationDuration*1000);
        else mhandler.removeCallbacks(runnable);
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
        mhandler.removeCallbacks(runnable);
        if (this.dataSource.numberOfPages() == 0) {
            totalPages = 0;
            currentPage = 0;
            pager.setVisibility(GONE);
            indicators.setVisibility(GONE);
        } else  { //多变少或者少变多
            boolean adapterChanged = (totalPages>1 != this.dataSource.numberOfPages()>1) || totalPages==0;
            totalPages = this.dataSource.numberOfPages();
            currentPage = currentPage%totalPages;
            pager.setVisibility(VISIBLE);
            if (animationDuration>0) mhandler.postDelayed(runnable, animationDuration*1000);
            if (totalPages>1) indicators.setVisibility(VISIBLE);
            else indicators.setVisibility(GONE);
            indicators.setTotalPages(this.totalPages, this.currentPage);
            if (adapterChanged) {
                pager.removeOnPageChangeListener(pagerListener);
                if (totalPages == 1) {
                    pager.setAdapter(singleAdapter);
                } else {
                    pager.setAdapter(pagerAdapter);
                    pager.addOnPageChangeListener(pagerListener);
                    pager.setCurrentItem(1, false);
                }
            } else {
                if (totalPages>1) {
                    changeView();
                } else {
                    changeSingleView();
                }
            }
        }
    }

//    private void setupPager() {
//        if (totalPages==0) pager.setVisibility(GONE);
//        else {
//            pager.setVisibility(VISIBLE);
//            pager.removeOnPageChangeListener(pagerListener);
//            if (totalPages == 1) {
//                pager.setAdapter(singleAdapter);
//            } else {
//                pager.setAdapter(pagerAdapter);
//                pager.addOnPageChangeListener(pagerListener);
//                pager.setCurrentItem(1, false);
//            }
//        }
//    }
//
//    private void setupIndicators() {
//        indicators.setTotalPages(this.totalPages, this.currentPage);
//        if (this.totalPages >1) {
//            indicators.setVisibility(VISIBLE);
//        } else  {
//            indicators.setVisibility(GONE);
//        }
//    }

    public int currentPageValue(int value) {
        if (totalPages == 0) return 0;
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

    public void changeView() {
        for(int i=0; i<viewList.size(); i++) {
            ImageView view = viewList.get(i);
            dataSource.pageAt(currentPageValue(i-1), view);
        }
    }

    public void changeSingleView() {
        ImageView view = viewList.get(0);
        dataSource.pageAt(0, view);
    }

    class CycleScrollViewSingleAdapter extends PagerAdapter {

        @Override
        public int getCount() {
            return 1;
        }

        @Override
        public boolean isViewFromObject(View view, Object object) {
            return view == object;
        }

        @Override
        public Object instantiateItem(ViewGroup container, int position) {//必须实现，实例化
            View view = viewList.get(0);
            dataSource.pageAt(0, (ImageView) view);
            container.addView(view);
            return viewList.get(0);
        }

        @Override
        public void destroyItem(ViewGroup container, int position, Object object) {//必须实现，销毁
            container.removeView(viewList.get(0));
        }
    }

    class CycleScrollViewPagerAdapter extends PagerAdapter {

        public CycleScrollViewPagerAdapter() {

        }

        @Override
        public int getCount() {//必须实现
            return 3;
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
                if ((position + positionOffset)==2.0 ){
                    currentPage = currentPageValue(1);
                    indicators.setCurrentPage(currentPage);
                    changeView();
                    pager.setCurrentItem(1,false);
                    Log.v("tag", "cur"+currentPage);
                } else if ((position + positionOffset)==0.0 ) {
                    currentPage = currentPageValue(-1);
                    indicators.setCurrentPage(currentPage);
                    changeView();
                    pager.setCurrentItem(1,false);
                    Log.v("tag", "pre"+currentPage);
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
                    if (animationDuration>0) mhandler.postDelayed(runnable, animationDuration*1000);
                    break;
                default: //end
                    break;
            }
        }

    }

}
