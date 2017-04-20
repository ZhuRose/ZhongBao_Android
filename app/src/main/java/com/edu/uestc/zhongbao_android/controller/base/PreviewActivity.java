package com.edu.uestc.zhongbao_android.controller.base;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.transition.Fade;
import android.view.MotionEvent;
import android.view.View;

import com.edu.uestc.zhongbao_android.R;
import com.edu.uestc.zhongbao_android.model.PictureModel;
import com.edu.uestc.zhongbao_android.view.HackyViewPager;

import java.util.List;

import butterknife.BindView;
import butterknife.OnTouch;

/**
 * Created by zhu on 17/4/20.
 */

public class PreviewActivity extends BaseActivity {

    List<String> imgUrlList;
    int index;
    ImageViewPagerAdapter adapter;

    @BindView(R.id.pager)
    HackyViewPager pager;

    @Override
    protected int loadLayout() {
        return R.layout.activity_preview;
    }

    @Override
    protected void initData() {
        super.initData();
        getWindow().setEnterTransition(new Fade());
        getWindow().setReturnTransition(new Fade());
        imgUrlList = getIntent().getStringArrayListExtra("imgUrlList");
        index = getIntent().getIntExtra("index", 0);
    }

    @Override
    protected void initView() {
        super.initView();
        adapter = new ImageViewPagerAdapter(getSupportFragmentManager(), imgUrlList);
        pager.setAdapter(adapter);
        pager.setCurrentItem(index);
    }

    class ImageViewPagerAdapter extends FragmentStatePagerAdapter {
        private static final String IMAGE_URL = "image";

        List<String> mDatas;

        public ImageViewPagerAdapter(FragmentManager fm, List data) {
            super(fm);
            mDatas = data;
        }

        @Override
        public Fragment getItem(int position) {
            String url = mDatas.get(position);
            Fragment fragment = ImageFragment.newInstance(url);
            return fragment;
        }

        @Override
        public int getCount() {
            return mDatas.size();
        }
    }
}
